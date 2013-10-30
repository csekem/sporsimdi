package com.sporsimdi.action.list;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.Tesis;

@ManagedBean(name = "orgTesisListesi")
@ViewScoped
public class OrgTesisListesi extends ListBean<OrgTesis> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private OrgTesisFacade orgTesisFacade;
	
	@EJB
	private OrganizasyonFacade organizasyonFacade;
	
	@EJB
	private KasaFacade kasaFacade;

	private Organizasyon organizasyon;

	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.organizasyonId");
		setModelSuper(organizasyonFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(orgTesisFacade.listByOrganizasyon(getSuperId()));
	}

	@Override
	public void disableNewerItem() {
		super.disableNewerItem();
	}

	@Override
	public void fillSaving() {		
		getInstance().setOrganizasyon((Organizasyon)getModelSuper());
	}

	@Override
	public String save() {
		fillSaving();
		if (isManaged()) {
			setInstance((OrgTesis) getEntityManagerDao().updateObject(getInstance()));
		} else {
			getEntityManagerDao().createObject(getInstance());
		}

		anaKasaKaydet();

		setNewerItem(false);
		setDeletion(false);
		clearInstance();

		fillList();

		return "saved";
	}

	private void anaKasaKaydet() {
		try {
			if (kasaFacade.getAnaKasaByOrgTesis(getInstance())==null) {
				Kasa kasa = new Kasa();
				kasa.setAdi(getInstance().getAdi()+" ANAKASA");
				kasa.setOrgTesis(getInstance());
				kasa.setAnaKasa(true);
				kasaFacade.persist(kasa);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String delete() {
		kasaSil();
		String ret = super.delete();
		return ret;
	}

	private void kasaSil() {
		List<Kasa> listKasa = kasaFacade.listAllByOrgTesis(getInstance());
		for (Kasa kasa : listKasa) {
			kasaFacade.remove(kasa);
		}
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

	public void organizasyonBelirle(Organizasyon organizasyon) {
		setOrganizasyon(organizasyon);
	}
	
	public void tesisSecildi(Tesis tes) {  
		getInstance().setAdi(tes.getAdi());
	}  

}
