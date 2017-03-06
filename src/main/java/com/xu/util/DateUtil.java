package com.xu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateUtil {
	public static String getFormat(Date date){
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String StringDate = format2.format(date);
		return StringDate;
		
	}
}
