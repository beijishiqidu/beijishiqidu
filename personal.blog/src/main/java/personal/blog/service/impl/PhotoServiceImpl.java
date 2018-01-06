package personal.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import personal.blog.cache.TypeCount;
import personal.blog.dao.GenericDao;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.service.PhotoService;
import personal.blog.util.FormValidateUtil;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.ExecResult;
import personal.blog.vo.Photo;
import personal.blog.vo.PhotoAlbum;
import personal.blog.vo.PhotoType;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final Logger LOGGER = Logger.getLogger(PhotoServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Override
    public PageSplitUtil<Photo> getPhotoListForPage(Integer firstResult, Integer maxResults, String albumId) {

        LOGGER.info("Query param typeId=" + albumId);

        firstResult = firstResult == null ? 0 : firstResult;
        maxResults = maxResults == null ? 6 : maxResults;

        DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);

        if (StringUtils.isNotEmpty(albumId)) {
            dc.add(Restrictions.eq("album.id", Long.valueOf(albumId)));
        }
        Long totalCount = genericDao.findCountByCriteria(dc);

        dc.addOrder(Order.desc("createDate"));
        List<Photo> list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);

        if (list.isEmpty()) {
            if (firstResult >= maxResults) {
                firstResult = firstResult - maxResults;
                list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);
            }
        }

        PageSplitUtil<Photo> ps = new PageSplitUtil<Photo>(list, firstResult, maxResults, totalCount.longValue());
        return ps;
    }


    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoTypeList'")
    public List<PhotoType> getPhotoTypeList() {
        return genericDao.listWithCache(PhotoType.class);
    }

    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoAlbumList'")
    public List<PhotoAlbum> getPhotoAlbumList() {
        return genericDao.listWithCache(PhotoAlbum.class);
    }

    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoAlbumCount'")
    public List<TypeCount> getPhotoAlbumCount() {
        List<TypeCount> list =
                genericDao
                        .getEntityObjectListByFullSql(
                                "select a.album typeId,count(a.id) as typeCount, ab.title as typeName from tbl_photo a, tbl_photo_album ab where a.album=ab.id group by a.album",
                                TypeCount.class);
        return list;
    }

    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoTypeCount'")
    public List<TypeCount> getPhotoTypeCount() {
        List<TypeCount> list =
                genericDao
                        .getEntityObjectListByFullSql(
                                "SELECT a.type AS typeId, count(a.type) as typeCount FROM tbl_photo_album a, tbl_photo_type b WHERE a.type=b.id GROUP BY a.type",
                                TypeCount.class);
        List<PhotoType> typeList = getPhotoTypeList();

        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<TypeCount>();
        }
        for (PhotoType at : typeList) {

            // 查询出某些类型下没有文章的列表.
            boolean find = false;
            for (TypeCount tc : list) {
                if (at.getId().longValue() == tc.getTypeId().longValue()) {
                    tc.setTypeName(at.getTypeName());
                    find = true;
                }
            }
            if (!find) {
                TypeCount tc = new TypeCount();
                tc.setTypeCount("0");
                tc.setTypeId(at.getId().longValue());
                tc.setTypeName(at.getTypeName());
                list.add(tc);
            }
        }
        return list;
    }

    @Override
    public List<FormAlert> validatePhotoForm(PhotoForm photoForm) {
        return FormValidateUtil.validate(photoForm);
    }


    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoTypeCount'", allEntries = true)
    public Long savePhotoInfo(String photoAlbumId, String title, String type, List<Photo> list) {

        PhotoAlbum pa = null;

        PhotoType pt = genericDao.getObject(PhotoType.class, type);
        if (StringUtils.isEmpty(photoAlbumId)) {
            pa = new PhotoAlbum();
            pa.setScanTimes(0L);
            pa.setCreateDate(Calendar.getInstance());
            pa.setType(pt);
            pa.setTitle(title);
        } else {
            pa = genericDao.getObject(PhotoAlbum.class, photoAlbumId);
            pa.setTitle(title);
            pa.setType(pt);
            pa.setUpdateDate(Calendar.getInstance());
        }

        genericDao.saveOrUpdateObject(pa);

        boolean first = false;
        for (Photo photo : list) {
            photo.setCreateDate(Calendar.getInstance());
            photo.setUpdateDate(Calendar.getInstance());
            photo.setAlbum(pa);
            photo.setScanTimes(0L);

            // 第一次新建相册时给定封面，后续编辑的时候不需要给定封面.
            if (!first && StringUtils.isEmpty(photoAlbumId)) {
                photo.setFace(true);
                first = true;
            }

            genericDao.saveObject(photo);
            LOGGER.info("photo===>" + photo.getFilePath() + "==>" + photo.getUrlPath());
        }

        return pa.getId();
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public boolean deletePhotoById(String photoId) {

        boolean result = false;
        Photo photo = genericDao.getObject(Photo.class, photoId);

        try {
            FileUtils.delete(new File(photo.getFilePath()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return result;
        }

        genericDao.deleteObject(photo);
        result = true;

        return result;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public ExecResult deletePhotoTypeById(String typeId) {

        ExecResult er = new ExecResult();

        // 查询有没有相册关联到此类型中，如果有，则提示不能删除.
        DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);

        if (StringUtils.isNotEmpty(typeId)) {
            dc.createAlias("album", "album");
            dc.createAlias("album.type", "type");
            dc.add(Restrictions.eq("type.id", Long.valueOf(typeId)));
        }
        Long totalCount = genericDao.findCountByCriteria(dc);

        if (totalCount.intValue() > 0) {
            er.setResult(false);
            er.setMessage("本相册类型下有对应的相册");
        } else {
            genericDao.deleteObject(PhotoType.class, typeId);
            er.setResult(true);
        }

        return er;
    }

    @Override
    public List<Photo> getPhotoAlnumFaceList(String photoTypeId) {

        DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);

        if (StringUtils.isNotEmpty(photoTypeId)) {
            PhotoType type = genericDao.getObject(PhotoType.class, photoTypeId);
            dc.createAlias("album", "album");
            dc.add(Restrictions.eq("album.type", type));
        }

        dc.add(Restrictions.eq("face", true));

        dc.addOrder(Order.desc("createDate"));
        // 给定一个默认的1000条.
        List<Photo> list = genericDao.findRowsByCriteria(dc, 0, 1000);

        return list;
    }

    @Override
    public PhotoType getPhotoTypeById(String typeId) {
        return genericDao.getObject(PhotoType.class, typeId);
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getPhotoTypeList'", allEntries = true)
    public ExecResult savePhotoTypeInfo(String typeId, String typeName) {

        ExecResult er = new ExecResult();
        if (StringUtils.isEmpty(typeName)) {
            er.setMessage("相册类型不能为空");
            return er;
        }

        List<PhotoType> list = getPhotoTypeList();
        for (PhotoType at : list) {
            if (at.getTypeName().equals(typeName) && StringUtils.isEmpty(typeId)) {
                er.setMessage("该类型名称已经存在");
                return er;
            }
        }

        PhotoType photoType = null;
        if (StringUtils.isEmpty(typeId)) {
            photoType = new PhotoType();
        } else {
            photoType = genericDao.getObject(PhotoType.class, typeId);
        }
        photoType.setTypeName(typeName);
        genericDao.saveOrUpdateObject(photoType);

        er.setResult(true);
        er.setMessage("类型保存成功");
        er.getAppend().put("id", photoType.getId());

        return er;
    }


    @Override
    public PageSplitUtil<Photo> getPhotoListForPageByAlbumId(Integer firstResult, Integer maxResults, String albumId) {

        LOGGER.info("Query param typeId=" + albumId);

        firstResult = firstResult == null ? 0 : firstResult;
        maxResults = maxResults == null ? 6 : maxResults;

        DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);

        if (StringUtils.isNotEmpty(albumId)) {
            dc.createAlias("album", "album");
            dc.add(Restrictions.eq("album.id", Long.valueOf(albumId)));
        }
        Long totalCount = genericDao.findCountByCriteria(dc);

        dc.addOrder(Order.desc("createDate"));
        List<Photo> list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);

        if (list.isEmpty()) {
            if (firstResult >= maxResults) {
                firstResult = firstResult - maxResults;
                list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);
            }
        }

        PageSplitUtil<Photo> ps = new PageSplitUtil<Photo>(list, firstResult, maxResults, totalCount.longValue());

        return ps;
    }

}
