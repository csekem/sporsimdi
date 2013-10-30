package com.sporsimdi.action.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.type.Status;

@Local
public interface DefterFacade {

	public Defter findById(long id);

	public Defter findLatest(Defter defter);

	public Defter findFirst(Kasa kasa);

	public List<Defter> listAll();
	
	public Long getCount();

	public void persist(Defter defter);

	public List<Defter> getByStatus(Status status);

	public void remove(long id);
	
	public List<Defter> listDefter(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters);

	public Defter findByIdEditMode(long id);

	public List<Defter> listAllByIsletme(Isletme isletme);

	public List<Defter> listAllByKasa(Kasa kasa);

	public List<Defter> listAllByTarih(Date tarih, Kasa kasa);
	
	public void updateAllBakiyeAfter(Defter defter);
	

}