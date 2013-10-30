package com.sporsimdi.action.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Bakiye;
import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.type.Status;

@Local
public interface BakiyeFacade {

	public Bakiye findById(long id);

	public Bakiye findLatest(Kasa kasa);

	public Bakiye findFirst(Kasa kasa);

	public List<Bakiye> listAll();
	
	public Long getCount();

	public void persist(Bakiye bakiye);

	public Bakiye update(long id);

	public void remove(long id);
	
	public List<Bakiye> getByStatus(Status status);

	public List<Bakiye> listBakiye(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters);

	public Bakiye findByIdEditMode(long id);

	public List<Bakiye> listAllByIsletme(Isletme isletme);

	public List<Bakiye> listAllByKasa(Kasa kasa);

	public List<Bakiye> listAllByTarih(Date tarih, Kasa kasa);
	
	public void updateAllBakiyeAfter(Defter defter);
	
	public Object listDefterSumByTarih(Date tarih, Kasa kasa);

}