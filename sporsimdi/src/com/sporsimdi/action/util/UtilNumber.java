package com.sporsimdi.action.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilNumber {

	private Calendar cal;
		
	public UtilNumber() {
		cal = Calendar.getInstance(new Locale("tr"));
	}
	
	public UtilNumber(Date date) {
		cal = Calendar.getInstance(new Locale("tr"));
		if (date != null) {
			cal.setTime(date);			
		} else {
			cal = null;
		}
	}
	
	public UtilNumber(int year, int month, int date) {
		cal = Calendar.getInstance(new Locale("tr"));
		cal.set(year, month, date);
	}

	public void setTime(Date date) {
		cal.setTime(date);
	}
	
	public Date getTime() {
		return cal.getTime();
	}

	public int getMonthCount(UtilNumber utilDate) {
		return getMonthCount(utilDate.getTime());
	}

	public int getMonthCount(Date date) {
		Calendar calDate = Calendar.getInstance(new Locale("tr"));
		calDate.setTime(date);

		int count = (calDate.get(Calendar.YEAR) - cal.get(Calendar.YEAR))*12;
		count = count + calDate.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
		//if (calDate.get(Calendar.DATE) > cal.get(Calendar.DATE)) {
		//	count = count + 1;
		//}
		return count;
	}
	
	public int get(int field) {
		return cal.get(field);
	}
	
	public Date add(int field, int amount) {
		cal.add(field, amount);
		return cal.getTime();
	}

	public Date set(int field, int amount) {
		int month = cal.get(Calendar.MONTH);
		cal.set(field, amount);
		if (field==Calendar.DATE) { // ayın son günlerindeki sıkıntıyı gidermek için
			if (month < cal.get(Calendar.MONTH)) {
				cal.set(Calendar.DATE, 1);
				cal.add(Calendar.MONTH, -1);
			}
		}
		return cal.getTime();
	}

	public Date set(int year, int month, int date) {
		cal.set(year, month, date);
		return cal.getTime();
	}

	@Override
	public String toString() {
		if(cal==null) {
			return "~";
		} else {
			Format dateFormat = new SimpleDateFormat("dd.MMM.yyyy", new Locale("tr"));
			return dateFormat.format(cal.getTime());			
		}
	}

	public String toString(String pattern) {
		if(cal==null) {
			return "~";
		} else {
			Format dateFormat = new SimpleDateFormat(pattern, new Locale("tr"));
			return dateFormat.format(cal.getTime());			
		}
	}
	
	public boolean before(UtilNumber utildate) {
		return cal.before(utildate);
	}

	public boolean after(UtilNumber utildate) {
		return cal.after(utildate);
	}

	public int compareTo(UtilNumber utildate) {
		return cal.compareTo(utildate.cal);
	}
	
	public int compareTo(String str, String pattern) {
		Format dateFormat = new SimpleDateFormat(pattern, new Locale("tr"));
		Integer intCal = new Integer(dateFormat.format(cal.getTime()));
		Integer intStr = new Integer(str);
		return intCal.compareTo(intStr);
	}

}
