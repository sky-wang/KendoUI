package kendoui.service;

import java.util.List;

import javax.persistence.*;

import kendoui.dao.IDao;
import kendoui.exception.BusinessException;

/**
 * The base service for all the service classes.
 */
public abstract class BaseEntityService<T> implements IEntityService<T> {

    public final static String CONTENT_TXM = "contentTransactionManager";


    private IDao<T> dao;

    @Override
    public void setDao(IDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public IDao<T> getDao() {
        return dao;
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#getItemById(Long)
     */
    @Override
    public T getItemById(Long id) throws BusinessException {
        try {
            return getDao().find(id);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#contains(Object)
     */
    @Override
    public boolean contains(T entity) throws BusinessException {
        try {
            return getDao().contains(entity);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#refresh(Object)
     */
    @Override
    public void refresh(T entity) throws BusinessException {
        try {
            getDao().refresh(entity);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (TransactionRequiredException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#update(Object)
     */
    @Override
    public T update(T entity) throws BusinessException {
        try {
            return getDao().merge(entity);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (TransactionRequiredException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Another user has modified " + entity.getClass().getSimpleName()
                    + " and the system could not save your changes.", e);
        } catch (EntityNotFoundException e) {
            throw new BusinessException("Concurrency Lock!");
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#create(Object)
     */
    @Override
    public T create(T entity) throws BusinessException {
        try {
            getDao().persist(entity);
            return entity;
        } catch (EntityExistsException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        } catch (TransactionRequiredException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#save(Object)
     */
    @Override
    public T save(T entity) throws BusinessException {
        if (contains(entity)) {
            return update(entity);
        } else {
            return create(entity);
        }
    }


    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#createQuery(String)
     */
    @Override
    public Query createQuery(String sqlString) throws BusinessException {
        try {
            return getDao().createQuery(sqlString);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#createNativeQuery(String)
     */
    @Override
    public Query createNativeQuery(String sqlString) {
        return getDao().createNativeQuery(sqlString);
    }

    /**
     * (non-Javadoc)
     *
     * @see IEntityService#getList()
     */
    @Override
    public List<T> getList() throws BusinessException {
        return getDao().getList();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#getListByCriteria(String, String, String, int, int) L
     */
    @Override
    public List<T> getByList(String query, int offset, int count, Object... args) throws BusinessException {
        return getDao().getByList(query, offset, count, args);
    }


    /**
     * (non-Javadoc)
     *
     * @see kendoui.service.IEntityService#getLastRecord(Class)
     */
    @Override
    public T getLastRecord(Class<T> clazz) {
        return getDao().getLastRecord(clazz);
    }

}
