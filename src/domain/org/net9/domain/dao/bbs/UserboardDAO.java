package org.net9.domain.dao.bbs;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.bbs.Userboard;

@EntityClass(value = Userboard.class)
public class UserboardDAO extends CachedJpaDAO {

}
