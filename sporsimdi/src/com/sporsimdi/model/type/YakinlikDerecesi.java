package com.sporsimdi.model.type;

public enum YakinlikDerecesi {
	ANNE("yakinlikDerecesi.anne"), BABA("yakinlikDerecesi.baba"), 
	VELI("yakinlikDerecesi.veli"), OGRETMEN("yakinlikDerecesi.ogretmen"), 
	ARKADAS("yakinlikDerecesi.arkadas"), DIGER("yakinlikDerecesi.diger") ;

	private String i18nKey;

	private YakinlikDerecesi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
