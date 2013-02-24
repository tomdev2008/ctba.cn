package org.net9.domain.dao.bbs;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.bbs.Forbidden;

@EntityClass(value = Forbidden.class)
public class ForbiddensDAO extends CachedJpaDAO {
}
