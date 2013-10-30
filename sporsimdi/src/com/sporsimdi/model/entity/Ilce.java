package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Ilce extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Il il;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Il getIl() {
		return il;
	}

	public void setIl(Il il) {
		this.il = il;
	}

}
