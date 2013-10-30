package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.YasGrubuFacade;
import com.sporsimdi.model.entity.YasGrubu;

@ManagedBean(name = "yasGrubuService")
@ViewScoped
public class YasGrubuService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@EJB
	private YasGrubuFacade yasGrubuFacade;

	private List<YasGrubu> yasGrubuListesi;
	
	

	public List<YasGrubu> getYasGrubuListesi() {
		if (yasGrubuListesi == null || yasGrubuListesi.size() < 1) {
			yasGrubuListesi = yasGrubuFacade.listAll();
		}
		for (YasGrubu k : yasGrubuListesi) {
			k.getAdi();
		}
		return yasGrubuListesi;
	}

	public void setYasGrubuListesi(List<YasGrubu> yasGrubuListesi) {
		this.yasGrubuListesi = yasGrubuListesi;
	}

	public List<YasGrubu> yasGrubuListesiGetir(Long isletmeId) {
		return yasGrubuFacade.listAllByIsletme(isletmeId);
	}
	
	public String yonlendir(Long isletmeId) {
		super.yonlendir(isletmeId);
		if (isletmeId == null) {
			return "/menu/yasGruplari?faces-redirect=true";			
		} else {
			return "/menu/yasGruplari?faces-redirect=true&amp;includeViewParams=true&amp;sporsimdi.com.isletmeId=" + isletmeId;			
		}
	}
}
