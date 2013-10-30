package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Meslek;

@Local
public interface MeslekFacade {

	public void persist(Meslek meslek);

	public void merge(Meslek meslek);

	public void remove(Meslek meslek);

	public void delete(Meslek meslek);

	public Meslek findById(long id);

	public List<Meslek> listAll();

	public List<Meslek> listAllByIsletme(Long isletmeId);

}