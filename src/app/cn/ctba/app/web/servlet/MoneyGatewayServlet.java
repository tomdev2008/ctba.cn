package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.UserSecurityType;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.OrderModel;

import cn.ctba.app.OrderFetchType;
import cn.ctba.app.OrderStateType;
import cn.ctba.app.service.OrderService;

import com.alipay.util.CheckURL;
import com.alipay.util.Payment;
import com.google.inject.Inject;

/**
 * 
 * 支付接口
 * 
 * @author gladstone
 * @since May 17, 2009
 */
@WebModule("money")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class MoneyGatewayServlet extends BusinessCommonServlet {
	private static final Log logger = LogFactory
			.getLog(MoneyGatewayServlet.class);
	private static final String partner = "2088002895849562"; // partner合作伙伴id（必须填写）
	private static final String privateKey = "8lth1lag62j6t2u9a3r2fnly4qobfrl2"; // partner的对应交易安全校验码（必须填写）
	private static final String seller_email = "gladstone9@gmail.com"; // 卖家支付宝帐户
	@Inject
	private OrderService orderService;

	/**
	 * alipay_notify.jsp为对支付宝返回通知处理，服务器post消息到这个页面。 所以对应给notify_url这个参数设置。
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	// @ReturnUrl(rederect = false, url = "/apps/donate/indexPage.jsp")
	public void handleNotify(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "&partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id");

		// 获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map params = new HashMap();
		// 获得POST 过来参数设置到新的params中
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		String mysign = com.alipay.util.SignatureHelper
				.sign(params, privateKey);

		if (mysign.equals(request.getParameter("sign"))
				&& responseTxt.equals("true")) {
			logger.info("success");
			// 如果您申请了支付宝的购物卷功能，请在返回的信息里面不要做金额的判断，否则会出现校验通不过，出现调单。如果您需要获取买家所使用购物卷的金额,
			// 请获取返回信息的这个字段discount的值，取绝对值，就是买家付款优惠的金额。即
			// 原订单的总金额=买家付款返回的金额total_fee +|discount|.
			String tradeNO = request.getParameter("out_trade_no");
			// String price = request.getParameter("price");
			OrderModel model = this.orderService.getOrderModel(Integer
					.valueOf(tradeNO));
			if (model == null) {
				throw new ServletException("empty order");// TODO: change this
			}
			String tradeStatus = request.getParameter("trade_status");
			if ("WAIT_BUYER_PAY".equalsIgnoreCase(tradeStatus)) {

			} else if ("WAIT_BUYER_CONFIRM_GOODS".equalsIgnoreCase(tradeStatus)) {
				model.setTransState(OrderStateType.SENT.getValue());
				this.orderService.saveOrderModel(model);
			} else if ("TRADE_FINISHED".equalsIgnoreCase(tradeStatus)) {
				model.setTransState(OrderStateType.PAID.getValue());
				this.orderService.saveOrderModel(model);
			}
		} else {
			logger.error("fail");
		}
	}

	/**
	 * 3 alipay_return.jsp为对支付宝返回通知处理，ie页面跳转通知，只要支付成功，
	 * 支付宝通过get方式跳转到这个地址，并且带有参数给这个页面。
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void handleReturn(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// **********************************************************************************
		// 如果您服务器不支持https交互，可以使用http的验证查询地址
		// String alipayNotifyURL =
		// "https://www.alipay.com/cooperate/gateway.do?service=notify_verify"
		String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id");
		// String sign = request.getParameter("sign");
		// 获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map params = new HashMap();
		// 获得POST 过来参数设置到新的params中
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		String mysign = com.alipay.util.SignatureHelper_return.sign(params,
				privateKey);
		String sign = request.getParameter("sign");
		logger.info(mysign + "--------------------" + sign);
		request.setAttribute("responseTxt", responseTxt);
		String isSuccess = request.getParameter("is_success");
		if ("T".equalsIgnoreCase(isSuccess)
				|| (mysign.equals(sign) && responseTxt.equals("true"))) {
			logger.info("success");
			logger.info(params.get("body"));// 测试时候用，可以删除
			logger.info(params.get("subject"));
			logger.info("显示订单信息");
			logger.info(responseTxt);
			// 如果您申请了支付宝的购物卷功能，请在返回的信息里面不要做金额的判断，否则会出现校验通不过，出现调单。如果您需要获取买家所使用购物卷的金额,
			// 请获取返回信息的这个字段discount的值，取绝对值，就是买家付款优惠的金额。即
			// 原订单的总金额=买家付款返回的金额total_fee +|discount|.
			// Integer totalFee = Integer.valueOf(request
			// .getParameter("total_fee"));
			// String username = UserHelper.getuserFromCookie(request);
			String tradeNO = request.getParameter("out_trade_no");
			// String price = request.getParameter("price");
			OrderModel model = this.orderService.getOrderModel(Integer
					.valueOf(tradeNO));
			if (model == null) {
				throw new ServletException("empty order");// TODO: change this
			} else {
				model.setTransState(OrderStateType.PAID.getValue());
				this.orderService.saveOrderModel(model);
			}
			request.getRequestDispatcher("/apps/order/done.jsp").forward(
					request, response);
		} else {
			logger.error(params.get("body"));// 测试时候用，可以删除
			logger.error(params.get("subject"));
			logger.error("显示订单信息");
			logger.error(responseTxt);
			logger.error("fail");
			this.sendError(request, response, "donate.error");
		}
	}

	/**
	 * 支付
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @ReturnUrl(rederect = false, url = "/apps/donate/confirm.jsp")
	public void pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("orderId");
		OrderModel model = this.orderService.getOrderModel(Integer.valueOf(id));
		if (model == null) {
			throw new ServletException("empty order");// TODO: change this
		}
		String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; // '支付接口
		String service = "trade_create_by_buyer";// create_direct_pay_by_user
		String sign_type = "MD5";
		String out_trade_no = "" + model.getId();
		String input_charset = "utf-8";

		// ******以上是账户信息，以下是商品信息**************************

		String body = model.getTitle(); // 为扯谈社的建设贡献力量 商品描述，推荐格式：商品名称（订单编号：订单编号）
		if (StringUtils.isNotEmpty(model.getDescription())) {
			body = StringUtils.getShorterStr(StringUtils.getFlatString(model
					.getDescription()));
		}

		String subject = model.getTitle(); // 商品名称
		String price = model.getPrice() + ""; // 订单总价
		String quantity = "1";
		String show_url = model.getReferenceUrl();
		String payment_type = "1";
		String discount = "0";

		String logistics_type = "EMS";
		String logistics_fee = "0.01";
		String logistics_payment = "SELLER_PAY";

		if (model.getFetchType().equals(OrderFetchType.BY_LOGISTICS.getValue())) {
			logistics_fee = "25";
			logistics_payment = "BUYER_PAY";
		}

		// 支付完成后跳转返回的网址URL
		String notify_url = "http://www.ctba.cn/money.action?method=handleNotify"; // 通知接收URL
		String return_url = "http://www.ctba.cn/money.action?method=handleReturn"; // 支付完成后跳转返回的网址URL
		String itemUrl = Payment.createUrl(paygateway, service, sign_type,
				out_trade_no, input_charset, partner, privateKey, seller_email,
				body, subject, price, quantity, show_url, payment_type,
				discount, logistics_type, logistics_fee, logistics_payment,
				return_url, notify_url);
		// notify_url需要的话请把这个参数加上到上面createurl
		response.sendRedirect(itemUrl);
	}
}
