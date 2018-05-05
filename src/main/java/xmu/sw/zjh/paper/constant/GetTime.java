package xmu.sw.zjh.paper.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime 
{
	public static String getCurrentTimeInString()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
}
