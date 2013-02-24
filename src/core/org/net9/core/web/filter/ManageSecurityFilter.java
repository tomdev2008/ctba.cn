package org.net9.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.web.filter.BaseFilter;
import org.net9.core.config.menu.AdminMenuHandler;
import org.net9.core.config.menu.MenuItem;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.util.HttpUtils;

/**
 * manage security filter
 * 
 * @author gladstone
 * @since Nov 16, 2008
 */
public class ManageSecurityFilter extends BaseFilter {

	/** Logger */
	private static Log log = LogFactory.getLog(ManageSecurityFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri =HttpUtils.getURL(httpRequest);

		String admin = (String) httpRequest.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME);

		try {
			if (HttpUtils.isInManageScope(httpRequest)) {
				if (CommonUtils.isEmpty(admin)
				&& !uri.equals(UrlConstants.MANAGE_LOGIN_PAGE)
						&& !uri.equals(UrlConstants.MANAGE_LOGIN_ACTION)
						&& isAppFileStr(uri) && !isPermitted(uri)) {
					log.error("admin forbidden");
					httpResponse.sendRedirect(httpRequest.getScheme() + "://"
							+ httpRequest.getServerName() + ":"
							+ httpRequest.getServerPort() + "/"
							+ httpRequest.getContextPath() + "/"
							+ UrlConstants.MANAGE_LOGIN_PAGE);
					return;
				}
				MenuItem rootMenuItem = AdminMenuHandler
						.buildRootMenuItem(admin);
				httpRequest.getSession().setAttribute("rootMenuItem",
						rootMenuItem);

			}
			chain.doFilter(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
