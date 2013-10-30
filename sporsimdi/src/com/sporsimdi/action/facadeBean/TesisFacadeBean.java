package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.TesisFacade;
import com.sporsimdi.model.entity.Tesis;
import com.sporsimdi.model.type.Status;

@Stateless
public class TesisFacadeBean implements TesisFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public TesisFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tesis> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Tesis k where k.status =:status")
				.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Tesis tesis = entityManager.find(Tesis.class, id);
		entityManager.remove(tesis);
	}

	@Override
	public Tesis findById(long id) {
		return entityManager.find(Tesis.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tesis> getAll() {
		Query q = entityManager.createQuery("select k from Tesis k order by k.adi");
		return q.getResultList();
	}

	@Override
	public void persist(Tesis tesis) {
		entityManager.persist(tesis);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tesis> listAllByIsletme(Long isletmeId) {
		Query q = entityManager.createQuery("select distinct t from Tesis t " +
											"join fetch t.tesisTipi tt " +
											"left outer join fetch t.sahaListesi s " +
											"join t.isletmeListesi o " +
											"where o.id=:isletmeId order by t.adi");
		q.setParameter("isletmeId", isletmeId);
		return q.getResultList();
	}


}
