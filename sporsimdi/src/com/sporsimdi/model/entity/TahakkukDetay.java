package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class TahakkukDetay extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Tahakkuk tahakkuk;

	@Column(columnDefinition = "number(1)")
	private boolean herAy;

	private long taksitNo;

	private BigDecimal taksitTutari;

	@Temporal(TemporalType.DATE)
	private Date baslangicTarihi;

	@Temporal(TemporalType.DATE)
	private Date bitisTarihi;

	private BigDecimal herAyTutar;

	private BigDecimal kalanTutar;

	@Temporal(TemporalType.DATE)
	private Date vadeTarihi;

	@Transient
	private BigDecimal tutar;

	public Tahakkuk getTahakkuk() {
		return tahakkuk;
	}

	public void setTahakkuk(Tahakkuk tahakkuk) {
		this.tahakkuk = tahakkuk;
	}

	public long getTaksitNo() {
		return taksitNo;
	}

	public void setTaksitNo(long taksitNo) {
		this.taksitNo = taksitNo;
	}

	public Date getVadeTarihi() {
		return vadeTarihi;
	}

	public void setVadeTarihi(Date vadeTarihi) {
		this.vadeTarihi = vadeTarihi;
	}

	public BigDecimal getTaksitTutari() {
		return taksitTutari;
	}

	public void setTaksitTutari(BigDecimal taksitTutari) {
		this.taksitTutari = taksitTutari;
	}

	public boolean isHerAy() {
		return herAy;
	}

	public void setHerAy(boolean herAy) {
		this.herAy = herAy;
	}

	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}

	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}

	public Date getBitisTarihi() {
		return bitisTarihi;
	}

	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

	public BigDecimal getHerAyTutar() {
		return herAyTutar;
	}

	public void setHerAyTutar(BigDecimal herAyTutar) {
		this.herAyTutar = herAyTutar;
	}

	public BigDecimal getKalanTutar() {
		return kalanTutar;
	}

	public void setKalanTutar(BigDecimal kalanTutar) {
		this.kalanTutar = kalanTutar;
	}

	public BigDecimal getTutar() {
		if (herAy) {
			return herAyTutar;
		} else {
			return taksitTutari;
		}
	}

	public void setTutar(BigDecimal tutar) {
		this.tutar = tutar;
	}

	public TahakkukDetay clone() {
		TahakkukDetay newDty = new TahakkukDetay();
		newDty.setTahakkuk(getTahakkuk());
		newDty.setHerAy(isHerAy());
		newDty.setTaksitNo(getTaksitNo());
		newDty.setTaksitTutari(getTaksitTutari());
		newDty.setBaslangicTarihi(getBaslangicTarihi());
		newDty.setBitisTarihi(getBitisTarihi());
		newDty.setHerAyTutar(getHerAyTutar());
		newDty.setKalanTutar(getKalanTutar());
		newDty.setVadeTarihi(getVadeTarihi());
		return newDty;
	}

}
