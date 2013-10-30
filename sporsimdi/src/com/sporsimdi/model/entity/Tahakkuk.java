package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class Tahakkuk extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tahakkuk",cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderBy("vadeTarihi")
	private List<TahakkukDetay> tahakkukDetayListesi = new ArrayList<TahakkukDetay>();

	@ManyToOne(fetch = FetchType.LAZY)
	private UyeGrup uyeGrup;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tarife tarife;

	private long taksitSayisi;

	private BigDecimal toplamTutar;

	private BigDecimal toplamIndirim;

	@Column(columnDefinition = "number(1)")
	private boolean herAy;

	private BigDecimal herAyTutar;

	@Column(columnDefinition = "number(1)")
	private boolean acikVade;

	private int aidatGun;

	public UyeGrup getUyeGrup() {
		return uyeGrup;
	}

	public void setUyeGrup(UyeGrup uyeGrup) {
		this.uyeGrup = uyeGrup;
	}

	public BigDecimal getHerAyTutar() {
		return herAyTutar;
	}

	public void setHerAyTutar(BigDecimal herAyTutar) {
		this.herAyTutar = herAyTutar;
	}

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


	public List<TahakkukDetay> getTahakkukDetayListesi() {
		return tahakkukDetayListesi;
	}

	public void setTahakkukDetayListesi(List<TahakkukDetay> tahakkukDetayListesi) {
		this.tahakkukDetayListesi = tahakkukDetayListesi;
	}

	public TahakkukDetay[] getListeliTahakkukDetayListesi() {
		return tahakkukDetayListesi
				.toArray(new TahakkukDetay[tahakkukDetayListesi.size()]);
	}

	public Tarife getTarife() {
		return tarife;
	}

	public void setTarife(Tarife tarife) {
		this.tarife = tarife;
	}

	public BigDecimal getToplamIndirim() {
		return toplamIndirim;
	}

	public void setToplamIndirim(BigDecimal toplamIndirim) {
		this.toplamIndirim = toplamIndirim;
	}

	public boolean isAcikVade() {
		return acikVade;
	}

	public void setAcikVade(boolean acikVade) {
		this.acikVade = acikVade;
	}

	public boolean isHerAy() {
		return herAy;
	}

	public void setHerAy(boolean herAy) {
		this.herAy = herAy;
	}

	public int getAidatGun() {
		return aidatGun;
	}

	public void setAidatGun(int aidatGun) {
		this.aidatGun = aidatGun;
	}
}
