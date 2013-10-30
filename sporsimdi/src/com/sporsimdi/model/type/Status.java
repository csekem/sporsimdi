package com.sporsimdi.model.type;

public enum Status {
	ACTIVE("status.active"), PASSIVE("status.passive");

	private String i18nKey;

	private Status(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
