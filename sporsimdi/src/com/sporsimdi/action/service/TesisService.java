package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.entity.Tesis;

@ManagedBean(name = "tesisService")
@ViewScoped
public class TesisService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	private Isletme isletme;

	@EJB
	private IsletmeFacade isletmeFacade;

	@EJB
	private TesisFacade tesisFacade;

	private List<Isletme> isletmeListesi;

	private Tesis tesis;

	private List<Tesis> tesisListesi = new ArrayList<Tesis>();

	private List<Tesis> isletmeTesisListesi = new ArrayList<Tesis>();

	private List<Saha> sahaListesi = new ArrayList<Saha>();

	@ManagedProperty(value="#{sessionObject}")
	private SessionObject sessionObject;

	public Isletme getIsletme() {
		return isletme;
	}

	public void setIsletme(Isletme isletme) {
		this.isletme = isletme;
	}

	public List<Isletme> getIsletmeListesi() {
		if (isletmeListesi == null || isletmeListesi.size() < 1) {
			isletmeListesi = isletmeFacade.listAll();
		}
		for (Isletme k : isletmeListesi) {
			k.getUnvan();
		}
		return isletmeListesi;
	}

	public void setIsletmeListesi(List<Isletme> isletmeListesi) {
		this.isletmeListesi = isletmeListesi;
	}

	public Tesis getTesis() {
		return tesis;
	}

	public void setTesis(Tesis tesis) {
		this.tesis = tesis;
	}

	public List<Tesis> getTesisListesi() {
		if (tesisListesi == null || tesisListesi.size() < 1) {
			tesisListesi = tesisFacade.getAll();
		}
		return tesisListesi;
	}

	public void setTesisListesi(List<Tesis> tesisListesi) {
		this.tesisListesi = tesisListesi;
	}

	public List<Saha> getSahaListesi() {
		return sahaListesi;
	}

	public void setSahaListesi(List<Saha> sahaListesi) {
		this.sahaListesi = sahaListesi;
	}

	public void isletmeninTesisleri() {
		tesisListesi = tesisFacade.listAllByIsletme(isletme.getId()); 
	}

	@SuppressWarnings("unchecked")
	public void tesisinSahalari() {
		tesisListesi = entityManager
				.createQuery("select k from Saha k where k.tesis =:tesis")
				.setParameter("tesis", tesis).getResultList();
	}

	public List<Tesis> getIsletmeTesisListesi() {
		if (isletmeTesisListesi == null || isletmeTesisListesi.size() < 1) {
			isletmeTesisListesi = tesisFacade.listAllByIsletme(sessionObject.getSelectedIsletme().getId());
		}
		return isletmeTesisListesi;
	}

	public void setIsletmeTesisListesi(List<Tesis> isletmeTesisListesi) {
		this.isletmeTesisListesi = isletmeTesisListesi;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}
	
	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/tesisler?faces-redirect=true";			
		} else {
			return "/menu/tesisler?faces-redirect=true&amp;includeViewParams=true&amp;sporsimdi.com.isletmeId=" + id;			
		}
	}
	
	public List<Tesis> listTesisByIsletme(Long isletmeId) {
		return tesisFacade.listAllByIsletme(isletmeId);
	}


}