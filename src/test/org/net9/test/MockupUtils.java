package org.net9.test;

import java.io.IOException;

import org.net9.common.util.StringUtils;
import org.net9.domain.dao.JpaDataBaseConfig;
import org.net9.tools.db.DBException;
import org.net9.tools.db.JdbcScriptRunner;

public class MockupUtils {

	static JdbcScriptRunner scriptRunner = new JdbcScriptRunner();

	static {
		scriptRunner.getDbManager().setConnProperties(
				JpaDataBaseConfig.jdbcDriver, JpaDataBaseConfig.jdbcUrl,
				JpaDataBaseConfig.jdbcUsername, JpaDataBaseConfig.jdbcPassword);
	}

	public static String dbType = StringUtils.isEmpty(System
			.getProperty("CTBA_DB_TYPE")) ? "mysql" : System
			.getProperty("CTBA_DB_TYPE");

	public static void clearTable(String tableName) throws IOException,
			DBException {
		scriptRunner.runSQLStatement("DELETE FROM " + tableName);
	}

	public static void createTables() throws IOException, DBException {
		scriptRunner.runScript("scripts/ddl/" + dbType + "/create_tables.sql");
	}

	public static void createViews() throws IOException, DBException {
		scriptRunner.runScript("scripts/ddl/" + dbType + "/create_views.sql");
	}

	public static void dropTables() throws IOException, DBException {
		scriptRunner.runScript("scripts/ddl/" + dbType + "/drop_tables.sql");
	}

}
