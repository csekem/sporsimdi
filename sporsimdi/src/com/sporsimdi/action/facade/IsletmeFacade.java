package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.Tesis;
import com.sporsimdi.model.type.Status;

public interface IsletmeFacade {

	public void persist(Isletme isletme);
	public void merge(Isletme isletme);
	public void remove(Isletme isletme);
	public void delete(Isletme isletme);

	public Isletme findById(long id);
	
	public Isletme getByIsletmeKisi(Isletme isletme, Kisi kisi) throws Exception;

	public List<Isletme> listAll();
	public List<Isletme> listByKisi(Kisi kisi);
	public List<Isletme> listByTesis(Tesis tesis);
	public List<Isletme> listByStatus(Status status);
	public String getIsletmeTipi(Isletme isletme);

}
