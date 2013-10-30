package com.sporsimdi.model.type;

public enum AcikKapali {
	ACIK("acikKapali.acik"), 
	KAPALI("acikKapali.kapali"), 
	YARI_KAPALI ("acikKapali.yariKapali"),
	ACILIR_KAPANIR("acikKapali.acilirKapanir");

	private String i18nKey;

	private AcikKapali(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
