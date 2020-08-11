package com.tp.wechat.utill;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtill {
	/*
	 * 返回当前时间的字符串 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * */
	public static String getStringDate() {
		     Date currentTime = new Date();
		     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     String dateString = formatter.format(currentTime);
		     return dateString;
		  }

}
