package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Kullanici;
import com.sporsimdi.model.type.Status;

public interface KullaniciFacade {

	public Kullanici findById(long id);
	public Kullanici findByKullanici(String kullanici);
	public List<Kullanici> getAll();
	public void persist(Kullanici kullanici);
	public List<Kullanici> getByStatus(Status status);
	public void remove(long id);
	public Long getCount();
	public List<Kullanici> listKullanici(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, String> filters);

}
