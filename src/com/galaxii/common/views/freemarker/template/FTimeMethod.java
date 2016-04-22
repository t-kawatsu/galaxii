package com.galaxii.common.views.freemarker.template;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class FTimeMethod implements TemplateMethodModel {
	
    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		Date d = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			d = format.parse((String)args.get(0));
		} catch(ParseException e) {
			return new SimpleScalar(null);
		}
		  
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		
		SimpleDateFormat sdf = null;
		if(now.before(d)) {
			sdf = new SimpleDateFormat("H時m分");
		} else if(DateUtils.addDays(now, -1).before(d)) {
			sdf = new SimpleDateFormat("昨日H時");
		} else if(DateUtils.truncate(now, Calendar.MONTH).before(d)) {
			sdf = new SimpleDateFormat("d日H時");
		} else if(DateUtils.truncate(now, Calendar.YEAR).before(d)) {
			sdf = new SimpleDateFormat("M月d日");
		} else {
			sdf = new SimpleDateFormat("yy年M月d日");
		}

		return new SimpleScalar( sdf.format(d) );
    }
}
