package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.model.type.Cinsiyet;
import com.sporsimdi.model.type.GrupCinsiyet;

@ManagedBean(name = "cinsiyetService")
@ViewScoped
public class CinsiyetService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	private List<GrupCinsiyet> grupCinsiyetListesi;

	private List<Cinsiyet> cinsiyetListesi;
	

	public void setGrupCinsiyetListesi(List<GrupCinsiyet> grupCinsiyetListesi) {
		this.grupCinsiyetListesi = grupCinsiyetListesi;
	}

	public List<GrupCinsiyet> getGrupCinsiyetListesi() {
		if (grupCinsiyetListesi == null) {
			grupCinsiyetListesi = Arrays.asList(GrupCinsiyet.values());
		}
		return grupCinsiyetListesi;
	}

	public List<Cinsiyet> getCinsiyetListesi() {
		if (cinsiyetListesi == null) {
			cinsiyetListesi = Arrays.asList(Cinsiyet.values());
		}
		return cinsiyetListesi;
	}
}
