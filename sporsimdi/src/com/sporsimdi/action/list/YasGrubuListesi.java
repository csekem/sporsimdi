package com.sporsimdi.action.list;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.YasGrubuFacade;
import com.sporsimdi.model.entity.YasGrubu;

@ManagedBean(name = "yasGrubuListesi")
@ViewScoped
public class YasGrubuListesi extends ListBean<YasGrubu> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private YasGrubuFacade yasGrubuFacade;
	
	@EJB
	private IsletmeFacade isletmeFacade;

	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.isletmeId");
		setModelSuper(isletmeFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(yasGrubuFacade.listAllByIsletme(getSuperId()));
	}

	@Override
	public void fillSaving() {		
		getInstance().setIsletme(isletmeFacade.findById(getSuperId()));
	}

}
