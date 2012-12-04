package kendoui.service;

import kendoui.exception.MTXException;
import kendoui.model.Category;
import kendoui.model.PointOfSale;

import java.util.List;

/**
 * Service interface for point of sale business logic process.
 *
 */
public interface IPointOfSaleService extends IEntityService<PointOfSale> {

    /**
     * Return list of Category by POS id.
     *
     * @param posId
     * @return a list of Category
     * @throws MTXException
     */
    List<Category> getCategoryByPosId(Long posId) throws MTXException;

}