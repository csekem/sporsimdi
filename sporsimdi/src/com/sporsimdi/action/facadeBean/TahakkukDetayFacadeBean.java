package com.sporsimdi.action.facadeBean;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.TahakkukDetayFacade;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.type.Status;

@Stateless
public class TahakkukDetayFacadeBean implements TahakkukDetayFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@Override
	public void persist(TahakkukDetay tahakkukDetay) {
		entityManager.persist(tahakkukDetay);
	}

	@Override
	public void merge(TahakkukDetay tahakkukDetay) {
		entityManager.merge(tahakkukDetay);
	}

	@Override
	public void remove(TahakkukDetay tahakkukDetay) {
		entityManager.remove(tahakkukDetay);
	}

	@Override
	public void delete(TahakkukDetay tahakkukDetay) {
		tahakkukDetay.setStatus(Status.PASSIVE);
		entityManager.merge(tahakkukDetay);
	}

	@Override
	public TahakkukDetay findById(long id) {
		return entityManager.find(TahakkukDetay.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TahakkukDetay> listAll() {
		return entityManager.createQuery("select t from TahakkukDetay t").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TahakkukDetay> listByTahakkuk(Tahakkuk tahakkuk) {
		return entityManager.createQuery("select td from TahakkukDetay td join fetch td.tahakkuk t where td.tahakkuk = :tahakkuk order by td.taksitNo")
				.setParameter("tahakkuk", tahakkuk).getResultList();
	}

	@Override
	public List<TahakkukDetay> listByTahakkukVadeTarihi(Tahakkuk tahakkuk, Date vadeTarihi) {
		Format frm = new SimpleDateFormat("MM");
		return listByTahakkukYilAy(tahakkuk, frm.format(vadeTarihi));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TahakkukDetay> listByTahakkukYilAy(Tahakkuk tahakkuk, String yilAy) {
		return entityManager
				.createQuery(
						"select td from TahakkukDetay td " + "join fetch td.tahakkuk t " + "where td.tahakkuk = :tahakkuk "
								+ " and to_char(td.vadeTarihi,'YYYYMM') = :vadeTarihi " + "order by td.taksitNo").setParameter("tahakkuk", tahakkuk)
				.setParameter("vadeTarihi", yilAy).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TahakkukDetay> listByTahakkukBetweenDates(Tahakkuk tahakkuk, UtilDate dateBegin, UtilDate dateEnd) {
		/*
		 * UtilDate date = new UtilDate(); date.set(2013, 9, 19); UtilDate date2
		 * = new UtilDate(); date2.set(2013, 9, 20);
		 */return entityManager
				.createQuery(
						"select td from TahakkukDetay td " + "join fetch td.tahakkuk t " + "where td.tahakkuk = :tahakkuk "
								+ " and vadeTarihi between :dateBegin and :dateEnd " + "order by td.taksitNo").setParameter("tahakkuk", tahakkuk)
				.setParameter("dateBegin", dateBegin.getTime()).setParameter("dateEnd", dateEnd.getTime()).getResultList();
		// .setParameter("dateBegin", date.getTime()).setParameter("dateEnd",
		// date2.getTime()).getResultList();

	}

}
