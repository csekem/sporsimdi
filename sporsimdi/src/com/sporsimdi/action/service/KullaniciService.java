package com.sporsimdi.action.service;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.model.entity.Kullanici;

@ManagedBean(name="kullaniciService")
@ViewScoped
public class KullaniciService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;
	
	@ManagedProperty ( value = "#{sessionObject}")
    private SessionObject sessionObject;
	
	private Kullanici kullanici;
	
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}
	
	public String sifreDegistirYonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "";			
		} else {
			return "/menu/sifreDegistir?faces-redirect=true&amp;includeViewParams=true&amp;kullaniciId=" + id;			
		}
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}


}
