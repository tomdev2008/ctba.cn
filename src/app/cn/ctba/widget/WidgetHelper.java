package cn.ctba.widget;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.MainUser;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class WidgetHelper implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected UserService userService;

	public WidgetHelper() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	static Log log = LogFactory.getLog(WidgetHelper.class);

	public MainUser validateUser(HttpServletRequest request)
			throws SecurityException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new SecurityException();
		}
		MainUser mainUser = userService.getUser(username);
		if (mainUser != null) {
			if (!UserHelper.authPassword(mainUser, password)) {
				throw new SecurityException();
			}
			return mainUser;
		} else {
			throw new SecurityException();
		}
	}
}
