package org.net9.domain.dao.view;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.view.ViewHotUser;

@EntityClass(value = ViewHotUser.class)
public class ViewHotUserDAO extends CachedJpaDAO {

}