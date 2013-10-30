package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.UyeFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.type.Status;

@Stateless
public class UyeFacadeBean implements UyeFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public UyeFacadeBean() {
	}

	public void persist(Uye uye) {
		entityManager.persist(uye);
	}

	public void merge(Uye uye) {
		entityManager.merge(uye);
	}

	public void remove(Uye uye) {
		entityManager.remove(uye);
	}

	public void delete(Uye uye) {
		uye.setStatus(Status.PASSIVE);
		entityManager.merge(uye);
	}

	public Uye findById(long id) {
		return entityManager.find(Uye.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Uye> getByStatus(Status status) {
		Query q = entityManager.createQuery("select k from Uye k where k.status =:status").setParameter("status", status);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Uye> listAll() {
		Query q = entityManager.createQuery("select u from Uye u join fetch u.grupListesi gl join fetch u.isletme o");
		return q.getResultList();
	}

	@Override
	public List<Uye> getUye(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		@SuppressWarnings("unchecked")
		List<Uye> list = entityManager.createQuery("select k from Uye k").setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (Uye uye : list) {
			entityManager.detach(uye);
		}
		return list;
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Uye k").getSingleResult();
	}

	@Override
	public Uye findByIdEager(long id) {
		Uye uye = (Uye) entityManager
				.createQuery(
						"select u from Uye u " + " left outer join fetch u.grupListesi gl " + " left outer join fetch gl.grup g " + " join fetch u.meslek m "
								+ " where u.id=:id").setParameter("id", id).getSingleResult();
		return uye;
	}

	@Override
	public Uye getWithIsletmeByIdEager(long id) {
		Uye uye = (Uye) entityManager.createQuery("select u from Uye u " + " join fetch u.isletmeListesi il " + " where u.id=:id").setParameter("id", id)
				.getSingleResult();
		return uye;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Uye> listByIsletme(Isletme isletme) {
		Query q = entityManager.createQuery(
				"select u from Uye u " + "join u.isletmeListesi il " + "join fetch u.meslek m " + "where il.id = :id " + "order by u.ad, u.soyad")
				.setParameter("id", isletme.getId());
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Uye> listByGrup(Long grupId) {
		return entityManager
				.createQuery(
						"select u from Uye u " + "join u.grupListesi g " + "left outer join fetch u.meslek m " + "where g.grup.id = :id "
								+ " and g.status = 'ACTIVE' " + "order by u.ad, u.soyad").setParameter("id", grupId).getResultList();
	}

}
