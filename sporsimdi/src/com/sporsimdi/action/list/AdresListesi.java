package com.sporsimdi.action.list;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.AdresFacade;
import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.service.IlIlceService;
import com.sporsimdi.model.entity.Adres;

@ManagedBean(name = "adresListesi")
@ViewScoped
public class AdresListesi extends ListBean<Adres> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private AdresFacade adresFacade;
	
	@EJB
	private IsletmeFacade isletmeFacade;

	@ManagedProperty(value="#{ilIlceService}")
	private IlIlceService ilIlceService;

	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.adresId");
		setModelSuper(new Adres());
	}

	@Override
	public void fillList() {
		setList(adresFacade.listByModelId(getSuperId()));
	}

	@Override
	public void fillSaving() {		
		//Tesis tesis = tesisFacade.findById(getSuperId());
//		if (isletmeListesi == null) {
//			isletmeListesi = new ArrayList<Isletme>();
//		}

		getInstance().setModelId(getSuperId());
	}
	
	public void semtSecildi() {
		getInstance().setPostaKodu(getInstance().getSemt().getPostaKodu());
	}

	public IlIlceService getIlIlceService() {
		return ilIlceService;
	}

	public void setIlIlceService(IlIlceService ilIlceService) {
		this.ilIlceService = ilIlceService;
	}

	@Override
	public void updateInstance(Adres t) {
		super.updateInstance(t);
		ilIlceService.ilSecildi(getInstance().getIl());
		ilIlceService.ilceSecildi(getInstance().getIlce());
	}

	@Override
	public void deleteInstance(Adres t) {
		super.deleteInstance(t);
		ilIlceService.ilSecildi(getInstance().getIl());
		ilIlceService.ilceSecildi(getInstance().getIlce());
	}


}
