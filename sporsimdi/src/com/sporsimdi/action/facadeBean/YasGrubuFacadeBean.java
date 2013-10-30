package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.YasGrubuFacade;
import com.sporsimdi.model.entity.YasGrubu;
import com.sporsimdi.model.type.Status;

@Stateless
public class YasGrubuFacadeBean implements YasGrubuFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public YasGrubuFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YasGrubu> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from YasGrubu k where k.status =:status")
				.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		YasGrubu yasGrubu = entityManager.find(YasGrubu.class, id);
		entityManager.remove(yasGrubu);
	}

	@Override
	public YasGrubu findById(long id) {
		return entityManager.find(YasGrubu.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YasGrubu> listAll() {
		Query q = entityManager.createQuery("select k from YasGrubu k order by k.adi");
		return q.getResultList();
	}

	@Override
	public void persist(YasGrubu yasGrubu) {
		entityManager.persist(yasGrubu);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<YasGrubu> listAllByIsletme(Long isletmeId) {
		Query q = entityManager.createQuery("select distinct y from YasGrubu y " +
											"join fetch y.isletme i " +
											"where i.id=:isletmeId order by y.baslangicYili");
		q.setParameter("isletmeId", isletmeId);
		return q.getResultList();
	}


}
