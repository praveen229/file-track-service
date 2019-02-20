package com.qcom.search.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	
	public static Date getDateObject(String dateString, String format) throws ParseException{
		Date tstamp=null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if(StringUtils.isNotBlank(dateString)){
			tstamp = formatter.parse(dateString);
		}
		return tstamp;
	}
	
	public static String getFormattedDate(Date date, String format) throws ParseException{
		String formattedDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if(date != null){
			formattedDate = formatter.format(date);
		}
		return formattedDate;
	}
}
