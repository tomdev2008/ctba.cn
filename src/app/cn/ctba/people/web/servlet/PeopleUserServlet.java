//package cn.ctba.people.web.servlet;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.net9.common.util.StringUtils;
//import org.net9.common.web.annotation.SecurityRule;
//import org.net9.common.web.annotation.WebModule;
//import org.net9.core.types.UserSecurityType;
//import org.net9.core.util.HttpUtils;
//import org.net9.core.util.UserHelper;
//import org.net9.core.web.servlet.BusinessCommonServlet;
//import org.net9.domain.model.ctba.People;
//import org.net9.domain.model.ctba.PeopleComment;
//
//import cn.ctba.people.service.PeopleCommentService;
//import cn.ctba.people.service.PeopleService;
//
//import com.google.inject.Inject;
//
//@WebModule("peopleUser")
//@SuppressWarnings("serial")
//@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
//public class PeopleUserServlet extends BusinessCommonServlet {
//
//	@Inject
//	protected PeopleService peopleService;
//	@Inject
//	protected PeopleCommentService peopleCommentService;
//
//	public void saveComment(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		String username = UserHelper.getuserFromCookie(request);
//		String pid = request.getParameter("pid");
//		People people = this.peopleService.getPeople(Integer.valueOf(pid));
//		PeopleComment model = new PeopleComment();
//		model.setCreateTime(StringUtils.getTimeStrByNow());
//		model.setIp(HttpUtils.getIpAddr(request));
//		model.setPeople(people);
//		model.setUsername(username);
//		model.setContent(request.getParameter("content"));
//		this.peopleCommentService.savePeopleComment(model);
//		response.sendRedirect("people.action?method=view&id=" + pid);
//	}
//
//	public void deleteComment(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		String username = UserHelper.getuserFromCookie(request);
//		String id = request.getParameter("id");
//		PeopleComment model = this.peopleCommentService
//				.getPeopleComment(Integer.valueOf(id));
//		Integer peopleId = model.getPeople().getId();
//		if (username.equals(model.getUsername())) {
//			this.peopleCommentService.deletePeopleComment(model);
//		}
//		response.sendRedirect("people.action?method=view&id=" + peopleId);
//	}
//
//	public void saveRef(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//	}
//
//}
