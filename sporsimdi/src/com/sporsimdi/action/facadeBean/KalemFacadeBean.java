package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.KalemFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kalem;
import com.sporsimdi.model.type.Status;

@Stateless
public class KalemFacadeBean implements KalemFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KalemFacadeBean() {
	}

	@Override
	public void persist(Kalem kalem) {
		entityManager.persist(kalem);
	}

	@Override
	public void merge(Kalem kalem) {
		entityManager.merge(kalem);
	}

	@Override
	public void remove(Kalem kalem) {
		entityManager.remove(kalem);
	}

	public void delete(Kalem kalem) {
		kalem.setStatus(Status.PASSIVE);
		entityManager.merge(kalem);
	}

	@Override
	public Kalem findById(long id) {
		return entityManager.find(Kalem.class, id);
	}

	@Override
	public Kalem getByAdi(Isletme isletme, String ad) {
		Query q = entityManager.createQuery("select k from Kalem k where (k.isletme = null or k.isletme = :isletme) and k.adi =:adi").setParameter("isletme", isletme)
				.setParameter("adi", ad);
		return (Kalem) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kalem> listAll() {
		Query q = entityManager.createQuery("select k from Kalem k order by k.adi");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kalem> listAllByIsletme(Long isletmeId) {
		Query q = entityManager.createQuery("select k from Kalem k where (k.isletme = null or k.isletme.id = :isletmeId) order by k.adi").setParameter("isletmeId", isletmeId);
		return q.getResultList();
	}

}
