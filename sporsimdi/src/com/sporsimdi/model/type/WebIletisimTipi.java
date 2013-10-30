package com.sporsimdi.model.type;

public enum WebIletisimTipi {
	E_MAIL("webIletisimTipi.e_mail"), 
	FACEBOOK("webIletisimTipi.facebook"), 
	TWITTER("webIletisimTipi.twitter"), 
	PINTEREST("webIletisimTipi.pinterest"), 
	LINKEDIN("webIletisimTipi.linkedin"), 
	WEB_SAYFASI("webIletisimTipi.web_sayfasi"),
	DIGER("webIletisimTipi.diger");

	private String i18nKey;

	private WebIletisimTipi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
