package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.Tesis;
import com.sporsimdi.model.type.Status;

@Stateless
public class IsletmeFacadeBean implements IsletmeFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public IsletmeFacadeBean() {
	}

	@Override
	public void persist(Isletme isletme) {
		entityManager.persist(isletme);
	}

	@Override
	public void merge(Isletme isletme) {
		entityManager.merge(isletme);
	}

	@Override
	public void remove(Isletme isletme) {
		entityManager.remove(isletme);
	}

	public void delete(Isletme isletme) {
		isletme.setStatus(Status.PASSIVE);
		entityManager.merge(isletme);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Isletme> listByStatus(Status status) {
		return entityManager.createQuery("select i from Isletme i where i.status = :status")
								.setParameter("status", status)
								.getResultList();
	}

	@Override
	public Isletme findById(long id) {
		return entityManager.find(Isletme.class, id);
	}

	public Isletme getByIsletmeKisi(Isletme isletme, Kisi kisi) throws Exception {
		try {
			return (Isletme) entityManager.createQuery("select i from Isletme i " +
														"join fetch i.kisiListesi kl " +
														"where i.id = :isletmeId " +
														" and kl.id = :kisiId")
											.setParameter("isletmeId", isletme.getId())
											.setParameter("kisiId", kisi.getId())
											.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Isletme> listAll() {
		return entityManager.createQuery("select i from Isletme i")
								.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Isletme> listByKisi(Kisi kisi) {
		return entityManager.createQuery(
				"select k.isletmeListesi from Kisi k where k.id =:id")
				.setParameter("id", kisi.getId())
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Isletme> listByTesis(Tesis tesis) {
		return entityManager.createQuery(
				"select t.isletmeListesi from Tesis t where t.id =:id")
				.setParameter("id", tesis.getId())
				.getResultList();
	}

	@Override
	public String getIsletmeTipi(Isletme isletme) {
		return (String) entityManager.createQuery("select t.adi from Isletme i,IsletmeTipi t where i.class=t.tipi and i.id = :id")
							.setParameter("id", isletme.getId())
							.getSingleResult();
	}

}
