package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.WebIletisim;
import com.sporsimdi.model.type.Status;

public interface WebIletisimFacade {

	  public WebIletisim findById(long id);
	  public List<WebIletisim> getAll();
	  public void persist(WebIletisim WebIletisim);
	  public List<WebIletisim> getByStatus(Status status);
	  public void remove(long id);
	  public List<WebIletisim> listByModelId(Long modelId);
}
