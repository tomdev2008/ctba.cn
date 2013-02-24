package org.net9.core.validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.junit.Test;
import org.net9.domain.model.core.User;

public class SampleValidatorTest {

	@Test
	public void testValidateEmail() throws IOException, ValidatorException {
		// 创建ValidatorResources实例，这里取得验证规则信息。可以同时加载多个规则文件。
		ValidatorResources resources = new ValidatorResources();
		InputStream in = SampleValidatorTest.class
				.getResourceAsStream("/org/net9/core/validator/sample_validator.xml");

		ValidatorResourcesInitializer.initialize(resources, in);

		// 创建要校验的bean
		User bean = new User();

		// 创建校验器
		Validator validator = new Validator(resources, "nameForm");
		validator.addResource(Validator.BEAN_KEY, bean);

		// 校验
		ValidatorResults results = validator.validate();
		printResults(bean, results, resources);
		bean.setUserName("gladstone");
		bean.setUserFace("dummy");
		results = validator.validate();
		printResults(bean, results, resources);
		results = validator.validate();
		printResults(bean, results, resources);

	}

	private static ResourceBundle apps = ResourceBundle
			.getBundle("ApplicationResources");

	@SuppressWarnings("unchecked")
	private static void printResults(User bean, ValidatorResults results,
			ValidatorResources resources) {
		boolean success = true;
		Form form = resources.get(Locale.getDefault(), "nameForm");
		System.out.println("--验证--");
		System.out.println(bean);
		Iterator propertyNames = results.get();
		while (propertyNames.hasNext()) {
			String propertyName = (String) propertyNames.next();
			Field field = (Field) form.getFieldMap().get(propertyName);
			String prettyFieldName = getGBKMsg(apps.getString(field.getArg0()
					.getKey()));
			// 解释校验结果，取得对email属性校验的结果
			ValidatorResult result = results.getValidatorResult(propertyName);
			Map actionMap = result.getActionMap();
			Iterator keys = actionMap.keySet().iterator();
			// 一个属性可以使用多种校验属性，需要知道某个校验器校验的结果可以通过下面的方法，它将返回boolean值
			while (keys.hasNext()) {
				String actName = (String) keys.next();
				ValidatorAction action = resources.getValidatorAction(actName);
				System.out.println(propertyName + "[" + actName + "] ("
						+ (result.isValid(actName) ? "验证通过" : "验证失败") + ")");
				if (!result.isValid(actName)) {
					success = false;
					String message = getGBKMsg(apps.getString(action.getMsg()));
					Object[] args = { prettyFieldName };
					System.out.println("错误信息是: "
							+ MessageFormat.format(message, args));
				}
			}
		}
		if (success) {
			System.out.println("表单验证通过");
		} else {
			System.out.println("表单验证失败");
		}
	}

	/**
	 * 验证:{ username =null, age=null} age[required] (验证失败)错误信息是: 必须提供年龄字段！
	 * username[required] (验证失败)错误信息是: 必须提供姓名字段！ 表单验证失败 验证:{ username =龚永生,
	 * age=很年轻} age[required] (验证通过) age[int] (验证失败) 错误信息是: 年龄字段必须是整数！
	 * username[required] (验证通过)表单验证失败 验证:{ username =龚永生, age=28} age[required]
	 * (验证通过) age[int] (验证通过) username[required] (验证通过) 表单验证通过
	 */
	private static String getGBKMsg(String msg) {
		String gbkStr = "";
		try {
			gbkStr = new String(msg.getBytes("iso-8859-1"), "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gbkStr;
	}
}
