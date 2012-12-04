package kendoui.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import kendoui.exception.DAOException;
import kendoui.utils.Constant;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * The wrapper for entity manager, can set the entity manager for mocking.
 */

@SuppressWarnings("unchecked")
public abstract class BaseDao<T> implements IDao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);

    private EntityManager entityManager;

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#setEntityManager(javax.persistence.EntityManager)
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#queryByList(String, Object[])
     */
    @Override
    public List<?> queryByList(String query, Object... args) {
        return queryByList(query, -1, -1, args);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getByList(String, Object[])
     */
    @Override
    public List<T> getByList(String query, Object... args) {
        return (List<T>) queryByList(query, args);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getByField(String, Object)
     */
    @Override
    public T getByField(String name, Object value) {
        return getByOne(MessageFormat.format("from {0} where {1} = ?", getEntityName(), name), value);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getByOne(String, Object[])
     */
    @Override
    public T getByOne(String query, Object... args) {
        return (T) queryByOne(query, args);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#queryByOne(String, Object[])
     */
    @Override
    public Object queryByOne(String query, Object... args) {
        int i = 1;
        Query q = entityManager.createQuery(query);
        // Binding the args
        for (Object arg : args) {
            q.setParameter(i++, arg);
        }
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            LOG.warn("Can not find result for query: {}", query);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#executeNativeQuery(String, Object[])
     */
    public void executeNativeQuery(String query, Object... args) {
        int i = 1;
        Query q = entityManager.createNativeQuery(query);
        // Binding the ARGS
        for (Object arg : args) {
            q.setParameter(i++, arg);
        }
        q.executeUpdate();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getByList(String, int, int, Object[])
     */
    @Override
    public List<T> getByList(String query, int offset, int count, Object... args) {
        return queryByList(query, offset, count, args);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getList()
     */
    @Override
    public List<T> getList() {
        return getByList("from " + getEntityName());
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#queryByList(String, int, int, Object[])
     */
    @Override
    public List<T> queryByList(String query, int offset, int count, Object... args) {
        int i = 1;
        Query q = entityManager.createQuery(query);
        // Binding the ARGS
        for (Object arg : args) {
            q.setParameter(i++, arg);
        }
        if (offset != -1) {
            q.setFirstResult(offset);
        }
        if (count != -1) {
            q.setMaxResults(count);
        }
        return q.getResultList();
    }


    /*
    * (non-Javadoc)
    *
    * @see kendoui.dao.IDao#queryByCriteria(java.lang.String, int, int)
    */
    @Override
    public List<T> queryByCriteria(String jpa, int pageSize, int pageNumber) throws DAOException {
        LOG.debug("Query the database using jpa {}, pageSize {} and pageNumber {}", new Object[]{jpa, pageSize,
                pageNumber});
        Query query = entityManager.createQuery(jpa);

        // Only apply pagination if pageSize is non-zero
        if (pageSize != 0) {
            int pageSizeResult = pageSize <= 0 ? Constant.DEFAULT_PAGE_SIZE : pageSize;
            int pageNumberResult = pageNumber <= 0 ? Constant.DEFAULT_PAGE_NUMBER : pageNumber;
            query.setFirstResult((pageNumberResult - 1) * pageSizeResult);
            query.setMaxResults(pageSizeResult);
        }
        List<Object> list = query.getResultList();
        if (!list.isEmpty() && getEntityName().equals(list.get(0).getClass().getSimpleName())) {
            return (List<T>) list;
        }
        List<T> actualList = new ArrayList<T>();
        for (Object object : list) {
            actualList.add((T) (((Object[]) object)[0]));
        }
        return actualList;
    }

    /*
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#queryByNativeCriteria(java.lang.String, java.lang.Class, int, int)
     */
    public List<T> queryByNativeCriteria(String sql, Class<T> entityClass, int pageSize, int pageNumber)
            throws DAOException {
        LOG.debug("Query the database using native sql {}, pageSize {} and pageNumber {}", new Object[]{sql,
                pageSize, pageNumber});
        Query query = createNativeQuery(sql, entityClass);

        // Only apply pagination if pageSize is non-zero
        if (pageSize != 0) {
            int pageSizeResult = pageSize <= 0 ? Constant.DEFAULT_PAGE_SIZE : pageSize;
            int pageNumberResult = pageNumber <= 0 ? Constant.DEFAULT_PAGE_NUMBER : pageNumber;
            query.setFirstResult((pageNumberResult - 1) * pageSizeResult);
            query.setMaxResults(pageSizeResult);
        }

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#queryByCriteria(javax.persistence.Query, java.lang.String, java.lang.String)
     */
    public List<T> queryByCriteria(Query query, int pageSize, int pageNumber) throws DAOException {
        LOG.debug("Query the database using jpa query, pageSize {} and pageNumber {}", new Object[]{pageSize,
                pageNumber});

        // Only apply pagination if pageSize is non-zero
        if (pageSize != 0) {
            int pageSizeResult = pageSize <= 0 ? Constant.DEFAULT_PAGE_SIZE : pageSize;
            int pageNumberResult = pageNumber <= 0 ? Constant.DEFAULT_PAGE_NUMBER : pageNumber;
            query.setFirstResult((pageNumberResult - 1) * pageSizeResult);
            query.setMaxResults(pageSizeResult);
        }

        return query.getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#persist(Object)
     */
    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#merge(Object)
     */
    @Override
    @Deprecated
    public T merge(T entity) {
        return merge(entity, null);
    }

    /**
     * Get version field from class
     *
     * @param clazz Entity class
     * @return
     */
    private Field getVersionField(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Version) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * Create a new entity instance
     *
     * @param entity
     * @return
     */
    private T createEntityInstance(T entity) {
        T obj = null;
        try {
            obj = (T) Class.forName(entity.getClass().getName()).newInstance();
        } catch (InstantiationException e) {
            LOG.error("Instantiation error: {}", e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.error("Illegal access error: {}", e.getMessage());
        } catch (ClassNotFoundException e) {
            LOG.error("Class not found, class name is: {}", entity.getClass().getName());
        }
        return obj == null ? entity : obj;
    }

    /**
     * Set value for version field
     *
     * @param obj         A new created entity instance
     * @param lastUpdated
     */
    private void setVersionField(T obj, Timestamp lastUpdated) throws IllegalArgumentException, SecurityException {
        Field versionField = getVersionField((Class<T>) obj.getClass());
        if (versionField == null) {
            return;
        }
        try {
            Method versionSetMethod = obj.getClass().getMethod(parSetName(versionField.getName()), versionField.getType());
            versionSetMethod.invoke(obj, lastUpdated);
        } catch (NoSuchMethodException e) {
            LOG.error("No such method error: {}", e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.error("Illegal access error: {}", e.getMessage());
        } catch (InvocationTargetException e) {
            LOG.error("Invocation error: {}", e.getMessage());
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#merge(Object, java.sql.Timestamp)
     */
    @Override
    public T merge(T entity, Timestamp lastUpdated) {
        T obj = null;
        if (lastUpdated == null) {
            //TODO Need refactor in the future for catch concurrency edit exception
            obj = entity;
        } else {
            obj = createEntityInstance(entity);
        }

        // Merge if already in persistence context
        if (entityManager.contains(obj)) {
            return entityManager.merge(obj);
        }

        // Clone entity with last updated time from FE
        BeanUtils.copyProperties(entity, obj);
        setVersionField(obj, lastUpdated);

        return entityManager.merge(obj);
    }

    /**
     * Generate set method name base on field name
     *
     * @param fieldName
     * @return
     */
    public static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#remove(Object)
     */
    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#find(Long)
     */
    @Override
    public T find(Long primaryKey) {
        try {
            return entityManager.find(getPersistentClass(), primaryKey);
        } catch (IllegalArgumentException e) {
            LOG.warn("Got illegal argument: {}, error message: {}", primaryKey, e.getMessage());
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#find(Long, java.util.Map)
     */
    @Override
    public T find(Long primaryKey, Map<String, Object> properties) {
        try {
            return entityManager.find(getPersistentClass(), primaryKey, properties);
        } catch (IllegalArgumentException e) {
            LOG.warn("Got illegal argument: {}, error message: {}", primaryKey, e.getMessage());
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#find(Long, javax.persistence.LockModeType)
     */
    @Override
    public T find(Long primaryKey, LockModeType lockMode) {
        try {
            return entityManager.find(getPersistentClass(), primaryKey, lockMode);
        } catch (IllegalArgumentException e) {
            LOG.warn("Got illegal argument: {}, error message: {}", primaryKey, e.getMessage());
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#find(Long, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public T find(Long primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        try {
            return entityManager.find(getPersistentClass(), primaryKey, lockMode, properties);
        } catch (IllegalArgumentException e) {
            LOG.warn("Got illegal argument {}, error message: {}", primaryKey, e.getMessage());
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getReference(Long)
     */
    @Override
    public T getReference(Long primaryKey) {
        return entityManager.getReference(getPersistentClass(), primaryKey);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#flush()
     */
    @Override
    public void flush() {
        entityManager.flush();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#setFlushMode(javax.persistence.FlushModeType)
     */
    @Override
    public void setFlushMode(FlushModeType flushMode) {
        entityManager.setFlushMode(flushMode);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getFlushMode()
     */
    @Override
    public FlushModeType getFlushMode() {
        return entityManager.getFlushMode();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#lock(Object, javax.persistence.LockModeType)
     */
    @Override
    public void lock(T entity, LockModeType lockMode) {
        entityManager.lock(entity, lockMode);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#lock(Object, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public void lock(T entity, LockModeType lockMode, Map<String, Object> properties) {
        entityManager.lock(entity, lockMode, properties);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#refresh(Object)
     */
    @Override
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#refresh(Object, java.util.Map)
     */
    @Override
    public void refresh(T entity, Map<String, Object> properties) {
        entityManager.refresh(entity, properties);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#refresh(Object, javax.persistence.LockModeType)
     */
    @Override
    public void refresh(T entity, LockModeType lockMode) {
        entityManager.refresh(entity, lockMode);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#refresh(Object, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public void refresh(T entity, LockModeType lockMode, Map<String, Object> properties) {
        entityManager.refresh(entity, lockMode, properties);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#clear()
     */
    @Override
    public void clear() {
        entityManager.clear();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#detach(Object)
     */
    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#contains(Object)
     */
    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getLockMode(Object)
     */
    @Override
    public LockModeType getLockMode(Object entity) {
        return entityManager.getLockMode(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#setProperty(String, Object)
     */
    @Override
    public void setProperty(String propertyName, Object value) {
        entityManager.setProperty(propertyName, value);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getProperties()
     */
    @Override
    public Map<String, Object> getProperties() {
        return entityManager.getProperties();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createQuery(String)
     */
    @Override
    public Query createQuery(String qlString) {
        return entityManager.createQuery(qlString);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createTypedQuery(String)
     */
    @Override
    public TypedQuery<T> createTypedQuery(String qlString) {
        return entityManager.createQuery(qlString, getPersistentClass());
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createTypedQueryWithType(String, Class)
     */
    @Override
    public <P> TypedQuery<P> createTypedQueryWithType(String qlString, Class<P> resultClass) {
        return entityManager.createQuery(qlString, resultClass);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createNamedQuery(String)
     */
    @Override
    public Query createNamedQuery(String name) {
        return entityManager.createNamedQuery(name);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createNativeQuery(String)
     */
    @Override
    public Query createNativeQuery(String sqlString) {
        return entityManager.createNativeQuery(sqlString);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createNativeQuery(String, Class)
     */
    @Override
    public Query createNativeQuery(String sqlString, Class<?> resultClass) {
        return entityManager.createNativeQuery(sqlString, resultClass);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#createNativeQuery(String, String)
     */
    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        return entityManager.createNativeQuery(sqlString, resultSetMapping);
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#joinTransaction()
     */
    @Override
    public void joinTransaction() {
        entityManager.joinTransaction();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#unwrap()
     */
    @Override
    public T unwrap() {
        return entityManager.unwrap(getPersistentClass());
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#close()
     */
    @Override
    public void close() {
        entityManager.close();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#isOpen()
     */
    @Override
    public boolean isOpen() {
        return entityManager.isOpen();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getTransaction()
     */
    @Override
    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getEntityManagerFactory()
     */
    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManager.getEntityManagerFactory();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getCriteriaBuilder()
     */
    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getMetaModel
     */
    @Override
    public Metamodel getMetaModel() {
        return entityManager.getMetamodel();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getLastRecord(Class)
     */
    @Override
    public T getLastRecord(Class<T> clazz) {
        Query query = createQuery(MessageFormat.format("from {0} order by id desc", clazz.getSimpleName()));
        query.setMaxResults(1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getEntityName()
     */
    @Override
    public String getEntityName() {
        Class<?> clazz = getPersistentClass();
        String entityName = clazz.getAnnotation(Entity.class).name();
        if (entityName == null || StringUtils.EMPTY.equals(entityName)) {
            entityName = clazz.getSimpleName();
        }
        return entityName;
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IDao#getPersistentClass()
     */
    @Override
    public Class<T> getPersistentClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
