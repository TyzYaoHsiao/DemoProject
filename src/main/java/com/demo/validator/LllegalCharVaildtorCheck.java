package com.demo.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 內容是否存在非法字元
 */
public class LllegalCharVaildtorCheck implements ConstraintValidator<LllegalCharVaildtor, String> {

	private static final Pattern LLLEGAL_PATTERN =
			Pattern.compile("^[A-Za-z0-9`~!@#$^&*()-_=+\\[\\]\\|;:'\",<.>?\\/\\{\\}\\s]+$");

	@Override
	public void initialize(LllegalCharVaildtor constraintAnnotation) {
	}

	/**
	 * 只允許英文、數字、特殊符號白名單
	 * 特殊符號: `~!@#$^&*()-_=+[]\|;:'",<.>?半形空白
	 * @param value
	 * @param context
	 * @return
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)) {
			return true;
		}
		return LLLEGAL_PATTERN.matcher(value).matches();
	}

}
