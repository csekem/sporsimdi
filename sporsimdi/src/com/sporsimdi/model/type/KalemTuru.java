package com.sporsimdi.model.type;

public enum KalemTuru {
	AIDAT("Aidat"), KART("Kart"), BONUS("Bonus"), DEPOZITO("Depozito");

	private String i18nKey;

	private KalemTuru(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}
}
