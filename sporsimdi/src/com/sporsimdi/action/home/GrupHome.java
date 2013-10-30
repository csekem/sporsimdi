package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.model.entity.Grup;


@ManagedBean(name = "grupHome")
@ViewScoped
public class GrupHome extends HomeBean<Grup>{

	private static final long serialVersionUID = 5528752636982509999L;
	
	@EJB
	GrupFacade grupFacade;

	
	public void sayfaLoad() {
		super.sayfaLoad();
	}

	public String getGrupIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("grupId");
	}
	
	public String getDonemIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("donemId");
	}
	
	@Override
	public String save() {
		return super.save();
	}

}