package com.sporsimdi.action.facadeBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.BakiyeFacade;
import com.sporsimdi.model.entity.Bakiye;
import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.type.Status;

@Stateless
public class BakiyeFacadeBean implements BakiyeFacade, Serializable {

	private static final long serialVersionUID = 1351677350164757297L;
	
	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public BakiyeFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bakiye> getByStatus(Status status) {
		Query q = entityManager
				.createQuery("select k from Bakiye k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Bakiye bakiye = entityManager.find(Bakiye.class, id);
		entityManager.remove(bakiye);
	}

	@Override
	public Bakiye findById(long id) {
		return entityManager.find(Bakiye.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bakiye> listAll() {
		Query q = entityManager.createQuery("select k from Bakiye k");
		return q.getResultList();
	}

	@Override
	public void persist(Bakiye bakiye) {
		entityManager.persist(bakiye);
	}

	public Bakiye update(long id){
		return entityManager.merge(findById(id));
	}

	@Override
	public List<Bakiye> listBakiye(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		@SuppressWarnings("unchecked")
		List<Bakiye> list = entityManager
				.createQuery("select k from Bakiye k join fetch k.isletme i")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
		for (Bakiye bakiye : list) {
			entityManager.detach(bakiye);
		}
		return list;
	}

	@Override
	public Long getCount() {
		return (Long) entityManager.createQuery("select count(k) from Bakiye k")
				.getSingleResult();
	}

	@Override
	public Bakiye findByIdEditMode(long id) {
		Bakiye d = entityManager.find(Bakiye.class, id);
		return d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bakiye> listAllByIsletme(Isletme isletme) {
		Query q = entityManager.createQuery("select d from Bakiye d join fetch d.isletme where d.isletme = :isletme")
								.setParameter("isletme", isletme);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bakiye> listAllByKasa(Kasa kasa) {
		Query q = entityManager.createQuery("select d from Bakiye d join fetch d.isletme where d.kasa = :kasa")
								.setParameter("kasa", kasa);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bakiye> listAllByTarih(Date tarih, Kasa kasa) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");
		Query q = entityManager.createQuery("select d from Bakiye d where to_char(d.tarih,'YYYY/MM/DD') = :tarih and d.kasa = :kasa order by d.id")
								.setParameter("tarih", format.format(tarih))
								.setParameter("kasa", kasa);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Bakiye findLatest(Kasa kasa) {
		List<Bakiye> bakiyeList = entityManager.createQuery("select b from Bakiye b where b.tarih = :tarih and b.kasa = :kasa")
									.setParameter("tarih", findLatestDay(kasa))
									.setParameter("kasa", kasa)
									.getResultList();
		if (bakiyeList.size() == 0) {
			return new Bakiye();
		} else {
			return bakiyeList.get(0);
		}
	}

	@Override
	public Bakiye findFirst(Kasa kasa) {
		Query q = entityManager.createQuery("select min(d.id) from Bakiye d where d.kasa = :kasa and d.tarih = " +
											"( select min(d.tarih) from Bakiye d where d.kasa = :kasa ) ")
								.setParameter("kasa", kasa);
		Long id = (Long) q.getSingleResult();
		if (id==null) {
			return null;
		}
		
		Query d = entityManager.createQuery("select d from Bakiye d where d.id = :id")
								.setParameter("id", id);
		return (Bakiye) d.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAllBakiyeAfter(Defter defter) {
		// TODO hızlandırılması gerek, boş günler sessiona alınabilir, bakiyeler sessiona

		// son gün bulunur
		Date lastDay = findLatestDay(defter.getKasa());
		Calendar lastCal = Calendar.getInstance(new Locale("tr"));
		lastCal.setTime(lastDay);
		
		// gün saat çıkarılarak bulunur
		Calendar tmp = Calendar.getInstance(new Locale("tr"));
		tmp.setTime(defter.getTarih());

		Calendar dayCal = Calendar.getInstance(new Locale("tr"));
		dayCal.set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONDAY), tmp.get(Calendar.DATE), 0, 0, 0);
		dayCal.set(Calendar.MILLISECOND, 0);
		Date dayDate = dayCal.getTime();

		Query q = entityManager.createQuery("select b from Bakiye b where b.tarih between :tarih1 and :tarih2 and b.kasa = :kasa order by b.tarih")
								.setParameter("tarih1", dayDate)
								.setParameter("tarih2", lastDay)
								.setParameter("kasa", defter.getKasa());
		List<Bakiye> bakiyeList = q.getResultList();
		
		// günün defter kayıtlarına ait bakiyeler toplanır.	
		Object[] obj = (Object[]) listDefterSumByTarih(defter.getTarih(), defter.getKasa());
		BigDecimal gelir = new BigDecimal(obj[0].toString());
		BigDecimal gider = new BigDecimal(obj[1].toString());
		
		// ilk bakiyeden önceki bir güne ait kayıt girildiyse
		if (bakiyeList.size()>0 && bakiyeList.get(0).getTarih().compareTo(dayDate) > 0) {
			Calendar cal = Calendar.getInstance(new Locale("tr"));
			cal.setTime(bakiyeList.get(0).getTarih());
			cal.add(Calendar.DATE, -1);
			createBakiyeBetweenDates(dayDate, cal.getTime(), defter.getKasa());

			bakiyeList = q.getResultList();
		}
		

		// günün defter kayıtlarına ait fark bakiyeler bulunur.	
		BigDecimal gelirFark = new BigDecimal(0);
		BigDecimal giderFark = new BigDecimal(0);	
		if (bakiyeList.size()>0 && bakiyeList.get(0).getTarih().compareTo(dayDate) == 0) {
			Bakiye bak = bakiyeList.get(0);
			gelirFark = gelir.subtract(bak.getGelir());
			giderFark = gider.subtract(bak.getGider());
			
			bak.setGelir(gelirFark.add(bak.getGelir()));
			bak.setGider(giderFark.add(bak.getGider()));
			bak.setBakiye(bak.getBakiye().add(gelirFark).subtract(giderFark));
			update(bak.getId());
			bakiyeList.remove(0);			
		}
		
		// sonraki günler için defter kayıtlarına ait bakiyeler toplanır.	
		Calendar cal = Calendar.getInstance(new Locale("tr"));
		cal.setTime(defter.getTarih());
		for (cal.add(Calendar.DATE, 1); cal.compareTo(lastCal) <= 0; cal.add(Calendar.DATE, 1)) {
				if (bakiyeList.size()==0 || bakiyeList.get(0).getTarih().compareTo(cal.getTime()) != 0) {
					Bakiye bak = new Bakiye();
					bak.setKasa(defter.getKasa());
					bak.setTarih(defter.getTarih());
					bak.setGelir(gelir);
					bak.setGider(gider);
					bak.setBakiye(bak.getGelir().subtract(bak.getGider()));
					persist(bak);
				} else {
					Bakiye bak = bakiyeList.get(0);
					bak.setBakiye(bak.getBakiye().add(gelirFark).subtract(giderFark));
					update(bak.getId());
					bakiyeList.remove(0);
				}
		}				
	}

	public Date findLatestDay(Kasa kasa) {
		Query q = entityManager.createQuery("select max(b.tarih) from Bakiye b where b.kasa = :kasa")
								.setParameter("kasa", kasa);		
		Date maxDate = (Date) q.getSingleResult();
		
		
		Calendar tmp = Calendar.getInstance(new Locale("tr"));

		Calendar cal = Calendar.getInstance(new Locale("tr"));
		cal.set(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONDAY), tmp.get(Calendar.DATE), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();

		if (maxDate == null) {
			createBakiyeBetweenDates(today, today, kasa);
			maxDate = today;
		}

		if (maxDate == null || maxDate.compareTo(today) < 0 ) {
			createBakiyeBetweenDates(maxDate, today, kasa);
			maxDate = today;
		}
		return maxDate;
	}
	
	@SuppressWarnings("unchecked")
	public void createBakiyeBetweenDates(Date beginDate, Date endDate, Kasa kasa) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");

		// son bakiye bulunur
		Query q = entityManager.createQuery("select b from Bakiye b where b.kasa = :kasa and to_char(b.tarih,'YYYY/MM/DD') = :tarih")
								.setParameter("kasa", kasa)		
								.setParameter("tarih", format.format(beginDate));
		List<Bakiye> maxBakiyeList = q.getResultList();
		Bakiye maxBakiye;
		if (maxBakiyeList.size()>0) {
			maxBakiye = maxBakiyeList.get(0);
		} else {
			maxBakiye = new Bakiye();
			maxBakiye.setKasa(kasa);
			maxBakiye.setGelir(new BigDecimal(0));
			maxBakiye.setGider(new BigDecimal(0));
			maxBakiye.setBakiye(new BigDecimal(0));
			maxBakiye.setTarih(beginDate);			
		}
		
		// iki tarih arası bakiye oluşturulur
		Calendar calBeginDate = Calendar.getInstance(new Locale("tr"));
		calBeginDate.setTime(beginDate);
		if (maxBakiyeList.size()==0) {
			calBeginDate.add(Calendar.DATE, -1);
		}

		Calendar calEndDate = Calendar.getInstance(new Locale("tr"));
		calEndDate.setTime(endDate);

		for (calBeginDate.add(Calendar.DATE, 1); calBeginDate.compareTo(calEndDate) <= 0; calBeginDate.add(Calendar.DATE, 1)) {
			Bakiye bak = new Bakiye();
			bak.setKasa(maxBakiye.getKasa());
			bak.setGelir(new BigDecimal(0));
			bak.setGider(new BigDecimal(0));
			bak.setBakiye(maxBakiye.getBakiye());
			bak.setTarih(calBeginDate.getTime());
			persist(bak);
		}

	}

	public Object listDefterSumByTarih(Date tarih, Kasa kasa) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");
		Query q = entityManager.createQuery("select sum(nvl(d.gelir,0)), sum(nvl(d.gider,0)) from Defter d where d.kasa = :kasa and to_char(d.tarih,'YYYY/MM/DD') = :tarih")
								.setParameter("kasa", kasa)		
								.setParameter("tarih", format.format(tarih));
		return q.getSingleResult();
	}

}
