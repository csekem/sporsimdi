package com.sporsimdi.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class OrgTesis extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@OneToOne(fetch = FetchType.LAZY)
	private Tesis tesis;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organizasyon organizasyon;

	@OneToMany(mappedBy = "orgTesis", fetch = FetchType.LAZY)
	private List<Grup> grupListesi = new ArrayList<Grup>();
	
	public List<Grup> getGrupListesi() {
		return grupListesi;
	}

	public void setGrupListesi(List<Grup> grupListesi) {
		this.grupListesi = grupListesi;
	}

	public Tesis getTesis() {
		return tesis;
	}

	public void setTesis(Tesis tesis) {
		this.tesis = tesis;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

	@Override
	public String toString() {
		return "Tesis [" + adi + "]";
	}
}
