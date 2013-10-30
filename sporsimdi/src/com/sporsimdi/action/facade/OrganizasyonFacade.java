package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Organizasyon;

@Local
public interface OrganizasyonFacade {

	public void persist(Organizasyon organizasyon);
	public void merge(Organizasyon organizasyon);
	public void remove(Organizasyon organizasyon);
	public void delete(Organizasyon organizasyon);

	public Organizasyon findById(long id);

	public List<Organizasyon> listAll();
	public List<Organizasyon> listByIsletme(Isletme isletme);
	
	public Long getCount();
	public List<Organizasyon> getOrganizasyon(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters);

}