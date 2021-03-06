package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.SysFeedback;

/**
 * system email dao
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = SysFeedback.class, useCache = false)
public class SysFeedbackDAO extends CachedJpaDAO {

}
