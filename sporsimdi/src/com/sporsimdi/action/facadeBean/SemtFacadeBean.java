package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.SemtFacade;
import com.sporsimdi.model.entity.Ilce;
import com.sporsimdi.model.entity.Semt;
import com.sporsimdi.model.type.Status;

@Stateless
public class SemtFacadeBean implements SemtFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public SemtFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Semt> getByStatus(Status status) {
		Query q = entityManager.createQuery("select k from Semt k where k.status =:status")
								.setParameter("status", status);
		return q.getResultList();
	}
	


	@Override
	public Semt findById(long id) {
		return	entityManager.find(Semt.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Semt> listAll() {
		Query q = entityManager.createQuery("select k from Semt k");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Semt> listByIlce(Ilce ilce) {
		return entityManager.createQuery("select k from Semt k where k.ilce = :ilce")
								.setParameter("ilce", ilce)
								.getResultList();
	}

	@Override
	public void persist(Semt semt) {
		entityManager.persist(semt);
		
	}
	 
	public void remove(long id){		 
		entityManager.remove(findById(id));
	}
	 
	public Semt update(long id){
		return entityManager.merge(findById(id));
	}
}

