package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.SahaFacade;
import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.type.AcikKapali;

@ManagedBean(name = "sahaService")
@ViewScoped
public class SahaService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;
	
	@EJB
	private SahaFacade sahaFacade;	

	private List<AcikKapali> acikKapaliListesi;
	
	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/sahalar?faces-redirect=true";			
		} else {
			return "/menu/sahalar?faces-redirect=true&amp;includeViewParams=true&amp;sporsimdi.com.tesisId=" + id;			
		}
	}

	public List<AcikKapali> getAcikKapaliListesi() {
		if (acikKapaliListesi==null) {			
			acikKapaliListesi = Arrays.asList(AcikKapali.values());
		}
		return acikKapaliListesi;
	}

	public void setAcikKapaliListesi(List<AcikKapali> acikKapaliListesi) {
		this.acikKapaliListesi = acikKapaliListesi;
	}

	public String sahaKisaAciklamasi(Saha saha) {
		saha = sahaFacade.findWideById(saha.getId());
		String str = saha.getAdi() ;
		for (int i = 0 ; i < saha.getSahaTipiListesi().size(); i++) {
			if (i==0) {
				str = str + " ( ";
			}
			str = str + saha.getSahaTipiListesi().get(i).getAdi();
			if (i==saha.getSahaTipiListesi().size()-1) {
				str = str + " )";
			} else {				
				str = str + ", ";
			}
		}
		return str;
	}

	public String sahaUzunAciklamasi(Saha saha) {
		String str = sahaKisaAciklamasi(saha) + 
					" YüzÖlçüm(m2): " + saha.getYuzOlcumM2() +
					" (En/Boy: " + saha.getEn() +
					"/" + saha.getBoy() + ")" +
					" Yükseklik: " + saha.getYukseklik() +
					" " +saha.getAcikKapali();		
		return str;
	}
	
}