package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.KisiIliskiFacade;
import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.type.TelefonTipi;
import com.sporsimdi.model.type.YakinlikDerecesi;

@ManagedBean(name="kisiIliskiService")
@ViewScoped
public class KisiIliskiService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;
	
	@EJB
	private KisiIliskiFacade kisiIliskiFacade;
	
	private List<KisiIliski> kisiIliskiListesi;

	private HashMap<Long, String> mapAnneBaba;

	@EJB
	private TelefonFacade telefonFacade;

	public List<KisiIliski> getkisiIliskiListesi() {
		if (kisiIliskiListesi == null || kisiIliskiListesi.size() < 1) {
			//kisiIliskiListesi = kisiIliskiFacade.listByUye(uye);
		}
		return kisiIliskiListesi;
	}

	public void setkisiIliskiListesi(List<KisiIliski> kisiIliskiListesi) {
		this.kisiIliskiListesi = kisiIliskiListesi;
	}

	public HashMap<Long, String> getMapAnneBaba() {
		if (mapAnneBaba == null) {
			mapAnneBaba = new HashMap<Long, String>();
		}
		return mapAnneBaba;
	}

	public void setMapAnneBaba(HashMap<Long, String> mapAnneBaba) {
		this.mapAnneBaba = mapAnneBaba;
	}

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/kisiIliskiTanimlama?faces-redirect=true";			
		} else {
			return "/menu/kisiIliskiTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;uyeId=" + id;			
		}
	}
	
	public String anneBabaGetir(Kisi kisi) throws Exception {
		if (!getMapAnneBaba().containsKey(kisi.getId())) {
			String anneBaba = "";
			List<KisiIliski> listeAnneBaba = kisiIliskiFacade.listByKisi(kisi);
			for (KisiIliski kisiIliski : listeAnneBaba) {
				if (kisiIliski.getYakinlikDerecesi().equals(YakinlikDerecesi.ANNE)) {
					Kisi anne = kisiIliski.getIliskiliKisi();
					Telefon telefon = telefonFacade.getByModelIdTip(kisiIliski.getIliskiliKisi().getId(), TelefonTipi.CEP);
					if (anne.getAd() != null && telefon !=null && telefon.getTelefon() != null ) {
						anneBaba = "ANNE: " + anne.getAdSoyad() + " " + telefon.getTelefon();
					}
				}
				if (kisiIliski.getYakinlikDerecesi().equals(YakinlikDerecesi.BABA)) {
					Kisi baba = kisiIliski.getIliskiliKisi();
					Telefon telefon = telefonFacade.getByModelIdTip(kisiIliski.getIliskiliKisi().getId(), TelefonTipi.CEP);
					if (baba.getAd() != null && telefon !=null && telefon.getTelefon() != null ) {
						if (anneBaba.length()>0) {
							anneBaba = anneBaba + " , BABA: ";
						} else {
							anneBaba = "BABA: ";
						}
						anneBaba = anneBaba + baba.getAdSoyad() + " " + telefon.getTelefon();
					}
				}
			}
			getMapAnneBaba().put(kisi.getId(), anneBaba);
		}
		return getMapAnneBaba().get(kisi.getId());
	}
	
	public void clearMapAnneBaba(Long kisiId) {
		getMapAnneBaba().remove(kisiId);
	}

}
