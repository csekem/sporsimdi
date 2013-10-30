package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.OrgTesis;

@Local
public interface OrgTesisFacade {

	public void persist(OrgTesis orgTesis);
	public void merge(OrgTesis orgTesis);
	public void remove(OrgTesis orgTesis);
	public void delete(OrgTesis orgTesis);

	public OrgTesis findById(long id);

	public List<OrgTesis> listAll();
	public List<OrgTesis> listByOrganizasyon(Long organizasyonId);
	
	public Long getCount();
	public List<OrgTesis> getOrgTesis(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters);

}