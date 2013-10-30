package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Tesis extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Isletme> isletmeListesi = new ArrayList<Isletme>();

	private String adi;

	@ManyToOne(fetch = FetchType.LAZY)
	private TesisTipi tesisTipi;

	private BigDecimal yuzOlcumM2;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tesis")
	private List<Saha> sahaListesi = new ArrayList<Saha>();

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public TesisTipi getTesisTipi() {
		return tesisTipi;
	}

	public void setTesisTipi(TesisTipi tesisTipi) {
		this.tesisTipi = tesisTipi;
	}

	public BigDecimal getYuzOlcumM2() {
		return yuzOlcumM2;
	}

	public void setYuzOlcumM2(BigDecimal yuzOlcumM2) {
		this.yuzOlcumM2 = yuzOlcumM2;
	}

	public List<Saha> getSahaListesi() {
		return sahaListesi;
	}

	public void setSahaListesi(List<Saha> sahaListesi) {
		this.sahaListesi = sahaListesi;
	}

	public List<Isletme> getIsletmeListesi() {
		return isletmeListesi;
	}

	public void setIsletmeListesi(List<Isletme> isletmeListesi) {
		this.isletmeListesi = isletmeListesi;
	}

	public Saha[] getListeliSahaListesi() {
		return sahaListesi.toArray(new Saha[sahaListesi.size()]);
	}

}
