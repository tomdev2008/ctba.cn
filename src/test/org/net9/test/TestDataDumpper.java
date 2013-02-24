package org.net9.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.dao.BaseJpaDAO;
import org.net9.domain.dao.JpaDataBaseConfig;
import org.net9.tools.db.DBException;
import org.net9.tools.db.DataManager;

/**
 * 测试数据库转存到xml文件，保存为scripts/ddl/init_data.xml
 * 
 * @author gladstone
 * @since Dec 14, 2008
 */
public class TestDataDumpper {

	static Log log = LogFactory.getLog(TestDataDumpper.class);

	public static void main(String[] args) {
//		EntityManagerHelper.setProperties(TestDataBaseConfig.testProperties);
		BaseJpaDAO.isConnectingToTestDb =true;
//		JpaConfigHandler.setPeoperties(TestDataBaseConfig.testProperties);
		try {
			DataManager dataManager = new DataManager(
					"scripts/ddl/init_data.xml");
			dataManager.getDbManager().setConnProperties(
					JpaDataBaseConfig.jdbcDriver, JpaDataBaseConfig.jdbcUrl,
					JpaDataBaseConfig.jdbcUsername,
					JpaDataBaseConfig.jdbcPassword);
			dataManager.backup();
		} catch (DBException e) {
			log.error(e.getMessage());
		}
	}

}
