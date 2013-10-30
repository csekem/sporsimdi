package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import com.sporsimdi.action.facade.KisiIliskiFacade;
import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.action.facade.UyeFacade;
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.action.home.UyeHome;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;
import com.sporsimdi.model.type.TelefonTipi;
import com.sporsimdi.model.type.YakinlikDerecesi;

@ManagedBean(name = "uyeService")
@ViewScoped
public class UyeService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{uyeHome}")
	private UyeHome uyeHome;

	@EJB
	private UyeFacade uyeFacade;

	@EJB
	private UyeGrupFacade uyeGrupFacade;

	@EJB
	private KisiIliskiFacade kisiIliskiFacade;

	@EJB
	private TelefonFacade telefonFacade;

	private List<Uye> isletmeUyeler;

	private List<Uye> uyeler;

	private List<Uye> filteredUyeler;

	private Uye uye;

	private Grup grup;

	private boolean skip;

	private Grup filterGrup;

	private String filterUye;

	public UyeHome getUyeHome() {
		return uyeHome;
	}

	public void setUyeHome(UyeHome uyeHome) {
		this.uyeHome = uyeHome;
	}

	public List<Uye> getUyeler() {
		return uyeler;
	}

	public void setUyeler(List<Uye> uyeler) {
		this.uyeler = uyeler;
	}

	public List<Uye> getFilteredUyeler() {
		return filteredUyeler;
	}

	public void setFilteredUyeler(List<Uye> filteredUyeler) {
		this.filteredUyeler = filteredUyeler;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public UyeService() {
	}

	public Uye getUye() {
		return uye;
	}

	public void setUye(Uye uye) {
		this.uye = uye;
	}

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id == null) {
			return "/menu/uyeTanimlama?faces-redirect=true";
		} else {
			return "/menu/uyeTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;uyeId=" + id;
		}
	}

	public void listele() {
		uyeler = null;
		getUyeler();
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			/*
			 * if (event.getOldStep().equals("tabGenel")) { uyeHome.save(); }
			 */
			return event.getNewStep();
		}
	}

	public Grup getFilterGrup() {
		return filterGrup;
	}

	public void setFilterGrup(Grup filterGrup) {
		this.filterGrup = filterGrup;
	}

	public String getFilterUye() {
		return filterUye;
	}

	public void setFilterUye(String filterUye) {
		this.filterUye = filterUye;
	}

	public List<Uye> getIsletmeUyeler() {
		if (isletmeUyeler == null) {
			isletmeUyeler = uyeFacade.listByIsletme(sessionObject.getSelectedIsletme());
		}
		return isletmeUyeler;
	}

	public void setIsletmeUyeler(List<Uye> isletmeUyeler) {
		this.isletmeUyeler = isletmeUyeler;
	}

	public UyeGrup uyeninGrubu(Grup grup, Uye uye) throws Exception {
		return uyeGrupFacade.getByGrupUye(grup, uye);
	}

	public String uyeAdiEbeveynli(Uye uye) {
		String uyeAd = uye.getAdSoyad();
		try {
			KisiIliski anneIliski = kisiIliskiFacade.getByKisiYakinlik(uye, YakinlikDerecesi.ANNE);
			if (anneIliski != null) {
				Kisi anne = anneIliski.getIliskiliKisi();
				if (anne != null) {
					Telefon telefon = telefonFacade.getByModelIdTip(anne.getId(), TelefonTipi.CEP);
					if (anne.getAd() != null && telefon != null && telefon.getTelefon() != null) {
						uyeAd = uyeAd.concat(", ANNE: ").concat(anne.getAdSoyad()).concat(" ").concat(telefon.getTelefon());
					}
				}
			}
			KisiIliski babaIliski = kisiIliskiFacade.getByKisiYakinlik(uye, YakinlikDerecesi.BABA);
			if (babaIliski != null) {
				Kisi baba = babaIliski.getIliskiliKisi();
				if (baba != null) {
					Telefon telefon = telefonFacade.getByModelIdTip(baba.getId(), TelefonTipi.CEP);
					if (baba.getAd() != null && telefon != null && telefon.getTelefon() != null) {
						uyeAd = uyeAd.concat(", BABA: ").concat(baba.getAdSoyad()).concat(" ").concat(telefon.getTelefon());
					}
				}
			}
		} catch (Exception e) {
			return uyeAd;
		}
		return uyeAd;
	}
}
