package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.MeslekFacade;
import com.sporsimdi.model.entity.Meslek;
import com.sporsimdi.model.type.Status;

@Stateless
public class MeslekFacadeBean implements MeslekFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public MeslekFacadeBean() {
	}

	@Override
	public void persist(Meslek meslek) {
		entityManager.persist(meslek);
	}

	@Override
	public void merge(Meslek meslek) {
		entityManager.merge(meslek);
	}

	@Override
	public void remove(Meslek meslek) {
		entityManager.remove(meslek);
	}

	public void delete(Meslek meslek) {
		meslek.setStatus(Status.PASSIVE);
		entityManager.merge(meslek);
	}

	@Override
	public Meslek findById(long id) {
		return entityManager.find(Meslek.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meslek> listAll() {
		Query q = entityManager.createQuery("select k from Meslek k order by k.adi");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meslek> listAllByIsletme(Long isletmeId) {
		Query q = entityManager.createQuery("select m from Meslek m where (m.isletme = null or m.isletme.id = :isletmeId) order by m.adi").setParameter("isletmeId", isletmeId);
		return q.getResultList();
	}

}
