package kendoui.dao.impl;

import kendoui.dao.BaseContentDao;
import kendoui.dao.IDefaultTextDao;
import kendoui.model.DefaultText;

import org.springframework.stereotype.Repository;

/**
 * DefaultExt DAO layer access.
 */
@Repository
public class DefaultTextDao extends BaseContentDao<DefaultText> implements IDefaultTextDao {
}
