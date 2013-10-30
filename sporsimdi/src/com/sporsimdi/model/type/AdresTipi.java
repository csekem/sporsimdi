package com.sporsimdi.model.type;

public enum AdresTipi {
	EV("adresTipi.ev"), IS("adresTipi.is"), DIGER("adresTipi.diger") ;

	private String i18nKey;

	private AdresTipi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
