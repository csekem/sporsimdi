package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.Rol;
import com.sporsimdi.model.type.Status;

public interface RolFacade {

	public Rol findById(long id);
	public Rol findByKullanici(String kullanici);
	public void persist(Rol rol);
	public void merge(Rol rol);
	public List<Rol> getByStatus(Status status);
	public void remove(long id);

}
