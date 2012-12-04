package kendoui.dao.impl;

import java.util.HashSet;
import java.util.Set;

import kendoui.dao.BaseContentDao;
import kendoui.dao.ICategoryDao;
import kendoui.exception.DAOException;
import kendoui.model.Category;

import org.springframework.stereotype.Repository;

/**
 * Category DAO layer access.
 */
@Repository
public class CategoryDao extends BaseContentDao<Category> implements ICategoryDao {
    private static final String GET_CATEGORY_BY_POSID = "select posc.category from PointOfSaleCategory posc where posc.pointOfSale.id = ? order by posc.pointOfSaleOrder";


    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.ICategoryDao#getCategorySetByPOS
     */
    public Set<Category> getCategorySetByPOS(Long posId) throws DAOException {
        return new HashSet<Category>(getByList(GET_CATEGORY_BY_POSID, posId));
    }
}
