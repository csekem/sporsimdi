package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Bakiye extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kasa kasa;
	
	@Temporal(TemporalType.DATE)
	private Date tarih;

	private BigDecimal gider;
	
	private BigDecimal gelir;
	
	private BigDecimal bakiye;
	
	public Kasa getKasa() {
		return kasa;
	}

	public void setKasa(Kasa kasa) {
		this.kasa = kasa;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public BigDecimal getGider() {
		return gider;
	}

	public void setGider(BigDecimal gider) {
		this.gider = gider;
	}

	public BigDecimal getGelir() {
		return gelir;
	}

	public void setGelir(BigDecimal gelir) {
		this.gelir = gelir;
	}

	public BigDecimal getBakiye() {
		return bakiye;
	}

	public void setBakiye(BigDecimal bakiye) {
		this.bakiye = bakiye;
	}
	
}
