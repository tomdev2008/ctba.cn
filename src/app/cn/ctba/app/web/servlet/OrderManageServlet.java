package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.OrderModel;

import cn.ctba.app.service.OrderService;

import com.google.inject.Inject;

/**
 * 
 * #908 用户团购订单管理功能
 * 
 * @author gladstone
 * @since May 17, 2009
 */
@WebModule("orderManage")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class OrderManageServlet extends BusinessCommonServlet {
	private static final Log logger = LogFactory
			.getLog(OrderManageServlet.class);
	@Inject
	private OrderService orderService;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/manage/equipment/orderList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("process list");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<OrderModel> models = null;
		models = this.orderService.findOrderModels(null, start, limit);
		request.setAttribute("count", orderService.getOrderModelsCnt(null));
		request.setAttribute("models", models);
	}
}
