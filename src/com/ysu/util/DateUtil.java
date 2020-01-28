package com.ysu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String longToString(String dateFormat,Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    Date date= new Date(millSec);
	    return sdf.format(date);
	}
	public static String DateToString(String dateFormat,Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	
	//String类型日期转化为Date
	public static Date StringToDate(String date) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date dt2 = null;
		try {
			dt2 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt2;
	}
	
	public static void main(String[] args) {
		//System.out.println(DateUtil.DateToString("yyyy-MM-dd-HH:mm:ss", new Date()));
		String sDt = "2017-10-14-11:00:00";
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date dt2 = null;
		try {
			dt2 = sdf.parse(sDt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//System.out.println(DateToString("yyyy-MM-dd-HH:mm:ss", dt2));
		//继续转换得到秒数的long型
		long lTime = dt2.getTime() / 1000;
		long res = new Date().getTime()/1000 - lTime;
		System.out.println(res);
	}
}
