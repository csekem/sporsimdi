package com.sporsimdi.action.list;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import com.sporsimdi.action.facade.SahaFacade;
import com.sporsimdi.action.facade.SahaTipiFacade;
import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.entity.SahaTipi;
import com.sporsimdi.model.entity.Tesis;

@ManagedBean(name = "sahaListesi")
@ViewScoped
public class SahaListesi extends ListBean<Saha> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private SahaFacade sahaFacade;
	
	@EJB
	private TesisFacade tesisFacade;
	
	@EJB
	private SahaTipiFacade sahaTipiFacade;

	private Tesis tesis;
	
	private DualListModel<SahaTipi> subSahaTipleri;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.tesisId");
		setModelSuper(tesisFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(sahaFacade.listAllByTesis(getSuperId()));
	}

	@Override
	public void disableNewerItem() {
		super.disableNewerItem();
		subSahaTipleri = null;
	}

	@Override
	public void fillSaving() {		
		Tesis tesis = tesisFacade.findById(getSuperId());
//		if (isletmeListesi == null) {
//			isletmeListesi = new ArrayList<Isletme>();
//		}

		getInstance().setTesis(tesis);
		getInstance().setSahaTipiListesi(subSahaTipleri.getTarget());
	}

	@Override
	public String save() {
		String ret = super.save();
		subSahaTipleri = null;
		return ret;
	}

	@Override
	public String delete() {
		String ret = super.delete();
		subSahaTipleri = null;
		return ret;
	}

	public Tesis getTesis() {
		if (tesis == null) {
			tesis = (Tesis) getModelSuper();
		}
		return tesis;
	}

	public void setTesis(Tesis tesis) {
		this.tesis = tesis;
	}

	public DualListModel<SahaTipi> getSubSahaTipleri() {
		if(subSahaTipleri==null) {
			List<SahaTipi> listAll = sahaTipiFacade.listAll();
			List<SahaTipi> listBySaha = sahaTipiFacade.listBySaha(getInstance());
			
			for (SahaTipi sahaTipi : listBySaha) {
				listAll.remove(sahaTipi);
			}
			subSahaTipleri = new DualListModel<SahaTipi>(listAll, listBySaha);
		}
		return subSahaTipleri;
	}

	public void setSubSahaTipleri(DualListModel<SahaTipi> subSahaTipleri) {
		this.subSahaTipleri = subSahaTipleri;
	}


}
