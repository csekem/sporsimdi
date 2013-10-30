package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.UrunKategoriFacade;
import com.qrpos.model.entity.UrunKategori;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.type.Status;

@Stateless
public class UrunKategoriFacadeBean implements UrunKategoriFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public UrunKategoriFacadeBean() {
	}


	@Override
	public UrunKategori findById(long id) {
		return entityManager.find(UrunKategori.class, id);
	}

	@Override
	public void persist(UrunKategori urunKategori) {
		entityManager.persist(urunKategori);
	}

	@Override
	public void merge(UrunKategori urunKategori) {
		entityManager.merge(urunKategori);
	}


	@Override
	public void remove(UrunKategori urunKategori) {
		entityManager.remove(urunKategori);
	}


	@Override
	public void delete(UrunKategori urunKategori) {
		urunKategori.setStatus(Status.PASSIVE);
		entityManager.merge(urunKategori);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UrunKategori> listByKasa(Kasa kasa) {
		Query q = entityManager.createQuery("select uk from UrunKategori uk where uk.kasa = :kasa")
				.setParameter("kasa", kasa);
		return q.getResultList();
	}
	

}
