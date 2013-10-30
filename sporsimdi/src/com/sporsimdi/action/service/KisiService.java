package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.model.entity.Kisi;

@ManagedBean(name="kisiService")
@ViewScoped
public class KisiService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;
	
	@ManagedProperty ( value = "#{sessionObject}")
    private SessionObject sessionObject;
	
	@EJB
	private KisiFacade kisiFacade;
	
	private List<Kisi> kisiListesi;

	private List<Kisi> tumKisiListesi;

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<String> completeKisi(String query) {
		List<Kisi> kisiListesi = kisiFacade.listByIsletme(sessionObject.getSelectedIsletme());
		List<String> kisiAdlari = new ArrayList<String>();
		for (Kisi kisi : kisiListesi) {
			kisiAdlari.add(kisi.getAdSoyad());
		}
		return kisiAdlari;
	}

	public List<Kisi> getKisiListesi() {
		if (kisiListesi == null || kisiListesi.size() < 1) {
			kisiListesi = kisiFacade.listByIsletme(sessionObject.getSelectedIsletme());
		}
		return kisiListesi;
	}

	public void setKisiListesi(List<Kisi> kisiListesi) {
		this.kisiListesi = kisiListesi;
	}

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/kisiTanimlama?faces-redirect=true";			
		} else {
			return "/menu/kisiTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;kisiId=" + id;			
		}
	}

	public List<Kisi> getTumKisiListesi() {
		if (tumKisiListesi == null || tumKisiListesi.size() < 1) {
			tumKisiListesi = kisiFacade.getAll();
		}
		return tumKisiListesi;
	}

	public void setTumKisiListesi(List<Kisi> tumKisiListesi) {
		this.tumKisiListesi = tumKisiListesi;
	}

	public String getKisiTipi(Kisi kisi) {
		return kisiFacade.getKisiTipi(kisi);
	}

}
