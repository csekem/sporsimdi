package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Saha;
import com.sporsimdi.model.type.Status;

@Local
public interface SahaFacade {

	public Saha findById(long id);

	public List<Saha> listAll();

	public void persist(Saha saha);

	public List<Saha> getByStatus(Status status);

	public void remove(long id);
	
	public List<Saha> listAllByTesis(Long tesisId);
	
	public Saha findWideById(long id);

}