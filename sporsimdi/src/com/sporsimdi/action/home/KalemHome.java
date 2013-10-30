package com.sporsimdi.action.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.KalemFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Kalem;

@ManagedBean(name = "kalemHome")
@ViewScoped
public class KalemHome extends HomeBean<Kalem> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private KalemFacade kalemFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	private Map<Long, List<Kalem>> kalemMap = new HashMap<Long, List<Kalem>>();

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pKalemId = getKalemIdParam(fc);

		if (pKalemId != null && !pKalemId.isEmpty()) {
			setInstance(kalemFacade.findById(Long.valueOf(pKalemId)));
		} else {
			clearInstance();
		}
	}

	public String getKalemIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("kalemId");
	}

	@Override
	public String save() {
		String returnStr = super.save();

		return returnStr;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	@Override
	public String saveWithoutNavigation() throws Exception {
		String ret = super.saveWithoutNavigation();
		kalemMap.clear();
		return ret;
	}

	public Map<Long, List<Kalem>> getKalemMap() {
		return kalemMap;
	}

	public void setKalemMap(Map<Long, List<Kalem>> kalemMap) {
		this.kalemMap = kalemMap;
	}

	public List<Kalem> listKalemByIsletme(Long isletmeId) {
		if (!kalemMap.containsKey(isletmeId)) {
			kalemMap.put(isletmeId, kalemFacade.listAllByIsletme(isletmeId));
		}
		return kalemMap.get(isletmeId);
	}

}