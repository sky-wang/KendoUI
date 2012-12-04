package kendoui.dao;

import org.junit.runner.RunWith;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
/**
 *Base class of Spring application context for DAO layer.
 *This class will roll back transaction automatically for DAO unit test.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application_context.xml" })
@TransactionConfiguration(defaultRollback = true)
public abstract class BaseApplicationDaoTest {

    public static final String CONTENT_TXM = "contentTransactionManager";
}
