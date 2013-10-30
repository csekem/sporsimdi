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
import com.qrpos.action.facade.FisFacade;
import com.qrpos.action.facade.KartDurumFacade;
import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.KartDurum;
import com.qrpos.model.entity.Urun;
import com.qrpos.model.json.view.FisJsonView;
import com.qrpos.model.json.view.KartJsonView;
import com.qrpos.model.json.view.UrunJsonView;

@ManagedBean(name = "genelViewList")
public class GenelViewList {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private UrunFacade urunFacade;

	@EJB
	private KartDurumFacade kartDurumFacade;

	@EJB
	private FisFacade fisFacade;

	@ManagedProperty(value = "#{param.kasaId}")
	private Long kasaId;

	@ManagedProperty(value = "#{param.qrCardNo}")
	private String qrCardNo;

	@ManagedProperty(value = "#{param.returnFisListByQrCardNo}")
	private String returnFisListByQrCartNo;

	public String getQrCardNo() {
		return qrCardNo;
	}

	public void setQrCardNo(String qrCardNo) {
		this.qrCardNo = qrCardNo;
	}

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

		Gson gson = new Gson();

		ec.getResponseOutputWriter().write(gson.toJson(getObjectListesi()));
		fc.responseComplete();
	}

	/**
	 * @return
	 */
	public List<? extends Object> getObjectListesi() {
		if (getKasaId() != null) {

			List<Urun> urunList = urunFacade.listByKasa(getKasaId());

			List<UrunJsonView> urunJsonList = new ArrayList<UrunJsonView>();
			for (Urun urun : urunList) {
				urunJsonList.add(new UrunJsonView(urun));
			}

			return urunJsonList;

		}
		if (getQrCardNo() != null && !getQrCardNo().equals("")) {

			KartDurum kartDurum = kartDurumFacade.findByQrCard(getQrCardNo());

			List<KartJsonView> jsonList = new ArrayList<KartJsonView>();
			jsonList.add(new KartJsonView(kartDurum));

			return jsonList;

		}
		if (getReturnFisListByQrCartNo() != null && !getReturnFisListByQrCartNo().equals("")) {

			List<FisJsonView> jsonList = fisFacade.getFisJsonView(getReturnFisListByQrCartNo());

			return jsonList;

		}
		return null;
	}

	public String getReturnFisListByQrCartNo() {
		return returnFisListByQrCartNo;
	}

	public void setReturnFisListByQrCartNo(String returnFisListByQrCartNo) {
		this.returnFisListByQrCartNo = returnFisListByQrCartNo;
	}
}
