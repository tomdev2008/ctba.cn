package org.net9.ext.msn;

import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.impl.MsnMessengerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BasicMessenger implements Runnable {

	private final static Log log = LogFactory.getLog(BasicMessenger.class);

	public BasicMessenger(String email, String password) {
		this.email = email;
		this.password = password;
	}

	private String email;

	private String password;

	private MsnMessenger messenger = null;

	protected void initMessenger(MsnMessenger messenger) {
	}

	public void start() {
		log.debug("start msn bot with:" + email + " " + password);
		messenger = MsnMessengerFactory.createMsnMessenger(email, password);
		messenger.setSupportedProtocol(new MsnProtocol[] {MsnProtocol.MSNP11});//设置登录协议
	      	messenger.getOwner().setInitStatus(MsnUserStatus.HIDE);
		messenger.setLogIncoming(false);
		messenger.setLogOutgoing(false);
		initMessenger(messenger);
		messenger.login();
		// messenger.logout();
	}

	public void stop() {
		if (messenger != null) {
			messenger.logout();
			messenger = null;
		}
	}

	public void run() {
		start();
	}

}
