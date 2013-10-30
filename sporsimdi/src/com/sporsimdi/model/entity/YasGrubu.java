package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class YasGrubu extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	private int baslangicYili;

	private int bitisYili;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Isletme isletme;

	public int getBaslangicYili() {
		return baslangicYili;
	}

	public void setBaslangicYili(int baslangicYili) {
		this.baslangicYili = baslangicYili;
	}

	public int getBitisYili() {
		return bitisYili;
	}

	public void setBitisYili(int bitisYili) {
		this.bitisYili = bitisYili;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Isletme getIsletme() {
		return isletme;
	}

	public void setIsletme(Isletme isletme) {
		this.isletme = isletme;
	}

	@Override
	public String toString() {
		return adi +" [ " + Integer.toString(baslangicYili) + " - " + Integer.toString(bitisYili) + " ]";
	}
}
