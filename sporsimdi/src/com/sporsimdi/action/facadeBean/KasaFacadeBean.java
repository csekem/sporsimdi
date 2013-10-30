package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.type.Status;

@Stateless
public class KasaFacadeBean implements KasaFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public KasaFacadeBean() {
	}

	@Override
	public void persist(Kasa kasa) {
		entityManager.persist(kasa);
	}

	@Override
	public void merge(Kasa kasa) {
		entityManager.merge(kasa);
	}

	@Override
	public void remove(Kasa kasa) {
		entityManager.remove(kasa);
	}

	public void delete(Kasa kasa) {
		kasa.setStatus(Status.PASSIVE);
		entityManager.merge(kasa);
	}

	@Override
	public Kasa findById(long id) {
		return entityManager.find(Kasa.class, id);
	}


	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Kasa k")
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kasa> listAllByOrgTesis(OrgTesis orgTesis) {
		Query q = entityManager.createQuery("select k from Kasa k where k.orgTesis = :orgTesis")
								.setParameter("orgTesis", orgTesis);
		return q.getResultList();
	}
	
	@Override
	public Kasa getAnaKasaByOrgTesis(OrgTesis orgTesis) throws Exception {
		try {
			return (Kasa) entityManager.createQuery("select k from Kasa k where k.orgTesis = :orgTesis and k.anaKasa = true")
										.setParameter("orgTesis", orgTesis)
										.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

}
