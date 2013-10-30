package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.YasGrubu;
import com.sporsimdi.model.type.Status;

@Local
public interface YasGrubuFacade {

	public YasGrubu findById(long id);

	public List<YasGrubu> listAll();

	public void persist(YasGrubu yasGrubu);

	public List<YasGrubu> getByStatus(Status status);

	public void remove(long id);
	
	public List<YasGrubu> listAllByIsletme(Long isletmeId);

}