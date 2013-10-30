package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.IlFacade;
import com.sporsimdi.model.entity.Il;
import com.sporsimdi.model.type.Status;

@Stateless
public class IlFacadeBean implements IlFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public IlFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Il> getByStatus(Status status) {
		Query q = entityManager.createQuery("select k from Il k where k.status =:status")
								.setParameter("status", status);
		return q.getResultList();
	}
	
	
	@Override
	public Il findById(long id) {
		return	entityManager.find(Il.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Il> getAll() {
		Query q = entityManager.createQuery("select distinct k from Il k join fetch k.ilceListesi l");
		List<Il> list = q.getResultList();
		return list;
	}

	@Override
	public void persist(Il il) {
		entityManager.persist(il);		
	}
		
	public void remove(long id){
		Il il = entityManager.find(Il.class, id);
		entityManager.remove(il);
	}
	 
	public Il update(long id){
		return entityManager.merge(findById(id));
	}
	    
}
