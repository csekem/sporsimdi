package com.sporsimdi.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Il extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	private String adi;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "il")
	private Set<Ilce> ilceListesi = new HashSet<Ilce>();

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Set<Ilce> getIlceListesi() {
		return ilceListesi;
	}

	public void setIlceListesi(Set<Ilce> ilceListesi) {
		this.ilceListesi = ilceListesi;
	}
	public Ilce[] getListeliIlceListesi()
	{
		return ilceListesi.toArray(new Ilce[ilceListesi.size()]);
	}

}
