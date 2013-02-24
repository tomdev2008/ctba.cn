package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.Online;

/**
 * bbs online dao
 * 
 * @author Administrator
 * 
 */
@EntityClass(value = Online.class, useCache = false)
public class OnlineDAO extends CachedJpaDAO {
}
