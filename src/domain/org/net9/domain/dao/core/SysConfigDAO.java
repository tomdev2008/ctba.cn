package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.SysConfig;

/**
 * @author gladstone
 * 
 */
@EntityClass(value = SysConfig.class)
public class SysConfigDAO extends CachedJpaDAO {

}
