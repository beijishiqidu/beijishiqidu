package personal.blog.dao.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import personal.blog.dao.GenericDao;

@Repository(value = "genericDao")
public class GenericDaoImpl implements GenericDao {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    /**
     * 调用此方法进行级联的保存，前提是Annotation中的级联必须是persist.
     */
    @Override
    public void persistObject(Object entity) {
        hibernateTemplate.persist(entity);
        hibernateTemplate.flush();
    }

    @Override
    public Long saveObject(Object entity) {
        Long entityId = (Long) hibernateTemplate.save(entity);
        hibernateTemplate.flush();
        return entityId;
    }

    @Override
    public void updateObject(Object entity) {
        hibernateTemplate.update(entity);
        hibernateTemplate.flush();
    }

    @Override
    public <T> void deleteObject(Class<T> cls, Long id) {
        T obj = hibernateTemplate.get(cls, id);
        if (null != obj) {
            hibernateTemplate.delete(obj);
        }
        hibernateTemplate.flush();
    }

    @Override
    public void deleteObject(Object entity) {
        hibernateTemplate.delete(entity);
        hibernateTemplate.flush();
    }

    @Override
    public void saveOrUpdateObject(Object entity) {
        hibernateTemplate.saveOrUpdate(entity);
        hibernateTemplate.flush();
    }

    @Override
    public <T> T getObject(Class<T> cls, Long id) {
        return hibernateTemplate.get(cls, id);
    }

    @Override
    public <T> T getObject(Class<T> cls, String id) {
        return hibernateTemplate.get(cls, Long.parseLong(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> findObjectListByHqlStr(Class<T> cls, String hqlStr, Object... params) {
        return (List<T>) hibernateTemplate.find(hqlStr, params);
    }

    @Override
    public <T> T getUniqueObject(Class<T> cls, final String hqlStr, final Object... params) {
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public T doInHibernate(Session session) throws HibernateException {
                Query queryObject = session.createQuery(hqlStr);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        queryObject.setParameter(i, params[i]);
                    }
                }
                return (T) queryObject.setMaxResults(1).uniqueResult();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> list(Class<T> cls) {
        return getCurrentSession().createCriteria(cls).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> listWithCache(Class<T> cls) {
        return getCurrentSession().createCriteria(cls).setCacheable(true).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findRowsByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults) {
        return (List<T>) hibernateTemplate.findByCriteria(detachedCriteria, firstResult, maxResults);
    }

    @Override
    public <T> List<T> findObjectListByHql(final String hql, final int firstResult, final int maxResults) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            @SuppressWarnings("unchecked")
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResults);
                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> findObjectListByHqlWithParams(final String hql, final int firstResult, final int maxResults, final Object... params) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            @SuppressWarnings("unchecked")
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResults);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public Long findCountByCriteria(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
        CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
        Projection projection = criteriaImpl.getProjection();
        System.currentTimeMillis();
        Long totalCount = (java.lang.Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }

        return totalCount;
    }

    @Override
    public <T> List<T> execSqlQuery(final Class<T> cls, final String sqlStr, final Object... params) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            @SuppressWarnings("unchecked")
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sqlStr);
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                }
                return query.addEntity(cls).list();
            }
        });
    }

    @Override
    public <T> List<T> execSqlQuery(final Class<T> cls, final String sqlStr, final int firstResult, final int maxResults, final Object... params) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            @SuppressWarnings("unchecked")
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sqlStr);
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                }
                return query.addEntity(cls).setFirstResult(firstResult).setMaxResults(maxResults).list();
            }
        });
    }

    @Override
    public List<?> execSqlQuery(final String sqlStr, final Object... params) {
        SQLQuery query = getCurrentSession().createSQLQuery(sqlStr);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query.list();
    }

    @Override
    public int bulkUpdate(String hqlStr, Object... params) {
        return hibernateTemplate.bulkUpdate(hqlStr, params);
    }

    @Override
    public int execSqlUpdate(final String sqlStr, final Object[] params) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sqlStr);
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                }
                Integer resultCount = query.executeUpdate();
                hibernateTemplate.flush();
                return resultCount;
            }
        });
    }

    @Override
    public Session getCurrentSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    @Override
    public Session openSession() {
        return hibernateTemplate.getSessionFactory().openSession();
    }

    @Override
    public <T> List<T> getEntityObjectListByFullSql(final String fullSql, final Class<T> clzz){
        
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery = session.createSQLQuery(fullSql);
                sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> list = sqlQuery.list();

                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }

                List<T> result = new ArrayList<T>();
                try {
                    PropertyDescriptor[] props = Introspector.getBeanInfo(clzz).getPropertyDescriptors();
                    for (Map<String, Object> map : list) {
                        T t = clzz.newInstance();
                        for (Entry<String, Object> entry : map.entrySet()) {
                            String attrName = entry.getKey();
                            for (PropertyDescriptor prop : props) {
                                if (!attrName.equals(prop.getName())) {
                                    continue;
                                }
                                Method method = prop.getWriteMethod();

                                Object value = entry.getValue();
                                if (value != null) {
                                    value = ConvertUtils.convert(value, prop.getPropertyType());
                                }
                                method.invoke(t, value);
                            }
                        }
                        result.add(t);
                    }
                } catch (Exception e) {
                    throw new HibernateException(e);
                }
                return result;
            }
        });
    }

}
