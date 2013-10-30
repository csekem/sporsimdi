package com.sporsimdi.model.type;

public enum GrupCinsiyet {
	ERKEK("grupCinsiyet.erkek"), KIZ("grupCinsiyet.kiz"),KARMA("grupCinsiyet.karma") ;

	private String i18nKey;

	private GrupCinsiyet(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
