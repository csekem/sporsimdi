package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class SahaTipi extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

}
