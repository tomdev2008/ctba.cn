package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.Friend;

@EntityClass(value = Friend.class)
public class FriendsDAO extends CachedJpaDAO {

}
