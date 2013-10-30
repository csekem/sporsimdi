package com.sporsimdi.action.list;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.WebIletisimFacade;
import com.sporsimdi.model.entity.WebIletisim;

@ManagedBean(name = "webIletisimListesi")
@ViewScoped
public class WebIletisimListesi extends ListBean<WebIletisim> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private WebIletisimFacade webIletisimFacade;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.webIletisimId");
		setModelSuper(new WebIletisim());
	}

	@Override
	public void fillList() {
		setList(webIletisimFacade.listByModelId(getSuperId()));
	}

	@Override
	public void fillSaving() {		
		getInstance().setModelId(getSuperId());
	}
	

}
