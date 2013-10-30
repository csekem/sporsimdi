package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.SahaTipiFacade;
import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.entity.SahaTipi;
import com.sporsimdi.model.type.Status;

@Stateless
public class SahaTipiFacadeBean implements SahaTipiFacade {

	@PersistenceContext(unitName = "sporsimdi")
	private EntityManager entityManager;

	public SahaTipiFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SahaTipi> listByStatus(Status status) {
		Query q = entityManager.createQuery("select s from SahaTipi s where s.status =:status")
								.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		SahaTipi sahaTipi = entityManager.find(SahaTipi.class, id);
		entityManager.remove(sahaTipi);
	}

	@Override
	public SahaTipi findById(long id) {
		return entityManager.find(SahaTipi.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SahaTipi> listAll() {
		Query q = entityManager.createQuery("select t from SahaTipi t order by t.adi");
		return q.getResultList();
	}

	@Override
	public void persist(SahaTipi sahaTipi) {
		entityManager.persist(sahaTipi);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SahaTipi> listBySaha(Saha saha) {
		Query q = entityManager.createQuery("select s.sahaTipiListesi from Saha s where s.id = :id")
								.setParameter("id", saha.getId());
		return q.getResultList();
	}

}
