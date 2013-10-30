package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.TesisTipiFacade;
import com.sporsimdi.model.entity.TesisTipi;
import com.sporsimdi.model.type.Status;

@Stateless
public class TesisTipiFacadeBean implements TesisTipiFacade {

	@PersistenceContext(unitName = "sporsimdi")
	private EntityManager entityManager;

	public TesisTipiFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TesisTipi> listByStatus(Status status) {
		Query q = entityManager.createQuery("select t from TesisTipi t where t.status =:status")
								.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		TesisTipi tesisTipi = entityManager.find(TesisTipi.class, id);
		entityManager.remove(tesisTipi);
	}

	@Override
	public TesisTipi findById(long id) {
		return entityManager.find(TesisTipi.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TesisTipi> listAll() {
		Query q = entityManager.createQuery("select t from TesisTipi t order by t.adi");
		return q.getResultList();
	}

	@Override
	public void persist(TesisTipi tesisTipi) {
		entityManager.persist(tesisTipi);
	}
	

}
