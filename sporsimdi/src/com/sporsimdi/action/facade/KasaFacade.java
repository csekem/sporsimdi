package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;

@Local
public interface KasaFacade {

	public void persist(Kasa kasa);
	public void merge(Kasa kasa);
	public void remove(Kasa kasa);
	public void delete(Kasa kasa);

	public Kasa findById(long id);

	public Long getCount();

	public List<Kasa> listAllByOrgTesis(OrgTesis orgTesis);

	public Kasa getAnaKasaByOrgTesis(OrgTesis orgTesis) throws Exception;
	
}