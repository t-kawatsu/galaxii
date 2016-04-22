package com.galaxii.common.struts.util;

import java.util.Locale;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class LocaleTypeConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (toClass == Locale.class) {
			try {
				return new Locale(values[0]);
			} catch (Exception e) {
				throw new TypeConversionException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		try {
			return o.toString();
		} catch (Exception e) {
			throw new TypeConversionException(e.getMessage());
		}
	}

}
