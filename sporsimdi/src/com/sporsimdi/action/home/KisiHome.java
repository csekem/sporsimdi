package com.sporsimdi.action.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.AdresFacade;
import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.action.service.AdresService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Adres;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.Telefon;

@ManagedBean(name = "kisiHome")
@ViewScoped
public class KisiHome extends HomeBean<Kisi> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private KisiFacade kisiFacade;
	
	@EJB
	private AdresFacade adresFacade;

	@EJB
	private TelefonFacade telefonFacade;

	@ManagedProperty(value = "#{adresHome}")
	private AdresHome adresHome;

	@ManagedProperty(value = "#{telefonHome}")
	private TelefonHome telefonHome;

	@ManagedProperty (value = "#{sessionObject}")
    private SessionObject sessionObject;
	
	@ManagedProperty(value = "#{adresService}")
	private AdresService adresService;

		
	private List<Adres> adresListe;
	
	private Adres adres;

	private List<Telefon> telListe;
	
	private Telefon tel;

	
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}


	@Override
	public String save() {		
		List<Isletme> isletmeListe = new ArrayList<Isletme>();
		isletmeListe.add(sessionObject.getSelectedIsletme());
		getInstance().setIsletmeListesi(isletmeListe);

		String ret = super.save();
		
		//adresService.adresSave(getInstance().getId());
		
		return ret;
	}

	public String saveGenel() {				
		List<Isletme> isletmeListe = new ArrayList<Isletme>();
		isletmeListe.add(sessionObject.getSelectedIsletme());
		getInstance().setIsletmeListesi(isletmeListe);

		return super.save();
	}

	public String getKisiIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("kisiId");
	}

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pKisiId = getKisiIdParam(fc);

		if (pKisiId != null && !pKisiId.isEmpty()) {
			setInstance(kisiFacade.findByIdEager(Long.valueOf(pKisiId)));
			setAdresListe(adresFacade.listByModelId(getInstance().getId()));
			setTelListe(telefonFacade.listByModelId(getInstance().getId()));
		}
	}

	public void sayfaLoad(Kisi kisi) {
		if (kisi != null && kisi.getId() != null) {
			setInstance( kisiFacade.findById(kisi.getId()));				
		}
	}

	public void adresEkle() {
		getAdresListe().add(adres);
		adres = null;
	}

	public void adresDuzenle(Adres adr) {
		getAdresListe().remove(adr);
		adres = adr;
	}

	public void adresSil(Adres adr) {
		getAdresListe().remove(adr);
	}

	public void telefonEkle() {
		getTelListe().add(tel);
		tel = null;
	}

	public List<Adres> getAdresListe() {
		if (adresListe == null) {
			adresListe =new ArrayList<Adres>();			
		}
		return adresListe;
	}

	public void setAdresListe(List<Adres> adresListe) {
		this.adresListe = adresListe;
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

	public List<Telefon> getTelListe() {
		if (telListe == null) {
			telListe =new ArrayList<Telefon>();			
		}
		return telListe;
	}

	public void setTelListe(List<Telefon> telListe) {
		this.telListe = telListe;
	}

	public Telefon getTel() {
		if (tel==null) {
			tel = new Telefon();
		}
		return tel;
	}

	public void setTel(Telefon tel) {
		this.tel = tel;
	}

	public AdresHome getAdresHome() {
		return adresHome;
	}

	public void setAdresHome(AdresHome adresHome) {
		this.adresHome = adresHome;
	}

	public TelefonHome getTelefonHome() {
		return telefonHome;
	}

	public void setTelefonHome(TelefonHome telefonHome) {
		this.telefonHome = telefonHome;
	}

	public AdresService getAdresService() {
		return adresService;
	}

	public void setAdresService(AdresService adresService) {
		this.adresService = adresService;
	}

}