package com.sporsimdi.action.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.facade.IlFacade;
import com.sporsimdi.action.facade.IlceFacade;
import com.sporsimdi.action.facade.SemtFacade;
import com.sporsimdi.model.entity.Il;
import com.sporsimdi.model.entity.Ilce;
import com.sporsimdi.model.entity.Semt;

@ManagedBean(name = "ilIlceService")
@ViewScoped
public class IlIlceService implements Serializable {

	private static final long serialVersionUID = 7954300649618644765L;

	private Il il;

	@EJB
	private IlFacade ilFacade;
	
	@EJB
	private IlceFacade ilceFacade;
	
	@EJB
	private SemtFacade semtFacade;
	
	
	private List<Il> ilListesi;

	private List<Ilce> ilceListesi;

	private List<Semt> semtListesi;
	
	public Il getIl() {
		return il;
	}

	public void setIl(Il il) {
		this.il = il;
	}

	public List<Il> getIlListesi() {
		if (ilListesi == null || ilListesi.size() < 1) {
			ilListesi = ilFacade.getAll();
		}
		return ilListesi;
	}

	public void ilSecildi(Il il1) {
		ilceListesi = ilceFacade.getAllByIl(il1);
	}

	public void ilceSecildi(Ilce ilce1) {
		semtListesi = semtFacade.listByIlce(ilce1);
	}

	public List<Ilce> getIlceListesi() {
		if (ilceListesi == null || ilceListesi.size() < 1) {
			ilceListesi = ilceFacade.getAllByIl(getIl());
		}
		return ilceListesi;
	}

	public void setIlceListesi(List<Ilce> ilceListesi) {
		this.ilceListesi = ilceListesi;
	}

	public void setIlListesi(List<Il> ilListesi) {
		this.ilListesi = ilListesi;
	}

	public List<SelectItem> ilinIlceleriniGetir(Il il1) {
		List<Ilce> ilce1 = ilceFacade.getAllByIl(il1);
		List<SelectItem> ilceSelect1 = new ArrayList<SelectItem>();
		for(Ilce k : ilce1) {
			SelectItem si = new SelectItem(k, k.getAdi());
			ilceSelect1.add(si);
		}
		return ilceSelect1;
	}

	public List<Semt> getSemtListesi() {
		if (semtListesi == null || semtListesi.size() < 1) {
			semtListesi = new ArrayList<Semt>();
		}
		return semtListesi;
	}

	public void setSemtListesi(List<Semt> semtListesi) {
		this.semtListesi = semtListesi;
	}
}
