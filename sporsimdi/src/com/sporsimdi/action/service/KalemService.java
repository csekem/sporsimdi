package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.facade.KalemFacade;
import com.sporsimdi.model.entity.Kalem;

@ManagedBean(name = "kalemService")
@ViewScoped
public class KalemService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	private Kalem kalem;

	@EJB
	private KalemFacade kalemFacade;

	private List<SelectItem> kalemListesiSelect;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	public Kalem getKalem() {
		return kalem;
	}

	public void setKalem(Kalem kalem) {
		this.kalem = kalem;
	}

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id == null) {
			return "/menu/kalemTanimlama?faces-redirect=true";
		} else {
			return "/menu/kalemTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;kalemId=" + id;
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<SelectItem> getKalemListesiSelect() {
		if (kalemListesiSelect == null || kalemListesiSelect.size() < 1) {
			kalemListesiSelect = new ArrayList<SelectItem>();
			List<Kalem> kalemList = kalemFacade.listAllByIsletme(sessionObject.getSelectedIsletme().getId());
			for (Kalem kalem : kalemList) {
				SelectItem item = new SelectItem(kalem, kalem.getAdi());
				kalemListesiSelect.add(item);
			}
		}
		return kalemListesiSelect;
	}

	public void setKalemListesiSelect(List<SelectItem> kalemListesiSelect) {
		this.kalemListesiSelect = kalemListesiSelect;
	}

}
