package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Defter extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kasa kasa;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tarih;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kullanici kullanici;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Kalem kalem;
	
	private BigDecimal gider;
	
	private BigDecimal gelir;
	
	private BigDecimal bakiye;
	
	@Lob
	private ExtendedModel referenceModel;
	
	private Long referenceId;

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

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Kalem getKalem() {
		return kalem;
	}

	public void setKalem(Kalem kalem) {
		this.kalem = kalem;
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

	public ExtendedModel getReferenceModel() {
		return referenceModel;
	}

	public void setReferenceModel(ExtendedModel referenceModel) {
		this.referenceModel = referenceModel;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	
}
