package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.KartFacade;
import com.qrpos.model.entity.Kart;
import com.sporsimdi.model.type.Status;

@Stateless
public class KartFacadeBean implements KartFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KartFacadeBean() {
	}

	@Override
	public Kart findById(long id) {
		return entityManager.find(Kart.class, id);
	}

	@Override
	public void persist(Kart kart) {
		entityManager.persist(kart);
	}

	@Override
	public void merge(Kart kart) {
		entityManager.merge(kart);
	}

	@Override
	public void remove(Kart kart) {
		entityManager.remove(kart);
	}

	@Override
	public void delete(Kart kart) {
		kart.setStatus(Status.PASSIVE);
		entityManager.merge(kart);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kart> listByOrganizasyon(Long organizasyonId) {
		Query q = entityManager.createQuery(
				"select k from Kart k where k.organizasyon.id = :organizasyonId order by k.id").setParameter(
				"organizasyonId", organizasyonId);
		return q.getResultList();
	}

}
