package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.YasGrubuFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.YasGrubu;


@ManagedBean(name = "yasGrubuHome")
@ViewScoped
public class YasGrubuHome extends HomeBean<YasGrubu>{

	private static final long serialVersionUID = 5528752636982509999L;
	
	@EJB
	YasGrubuFacade yasGrubuFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pyasGrubuId = getYasGrubuIdParam(fc);

		if (pyasGrubuId != null && !pyasGrubuId.isEmpty()) {
			setInstance(yasGrubuFacade.findById(Long.valueOf(pyasGrubuId)));
		}
	}
	
	public String getYasGrubuIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		return params.get("yasGrubuId");

	}
	
	@Override
	public String save() {
		getInstance().setIsletme(sessionObject.getSelectedIsletme());
		return super.save();
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	
}