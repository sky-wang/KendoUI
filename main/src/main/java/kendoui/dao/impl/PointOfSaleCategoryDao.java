package kendoui.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import kendoui.dao.BaseContentDao;
import kendoui.dao.ICategoryDao;
import kendoui.dao.IPointOfSaleCategoryDao;
import kendoui.dao.IPointOfSaleDao;
import kendoui.exception.DAOException;
import kendoui.model.Category;
import kendoui.model.PointOfSale;
import kendoui.model.PointOfSaleCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class for point of sale category DAO access.
 */
@Repository
public class PointOfSaleCategoryDao extends BaseContentDao<PointOfSaleCategory> implements IPointOfSaleCategoryDao {

    private static String GET_POINT_OF_SALE_CATEGORY_BY_POINT_OF_SALE_ID = "Select distinct(posc) from PointOfSaleCategory posc where posc.pointOfSale.id = :pointOfSaleId";

    private static String GET_POINT_OF_SALE_CATEGORY_BY_POINT_OF_SALE_ID_AND_CATEGORY_ID = "Select distinct(posc) from PointOfSaleCategory posc where posc.pointOfSale.id = :pointOfSaleId and posc.category.id = :categoryId";

    private static String GET_CATEGORY_TREE = "Select p.category from PointOfSaleCategory p where p.pointOfSale.id = :pointofsaleId";

    private static String GET_POINT_OF_SALE_CATEGORY_BY_CATEGORY_ID = "FROM PointOfSaleCategory x WHERE x.category.id = :category";

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private IPointOfSaleDao pointOfSaleDao;


    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#getPointOfSaleCategorybyPointOfSaleId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PointOfSaleCategory> getPointOfSaleCategorybyPointOfSaleId(Long pointOfSaleId) throws DAOException {
        Query query = createQuery(GET_POINT_OF_SALE_CATEGORY_BY_POINT_OF_SALE_ID);
        query.setParameter("pointOfSaleId", pointOfSaleId);
        return (List<PointOfSaleCategory>) query.getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#getCategoryTree(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> getCategoryTree(Long pointofsaleId) throws DAOException {
        Query query = createQuery(GET_CATEGORY_TREE);
        query.setParameter("pointofsaleId", pointofsaleId);
        return (List<Category>) query.getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#getPointOfSaleCategoriesByCategoryId(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PointOfSaleCategory> getPointOfSaleCategoriesByCategoryId(Long categoryId) throws DAOException {
        Query query = createQuery(GET_POINT_OF_SALE_CATEGORY_BY_CATEGORY_ID);
        query.setParameter("category", categoryId);
        return (List<PointOfSaleCategory>) query.getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#deleteCategoryByPointOfSale(java.lang.Long, java.lang.Long)
     */
    @Override
    public void deleteCategoryByPointOfSale(Long categoryId, Long pointofsaleId) throws DAOException {
        PointOfSaleCategory pointOfSaleCategory = getPointOfSaleCategoriesByCategoryIdAndPosId(categoryId,
                pointofsaleId);
        if (pointOfSaleCategory != null) {
            remove(pointOfSaleCategory);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#getPointOfSaleCategoriesByCategoryIdAndPosId(Long, Long)
     */
    @Override
    public PointOfSaleCategory getPointOfSaleCategoriesByCategoryIdAndPosId(Long categoryId, Long posId) {
        Query query = createQuery(GET_POINT_OF_SALE_CATEGORY_BY_POINT_OF_SALE_ID_AND_CATEGORY_ID);
        query.setParameter("pointOfSaleId", posId);
        query.setParameter("categoryId", categoryId);
        try {
            return (PointOfSaleCategory) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#setCategoryTree(Long, Long)
     */
    @Override
    public Boolean setCategoryTree(Long categoriyId, Long pointOfSaleId) {
        Category category = categoryDao.find(categoriyId);
        PointOfSale pointOfSale = pointOfSaleDao.find(pointOfSaleId);
        PointOfSaleCategory pointOfSaleCategory = new PointOfSaleCategory();
        pointOfSaleCategory.setCategory(category);
        pointOfSaleCategory.setPointOfSale(pointOfSaleDao.find(pointOfSaleId));
        persist(pointOfSaleCategory);

        if (category.getInUse()) {
            pointOfSale.setInUse(true);
            pointOfSaleDao.merge(pointOfSale);
        }
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#updatePosInUseStateTrue(Long)
     */
    @Override
    public void updatePosInUseStateTrue(Long categoryId) throws DAOException {
        List<PointOfSaleCategory> pointOfSaleCategoryList = getPointOfSaleCategoriesByCategoryId(categoryId);
        for (PointOfSaleCategory pointOfSaleCategory : pointOfSaleCategoryList) {
            PointOfSale pointOfSale = pointOfSaleCategory.getPointOfSale();
            if (!pointOfSale.getInUse()) {
                pointOfSale.setInUse(true);
                pointOfSaleDao.merge(pointOfSale);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see kendoui.dao.IPointOfSaleCategoryDao#updatePosInUseStateFalse(Long)
     */
    @Override
    public void updatePosInUseStateFalse(Long categoryId) throws DAOException {
        List<PointOfSaleCategory> pointOfSaleCategoryList = getPointOfSaleCategoriesByCategoryId(categoryId);
        for (PointOfSaleCategory pointOfSaleCategory : pointOfSaleCategoryList) {
            PointOfSale pointOfSale = pointOfSaleCategory.getPointOfSale();
            List<Category> categories = getCategoryTree(pointOfSale.getId());
            boolean inUse = false;
            for (Category category : categories) {
                if (category.getInUse()) {
                    inUse = true;
                    break;
                }
            }

            // Only update inUse if all the categories associated with this pos if it is false
            if (!inUse && pointOfSale.getInUse()) {
                pointOfSale.setInUse(false);
                pointOfSaleDao.merge(pointOfSale);
            }
        }
    }
}
