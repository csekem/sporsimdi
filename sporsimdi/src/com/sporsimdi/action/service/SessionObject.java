package com.sporsimdi.action.service;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.KullaniciFacade;
import com.sporsimdi.action.facade.RolFacade;
import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.Kullanici;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.Rol;
import com.sporsimdi.model.entity.SporOkulu;
import com.sporsimdi.model.entity.SporSalonu;
import com.sporsimdi.model.type.OrganizasyonTipi;

@ManagedBean(name = "sessionObject")
@SessionScoped
public class SessionObject implements Serializable{

	private static final long serialVersionUID = 5916057262016455978L;

	public static final String SPOR_OKULU = "SPOR OKULU";
	public static final String SPOR_SALONU = "SPOR SALONU";
	
	private Kullanici kullanici;
	
	private Rol rol;
	
	private Kisi kisi;

	private Boolean tekIsletme;

	private Isletme selectedIsletme;

	private Boolean isletmeSecildi;
	
	private String selectedIsletmeTipi;
	
	private Organizasyon selectedOrganizasyon;
	
	private OrganizasyonTipi selectedOrganizasyonTipi;
	
	
	
	private SporOkulu sporOkulu;
	
	private SporSalonu sporSalonu;
	
	
	//private Kasa anaKasa;
	
	
	private String labelsProperties="";

	@EJB
	private KullaniciFacade kullaniciFacade;

	@EJB
	private RolFacade rolFacade;

	@EJB
	private IsletmeFacade isletmeFacade;

	@EJB
	private TesisFacade tesisFacade;
	
	@EJB
	private KasaFacade kasaFacade;

	public String getSelectedIsletmeTipi() {
		if (selectedIsletmeTipi==null && getIsletmeSecildi()) {
			selectedIsletmeTipi = isletmeFacade.getIsletmeTipi(getSelectedIsletme());
		}
		return selectedIsletmeTipi;
	}

	public void setSelectedIsletmeTipi(String selectedIsletmeTipi) {
		this.selectedIsletmeTipi = selectedIsletmeTipi;
	}

	public Organizasyon getSelectedOrganizasyon() {
		return selectedOrganizasyon;
	}

	public void setSelectedOrganizasyon(Organizasyon selectedOrganizasyon) {
		this.selectedOrganizasyon = selectedOrganizasyon;
	}

	public OrganizasyonTipi getSelectedOrganizasyonTipi() {
		return selectedOrganizasyonTipi;
	}

	public void setSelectedOrganizasyonTipi(
			OrganizasyonTipi selectedOrganizasyonTipi) {
		this.selectedOrganizasyonTipi = selectedOrganizasyonTipi;
	}

	public SporSalonu getSporSalonu() {
		return sporSalonu;
	}

	public void setSporSalonu(SporSalonu sporSalonu) {
		this.sporSalonu = sporSalonu;
	}


	public Kullanici getKullanici() {
		if(kullanici==null){
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Principal principal = (Principal) request.getUserPrincipal();
            if (principal != null) {
                kullanici = kullaniciFacade.findByKullanici(principal.getName()); // Find User by j_username.
            }
		}
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Kisi getKisi() {
		if (kisi==null) {
			kisi = getKullanici().getKisi();
		}
		return kisi;
	}

	public void setKisi(Kisi kisi) {
		this.kisi = kisi;
	}

	public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.invalidateSession();
        
        externalContext.redirect("/giris.jsf");  
    }

	public void sporOkuluSecildi() {
        setSelectedIsletmeTipi(SPOR_OKULU);
    }

	public SporOkulu getSporOkulu() {
		return sporOkulu;
	}

	public void setSporOkulu(SporOkulu sporOkulu) {
		this.sporOkulu = sporOkulu;
		//sporOkulu.setTesisListesi(tesisFacade.getIsletmeTesisListesi(sporOkulu));
	}

	public String getLabelsProperties() {
		if(getSelectedIsletmeTipi().equals("SPOR_OKULU")) {
			labelsProperties = "com.sporsimdi.resources.okul";
		} else if(getSelectedIsletmeTipi().equals("SPOR_SALONU")) {
			labelsProperties = "com.sporsimdi.resources.salon";
		}	
		return labelsProperties;
	}

	public void setLabelsProperties(String labelsProperties) {
		this.labelsProperties = labelsProperties;
	}

	/*public Kasa getAnaKasa() {
		if (anaKasa==null) {
			anaKasa = kasaFacade.getAnaKasaByOrgTesis(getSelectedIsletme());
		}
		return anaKasa;
	}

	public void setAnaKasa(Kasa anaKasa) {
		this.anaKasa = anaKasa;
	}*/

	public Rol getRol() {
		if(rol==null){
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Principal principal = (Principal) request.getUserPrincipal();
            if (principal != null) {
                rol = rolFacade.findByKullanici(principal.getName()); // Find Rol by j_username.
            }
		}
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Boolean getTekIsletme() {
		if(tekIsletme==null) {
			return getKisi().getIsletmeListesi().size() <= 1;
		}
		return tekIsletme;
	}

	public void setTekIsletme(Boolean tekIsletme) {
		this.tekIsletme = tekIsletme;
	}

	public Isletme getSelectedIsletme() {
		if (getTekIsletme()) {
			selectedIsletme = getKisi().getIsletmeListesi().get(0);
		}
		return selectedIsletme;
	}

	public void setSelectedIsletme(Isletme selectedIsletme) {
		this.selectedIsletme = selectedIsletme;
	}

	public Boolean getIsletmeSecildi() {
		if (isletmeSecildi == null ) {
			isletmeSecildi = getSelectedIsletme() != null;
		}
		return isletmeSecildi;
	}

	public void setIsletmeSecildi(Boolean isletmeSecildi) {
		this.isletmeSecildi = isletmeSecildi;
	}


}
