package com.sporsimdi.action.util;

import java.util.Calendar;

import com.sporsimdi.action.util.spi.SSSchedulerBase;

public class SSSchedulerDay extends SSSchedulerBase {

	private static final long serialVersionUID = 8408817005002144923L;

	@Override
	public UtilDate getDateBegin(int index) {
		UtilDate retDate = new UtilDate(getDateList().get(index).getTime());
		retDate.add(Calendar.DATE, -1);
		retDate.setTimeOnly(23, 59, 59);
		return retDate;
	}

	@Override
	public UtilDate getDateEnd(int index) {
		UtilDate retDate = new UtilDate(getDateList().get(index).getTime());
		retDate.setTimeOnly(23, 59, 59);
		return retDate;
	}

	@Override
	public String getDisplayName(int index) {
		return getDateList().get(index).toString("dd MMM YYYY");
	}

	@Override
	protected void setBaseName() {
		baseName = "GÜNLÜK";
	}

	@Override
	public void addFirstDate() {
		UtilDate aDate = new UtilDate(getDateList().get(0).getTime());
		int ind = aDate.get(Calendar.DATE);
		for (int i = 1; i < ind; i++) {
			UtilDate firstDate = new UtilDate(aDate.add(Calendar.DATE, -1));
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
		aDate.add(Calendar.DATE, 1);
		getDateList().add(aDate);
		controlDateLimits();
	}

	@Override
	public void addLastDate() {
		UtilDate aDate = new UtilDate(getDateList().get(getDateList().size() - 1).getTime());
		int ind = aDate.get(Calendar.DATE);

		UtilDate lastDate = new UtilDate(aDate.getTime());
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.DATE, -1);
		int lastInd = lastDate.get(Calendar.DATE);
		for (int i = ind; i < lastInd; i++) {
			getDateList().add(new UtilDate(aDate.add(Calendar.DATE, 1)));
		}
		controlDateLimits();
	}

	@Override
	public void controlDateLimits() {
		int firstInd = getDateList().get(0).get(Calendar.DATE);

		UtilDate lastDay = new UtilDate(getDateList().get(getDateList().size() - 1).getTime());
		lastDay.add(Calendar.DATE, 1);
		int lastInd = lastDay.get(Calendar.DATE);

		if (firstInd == 1) {
			setDateAtBegin(true);
		}
		if (lastInd == 1) {
			setDateAtEnd(true);
		}
	}

}
