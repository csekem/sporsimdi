package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.type.Status;

@Local
public interface TahsilatFacade {

	public void persist(Tahsilat tahsilat);
	public void merge(Tahsilat tahsilat);
	public void remove(Tahsilat tahsilat);
	public void delete(Tahsilat tahsilat);

	public Tahsilat findById(long id);

	public List<Tahsilat> listAll();
	
	public Uye getUyeByTahsilat(long tahsilatId);

	public TahakkukDetay getTahakkukDetayByTahsilat(long tahsilatId);

	public List<Tahsilat> getByStatus(Status status);

	public List<Tahsilat> listByTahakkukDetay(TahakkukDetay tahakkukDetay);

}