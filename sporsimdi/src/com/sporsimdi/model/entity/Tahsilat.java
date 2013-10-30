package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.OdemeSekli;
import com.sporsimdi.model.type.TahsilatTipi;

@Table
@Entity
public class Tahsilat extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY)
	private TahakkukDetay tahakkukDetay;

	@Enumerated(EnumType.STRING)
	private TahsilatTipi tahsilatTipi;

	@Temporal(TemporalType.DATE)
	private Date tahsilTarihi;

	private BigDecimal tahsilTutari;

	private BigDecimal indirimTutari;

	@OneToOne(fetch=FetchType.EAGER)
	private Kisi odeyenKisi;
	
	@Enumerated(EnumType.STRING)
	private OdemeSekli odemeSekli;
	

	public OdemeSekli getOdemeSekli() {
		return odemeSekli;
	}

	public void setOdemeSekli(OdemeSekli odemeSekli) {
		this.odemeSekli = odemeSekli;
	}

	public Date getTahsilTarihi() {
		return tahsilTarihi;
	}

	public void setTahsilTarihi(Date tahsilTarihi) {
		this.tahsilTarihi = tahsilTarihi;
	}

	public TahakkukDetay getTahakkukDetay() {
		return tahakkukDetay;
	}

	public void setTahakkukDetay(TahakkukDetay tahakkukDetay) {
		this.tahakkukDetay = tahakkukDetay;
	}

	public TahsilatTipi getTahsilatTipi() {
		return tahsilatTipi;
	}

	public void setTahsilatTipi(TahsilatTipi tahsilatTipi) {
		this.tahsilatTipi = tahsilatTipi;
	}

	public Kisi getOdeyenKisi() {
		return odeyenKisi;
	}

	public void setOdeyenKisi(Kisi odeyenKisi) {
		this.odeyenKisi = odeyenKisi;
	}

	public BigDecimal getTahsilTutari() {
		return tahsilTutari;
	}

	public void setTahsilTutari(BigDecimal tahsilTutari) {
		this.tahsilTutari = tahsilTutari;
	}

	public BigDecimal getIndirimTutari() {
		return indirimTutari;
	}

	public void setIndirimTutari(BigDecimal indirimTutari) {
		this.indirimTutari = indirimTutari;
	}
}