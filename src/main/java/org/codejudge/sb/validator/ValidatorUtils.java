package org.codejudge.sb.validator;

public class ValidatorUtils {

	public static boolean isEmptyOrNull(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isEmptyOrNull(Long value) {
		return value == null || value == 0l;
	}
}
