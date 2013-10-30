package com.sporsimdi.model.type;

public enum Cinsiyet {
	ERKEK("cinsiyet.erkek"), KIZ("cinsiyet.kiz") ;

	private String i18nKey;

	private Cinsiyet(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
