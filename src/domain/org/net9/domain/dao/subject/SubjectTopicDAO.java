package org.net9.domain.dao.subject;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.subject.SubjectTopic;

/**
 * Subject Topic DAO
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = SubjectTopic.class)
public class SubjectTopicDAO extends CachedJpaDAO {

}
