package org.net9.core.util;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.I18nMsgUtils;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.ctba.Vote;

public class UserHelperTest {

	@Test
	public void testAuthUserForCurrentPojoSimply() {
		BlogEntry model = new BlogEntry();
		model.setAuthor("gladstone");
		boolean isValid = false;
		try {
			UserHelper.authUserForCurrentPojoSimply("gladstone9", model);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = true;
		}

		try {
			UserHelper.authUserForCurrentPojoSimply("gladstone", model);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = true;
		}

		Topic t = new Topic();
		t.setTopicAuthor("painter");
		try {
			UserHelper.authUserForCurrentPojoSimply("painter", t);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = false;
		}
		try {
			UserHelper.authUserForCurrentPojoSimply("painter_dddddd", t);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = true;
		}

		BlogComment c = new BlogComment();
		c.setAuthor("gladstone_000");
		try {
			UserHelper.authUserForCurrentPojoSimply("gladstone_000", c);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = false;
		}
		try {
			UserHelper.authUserForCurrentPojoSimply("gladstone", c);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = true;
		}

		Vote v = new Vote();
		v.setUsername("ggg");
		try {
			UserHelper.authUserForCurrentPojoSimply("ggg", v);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = false;
		}
		try {
			UserHelper.authUserForCurrentPojoSimply("gladstone", v);
		} catch (InvalidAuthorSecurityException e) {
			System.out.println(I18nMsgUtils.getInstance().getMessage(
					e.getMessage()));
			isValid = true;
		}

		if (!isValid) {
			fail("did not catch the exception!");
		}
	}
}
