package com.sporsimdi.action.facadeBean;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.EntityManagerDao;

@Stateless
public class EntityManagerDaoImpl implements Serializable, EntityManagerDao {

	private static final long serialVersionUID = -8535319758833964664L;

	@PersistenceContext
	private EntityManager entityManager;

	public Object updateObject(Object object) {
		return entityManager.merge(object);
	}

	public void createObject(Object object) {
		entityManager.persist(object);
	}

	public void refresh(Object object) {
		entityManager.refresh(object);
	}

	public <T> T find(Class<T> clazz, Long id) {
		return entityManager.find(clazz, id);
	}

	public void deleteObject(Object object) {
		if (!entityManager.contains(object)) {
			object = entityManager.merge(object);
		}
		entityManager.remove(object);
	}

	public void flush() {
		entityManager.flush();
	}

}
