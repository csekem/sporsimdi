package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Il;
import com.sporsimdi.model.entity.Ilce;
import com.sporsimdi.model.type.Status;

@Local
public interface IlceFacade {

	public Ilce findById(long id);

	public List<Ilce> getAll();

	public List<Ilce> getAllByIl(Il il);

	public void persist(Ilce ilce);

	public List<Ilce> getByStatus(Status status);

	public void remove(long id);

	public Ilce update(long id);

}