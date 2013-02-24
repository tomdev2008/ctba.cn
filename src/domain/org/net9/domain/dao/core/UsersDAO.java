package org.net9.domain.dao.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.User;

@EntityClass(value = User.class)
public class UsersDAO extends CachedJpaDAO {

	static Log log = LogFactory.getLog(UsersDAO.class);

	public User getUser(String userName) {
		return (User) findSingleByStatement("select model from User model where model.userName='"
				+ userName + "'");
	}

}
