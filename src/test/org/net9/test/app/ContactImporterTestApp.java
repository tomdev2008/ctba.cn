package org.net9.test.app;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.xdatasystem.contactsimporter.ContactListImporter;
import com.xdatasystem.contactsimporter.ContactListImporterException;
import com.xdatasystem.contactsimporter.ContactListImporterFactory;
import com.xdatasystem.user.Contact;

/**
 * Contactlistimporter的简单测试case
 * 
 * http://code.google.com/p/contactlistimporter/
 * 
 * @author gladstone
 * @since Feb 7, 2009
 */
public class ContactImporterTestApp {
	public static void main(String[] args) throws ContactListImporterException, UnsupportedEncodingException {
		// automatically guess the correct implementaion based on the email
		// address

		// Hotmail / Windows Live
		ContactListImporter importer = ContactListImporterFactory.guess(
				"jpwiki@hotmail.com", "painter");
		List<Contact> contacts = importer.getContactList();
		for (Contact c : contacts) {
			System.out.println("name: " + new String(c.getName().getBytes("ISO-8859-1"),"UTF-8") + ", email: "
					+ c.getEmailAddress());
		}

		// Gmail
		ContactListImporter importer1 = ContactListImporterFactory.guess(
				"ctba.cn@gmail.com", "ctba4ever");
		List<Contact> contacts1 = importer1.getContactList();
		for (Contact c : contacts1) {
			System.out.println("name: " + c.getName() + ", email: "
					+ c.getEmailAddress());
		}
		// Yahoo
		ContactListImporter importer2 = ContactListImporterFactory.guess(
				"gladstone9@yahoo.cn", "painter");
		List<Contact> contacts2 = importer2.getContactList();
		for (Contact c : contacts2) {
			System.out.println("name: " + c.getName() + ", email: "
					+ c.getEmailAddress());
		}
		/*
		 * // creates an hotmail contact importer
		 * importer=ContactListImporterFactory.hotmail("someuser@hotmail.com",
		 * "password"); // creates an gmail contact importer
		 * importer=ContactListImporterFactory.gmail("someuser@gmail.com",
		 * "password");
		 */
	}
}
