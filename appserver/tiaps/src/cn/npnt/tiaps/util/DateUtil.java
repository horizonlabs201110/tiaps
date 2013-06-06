package cn.npnt.tiaps.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String format_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-2-5 下午11:44:20
	 * @description 获取给定的日期的相差的时间
	 */
	public static Date getDecreaseDateString(Date date , int descDateNum){
		Date desc = null;
		if(date != null && descDateNum != 0){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, descDateNum);
			desc = calendar.getTime();
		}
		return desc;
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-2-5 下午11:52:46
	 * @description 根据给定的格式，返回格式化后的日期字符串
	 */
	public static String getFormatedDateString(Date date,String formatString){
		String result = null;
		if(date != null && formatString != null){
			DateFormat formator = new SimpleDateFormat(formatString);
			result = formator.format(date);
		}
		return result;
	}
}
