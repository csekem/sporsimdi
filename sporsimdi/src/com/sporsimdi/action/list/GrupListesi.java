package com.sporsimdi.action.list;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.OrgTesis;

@ManagedBean(name = "grupListesi")
@ViewScoped
public class GrupListesi extends ListBean<Grup> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private GrupFacade grupFacade;
	
	@EJB
	private OrgTesisFacade orgTesisFacade;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.orgTesisId");
		setModelSuper(orgTesisFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(grupFacade.listByOrgTesis(getSuperId()));
	}

	@Override
	public void disableNewerItem() {
		super.disableNewerItem();
	}

	@Override
	public void fillSaving() {		
		getInstance().setOrgTesis((OrgTesis)getModelSuper());
		//getInstance().setSahaTipiListesi(subSahaTipleri.getTarget());
	}

	@Override
	public String save() {
		String ret = super.save();
		return ret;
	}

	@Override
	public String delete() {
		String ret = super.delete();
		return ret;
	}
	
}
