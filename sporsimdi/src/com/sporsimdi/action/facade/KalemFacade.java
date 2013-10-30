package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kalem;

@Local
public interface KalemFacade {

	public void persist(Kalem kalem);

	public void merge(Kalem kalem);

	public void remove(Kalem kalem);

	public void delete(Kalem kalem);

	public Kalem findById(long id);

	public List<Kalem> listAll();

	public List<Kalem> listAllByIsletme(Long isletmeId);

	public Kalem getByAdi(Isletme isletme, String ad);

}