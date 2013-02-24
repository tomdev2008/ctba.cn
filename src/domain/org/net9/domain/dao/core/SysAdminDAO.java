package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.SysAdmin;

/**
 * system admin
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = SysAdmin.class)
public class SysAdminDAO extends CachedJpaDAO {
}
