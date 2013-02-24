package org.net9.subject.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.CommendType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.User;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTemplate;
import org.net9.domain.model.subject.SubjectTopic;
import org.net9.subject.service.SubjectTypeHelper;

/**
 * subject action
 * 
 * @author gladstone
 */
public class SubjectAction extends BusinessDispatchAction {

	public static Log log = LogFactory.getLog(SubjectAction.class);

	private void _prepareCommends(HttpServletRequest request) {
		List<MainCommend> commendList = commonService.findMainCommendsByType(
				CommendType.SUBJECT.getValue(), 0, 10);
		request.setAttribute("commendList", commendList);
	}

	@SuppressWarnings("unchecked")
	private void _topics(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List newTopics = subjectService.findSubjectTopics(null, null, 0, 10);
		List hotTopics = subjectService.findHotSubjectTopics(0, 10);
		List newSubjects = subjectService.findSubjects(null, null, 0, 10);
		request.setAttribute("newSubjects", newSubjects);
		request.setAttribute("hotTopics", hotTopics);
		request.setAttribute("newTopics", newTopics);
	}

	/**
	 * index page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward indexPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List typeList = SubjectTypeHelper.typeList;
		List subsWithType = new ArrayList();
		for (int i = 0; i < typeList.size(); i++) {
			Map m = new HashMap();
			Map typeMap = (Map) typeList.get(i);
			m.put("typeMap", typeMap);
			String code = (String) typeMap.get("subjectTypeCode");
			Integer cnt = subjectService.getSubjectCnt(null, code);
			if (cnt <= 0) {
				continue;
			}
			List subs = subjectService.findSubjects(null, code, 0, 6);
			List subsMapList = new ArrayList();
			for (int j = 0; j < subs.size(); j++) {
				Map subMap = new HashMap();
				Subject s = (Subject) subs.get(j);
				Integer topicCnt = subjectService.getSubjectTopicCnt(s.getId(),
						null);
				SubjectTopic t = subjectService.getNewSubjectTopic(s.getId());
				subMap.put("newTopic", t);
				subMap.put("subject", s);
				subMap.put("topicCnt", topicCnt);
				subsMapList.add(subMap);
			}

			m.put("subs", subsMapList);
			m.put("cnt", cnt);
			subsWithType.add(m);
		}
		List newTopics = subjectService.findSubjectTopics(null, null, 0, 20);
		List hotTopics = subjectService.findHotSubjectTopics(0, 10);
		List newSubjects = subjectService.findSubjects(null, null, 0, 20);
		request.setAttribute("newSubjects", newSubjects);
		request.setAttribute("hotTopics", hotTopics);
		request.setAttribute("newTopics", newTopics);
		request.setAttribute("subsWithType", subsWithType);
		this._prepareCommends(request);
		return mapping.findForward("sub.indexPage");
	}

	/**
	 * list subjects
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String searchKey;
		String type = request.getParameter("type");
		if (HttpUtils.isMethodPost(request)) {
			searchKey = request.getParameter("searchKey");
			request.getSession().setAttribute("searchKey_subject", searchKey);

		} else {
			searchKey = (String) request.getSession().getAttribute(
					"searchKey_subject");
		}

		log.debug(searchKey + "####");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List topics = subjectService
				.findSubjects(searchKey, type, start, limit);
		Integer count = subjectService.getSubjectCnt(searchKey, type);
		request.setAttribute("count", count);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("models", topics);
		this._prepareCommends(request);
		return mapping.findForward("sub.list");
	}

	/**
	 * list votes
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sid = request.getParameter("sid");
		log.debug(sid + "####");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Subject model = subjectService.getSubject(new Integer(sid));
		List topics = subjectService.findSubjectTopics(new Integer(sid), null,
				start, limit);
		Integer count = subjectService.getSubjectTopicCnt(new Integer(sid),
				null);
		request.setAttribute("subject", model);
		request.setAttribute("count", count);
		request.setAttribute("models", topics);
		_topics(request, response);
		this._prepareCommends(request);
		return mapping.findForward("sub.view");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sid = request.getParameter("sid");
		Subject model = subjectService.getSubject(new Integer(sid));
		SubjectTemplate template = subjectService
				.getSubjectTemplateByCode(model.getTemplate());
		if (template != null) {
			String url = template.getUrl();
			if (StringUtils.isNotEmpty(url)) {
				return new ActionForward(url, true);
			}
		}
		this._prepareCommends(request);
		return listTopics(mapping, form, request, response);
	}

	/**
	 * view sub topic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tid = request.getParameter("tid");
		SubjectTopic topic = subjectService.getSubjectTopic(new Integer(tid));
		Subject subject = subjectService.getSubject(topic.getSubjectId());
		request.setAttribute("subject", subject);
		User user = userService.getUser(null, subject.getAuthor());
		request.setAttribute("author", user);
		request.setAttribute("topic", topic);
		int hitPlus = new Random().nextInt(5) + 1;
		topic.setHits(topic.getHits() + hitPlus);
		subjectService.saveSubjectTopic(topic);
		_topics(request, response);

		// 增加上一篇和下一篇
		SubjectTopic nextTopic = subjectService.getNextSubjectTopic(topic);
		request.setAttribute("nextTopic", nextTopic);
		SubjectTopic prevTopic = subjectService.getPrevSubjectTopic(topic);
		request.setAttribute("prevTopic", prevTopic);
		this._prepareCommends(request);
		return mapping.findForward("sub.topic.view");
	}
}
