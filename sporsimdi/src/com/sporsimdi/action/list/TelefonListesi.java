package com.sporsimdi.action.list;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.model.entity.Telefon;

@ManagedBean(name = "telefonListesi")
@ViewScoped
public class TelefonListesi extends ListBean<Telefon> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private TelefonFacade telefonFacade;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.telefonId");
		setModelSuper(new Telefon());
	}

	@Override
	public void fillList() {
		setList(telefonFacade.listByModelId(getSuperId()));
	}

	@Override
	public void fillSaving() {		
		getInstance().setModelId(getSuperId());
	}
	

}
