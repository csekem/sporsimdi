package com.sporsimdi.action.home;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.TarifeFacade;
import com.sporsimdi.model.entity.Tarife;

@ManagedBean(name = "tarifeHome")
@ViewScoped
public class TarifeHome extends HomeBean<Tarife> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private TarifeFacade tarifeFacade;

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String ptarifeId = getTarifeIdParam(fc);

		if (ptarifeId != null && !ptarifeId.isEmpty()) {
			setInstance(tarifeFacade.findById(Long.valueOf(ptarifeId)));
		} else {
			Tarife trf = new Tarife();
			//String pDonemId = getDonemIdParam(fc);
			setInstance(trf);			
		}
	}

	public String getTarifeIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("tarifeId");
	}


	public String getDonemIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("donemId");
	}

	public void fixByTaksitTutar() {
		/** TODO düzelt
		if (getInstance().getTaksitTutar()!=null && getInstance().getTaksitSayisi()!=0) {		
			getInstance().setToplamTutar(getInstance().getTaksitTutar().multiply(new BigDecimal(getInstance().getTaksitSayisi())));
		}
		*/
	}

}