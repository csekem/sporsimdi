package com.sporsimdi.action.util.spi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sporsimdi.action.util.UtilDate;

public abstract class SSSchedulerBase implements Serializable {

	private static final long serialVersionUID = 6241171977842416805L;

	private List<UtilDate> dateList = new ArrayList<UtilDate>();

	protected String baseName;

	private boolean dateAtBegin = false;

	private boolean dateAtEnd = false;

	public SSSchedulerBase() {
		getDateList().add(new UtilDate());
		setBaseName();
		controlDateLimits();
	}

	public SSSchedulerBase(UtilDate date) {
		getDateList().add(date);
	}

	public List<UtilDate> getDateList() {
		return dateList;
	}

	public void setDateList(List<UtilDate> dateList) {
		this.dateList = dateList;
	}

	public String getBaseName() {
		return baseName;
	}

	public boolean isDateAtBegin() {
		return dateAtBegin;
	}

	public void setDateAtBegin(boolean dateAtBegin) {
		this.dateAtBegin = dateAtBegin;
	}

	public boolean isDateAtEnd() {
		return dateAtEnd;
	}

	public void setDateAtEnd(boolean dateAtEnd) {
		this.dateAtEnd = dateAtEnd;
	}

	protected abstract void setBaseName();

	public abstract String getDisplayName(int index);

	public abstract UtilDate getDateBegin(int index);

	public abstract UtilDate getDateEnd(int index);

	public abstract void addFirstDate();

	public abstract void addPrevDate();

	public abstract void addNextDate();

	public abstract void addLastDate();

	public abstract void controlDateLimits();
}
