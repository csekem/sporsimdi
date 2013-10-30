package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.Status;

public interface GenericFacade {

	  public ExtendedModel findById(long id);
	  public List<ExtendedModel> getAll();
	  public void persist(ExtendedModel extendedModel);
	  public List<ExtendedModel> getByStatus(Status status);
	  public void remove(long id);
}
