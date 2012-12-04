package kendoui.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Base content database DAO.
 */
public class BaseContentDao<T> extends BaseDao<T> implements IDao<T> {

    @PersistenceContext(unitName = "content")
    protected EntityManager entityManager;

    @PostConstruct
    public void init() {
        setEntityManager(entityManager);
    }
}
