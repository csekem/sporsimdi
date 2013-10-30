package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;

@ManagedBean(name = "isletmeService")
@ViewScoped
public class IsletmeService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;
	
	@ManagedProperty( value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private IsletmeFacade isletmeFacade;
	
	private Isletme isletme;
	
	private List<SelectItem> isletmeListesiSelect;


	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}


	public List<Isletme> listIsletmeByKisi(Kisi kisi) {
		return isletmeFacade.listByKisi(kisi);
	}

	public List<SelectItem> getIsletmeListesiSelect() {
		List<Isletme> l= isletmeFacade.listAll();

		isletmeListesiSelect = new ArrayList<SelectItem>();
		isletmeListesiSelect.add(new SelectItem("", "Hepsi"));
		
		for(Isletme k : l)	{
			SelectItem si = new SelectItem(k.getUnvan(), k.getUnvan());
			isletmeListesiSelect.add(si);
		}

		return isletmeListesiSelect;
	}

	public void setIsletmeListesiSelect(List<SelectItem> isletmeListesiSelect) {
		this.isletmeListesiSelect = isletmeListesiSelect;
	}

	public Isletme getIsletme() {
		return isletme;
	}

	public void setIsletme(Isletme isletme) {
		this.isletme = isletme;
	}

	public String isletmeTipiGetir(Isletme isletme) {
		return isletmeFacade.getIsletmeTipi(isletme);
	}

}
