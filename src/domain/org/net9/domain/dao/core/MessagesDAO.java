package org.net9.domain.dao.core;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;

import com.google.inject.spi.Message;

@EntityClass(value = Message.class, useCache = false)
public class MessagesDAO extends CachedJpaDAO {

}
