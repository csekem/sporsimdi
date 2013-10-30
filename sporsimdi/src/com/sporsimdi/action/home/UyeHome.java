package com.sporsimdi.action.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.action.facade.UyeFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tarife;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;

@ManagedBean(name = "uyeHome")
@ViewScoped
public class UyeHome extends HomeBean<Uye> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private UyeFacade uyeFacade;
	
	@EJB
	private TahakkukFacade tahakkukFacade;

	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;

	@ManagedProperty(value = "#{tahakkukHome}")
	private TahakkukHome tahakkukHome;
	
	@ManagedProperty(value = "#{tahakkukDetayHome}")
	private TahakkukDetayHome tahakkukDetayHome;

	@ManagedProperty (value = "#{sessionObject}")
    private SessionObject sessionObject;
	
	private Tarife tarife;
	
	private Tahakkuk tahakkuk;
	
	private Kisi anne;
	
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	private UyeGrup uyeGrup = new UyeGrup();


	public String saveGenel() throws Exception {		
		List<Isletme> isletmeListe = new ArrayList<Isletme>();
		isletmeListe.add(sessionObject.getSelectedIsletme());
		getInstance().setIsletmeListesi(isletmeListe);
		return super.saveWithoutNavigation();
	}

	@Override
	public Uye createInstance() {
		try {
			Uye uye = new Uye();
			return uye;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public UyeGrup getUyeGrup() {
		return uyeGrup;
	}

	public void setUyeGrup(UyeGrup uyeGrup) {
		this.uyeGrup = uyeGrup;
	}

	
	public String getUyeIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("uyeId");
	}

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pUyeId = getUyeIdParam(fc);

		if (pUyeId != null && !pUyeId.isEmpty()) {
			setInstance(uyeFacade.findByIdEager(Long.valueOf(pUyeId)));
			if (getInstance().getGrupListesi()!=null && getInstance().getGrupListesi().size()>0) {
				setUyeGrup(getInstance().getGrupListesi().iterator().next());
					try {
						setTahakkuk(tahakkukFacade.getByUyeGrup(getUyeGrup()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getTahakkuk().setTahakkukDetayListesi(tahakkukDetayFacade.listByTahakkuk(tahakkuk));
					setTarife(getTahakkuk().getTarife());
			}
		}
	}

	public void tahakkukOlustur() {
		tahakkukHome.tahakkukOlustur(getUyeGrup(),getTarife());
		setTahakkuk(tahakkukHome.getInstance());
	}
	
	public void tahakkukSil() {
		setTahakkuk(null);
	}
	
	public void toplamTutarOlustur() {
		BigDecimal toplamTutar = new BigDecimal(0);
		for (TahakkukDetay dty : getTahakkuk().getTahakkukDetayListesi()) {
			toplamTutar = toplamTutar.add(dty.getTaksitTutari());
		}
		getTahakkuk().setToplamTutar(toplamTutar);
		getTahakkuk().setToplamIndirim(getTarife().getToplamTutar().subtract(toplamTutar));
	}

	public Tarife getTarife() {
		return tarife;
	}

	public void setTarife(Tarife tarife) {
		this.tarife = tarife;
	}

	public TahakkukHome getTahakkukHome() {
		return tahakkukHome;
	}

	public void setTahakkukHome(TahakkukHome tahakkukHome) {
		this.tahakkukHome = tahakkukHome;
	}

	public Tahakkuk getTahakkuk() {
		return tahakkuk;
	}

	public void setTahakkuk(Tahakkuk tahakkuk) {
		this.tahakkuk = tahakkuk;
	}


	public TahakkukDetayHome getTahakkukDetayHome() {
		return tahakkukDetayHome;
	}

	public void setTahakkukDetayHome(TahakkukDetayHome tahakkukDetayHome) {
		this.tahakkukDetayHome = tahakkukDetayHome;
	}

	public Kisi getAnne() {
		return anne;
	}

	public void setAnne(Kisi anne) {
		this.anne = anne;
	}

}