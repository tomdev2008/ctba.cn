package org.net9.ext.msn;

import java.util.ArrayList;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnList;
import net.sf.jml.MsnMessenger;
import net.sf.jml.event.MsnContactListAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

;

public class ContactListAdapter extends MsnContactListAdapter {

	private ContactMessenger bot;

	public ContactListAdapter(ContactMessenger bot) {
		 log.debug("ContactListAdapter Init" );
		this.bot = bot;
	}

	private final static Log log = LogFactory.getLog(ContactListAdapter.class);

	public void contactListInitCompleted(MsnMessenger messenger) {
		MsnContact[] contacts = messenger.getContactList().getContactsInList(
				MsnList.AL);
		bot.setContactList(new ArrayList<MsnContact>());
		for (MsnContact contact : contacts) {
			bot.getContactList().add(contact);
		}
		// ContactMessenger.class.notify();
		log.debug(bot.getContactList().size() + " contacts in the list.");
		// for(MsnContact c: contacts){
		// System.out.println(c.getEmail());
		// }
		// notifyAll();
	}
}
