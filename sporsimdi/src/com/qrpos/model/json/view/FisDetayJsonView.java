package com.qrpos.model.json.view;

import java.io.Serializable;
import java.math.BigDecimal;

import com.qrpos.model.entity.FisDetay;

public class FisDetayJsonView implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long id;

	private String urunAdi;

	private Long urunId;

	private BigDecimal satisFiyat;

	private BigDecimal adet;

	public FisDetayJsonView(FisDetay fisDetay) {
		super();
		this.id = fisDetay.getId();
		this.urunId = fisDetay.getUrun().getId();
		this.urunAdi = fisDetay.getUrun().getAdi();
		this.satisFiyat = fisDetay.getSatisFiyat();
		this.adet = fisDetay.getAdet();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrunAdi() {
		return urunAdi;
	}

	public void setUrunAdi(String urunAdi) {
		this.urunAdi = urunAdi;
	}

	public Long getUrunId() {
		return urunId;
	}

	public void setUrunId(Long urunId) {
		this.urunId = urunId;
	}

	public BigDecimal getSatisFiyat() {
		return satisFiyat;
	}

	public void setSatisFiyat(BigDecimal satisFiyat) {
		this.satisFiyat = satisFiyat;
	}

	public BigDecimal getAdet() {
		return adet;
	}

	public void setAdet(BigDecimal adet) {
		this.adet = adet;
	}

}
