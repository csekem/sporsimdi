package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;
import com.sporsimdi.model.type.Status;

@Stateless
public class UyeGrupFacadeBean implements UyeGrupFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public UyeGrupFacadeBean() {
	}

	public void persist(UyeGrup uyeGrup) {
		entityManager.persist(uyeGrup);
	}

	public void merge(UyeGrup uyeGrup) {
		entityManager.merge(uyeGrup);
	}

	public void remove(UyeGrup uyeGrup) {
		entityManager.remove(uyeGrup);
	}

	public void delete(UyeGrup uyeGrup) {
		uyeGrup.setStatus(Status.PASSIVE);
		entityManager.merge(uyeGrup);
	}

	public UyeGrup findById(long id) {
		return entityManager.find(UyeGrup.class, id);
	}

	public UyeGrup getByGrupUye(Grup grup, Uye uye) throws Exception {
		try {			
			return (UyeGrup) entityManager.createQuery("select ug from UyeGrup ug " +
														"join fetch ug.grup g " +
														"join fetch ug.uye u " +
														"where ug.uye.id = :uyeId " +
														" and ug.status = 'ACTIVE' " +
														" and ug.grup.id = :grupId")
											.setParameter("grupId", grup.getId())
											.setParameter("uyeId", uye.getId())
											.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UyeGrup> listAll() {
		Query q = entityManager.createQuery("select ug from UyeGrup ug " +
											"join fetch ug.grup g " +
											"join fetch ug.uye u ");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UyeGrup> listByUye(Uye uye) {
		Query q = entityManager.createQuery("select ug from UyeGrup ug " +
											"join fetch ug.grup g " +
											"join fetch ug.uye u " +
											"where ug.uye.id = :id " +
											" and ug.status = 'ACTIVE' ")
								.setParameter("id", uye.getId());
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UyeGrup> listByGrup(Grup grup) {
		Query q = entityManager.createQuery("select ug from UyeGrup ug " +
											"join fetch ug.grup g " +
											"join fetch ug.uye u " +
											"where ug.grup.id = :id " +
											" and ug.status = 'ACTIVE' " +
											"order by u.ad, u.soyad").
								setParameter("id", grup.getId());
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UyeGrup> listLikeUye(Grup grup, String ad) {
		Query q = entityManager.createQuery("select ug from UyeGrup ug " +
											"join fetch ug.grup g " +
											"join fetch ug.uye u " +
											"where ug.grup.id = :id " +
											" and ug.status = 'ACTIVE' " +
											" and ug.uye.ad like :ad")
								.setParameter("id", grup.getId())
								.setParameter("ad", "%"+ad+"%");
		return q.getResultList();
	}

	@Override
	public Long countByGrup(Grup grup) {
		Query q = entityManager.createQuery("select count(*) from UyeGrup ug " +
											"where ug.grup.id = :id " +
											" and ug.status = 'ACTIVE' ")
								.setParameter("id", grup.getId());
		return (Long) q.getSingleResult();
	}

}
