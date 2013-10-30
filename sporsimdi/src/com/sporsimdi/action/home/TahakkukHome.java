package com.sporsimdi.action.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.action.facade.TarifeFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tarife;
import com.sporsimdi.model.entity.TarifeDetay;
import com.sporsimdi.model.entity.UyeGrup;

@ManagedBean(name = "tahakkukHome")
@ViewScoped
public class TahakkukHome extends HomeBean<Tahakkuk> {

	private static final long serialVersionUID = 5528752636982509999L;

	@EJB
	private TahakkukFacade tahakkukFacade;
	
	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;

	@EJB
	private TarifeFacade tarifeFacade;

	@EJB
	private TahsilatFacade tahsilatFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{tahakkukDetayHome}")
	private TahakkukDetayHome tahakkukDetayHome;

	private UyeGrup uyeGrup;
	
	private Map<Long, List<TahakkukDetay>> tahakkukDetayMap;
	
	private Date ilkTahakkukTarihi;
		
	private Map<Long, Boolean> tahsilatMap;

	
	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pTahakkukId = getTahakkukIdParam(fc);

		if (pTahakkukId != null && !pTahakkukId.isEmpty()) {
			setInstance(tahakkukFacade.findById(Long.valueOf(pTahakkukId)));
		} else {
			Tahakkuk thk = new Tahakkuk();
			setInstance(thk);			
		}
	}

	public void sayfaLoad(UyeGrup uyeGrup) throws Exception {
		setUyeGrup(uyeGrup);
		if (uyeGrup != null && uyeGrup.getId() != null) {
			Tahakkuk thk = tahakkukFacade.getByUyeGrup(uyeGrup);
			if (thk == null) {
				thk = new Tahakkuk();
			}
			getTahsilatMap().clear();
			for (TahakkukDetay dty : thk.getTahakkukDetayListesi()) {
				if (tahsilatFacade.listByTahakkukDetay(dty).size()>0) {
					getTahsilatMap().put(dty.getId(), true);
				}
			}
			setInstance(thk);
		} else {
			Tahakkuk thk = new Tahakkuk();
			setInstance(thk);			
		}
	}

	public String getTahakkukIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("tahakkukId");
	}

	public String getGrupIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("grupId");
	}

	public UyeGrup getUyeGrup() {
		return uyeGrup;
	}

	public void setUyeGrup(UyeGrup uyeGrup) {
		this.uyeGrup = uyeGrup;
	}

	public void tahakkukOlustur(UyeGrup uyeGrup, Tarife trf) {
		Tahakkuk thk = new Tahakkuk();
		
		if (trf.isHerAy()) {
			UtilDate dateBas = new UtilDate();
			UtilDate dateBit = new UtilDate();
			boolean acikVade = false;
			if (uyeGrup.getGirisTarihi()!=null) {
				dateBas.setTime(uyeGrup.getGirisTarihi());
			} else {
				if (uyeGrup.getGrup().getTarihBaslangic()!=null) {
					dateBas.setTime(uyeGrup.getGrup().getTarihBaslangic());
				} else {
					if (sessionObject.getSelectedOrganizasyon().getTarihBaslangic()!=null) {
						dateBas.setTime(sessionObject.getSelectedOrganizasyon().getTarihBaslangic());
					}				
				}			
			}
			if (uyeGrup.getCikisTarihi()!=null) {
				dateBit.setTime(uyeGrup.getCikisTarihi());
			} else {
				if (uyeGrup.getGrup().getTarihBitis()!=null) {
					dateBit.setTime(uyeGrup.getGrup().getTarihBitis());
				} else {
					if (sessionObject.getSelectedOrganizasyon().getTarihBitis()!=null) {
						dateBit.setTime(sessionObject.getSelectedOrganizasyon().getTarihBitis());
					} else {
						acikVade = true;
					}
				}			
			}
			
			/** TODO gecerli tarihler olsun mu? */
			//acikVade = true;
			
			thk.setAcikVade(acikVade);
			thk.setHerAyTutar(trf.getHerAyTutar());

			int taksitSayisi = 1;
			if (acikVade) {
				trf.setAidatGun(dateBas.get(Calendar.DATE));
			} else {
				taksitSayisi = dateBas.getMonthCount(dateBit);
			}			
			trf.setTaksitSayisi(taksitSayisi);

			
			List<TahakkukDetay> dtyList = new ArrayList<TahakkukDetay>();
			for (int i = 1; i <= taksitSayisi; i++) {
				TahakkukDetay thkDty = new TahakkukDetay();
				thkDty.setHerAy(trf.isHerAy());
				
				UtilDate date = new UtilDate(dateBas.getTime());
				thkDty.setTaksitNo(i);
				thkDty.setBaslangicTarihi(date.getTime());

				if (trf.getAidatGun()==0) { // aidat günü yoksa
					thkDty.setVadeTarihi(date.getTime());
				} else {
					date.set(Calendar.DATE, trf.getAidatGun());
					date.add(Calendar.MONTH, i-1);
					thkDty.setVadeTarihi(date.getTime());					
				}
				
				date.add(Calendar.MONTH, 1);
				date.add(Calendar.DATE, -1);
				thkDty.setBitisTarihi(date.getTime());
				thkDty.setHerAyTutar(trf.getHerAyTutar());
							
				thkDty.setTahakkuk(thk);
				
				dtyList.add(thkDty);
			}
			thk.setTahakkukDetayListesi(dtyList);

		} else {
			thk.setTaksitSayisi(trf.getTaksitSayisi());
			thk.setToplamTutar(trf.getToplamTutar());

			List<TahakkukDetay> dtyList = new ArrayList<TahakkukDetay>();
			for (TarifeDetay trfDty: tarifeFacade.listTarifeDetayByTarife(trf)) {
				TahakkukDetay thkDty = new TahakkukDetay();
				thkDty.setHerAy(trf.isHerAy());
				
				UtilDate date = new UtilDate();
				if (trf.getAidatGun()!=0) {
					if (trf.getAidatGun()<date.get(Calendar.DATE)) {
						date.addMonth(1);
					}
					date.set(Calendar.DATE, trf.getAidatGun());
				}
				date.add(Calendar.MONTH, trfDty.getTaksitNo()-1);
				thkDty.setVadeTarihi(date.getTime());

				if (trf.isHerAy()) {
					thkDty.setTaksitNo(trfDty.getTaksitNo());
					thkDty.setBaslangicTarihi(date.getTime());
					date.add(Calendar.MONTH, 1);
					date.add(Calendar.DATE, -1);
					thkDty.setBitisTarihi(date.getTime());
					thkDty.setHerAyTutar(trf.getHerAyTutar());
				} else {
					thkDty.setTaksitNo(trfDty.getTaksitNo());
					thkDty.setTaksitTutari(trfDty.getTaksitTutar());
				}
							
				thkDty.setTahakkuk(thk);
				
				dtyList.add(thkDty);
			}
			thk.setTahakkukDetayListesi(dtyList);
		}
		
		thk.setAidatGun(trf.getAidatGun());
		thk.setHerAy(trf.isHerAy());
		thk.setTarife(trf);
		thk.setAidatGun(trf.getAidatGun());
		thk.setUyeGrup(getUyeGrup());
		setInstance(thk);		
		
		getTahsilatMap().clear();
	}
	
	
	public void vadeTarihAyDegistir(long amount) {
		List<TahakkukDetay> detayList = getInstance().getTahakkukDetayListesi();
		for (TahakkukDetay tahakkukDetay : detayList) {
			UtilDate vadeTarihi = new UtilDate(tahakkukDetay.getVadeTarihi());
			tahakkukDetay.setVadeTarihi(vadeTarihi.add(Calendar.MONTH, (new Long(amount)).intValue()));
		}
	}

	public void vadeTarihGunDegistir(int amount) {
		List<TahakkukDetay> detayList = getInstance().getTahakkukDetayListesi();
		for (TahakkukDetay tahakkukDetay : detayList) {
			UtilDate vadeTarihi = new UtilDate(tahakkukDetay.getVadeTarihi());
			tahakkukDetay.setVadeTarihi(vadeTarihi.set(Calendar.DATE, amount));
		}
	}
	
	public void taksitEkleBasa() {
		List<TahakkukDetay> detayList = getInstance().getTahakkukDetayListesi();
		List<TahakkukDetay> newList = new ArrayList<TahakkukDetay>();
		for (TahakkukDetay tahakkukDetay : detayList) {
			if (tahakkukDetay.getTaksitNo()==1) {
				TahakkukDetay newDty = tahakkukDetay.clone();
				UtilDate newVadeTarihi = new UtilDate(newDty.getVadeTarihi());
				newVadeTarihi.add(Calendar.MONTH, -1);
				newDty.setVadeTarihi(newVadeTarihi.getTime());
				newList.add(newDty);
			}
			tahakkukDetay.setTaksitNo(tahakkukDetay.getTaksitNo()+1);
			newList.add(tahakkukDetay);
		}
		getInstance().setTahakkukDetayListesi(newList);
		toplamTutarOlustur();
	}

	public void taksitEkleSona() {
		List<TahakkukDetay> detayList = getInstance().getTahakkukDetayListesi();
		
		TahakkukDetay newDty = detayList.get(detayList.size()-1).clone();
		UtilDate newVadeTarihi = new UtilDate(newDty.getVadeTarihi());
		newVadeTarihi.add(Calendar.MONTH, 1);
		newDty.setVadeTarihi(newVadeTarihi.getTime());
		newDty.setTaksitNo(newDty.getTaksitNo()+1);
		detayList.add(newDty);
		toplamTutarOlustur();
	}

	public void taksitSil(int rowInd) {
		List<TahakkukDetay> detayList = getInstance().getTahakkukDetayListesi();
		List<TahakkukDetay> newList = new ArrayList<TahakkukDetay>();
		
		for (TahakkukDetay tahakkukDetay : detayList) {
			if (tahakkukDetay.getTaksitNo()<rowInd+1) {
				newList.add(tahakkukDetay);
			}
			if (tahakkukDetay.getTaksitNo()>rowInd+1) {
				tahakkukDetay.setTaksitNo(tahakkukDetay.getTaksitNo()-1);
				newList.add(tahakkukDetay);
			}
		}
		getInstance().setTahakkukDetayListesi(newList);
		toplamTutarOlustur();
	}

	public void toplamTutarOlustur() {
		BigDecimal toplamTutar = new BigDecimal(0);
		for (TahakkukDetay dty : getInstance().getTahakkukDetayListesi()) {
			toplamTutar = toplamTutar.add(dty.getTaksitTutari());
		}
		getInstance().setToplamTutar(toplamTutar);
		getInstance().setToplamIndirim(getInstance().getTarife().getToplamTutar().subtract(toplamTutar));
	}

	public void herAyTutarGuncelle() {
		for (TahakkukDetay detay : getInstance().getTahakkukDetayListesi()) {
			detay.setHerAyTutar(getInstance().getHerAyTutar());
		}
		/** TODO düzelt
		getInstance().setToplamIndirim(getInstance().getTarife().getTaksitTutar().subtract(getInstance().getHerAyTutar()));
		*/
	}

	@Override
	public String save() {
		String retVal = super.save();
		return retVal;
	}

	@Override
	public String saveWithoutNavigation() throws Exception {
		if (getInstance().getId()==null) {
			deleteOldTahakkuk();			
		}
		
		String result = super.saveWithoutNavigation();
		
		getTahakkukDetayMap().remove(getInstance().getId());
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Başarılı", "Aidat tarifesi kaydedildi."));  
		
		return result;				
	}

	private void deleteOldTahakkuk() throws Exception {
		boolean tahsilatVar = false;
		Tahakkuk eskiTahakkuk = tahakkukFacade.getByUyeGrup(getUyeGrup());
		if (eskiTahakkuk == null) {
			return;
		}

		List<TahakkukDetay> detayList = tahakkukDetayFacade.listByTahakkuk(eskiTahakkuk);
		for (TahakkukDetay tahakkukDetay : detayList) {
			try {
				tahsilatFacade.listByTahakkukDetay(tahakkukDetay);
				tahsilatVar = true;
				tahakkukDetayFacade.delete(tahakkukDetay);
			} catch (EJBException e) {
				if (e.getCausedByException() instanceof NoResultException) {
					tahakkukDetayFacade.remove(tahakkukDetay);
				} else {
					throw e;
				}
			}
		}
		if (tahsilatVar) {
			tahakkukFacade.delete(eskiTahakkuk);
		} else {
			tahakkukFacade.remove(eskiTahakkuk);
		}
	}

	public TahakkukDetayHome getTahakkukDetayHome() {
		return tahakkukDetayHome;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public void setTahakkukDetayHome(TahakkukDetayHome tahakkukDetayHome) {
		this.tahakkukDetayHome = tahakkukDetayHome;
	}

	public Map<Long, List<TahakkukDetay>> getTahakkukDetayMap() {
		if (tahakkukDetayMap == null) {
			tahakkukDetayMap = new HashMap<Long, List<TahakkukDetay>>();
		}
		return tahakkukDetayMap;
	}

	public void setTahakkukDetayMap(Map<Long, List<TahakkukDetay>> tahakkukDetayMap) {
		this.tahakkukDetayMap = tahakkukDetayMap;
	}

	public Date getIlkTahakkukTarihi() {
		if (ilkTahakkukTarihi==null) {
			Calendar cal = Calendar.getInstance(new Locale("tr"));
			cal.set(Calendar.DATE, getInstance().getTarife().getAidatGun());
			ilkTahakkukTarihi = cal.getTime();
		}
		return ilkTahakkukTarihi;
	}

	public void setIlkTahakkukTarihi(Date ilkTahakkukTarihi) {
		this.ilkTahakkukTarihi = ilkTahakkukTarihi;
	}

	public Map<Long, Boolean> getTahsilatMap() {
		if (tahsilatMap == null) {
			tahsilatMap = new HashMap<Long, Boolean>();
		}
		return tahsilatMap;
	}

	public void setTahsilatMap(Map<Long, Boolean> tahsilatMap) {
		this.tahsilatMap = tahsilatMap;
	}
	
	
	public boolean tahsilatVar(TahakkukDetay detay) {
		return getTahsilatMap().containsKey(detay.getId());
	}
}