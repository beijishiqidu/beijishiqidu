package personal.blog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

public interface GenericDao {

    <T> T getObject(Class<T> cls, Long id);

    <T> T getObject(Class<T> cls, String id);

    <T> List<T> findObjectListByHqlStr(Class<T> cls, String hqlStr, Object... params);

    <T> T getUniqueObject(Class<T> cls, String hqlStr, Object... params);

    <T> List<T> list(Class<T> cls);

    Long saveObject(Object entity);

    void updateObject(Object entity);

    <T> List<T> findRowsByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults);

    Long findCountByCriteria(DetachedCriteria detachedCriteria);

    void saveOrUpdateObject(Object entity);

    <T> void deleteObject(Class<T> cls, Long id);

    void deleteObject(Object entity);

    <T> List<T> execSqlQuery(Class<T> cls, String sqlStr, Object... params);

    <T> List<T> execSqlQuery(Class<T> cls, String sqlStr, int firstResult, int maxResults, Object[] params);

    List<?> execSqlQuery(String sqlStr, Object... params);

    int execSqlUpdate(String sqlStr, Object[] params);

    <T> List<T> findObjectListByHql(String hql, int firstResult, int maxResults);

    <T> List<T> findObjectListByHqlWithParams(String hql, int firstResult, int maxResults, Object... params);

    void persistObject(Object entity);

    int bulkUpdate(String hqlStr, Object... params);

    Session getCurrentSession();

    <T> List<T> listWithCache(Class<T> cls);

    Session openSession();

    <T> List<T> getEntityObjectListByFullSql(String fullSql, Class<T> clzz);


}
