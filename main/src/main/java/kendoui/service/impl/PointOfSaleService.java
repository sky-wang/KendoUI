package kendoui.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kendoui.dao.ICategoryDao;
import kendoui.exception.BusinessException;
import kendoui.exception.DAOException;
import kendoui.model.Category;
import kendoui.model.PointOfSale;
import kendoui.service.BaseEntityService;
import kendoui.service.IPointOfSaleService;
import kendoui.utils.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for access PointOfSale related info.
 */
@Service
public class PointOfSaleService extends BaseEntityService<PointOfSale> implements IPointOfSaleService {
    private final Logger logger = LoggerFactory.getLogger(PointOfSaleService.class);

    @Autowired
    private ICategoryDao categoryDao;

    /**
     * Return list of Category by POS id.
     *
     * @param posId
     * @return a list of Category
     * @throws BusinessException
     */
    public List<Category> getCategoryByPosId(Long posId) throws BusinessException {
        List<Category> categoryList = new ArrayList<Category>();
        Set<Category> categorySet = null;
        try {
            categorySet = categoryDao.getCategorySetByPOS(posId);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), e);
        }
        if (categorySet != null) {
            categoryList.addAll(categorySet);
        } else {
            throw new BusinessException(Constant.ERROR_CAUSE_CATEGORY_NOT_EXIST);
        }
        return categoryList;
    }
}
