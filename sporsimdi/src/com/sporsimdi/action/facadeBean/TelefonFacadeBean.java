package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.type.Status;
import com.sporsimdi.model.type.TelefonTipi;

@Stateless
public class TelefonFacadeBean implements TelefonFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public TelefonFacadeBean() {
	}

	@Override
	public void persist(Telefon telefon) {
		entityManager.persist(telefon);
	}

	@Override
	public void merge(Telefon telefon) {
		entityManager.merge(telefon);
	}

	@Override
	public void delete(Telefon telefon) {
		telefon.setStatus(Status.PASSIVE);
		entityManager.merge(telefon);
	}

	@Override
	public void remove(Telefon telefon) {
		entityManager.remove(telefon);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telefon> listByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Telefon k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}


	@Override
	public Telefon findById(long id) {
		Telefon telefon = entityManager.find(Telefon.class, id);
		return telefon;
	}

	@Override
	public Telefon findByIdEditMode(long id) {
		Telefon telefon = entityManager.find(Telefon.class, id);
		return telefon;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telefon> listAll() {
		Query q = entityManager.createQuery("select k from Telefon k ");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telefon> listByModelId(Long modelId) {
		return entityManager.createQuery("select k from Telefon k where k.modelId = :modelId")
							.setParameter("modelId", modelId)
							.getResultList();
	}

	@Override
	public Telefon getByModelIdTip(Long modelId, TelefonTipi tip) throws Exception {
		try {
			return (Telefon) entityManager.createQuery("select t from Telefon t " +
													"where t.modelId = :modelId " +
													" and t.telefonTipi = :tip")
										.setParameter("modelId", modelId)
										.setParameter("tip", tip)
										.getSingleResult();		
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	
}
