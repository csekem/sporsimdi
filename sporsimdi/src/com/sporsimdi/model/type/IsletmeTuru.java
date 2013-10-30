package com.sporsimdi.model.type;

public enum IsletmeTuru {
	SPOR_OKULU("isletmeTuru.spor_okulu"), 
	SPOR_SALONU("isletmeTuru.spor_salonu"),
	HALI_SAHA("isletmeTuru.hali_saha"),
	YUZME_HAVUZU("isletmeTuru.yuzme_havuzu"),
	TENIS_KORTU("isletmeTuru.tenis_kortu")
	;

	private String i18nKey;

	private IsletmeTuru(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}
}
