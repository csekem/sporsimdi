package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.FisFacade;
import com.qrpos.action.facade.KartDurumFacade;
import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Fis;
import com.qrpos.model.entity.FisDetay;
import com.qrpos.model.entity.KartDurum;
import com.qrpos.model.entity.Urun;
import com.qrpos.model.json.FisDetayJson;
import com.qrpos.model.json.FisJson;
import com.qrpos.model.json.FisKaydetSonucJson;
import com.qrpos.model.json.view.FisJsonView;
import com.qrpos.model.type.KartStatus;
import com.sporsimdi.model.type.Status;

@Stateless
public class FisFacadeBean implements FisFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@EJB
	private KartDurumFacade kartDurumFacade;

	@EJB
	private UrunFacade urunFacade;

	public FisFacadeBean() {
	}

	@Override
	public FisKaydetSonucJson fisIptal(FisJson fisJson) {
		FisKaydetSonucJson fisKaydetSonucJson = new FisKaydetSonucJson();

		Fis fisEski = entityManager.find(Fis.class, fisJson.getFisId());
		fisEski.getKartDurum().getKart().setBakiye(fisEski.getKartDurum().getKart().getBakiye().add(fisEski.getToplamTutar()));
		if (fisJson.getFisDetayListesi() != null && fisJson.getFisDetayListesi().size() > 0) {
			fisKaydetSonucJson = fisKaydetVeKarttanDus(fisJson, fisEski.getKartDurum());
			if (fisKaydetSonucJson.getStatus() == 1) {
				entityManager.refresh(fisEski.getKartDurum().getKart());
			}

		}
		fisEski.setStatus(Status.PASSIVE);
		return fisKaydetSonucJson;

	}

	@Override
	public FisKaydetSonucJson fisKaydet(FisJson fisJson) {
		KartDurum kartDurum = kartDurumFacade.findByQrCard(fisJson.getQrCardNo());
		return fisKaydetVeKarttanDus(fisJson, kartDurum);

	}

	private FisKaydetSonucJson fisKaydetVeKarttanDus(FisJson fisJson, KartDurum kartDurum) {
		FisKaydetSonucJson fisKaydetSonucJson = new FisKaydetSonucJson();
		if (kartDurum != null && kartDurum.getKartStatus().equals(KartStatus.ACIK)) {
			Fis fis = fisDoldur(fisJson, kartDurum);
			if (fis.getToplamTutar().compareTo(kartDurum.getKart().getBakiye()) >= 0) {
				fisKaydetSonucJson.setStatus(1);
				fisKaydetSonucJson.setHataKodu(2L);
				fisKaydetSonucJson.setHataDetay("Yetersiz Bakiye");
				fisKaydetSonucJson.setOncekiBakiye(kartDurum.getKart().getBakiye());
				fisKaydetSonucJson.setKalanBakiye(kartDurum.getKart().getBakiye().subtract(fis.getToplamTutar()));
				fisKaydetSonucJson.setUyeIsim(kartDurum.getKart().getUye().getAd() + " " + kartDurum.getKart().getUye().getSoyad());
				return fisKaydetSonucJson;
			} else {

				entityManager.persist(fis);
				kartDurum.getKart().setBakiye(kartDurum.getKart().getBakiye().subtract(fis.getToplamTutar()));

				fisKaydetSonucJson.setStatus(0);
				fisKaydetSonucJson.setHataKodu(0L);
				fisKaydetSonucJson.setHataDetay("Islem Basarili");
				fisKaydetSonucJson.setOncekiBakiye(kartDurum.getKart().getBakiye().add(fis.getToplamTutar()));
				fisKaydetSonucJson.setKalanBakiye(kartDurum.getKart().getBakiye());
				return fisKaydetSonucJson;
			}
		} else {
			fisKaydetSonucJson.setStatus(1);
			fisKaydetSonucJson.setHataKodu(1L);
			fisKaydetSonucJson.setHataDetay("Kart Durumu " + (kartDurum == null ? " KART YOK " : kartDurum.getKartStatus()));
			return fisKaydetSonucJson;
		}
	}

	private Fis fisDoldur(FisJson fisJson, KartDurum kartDurum) {
		BigDecimal toplamTutar = BigDecimal.ZERO;
		BigDecimal toplamKdv = BigDecimal.ZERO;
		FisDetay detay = null;
		Fis fis = new Fis();
		for (FisDetayJson fisDetayJson : fisJson.getFisDetayListesi()) {
			BigDecimal detayToplamTutar = BigDecimal.ZERO;
			BigDecimal detayToplamKdv = BigDecimal.ZERO;
			detay = new FisDetay();

			Urun urun = urunFacade.findById(fisDetayJson.getUrunId());
			detayToplamTutar = fisDetayJson.getAdet().multiply(urun.getSatisFiyat());
			detayToplamKdv = detayToplamTutar.multiply(urun.getKdvOrani());
			toplamTutar = toplamTutar.add(detayToplamTutar);
			toplamKdv = toplamKdv.add(detayToplamKdv);

			detay.setFis(fis);
			detay.setUrun(urun);
			detay.setAdet(fisDetayJson.getAdet());
			detay.setSatisTutar(detayToplamTutar);
			detay.setSatisFiyat(urun.getSatisFiyat());
			detay.setKdvOran(urun.getKdvOrani());
			detay.setKdvTutar(toplamKdv);
			fis.getFisDetayListesi().add(detay);

		}
		fis.setKartDurum(kartDurum);
		fis.setToplamTutar(toplamTutar);
		fis.setKdvTutar(toplamKdv);
		return fis;
	}

	@Override
	public void persist(Fis fis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void merge(Fis fis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Fis fis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Fis fis) {
		// TODO Auto-generated method stub

	}

	@Override
	public Fis findById(long id) {

		return entityManager.find(Fis.class, id);
	}

	@Override
	public FisJsonView getFisJsonView(long id) {
		return new FisJsonView(entityManager.find(Fis.class, id));
	}

	@Override
	public List<Fis> listByQRCard(String qrCardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FisJsonView> getFisJsonView(String qrCardNo) {
		List<FisJsonView> fisJsonViews = new ArrayList<FisJsonView>();

		Query q = entityManager.createQuery("select f from Fis f where f.kartDurum.qrcard.no = :no order by f.updateDate desc").setParameter("no", qrCardNo).setMaxResults(10);
		@SuppressWarnings("unchecked")
		List<Fis> fisList = q.getResultList();

		for (Fis fis : fisList) {
			fisJsonViews.add(new FisJsonView(fis));
		}
		return fisJsonViews;

	}
}
