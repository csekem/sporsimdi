package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Tarife;
import com.sporsimdi.model.entity.TarifeDetay;

@Local
public interface TarifeFacade {

	public void persist(Tarife tarife);
	public void merge(Tarife tarife);
	public void remove(Tarife tarife);
	public void delete(Tarife tarife);

	public Tarife findById(long id);
	
	public List<Tarife> listAll();
	public List<Tarife> listByGrup(Long grupId);
	
	public List<TarifeDetay> listTarifeDetayByTarife(Tarife tarife);

}