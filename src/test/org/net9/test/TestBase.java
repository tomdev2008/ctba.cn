package org.net9.test;

import org.net9.domain.dao.BaseJpaDAO;

/**
 * 测试用例务必继承这个类
 * 
 * @author gladstone
 * 
 */
public abstract class TestBase {

	// 只執行一次
	// 注意，这里的执行顺序非常重要，不要随便修改
	static {
		// EntityManagerHelper.setProperties(TestDataBaseConfig.testProperties);
		BaseJpaDAO.isConnectingToTestDb = true;
		// JpaConfigHandler.setPeoperties(TestDataBaseConfig.testProperties);
	}
}
