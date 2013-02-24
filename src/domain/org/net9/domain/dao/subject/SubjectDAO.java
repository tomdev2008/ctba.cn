package org.net9.domain.dao.subject;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.subject.Subject;

/**
 * Subject DAO
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = Subject.class)
public class SubjectDAO extends CachedJpaDAO {

}
