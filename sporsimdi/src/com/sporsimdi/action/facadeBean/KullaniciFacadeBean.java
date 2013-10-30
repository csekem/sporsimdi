package com.sporsimdi.action.facadeBean;


import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.KullaniciFacade;
import com.sporsimdi.model.entity.Kullanici;
import com.sporsimdi.model.type.Status;

@Stateless
public class KullaniciFacadeBean implements KullaniciFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KullaniciFacadeBean() {
	}

	@Override
	public Kullanici findById(long id) {
		return entityManager.find(Kullanici.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kullanici> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Kullanici k where k.status =:status")
				.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Kullanici kullanici = entityManager.find(Kullanici.class, id);
		entityManager.remove(kullanici);
	}


	@Override
	public Kullanici findByKullanici(String kullanici) {

		Kullanici kull = new Kullanici();
		
		@SuppressWarnings("unchecked")
		List<Kullanici> kullaniciList = entityManager
				.createQuery(
						"select s from Kullanici s join fetch s.kisi k join fetch k.isletmeListesi i where s.kullanici = :kul ")
				.setParameter("kul", kullanici).getResultList();
		if(kullaniciList.size() > 0) {
			kull = kullaniciList.get(0);
		}
		else {
			kull = null;
		}
		
		return kull;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kullanici> getAll() {
		Query q = entityManager.createQuery("select k from Kullanici k ");
		return q.getResultList();
	}

	@Override
	public void persist(Kullanici kullanici) {
		entityManager.persist(kullanici);
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Kullanici k")
									.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kullanici> listKullanici(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, String> filters) {
		List<Kullanici> list = entityManager
				.createQuery("select k from Kullanici k")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (Kullanici kullanici : list) {
			entityManager.detach(kullanici);
		}
		return list;
	}

}
