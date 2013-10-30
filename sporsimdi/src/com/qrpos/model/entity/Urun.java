package com.qrpos.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Kasa;

@Table
@Entity
public class Urun extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@ManyToOne(fetch = FetchType.LAZY)
	private UrunKategori urunKategori;

	private BigDecimal satisFiyat;
	private BigDecimal kdvOrani;

	public BigDecimal getKdvOrani() {
		return kdvOrani;
	}

	public void setKdvOrani(BigDecimal kdvOrani) {
		this.kdvOrani = kdvOrani;
	}

	private String resim;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	private int sira;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kasa kasa;

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public UrunKategori getUrunKategori() {
		return urunKategori;
	}

	public void setUrunKategori(UrunKategori urunKategori) {
		this.urunKategori = urunKategori;
	}

	public BigDecimal getSatisFiyat() {
		return satisFiyat;
	}

	public void setSatisFiyat(BigDecimal satisFiyat) {
		this.satisFiyat = satisFiyat;
	}

	public Kasa getKasa() {
		return kasa;
	}

	public void setKasa(Kasa kasa) {
		this.kasa = kasa;
	}

	public String getResim() {
		return resim;
	}

	public void setResim(String resim) {
		this.resim = resim;
	}

	public int getSira() {
		return sira;
	}

	public void setSira(int sira) {
		this.sira = sira;
	}

	@Override
	public String toString() {
		return getAdi();
	}

	@Override
	public Urun clone() {
		Urun cloneUrun = null;
		try {
			cloneUrun = (Urun) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloneUrun.setUrunKategori(null);
		cloneUrun.setKasa(null);
		return cloneUrun;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
