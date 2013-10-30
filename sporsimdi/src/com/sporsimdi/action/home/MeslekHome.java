package com.sporsimdi.action.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.MeslekFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Meslek;

@ManagedBean(name = "meslekHome")
@ViewScoped
public class MeslekHome extends HomeBean<Meslek> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private MeslekFacade meslekFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	private Map<Long, List<Meslek>> meslekMap = new HashMap<Long, List<Meslek>>();

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pMeslekId = getMeslekIdParam(fc);

		if (pMeslekId != null && !pMeslekId.isEmpty()) {
			setInstance(meslekFacade.findById(Long.valueOf(pMeslekId)));
		} else {
			clearInstance();
		}
	}

	public String getMeslekIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("meslekId");

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

	public Map<Long, List<Meslek>> getMeslekMap() {
		return meslekMap;
	}

	public void setMeslekMap(Map<Long, List<Meslek>> meslekMap) {
		this.meslekMap = meslekMap;
	}

	public List<Meslek> listMeslekByIsletme(Long isletmeId) {
		if (!meslekMap.containsKey(isletmeId)) {
			meslekMap.put(isletmeId, meslekFacade.listAllByIsletme(isletmeId));
		}
		return meslekMap.get(isletmeId);
	}

	@Override
	public String saveWithoutNavigation() throws Exception {
		getInstance().setIsletme(sessionObject.getSelectedIsletme());
		String ret = super.saveWithoutNavigation();
		meslekMap.clear();
		return ret;
	}

}