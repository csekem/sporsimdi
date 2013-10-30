package com.sporsimdi.model.type;

public enum TelefonTipi {
	CEP("telefonTipi.cep"), 
	EV("telefonTipi.ev"), 
	IS("telefonTipi.is"), 
	FAKS("telefonTipi.faks"),
	DIGER("telefonTipi.diger");

	private String i18nKey;

	private TelefonTipi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
