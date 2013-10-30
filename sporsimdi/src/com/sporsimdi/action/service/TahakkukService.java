package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;

@ManagedBean(name="tahakkukService")
@ViewScoped
public class TahakkukService implements Serializable {

	private static final long serialVersionUID = 5390492535300935394L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;
	
	@EJB
	private UyeGrupFacade uyeGrupFacade;
	
	@EJB
	private TahakkukFacade tahakkukFacade;
	
	public List<TahakkukDetay> tahakkukDetayListele(Tahakkuk tahakkuk) {  
		return tahakkukDetayFacade.listByTahakkuk(tahakkuk);			
	}  

	public Tahakkuk uyeninTahakkuku(Uye uye, Grup grup) {
		try {
			UyeGrup uyeGrup = uyeGrupFacade.getByGrupUye(grup, uye);
			Tahakkuk tahakkuk= tahakkukFacade.getByUyeGrup(uyeGrup);
			return tahakkuk;			
		} catch (Exception e) {
			return null;
		}
	}
}
