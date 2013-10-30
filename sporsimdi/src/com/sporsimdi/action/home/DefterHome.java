package com.sporsimdi.action.home;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.BakiyeFacade;
import com.sporsimdi.action.facade.DefterFacade;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.service.DefterService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Kasa;

@ManagedBean(name = "defterHome")
@ViewScoped
public class DefterHome extends HomeBean<Defter> {

	private static final long serialVersionUID = 5528752636982509999L;
	
	@EJB
	DefterFacade defterFacade;

	@EJB
	BakiyeFacade bakiyeFacade;

	@EJB
	KasaFacade kasaFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
	
	@ManagedProperty(value = "#{defterService}")
	private DefterService defterService;

	public void sayfaLoad() {
		setInstance(createInstance());
		getInstance().setTarih(defterService.getTarih());
		
		FacesContext fc = FacesContext.getCurrentInstance();
		String pdefterId = getDefterIdParam(fc);

		if (pdefterId != null && !pdefterId.isEmpty()) {
			setInstance(defterFacade.findByIdEditMode(Long.valueOf(pdefterId)));
		} else {
			//String tarih = getTarihParam(fc);
		}
	}

	public String getDefterIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("defterId");
	}
		
	public String getTarihParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("tarih");
	}
		
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	@Override
	public String saveWithoutNavigation()  throws Exception {
		getInstance().setKasa(getDefterService().getSelectedKasa());
		getInstance().setKullanici(sessionObject.getKullanici());
		getInstance().setBakiye(latestBakiyeBul().add(getInstance().getGelir().subtract(getInstance().getGider())));
		updateAllBakiyeAfter(getInstance());
		String ret = super.saveWithoutNavigation();

		updateDailyBakiyeAfter(getInstance());
		
		setInstance(createInstance());
		defterService.listele();
		return ret;
	}

	public BigDecimal latestBakiyeBul() {
		Defter latestDefter = defterFacade.findLatest(getInstance());
		if (latestDefter == null) {
			return new BigDecimal(0);
		} else {
			return latestDefter.getBakiye();			
		}
	}

	public void updateAllBakiyeAfter(Defter def) {
		defterFacade.updateAllBakiyeAfter(def);
	}

	public void updateDailyBakiyeAfter(Defter def) {
		bakiyeFacade.updateAllBakiyeAfter(def);
	}

	@Override
	public String delete() throws Exception {
		Kasa kasa = getInstance().getKasa();
		Defter def = defterFacade.findLatest(getInstance());
		String ret = super.delete();
		
		if (def == null) {
			def = defterFacade.findFirst(kasa);
		}
		if (def != null) {
			setInstance(def);
			saveWithoutNavigation();
		}
		
		setInstance(createInstance());
		defterService.listele();
		return ret;
	}

	public DefterService getDefterService() {
		return defterService;
	}

	public void setDefterService(DefterService defterService) {
		this.defterService = defterService;
	}

}