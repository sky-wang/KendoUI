package kendoui.dao;

import java.util.List;

import kendoui.dao.impl.PointOfSaleCategoryDao;
import kendoui.dao.impl.PointOfSaleDao;
import kendoui.exception.DAOException;
import kendoui.model.Category;
import kendoui.model.DefaultText;
import kendoui.model.PointOfSale;
import kendoui.model.PointOfSaleCategory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(BaseApplicationDaoTest.CONTENT_TXM)
public class PointOfSaleCategoryDaoTest extends BaseApplicationDaoTest {

    private Category category;

    private PointOfSale pointOfSale;

    private PointOfSaleCategory pointOfSaleCategory;

    @Autowired
    private PointOfSaleCategoryDao pointOfSaleCategoryDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private IDefaultTextDao defaultTextDao;

    @Autowired
    private PointOfSaleDao pointOfSaleDao;

    @Before
    public void setUp() {
        DefaultText name = new DefaultText();
        name.setText("category name");
        defaultTextDao.persist(name);
        DefaultText desc = new DefaultText();
        desc.setText("category desc");
        defaultTextDao.persist(desc);
        category = new Category();
        category.setName(name);
        category.setDescription(desc);
        categoryDao.persist(category);
        pointOfSale = new PointOfSale();
        pointOfSale.setName("pointofsale");
        pointOfSaleDao.persist(pointOfSale);
        pointOfSaleCategory = new PointOfSaleCategory();
        pointOfSaleCategory.setPointOfSale(pointOfSale);
        pointOfSaleCategory.setCategory(category);
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
    }

    @Test
    public void testgetPointOfSaleCategorybyPointOfSaleId() throws DAOException {
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
        List<PointOfSaleCategory> pointOfSaleCategoryList = pointOfSaleCategoryDao
                .getPointOfSaleCategorybyPointOfSaleId(pointOfSale.getId());
        Assert.assertEquals(1, pointOfSaleCategoryList.size());
        Assert.assertEquals(category.getName(), pointOfSaleCategoryList.get(0).getCategory().getName());
    }

    @Test
    public void testgetCategoryTree() throws DAOException {
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
        List<Category> categoryList = pointOfSaleCategoryDao.getCategoryTree(pointOfSale.getId());
        Assert.assertEquals(1, categoryList.size());
        Assert.assertEquals(category.getName(), categoryList.get(0).getName());
    }

    @Test
    public void testgetPointOfSaleCategoriesByCategoryId() throws DAOException {
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
        List<PointOfSaleCategory> pointOfSaleCategoryList = pointOfSaleCategoryDao.getPointOfSaleCategoriesByCategoryId(category.getId());
        Assert.assertEquals(1, pointOfSaleCategoryList.size());
        Assert.assertEquals(category.getName(), pointOfSaleCategoryList.get(0).getCategory().getName());
    }

    @Test
    public void testdeleteCategoryByPointOfSale() throws DAOException {
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
        pointOfSaleCategoryDao.deleteCategoryByPointOfSale(category.getId(), pointOfSale.getId());
        Assert.assertTrue(true);
    }

    @Test
    public void testGetPointOfSaleCategoriesByCategoryIdAndPosId() {
        pointOfSaleCategoryDao.persist(pointOfSaleCategory);
        PointOfSaleCategory posCategory = pointOfSaleCategoryDao.getPointOfSaleCategoriesByCategoryIdAndPosId(category.getId(), pointOfSale.getId());
        Assert.assertNotNull(posCategory);
        Assert.assertEquals(posCategory.getPointOfSale().getName(), "pointofsale");
    }

    @Test
    public void testUpdatePosInUseStateTrue() throws DAOException {
        Assert.assertFalse(pointOfSale.getInUse());
        pointOfSaleCategoryDao.updatePosInUseStateTrue(category.getId());
        Assert.assertTrue(pointOfSale.getInUse());
    }

    @Test
    public void testUpdatePosInUseStateFalse() throws DAOException {
        Assert.assertFalse(category.getInUse());
        pointOfSaleCategoryDao.updatePosInUseStateFalse(category.getId());
        Assert.assertFalse(pointOfSale.getInUse());
    }

}
