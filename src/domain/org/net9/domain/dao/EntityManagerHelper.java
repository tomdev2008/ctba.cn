package org.net9.domain.dao;

import java.util.Properties;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * EntityManagerHelper, to get the persistence EntityManager
 * 
 * @author gladstone
 */
public class EntityManagerHelper {

	private static EntityManagerFactory emf;

	private static ThreadLocal<EntityManager> threadLocal;

	private static final Log logger = LogFactory
			.getLog(EntityManagerHelper.class);

	public static final String PERSISTANCE_UNIT_PRODUCT = "ctba-persistence-product";

	public static final String PERSISTANCE_UNIT_TEST = "ctba-persistence-test";

	static {
		initContext();
	}

	/**
	 * begin transaction
	 */
	public static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public static void closeEntityManager() {
		EntityManager em = threadLocal.get();
		threadLocal.set(null);
		if (em != null)
			em.close();
	}

	/**
	 * commit transaction
	 */
	public static void commit() {
		getEntityManager().getTransaction().commit();
	}

	/**
	 * create jpql query
	 */
	public static Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}

	public static EntityManager getEntityManager() {
		EntityManager manager = threadLocal.get();
		if (manager == null || !manager.isOpen()) {
			manager = emf.createEntityManager();
			threadLocal.set(manager);
		}
		return manager;
	}

	/**
	 * load database persistence context
	 */
	private static void initContext() {
		if (emf == null) {
			if (BaseJpaDAO.isConnectingToTestDb) {
				logger.info("connecting to the test database");
				emf = Persistence
						.createEntityManagerFactory(PERSISTANCE_UNIT_TEST);
			} else {
				logger.info("connecting to the product database");
				emf = Persistence
						.createEntityManagerFactory(PERSISTANCE_UNIT_PRODUCT);
			}
		}
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<EntityManager>();
		}
	}

	/**
	 * database log
	 * 
	 * @param info
	 * @param level
	 * @param ex
	 */
	public static void log(String info, Level level, Throwable ex) {
		logger.info(info, ex);
	}

	/**
	 * 可以使用别的数据库链接
	 * 
	 * 但是只有在se(batch)环境下才能用
	 * 
	 * @param p
	 */
	public static void setProperties(Properties p) {
		logger.info("database url changed to: "
				+ p.getProperty("toplink.jdbc.url"));
		emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_PRODUCT,
				p);
		threadLocal = new ThreadLocal<EntityManager>();
	}
}
