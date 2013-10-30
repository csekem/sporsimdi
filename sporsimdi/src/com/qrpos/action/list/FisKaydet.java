package com.qrpos.action.list;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.qrpos.action.facade.FisFacade;
import com.qrpos.model.json.FisJson;
import com.qrpos.model.json.FisKaydetSonucJson;

@ManagedBean(name = "fisKaydet")
public class FisKaydet {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private FisFacade fisFacade;

	public void renderJson(ComponentSystemEvent event) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		ec.setResponseContentType("application/json");
		ec.setResponseCharacterEncoding("UTF-8");

		HttpServletRequest req = (HttpServletRequest) ec.getRequest();
		Gson gson = new Gson();

		BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));

		FisJson fisJson = gson.fromJson(bf, FisJson.class);
		// FisJson fisJson = new FisJson();
		// fisJson.setQrCardNo("3");
		// fisJson.setTerminalId(0L);
		//
		// FisDetayJson detayJson = new FisDetayJson();
		// detayJson.setUrunId(5L);
		// detayJson.setAdet(BigDecimal.ONE);
		// fisJson.getFisDetayListesi().add(detayJson);
		FisKaydetSonucJson fisKaydetSonucJson;
		if (fisJson.getFisId() == null) {

			fisKaydetSonucJson = fisFacade.fisKaydet(fisJson);
		} else {
			fisKaydetSonucJson = fisFacade.fisIptal(fisJson);

		}

		ec.getResponseOutputWriter().write(gson.toJson(fisKaydetSonucJson));
		fc.responseComplete();
	}
}
