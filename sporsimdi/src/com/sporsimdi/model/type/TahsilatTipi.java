package com.sporsimdi.model.type;

public enum TahsilatTipi {
	TAHSILAT("tahsilatTipi.tahsilat"), 
	INDIRIM("tahsilatTipi.indirim"), 
	IPTAL("tahsilatTipi.tahakkukIptal") ;

	private String i18nKey;

	private TahsilatTipi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
