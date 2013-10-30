package com.sporsimdi.action.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.type.OdemeSekli;

@ManagedBean(name = "tahsilatService")
@ViewScoped
public class TahsilatService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@EJB
	private TahsilatFacade tahsilatFacade;

	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;

	private TahakkukDetay tahakkukDetay;

	private List<Tahsilat> tahsilatListesi;

	private Tahsilat tahsilat;

	private List<OdemeSekli> odemeSekliListesi;

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id == null) {
			return "/menu/tahsilatYap?faces-redirect=true";
		} else {
			return "/menu/tahsilatYap?faces-redirect=true&amp;includeViewParams=true&amp;tahakkukDetayId=" + id;
		}
	}

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String pTahakkukDetayId = params.get("tahakkukDetayId");

		if (pTahakkukDetayId != null && !pTahakkukDetayId.isEmpty()) {
			setTahakkukDetay(tahakkukDetayFacade.findById(Long.valueOf(pTahakkukDetayId)));
		} else {
			TahakkukDetay detay = new TahakkukDetay();
			setTahakkukDetay(detay);
		}
	}

	public void kalanTutarSifirla() {
		tahakkukDetay.setKalanTutar(new BigDecimal(0));
	}

	public void tahsilatYap() {

	}

	public List<Tahsilat> tahsilatListele(TahakkukDetay detay) {
		if (detay != null) {
			return tahsilatFacade.listByTahakkukDetay(detay);
		} else {
			return null;
		}
	}

	public void tahsilatIptalEt(Tahsilat tahsilat) {
		tahsilatFacade.remove(tahsilat);
	}

	public TahakkukDetay getTahakkukDetay() {
		return tahakkukDetay;
	}

	public void setTahakkukDetay(TahakkukDetay tahakkukDetay) {
		this.tahakkukDetay = tahakkukDetay;
	}

	public List<Tahsilat> getTahsilatListesi() {
		if (tahsilatListesi == null || tahsilatListesi.size() < 1) {
			tahsilatListesi = tahsilatFacade.listByTahakkukDetay(tahakkukDetay);
		}
		return tahsilatListesi;
	}

	public void setTahsilatListesi(List<Tahsilat> tahsilatListesi) {
		this.tahsilatListesi = tahsilatListesi;
	}

	public Tahsilat getTahsilat() {
		if (tahsilat == null) {
			tahsilat = new Tahsilat();
			tahsilat.setTahsilTutari(tahakkukDetay.getKalanTutar());
			tahsilat.setTahakkukDetay(tahakkukDetay);
			tahsilat.setTahsilTarihi(new Date());
		}
		return tahsilat;
	}

	public void setTahsilat(Tahsilat tahsilat) {
		this.tahsilat = tahsilat;
	}

	public List<OdemeSekli> getOdemeSekliListesi() {
		if (odemeSekliListesi == null) {
			odemeSekliListesi = Arrays.asList(OdemeSekli.values());
		}
		return odemeSekliListesi;
	}

	public void setOdemeSekliListesi(List<OdemeSekli> odemeSekliListesi) {
		this.odemeSekliListesi = odemeSekliListesi;
	}

}
