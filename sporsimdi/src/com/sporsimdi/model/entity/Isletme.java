package com.sporsimdi.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.facade.IsletmeEntityFacade;

@Table
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ISLETME_TIPI", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("ISLETME")
public class Isletme extends ExtendedModel implements IsletmeEntityFacade {

	private static final long serialVersionUID = 8986161490957095179L;

	private String unvan;

	private String vergiNo;

	private String vergiDaire;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "isletmeListesi")
	private List<Tesis> tesisListesi = new ArrayList<Tesis>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "isletmeListesi")
	private List<Kisi> kisiListesi = new ArrayList<Kisi>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Menu> menuListesi = new ArrayList<Menu>();

	@OneToMany(mappedBy = "isletme", fetch = FetchType.LAZY)
	private List<Organizasyon> organizasyonListesi = new ArrayList<Organizasyon>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "isletme")
	private Set<YasGrubu> yasGrubuListesi = new HashSet<YasGrubu>();

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public String getVergiNo() {
		return vergiNo;
	}

	public void setVergiNo(String vergiNo) {
		this.vergiNo = vergiNo;
	}

	public String getVergiDaire() {
		return vergiDaire;
	}

	public void setVergiDaire(String vergiDaire) {
		this.vergiDaire = vergiDaire;
	}

	public List<Tesis> getTesisListesi() {
		return tesisListesi;
	}

	public void setTesisListesi(List<Tesis> tesisListesi) {
		this.tesisListesi = tesisListesi;
	}

	public List<Kisi> getKisiListesi() {
		return kisiListesi;
	}

	public void setKisiListesi(List<Kisi> kisiListesi) {
		this.kisiListesi = kisiListesi;
	}

	public Tesis[] getListeliTesisListesi() {
		return tesisListesi.toArray(new Tesis[tesisListesi.size()]);
	}

	public Kullanici[] getListeliKullaniciListesi() {
		return kisiListesi.toArray(new Kullanici[kisiListesi.size()]);
	}
	
	public boolean isSporOkulu() {
		return (this.getClass().equals(SporOkulu.class));
	}

	public boolean isSporSalonu() {
		return (this.getClass().equals(SporSalonu.class));
	}

	@Override
	public String getMenuValue() {
		return null;
	}

	@Override
	public String getMenuUrl() {
		return null;
	}

	@Override
	public String getMenuBgColor() {
		return null;
	}

	public List<Organizasyon> getOrganizasyonListesi() {
		return organizasyonListesi;
	}

	public void setOrganizasyonListesi(List<Organizasyon> organizasyonListesi) {
		this.organizasyonListesi = organizasyonListesi;
	}

	public List<Menu> getMenuListesi() {
		return menuListesi;
	}

	public void setMenuListesi(List<Menu> menuListesi) {
		this.menuListesi = menuListesi;
	}

	public Set<YasGrubu> getYasGrubuListesi() {
		return yasGrubuListesi;
	}

	public void setYasGrubuListesi(Set<YasGrubu> yasGrubuListesi) {
		this.yasGrubuListesi = yasGrubuListesi;
	}

	@Override
	public String toString() {
		return "İşletme:[" + getUnvan() + "]";
	}


}
