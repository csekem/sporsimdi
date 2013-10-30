package com.qrpos.model.json.view;

import java.io.Serializable;
import java.math.BigDecimal;

import com.qrpos.model.entity.Urun;

public class UrunJsonView implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long id;

	private String adi;

	private BigDecimal satisFiyat;

	private int sira;

	public UrunJsonView() {
		super();
	}

	public UrunJsonView(Urun urun) {
		super();
		this.id = urun.getId();
		this.adi = urun.getAdi();
		this.satisFiyat = urun.getSatisFiyat();
		this.sira = urun.getSira();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public BigDecimal getSatisFiyat() {
		return satisFiyat;
	}

	public void setSatisFiyat(BigDecimal satisFiyat) {
		this.satisFiyat = satisFiyat;
	}

	public int getSira() {
		return sira;
	}

	public void setSira(int sira) {
		this.sira = sira;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
