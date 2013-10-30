package com.qrpos.model.type;

public enum KartStatus {
	YENI("kartStatus.yeni"), ACIK("kartStatus.acik"), KAPALI("kartStatus.kapali"), BOZUK("kartStatus.bozuk"), KAYIP("kartStatus.kayip"), PASIF(
			"kartStatus.pasif");

	private String i18nKey;

	private KartStatus(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getI18nKey() {
		return i18nKey;
	}

}
