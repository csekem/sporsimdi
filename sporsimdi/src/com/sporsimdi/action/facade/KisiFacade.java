package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.type.Status;

public interface KisiFacade {

	  public Kisi findById(long id);
	  public List<Kisi> getAll();
	  public List<Kisi> listByIsletme(Isletme isletme);
	  public void persist(Kisi kisi);
	  public void merge(Kisi kisi);
	  public List<Kisi> getByStatus(Status status);
	  public void remove(long id);
	  public Kisi findByIdEager(long id);
	  public Long getCount();
	  public List<Kisi> listKisi(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, String> filters);
	  public String getKisiTipi(Kisi kisi);
}
