package com.sporsimdi.model.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Kalem extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;
	
	@ManyToOne (fetch=FetchType.LAZY)
	private Isletme isletme;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Isletme getIsletme() {
		return isletme;
	}

	public void setIsletme(Isletme isletme) {
		this.isletme = isletme;
	}

}
