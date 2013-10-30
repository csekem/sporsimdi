package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.DefterFacade;
import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.type.Status;

@Stateless
public class DefterFacadeBean implements DefterFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public DefterFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Defter> getByStatus(Status status) {
		Query q = entityManager
				.createQuery("select k from Defter k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Defter defter = entityManager.find(Defter.class, id);
		entityManager.remove(defter);
	}

	@Override
	public Defter findById(long id) {
		return entityManager.find(Defter.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Defter> listAll() {
		Query q = entityManager.createQuery("select k from Defter k");
		return q.getResultList();
	}

	@Override
	public void persist(Defter defter) {
		entityManager.persist(defter);
	}

	@Override
	public List<Defter> listDefter(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		@SuppressWarnings("unchecked")
		List<Defter> list = entityManager
				.createQuery("select k from Defter k join fetch k.isletme i")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (Defter defter : list) {
			entityManager.detach(defter);
		}
		return list;
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Defter k")
				.getSingleResult();
	}

	@Override
	public Defter findByIdEditMode(long id) {
		Defter d = entityManager.find(Defter.class, id);
		return d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Defter> listAllByIsletme(Isletme isletme) {
		Query q = entityManager.createQuery("select d from Defter d join fetch d.isletme where d.isletme = :isletme")
								.setParameter("isletme", isletme);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Defter> listAllByKasa(Kasa kasa) {
		Query q = entityManager.createQuery("select d from Defter d join fetch d.isletme where d.kasa = :kasa")
								.setParameter("kasa", kasa);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Defter> listAllByTarih(Date tarih, Kasa kasa) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");
		Query q = entityManager.createQuery("select d from Defter d join fetch d.kullanici k where to_char(d.tarih,'YYYY/MM/DD') = :tarih and d.kasa = :kasa order by d.id")
								.setParameter("tarih", format.format(tarih))
								.setParameter("kasa", kasa);
		return q.getResultList();
	}
	
	@Override
	public Defter findLatest(Defter defter) {
		Long id;
		if (defter.getId()==null) {
			// kayıt ekleme ise aynı gunde son kaydı bul
			id = (Long) entityManager.createQuery("select max(d.id) from Defter d where d.tarih = :tarih and d.kasa = :kasa")
									.setParameter("tarih", defter.getTarih())
									.setParameter("kasa", defter.getKasa())
									.getSingleResult();			
		} else {
			// kayıt silme ise aynı gunde bir önceki kaydı bul
			id = (Long) entityManager.createQuery("select max(d.id) from Defter d where d.tarih = :tarih and d.kasa = :kasa and d.id < :id")
									.setParameter("tarih", defter.getTarih())
									.setParameter("kasa", defter.getKasa())
									.setParameter("id", defter.getId())
									.getSingleResult();			
		}
		// aynı gunde kayıt yoksa bir önceki tarihteki son kaydı bul
		if (id==null) {
			id = (Long) entityManager.createQuery("select max(d.id) from Defter d where d.kasa = :kasa and d.tarih = " +
											"( select max(d.tarih) from Defter d where d.tarih < :tarih and d.kasa = :kasa ) ")
									.setParameter("tarih", defter.getTarih())
									.setParameter("kasa", defter.getKasa())
									.getSingleResult();
		}
		
		if (id==null) {
			return null;
		}
		
		return (Defter) entityManager.createQuery("select d from Defter d where d.id = :id")
									.setParameter("id", id)
									.getSingleResult();
	}

	@Override
	public Defter findFirst(Kasa kasa) {
		Query q = entityManager.createQuery("select min(d.id) from Defter d where d.kasa = :kasa and d.tarih = " +
											"( select min(d.tarih) from Defter d where d.kasa = :kasa ) ")
								.setParameter("kasa", kasa);
		Long id = (Long) q.getSingleResult();
		if (id==null) {
			return null;
		}
		
		Query d = entityManager.createQuery("select d from Defter d where d.id = :id")
								.setParameter("id", id);
		return (Defter) d.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAllBakiyeAfter(Defter defter) {
		Defter tmpDefter = defter;
		
		Query q = entityManager.createQuery("select d from Defter d where d.tarih = :tarih and d.kasa = :kasa and id > :id order by d.tarih, d.id")
								.setParameter("tarih", defter.getTarih())
								.setParameter("kasa", defter.getKasa())
								.setParameter("id", defter.getId());
		List<Defter> defterList = q.getResultList();
		for (Defter def : defterList) {
			def.setBakiye(tmpDefter.getBakiye().add(def.getGelir().subtract(def.getGider())));
			entityManager.merge(def);
			tmpDefter = def;
		}

		q = entityManager.createQuery("select d from Defter d where d.tarih > :tarih and d.kasa = :kasa order by d.tarih, d.id")
						.setParameter("tarih", defter.getTarih())
						.setParameter("kasa", defter.getKasa());
		defterList = q.getResultList();
		for (Defter def : defterList) {
			def.setBakiye(tmpDefter.getBakiye().add(def.getGelir().subtract(def.getGider())));
			entityManager.merge(def);
			tmpDefter = def;
		}

	}

}
