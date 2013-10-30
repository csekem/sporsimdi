package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.type.Status;

@Stateless
public class OrgTesisFacadeBean implements OrgTesisFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public OrgTesisFacadeBean() {
	}

	public void persist(OrgTesis orgTesis) {
		entityManager.persist(orgTesis);
	}

	public void merge(OrgTesis orgTesis) {
		entityManager.merge(orgTesis);
	}

	public void remove(OrgTesis orgTesis) {
		entityManager.remove(orgTesis);
	}

	public void delete(OrgTesis orgTesis) {
		orgTesis.setStatus(Status.PASSIVE);
		entityManager.merge(orgTesis);
	}

	public OrgTesis findById(long id) {
		return entityManager.find(OrgTesis.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<OrgTesis> listAll() {
		Query q = entityManager.createQuery("select o from OrgTesis o");
		return q.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<OrgTesis> getOrgTesis(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, String> filters) {
		List<OrgTesis> list = entityManager.createQuery("select k from OrgTesis k join fetch k.organizasyonTipi t")
												.setFirstResult(first)
												.setMaxResults(pageSize)
												.getResultList();
		for (OrgTesis orgTesis : list) {
			entityManager.detach(orgTesis);
		}
		return list;
	}

	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from OrgTesis k")
									.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<OrgTesis> listByOrganizasyon(Long organizasyonId) {
		Query q = entityManager.createQuery("select distinct o from OrgTesis o " +
											"left outer join fetch o.grupListesi g " +
											"left outer join fetch o.tesis t " +
											"where o.organizasyon.id = :id")
								.setParameter("id", organizasyonId);
		return q.getResultList();
	}
	
}
