package spiderhub.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Array;

public class TimeDateFormat {

	private static String DATE_PART = "yyyy/MM/dd";
	private static String HOUR_PART = "HH:mm:ss";
	private static java.text.DateFormat FORMAT = new java.text.SimpleDateFormat(DATE_PART + " " + HOUR_PART);
	private static java.text.DateFormat HOUR_FORMAT = new java.text.SimpleDateFormat(HOUR_PART);
	private static java.text.DateFormat DATE_FORMAT = new java.text.SimpleDateFormat(DATE_PART);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String date = "2014/04/05";
		String time = "03:05:00";
		java.util.Date dt = fromStrings(date, time);
		System.out.println(dt);
		System.out.println(DATE_FORMAT.format(dt));
		System.out.println(HOUR_FORMAT.format(dt));

		String date1 = "2014/04/06";
		String time1 = "04:00:00";
		java.util.Date start = fromStrings(date1, time1);

		String date2 = "2014/04/06";
		String time2 = "04:30:00";
		java.util.Date end = fromStrings(date2, time2);

		count_hr_from_start_end(start, end);

		Calendar cal = Calendar.getInstance();
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

		System.out.println("no of days:   " + days);

		Calendar now = Calendar.getInstance();
		//
		System.out.println("Current Year is : " + now.get(Calendar.YEAR));
		// month start from 0 to 11
		System.out.println("Current Month is : " + (now.get(Calendar.MONTH) + 1));
		System.out.println("Current Date is : " + now.get(Calendar.DATE));

		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 int currentDate = now.get(Calendar.DATE);
		for (int i = 1; i <= currentDate; i++) {
			now.set(Calendar.DATE, i);
			Date d = now.getTime();
			dateList.add(d);
			System.out.println(d);
		}
	}

	public static Date fromStrings(String date, String time) {
		StringBuilder sb = new StringBuilder(date);
		sb.append(" ").append(time);
		try {
			return FORMAT.parse(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double count_hr_from_start_end(Date start, Date end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		double hours = 0;
		try {
			String d1 = format.format(start);
			String d2 = format.format(end);

			System.out.println("d1: " + d1 + "\n\nd2: " + d2);

			long diff = (format.parse(d2).getTime() - format.parse(d1).getTime()) / 1000;
			hours = (diff / (double) 3600);

		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
		return hours;
	}
}
