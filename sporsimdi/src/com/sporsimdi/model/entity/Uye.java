package com.sporsimdi.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("UYE")
public class Uye extends Kisi {

	private static final long serialVersionUID = 7383685922929263844L;

	//@OneToMany(mappedBy = "ogrenci", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH})
	//private Set<OgrenciVeli> veliListesi = new HashSet<OgrenciVeli>();
	
	@OneToMany(mappedBy = "uye", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private List<UyeGrup> grupListesi = new ArrayList<UyeGrup>();

	public List<UyeGrup> getGrupListesi() {
		return grupListesi;
	}

	public void setGrupListesi(List<UyeGrup> grupListesi) {
		this.grupListesi = grupListesi;
	}

}
