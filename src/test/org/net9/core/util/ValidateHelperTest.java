package org.net9.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.net9.common.exception.ValidateException;
import org.net9.common.util.DateUtils;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;

public class ValidateHelperTest {

	@Test
	public void testValidateBean() {
		System.out.println("== testValidateBean ==");
		Message model = new Message();
		model.setMsgContent("dummyContent");
		model.setMsgFrom("gladstone");
		model.setMsgTitle("dummyTitle");
		model.setMsgTime(DateUtils.getNow());
		model.setMsgTo("gladstone9");

		try {
			ValidateHelper.validateBean(model, "messageBean");
		} catch (ValidateException e) {

			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		boolean succeed = false;

		model.setMsgContent(null);
		model.setMsgTitle(null);
		model.setMsgTime(DateUtils.getNow());
		model.setMsgTo("gladstone9");

		try {
			ValidateHelper.validateBean(model, "messageBean");
		} catch (ValidateException e) {
			// System.out.println(e.getMessage());
			System.out.println(e.getMsgId());
			Assert.assertTrue("page.message.error.title".equals(e.getMsgId()));
			// e.printStackTrace();
			succeed = true;
		}
		if (!succeed) {
			Assert.fail();
		}
	}

	@Test
	public void testValidateTopicBean() {
		System.out.println("== testValidateTopicBean ==");
		Topic model = new Topic();

		model.setTopicTitle("ntdummyContentdummyContent");
		model.setTopicContent("gladstone");

		try {
			ValidateHelper.validateBean(model, "topicBean");
		} catch (ValidateException e) {

			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		boolean succeed = false;
		// 超过50
		model
				.setTopicTitle("dummyContentdummyContentdummyContentdummyContentdummyContentdummyContent");
		model.setTopicContent("gladstone");
		try {
			ValidateHelper.validateBean(model, "topicBean");
		} catch (ValidateException e) {
			// System.out.println(e.getMessage());
			System.out.println(e.getMsgId());
			Assert.assertTrue("page.board.topic.form.error.title".equals(e
					.getMsgId()));
			// e.printStackTrace();
			succeed = true;
		}
		if (!succeed) {
			Assert.fail();
		}
	}

	@Test
	public void testValidateMainUserBean() {
		System.out.println("== testValidateMainUserBean ==");
		MainUser model = new MainUser();

		model.setQq("2339585");
		model.setMsn("gladstone@gmail.com");

		try {
			ValidateHelper.validateBean(model, "mainUserBean");
		} catch (ValidateException e) {

			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		boolean succeed = false;
		model.setQq("ntdummyContentdummyContent");
		model.setMsn("gladstone");
		try {
			ValidateHelper.validateBean(model, "mainUserBean");
		} catch (ValidateException e) {
			// System.out.println(e.getMessage());
			System.out.println(e.getMsgId());
			Assert
					.assertTrue("page.setting.info.error.qq".equals(e
							.getMsgId()));
			// e.printStackTrace();
			succeed = true;
		}
		if (!succeed) {
			Assert.fail();
		}
	}

	@Test
	public void testValidateSingle() {
		System.out.println("== testValidateSingle ==");
		try {
			ValidateHelper.validateSingleField("hahahad", "regUsername");
		} catch (ValidateException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		try {
			ValidateHelper.validateSingleField("hahaha哈哈d", "regUsername");
		} catch (ValidateException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			ValidateHelper.validateSingleField("番茄树", "regUsername");
		} catch (ValidateException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			ValidateHelper.validateSingleField("gladstone@gmail.com", "email");
		} catch (ValidateException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		boolean succeed = false;
		try {
			ValidateHelper.validateSingleField("h", "regUsername");
		} catch (ValidateException e) {
			// e.printStackTrace();
			Assert.assertEquals(e.getMsgId(), "page.user.reg.log.username");
			succeed = true;
		}

		try {
			ValidateHelper.validateSingleField("gladstone#gmail.com", "email");
		} catch (ValidateException e) {
			// e.printStackTrace();
			Assert.assertEquals(e.getMsgId(), "page.user.reg.log.email");
			succeed = true;
		}

		if (!succeed) {
			Assert.fail();
		}

	}
}
