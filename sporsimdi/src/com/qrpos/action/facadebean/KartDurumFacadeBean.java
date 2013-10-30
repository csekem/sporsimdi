package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.KartDurumFacade;
import com.qrpos.model.entity.KartDurum;
import com.sporsimdi.model.type.Status;

@Stateless
public class KartDurumFacadeBean implements KartDurumFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;
	//deneme

	public KartDurumFacadeBean() {
	}

	@Override
	public KartDurum findById(long id) {
		return entityManager.find(KartDurum.class, id);
	}

	@Override
	public void persist(KartDurum kartDurum) {
		entityManager.persist(kartDurum);
	}

	@Override
	public void merge(KartDurum kartDurum) {
		entityManager.merge(kartDurum);
	}

	@Override
	public void remove(KartDurum kartDurum) {
		entityManager.remove(kartDurum);
	}

	@Override
	public void delete(KartDurum kartDurum) {
		kartDurum.setStatus(Status.PASSIVE);
		entityManager.merge(kartDurum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KartDurum> listByOrganizasyon(Long organizasyonId) {
		Query q = entityManager
				.createQuery(
						"select kd from KartDurum kd join fetch kd.qrcard q left outer join fetch kd.kart k left outer join fetch k.uye u where q.organizasyon.id = :organizasyonId order by q.no")
				.setParameter("organizasyonId", organizasyonId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public KartDurum findByQrCard(String no) {
		Query q = entityManager
				.createQuery("select kd from KartDurum kd where kd.qrcard.no = :no order by kd.updateDate desc")
				.setParameter("no", no).setMaxResults(1);
		List<KartDurum> kartDurumList = q.getResultList();

		if (kartDurumList.size() == 1) {
			return kartDurumList.get(0);
		} else {
			return null;
		}

	}

}
