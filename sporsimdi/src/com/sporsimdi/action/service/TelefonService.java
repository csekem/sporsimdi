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

import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.action.home.TelefonHome;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.type.TelefonTipi;

@ManagedBean(name = "telefonService")
@ViewScoped
public class TelefonService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@ManagedProperty( value = "#{sessionObject}")
	private SessionObject sessionObject;
	
	@EJB
	private TelefonFacade telefonFacade;
	
	@ManagedProperty(value = "#{telefonHome}")
	private TelefonHome telefonHome;

	private Long modelId;
	
	private Boolean save;
	
	private List<Telefon> telefonListesi;
		
	private Telefon telefon;

	private List<TelefonTipi> telefonTipiListesi;
	
	public List<TelefonTipi> getTelefonTipiListesi() {
		if (telefonTipiListesi == null || telefonTipiListesi.size() < 1) {
			telefonTipiListesi = Arrays.asList(TelefonTipi.values());
		}
		return telefonTipiListesi;
	}

	public void setTelefonTipiListesi(List<TelefonTipi> telefonTipiListesi) {
		this.telefonTipiListesi = telefonTipiListesi;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<Telefon> getTelefonListesi() {
		if (telefonListesi == null) {
			telefonListesi = telefonFacade.listByModelId(modelId);
		}
		return telefonListesi;
	}
	
	public void setTelefonListesi(List<Telefon> telefonListesi) {
		this.telefonListesi = telefonListesi;
	}

	public Telefon getTelefon() {
		if (telefon==null) {
			telefon = new Telefon();
		}
		return telefon;
	}

	public void setTelefon(Telefon telefon) {
		this.telefon = telefon;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public void telefonEkle() throws Exception {
		if (getSave()) {
			telefon.setModelId(getModelId());
			telefonHome.clearInstance();
			telefonHome.setInstance(telefon);
			telefonHome.saveWithoutNavigation();
		}
		getTelefonListesi().add(telefon);
		telefon = null;
	}

	public void telefonDuzenle(Telefon tel) {
		getTelefonListesi().remove(tel);
		telefon = tel;
	}

	public void telefonSil(Telefon tel) {
		if (getSave()) {
			telefonHome.delete(tel);
		}
		getTelefonListesi().remove(tel);
	}

	public TelefonHome getTelefonHome() {
		return telefonHome;
	}

	public void setTelefonHome(TelefonHome telefonHome) {
		this.telefonHome = telefonHome;
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

	public Telefon telefonGetir(Long modelId, TelefonTipi tip) throws Exception {
		return telefonFacade.getByModelIdTip(modelId, tip);
	}

}
