package org.net9.subject.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTemplate;
import org.net9.domain.model.subject.SubjectTopic;

import com.google.inject.servlet.SessionScoped;

/**
 * 
 * Subject service
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class SubjectService extends BaseService {

	private static final long serialVersionUID = 1L;
	static Log log = LogFactory.getLog(SubjectService.class);

	/**
	 * delete a Subject
	 * 
	 * @param model
	 */
	public void delSubject(Subject model) {
		subjectDAO.remove(model);
	}

	/**
	 * del SubjectTemplate
	 * 
	 * @param model
	 */
	public void delSubjectTemplate(SubjectTemplate model) {
		subjectTemplateDAO.remove(model);
	}

	/**
	 * del SubjectTopic
	 * 
	 * @param model
	 */
	public void delSubjectTopic(SubjectTopic model) {
		subjectTopicDAO.remove(model);
	}

	/**
	 * find by hits
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SubjectTopic> findHotSubjectTopics(Integer start, Integer limit) {
		return subjectTopicDAO
				.findByStatement(
						"select model from SubjectTopic model order by model.hits desc",
						start, limit);
	}

	/**
	 * get subjects
	 * 
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Subject> findSubjects(String title, String type, Integer start,
			Integer limit) {
		String jpql = "select model  from Subject model  where model.id>0 ";
		if (StringUtils.isNotEmpty(type)) {
			jpql += " and  model.type ='" + type + "' ";
		}
		if (StringUtils.isNotEmpty(title)) {
			jpql += " and  model.title like '%" + title + "%' ";
		}
		jpql += " order by model.id desc";
		return subjectDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find templates
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SubjectTemplate> findSubjectTemplates(Integer start,
			Integer limit) {
		String jpql = "select model from SubjectTemplate model  where model.id>0 order by model.id desc";
		return subjectTemplateDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find topics
	 * 
	 * @param subjectId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SubjectTopic> findSubjectTopics(Integer subjectId, String type,
			Integer start, Integer limit) {
		String jpql = "select model from SubjectTopic model  where model.id>0 ";
		if (subjectId != null) {
			jpql += " and  model.subjectId =" + subjectId;
		}
		if (StringUtils.isNotEmpty(type)) {
			jpql += " and  model.type ='" + type + "' ";
		}
		jpql += " order by model.id desc";
		return subjectTopicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get newest topic
	 * 
	 * @param sid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SubjectTopic getNewSubjectTopic(Integer sid) {
		List list = findSubjectTopics(sid, null, 0, 1);
		return list == null || list.size() < 1 ? null : (SubjectTopic) list
				.get(0);
	}

	public SubjectTopic getNextSubjectTopic(SubjectTopic currTopic) {
		String jpql = "select model from SubjectTopic model where model.id< "
				+ currTopic.getId() + " and  model.subjectId ="
				+ currTopic.getSubjectId() + " order by model.id desc";
		return (SubjectTopic) subjectTopicDAO.findSingleByStatement(jpql);
	}

	public SubjectTopic getPrevSubjectTopic(SubjectTopic currTopic) {

		String jpql = "select model from SubjectTopic model where model.id> "
				+ currTopic.getId() + " and  model.subjectId ="
				+ currTopic.getSubjectId() + " order by model.id asc";
		return (SubjectTopic) subjectTopicDAO.findSingleByStatement(jpql);
	}

	/**
	 * get a Subject
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubject(Integer id) {
		return (Subject) subjectDAO.getByPrimaryKey(Subject.class, id);
	}

	/**
	 * get subject count
	 * 
	 * @param type
	 * @return
	 */
	public Integer getSubjectCnt(String title, String type) {
		String jpql = "select count(model)  from Subject model  where model.id>0 ";
		if (StringUtils.isNotEmpty(type)) {
			jpql += " and  model.type ='" + type + "' ";
		}
		if (StringUtils.isNotEmpty(title)) {
			jpql += " and  model.title like '%" + title + "%' ";
		}
		return subjectDAO.getCntByStatement(jpql);
	}

	/**
	 * get SubjectTemplate
	 * 
	 * @param id
	 * @return
	 */
	public SubjectTemplate getSubjectTemplate(Integer id) {
		return (SubjectTemplate) subjectTemplateDAO.getByPrimaryKey(
				SubjectTemplate.class, id);
	}

	/**
	 * get template by code
	 * 
	 * @param id
	 * @return
	 */
	public SubjectTemplate getSubjectTemplateByCode(String code) {
		String jpql = "select model from SubjectTemplate model where model.code='"
				+ code + "' ";
		return (SubjectTemplate) subjectTemplateDAO.findSingleByStatement(jpql);
	}

	/**
	 * get subject template count
	 * 
	 * @param type
	 * @return
	 */
	public Integer getSubjectTemplateCnt() {
		String jpql = "select count(model) from SubjectTemplate model  where model.id>0";
		return subjectTemplateDAO.getCntByStatement(jpql);
	}

	/**
	 * get SubjectTopic
	 * 
	 * @param id
	 * @return
	 */
	public SubjectTopic getSubjectTopic(Integer id) {
		return (SubjectTopic) subjectTopicDAO.getByPrimaryKey(
				SubjectTopic.class, id);
	}

	/**
	 * get topic count
	 * 
	 * @param boardId
	 * @param username
	 * @param type
	 * @return
	 */
	public Integer getSubjectTopicCnt(Integer subjectId, String type) {
		String jpql = "select count(model) from SubjectTopic model where model.id>0  ";
		if (subjectId != null) {
			jpql += " and  model.subjectId =" + subjectId;
		}
		if (StringUtils.isNotEmpty(type)) {
			jpql += " and  model.type ='" + type + "' ";
		}
		return subjectTopicDAO.getCntByStatement(jpql);
	}

	/**
	 * save a Subject
	 * 
	 * @param model
	 * @param update
	 */
	public void saveSubject(Subject model) {
		if (model.getId() != null) {
			subjectDAO.update(model);
		} else {
			subjectDAO.save(model);
		}
	}

	/**
	 * save SubjectTemplate
	 * 
	 * @param model
	 */
	public void saveSubjectTemplate(SubjectTemplate model) {
		if (model.getId() != null && model.getId() > 0) {
			subjectTemplateDAO.update(model);
		} else {
			subjectTemplateDAO.save(model);
		}
	}

	/**
	 * save SubjectTopic
	 * 
	 * @param model
	 */
	public void saveSubjectTopic(SubjectTopic model) {
		if (model.getId() != null) {
			subjectTopicDAO.update(model);
		} else {
			subjectTopicDAO.save(model);
		}
	}

}
