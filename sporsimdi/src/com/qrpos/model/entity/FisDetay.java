package com.qrpos.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class FisDetay extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Fis fis;
	@ManyToOne(fetch = FetchType.LAZY)
	private Urun urun;

	private BigDecimal satisFiyat;

	private BigDecimal adet;

	private BigDecimal kdvOran;

	private BigDecimal kdvTutar;

	private BigDecimal indirimOran;

	private BigDecimal indirimTutar;

	private BigDecimal satisTutar;

	public Fis getFis() {
		return fis;
	}

	public void setFis(Fis fis) {
		this.fis = fis;
	}

	public Urun getUrun() {
		return urun;
	}

	public void setUrun(Urun urun) {
		this.urun = urun;
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

	public BigDecimal getKdvOran() {
		return kdvOran;
	}

	public void setKdvOran(BigDecimal kdvOran) {
		this.kdvOran = kdvOran;
	}

	public BigDecimal getKdvTutar() {
		return kdvTutar;
	}

	public void setKdvTutar(BigDecimal kdvTutar) {
		this.kdvTutar = kdvTutar;
	}

	public BigDecimal getIndirimOran() {
		return indirimOran;
	}

	public void setIndirimOran(BigDecimal indirimOran) {
		this.indirimOran = indirimOran;
	}

	public BigDecimal getIndirimTutar() {
		return indirimTutar;
	}

	public void setIndirimTutar(BigDecimal indirimTutar) {
		this.indirimTutar = indirimTutar;
	}

	public BigDecimal getSatisTutar() {
		return satisTutar;
	}

	public void setSatisTutar(BigDecimal satisTutar) {
		this.satisTutar = satisTutar;
	}

}
