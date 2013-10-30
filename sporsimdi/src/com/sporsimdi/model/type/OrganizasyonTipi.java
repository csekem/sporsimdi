package com.sporsimdi.model.type;

public enum OrganizasyonTipi {
	BASKETBOL_OKULU("organizasyonTipi.basketbol_okulu"), 
	FITNESS_SALONU("organizasyonTipi.fitness_salonu"), 
	FUTBOL_OKULU("organizasyonTipi.futbol_okulu"), 
	HALI_SAHA("isletmeTuru.hali_saha"),
	JIMNASTIK_OKULU("organizasyonTipi.jimnastik_okulu"), 
	KAMP("organizasyonTipi.kamp"), 
	SPOR_SALONU("isletmeTuru.spor_salonu"),
	TENIS_KORTU("isletmeTuru.tenis_kortu"),
	TENIS_OKULU("organizasyonTipi.tenis_okulu"), 
	VOLEYBOL_OKULU("organizasyonTipi.voleybol_okulu"), 
	YAZ_OKULU("organizasyonTipi.yaz_okulu"), 
	YUZME_HAVUZU("isletmeTuru.yuzme_havuzu"),
	RESTORAN("isletmeTuru.restoran")
	;

	private String i18nKey;

	private OrganizasyonTipi(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}
}
