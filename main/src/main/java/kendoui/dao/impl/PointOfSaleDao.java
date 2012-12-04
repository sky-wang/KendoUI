package kendoui.dao.impl;

import kendoui.dao.BaseContentDao;
import kendoui.dao.IPointOfSaleDao;
import kendoui.model.PointOfSale;

import org.springframework.stereotype.Repository;

/**
 * Category DAO layer access.
 */
@Repository
public class PointOfSaleDao extends BaseContentDao<PointOfSale> implements IPointOfSaleDao {
}
