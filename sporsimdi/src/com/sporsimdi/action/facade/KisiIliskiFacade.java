package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.type.Status;
import com.sporsimdi.model.type.YakinlikDerecesi;

public interface KisiIliskiFacade {

	  public void persist(KisiIliski kisiIliski);
	  public void merge(KisiIliski kisiIliski);
	  public void delete(KisiIliski kisiIliski);
	  public void remove(KisiIliski kisiIliski);

	  public KisiIliski findById(long id);
	  
	  public List<KisiIliski> listAll();
	  public List<KisiIliski> listByKisi(Kisi kisi);
	  
	  public KisiIliski getByKisiYakinlik(Kisi kisi, YakinlikDerecesi yakinlik) throws Exception;
	  public KisiIliski getByStatus(Status status);

	  public Long getCount();
	  public List<KisiIliski> listKisiIliski(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, String> filters);
}
