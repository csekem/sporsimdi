package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.AdresFacade;
import com.sporsimdi.model.entity.Adres;
import com.sporsimdi.model.type.Status;

@Stateless
public class AdresFacadeBean implements AdresFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public AdresFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Adres k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Adres adres = entityManager.find(Adres.class, id);
		entityManager.remove(adres);
	}


	@Override
	public Adres findById(long id) {
		Adres adres = entityManager.find(Adres.class, id);
		return adres;
	}

	@Override
	public Adres findByIdEditMode(long id) {
		Adres adres = entityManager.find(Adres.class, id);
		adres.getIlce().getAdi();
		adres.getIl().getAdi();
//		adres.getKisi().getAd();
		return adres;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> listAll() {
		Query q = entityManager.createQuery("select k from Adres k ");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Adres> listByModelId(Long modelId) {
		return entityManager.createQuery("select a from Adres a " +
										"join fetch a.il i " +
										"join fetch a.ilce ic " +
										"join fetch a.semt s " +
										"where a.modelId = :modelId")
							.setParameter("modelId", modelId)
							.getResultList();
	}

	@Override
	public void persist(Adres adres) {
		entityManager.persist(adres);
	}

	
}
