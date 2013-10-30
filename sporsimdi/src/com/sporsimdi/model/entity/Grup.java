package com.sporsimdi.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.GrupCinsiyet;

@Table
@Entity
public class Grup extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@ManyToOne(fetch = FetchType.EAGER)
	private OrgTesis orgTesis;

	@OneToMany(mappedBy="grup", fetch = FetchType.LAZY)
	private List<UyeGrup> uyeListesi = new ArrayList<UyeGrup>();

	@Enumerated(EnumType.STRING)
	private GrupCinsiyet grupCinsiyet;

	@OneToMany(mappedBy = "grup", fetch = FetchType.LAZY)
	private List<Tarife> tarifeListesi = new ArrayList<Tarife>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tarihBaslangic;

	@Temporal(TemporalType.TIMESTAMP)
	private Date tarihBitis;

	@Temporal(TemporalType.DATE)
	private Date yasGrupBaslangic;

	@Temporal(TemporalType.DATE)
	private Date yasGrupBitis;

	private int aidatGun;
	
	public int getAidatGun() {
		return aidatGun;
	}

	public Date getTarihBaslangic() {
		return tarihBaslangic;
	}

	public void setTarihBaslangic(Date tarihBaslangic) {
		this.tarihBaslangic = tarihBaslangic;
	}

	public Date getTarihBitis() {
		return tarihBitis;
	}

	public void setTarihBitis(Date tarihBitis) {
		this.tarihBitis = tarihBitis;
	}

	public void setAidatGun(int aidatGun) {
		this.aidatGun = aidatGun;
	}

	public GrupCinsiyet getGrupCinsiyet() {
		return grupCinsiyet;
	}

	public void setGrupCinsiyet(GrupCinsiyet grupCinsiyet) {
		this.grupCinsiyet = grupCinsiyet;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}


	public List<UyeGrup> getUyeListesi() {
		return uyeListesi;
	}

	public void setUyeListesi(List<UyeGrup> uyeListesi) {
		this.uyeListesi = uyeListesi;
	}

	public OrgTesis getOrgTesis() {
		return orgTesis;
	}

	public void setOrgTesis(OrgTesis orgTesis) {
		this.orgTesis = orgTesis;
	}

	public List<Tarife> getTarifeListesi() {
		return tarifeListesi;
	}

	public void setTarifeListesi(List<Tarife> tarifeListesi) {
		this.tarifeListesi = tarifeListesi;
	}

	public Date getYasGrupBaslangic() {
		return yasGrupBaslangic;
	}

	public void setYasGrupBaslangic(Date yasGrupBaslangic) {
		this.yasGrupBaslangic = yasGrupBaslangic;
	}

	public Date getYasGrupBitis() {
		return yasGrupBitis;
	}

	public void setYasGrupBitis(Date yasGrupBitis) {
		this.yasGrupBitis = yasGrupBitis;
	}

	@Override
	public String toString() {
		return "Grup [" + getAdi() + "]" + " (" + new UtilDate(getTarihBaslangic()) + "-" + new UtilDate(getTarihBitis()) + ")";
	}
}
