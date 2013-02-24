package org.net9.subject.web.struts.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTemplate;
import org.net9.domain.model.subject.SubjectTopic;
import org.net9.subject.service.SubjectTypeHelper;
import org.net9.subject.web.struts.form.SubjectForm;

/**
 * subject manage action
 * 
 * @author gladstone
 */
public class SubjectManageAction extends BusinessDispatchAction {

	public static Log log = LogFactory.getLog(SubjectManageAction.class);

	/**
	 * delete a subject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sid = request.getParameter("sid");
		if (StringUtils.isNotEmpty(sid)) {
			Subject model = subjectService.getSubject(new Integer(sid));
			subjectService.delSubject(model);
		}
		return new ActionForward("subManage.shtml?method=listSubjects", true);
	}

	/**
	 * delete a subject template
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubjectTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		if (StringUtils.isNotEmpty(sid)) {
			SubjectTemplate model = subjectService
					.getSubjectTemplate(new Integer(sid));

			subjectService.delSubjectTemplate(model);
		}
		return new ActionForward("subManage.shtml?method=listTemplates", true);
	}

	/**
	 * delete a subject topic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubjectTopic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		if (StringUtils.isNotEmpty(sid)) {
			SubjectTopic model = subjectService
					.getSubjectTopic(new Integer(sid));
			subjectService.delSubjectTopic(model);
		}
		return new ActionForward("subManage.shtml?method=listTopics", true);
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
	public ActionForward listSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Integer count = subjectService.getSubjectCnt(null, type);
		List<Subject> models = subjectService.findSubjects(null, type, start,
				limit);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		request.setAttribute("typeList", SubjectTypeHelper.typeList);
		return mapping.findForward("sub.list");
	}

	/**
	 * list subject templates
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTemplates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Integer count = subjectService.getSubjectTemplateCnt();
		List<SubjectTemplate> models = subjectService.findSubjectTemplates(
				start, limit);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		return mapping.findForward("sub.template.list");
	}

	/**
	 * list subject topics
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
		String type = request.getParameter("type");
		String subjectId = request.getParameter("sid");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Integer sid = null;
		if (StringUtils.isNotEmpty(subjectId)) {
			sid = new Integer(subjectId);
		}
		Integer count = subjectService.getSubjectTopicCnt(sid, type);
		List<SubjectTopic> models = subjectService.findSubjectTopics(sid, type,
				start, limit);
		List subjectList = subjectService.findSubjects(null, null, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		return mapping.findForward("sub.topic.list");
	}

	/**
	 * save subject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = (String) request.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME);

		SubjectForm subForm = (SubjectForm) form;

		FormFile file = subForm.getPic();
		String filePath = "";
		if (file != null && file.getFileSize() > 0
				&& StringUtils.isNotEmpty(file.getFileName())) {
			log.debug("get file:" + file.getFileName());

			String filename = ImageUtils.wrapImageNameByTime(file
					.getFileName());
			InputStream in = file.getInputStream();
			String fileDir = SystemConfigVars.UPLOAD_DIR_PATH;
			String fullPath = request.getSession().getServletContext()
					.getRealPath(fileDir)
					+ File.separator + filename;
			ImageUtils.checkDir(fullPath);
			OutputStream out = new FileOutputStream(fullPath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			in.close();
			file.destroy();

			filePath = filename;
			ImageUtils.getDefaultImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), false);
			ImageUtils.getMiniImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), false);
			ImageUtils.getSizedImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), 32);
			ImageUtils.getSizedImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), 209);
		}
		Subject model = null;
		if (null != subForm.getSid() && subForm.getSid() > 0) {
			model = subjectService.getSubject(subForm.getSid());
			PropertyUtil.copyProperties(model, subForm);
			model.setAuthor(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
		} else {
			model = new Subject();
			PropertyUtil.copyProperties(model, subForm);
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setAuthor(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
		}
		if (StringUtils.isNotEmpty(filePath)) {
			model.setImage(filePath);
		}
		subjectService.saveSubject(model);
		return new ActionForward("subManage.shtml?method=listSubjects", true);
	}

	/**
	 * save subject template
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSubjectTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = (String) request.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME);
		String sid = request.getParameter("sid");
		log.debug("#######" + sid);
		SubjectTemplate model = null;
		if (StringUtils.isNotEmpty(sid)) {
			model = subjectService.getSubjectTemplate(new Integer(sid));
			model.setUpdateTime(StringUtils.getTimeStrByNow());
		} else {
			model = new SubjectTemplate();
			model.setAuthor(username);
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setUpdateTime(StringUtils.getTimeStrByNow());
		}
		PropertyUtil.populateBean(model, request);
		subjectService.saveSubjectTemplate(model);
		return new ActionForward("subManage.shtml?method=listTemplates", true);
	}

	/**
	 * save subject topic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSubjectTopic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String sid = request.getParameter("sid");
		String topicNum = request.getParameter("topicNum");
		String type = request.getParameter("type");
		String description = request.getParameter("description");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String subjectId = request.getParameter("subjectId");
		SubjectTopic model = null;
		if (StringUtils.isNotEmpty(sid)) {
			model = subjectService.getSubjectTopic(new Integer(sid));
			model.setUpdateTime(StringUtils.getTimeStrByNow());

		} else {
			model = new SubjectTopic();
			model.setAuthor(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setHits(0);

		}
		if ("t".equals(type)) {
			Topic t = topicService.getTopic(new Integer(topicNum));
			if (t == null) {
				ActionMessages msgs = new ActionMessages();
				ActionMessage msg = new ActionMessage("subject.noTopic");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				saveMessages(request, msgs);
				return mapping.findForward("sub.topic.form");
			}
			model.setAuthor(t.getTopicAuthor());
			model.setContent(t.getTopicContent());
			model.setDescription(description);
			model.setEntryId(0);

			model.setTitle(t.getTopicTitle());
			model.setTopicId(t.getTopicId().intValue());
		} else if ("e".equals(type)) {
			BlogEntry e = entryService.getEntry(new Integer(topicNum));
			if (e == null) {
				ActionMessages msgs = new ActionMessages();
				ActionMessage msg = new ActionMessage("subject.noEntry");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				saveMessages(request, msgs);
				return mapping.findForward("sub.topic.form");
			}
			model.setAuthor(e.getAuthor());
			model.setContent(e.getBody());
			model.setDescription(description);
			model.setEntryId(e.getId());
			model.setTitle(e.getTitle());
			model.setTopicId(0);
		}
		model.setType(type);
		model.setSubjectId(new Integer(subjectId));
		if (StringUtils.isNotEmpty(title)) {
			model.setTitle(title);
		}
		if (StringUtils.isNotEmpty(content)) {
			model.setContent(content);
			if (StringUtils.isEmpty(description)) {
				model.setDescription(content);
			}
		}
		subjectService.saveSubjectTopic(model);
		return new ActionForward("subManage.shtml?method=listTopics", true);
	}

	/**
	 * subject form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward subjectForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sid = request.getParameter("sid");
		if (StringUtils.isNotEmpty(sid)) {
			Subject model = subjectService.getSubject(new Integer(sid));
			request.setAttribute("model", model);
			request.setAttribute("topicNum", model);

		}
		request.setAttribute("typeList", SubjectTypeHelper.typeList);
		return mapping.findForward("sub.form");
	}

	/**
	 * subject template form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward subjectTemplateForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		if (StringUtils.isNotEmpty(sid)) {
			SubjectTemplate model = subjectService
					.getSubjectTemplate(new Integer(sid));
			request.setAttribute("model", model);
		}
		return mapping.findForward("sub.template.form");
	}

	/**
	 * subject topic form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward subjectTopicForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		String tid = request.getParameter("tid");
		String eid = request.getParameter("eid");
		if (StringUtils.isNotEmpty(sid)) {
			SubjectTopic model = subjectService
					.getSubjectTopic(new Integer(sid));
			Integer topicNum = model.getType().equals("e") ? model.getEntryId()
					: model.getTopicId();
			request.setAttribute("topicNum", topicNum);
			request.setAttribute("model", model);
		} else {
			if (StringUtils.isNotEmpty(tid)) {
				SubjectTopic model = new SubjectTopic();
				model.setType("t");
				Topic t = topicService.getTopic(new Integer(tid));
				if (t != null) {
					model.setContent(t.getTopicContent());
					model.setEntryId(0);
					model.setTitle(t.getTopicTitle());
					model.setTopicId(t.getTopicId().intValue());
					request.setAttribute("topicNum", tid);
					request.setAttribute("model", model);
				}

			}
			if (StringUtils.isNotEmpty(eid)) {
				SubjectTopic model = new SubjectTopic();
				model.setType("e");
				BlogEntry e = entryService.getEntry(new Integer(eid));
				if (e != null) {
					model.setContent(e.getBody());
					model.setTitle(e.getTitle());
					request.setAttribute("topicNum", eid);
					request.setAttribute("model", model);
				}
			}
		}
		List subjectList = subjectService.findSubjects(null, null, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("subjectList", subjectList);
		return mapping.findForward("sub.topic.form");
	}

	// /**
	// * list comments
	// *
	// * @param mapping
	// * @param form
	// * @param request
	// * @param response
	// * @return
	// * @throws Exception
	// */
	// public ActionForward listComments(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// String offset = request.getParameter(Constant.PAGER_OFFSET);
	// int start = 0;
	// int limit = WebConstants.PAGE_SIZE_30;
	// if (StringUtils.isNotEmpty(offset)) {
	// start = new Integer(offset);
	// }
	// Integer count = subjectService.getSubjectCommentsCnt(null);
	// List<SubjectComment> models = subjectService.findSubjectComments(null,
	// start, limit);
	// request.setAttribute("count", count);
	// request.setAttribute("models", models);
	// return mapping.findForward("sub.comment.list");
	// }
	//
	// /**
	// * delete comment
	// *
	// * @param mapping
	// * @param form
	// * @param request
	// * @param response
	// * @return
	// * @throws Exception
	// */
	// public ActionForward deleteSubjectComment(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// String cid = request.getParameter("cid");
	// if (StringUtils.isNotEmpty(cid)) {
	// SubjectComment model = subjectService
	// .getSubjectComment(new Integer(cid));
	// subjectService.delSubjectComment(model);
	// }
	// return new ActionForward("subManage.shtml?method=listComments", true);
	// }
}
