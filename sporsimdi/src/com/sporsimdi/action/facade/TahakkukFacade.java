package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Tahakkuk;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.UyeGrup;

@Local
public interface TahakkukFacade {

	public void persist(Tahakkuk tahakkuk);

	public void merge(Tahakkuk tahakkuk);

	public void remove(Tahakkuk tahakkuk);

	public void delete(Tahakkuk tahakkuk);

	public Tahakkuk findById(long id);

	public Tahakkuk findByIdEager(long id);

	public List<Tahakkuk> listAll();

	public Tahakkuk getByTahakkukDetay(TahakkukDetay tahakkukDetay) throws Exception;

	public Tahakkuk getByUyeGrup(UyeGrup uyeGrup) throws Exception;

	public List<Tahakkuk> listByUyeGrup(UyeGrup uyeGrup) throws Exception;

	public UyeGrup getUyeGrup(Tahakkuk tahakkuk) throws Exception;

}