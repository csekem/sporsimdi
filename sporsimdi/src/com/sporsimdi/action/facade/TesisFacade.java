package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Tesis;
import com.sporsimdi.model.type.Status;

@Local
public interface TesisFacade {

	public Tesis findById(long id);

	public List<Tesis> getAll();

	public void persist(Tesis tesis);

	public List<Tesis> getByStatus(Status status);

	public void remove(long id);
	
	public List<Tesis> listAllByIsletme(Long isletmeId);
}