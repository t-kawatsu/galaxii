package com.galaxii.common.util;

/**
 * http://www.jarvana.com/jarvana/view/org/extrema-sistemas/loom/loom/1.5/loom-1.5-sources.jar!/org/loom/util/StringUtils.java?format=ok
 *
 */
public class StringUtils {
	
	public static String dasherize(String text) {
		return separate(text, '-');
	}
	
	public static String separate(String text, char separator) {
		StringBuilder builder = new StringBuilder(text.length() + text.length() / 2);
		int l = text.length();
		for (int i = 0; i < l; i++) {
			char c = text.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i != 0) {
					builder.append(separator);
				}
				builder.append(Character.toLowerCase(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}
