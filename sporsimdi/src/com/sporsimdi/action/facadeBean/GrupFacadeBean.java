package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.type.Status;

@Stateless
public class GrupFacadeBean implements GrupFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public GrupFacadeBean() {
	}

	public void persist(Grup grup) {
		entityManager.persist(grup);
	}

	public void merge(Grup grup) {
		entityManager.merge(grup);
	}

	public void remove(Grup grup) {
		entityManager.remove(grup);
	}

	public void delete(Grup grup) {
		grup.setStatus(Status.PASSIVE);
		entityManager.merge(grup);
	}

	@Override
	public Grup findById(long id) {
		return entityManager.find(Grup.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grup> listAll() {
		Query q = entityManager.createQuery("select g from Grup g");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grup> listByOrgTesis(Long orgTesisId) {
		Query q = entityManager.createQuery("select g from Grup g " +
											"where g.orgTesis.id = :id")
								.setParameter("id", orgTesisId);
		List<Grup> grupListesi = q.getResultList();
		
		return grupListesi;
	}


}
