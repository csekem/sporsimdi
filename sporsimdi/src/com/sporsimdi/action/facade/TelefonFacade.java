package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.type.Status;
import com.sporsimdi.model.type.TelefonTipi;

public interface TelefonFacade {

	  public void persist(Telefon telefon);
	  public void merge(Telefon telefon);
	  public void delete(Telefon telefon);
	  public void remove(Telefon telefon);

	  public Telefon findById(long id);
	  public Telefon findByIdEditMode(long id);
	  
	  public Telefon getByModelIdTip(Long modelId, TelefonTipi tip) throws Exception;
	  
	  public List<Telefon> listAll();
	  public List<Telefon> listByStatus(Status status);
	  public List<Telefon> listByModelId(Long modelId);
}
