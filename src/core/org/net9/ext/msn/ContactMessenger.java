package org.net9.ext.msn;

import java.util.ArrayList;
import java.util.List;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Msn robot
 * 
 * @author gladstone
 * 
 */
public class ContactMessenger extends BasicMessenger {

	public ContactMessenger(String email, String password) {
		super(email, password);
		contactList = new ArrayList<MsnContact>();
	}

	private final static Log log = LogFactory.getLog(ContactMessenger.class);

	private List<MsnContact> contactList = null;

	protected void initMessenger(MsnMessenger messenger) {
		// messenger.addListener(new DefaultMsnAdapter());
		// 得到所有的联系人，添加到列表中
		log.debug("MsnMessenger init...");
		messenger.addContactListListener(new ContactListAdapter(this));

	}

	public List<MsnContact> getContactList() {
		return contactList;
	}

	public void setContactList(List<MsnContact> contactList) {
		this.contactList = contactList;
	}

}
