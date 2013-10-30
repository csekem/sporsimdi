package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Il;
import com.sporsimdi.model.type.Status;

@Local
public interface IlFacade {

	public Il findById(long id);
	public List<Il> getAll();
	public void persist(Il il);
	public List<Il> getByStatus(Status status);
	public void remove(long id);
	public Il update(long id);
	
}