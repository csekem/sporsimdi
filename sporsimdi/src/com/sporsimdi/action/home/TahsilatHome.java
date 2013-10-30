package com.sporsimdi.action.home;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.KalemFacade;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.action.service.DefterService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.type.KalemTuru;
import com.sporsimdi.model.type.TahsilatTipi;

@ManagedBean(name = "tahsilatHome")
@ViewScoped
public class TahsilatHome extends HomeBean<Tahsilat> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private TahsilatFacade tahsilatFacade;

	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;

	@EJB
	private TahakkukFacade tahakkukFacade;

	@EJB
	private KasaFacade kasaFacade;

	@EJB
	private UyeGrupFacade uyeGrupFacade;

	private TahakkukDetay tahakkukDetay;

	private Tahakkuk tahakkuk;

	private List<Tahsilat> tahsilatListesi;

	private boolean kalanVar;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{defterHome}")
	private DefterHome defterHome;

	@ManagedProperty(value = "#{defterService}")
	private DefterService defterService;

	@EJB
	private KalemFacade kalemFacade;

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String pTahakkukDetayId = params.get("tahakkukDetayId");

		if (pTahakkukDetayId != null && !pTahakkukDetayId.isEmpty()) {
			setTahakkukDetay(tahakkukDetayFacade.findById(Long.valueOf(pTahakkukDetayId)));
		}

		try {
			tahakkuk = tahakkukFacade.getByTahakkukDetay(tahakkukDetay);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Tahsilat tahsilat = new Tahsilat();
		BigDecimal kalanTutar = tahakkukDetay.getTaksitTutari();
		for (Tahsilat tah : getTahsilatListesi()) {
			kalanTutar = kalanTutar.subtract(tah.getTahsilTutari().add(tah.getIndirimTutari()));
		}
		// tahsilat.setTahsilTutar(tahakkukDetay.getKalanTutar());
		tahsilat.setTahsilTutari(kalanTutar);
		tahsilat.setIndirimTutari(BigDecimal.ZERO);
		tahsilat.setTahakkukDetay(tahakkukDetay);
		tahsilat.setTahsilTarihi(new Date());
		tahsilat.setTahsilatTipi(TahsilatTipi.TAHSILAT);
		setInstance(tahsilat);

		setKalanVar(tahsilat.getTahsilTutari().compareTo(BigDecimal.ZERO) != 0);
	}

	public void sayfaLoad(TahakkukDetay detay) {

		setTahakkukDetay(detay);

		try {
			tahakkuk = tahakkukFacade.getByTahakkukDetay(tahakkukDetay);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Tahsilat tahsilat = new Tahsilat();
		BigDecimal kalanTutar = (tahakkukDetay.isHerAy() ? tahakkukDetay.getHerAyTutar() : tahakkukDetay
				.getTaksitTutari());
		for (Tahsilat tah : getTahsilatListesi()) {
			kalanTutar = kalanTutar.subtract(tah.getTahsilTutari().add(tah.getIndirimTutari()));
		}

		tahsilat.setTahsilTutari(kalanTutar);
		tahsilat.setIndirimTutari(BigDecimal.ZERO);
		tahsilat.setTahakkukDetay(tahakkukDetay);
		tahsilat.setTahsilTarihi(new Date());
		tahsilat.setTahsilatTipi(TahsilatTipi.TAHSILAT);
		setInstance(tahsilat);

		setKalanVar(tahsilat.getTahsilTutari().compareTo(BigDecimal.ZERO) != 0);
	}

	public String getTahsilatIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("tahsilatId");
	}

	public void tahsilatIptalEt(Tahsilat tahsilat) throws Exception {
		Calendar cal = Calendar.getInstance(new Locale("tr"));
		cal.setTime(tahsilatFacade.getTahakkukDetayByTahsilat(tahsilat.getId()).getVadeTarihi());
		String odemeAy = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("tr"));

		defterHome.sayfaLoad();
		defterHome.getInstance().setTarih(tahsilat.getTahsilTarihi());
		defterHome.getInstance().setKalem(
				kalemFacade.getByAdi(sessionObject.getSelectedIsletme(), KalemTuru.AIDAT.getI18nKey()));
		defterHome.getInstance().setAciklama(
				"[Otomatik Kayıt]: " + (tahsilatFacade.getUyeByTahsilat(tahsilat.getId())).getAdSoyad() + " " + odemeAy
						+ " ayı  ödemesi iptali.");
		defterHome.getInstance().setGider(tahsilat.getTahsilTutari());
		defterHome.getInstance().setGelir(new BigDecimal(0));
		defterHome.getInstance().setReferenceModel(tahsilat);
		defterHome.getInstance().setReferenceId(tahsilatFacade.getTahakkukDetayByTahsilat(tahsilat.getId()).getId());

		defterService.sayfaLoad();
		defterService.setSelectedKasa(kasaFacade.getAnaKasaByOrgTesis(tahakkukFacade.getUyeGrup(getTahakkuk())
				.getGrup().getOrgTesis()));

		defterHome.saveWithoutNavigation();

		delete(tahsilat);

		tahsilatListesi.clear();
	}

	public String tahsilatYap() throws Exception {
		String retSave;
		retSave = saveWithoutNavigation();

		Calendar cal = Calendar.getInstance(new Locale("tr"));
		cal.setTime(tahsilatFacade.getTahakkukDetayByTahsilat(getInstance().getId()).getVadeTarihi());
		String odemeAy = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("tr"));

		defterHome.sayfaLoad();

		defterHome.getInstance().setTarih(getInstance().getTahsilTarihi());
		defterHome.getInstance().setKalem(
				kalemFacade.getByAdi(sessionObject.getSelectedIsletme(), KalemTuru.AIDAT.getI18nKey()));
		defterHome.getInstance().setAciklama(
				"[Otomatik Kayıt]: " + (tahsilatFacade.getUyeByTahsilat(getInstance().getId())).getAdSoyad() + " "
						+ odemeAy + " ayı ödemesi.");
		defterHome.getInstance().setGider(new BigDecimal(0));
		defterHome.getInstance().setGelir(getInstance().getTahsilTutari());
		// defterHome.getInstance().setReferenceModel(getInstance());
		defterHome.getInstance().setReferenceId(
				tahsilatFacade.getTahakkukDetayByTahsilat(getInstance().getId()).getId());

		defterService.sayfaLoad();
		defterService.setSelectedKasa(kasaFacade.getAnaKasaByOrgTesis(tahakkukFacade.getUyeGrup(getTahakkuk())
				.getGrup().getOrgTesis()));

		defterHome.saveWithoutNavigation();

		tahsilatListesi.clear();

		return retSave;
	}

	public void kalanTutarOlustur() {
		instance.setIndirimTutari(tahakkukDetay.getTaksitTutari().subtract(instance.getTahsilTutari()));
	}

	public String kalanTutarSifirla() throws Exception {
		/*
		 * instance.setIskonto(getInstance().getTahsilTutar());
		 * instance.setTahsilTutar(BigDecimal.ZERO);
		 * instance.setTahsilatTipi(TahsilatTipi.INDIRIM);
		 * instance.setOdeyenKisi(null); instance.setOdemeSekli(null);
		 */
		return saveWithoutNavigation();
	}

	@Override
	public String save() {
		return super.save();
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

	public boolean isKalanVar() {
		return kalanVar;
	}

	public void setKalanVar(boolean kalanVar) {
		this.kalanVar = kalanVar;
	}

	public Tahakkuk getTahakkuk() {
		return tahakkuk;
	}

	public void setTahakkuk(Tahakkuk tahakkuk) {
		this.tahakkuk = tahakkuk;
	}

	public DefterHome getDefterHome() {
		return defterHome;
	}

	public void setDefterHome(DefterHome defterHome) {
		this.defterHome = defterHome;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public DefterService getDefterService() {
		return defterService;
	}

	public void setDefterService(DefterService defterService) {
		this.defterService = defterService;
	}

}