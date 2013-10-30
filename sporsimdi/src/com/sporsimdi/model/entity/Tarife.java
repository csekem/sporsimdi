package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Tarife extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@Column(columnDefinition = "number(1)")
	private boolean herAy;
	
	private BigDecimal herAyTutar;

	private long taksitSayisi;

	private BigDecimal toplamTutar;

	private int aidatGun;

	@ManyToOne(fetch = FetchType.LAZY)
	private Grup grup;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="tarife",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("taksitNo")
	private List<TarifeDetay> tarifeDetayListesi = new ArrayList<TarifeDetay>();

	public long getTaksitSayisi() {
		return taksitSayisi;
	}

	public void setTaksitSayisi(long taksitSayisi) {
		this.taksitSayisi = taksitSayisi;
	}

	public BigDecimal getToplamTutar() {
		return toplamTutar;			
	}

	public void setToplamTutar(BigDecimal toplamTutar) {
		this.toplamTutar = toplamTutar;
	}

	public boolean isHerAy() {
		return herAy;
	}

	public void setHerAy(boolean herAy) {
		this.herAy = herAy;
	}

	public BigDecimal getHerAyTutar() {
		return herAyTutar;
	}

	public void setHerAyTutar(BigDecimal herAyTutar) {
		this.herAyTutar = herAyTutar;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	public int getAidatGun() {
		return aidatGun;
	}

	public void setAidatGun(int aidatGun) {
		this.aidatGun = aidatGun;
	}

	public List<TarifeDetay> getTarifeDetayListesi() {
		return tarifeDetayListesi;
	}

	public void setTarifeDetayListesi(List<TarifeDetay> tarifeDetayListesi) {
		this.tarifeDetayListesi = tarifeDetayListesi;
	}

	@Override
	public String toString() {
		if (getId()==null) {
			return "";
		}
		NumberFormat nm = DecimalFormat.getInstance(new Locale("tr"));		
		if (isHerAy()) {
			return getAdi() + " [" + "HER AY " + nm.format(getHerAyTutar()) + "]";
		} else {
			return getAdi() + " [" + taksitSayisi + " TAKSİT, TOPLAM " + nm.format(getToplamTutar()) + "]";
		}
	}

}
