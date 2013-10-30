package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.SahaFacade;
import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.type.Status;

@Stateless
public class SahaFacadeBean implements SahaFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public SahaFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Saha> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Saha k where k.status =:status")
				.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Saha saha = entityManager.find(Saha.class, id);
		entityManager.remove(saha);
	}

	@Override
	public Saha findById(long id) {
		return entityManager.find(Saha.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Saha> listAll() {
		Query q = entityManager.createQuery("select k from Saha k order by k.adi");
		return q.getResultList();
	}

	@Override
	public void persist(Saha saha) {
		entityManager.persist(saha);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Saha> listAllByTesis(Long tesisId) {
		Query q = entityManager.createQuery("select distinct s from Saha s " +
											"left outer join fetch s.sahaTipiListesi st " +
//											"left outer join fetch t.sahaListesi s " +
											"join s.tesis t " +
											"where t.id=:tesisId " +
											"order by s.adi");
		q.setParameter("tesisId", tesisId);
		return q.getResultList();
	}

	@Override
	public Saha findWideById(long id) {
		return (Saha) entityManager.createQuery("select s from Saha s " +
												"left outer join fetch s.sahaTipiListesi st " +
												"where s.id=:id ")
									.setParameter("id", id)
									.getSingleResult();
	}

}
