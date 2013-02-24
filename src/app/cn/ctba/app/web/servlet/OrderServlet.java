package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.ShareType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.OrderModel;
import org.net9.domain.model.group.ActivityModel;

import cn.ctba.app.OrderStateType;
import cn.ctba.app.service.OrderService;

import com.google.inject.Inject;

/**
 * 
 * #908 用户团购订单管理功能
 * 
 * @author gladstone
 * @since May 17, 2009
 */
@WebModule("order")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class OrderServlet extends BusinessCommonServlet {
	private static final Log logger = LogFactory.getLog(OrderServlet.class);
	@Inject
	private OrderService orderService;

	/**
	 * 创建订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/apps/order/confirm.jsp")
	public void create(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process create");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		// Integer type = Integer.valueOf(typeStr);
		OrderModel model = new OrderModel();

		if ("activity".equals(type)) {
			ActivityModel activityModel = this.activityService
					.getActivity(Integer.valueOf(id));
			model.setTitle(activityModel.getTitle());
			model.setType(1);// TODO:change this
			model.setDescription(activityModel.getContent());
			model.setPrice(Double.valueOf(activityModel.getRecieveMoney()));
			model.setReferenceUrl(HttpUtils.getReferer(request));
			model.setOwner(activityModel.getUsername());
		} else if ("equipment".equals(type)) {
			Equipment equipmentModel = this.equipmentService
					.getEquipment(Integer.valueOf(id));
			model.setTitle(equipmentModel.getName());
			model.setType(2);// TODO:change this
			model.setDescription(equipmentModel.getDiscription());
			model.setPrice(Double.valueOf(equipmentModel.getPrice()));
			model.setReferenceUrl(HttpUtils.getReferer(request));
			model.setOwner(equipmentModel.getUsername());
		}
		// 地址/邮编/提货方式/联系电话
		request.setAttribute("model", model);
	}

	/**
	 * 用户删除订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = true, url = "order.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process delete");
		String id = request.getParameter("id");
		OrderModel model = orderService.getOrderModel(Integer.valueOf(id));
		orderService.deleteOrder(model);
		// return new ActionForward("share.shtml?method=shares", true);
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
	@ReturnUrl(rederect = false, url = "/apps/order/list.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process list");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String username = UserHelper.getuserFromCookie(request);
		List<OrderModel> models = null;
		models = this.orderService.findOrderModels(username, start, limit);
		request.setAttribute("count", orderService.getOrderModelsCnt(username));
		request.setAttribute("models", models);
	}

	// @ReturnUrl(rederect = false, url = "/apps/order/confirm.jsp")
	public void submit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process indexPage");
		String username = UserHelper.getuserFromCookie(request);

		OrderModel model = new OrderModel();
		PropertyUtil.populateBean(model, request);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		model.setId(null);
		model.setTransState(OrderStateType.NEW.getValue());
		orderService.saveOrderModel(model);

		model = this.orderService.getNewestOrderModelByUsername(username);
		// redirect to alipay
		request.getRequestDispatcher(
				"/money.action?method=pay&orderId=" + model.getId()).forward(
				request, response);
	}

	/**
	 * 创建订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @ReturnUrl(rederect = false, url = "/apps/donate/confirm.jsp")
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process indexPage");
		String id = request.getParameter("id");
		String typeStr = request.getParameter("type");
		String username = UserHelper.getuserFromCookie(request);
		Integer type = Integer.valueOf(typeStr);
		String label = "";
		if (ShareType.BBS.getValue().equals(type)) {
			Topic topic = topicService.getTopic(Integer.valueOf(id));
			label = topic.getTopicTitle();
		} else if (ShareType.BLOG.getValue().equals(type)) {
			BlogEntry e = entryService.getEntry(Integer.valueOf(id));
			label = e.getTitle();
		}

		OrderModel model = new OrderModel();
		model.setTitle(label);
		model.setType(type);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		if (model != null) {
			userService.trigeEvent(this.userService.getUser(username), String
					.valueOf(model.getId()), UserEventType.SHARE_NEW);
		}
	}

	/**
	 * 查看订单详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/apps/order/view.jsp")
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process view");
		String id = request.getParameter("id");
		OrderModel model = this.orderService.getOrderModel(Integer.valueOf(id));
		request.setAttribute("model", model);
		request.setAttribute("ownerModel", this.userService.getUser(null, model
				.getOwner()));
	}
}
