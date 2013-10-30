package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.Adres;
import com.sporsimdi.model.type.Status;

public interface AdresFacade {

	  public Adres findById(long id);
	  public Adres findByIdEditMode(long id);
	  public List<Adres> listAll();
	  public void persist(Adres adres);
	  public List<Adres> getByStatus(Status status);
	  public void remove(long id);
	  public List<Adres> listByModelId(Long modelId);
}
