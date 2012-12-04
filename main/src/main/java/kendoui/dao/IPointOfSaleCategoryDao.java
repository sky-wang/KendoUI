package kendoui.dao;

import java.util.List;

import kendoui.model.Category;
import kendoui.model.PointOfSaleCategory;
import kendoui.dao.IDao;
import kendoui.exception.DAOException;

public interface IPointOfSaleCategoryDao extends IDao<PointOfSaleCategory> {
    /**
     * get PointOfSaleCategory by PointOfSaleId
     *
     * @param pointOfSaleId
     * @return
     * @throws DAOException
     */
    List<PointOfSaleCategory> getPointOfSaleCategorybyPointOfSaleId(Long pointOfSaleId) throws DAOException;

    /**
     * get PointOfSaleCategories By CategoryId
     *
     * @param categoryId
     * @return
     * @throws DAOException
     */
    List<PointOfSaleCategory> getPointOfSaleCategoriesByCategoryId(Long categoryId) throws DAOException;

    /**
     * delete CategoryId By PointOfSaleId
     *
     * @param categoryId
     * @param pointofsaleId
     * @throws DAOException
     */
    void deleteCategoryByPointOfSale(Long categoryId, Long pointofsaleId) throws DAOException;

    /**
     * get PointOfSaleCategory By CategoryId and pointofsaleId
     *
     * @param categoryId
     * @param pointofsaleId
     * @return
     */
    PointOfSaleCategory getPointOfSaleCategoriesByCategoryIdAndPosId(Long categoryId, Long pointofsaleId);

    /**
     * Update inUse of pointofsale to true if child categories is associated with Sku
     *
     * @param categoryId
     * @throws DAOException
     */
    void updatePosInUseStateTrue(Long categoryId) throws DAOException;

    /**
     * Create a new PointOfSaleCategory by categoryId and pointOfSaleId
     *
     * @param categoriyId
     * @param pointOfSaleId
     * @return
     */
    Boolean setCategoryTree(Long categoriyId, Long pointOfSaleId);

    /**
     * Update inUse state of PointOfSale after de-associate a category from Sku
     *
     * @param categoryId
     * @throws DAOException
     */
    void updatePosInUseStateFalse(Long categoryId) throws DAOException;

    /**
     * get CategoryTree
     *
     * @param pointofsaleId
     * @return
     * @throws DAOException
     */
    List<Category> getCategoryTree(Long pointofsaleId) throws DAOException;
}
