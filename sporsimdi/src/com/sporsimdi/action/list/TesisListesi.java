package com.sporsimdi.action.list;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Tesis;

@ManagedBean(name = "tesisListesi")
@ViewScoped
public class TesisListesi extends ListBean<Tesis> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private TesisFacade tesisFacade;
	
	@EJB
	private IsletmeFacade isletmeFacade;

	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.isletmeId");
		setModelSuper(isletmeFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(tesisFacade.listAllByIsletme(getSuperId()));
	}

	@Override
	public void fillSaving() {		
		List<Isletme> isletmeListesi = isletmeFacade.listByTesis(getInstance());
		if (isletmeListesi == null) {
			isletmeListesi = new ArrayList<Isletme>();
		}
		boolean newTesis = true;
		for (Isletme isletme : isletmeListesi) {
			if (isletme.getId().compareTo(getSuperId()) == 0) {
				newTesis = false;
				break;
			}
		}
		if (newTesis) {
			isletmeListesi.add(isletmeFacade.findById(getSuperId()));
			getInstance().setIsletmeListesi(isletmeListesi);
		}
	}

}
