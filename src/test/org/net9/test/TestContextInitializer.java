package org.net9.test;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.dao.BaseJpaDAO;
import org.net9.domain.dao.JpaDataBaseConfig;
import org.net9.tools.db.DBException;
import org.net9.tools.db.DataManager;

/**
 * 初始化测试数据库
 * 
 * @author gladstone
 * @since Dec 14, 2008
 */
public class TestContextInitializer {

	static Log log = LogFactory.getLog(TestContextInitializer.class);

	public static void main(String[] args) {
		TestContextInitializer testContextInitializer = new TestContextInitializer();
		try {
			testContextInitializer.dropTables();
			testContextInitializer.createTables();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (DBException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		try {
			DataManager dataManager = new DataManager(
					"scripts/ddl/init_data.xml");
			dataManager.getDbManager().setConnProperties(
					JpaDataBaseConfig.jdbcDriver, JpaDataBaseConfig.jdbcUrl,
					JpaDataBaseConfig.jdbcUsername,
					JpaDataBaseConfig.jdbcPassword);
			dataManager.restore();
		} catch (DBException e) {
			log.error(e.getMessage());
		}
	}

	public TestContextInitializer() { // 注意，这里的执行顺序非常重要，不要随便修改
//		EntityManagerHelper.setProperties(TestDataBaseConfig.testProperties);
		BaseJpaDAO.isConnectingToTestDb =true;
//		JpaConfigHandler.setPeoperties(TestDataBaseConfig.testProperties);
	}

	private void createTables() throws IOException, DBException {
		MockupUtils.createTables();
		MockupUtils.createViews();
	}

	private void dropTables() throws IOException, DBException {
		MockupUtils.dropTables();
	}

}
