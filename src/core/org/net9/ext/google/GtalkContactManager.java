package org.net9.ext.google;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

/**
 * Gtalk 机器人，取得Gtalk联系人
 * 
 * @author gladstone
 *
 */
public class GtalkContactManager {

	private static final Log logger= LogFactory.getLog(GtalkContactManager.class);
	
	private String username;
	
	private String password;
	
	public GtalkContactManager(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 取得Gtalk联系人
	 * @return
	 */
	public  List<String> getContactList()  {
		List<String > contactList = new ArrayList<String>();
		ConnectionConfiguration config = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		config.setSASLAuthenticationEnabled(true);
		XMPPConnection.DEBUG_ENABLED = false;
		final XMPPConnection con = new XMPPConnection(config.getServiceName());
		try {
			con.connect();
			con.login(this.username, this.password);
		} catch (XMPPException e) {
			logger.error(e.getMessage());
			return null;
		}
		//隐身登录
		Presence presence = new Presence(Presence.Type.unavailable);
		con.sendPacket(presence);
		final Roster roster = con.getRoster();

		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			String u = entry.getUser();
			if (roster.getPresence(u) != null) {
				logger.debug(u + ": " + roster.getPresence(u));
				contactList.add(u);
			}
		}
		return contactList;

	}

}