package kendoui.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import kendoui.exception.DAOException;

/**
 * The Dao interface for all the Daos, it is the facade for JPA's EntityManager, but has many shorthand methods and it
 * is strongly typed using template type, so all the method dao provided is strongly typed.
 */
public interface IDao<T> {

    /**
     * Return an instance of <code>Metamodel</code> interface for access to the metamodel of the persistence unit.
     *
     * @return Metamodel instance
     * @throws IllegalStateException if the entity manager has been closed
     * @since Java Persistence 2.0
     */
    Metamodel getMetaModel();

    /**
     * Return an instance of <code>CriteriaBuilder</code> for the creation of <code>CriteriaQuery</code> objects.
     *
     * @return CriteriaBuilder instance
     * @throws IllegalStateException if the entity manager has been closed
     * @since Java Persistence 2.0
     */
    CriteriaBuilder getCriteriaBuilder();

    /**
     * Return the entity manager factory for the entity manager.
     *
     * @return EntityManagerFactory instance
     * @throws IllegalStateException if the entity manager has been closed
     * @since Java Persistence 2.0
     */
    EntityManagerFactory getEntityManagerFactory();

    /**
     * Return the resource-level <code>EntityTransaction</code> object. The <code>EntityTransaction</code> instance may
     * be used serially to begin and commit multiple transactions.
     *
     * @return EntityTransaction instance
     * @throws IllegalStateException if invoked on a JTA entity manager
     */
    EntityTransaction getTransaction();

    /**
     * Determine whether the entity manager is open.
     *
     * @return true until the entity manager has been closed
     */
    boolean isOpen();

    /**
     * Close an application-managed entity manager. After the close method has been invoked, all methods on the
     * <code>EntityManager</code> instance and any <code>Query</code> and <code>TypedQuery</code> objects obtained from
     * it will throw the <code>IllegalStateException</code> except for <code>getProperties</code>,
     * <code>getTransaction</code>, and <code>isOpen</code> (which will return false). If this method is called when the
     * entity manager is associated with an active transaction, the persistence context remains managed until the
     * transaction completes.
     *
     * @throws IllegalStateException if the entity manager is container-managed
     */
    void close();

    /**
     * Return an object of the specified type to allow access to the provider-specific API. If the provider's
     * <code>EntityManager</code> implementation does not support the specified class, the
     * <code>PersistenceException</code> is thrown.
     *
     * @return
     */
    T unwrap();

    /**
     * Indicate to the entity manager that a JTA transaction is active. This method should be called on a JTA
     * application managed entity manager that was created outside the scope of the active transaction to associate it
     * with the current JTA transaction.
     *
     * @throws javax.persistence.TransactionRequiredException
     *          if there is no transaction
     */
    void joinTransaction();

    /**
     * Create an instance of <code>Query</code> for executing a native SQL query.
     *
     * @param sqlString        a native SQL query string
     * @param resultSetMapping the name of the result set mapping
     * @return the new query instance
     */
    Query createNativeQuery(String sqlString, String resultSetMapping);

    /**
     * Create an instance of <code>Query</code> for executing a native SQL statement, e.g., for update or delete.
     *
     * @param sqlString a native SQL query string
     * @return the new query instance
     */
    Query createNativeQuery(String sqlString);

    /**
     * Create an instance of <code>Query</code> for executing
     * a native SQL query.
     *
     * @param sqlString   a native SQL query string
     * @param resultClass the class of the resulting instance(s)
     * @return the new query instance
     */
    Query createNativeQuery(String sqlString, Class<?> resultClass);

    /**
     * Create an instance of <code>Query</code> for executing a named query (in the Java Persistence query language or
     * in native SQL).
     *
     * @param name the name of a query defined in metadata
     * @return the new query instance
     * @throws IllegalArgumentException if a query has not been defined with the given name or if the query string is
     *                                  found to be invalid
     */
    Query createNamedQuery(String name);

    /**
     * Create an instance of <code>TypedQuery</code> for executing a Java Persistence query language statement. The
     * select list of the query must contain only a single item, which must be assignable to the type specified by the
     * <code>resultClass</code> argument.
     *
     * @param qlString    a Java Persistence query string
     * @param resultClass the type of the query result
     * @param <P>
     * @return the new query instance
     */
    <P> TypedQuery<P> createTypedQueryWithType(String qlString, Class<P> resultClass);

