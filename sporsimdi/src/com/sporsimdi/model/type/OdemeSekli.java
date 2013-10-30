package com.sporsimdi.model.type;

public enum OdemeSekli {
	NAKIT("odemeSekli.nakit"), KREDI_KARTI("odemeSekli.krediKarti"), CEK("odemeSekli.cek"), SENET("odemeSekli.senet");

	private String i18nKey;

	private OdemeSekli(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
