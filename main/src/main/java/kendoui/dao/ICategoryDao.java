package kendoui.dao;


import kendoui.exception.DAOException;
import kendoui.model.Category;

import java.util.Set;

/**
 * Interface for category model database access.
 */
public interface ICategoryDao extends IDao<Category> {
    /**
     * Get a set of category by pointOfSale id.
     *
     * @param posId a PointOfSale id
     * @return a set of category or null if the category does not exist
     * @throws DAOException
     */
    Set<Category> getCategorySetByPOS(Long posId) throws DAOException;
}
