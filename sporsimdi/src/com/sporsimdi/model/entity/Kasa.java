package com.sporsimdi.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Kasa extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@ManyToOne(fetch=FetchType.EAGER)
	private OrgTesis orgTesis;
	
	@Column(columnDefinition = "number(1)")
	private boolean anaKasa;
	
	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public OrgTesis getOrgTesis() {
		return orgTesis;
	}

	public void setOrgTesis(OrgTesis orgTesis) {
		this.orgTesis = orgTesis;
	}

	public boolean isAnaKasa() {
		return anaKasa;
	}

	public void setAnaKasa(boolean anaKasa) {
		this.anaKasa = anaKasa;
	}

	@Override
	public String toString() {
		return "Kasa [" + getAdi() + "]";
	}

}
