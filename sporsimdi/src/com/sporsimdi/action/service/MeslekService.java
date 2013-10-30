package com.sporsimdi.action.service;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.MeslekFacade;

@ManagedBean(name = "meslekService")
@ViewScoped
public class MeslekService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@EJB
	private MeslekFacade meslekFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id == null) {
			return "/menu/meslekTanimlama?faces-redirect=true";
		} else {
			return "/menu/meslekTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;meslekId=" + id;
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}
}
