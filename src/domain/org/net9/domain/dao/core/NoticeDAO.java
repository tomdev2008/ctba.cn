package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.Notice;

/**
 * notice dao
 * @author gladstone
 * @since Feb 7, 2009
 */
@EntityClass(value = Notice.class, useCache = false)
public class NoticeDAO extends CachedJpaDAO {
}
