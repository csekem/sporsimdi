package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.TesisTipiFacade;
import com.sporsimdi.model.entity.TesisTipi;

@ManagedBean(name = "tesisTipiService")
@ViewScoped
public class TesisTipiService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	private TesisTipi tesisTipi;

	@EJB
	private TesisTipiFacade tesisTipiFacade;

	private List<TesisTipi> tesisTipiListesi;
	
	public TesisTipi getTesisTipi() {
		return tesisTipi;
	}

	public void setTesisTipi(TesisTipi tesisTipi) {
		this.tesisTipi = tesisTipi;
	}

	public List<TesisTipi> getTesisTipiListesi() {
		if (tesisTipiListesi == null || tesisTipiListesi.size() < 1) {
			tesisTipiListesi = tesisTipiFacade.listAll();
		}
		return tesisTipiListesi;
	}

	public void setTesisTipiListesi(List<TesisTipi> tesisTipiListesi) {
		this.tesisTipiListesi = tesisTipiListesi;
	}
	
}
