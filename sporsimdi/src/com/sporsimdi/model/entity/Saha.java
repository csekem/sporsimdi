package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.AcikKapali;

@Table
@Entity
public class Saha extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Tesis tesis;

	private String adi;

	@ManyToMany(fetch=FetchType.LAZY)
	private List<SahaTipi> sahaTipiListesi = new ArrayList<SahaTipi>();

	@Enumerated(EnumType.STRING)
	private AcikKapali acikKapali;

	private BigDecimal yuzOlcumM2;
	
	private BigDecimal en;
	
	private BigDecimal boy;
	
	private BigDecimal yukseklik;
	
	private String zemin;

	public BigDecimal getEn() {
		return en;
	}

	public void setEn(BigDecimal en) {
		this.en = en;
	}

	public BigDecimal getBoy() {
		return boy;
	}

	public void setBoy(BigDecimal boy) {
		this.boy = boy;
	}

	public String getZemin() {
		return zemin;
	}

	public void setZemin(String zemin) {
		this.zemin = zemin;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}


	public AcikKapali getAcikKapali() {
		return acikKapali;
	}

	public void setAcikKapali(AcikKapali acikKapali) {
		this.acikKapali = acikKapali;
	}

	public BigDecimal getYuzOlcumM2() {
		return yuzOlcumM2;
	}

	public void setYuzOlcumM2(BigDecimal yuzOlcumM2) {
		this.yuzOlcumM2 = yuzOlcumM2;
	}

	public Tesis getTesis() {
		return tesis;
	}

	public void setTesis(Tesis tesis) {
		this.tesis = tesis;
	}

	public List<SahaTipi> getSahaTipiListesi() {
		return sahaTipiListesi;
	}

	public void setSahaTipiListesi(List<SahaTipi> sahaTipiListesi) {
		this.sahaTipiListesi = sahaTipiListesi;
	}

	public BigDecimal getYukseklik() {
		return yukseklik;
	}

	public void setYukseklik(BigDecimal yukseklik) {
		this.yukseklik = yukseklik;
	}

	@Override
	public String toString() {
		return super.toString(); 
	}

}
