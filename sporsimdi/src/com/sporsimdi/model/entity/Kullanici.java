package com.sporsimdi.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Kullanici extends ExtendedModel {

	private static final long serialVersionUID = 8426751709187472621L;

	private String kullanici;
	
	private String sifre;
	
	@Column(columnDefinition = "number(1)")
	private boolean blokeli;
	
	@OneToOne
	private Kisi kisi;

	public Kisi getKisi() {
		return kisi;
	}

	public void setKisi(Kisi kisi) {
		this.kisi = kisi;
	}

	public String getKullanici() {
		return kullanici;
	}

	public void setKullanici(String kullanici) {
		this.kullanici = kullanici;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public boolean isBlokeli() {
		return blokeli;
	}

	public void setBlokeli(boolean blokeli) {
		this.blokeli = blokeli;
	}
}
