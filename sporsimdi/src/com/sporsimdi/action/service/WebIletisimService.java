package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.WebIletisimFacade;
import com.sporsimdi.action.home.WebIletisimHome;
import com.sporsimdi.model.entity.WebIletisim;
import com.sporsimdi.model.type.WebIletisimTipi;

@ManagedBean(name = "webIletisimService")
@ViewScoped
public class WebIletisimService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@ManagedProperty( value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private WebIletisimFacade webIletisimFacade;
	
	@ManagedProperty(value = "#{webIletisimHome}")
	private WebIletisimHome webIletisimHome;

	private Long modelId;
	
	private Boolean save;
	
	private List<WebIletisim> webIletisimListesi;
		
	private WebIletisim webIletisim;

	private List<WebIletisimTipi> webIletisimTipiListesi;

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<WebIletisim> getWebIletisimListesi() {
		if (webIletisimListesi == null) {
			webIletisimListesi = webIletisimFacade.listByModelId(modelId);
		}
		return webIletisimListesi;
	}
	
	public void setWebIletisimListesi(List<WebIletisim> webIletisimListesi) {
		this.webIletisimListesi = webIletisimListesi;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public WebIletisim getWebIletisim() {
		if (webIletisim==null) {
			webIletisim = new WebIletisim();
		}
		return webIletisim;
	}

	public void setWebIletisim(WebIletisim webIletisim) {
		this.webIletisim = webIletisim;
	}

	public List<WebIletisimTipi> getWebIletisimTipiListesi() {
		if (webIletisimTipiListesi == null || webIletisimTipiListesi.size() < 1) {
			webIletisimTipiListesi = Arrays.asList(WebIletisimTipi.values());
		}
		return webIletisimTipiListesi;
	}

	public void setWebIletisimTipiListesi(List<WebIletisimTipi> webIletisimTipiListesi) {
		this.webIletisimTipiListesi = webIletisimTipiListesi;
	}

	public void webIletisimEkle() throws Exception {
		if (getSave()) {
			webIletisim.setModelId(getModelId());
			webIletisimHome.clearInstance();
			webIletisimHome.setInstance(webIletisim);
			webIletisimHome.saveWithoutNavigation();
		}
		getWebIletisimListesi().add(webIletisim);
		webIletisim = null;
	}

	public void webIletisimDuzenle(WebIletisim web) {
		getWebIletisimListesi().remove(web);
		webIletisim = web;
	}

	public void webIletisimSil(WebIletisim web) {
		if (getSave()) {
			webIletisimHome.delete(web);
		}
		getWebIletisimListesi().remove(web);
	}

	public WebIletisimHome getWebIletisimHome() {
		return webIletisimHome;
	}

	public void setWebIletisimHome(WebIletisimHome webIletisimHome) {
		this.webIletisimHome = webIletisimHome;
	}

	public Boolean getSave() {
		return save;
	}

	public void setSave(Boolean save) {
		this.save = save;
	}

	public void initialize(Long modelId, boolean save) {
		setModelId(modelId);
		setSave(save);
	}

}
