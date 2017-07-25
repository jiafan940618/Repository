package com.hy.gf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String formatDate(Date date, String format){
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(date != null){
			result = sdf.format(date);
		}
		return result;
	}
	
	public static Date formatString(String str, String format) throws ParseException{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static Calendar getToday() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	public static String getCurrentDateStr(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
}
