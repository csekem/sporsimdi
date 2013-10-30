package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Grup;

@Local
public interface GrupFacade {

	public void persist(Grup grup);
	public void merge(Grup grup);
	public void remove(Grup grup);
	public void delete(Grup grup);

	public Grup findById(long id);
	
	public List<Grup> listAll();
	public List<Grup> listByOrgTesis(Long orgTesisId);

}