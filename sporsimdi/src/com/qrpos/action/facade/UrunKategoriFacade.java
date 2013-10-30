package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.UrunKategori;
import com.sporsimdi.model.entity.Kasa;


@Local
public interface UrunKategoriFacade {

	public void persist(UrunKategori urunKategori);
	public void merge(UrunKategori urunKategori);
	public void remove(UrunKategori urunKategori);
	public void delete(UrunKategori urunKategori);

	public UrunKategori findById(long id);	
	
	public List<UrunKategori> listByKasa(Kasa kasa);

}