package com.sporsimdi.action.facadeBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.TahakkukFacade;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.UyeGrup;
import com.sporsimdi.model.type.Status;

@Stateless
public class TahakkukFacadeBean implements TahakkukFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public void persist(Tahakkuk tahakkuk) {
		entityManager.persist(tahakkuk);
	}

	public void merge(Tahakkuk tahakkuk) {
		entityManager.merge(tahakkuk);
	}

	public void remove(Tahakkuk tahakkuk) {
		entityManager.remove(findById(tahakkuk.getId()));
	}

	public void delete(Tahakkuk tahakkuk) {
		tahakkuk.setStatus(Status.PASSIVE);
		entityManager.merge(tahakkuk);
	}

	@Override
	public Tahakkuk findById(long id) {
		return entityManager.find(Tahakkuk.class, id);
	}

	@Override
	public Tahakkuk findByIdEager(long id) {
		Query q = entityManager.createQuery(
				"select t from Tahakkuk t " + "join fetch t.tahakkukDetayListesi td " + "where t.id = :id " + "order by td.taksitNo").setParameter("id", id);
		return (Tahakkuk) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tahakkuk> listAll() {
		Query q = entityManager.createQuery("select t from Tahakkuk t");
		return q.getResultList();
	}

	@Override
	public Tahakkuk getByUyeGrup(UyeGrup uyeGrup) throws Exception {
		try {
			return (Tahakkuk) entityManager
					.createQuery(
							"select t from Tahakkuk t " + "join fetch t.uyeGrup ug " + "join fetch t.tarife tr "
									+ "left outer join fetch t.tahakkukDetayListesi td " + "where t.status = 'ACTIVE' " +
									// "and td.status = 'ACTIVE' " +
									"and t.uyeGrup = :uyeGrup").setParameter("uyeGrup", uyeGrup).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tahakkuk> listByUyeGrup(UyeGrup uyeGrup) throws Exception {
		try {
			return entityManager
					.createQuery(
							"select distinct t from Tahakkuk t " + "join fetch t.uyeGrup ug " + "join fetch t.tarife tr "
									+ "left outer join fetch t.tahakkukDetayListesi td " + "where t.uyeGrup = :uyeGrup").setParameter("uyeGrup", uyeGrup)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Tahakkuk getByTahakkukDetay(TahakkukDetay tahakkukDetay) throws Exception {
		try {
			return (Tahakkuk) entityManager.createQuery("select td.tahakkuk from TahakkukDetay td where td.id = :id").setParameter("id", tahakkukDetay.getId())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public UyeGrup getUyeGrup(Tahakkuk tahakkuk) throws Exception {
		try {
			return (UyeGrup) entityManager.createQuery("select t.uyeGrup from Tahakkuk t where t.id = :id").setParameter("id", tahakkuk.getId())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

}