    TypedQuery<T> createTypedQuery(String qlString);

    /**
     * Create an instance of <code>TypedQuery</code> for executing a criteria query.
     *
     * @param criteriaQuery a criteria query object
     * @return the new query instance
     * @throws IllegalArgumentException if the criteria query is found to be invalid
     * @since Java Persistence 2.0
     */
    TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);

    /**
     * Create an instance of <code>Query</code> for executing a Java Persistence query language statement.
     *
     * @param qlString a Java Persistence query string
     * @return the new query instance
     * @throws IllegalArgumentException if the query string is found to be invalid
     */
    Query createQuery(String qlString);

    /**
     * Get the properties and hints and associated values that are in effect for the entity manager. Changing the
     * contents of the map does not change the configuration in effect.
     *
     * @return map of properties and hints in effect for entity manager
     * @since Java Persistence 2.0
     */
    Map<String, Object> getProperties();

    /**
     * Set an entity manager property or hint. If a vendor-specific property or hint is not recognized, it is silently
     * ignored.
     *
     * @param propertyName name of property or hint
     * @param value        value for property or hint
     * @throws IllegalArgumentException if the second argument is not valid for the implementation
     * @since Java Persistence 2.0
     */
    void setProperty(String propertyName, Object value);

    /**
     * Get the current lock mode for the entity instance.
     *
     * @param entity entity instance
     * @return lock mode
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction
     * @throws IllegalArgumentException if the instance is not a managed entity and a transaction is active
     * @since Java Persistence 2.0
     */
    LockModeType getLockMode(Object entity);

    /**
     * Check if the instance is a managed entity instance belonging to the current persistence context.
     *
     * @param entity entity instance
     * @return boolean indicating if entity is in persistence context
     * @throws IllegalArgumentException if not an entity
     */
    boolean contains(T entity);

    /**
     * Remove the given entity from the persistence context, causing a managed entity to become detached. Unflushed
     * changes made to the entity if any (including removal of the entity), will not be synchronized to the database.
     * Entities which previously referenced the detached entity will continue to reference it.
     *
     * @param entity entity instance
     * @throws IllegalArgumentException if the instance is not an entity
     * @since Java Persistence 2.0
     */
    void detach(T entity);

    /**
     * Clear the persistence context, causing all managed entities to become detached. Changes made to entities that
     * have not been flushed to the database will not be persisted.
     */
    void clear();

    /**
     * Refresh the state of the instance from the database, overwriting changes made to the entity, if any, and lock it
     * with respect to given lock mode type and with specified properties.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback
     * </ul>
     * <p/>
     * If a vendor-specific property or hint is not recognized, it is silently ignored.
     * <p/>
     * Portable applications should not rely on the standard timeout hint. Depending on the database in use and the
     * locking mechanisms used by the provider, the hint may or may not be observed.
     *
     * @param entity     entity instance
     * @param lockMode   lock mode
     * @param properties standard and vendor-specific properties and hints
     * @throws IllegalArgumentException if the instance is not an entity or the entity is not managed
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction and if invoked on a container-managed
     *                                  <code>EntityManager</code> instance with <code>PersistenceContextType.TRANSACTION</code> or with a
     *                                  lock mode other than <code>NONE</code>
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity no longer exists in the database
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     * @since Java Persistence 2.0
     */
    void refresh(T entity, LockModeType lockMode, Map<String, Object> properties);

    /**
     * Refresh the state of the instance from the database, overwriting changes made to the entity, if any, and lock it
     * with respect to given lock mode type.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback.
     * </ul>
     *
     * @param entity   entity instance
     * @param lockMode lock mode
     * @throws IllegalArgumentException if the instance is not an entity or the entity is not managed
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction and if invoked on a container-managed
     *                                  <code>EntityManager</code> instance with <code>PersistenceContextType.TRANSACTION</code> or with a
     *                                  lock mode other than <code>NONE</code>
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity no longer exists in the database
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     * @since Java Persistence 2.0
     */
    void refresh(T entity, LockModeType lockMode);

    /**
     * Refresh the state of the instance from the database, using the specified properties, and overwriting changes made
     * to the entity, if any.
     * <p/>
     * If a vendor-specific property or hint is not recognized, it is silently ignored.
     *
     * @param entity     entity instance
     * @param properties standard and vendor-specific properties and hints
     * @throws IllegalArgumentException if the instance is not an entity or the entity is not managed
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity no longer exists in the database
     * @since Java Persistence 2.0
     */
    void refresh(T entity, Map<String, Object> properties);

    /**
     * Refresh the state of the instance from the database, overwriting changes made to the entity, if any.
     *
     * @param entity entity instance
     * @throws IllegalArgumentException if the instance is not an entity or the entity is not managed
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity no longer exists in the database
     */
    void refresh(T entity);

    /**
     * Lock an entity instance that is contained in the persistence context with the specified lock mode type and with
     * specified properties.
     * <p/>
     * If a pessimistic lock mode type is specified and the entity contains a version attribute, the persistence
     * provider must also perform optimistic version checks when obtaining the database lock. If these checks fail, the
     * <code>OptimisticLockException</code> will be thrown.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback
     * </ul>
     * <p/>
     * If a vendor-specific property or hint is not recognized, it is silently ignored.
     * <p/>
     * Portable applications should not rely on the standard timeout hint. Depending on the database in use and the
     * locking mechanisms used by the provider, the hint may or may not be observed.
     *
     * @param entity     entity instance
     * @param lockMode   lock mode
     * @param properties standard and vendor-specific properties and hints
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity does not exist in the database when pessimistic locking is
     *                                  performed
     * @throws javax.persistence.OptimisticLockException
     *                                  if the optimistic version check fails
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     * @since Java Persistence 2.0
     */
    void lock(T entity, LockModeType lockMode, Map<String, Object> properties);

    /**
     * Lock an entity instance that is contained in the persistence context with the specified lock mode type.
     * <p/>
     * If a pessimistic lock mode type is specified and the entity contains a version attribute, the persistence
     * provider must also perform optimistic version checks when obtaining the database lock. If these checks fail, the
     * <code>OptimisticLockException</code> will be thrown.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback
     * </ul>
     *
     * @param entity   entity instance
     * @param lockMode lock mode
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity does not exist in the database when pessimistic locking is
     *                                  performed
     * @throws javax.persistence.OptimisticLockException
     *                                  if the optimistic version check fails
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     */
    void lock(T entity, LockModeType lockMode);

    /**
     * Get the flush mode that applies to all objects contained in the persistence context.
     *
     * @return flushMode
     */
    FlushModeType getFlushMode();

    /**
     * Set the flush mode that applies to all objects contained in the persistence context.
     *
     * @param flushMode flush mode
     */
    void setFlushMode(FlushModeType flushMode);

    /**
     * Synchronize the persistence context to the underlying database.
     *
     * @throws javax.persistence.TransactionRequiredException
     *          if there is no transaction
     * @throws javax.persistence.PersistenceException
     *          if the flush fails
     */
    void flush();

    /**
     * Get an instance, whose state may be lazily fetched. If the requested instance does not exist in the database, the
     * <code>EntityNotFoundException</code> is thrown when the instance state is first accessed. (The persistence
     * provider runtime is permitted to throw the <code>EntityNotFoundException</code> when <code>getReference</code> is
     * called.) The application should not expect that the instance state will be available upon detachment, unless it
     * was accessed by the application while the entity manager was open.
     *
     * @param primaryKey primary key
     * @return the found entity instance
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is
     *                                  not a valid type for that entitys primary key or is null
     * @throws javax.persistence.EntityNotFoundException
     *                                  if the entity state cannot be accessed
     */
    T getReference(Long primaryKey);

    /**
     * Find by primary key and lock, using the specified properties. Search for an entity of the specified class and
     * primary key and lock it with respect to the specified lock type. If the entity instance is contained in the
     * persistence context, it is returned from there.
     * <p/>
     * If the entity is found within the persistence context and the lock mode type is pessimistic and the entity has a
     * version attribute, the persistence provider must perform optimistic version checks when obtaining the database
     * lock. If these checks fail, the <code>OptimisticLockException</code> will be thrown.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback
     * </ul>
     * <p/>
     * If a vendor-specific property or hint is not recognized, it is silently ignored.
     * <p/>
     * Portable applications should not rely on the standard timeout hint. Depending on the database in use and the
     * locking mechanisms used by the provider, the hint may or may not be observed.
     *
     * @param primaryKey primary key
     * @param lockMode   lock mode
     * @param properties standard and vendor-specific properties and hints
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is
     *                                  not a valid type for that entity's primary key or is null
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction and a lock mode other than <code>NONE</code> is
     *                                  specified
     * @throws javax.persistence.OptimisticLockException
     *                                  if the optimistic version check fails
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     * @since Java Persistence 2.0
     */
    T find(Long primaryKey, LockModeType lockMode, Map<String, Object> properties);

    /**
     * Find by primary key and lock. Search for an entity of the specified class and primary key and lock it with
     * respect to the specified lock type. If the entity instance is contained in the persistence context, it is
     * returned from there, and the effect of this method is the same as if the lock method had been called on the
     * entity.
     * <p/>
     * If the entity is found within the persistence context and the lock mode type is pessimistic and the entity has a
     * version attribute, the persistence provider must perform optimistic version checks when obtaining the database
     * lock. If these checks fail, the <code>OptimisticLockException</code> will be thrown.
     * <p/>
     * If the lock mode type is pessimistic and the entity instance is found but cannot be locked:
     * <ul>
     * <li>the <code>PessimisticLockException</code> will be thrown if the database locking failure causes
     * transaction-level rollback
     * <li>the <code>LockTimeoutException</code> will be thrown if the database locking failure causes only
     * statement-level rollback
     * </ul>
     *
     * @param primaryKey primary key
     * @param lockMode   lock mode
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is
     *                                  not a valid type for that entity's primary key or is null
     * @throws javax.persistence.TransactionRequiredException
     *                                  if there is no transaction and a lock mode other than NONE is specified
     * @throws javax.persistence.OptimisticLockException
     *                                  if the optimistic version check fails
     * @throws javax.persistence.PessimisticLockException
     *                                  if pessimistic locking fails and the transaction is rolled back
     * @throws javax.persistence.LockTimeoutException
     *                                  if pessimistic locking fails and only the statement is rolled back
     * @throws javax.persistence.PersistenceException
     *                                  if an unsupported lock call is made
     * @since Java Persistence 2.0
     */
    T find(Long primaryKey, LockModeType lockMode);

    /**
     * Find by primary key, using the specified properties. Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context, it is returned from there. If a vendor-specific
     * property or hint is not recognized, it is silently ignored.
     *
     * @param primaryKey primary key
     * @param properties standard and vendor-specific properties and hints
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is
     *                                  is not a valid type for that entitys primary key or is null
     * @since Java Persistence 2.0
     */
    T find(Long primaryKey, Map<String, Object> properties) throws DAOException;

    /**
     * Find by primary key. Search for an entity of the specified class and primary key. If the entity instance is
     * contained in the persistence context, it is returned from there.
     *
     * @param primaryKey primary key
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is
     *                                  is not a valid type for that entitys primary key or is null
     */
    T find(Long primaryKey);

    /**
     * Remove the entity instance.
     *
     * @param entity entity instance
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    void remove(T entity);

    /**
     * Merge the state of the given entity into the current persistence context.
     *
     * @param entity entity instance
     * @return the managed instance that the state was merged to
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    T merge(T entity);

    /**
     * Merge the state of the given entity into the current persistence context.
     *
     * @param entity      entity instance
     * @param lastUpdated entity last updated timestamp pass from FE
     * @return the managed instance that the state was merged to
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    T merge(T entity, Timestamp lastUpdated);

    /**
     * Make an instance managed and persistent.
     *
     * @param entity entity instance
     * @throws javax.persistence.EntityExistsException
     *                                  if the entity already exists. (If the entity already exists, the
     *                                  <code>EntityExistsException</code> may be thrown when the persist operation is invoked, or the
     *                                  <code>EntityExistsException</code> or another <code>PersistenceException</code> may be thrown at
     *                                  flush or commit time.)
     * @throws IllegalArgumentException if the instance is not an entity
     * @throws javax.persistence.TransactionRequiredException
     *                                  if invoked on a container-managed entity manager of type
     *                                  <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    void persist(T entity);

    /**
     * Get the persistent class of this dao.
     *
     * @return
     */
    Class<T> getPersistentClass();

    /**
     * This setter for entity manager
     *
     * @param entityManager
     */
    void setEntityManager(EntityManager entityManager);

    /**
     * Return a list of all objects or an empty list by the given query conditions
     *
     * @param query  a Java Persistence query string
     * @param offset search result from the number
     * @param count  the count of the result returned
     * @return the found entity instance list or an empty list if the entity does not exist
     * @paReturn a list of all objects or an empty list by the given query conditionsram args an object array of query
     * parameters
     */
    List<T> queryByList(String query, int offset, int count, Object... args);

    /**
     * Return a list of all objects or an empty list by the given query conditions
     *
     * @param query  a Java Persistence query string
     * @param offset search result from the number
     * @param count  the count of the result returned
     * @param args   an object array of query parameters
     * @return the found entity instance list or an empty list if the entity does not exist
     */
    List<T> getByList(String query, int offset, int count, Object... args);

    /**
     * Return one object of the given query condition
     *
     * @param query a Java Persistence query string
     * @param args  an object array of query parameters
     * @return the found object instance or null if the entity does not exist
     */
    Object queryByOne(String query, Object... args);

    /**
     * Return one entity of the given query condition
     *
     * @param query a Java Persistence query string
     * @param args  an object array of query parameters
     * @return the found entity instance or null if the entity does not exist
     */
    T getByOne(String query, Object... args);

    /**
     * Return the name of the entity.
     *
     * @return the entity name
     */
    String getEntityName();

    /**
     * Return an entity of the given filed name and value
     *
     * @param name  the name of the field
     * @param value the value of the field
     * @return the found entity instance or null if the entity does not exist
     */
    T getByField(String name, Object value);

    /**
     * Return a list a entity of the given query and parameters
     *
     * @param query a Java Persistence query string
     * @param args  an object array of query parameters
     * @return the found entity instance list or an empty list if the entity does not exist
     */
    List<T> getByList(String query, Object... args);

    /**
     * Return a list a entity of the given query and parameters
     *
     * @param query a Java Persistence query string
     * @param args  an object array of query parameters
     * @return the found entity instance list or an empty list if the entity does not exist
     */
    List<?> queryByList(String query, Object... args);

    /**
     * Return a list of all objects or an empty list
     *
     * @return the found entity instance list or an empty list if the entity does not exist
     */
    List<T> getList();

    /**
     * Return a list of all objects or an empty list by the given jpq query
     *
     * @param jpa        a Java Persistence query string
     * @param pageSize   Number of entity per page
     * @param pageNumber Page to display
     * @return
     * @throws DAOException
     */
    List<T> queryByCriteria(String jpa, int pageSize, int pageNumber) throws DAOException;

    /**
     * Return a list of all objects or an empty list by the given native sql query
     *
     * @param sql         a native SQL query string
     * @param entityClass Entity class used to save returned result
     * @param pageSize    Number of entity per page
     * @param pageNumber  Page to display
     * @return
     * @throws DAOException
     */
    List<T> queryByNativeCriteria(String sql, Class<T> entityClass, int pageSize, int pageNumber) throws DAOException;

    /**
     * Return a list of all objects or an empty list by the given native sql query
     *
     * @param query      javax.persistence.Query
     * @param pageSize   Number of entity per page
     * @param pageNumber Page to display
     * @return
     * @throws DAOException
     */
    List<T> queryByCriteria(Query query, int pageSize, int pageNumber) throws DAOException;

    /**
     * Create an instance of <code>Query</code> for executing a native SQL statement, e.g., for update or delete.
     *
     * @param query a Java Persistence query string
     * @param args  an object array of query parameters
     */
    void executeNativeQuery(String query, Object... args);

    /**
     * Get last record for this entity
     *
     * @return last record of specified class.
     */
    T getLastRecord(Class<T> clazz);

}
