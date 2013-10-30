package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.type.Status;

@Stateless
public class OrganizasyonFacadeBean implements OrganizasyonFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public OrganizasyonFacadeBean() {
	}

	@Override
	public void persist(Organizasyon organizasyon) {
		entityManager.persist(organizasyon);
	}

	@Override
	public void merge(Organizasyon organizasyon) {
		entityManager.merge(organizasyon);
	}

	@Override
	public void remove(Organizasyon organizasyon) {
		entityManager.remove(organizasyon);
	}

	@Override
	public void delete(Organizasyon organizasyon) {
		organizasyon.setStatus(Status.PASSIVE);
		entityManager.merge(organizasyon);
	}

	@Override
	public Organizasyon findById(long id) {
		return entityManager.find(Organizasyon.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organizasyon> listAll() {
		Query q = entityManager.createQuery("select o from Organizasyon o");
		return q.getResultList();
	}


	@Override
	public List<Organizasyon> getOrganizasyon(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		@SuppressWarnings("unchecked")
		List<Organizasyon> list = entityManager.createQuery("select k from Organizasyon k join fetch k.organizasyonTipi t")
												.setFirstResult(first)
												.setMaxResults(pageSize)
												.getResultList();
		for (Organizasyon organizasyon : list) {
			entityManager.detach(organizasyon);
		}
		return list;
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Donem k")
									.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organizasyon> listByIsletme(Isletme isletme) {
		Query q = entityManager.createQuery("select distinct o from Organizasyon o " +
											"left outer join fetch o.orgTesisListesi t " +
											"where o.isletme.id = :isletme")
								.setParameter("isletme", isletme.getId());
		return q.getResultList();
	}
	
}
