package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.model.entity.Grup;

@ManagedBean(name = "grupService")
@ViewScoped
public class GrupService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@EJB
	private GrupFacade grupFacade;

	private List<Grup> grupListesi = new ArrayList<Grup>();
	
	private List<SelectItem> gunListesi;
		
	public List<Grup> getGrupListesi() {
		return grupListesi;
	}

	public void setGrupListesi(List<Grup> grupListesi) {
		this.grupListesi = grupListesi;
	}

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/grupTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;donemId=" ;		
		} else {
			return "/menu/grupTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;grupId=" + id;			
		}
	}

	public List<SelectItem> getGunListesi() {
		if (gunListesi == null || gunListesi.isEmpty()) {

			gunListesi = new ArrayList<SelectItem>();
			
			for(int i = 1; i<31; i++)	{
				SelectItem si = new SelectItem(i, Integer.toString(i));
				gunListesi.add(si);
			}
		}
		return gunListesi;
	}

	public void setGunListesi(List<SelectItem> gunListesi) {
		this.gunListesi = gunListesi;
	}


}
