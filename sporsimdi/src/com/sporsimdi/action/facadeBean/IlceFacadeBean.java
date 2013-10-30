package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.IlceFacade;
import com.sporsimdi.model.entity.Il;
import com.sporsimdi.model.entity.Ilce;
import com.sporsimdi.model.type.Status;

@Stateless
public class IlceFacadeBean implements IlceFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public IlceFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ilce> getByStatus(Status status) {
		Query q = entityManager.createQuery("select k from Ilce k where k.status =:status")
			.setParameter("status", status);

		return q.getResultList();
	}
	


	@Override
	public Ilce findById(long id) {
		return	entityManager.find(Ilce.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ilce> getAll() {
		Query q = entityManager.createQuery("select k from Ilce k");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ilce> getAllByIl(Il il) {
		return entityManager.createQuery("select k from Ilce k where k.il = :il")
								.setParameter("il", il)
								.getResultList();
	}

	@Override
	public void persist(Ilce ilce) {
		entityManager.persist(ilce);
		
	}
	
	public void remove(long id){
		entityManager.remove(findById(id));		 
	}
	 
	public Ilce update(long id){
		return entityManager.merge(findById(id));
	}
	
}

