package com.sporsimdi.action.facadeBean;


import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.type.Status;

@Stateless
public class KisiFacadeBean implements KisiFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KisiFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kisi> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Kisi k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Kisi> listByIsletme(Isletme isletme) {
		Query q = entityManager.createQuery("select k from Kisi k join k.isletmeListesi i " +
											"where i.id = :id " +
											"order by k.ad, k.soyad")
								.setParameter("id", isletme.getId());
		return (List<Kisi>) q.getResultList();		
	}

	@Override
	public void remove(long id) {
		Kisi kisi = entityManager.find(Kisi.class, id);
		entityManager.remove(kisi);
	}


	@Override
	public Kisi findById(long id) {
		Kisi kisi = new Kisi();
		
		kisi = (Kisi) entityManager
				.createQuery(
						"select s from Kisi s " + " where s.id = :id ")
				.setParameter("id", id).getSingleResult();
		
		return kisi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kisi> getAll() {
		Query q = entityManager.createQuery("select k from Kisi k ");
		return q.getResultList();
	}

	@Override
	public void persist(Kisi kisi) {
		entityManager.persist(kisi);
	}

	@Override
	public void merge(Kisi kisi) {
		entityManager.merge(kisi);
	}

	@Override
	public Kisi findByIdEager(long id) {
		Kisi kisi = (Kisi) entityManager.createQuery("select k from Kisi k " +
													 " join fetch k.resim r " +
													" where k.id=:id")
										.setParameter("id", id)
										.getSingleResult();
		return kisi;
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Kisi k")
									.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kisi> listKisi(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, String> filters) {
		List<Kisi> list = entityManager
				.createQuery("select k from Kisi k")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (Kisi kisi : list) {
			entityManager.detach(kisi);
		}
		return list;
	}
	
	@Override
	 public String getKisiTipi(Kisi kisi) {
		return (String) entityManager.createQuery("select k.class from Kisi k where k.id = :id")
									.setParameter("id", kisi.getId())
									.getSingleResult();
	 }

}
