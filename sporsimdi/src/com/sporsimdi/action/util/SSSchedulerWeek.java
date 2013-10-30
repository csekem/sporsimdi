package com.sporsimdi.action.util;

import java.util.Calendar;

import com.sporsimdi.action.util.spi.SSSchedulerBase;

public class SSSchedulerWeek extends SSSchedulerBase {

	private static final long serialVersionUID = 8408817005002144923L;

	@Override
	public UtilDate getDateBegin(int index) {
		UtilDate retDate = new UtilDate(getDateList().get(index).set(Calendar.MONTH, 0));
		retDate = new UtilDate(retDate.set(Calendar.DATE, 0));
		retDate.setTimeOnly(0, 0, 0);
		return retDate;
	}

	@Override
	public UtilDate getDateEnd(int index) {
		UtilDate retDate = new UtilDate(getDateList().get(index).set(Calendar.MONTH, 11));
		retDate = new UtilDate(retDate.set(Calendar.DATE, 30));
		retDate.setTimeOnly(23, 59, 59);
		return retDate;
	}

	@Override
	public String getDisplayName(int index) {
		return getDateList().get(index).toString("WWWW YYYY");
	}

	@Override
	protected void setBaseName() {
		baseName = "HAFTALIK";
	}

	@Override
	public void addFirstDate() {
		UtilDate aDate = new UtilDate(getDateList().get(0).getTime());
		int ind = aDate.get(Calendar.MONTH);
		for (int i = 0; i < ind; i++) {
			UtilDate firstDate = new UtilDate(aDate.add(Calendar.MONTH, -1));
			getDateList().add(0, firstDate);
		}
		controlDateLimits();
	}

	@Override
	public void addPrevDate() {
		UtilDate aDate = new UtilDate(getDateList().get(0).getTime());
		aDate.add(Calendar.DATE, -1);
		getDateList().add(0, aDate);
		controlDateLimits();
	}

	@Override
	public void addNextDate() {
		UtilDate aDate = new UtilDate(getDateList().get(getDateList().size() - 1).getTime());
		aDate.add(Calendar.MONTH, 1);
		getDateList().add(aDate);
		controlDateLimits();
	}

	@Override
	public void addLastDate() {
		UtilDate aDate = new UtilDate(getDateList().get(getDateList().size() - 1).getTime());
		int ind = aDate.get(Calendar.MONTH);
		for (int i = ind; i < 12; i++) {
			UtilDate lastDate = new UtilDate(aDate.add(Calendar.MONTH, 1));
			getDateList().add(lastDate);
		}
		controlDateLimits();
	}

	@Override
	public void controlDateLimits() {
		int firstInd = getDateList().get(0).get(Calendar.MONTH);
		int lastInd = getDateList().get(getDateList().size() - 1).get(Calendar.MONTH);

		if (firstInd == 0) {
			setDateAtBegin(true);
		}
		if (lastInd == 11) {
			setDateAtEnd(true);
		}
	}

}
