package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Semt extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;
	
	private String postaKodu;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Ilce ilce;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Ilce getIlce() {
		return ilce;
	}

	public void setIlce(Ilce ilce) {
		this.ilce = ilce;
	}

	public String getPostaKodu() {
		return postaKodu;
	}

	public void setPostaKodu(String postaKodu) {
		this.postaKodu = postaKodu;
	}

}
