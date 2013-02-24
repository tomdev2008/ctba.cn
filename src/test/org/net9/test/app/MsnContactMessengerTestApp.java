package org.net9.test.app;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnList;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.impl.MsnMessengerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MsnContactMessengerTestApp {

	private final static Log log = LogFactory.getLog(MsnContactMessengerTestApp.class);

	public static void main(String [] args) throws InterruptedException {
		
		String email =   "jpwiki@hotmail.com";
		String password =  "painter" ;
		
		MsnMessenger messenger ;
		log.debug("start msn bot with:" + email + " " + password);
		messenger = MsnMessengerFactory.createMsnMessenger(email, password);
		
		messenger.setSupportedProtocol(new MsnProtocol[] {MsnProtocol.MSNP11});//设置登录协议
        
		messenger.getOwner().setInitStatus(MsnUserStatus.HIDE);
//		messenger.setLogIncoming(false);
//		messenger.setLogOutgoing(false);
		messenger.setLogIncoming(true);
		messenger.setLogOutgoing(true);
		messenger.login();
		Thread.sleep(10000);
		MsnContact[] contacts = messenger.getContactList().getContactsInList(
				MsnList.AL);
		 for(MsnContact c: contacts){
		  System.out.println(c.getEmail());
		 }
		 log.debug("done");
		 messenger.logout();
		 messenger= null;
	}
}
