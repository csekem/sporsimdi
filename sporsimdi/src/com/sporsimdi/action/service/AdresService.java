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

import com.sporsimdi.action.facade.AdresFacade;
import com.sporsimdi.action.home.AdresHome;
import com.sporsimdi.model.entity.Adres;
import com.sporsimdi.model.type.AdresTipi;

@ManagedBean(name = "adresService")
@ViewScoped
public class AdresService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@ManagedProperty( value = "#{sessionObject}")
	private SessionObject sessionObject;
		
	@EJB
	private AdresFacade adresFacade;
	
	@ManagedProperty(value = "#{adresHome}")
	private AdresHome adresHome;

	private Long modelId;
	
	private Boolean save;
	
	private List<Adres> adresListesi;
			
	private Adres adres;

	private List<AdresTipi> adresTipiListesi;
	
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<Adres> getAdresListesi() {
		if (adresListesi == null) {
			adresListesi = adresFacade.listByModelId(modelId);
			for (Adres adr : adresListesi) {
				adr.getIlce().getAdi();
			}
		}
		return adresListesi;
	}
	

	public void setAdresListesi(List<Adres> adresListesi) {
		this.adresListesi = adresListesi;
	}
	
	public List<AdresTipi> getAdresTipiListesi() {
		if (adresTipiListesi == null || adresTipiListesi.size() < 1) {
			adresTipiListesi = Arrays.asList(AdresTipi.values());
		}
		return adresTipiListesi;
	}

	public void setAdresTipiListesi(List<AdresTipi> adresTipiListesi) {
		this.adresTipiListesi = adresTipiListesi;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Adres getAdres() {
		if (adres==null) {
			adres = new Adres();
		}
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public void adresEkle() throws Exception {
		if (getSave()) {
			adres.setModelId(getModelId());
			adresHome.clearInstance();
			adresHome.setInstance(adres);
			adresHome.saveWithoutNavigation();
		}
		getAdresListesi().add(adres);
		adres = null;
	}

	public void adresDuzenle(Adres adr) {
//		if (getSave()) {
//			adresHome.delete(adr);
//		}
		getAdresListesi().remove(adr);
		adres = adr;
	}

	public void adresSil(Adres adr) {
		if (getSave()) {
			adresHome.delete(adr);
		}
		getAdresListesi().remove(adr);
	}

	
	public void adresSave(Long modelId) throws Exception {
		List<Adres> eskiAdresler = adresFacade.listByModelId(modelId);
		for (Adres adres1 : getAdresListesi()) {
			adres1.setModelId(modelId);
			adresHome.clearInstance();
			adresHome.setInstance(adres1);
			adresHome.saveWithoutNavigation();
	
			eskiAdresler.remove(adres1);
		}
		for (Adres eskiAdres : eskiAdresler) {
			adresHome.delete(eskiAdres);
		}
	}
	
	
	public void initialize(Long modelId, boolean save) {
		setModelId(modelId);
		setSave(save);
	}

	public Boolean getSave() {
		return save;
	}

	public void setSave(Boolean save) {
		this.save = save;
	}

	public AdresHome getAdresHome() {
		return adresHome;
	}

	public void setAdresHome(AdresHome adresHome) {
		this.adresHome = adresHome;
	}

}
