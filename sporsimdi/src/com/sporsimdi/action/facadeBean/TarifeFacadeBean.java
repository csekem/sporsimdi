package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.TarifeFacade;
import com.sporsimdi.model.entity.Tarife;
import com.sporsimdi.model.entity.TarifeDetay;
import com.sporsimdi.model.type.Status;

@Stateless
public class TarifeFacadeBean implements TarifeFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public TarifeFacadeBean() {
	}

	public void persist(Tarife tarife) {
		entityManager.persist(tarife);
	}

	public void merge(Tarife tarife) {
		entityManager.merge(tarife);
	}

	public void remove(Tarife tarife) {
		entityManager.remove(tarife);
	}

	public void delete(Tarife tarife) {
		tarife.setStatus(Status.PASSIVE);
		entityManager.merge(tarife);
	}

	public Tarife findById(long id) {
		return entityManager.find(Tarife.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Tarife> listAll() {
		Query q = entityManager.createQuery("select t from Tarife t");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Tarife> listByGrup(Long grupId) {
		Query q = entityManager.createQuery("select distinct t from Tarife t " +
											"left outer join fetch t.tarifeDetayListesi td " +
											"where t.grup.id = :id")
								.setParameter("id", grupId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TarifeDetay> listTarifeDetayByTarife(Tarife tarife){
		Query q = entityManager.createQuery("select t.tarifeDetayListesi from Tarife t " +
											"where t.id = :id")
								.setParameter("id", tarife.getId());
		return q.getResultList();		
	}


}
