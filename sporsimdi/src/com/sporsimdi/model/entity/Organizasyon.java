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

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.OrganizasyonTipi;

@Table
@Entity
public class Organizasyon extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@Enumerated(EnumType.STRING)
	private OrganizasyonTipi organizasyonTipi;

	private String adi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date tarihBaslangic;

	@Temporal(TemporalType.TIMESTAMP)
	private Date tarihBitis;

	@ManyToOne(fetch = FetchType.LAZY)
	private Isletme isletme;

	@OneToMany(mappedBy = "organizasyon", fetch = FetchType.LAZY)
	private List<OrgTesis> orgTesisListesi = new ArrayList<OrgTesis>();
	
	public Isletme getIsletme() {
		return isletme;
	}

	public void setIsletme(Isletme isletme) {
		this.isletme = isletme;
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

	public OrganizasyonTipi getOrganizasyonTipi() {
		return organizasyonTipi;
	}

	public void setOrganizasyonTipi(OrganizasyonTipi organizasyonTipi) {
		this.organizasyonTipi = organizasyonTipi;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public List<OrgTesis> getOrgTesisListesi() {
		return orgTesisListesi;
	}

	public void setOrgTesisListesi(List<OrgTesis> orgTesisListesi) {
		this.orgTesisListesi = orgTesisListesi;
	}

	@Override
	public String toString() {
		return "Organizasyon [" + adi + "]";
	}
}
