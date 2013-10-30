package com.qrpos.action.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.gson.Gson;
import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Urun;
import com.qrpos.model.json.view.UrunJsonView;

@ManagedBean(name = "urunList")
public class UrunList {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private UrunFacade urunFacade;

	@ManagedProperty(value = "#{param.kasaId}")
	private Long kasaId;

	public Long getKasaId() {
		return kasaId;
	}

	public void setKasaId(Long kasaId) {
		this.kasaId = kasaId;
	}

	public void renderJson(ComponentSystemEvent event) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		ec.setResponseContentType("application/json");
		ec.setResponseCharacterEncoding("UTF-8");

		List<Urun> urunList = new ArrayList<Urun>();

		Gson gson = new Gson();

		urunList = urunFacade.listByKasa(getKasaId());

		List<UrunJsonView> urunJsonList = new ArrayList<UrunJsonView>();
		for (Urun urun : urunList) {
			urunJsonList.add(new UrunJsonView(urun));
		}

		ec.getResponseOutputWriter().write(gson.toJson(urunJsonList));
		fc.responseComplete();
	}
}
