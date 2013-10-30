package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.WebIletisimFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.WebIletisim;

@ManagedBean(name = "webIletisimHome")
@ViewScoped
public class WebIletisimHome extends HomeBean<WebIletisim> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	WebIletisimFacade webIletisimFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
		
	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pWebIletisimId = getWebIletisimParam(fc);

		if (pWebIletisimId != null && !pWebIletisimId.isEmpty()) {
			setInstance(webIletisimFacade.findById(Long.valueOf(pWebIletisimId)));
		}
	}

	public String getWebIletisimParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("webIletisimId");

	}

	@Override
	public String save() {
		return super.save();
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}