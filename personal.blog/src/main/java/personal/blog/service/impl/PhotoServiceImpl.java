package personal.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import personal.blog.cache.TypeCount;
import personal.blog.dao.GenericDao;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.service.PhotoService;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Photo;
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
            dc.add(Restrictions.eq("type", type));
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
                genericDao.getEntityObjectListByFullSql("select a.type typeId,count(a.id) as typeCount from tbl_photo a group by a.type",
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
    public List<FormAlert> validatePhotoForm(PhotoForm articleForm) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Long savePhotoInfo(String photoId, String title, String type) {
        // TODO Auto-generated method stub
        return null;
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
