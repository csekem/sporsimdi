package com.sporsimdi.action.list;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.type.OrganizasyonTipi;

@ManagedBean(name = "organizasyonListesi")
@ViewScoped
public class OrganizasyonListesi extends ListBean<Organizasyon> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private OrganizasyonFacade organizasyonFacade;
	
	@ManagedProperty(value="#{sessionObject}")
	private SessionObject sessionObject;
	
	private List<OrganizasyonTipi> organizasyonTipiListesi;
	
	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.isletmeId");
		setSuperId(sessionObject.getSelectedIsletme().getId());
		setModelSuper(sessionObject.getSelectedIsletme());
	}

	@Override
	public void fillList() {
		setList(organizasyonFacade.listByIsletme((Isletme)getModelSuper()));
	}

	@Override
	public void disableNewerItem() {
		super.disableNewerItem();
	}

	@Override
	public void fillSaving() {		
		getInstance().setIsletme(sessionObject.getSelectedIsletme());
		//getInstance().setSahaTipiListesi(subSahaTipleri.getTarget());
	}

	@Override
	public String save() {
		String ret = super.save();
		menuService.setMenuModel(null);
		return ret;
	}

	@Override
	public String delete() {
		String ret = super.delete();
		menuService.setMenuModel(null);
		return ret;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<OrganizasyonTipi> getOrganizasyonTipiListesi() {
		if (organizasyonTipiListesi == null || organizasyonTipiListesi.size() < 1) {
			organizasyonTipiListesi = Arrays.asList(OrganizasyonTipi.values());
		}
		return organizasyonTipiListesi;
	}

	public void setOrganizasyonTipiListesi(
			List<OrganizasyonTipi> organizasyonTipiListesi) {
		this.organizasyonTipiListesi = organizasyonTipiListesi;
	}


}
