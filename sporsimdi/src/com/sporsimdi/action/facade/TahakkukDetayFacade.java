package com.sporsimdi.action.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;

@Local
public interface TahakkukDetayFacade {

	public void persist(TahakkukDetay tahakkukDetay);

	public void merge(TahakkukDetay tahakkukDetay);

	public void remove(TahakkukDetay tahakkukDetay);

	public void delete(TahakkukDetay tahakkukDetay);

	public TahakkukDetay findById(long id);

	public List<TahakkukDetay> listAll();

	public List<TahakkukDetay> listByTahakkuk(Tahakkuk tahakkuk);

	public List<TahakkukDetay> listByTahakkukVadeTarihi(Tahakkuk tahakkuk, Date vadeTarihi);

	public List<TahakkukDetay> listByTahakkukYilAy(Tahakkuk tahakkuk, String yilAy);

	public List<TahakkukDetay> listByTahakkukBetweenDates(Tahakkuk tahakkuk, UtilDate dateBegin, UtilDate dateEnd);

}