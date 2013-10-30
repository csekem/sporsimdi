package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Telefon;

@ManagedBean(name = "telefonHome")
@ViewScoped
public class TelefonHome extends HomeBean<Telefon> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	TelefonFacade telefonFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
		
	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pTelefonId = getTelefonIdParam(fc);

		if (pTelefonId != null && !pTelefonId.isEmpty()) {
			setInstance(telefonFacade.findByIdEditMode(Long.valueOf(pTelefonId)));
		}
	}

	public void sayfaLoad(Long modelId) {
	}

	public String getTelefonIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("telefonId");

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