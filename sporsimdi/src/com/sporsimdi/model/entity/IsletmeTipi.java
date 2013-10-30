package com.sporsimdi.model.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class IsletmeTipi extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	private String tipi;

	public String getTipi() {
		return tipi;
	}

	public void setTipi(String tipi) {
		this.tipi = tipi;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

}
