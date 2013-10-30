package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.RolFacade;
import com.sporsimdi.model.entity.Rol;
import com.sporsimdi.model.type.Status;

@Stateless
public class RolFacadeBean implements RolFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public RolFacadeBean() {
	}

	@Override
	public Rol findById(long id) {
		return entityManager.find(Rol.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Rol k where k.status =:status")
				.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Rol rol = entityManager.find(Rol.class, id);
		entityManager.remove(rol);
	}


	@Override
	public Rol findByKullanici(String kullanici) {
		Rol rol = (Rol) entityManager.createQuery("select r from Rol r where r.kullanici = :kullanici")
												.setParameter("kullanici", kullanici).getSingleResult();
		return rol;
	}


	@Override
	public void persist(Rol rol) {
		entityManager.persist(rol);
	}
	
	@Override
	public void merge(Rol rol) {
		entityManager.merge(rol);
	}

}
