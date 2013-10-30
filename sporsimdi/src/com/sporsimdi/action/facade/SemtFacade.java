package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Ilce;
import com.sporsimdi.model.entity.Semt;
import com.sporsimdi.model.type.Status;

@Local
public interface SemtFacade {

	public Semt findById(long id);

	public List<Semt> listAll();

	public List<Semt> listByIlce(Ilce ilce);

	public void persist(Semt semt);

	public List<Semt> getByStatus(Status status);

	public void remove(long id);

	public Semt update(long id);

}