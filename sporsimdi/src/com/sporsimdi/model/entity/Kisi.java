package com.sporsimdi.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.Cinsiyet;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "KISI_TIPI", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("KISI")
public class Kisi extends ExtendedModel {

	private static final long serialVersionUID = -7154420595137410398L;

	private int tcKimlikNo;

	private String ad;

	private String soyad;

	@Transient
	private String adSoyad;

	private Cinsiyet cinsiyet;

	@OneToOne(fetch = FetchType.LAZY)
	private Meslek meslek;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dogumTarihi;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Isletme> isletmeListesi = new ArrayList<Isletme>();

	public List<Isletme> getIsletmeListesi() {
		return isletmeListesi;
	}

	public void setIsletmeListesi(List<Isletme> isletmeListesi) {
		this.isletmeListesi = isletmeListesi;
	}

	public int getTcKimlikNo() {
		return tcKimlikNo;
	}

	public Date getDogumTarihi() {
		return dogumTarihi;
	}

	public void setDogumTarihi(Date dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}

	public void setTcKimlikNo(int tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getAdSoyad() {
		return ad + " " + soyad;
	}

	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}

	public Cinsiyet getCinsiyet() {
		return cinsiyet;
	}

	public void setCinsiyet(Cinsiyet cinsiyet) {
		this.cinsiyet = cinsiyet;
	}

	public Meslek getMeslek() {
		return meslek;
	}

	public void setMeslek(Meslek meslek) {
		this.meslek = meslek;
	}

	@Override
	public String toString() {
		return "Kisi [" + getAdSoyad() + "]";
	}

}
