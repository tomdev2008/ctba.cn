package org.net9.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.net9.common.exception.ValidateException;

/**
 * 验证通用类, 使用Common Validator
 * 
 * @author gladstone
 * @since Apr 30, 2009
 */
public class ValidateHelper {

	static Log logger = LogFactory.getLog(ValidateHelper.class);

	/** 默认的认证规则定义文件 */
	public static final String VALIDATOR_DEF = "/validation.xml";
	/** 单个field认证时使用的form名字 */
	public static final String GLOBAL_FORM = "globalBean";

	/**
	 * 
	 * 验证POJO
	 * 
	 * @param bean
	 * @param formName
	 * @throws ValidateException
	 */
	@SuppressWarnings("unchecked")
	public static void validateBean(Object bean, String formName)
			throws ValidateException {
		// 创建ValidatorResources实例，这里取得验证规则信息。可以同时加载多个规则文件。
		ValidatorResources resources = new ValidatorResources();
		InputStream in = ValidateHelper.class
				.getResourceAsStream(VALIDATOR_DEF);
		try {
			ValidatorResourcesInitializer.initialize(resources, in);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ValidateException(e.getMessage());
		}

		// 创建校验器
		Validator validator = new Validator(resources, formName);

		if (validator == null) {
			logger.warn("validator not exists!");
			return;
		}
		validator.addResource(Validator.BEAN_KEY, bean);

		// 校验
		try {
			ValidatorResults results = validator.validate();
			boolean success = true;
			Form form = resources.get(Locale.getDefault(), formName);
			for (Iterator propertyNames = results.get(); propertyNames
					.hasNext();) {
				String propertyName = (String) propertyNames.next();
				logger.debug("propertyName: " + propertyName);

				// 解释校验结果，取得对email属性校验的结果
				ValidatorResult result = results
						.getValidatorResult(propertyName);
				Map actionMap = result.getActionMap();
				Iterator keys = actionMap.keySet().iterator();
				// 一个属性可以使用多种校验属性，需要知道某个校验器校验的结果可以通过下面的方法，它将返回boolean值
				while (keys.hasNext()) {
					String validatorName = (String) keys.next();

					logger.debug(propertyName + "[" + validatorName + "] ("
							+ (result.isValid(validatorName) ? "通过" : "失败")
							+ ")");
					if (!result.isValid(validatorName)) {

						logger.debug("actName: " + validatorName);
						Field field = (Field) form.getFieldMap().get(
								propertyName);
						Arg arg = field.getArg0();
						if (arg == null) {
							ValidatorAction action = resources
									.getValidatorAction(validatorName);
							String messageKey = action.getMsg();
							throw new ValidateException(messageKey);
						} else {
							String messageKey = arg.getKey();
							throw new ValidateException(messageKey);
						}

						// success = false;
						// String message = getGBKMsg(apps.getString(action
						// .getMsg()));
						// Object[] args = { prettyFieldName };
						// System.out.println("错误信息是: "
						// + MessageFormat.format(message, args));
					}
				}
			}
			if (!success) {
				throw new ValidateException();
			}
		} catch (ValidatorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ValidateException(e.getMessage());
		}
	}

	/**
	 * 验证单个Field
	 * 
	 * 验证规则定义在validator.xml的globalBean里面
	 * 
	 * !注意，这里不验证required!
	 * 
	 * @param field
	 *            被验证的物件值
	 * @param fieldName
	 *            validator.xml的globalBean里定义的规则名称
	 * @throws ValidateException
	 */
	@SuppressWarnings("unchecked")
	public static void validateSingleField(Object field, String fieldName)
			throws ValidateException {
		// 创建ValidatorResources实例，这里取得验证规则信息。可以同时加载多个规则文件。
		ValidatorResources resources = new ValidatorResources();
		InputStream in = ValidateHelper.class
				.getResourceAsStream(VALIDATOR_DEF);
		try {
			ValidatorResourcesInitializer.initialize(resources, in);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ValidateException(e.getMessage());
		}

		// 创建校验器
		Validator validator = new Validator(resources, GLOBAL_FORM);

		if (validator == null) {
			logger.warn("validator not exists!");
			return;
		}

		Map m = new HashMap();
		m.put(fieldName, field);

		validator.addResource(Validator.FIELD_KEY, field);
		validator.addResource(Validator.BEAN_KEY, m);

		// 校验
		try {
			ValidatorResults results = validator.validate();
			boolean success = true;
			Form form = resources.get(Locale.getDefault(), GLOBAL_FORM);
			for (Iterator propertyNames = results.get(); propertyNames
					.hasNext();) {
				String propertyName = (String) propertyNames.next();
				logger.debug("propertyName: " + propertyName);

				// 解释校验结果，取得对email属性校验的结果
				ValidatorResult result = results
						.getValidatorResult(propertyName);
				Map actionMap = result.getActionMap();
				Iterator keys = actionMap.keySet().iterator();
				// 一个属性可以使用多种校验属性，需要知道某个校验器校验的结果可以通过下面的方法，它将返回boolean值
				while (keys.hasNext()) {
					String validatorName = (String) keys.next();

					logger.debug(propertyName + "[" + validatorName + "] ("
							+ (result.isValid(validatorName) ? "通过" : "失败")
							+ ")");
					if (!result.isValid(validatorName)) {

						logger.debug("actName: " + validatorName);
						Field validateField = (Field) form.getFieldMap().get(
								propertyName);
						Arg arg = validateField.getArg0();
						if (arg == null) {
							ValidatorAction action = resources
									.getValidatorAction(validatorName);
							String messageKey = action.getMsg();
							throw new ValidateException(messageKey);
						} else {
							String messageKey = arg.getKey();
							throw new ValidateException(messageKey);
						}
					}
				}
			}
			if (!success) {
				throw new ValidateException();
			}
		} catch (ValidatorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ValidateException(e.getMessage());
		}
	}
}
