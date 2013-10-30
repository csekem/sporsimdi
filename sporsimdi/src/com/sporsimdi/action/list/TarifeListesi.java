package com.sporsimdi.action.list;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.action.facade.TarifeFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Tarife;
import com.sporsimdi.model.entity.TarifeDetay;

@ManagedBean(name = "tarifeListesi")
@ViewScoped
public class TarifeListesi extends ListBean<Tarife> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private TarifeFacade tarifeFacade;
	
	@EJB
	private GrupFacade grupFacade;
	
	private BigDecimal tumTaksitler;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.grupId");
		setModelSuper(grupFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(tarifeFacade.listByGrup(getSuperId()));
	}

	@Override
	public void disableNewerItem() {
		super.disableNewerItem();
	}

	@Override
	public void fillSaving() {		
		getInstance().setGrup((Grup)getModelSuper());
	}

	@Override
	public String save() {
		String ret = super.save();
/*		for (TarifeDetay detay : getInstance().getTarifeDetayListesi()) {
			if (detay.getId()!=null) {
				getEntityManagerDao().updateObject(detay);
			} else {
				getEntityManagerDao().createObject(detay);
			}
		}
*/		return ret;
	}

	@Override
	public String delete() {
		String ret = super.delete();
		return ret;
	}
	
	@Override
	public Tarife createInstance() {
		Tarife trf = super.createInstance();
		return trf;
	}

	@Override
	public void enableNewerItem() {
		getInstance().setAidatGun(((Grup)getModelSuper()).getAidatGun() );

		super.enableNewerItem();
	}

	public BigDecimal getTumTaksitler() {
		return tumTaksitler;
	}

	public void setTumTaksitler(BigDecimal tumTaksitler) {
		this.tumTaksitler = tumTaksitler;
	}


	public void taksitSayisiYok() {
		if (getInstance().isHerAy()) {		
			getInstance().setTaksitSayisi(0);
			taksitTablosuOluştur();
		}
	}

	public void taksitTablosuOluştur() {
		getInstance().setTarifeDetayListesi(new ArrayList<TarifeDetay>());
		for (int i = 0; i < getInstance().getTaksitSayisi(); i++) {
			TarifeDetay detay = new TarifeDetay();
			detay.setTaksitNo(i+1);
			detay.setTaksitTutar(getTumTaksitler());
			detay.setTarife(getInstance());
			getInstance().getTarifeDetayListesi().add(detay);
		}
		toplamTutarOluştur();
	}

	public void toplamTutarOluştur() {
		BigDecimal toplam = new BigDecimal(0);
		for (TarifeDetay detay: getInstance().getTarifeDetayListesi()) {
			if (detay.getTaksitTutar()!=null) {
				toplam = toplam.add(detay.getTaksitTutar());
			}
		}
		getInstance().setToplamTutar(toplam);
	}

}
