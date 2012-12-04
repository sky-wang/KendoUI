package kendoui.service;

import kendoui.dao.IDao;
import kendoui.exception.BusinessException;

import javax.persistence.Query;
import java.util.List;

public interface IEntityService<T> {

    /**
     * Return an entity by the given id.
     *
     * @param id id of the entity
     * @return
     * @throws BusinessException
     */
    T getItemById(Long id) throws BusinessException;

    /**
     * Return DAO instance.
     *
     * @return a dao instance
     */
    IDao<T> getDao();

    /**
     * Check if the instance is a managed entity instance belonging to the current persistence context.
     *
     * @param entity entity instance
     * @return
     * @throws BusinessException
     */
    boolean contains(T entity) throws BusinessException;

    /**
     * Refresh the state of the instance from the database, overwriting changes made to the entity, if any.
     *
     * @param entity entity instance
     * @throws BusinessException
     */
    void refresh(T entity) throws BusinessException;

    /**
     * Make an instance managed and persistent.
     *
     * @param entity entity instance
     * @return
     * @throws BusinessException
     */
    T create(T entity) throws BusinessException;

    /**
     * Merge the state of the given entity into the current persistence context.
     *
     * @param entity entity instance
     * @return
     * @throws BusinessException
     */
    T update(T entity) throws BusinessException;

    /**
     * Make an instance managed and persistent.
     *
     * @param entity
     * @return
     * @throws BusinessException
     */
    T save(T entity) throws BusinessException;

    /**
     * Set dao instance
     *
     * @param dao dao instance
     */
    void setDao(IDao<T> dao);

    /**
     * Create an instance of <code>Query</code> for executing a native SQL statement, e.g., for update or delete.
     *
     * @param sqlString a native SQL query string
     * @return
     */
    Query createNativeQuery(String sqlString);

    /**
     * Create an instance of <code>TypedQuery</code> for executing a criteria query.
     *
     * @param sqlString a Java Persistence query string
     * @return
     * @throws BusinessException
     */
    Query createQuery(String sqlString) throws BusinessException;

    /**
     * Return a list of all objects or an empty list
     *
     * @return the found entity instance list or an empty list if the entity does not exist
     * @throws BusinessException
     */
    List<T> getList() throws BusinessException;

    /**
     * Return a list of all objects or an empty list by the given query conditions
     *
     * @param query a Java Persistence query string
     * @param offset search result from the number
     * @param count the count of the result returned
     * @param args an object array of query parameters
     * @return the found entity instance list or an empty list if the entity does not exist
     * @throws BusinessException
     */
    List<T> getByList(String query, int offset, int count, Object... args) throws BusinessException;



    /**
     * Get last record for the entity.
     *
     * @return last record
     */
    T getLastRecord(Class<T> clazz);
}
