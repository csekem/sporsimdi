package com.qrpos.action.facadebean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qrpos.action.facade.QRCardFacade;
import com.qrpos.model.entity.QRCard;
import com.sporsimdi.model.type.Status;

@Stateless
public class QRCardFacadeBean implements QRCardFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public QRCardFacadeBean() {
	}

	@Override
	public QRCard findById(long id) {
		return entityManager.find(QRCard.class, id);
	}

	@Override
	public void persist(QRCard qrcard) {
		entityManager.persist(qrcard);
	}

	@Override
	public void merge(QRCard qrcard) {
		entityManager.merge(qrcard);
	}

	@Override
	public void remove(QRCard qrcard) {
		entityManager.remove(qrcard);
	}

	@Override
	public void delete(QRCard qrcard) {
		qrcard.setStatus(Status.PASSIVE);
		entityManager.merge(qrcard);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QRCard> listByOrganizasyon(Long organizasyonId) {
		Query q = entityManager.createQuery(
				"select q from QRCard q where q.organizasyon.id = :organizasyonId order by q.no").setParameter(
				"organizasyonId", organizasyonId);
		return q.getResultList();
	}

	@Override
	public QRCard findByNo(String no) {
		@SuppressWarnings("unchecked")
		List<QRCard> list = entityManager.createQuery("select q from QRCard q where q.no = :no").setParameter("no", no)
				.getResultList();

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
