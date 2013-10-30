package com.sporsimdi.action.home;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import com.sporsimdi.action.facade.KisiIliskiFacade;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.type.YakinlikDerecesi;

@ManagedBean(name = "kisiIliskiHome")
@ViewScoped
public class KisiIliskiHome extends HomeBean<KisiIliski> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private KisiIliskiFacade kisiIliskiFacade;
	
	private Kisi kisi;
	
	private Telefon cepTel;
	
	@ManagedProperty(value = "#{telefonHome}")
	private TelefonHome telefonHome;
	
	@ManagedProperty(value = "#{kisiHome}")
	private KisiHome iliskiliKisiHome;
	

	public Kisi getKisi() {
		return kisi;
	}

	public void setKisi(Kisi kisi) {
		this.kisi = kisi;
	}

	public Telefon getCepTel() {
		return cepTel;
	}

	public void setCepTel(Telefon cepTel) {
		this.cepTel = cepTel;
	}

	public TelefonHome getTelefonHome() {
		return telefonHome;
	}

	public void setTelefonHome(TelefonHome telefonHome) {
		this.telefonHome = telefonHome;
	}

	public KisiHome getIliskiliKisiHome() {
		return iliskiliKisiHome;
	}

	public void setIliskiliKisiHome(KisiHome iliskiliKisiHome) {
		this.iliskiliKisiHome = iliskiliKisiHome;
	}

	@Override
	public String save() {		
		String ret = super.save();
		return ret;
	}

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pUyeId = fc.getExternalContext().getRequestParameterMap().get("uyeId");

		if (pUyeId != null && !pUyeId.isEmpty()) {
			setInstance(kisiIliskiFacade.findById(Long.valueOf(pUyeId)));
		}
	}

	public void sayfaLoad(Kisi kisi, YakinlikDerecesi yakinlik) {
		setKisi(kisi);
		KisiIliski iliski  = new KisiIliski();
		iliski.setKisi(kisi);
		iliski.setYakinlikDerecesi(yakinlik);

		if (kisi != null && kisi.getId() != null) {
			try {
				List<KisiIliski> list = kisiIliskiFacade.listByKisi(kisi);					
				for (KisiIliski kisiIliski : list) {
					if (kisiIliski.getYakinlikDerecesi().equals(yakinlik) ) {
						iliski = kisiIliski;
					}
				}
			} catch (EJBException e) {
				if (e.getCausedByException() instanceof NoResultException) {
				} else {
					throw e;
				}
			}
		}
		iliskiliKisiHome.sayfaLoad(iliski.getIliskiliKisi());
		iliski.setIliskiliKisi(iliskiliKisiHome.getInstance());

		setInstance(iliski);
		//telefonHome.sayfaLoad(getInstance().getIliskiliKisi().getId());
	}

	@Override
	public String saveWithoutNavigation() throws Exception {
		iliskiliKisiHome.saveWithoutNavigation();
		return super.saveWithoutNavigation();
	}



}