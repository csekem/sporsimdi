package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.AdresFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Adres;

@ManagedBean(name = "adresHome")
@ViewScoped
public class AdresHome extends HomeBean<Adres> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	AdresFacade adresFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
		
	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pAdresId = getAdresIdParam(fc);

		if (pAdresId != null && !pAdresId.isEmpty()) {
			setInstance(adresFacade.findByIdEditMode(Long.valueOf(pAdresId)));
		}
	}

	public String getAdresIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("adresId");

	}

	@Override
	public String save() {
		//getInstance().setIsletme(sessionObject.getIsletme());
		return super.save();
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}