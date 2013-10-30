package com.sporsimdi.action.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.action.service.AidatService.Aidat;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.action.util.spi.SSSchedulerBase;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.entity.UyeGrup;

@ManagedBean(name = "aidatTahakkukService")
@SessionScoped
public class AidatTahakkukService implements Serializable {

	private static final long serialVersionUID = -3314039199200265446L;

	@EJB
	private TahakkukFacade tahakkukFacade;

	@EJB
	private TahakkukDetayFacade tahakkukDetayFacade;

	@EJB
	private TahsilatFacade tahsilatFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	private Map<String, Aidat> aidatRowMap = new HashMap<String, Aidat>();

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public Map<String, Aidat> getAidatRowMap() {
		return aidatRowMap;
	}

	public void setAidatRowMap(Map<String, Aidat> aidatRowMap) {
		this.aidatRowMap = aidatRowMap;
	}

	public Aidat getRow(UyeGrup uyeGrup, SSSchedulerBase aidatDateBase, int dateIndex) {
		try {
			if (!getAidatRowMap().containsKey(uyeGrup.getId() + aidatDateBase.getDisplayName(dateIndex))) {
				// Mapte yoksa bulunur, eklenir
				Aidat aidat = new Aidat();
				aidat.setDateIndex(dateIndex);
				aidat.setUyeGrup(uyeGrup);
				aidat.setTahakkukTutar(new BigDecimal(0));
				aidat.setTahsilTutar(new BigDecimal(0));
				aidat.setOdemeDurumu(Aidat.ODEME_YOK);

				List<Tahakkuk> tahakkukList = tahakkukFacade.listByUyeGrup(uyeGrup);
				if (tahakkukList.size() == 0) {
					// TODO tahakkuk = new Tahakkuk();
					aidat.setOdemeDurumu(Aidat.TARIFE_YOK);
					aidat.setTahakkukDetayList(new ArrayList<TahakkukDetay>());
				} else {
					aidat.setTarifeVar(true);
					aidat.setTahakkukTutar(BigDecimal.ZERO);
					aidat.setTahsilTutar(BigDecimal.ZERO);
					aidat.setIndirimTutar(BigDecimal.ZERO);
					for (Tahakkuk tahakkuk : tahakkukList) {
						List<TahakkukDetay> tahakkukDetayListesi = tahakkukDetayFacade.listByTahakkukBetweenDates(
								tahakkuk, aidatDateBase.getDateBegin(dateIndex), aidatDateBase.getDateEnd(dateIndex));
						tahakkukDetayListesi = herAyTahakkukDetayOlustur(uyeGrup, tahakkuk, tahakkukDetayListesi,
								aidatDateBase.getDateBegin(dateIndex), aidatDateBase.getDateEnd(dateIndex));
						// TODO session bazında bir mapa alınmalı

						aidat.setTahakkukDetayList(tahakkukDetayListesi);
						for (TahakkukDetay tahakkukDetay : tahakkukDetayListesi) {
							aidat.setOdemeDurumu(Aidat.ODEME_AL);
							aidat.setBgColor("red");

							aidat.setTahakkukTutar(aidat.getTahakkukTutar().add(
									tahakkukDetay.isHerAy() ? tahakkukDetay.getHerAyTutar() : tahakkukDetay
											.getTaksitTutari()));

							List<Tahsilat> tahsilatListesi = tahsilatFacade.listByTahakkukDetay(tahakkukDetay);
							for (Tahsilat tahsilat : tahsilatListesi) {
								aidat.setTahsilTutar(aidat.getTahsilTutar().add(tahsilat.getTahsilTutari()));
								aidat.setIndirimTutar(aidat.getIndirimTutar().add(tahsilat.getIndirimTutari()));
							}
						}
					}
					aidat.setKalanTutar(aidat.getTahakkukTutar().subtract(aidat.getTahsilTutar())
							.subtract(aidat.getIndirimTutar()));

					if (aidat.getTahsilTutar().longValue() > aidat.getTahakkukTutar().longValue()) {
						aidat.setOdemeVar(true);
						aidat.setOdemeTam(true);
						aidat.setOdemeDurumu(Aidat.FAZLA_ODEME);
						aidat.setBgColor("red");
					} else if (aidat.getTahsilTutar().longValue() > 0) {
						aidat.setOdemeVar(true);
						aidat.setOdemeDurumu(Aidat.EKSIK_ODEME);
						aidat.setBgColor("orange");
						if (aidat.getKalanTutar().longValue() == 0) {
							aidat.setOdemeDurumu(Aidat.ODEME_IPTAL);
							aidat.setOdemeTam(true);
							aidat.setBgColor("green");
						}
					}
					if (aidat.getTahakkukTutar().compareTo(BigDecimal.ZERO) != 0
							&& aidat.getTahakkukTutar().longValue() == aidat.getIndirimTutar().longValue()) {
						aidat.setOdemeDurumu(Aidat.INDIRIM_IPTAL);
						aidat.setOdemeTam(true);
						aidat.setOdemeVar(true);
						aidat.setBgColor("green");
					}
				}
				getAidatRowMap().put(uyeGrup.getId() + aidatDateBase.getDisplayName(dateIndex), aidat);
			}
			return getAidatRowMap().get(uyeGrup.getId() + aidatDateBase.getDisplayName(dateIndex));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<TahakkukDetay> herAyTahakkukDetayOlustur(UyeGrup uyeGrup, Tahakkuk tahakkuk,
			List<TahakkukDetay> detayList, UtilDate dateBegin, UtilDate dateEnd) {
		// Her ay alınan aidat için, günün tarihine ait aya ait tahakkkuk detayı
		// yoksa create et
		if (tahakkukTarihiUygun(uyeGrup, tahakkuk, dateBegin, dateEnd)) {
			TahakkukDetay detay = new TahakkukDetay();
			detay.setTahakkuk(tahakkuk);
			detay.setHerAy(true);

			UtilDate today = new UtilDate();

			UtilDate girisTarihi = new UtilDate(uyeGrup.getGirisTarihi());
			UtilDate date = new UtilDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
					girisTarihi.get(Calendar.DATE));
			detay.setBaslangicTarihi(date.getTime());

			date.add(Calendar.MONTH, 1);
			date.add(Calendar.DATE, -1);
			detay.setBitisTarihi(date.getTime());

			detay.setHerAyTutar(tahakkuk.getHerAyTutar());

			date.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), tahakkuk.getAidatGun());
			detay.setVadeTarihi(date.getTime());

			detay.setKalanTutar(new BigDecimal(0));
			tahakkukDetayFacade.persist(detay);

			detayList.add(detay);
		}
		return detayList;
	}

	private boolean tahakkukTarihiUygun(UyeGrup uyeGrup, Tahakkuk tahakkuk, UtilDate dateBegin, UtilDate dateEnd) {
		// Her ay alınan aidat, organizasyon, grup ve UyeGrup tarihleri arasında
		// mı?
		if (tahakkuk.isAcikVade()) {
			UtilDate today = new UtilDate();
			String yilAy = today.toString("yyyyMM");
			if ((tahakkukDetayFacade.listByTahakkukYilAy(tahakkuk, yilAy)).size() == 0) {
				if (today.compareTo(dateBegin) >= 0 && today.compareTo(dateEnd) <= 0) {
					UtilDate dateBas = new UtilDate();
					if (sessionObject.getSelectedOrganizasyon().getTarihBaslangic() == null) {
						dateBas.set(1900, 0, 1);
					} else {
						dateBas.setTime(sessionObject.getSelectedOrganizasyon().getTarihBaslangic());
					}

					UtilDate dateBit = new UtilDate();
					if (sessionObject.getSelectedOrganizasyon().getTarihBitis() == null) {
						dateBit.set(2999, 11, 31);
					} else {
						dateBit.setTime(sessionObject.getSelectedOrganizasyon().getTarihBitis());
					}

					// organizasyon tarih kontrolü
					if (dateBas.compareTo(yilAy, "yyyyMM") <= 0 && dateBit.compareTo(yilAy, "yyyyMM") >= 0) {
						if (uyeGrup.getGrup().getTarihBaslangic() == null) {
							dateBas.set(1900, 0, 1);
						} else {
							dateBas.setTime(uyeGrup.getGrup().getTarihBaslangic());
						}
						if (uyeGrup.getGrup().getTarihBitis() == null) {
							dateBit.set(2999, 11, 31);
						} else {
							dateBit.setTime(uyeGrup.getGrup().getTarihBitis());
						}

						// grup tarih kontrolü
						if (dateBas.compareTo(yilAy, "yyyyMM") <= 0 && dateBit.compareTo(yilAy, "yyyyMM") >= 0) {
							if (uyeGrup.getGirisTarihi() == null) {
								dateBas.set(1900, 0, 1);
							} else {
								dateBas.setTime(uyeGrup.getGirisTarihi());
							}
							if (uyeGrup.getCikisTarihi() == null) {
								dateBit.set(2999, 11, 31);
							} else {
								dateBit.setTime(uyeGrup.getCikisTarihi());
							}

							// Uye Grup tarih kontrolü
							if (dateBas.compareTo(yilAy, "yyyyMM") <= 0 && dateBit.compareTo(yilAy, "yyyyMM") >= 0) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
