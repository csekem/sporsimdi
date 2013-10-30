package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.WebIletisimFacade;
import com.sporsimdi.model.entity.WebIletisim;
import com.sporsimdi.model.type.Status;

@Stateless
public class WebIletisimFacadeBean implements WebIletisimFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public WebIletisimFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebIletisim> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from WebIletisim k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		WebIletisim webIletisim = entityManager.find(WebIletisim.class, id);
		entityManager.remove(webIletisim);
	}


	@Override
	public WebIletisim findById(long id) {
		WebIletisim webIletisim = new WebIletisim();		
		webIletisim = (WebIletisim) entityManager
				.createQuery(
						"select s from WebIletisim s where s.id = :id ")
				.setParameter("id", id).getSingleResult();		
		return webIletisim;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebIletisim> getAll() {
		Query q = entityManager.createQuery("select k from WebIletisim k ");
		return q.getResultList();
	}

	@Override
	public void persist(WebIletisim webIletisim) {
		entityManager.persist(webIletisim);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebIletisim> listByModelId(Long modelId) {
		return entityManager.createQuery("select k from WebIletisim k where k.modelId = :modelId")
							.setParameter("modelId", modelId)
							.getResultList();
	}

	
}
