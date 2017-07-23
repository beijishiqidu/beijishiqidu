package personal.blog.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
import personal.blog.vo.Photo;
import personal.blog.vo.PhotoAlbum;
import personal.blog.vo.PhotoType;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final Logger LOGGER = Logger.getLogger(PhotoServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Override
    public PageSplitUtil<Photo> getPhotoListForPage(Integer firstResult, Integer maxResults, String typeId) {

        LOGGER.info("Query param typeId=" + typeId);

        firstResult = firstResult == null ? 0 : firstResult;
        maxResults = maxResults == null ? 6 : maxResults;

        DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);

        if (StringUtils.isNotEmpty(typeId)) {
            PhotoType type = genericDao.getObject(PhotoType.class, typeId);
            dc.createAlias("album", "album");
            dc.add(Restrictions.eq("album.type", type));
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
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache")
    public List<PhotoType> getPhotoTypeList() {
        return genericDao.listWithCache(PhotoType.class);
    }

    @Override
    // @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache")
    public List<TypeCount> getPhotoTypeCount() {
        List<TypeCount> list =
                genericDao.getEntityObjectListByFullSql(
                        "select ab.type typeId,count(a.id) as typeCount from tbl_photo a, tbl_photo_album ab where a.album=ab.id group by a.album",
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
    public Long savePhotoInfo(String photoId, String title, String type, List<Photo> list) {

        PhotoAlbum pa = null;

        PhotoType pt = genericDao.getObject(PhotoType.class, type);
        if (StringUtils.isEmpty(photoId)) {
            pa = new PhotoAlbum();
            pa.setScanTimes(0L);
            pa.setCreateDate(Calendar.getInstance());
            pa.setType(pt);
            pa.setTitle(title);
        } else {
            pa = genericDao.getObject(PhotoAlbum.class, photoId);
            pa.setTitle(title);
            pa.setType(pt);
            pa.setUpdateDate(Calendar.getInstance());
        }

        genericDao.saveOrUpdateObject(pa);

        // DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);
        // dc.add(Restrictions.eq("album", pa));
        // List<Photo> photoList = genericDao.findRowsByCriteria(dc, 0, 999);

        for (Photo photo : list) {
            photo.setCreateDate(Calendar.getInstance());
            photo.setUpdateDate(Calendar.getInstance());
            photo.setAlbum(pa);
            photo.setScanTimes(0L);
            genericDao.saveObject(photo);
            LOGGER.info("photo===>" + photo.getFilePath() + "==>" + photo.getUrlPath());
        }

        return pa.getId();
    }


    @Override
    public void deletePhotoById(Long photoId) {
        // TODO Auto-generated method stub

    }

    @Override
    public Photo getPhotoById(Long photoId) {
        // TODO Auto-generated method stub
        return null;
    }
}
