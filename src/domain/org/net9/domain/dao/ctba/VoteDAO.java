package org.net9.domain.dao.ctba;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.Vote;

@EntityClass(value = Vote.class)
public class VoteDAO extends CachedJpaDAO {

}
