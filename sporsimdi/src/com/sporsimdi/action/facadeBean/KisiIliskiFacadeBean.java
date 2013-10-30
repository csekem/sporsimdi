package com.sporsimdi.action.facadeBean;


import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.KisiIliskiFacade;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.type.Status;
import com.sporsimdi.model.type.YakinlikDerecesi;

@Stateless
public class KisiIliskiFacadeBean implements KisiIliskiFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KisiIliskiFacadeBean() {
	}

	@Override
	public void persist(KisiIliski kisiIliski) {
		entityManager.persist(kisiIliski);
	}

	@Override
	public void merge(KisiIliski kisiIliski) {
		entityManager.merge(kisiIliski);
	}

	@Override
	public void delete(KisiIliski kisiIliski) {
		kisiIliski.setStatus(Status.PASSIVE);
		entityManager.merge(kisiIliski);
	}

	@Override
	public void remove(KisiIliski kisiIliski) {
		entityManager.remove(kisiIliski);
	}


	@Override
	public KisiIliski getByStatus(Status status) {
		Query q = entityManager.createQuery("select k from KisiIliski k where k.status =:status")
								.setParameter("status", status);
		return (KisiIliski) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KisiIliski> listByKisi(Kisi kisi) {
		Query q = entityManager.createQuery("select k from KisiIliski k " +
											"join fetch k.iliskiliKisi ik " +
											"join fetch k.kisi kk " +
											"where kk.id = :id " )
								.setParameter("id", kisi.getId());
		return (List<KisiIliski>) q.getResultList();		
	}

	@Override
	public KisiIliski getByKisiYakinlik(Kisi kisi, YakinlikDerecesi yakinlik) throws Exception {
		try {
			return (KisiIliski) entityManager.createQuery("select k from KisiIliski k " +
														"join fetch k.iliskiliKisi ik " +
														"join fetch k.kisi kk " +
														"where kk.id = :id " +
														" and k.yakinlikDerecesi = :yakinlik" )
								.setParameter("id", kisi.getId())
								.setParameter("yakinlik", yakinlik)
								.getSingleResult();		
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public KisiIliski findById(long id) {
		return (KisiIliski) entityManager.createQuery("select k from KisiIliski k where k.id = :id ")
										.setParameter("id", id)
										.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KisiIliski> listAll() {
		Query q = entityManager.createQuery("select k from KisiIliski k ");
		return q.getResultList();
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from KisiIliski k")
									.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KisiIliski> listKisiIliski(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, String> filters) {
		List<KisiIliski> list = entityManager
				.createQuery("select k from KisiIliski k")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (KisiIliski kisiIliski : list) {
			entityManager.detach(kisiIliski);
		}
		return list;
	}
	

}
