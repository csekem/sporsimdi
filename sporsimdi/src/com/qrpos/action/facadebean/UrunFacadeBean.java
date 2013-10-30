package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Urun;
import com.sporsimdi.model.type.Status;

@Stateless
public class UrunFacadeBean implements UrunFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public UrunFacadeBean() {
	}

	@Override
	public Urun findById(long id) {
		return entityManager.find(Urun.class, id);
	}

	@Override
	public void persist(Urun urun) {
		entityManager.persist(urun);
	}

	@Override
	public void merge(Urun urun) {
		entityManager.merge(urun);
		entityManager.flush();
	}

	@Override
	public void remove(Urun urun) {
		entityManager.remove(urun);
	}

	@Override
	public void delete(Urun urun) {
		urun.setStatus(Status.PASSIVE);
		entityManager.merge(urun);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Urun> listByKasa(Long kasaId) {
		Query q = entityManager.createQuery("select u from Urun u where u.kasa.id = :kasaId order by u.sira")
				.setParameter("kasaId", kasaId);
		return q.getResultList();
	}

	public int findNextSira(long kasaId) {
		Query q = entityManager.createQuery("select nvl(max(u.sira),0) from Urun u where u.kasa.id = :kasaId")
				.setParameter("kasaId", kasaId);
		return (Integer) q.getSingleResult();
	}
}
