//package cn.ctba.people.web.servlet;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.DiskFileUpload;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.net9.common.exception.CommonSystemException;
//import org.net9.common.exception.InvalidAuthorSecurityException;
//import org.net9.common.exception.ModelNotFoundException;
//import org.net9.common.util.PropertyUtil;
//import org.net9.common.util.StringUtils;
//import org.net9.common.web.annotation.ReturnUrl;
//import org.net9.common.web.annotation.SecurityRule;
//import org.net9.common.web.annotation.WebModule;
//import org.net9.core.constant.SystemConfigVars;
//import org.net9.core.constant.WebConstants;
//import org.net9.core.types.UserSecurityType;
//import org.net9.core.util.HttpUtils;
//import org.net9.core.util.ImageUtils;
//import org.net9.core.util.UserHelper;
//import org.net9.core.web.servlet.BusinessCommonServlet;
//import org.net9.domain.model.ctba.People;
//import org.net9.domain.model.ctba.PeopleComment;
//import org.net9.group.web.GroupHelper;
//
//import cn.ctba.people.PeopleConstant;
//import cn.ctba.people.service.PeopleCommentService;
//import cn.ctba.people.service.PeopleService;
//
//import com.google.inject.Inject;
//
//@WebModule("people")
//@SuppressWarnings("serial")
//public class PeopleServlet extends BusinessCommonServlet {
//
//	@Inject
//	protected PeopleService peopleService;
//
//	@Inject
//	protected PeopleCommentService peopleCommentService;
//
//	/**
//	 * 首页
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@ReturnUrl(rederect = false, url = "/apps/people/indexPage.jsp")
//	public void indexPage(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		int start = HttpUtils.getStartParameter(request);
//		int limit = WebConstants.PAGE_SIZE_15;
//		List<People> hotList = this.peopleService.findPeoplesByHits(null, null,
//				start, limit);
//		request.setAttribute("hotList", hotList);
//
//	}
//
//	@ReturnUrl(rederect = false, url = "/apps/people/view.jsp")
//	public void view(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException, CommonSystemException {
//		String id = request.getParameter("pid");
//		People model;
//		model = this.peopleService.getPeople(Integer.valueOf(id));
//		if (model == null) {
//			throw new ModelNotFoundException();
//		}
//		request.setAttribute("model", model);
//		int start = HttpUtils.getStartParameter(request);
//		int limit = WebConstants.PAGE_SIZE_15;
//		List<PeopleComment> commentList = this.peopleCommentService
//				.findPeopleComment(null, model.getId(), start, limit);
//		request.setAttribute("commentList", commentList);
//		request.setAttribute("count", this.peopleCommentService
//				.getPeopleCommentCnt(null, model.getId()));
//	}
//
//	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
//	@ReturnUrl(rederect = false, url = "/apps/people/list.jsp")
//	public void list(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String username = UserHelper.getuserFromCookie(request);
//		int start = HttpUtils.getStartParameter(request);
//		int limit = WebConstants.PAGE_SIZE_15;
//		List<People> list = this.peopleService.findPeoples(username, null,
//				start, limit);
//		request.setAttribute("modelList", list);
//	}
//
//	/**
//	 * TODO:Make it common
//	 * 
//	 * @param request
//	 * @param wantedFilename
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	@SuppressWarnings("unchecked")
//	private Map<String, Object> uploadAttachment(HttpServletRequest request,
//			String wantedFilename) throws ServletException {
//		DiskFileUpload upload = new DiskFileUpload();
//		Map<String, Object> fields = new HashMap<String, Object>();
//		String attachmentStr = "dummy";
//		List<FileItem> items = null;
//		try {
//			items = upload.parseRequest(request);
//		} catch (FileUploadException e) {
//			e.printStackTrace();
//		}
//		if (items != null) {
//			for (FileItem item : items) {
//				try {
//					if (item.isFormField()) {
//						fields.put(item.getFieldName(), item
//								.getString(GroupHelper.DEFAULT_ENCODING));
//					} else {
//						fields.put(item.getFieldName(), item);
//					}
//
//					// 注意这里，名称必须是有后缀的
//					if (item.getFieldName().indexOf(wantedFilename) >= 0) {
//						FileItem uplFile = (FileItem) item;
//						if (uplFile != null
//								&& StringUtils.isNotEmpty(uplFile.getName())) {
//							String filename = ImageUtils
//									.wrapImageNameByTime(uplFile.getName());
//							String fileDir = SystemConfigVars.UPLOAD_DIR_PATH;
//							String fullPath = request
//									.getSession()
//									.getServletContext()
//									.getRealPath(
//											fileDir + File.separator + filename);
//							ImageUtils.checkDir(fullPath);
//							File pathToSave = new File(fullPath);
//							uplFile.write(pathToSave);
//							attachmentStr = filename;
//						}
//					}
//				} catch (NullPointerException ex) {
//					ex.printStackTrace();
//					continue;
//				} catch (Exception ex) {
//					continue;
//				} catch (Error ex) {
//					continue;
//				}
//
//			}
//		}
//		fields.put(wantedFilename, attachmentStr);
//		return fields;
//	}
//
//	/**
//	 * 提交修改
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
//	public boolean save(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String id = request.getParameter("pid");
//		String username = UserHelper.getuserFromCookie(request);
//
//		Map<String, Object> requestMap = this.uploadAttachment(request, "face");
//
//		People model;
//		if (StringUtils.isNotEmpty(id)) {
//			model = this.peopleService.getPeople(Integer.valueOf(id));
//		} else {
//			model = new People();
//			model.setCreateTime(StringUtils.getTimeStrByNow());
//			model.setHits(0);
//			model.setFamous(0);
//			model.setUpdateUsername(username);
//			model.setCreateUsername(username);
//			model.setCommentCnt(0);
//		}
//		PropertyUtil.populateBean(model, requestMap);
//		model.setUpdateTime(StringUtils.getTimeStrByNow());
//		model.setUpdateUsername(username);
//		this.peopleService.savePeople(model);
//		if (StringUtils.isNotEmpty(id)) {
//			response.sendRedirect("people/" + id);
//
//		} else {
//			List<People> list = this.peopleService.findPeoples(username, null,
//					0, 1);
//			response.sendRedirect("people/" + list.get(0).getId());
//		}
//		return true;
//	}
//
//	/**
//	 * 删除
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 * @throws InvalidAuthorSecurityException
//	 */
//	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
//	@ReturnUrl(rederect = true, url = "people.action?method=list")
//	public void delete(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException,
//			InvalidAuthorSecurityException {
//		String id = request.getParameter("pid");
//		People model;
//		if (StringUtils.isNotEmpty(id)) {
//			model = this.peopleService.getPeople(Integer.valueOf(id));
//
//			// validate user
//			String username = UserHelper.getuserFromCookie(request);
//			UserHelper.authUserForCurrentPojoSimply(username, model);
//
//			this.peopleService.deletePeople(model);
//		}
//	}
//
//	/**
//	 * 表单
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
//	@ReturnUrl(rederect = false, url = "/apps/people/form.jsp")
//	public void form(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String id = request.getParameter("pid");
//		People model;
//		if (StringUtils.isNotEmpty(id)) {
//			model = this.peopleService.getPeople(Integer.valueOf(id));
//			request.setAttribute("model", model);
//		}
//		request.setAttribute("peopleTypeList", PeopleConstant.peopleTypeList);
//
//		String username = UserHelper.getuserFromCookie(request);
//		// 作者最近的人物(10件)
//		List<People> newPeopleList = this.peopleService.findPeoples(username,
//				null, 0, 10);
//		request.setAttribute("newPeopleList", newPeopleList);
//	}
//}
