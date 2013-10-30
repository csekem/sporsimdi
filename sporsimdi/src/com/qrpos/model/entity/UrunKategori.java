package com.qrpos.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Kasa;

@Table
@Entity
public class UrunKategori extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kasa kasa;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Kasa getKasa() {
		return kasa;
	}

	public void setKasa(Kasa kasa) {
		this.kasa = kasa;
	}

}
