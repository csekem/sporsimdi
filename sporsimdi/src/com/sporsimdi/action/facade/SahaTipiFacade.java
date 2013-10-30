package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.entity.SahaTipi;
import com.sporsimdi.model.type.Status;

@Local
public interface SahaTipiFacade {

	public SahaTipi findById(long id);

	public List<SahaTipi> listAll();

	public void persist(SahaTipi sahaTipi);

	public List<SahaTipi> listByStatus(Status status);

	public void remove(long id);
	
	public List<SahaTipi> listBySaha(Saha saha);

}