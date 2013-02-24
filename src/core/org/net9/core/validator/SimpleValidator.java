package org.net9.core.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;

public class SimpleValidator {

	/**
	 * Commons Logging instance.
	 */
	private static final Log log = LogFactory.getLog(SimpleValidator.class);

	public static final String FIELD_TEST_NULL = "NULL";
	public static final String FIELD_TEST_NOTNULL = "NOTNULL";
	public static final String FIELD_TEST_EQUAL = "EQUAL";

	// public static boolean validateEmail(Object bean, Field field) {
	// return GenericValidator.isEmail(ValidatorUtil.getValueAsString(bean,
	// field.getProperty()));
	// }

	public static boolean validateRequired(Object bean, Field field) {
		// String value = ValidatorUtil
		// .getValueAsString(bean, field.getProperty());
		// return !GenericValidator.isBlankOrNull(value);

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return false;
		} else {
			return true;
		}
	}

	public static Integer validateInt(Object bean, Field field) {
		String value = ValidatorUtil
				.getValueAsString(bean, field.getProperty());
		Integer x = GenericTypeValidator.formatInt(value);
		return x;
	}

	/**
	 * Checks if the field matches the regular expression in the field's mask
	 * attribute.
	 * 
	 * @return true if field matches mask, false otherwise.
	 */
	public static boolean validateMask(Object bean, Field field) {

		String mask = field.getVarValue("mask");
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}
		log.debug("value: " + value);
		log.debug("mask: " + mask);
		try {
			if (!GenericValidator.isBlankOrNull(value)
					&& !GenericValidator.matchRegexp(value, mask)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * Checks if the field can safely be converted to a byte primitive.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateByte(Object bean, Field field) {

		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatByte(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field can safely be converted to a short primitive.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateShort(Object bean, Field field) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatShort(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field can safely be converted to an int primitive.
	 * 
	 * @param bean
	 *            The bean validation is being performed on.
	 * @param va
	 *            The <code>ValidatorAction</code> that is currently being
	 *            performed.
	 * @param field
	 *            The <code>Field</code> object associated with the current
	 *            field being validated.
	 * @param errors
	 *            The <code>ActionMessages</code> object to add errors to if
	 *            any validation errors occur.
	 * @param request
	 *            Current request object.
	 * @return true if valid, false otherwise.
	 */
	public static Object validateInteger(Object bean, Field field) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatInt(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field can safely be converted to a long primitive.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateLong(Object bean, Field field) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatLong(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field can safely be converted to a float primitive.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateFloat(Object bean, Field field) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatFloat(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field can safely be converted to a double primitive.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateDouble(Object bean, Field field) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatDouble(value);

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if the field is a valid date. If the field has a datePattern
	 * variable, that will be used to format
	 * <code>java.text.SimpleDateFormat</code>. If the field has a
	 * datePatternStrict variable, that will be used to format
	 * <code>java.text.SimpleDateFormat</code> and the length will be checked
	 * so '//' will not pass validation with the format 'MM/dd/yyyy' because the
	 * month isn't two digits. If no datePattern variable is specified, then the
	 * field gets the DateFormat.SHORT format for the locale. The setLenient
	 * method is set to <code>false</code> for all variations.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateDate(Object bean, Field field) {

		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}
		String datePattern = field.getVarValue("datePattern");
		String datePatternStrict = field.getVarValue("datePatternStrict");
		Locale locale = Locale.getDefault();

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		try {
			if (datePattern != null && datePattern.length() > 0) {
				result = GenericTypeValidator.formatDate(value, datePattern,
						false);
			} else if (datePatternStrict != null
					&& datePatternStrict.length() > 0) {
				result = GenericTypeValidator.formatDate(value,
						datePatternStrict, true);
			} else {
				result = GenericTypeValidator.formatDate(value, locale);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result == null ? Boolean.FALSE : result;
	}

	/**
	 * Checks if a fields value is within a range (min &amp; max specified in
	 * the vars attribute).
	 * 
	 * @return True if in range, false otherwise.
	 */
	public static boolean validateIntRange(Object bean, Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				int intValue = Integer.parseInt(value);
				int min = Integer.parseInt(field.getVarValue("min"));
				int max = Integer.parseInt(field.getVarValue("max"));

				if (!GenericValidator.isInRange(intValue, min, max)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if a fields value is within a range (min &amp; max specified in
	 * the vars attribute).
	 * 
	 * @return True if in range, false otherwise.
	 */
	public static boolean validateDoubleRange(Object bean, ValidatorAction va,
			Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				double doubleValue = Double.parseDouble(value);
				double min = Double.parseDouble(field.getVarValue("min"));
				double max = Double.parseDouble(field.getVarValue("max"));

				if (!GenericValidator.isInRange(doubleValue, min, max)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a fields value is within a range (min &amp; max specified in
	 * the vars attribute).
	 * 
	 * @return True if in range, false otherwise.
	 */
	public static boolean validateFloatRange(Object bean, Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				float floatValue = Float.parseFloat(value);
				float min = Float.parseFloat(field.getVarValue("min"));
				float max = Float.parseFloat(field.getVarValue("max"));

				if (!GenericValidator.isInRange(floatValue, min, max)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if the field is a valid credit card number.
	 * 
	 * @return true if valid, false otherwise.
	 */
	public static Object validateCreditCard(Object bean, Field field) {

		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return Boolean.TRUE;
		}

		result = GenericTypeValidator.formatCreditCard(value);

		return result == null ? Boolean.FALSE : result;

	}

	/**
	 * Checks if a field has a valid e-mail address.
	 * 
	 * @return True if valid, false otherwise.
	 */
	public static boolean validateEmail(Object bean, Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (!GenericValidator.isBlankOrNull(value)
				&& !GenericValidator.isEmail(value)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if the field's length is less than or equal to the maximum value.
	 * A <code>Null</code> will be considered an error.
	 * 
	 * @return True if stated conditions met.
	 */
	public static boolean validateMaxLength(Object bean, Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (value != null) {
			try {
				int max = Integer.parseInt(field.getVarValue("maxlength"));
				if (!GenericValidator.maxLength(value, max)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if the field's length is greater than or equal to the minimum
	 * value. A <code>Null</code> will be considered an error.
	 * 
	 * @return True if stated conditions met.
	 */
	public static boolean validateMinLength(Object bean, Field field) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				int min = Integer.parseInt(field.getVarValue("minlength"));

				if (!GenericValidator.minLength(value, min)) {

					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Return <code>true</code> if the specified object is a String or a
	 * <code>null</code> value.
	 * 
	 * @param o
	 *            Object to be tested
	 * @return The string value
	 */
	private static boolean isString(Object o) {
		return (o == null) ? true : String.class.isInstance(o);
	}

}
