package com.qrpos.model.json.view;

import java.io.Serializable;
import java.math.BigDecimal;

import com.qrpos.model.entity.KartDurum;

public class KartJsonView implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long kartId;

	private Long uyeId;

	private String uyeAdiSoyadi;

	private BigDecimal bakiye;

	private BigDecimal depozito;

	private BigDecimal bonus;

	private BigDecimal kredi;

	private String kartStatus;

	public KartJsonView(KartDurum kartDurum) {
		super();
		this.kartId = kartDurum.getKart().getId();

		this.uyeId = kartDurum.getKart().getUye().getId();

		this.uyeAdiSoyadi = kartDurum.getKart().getUye().getAd() + " " + kartDurum.getKart().getUye().getSoyad();

		this.bakiye = kartDurum.getKart().getBakiye();

		this.depozito = kartDurum.getKart().getDepozito();

		this.bonus = kartDurum.getKart().getBonus();

		this.kredi = kartDurum.getKart().getKredi();

		this.kartStatus = kartDurum.getStatus().name();
	}

	public Long getKartId() {
		return kartId;
	}

	public void setKartId(Long id) {
		this.kartId = id;
	}

	public String getUyeAdiSoyadi() {
		return uyeAdiSoyadi;
	}

	public void setUyeAdiSoyadi(String uyeAdiSoyadi) {
		this.uyeAdiSoyadi = uyeAdiSoyadi;
	}

	public BigDecimal getBakiye() {
		return bakiye;
	}

	public void setBakiye(BigDecimal bakiye) {
		this.bakiye = bakiye;
	}

	public BigDecimal getDepozito() {
		return depozito;
	}

	public void setDepozito(BigDecimal depozito) {
		this.depozito = depozito;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getKredi() {
		return kredi;
	}

	public void setKredi(BigDecimal kredi) {
		this.kredi = kredi;
	}

	public String getKartStatus() {
		return kartStatus;
	}

	public void setKartStatus(String kartStatus) {
		this.kartStatus = kartStatus;
	}

	public Long getUyeId() {
		return uyeId;
	}

	public void setUyeId(Long uyeId) {
		this.uyeId = uyeId;
	}

}
