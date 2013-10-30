package com.sporsimdi.action.home;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Tesis;

@ManagedBean(name = "tesisHome")
@ViewScoped
public class TesisHome extends HomeBean<Tesis> {

	private static final long serialVersionUID = 5528752636982509999L;
	
	@EJB
	private TesisFacade tesisFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
	
	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pTesisId = getTesisIdParam(fc);

		if (pTesisId != null && !pTesisId.isEmpty()) {
			setInstance(tesisFacade.findById(Long.valueOf(pTesisId)));
		}
	}

	public String getTesisIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("tesisId");

	}

	@Override
	public String save() {
		List<Isletme> isletmeList = getInstance().getIsletmeListesi();
		isletmeList.add(sessionObject.getSelectedIsletme());
		getInstance().setIsletmeListesi(isletmeList);
		
		String returnStr = super.save();
		
		return returnStr;
	}

	@Override
	public String saveWithoutNavigation() throws Exception {
		List<Isletme> isletmeList = getInstance().getIsletmeListesi();
		isletmeList.add(sessionObject.getSelectedIsletme());
		getInstance().setIsletmeListesi(isletmeList);

		return super.saveWithoutNavigation();
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}