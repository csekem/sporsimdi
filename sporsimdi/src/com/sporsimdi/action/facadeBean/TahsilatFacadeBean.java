package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.type.Status;

@Stateless
public class TahsilatFacadeBean implements TahsilatFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@Override
	public void persist(Tahsilat tahsilat) {
		entityManager.persist(tahsilat);
	}

	public void merge(Tahsilat tahsilat) {
		entityManager.merge(tahsilat);
	}

	public void remove(Tahsilat tahsilat) {
		entityManager.remove(tahsilat);
	}

	public void delete(Tahsilat tahsilat) {
		tahsilat.setStatus(Status.PASSIVE);
		entityManager.merge(tahsilat);
	}

	@Override
	public Tahsilat findById(long id) {
		return entityManager.find(Tahsilat.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tahsilat> listAll() {
		return entityManager.createQuery("select t from Tahsilat t").getResultList();
	}
	
	@Override
	public Uye getUyeByTahsilat(long tahsilatId) {
		return (Uye) entityManager.createQuery("select t.tahakkukDetay.tahakkuk.uyeGrup.uye from Tahsilat t where t.id = :id")
									.setParameter("id", tahsilatId)
									.getSingleResult();
	}

	@Override
	public TahakkukDetay getTahakkukDetayByTahsilat(long tahsilatId) {
		return (TahakkukDetay) entityManager.createQuery("select t.tahakkukDetay from Tahsilat t where t.id = :id")
											.setParameter("id", tahsilatId)
											.getSingleResult();		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Tahsilat> getByStatus(Status status) {
		return entityManager.createQuery("select k from Tahsilat k where k.status = :status")
							.setParameter("status", status)
							.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tahsilat> listByTahakkukDetay(TahakkukDetay tahakkukDetay) {
		return entityManager.createQuery("select k from Tahsilat k where k.tahakkukDetay = :tahakkukDetay")
				.setParameter("tahakkukDetay", tahakkukDetay)
				.getResultList();
	}

}
