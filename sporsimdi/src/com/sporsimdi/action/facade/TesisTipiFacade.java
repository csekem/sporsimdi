package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.TesisTipi;
import com.sporsimdi.model.type.Status;

@Local
public interface TesisTipiFacade {

	public TesisTipi findById(long id);

	public List<TesisTipi> listAll();

	public void persist(TesisTipi tesisTipi);

	public List<TesisTipi> listByStatus(Status status);

	public void remove(long id);
	
}