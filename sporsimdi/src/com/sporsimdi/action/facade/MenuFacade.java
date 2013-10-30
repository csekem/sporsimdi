package com.sporsimdi.action.facade;

import java.util.List;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Menu;
import com.sporsimdi.model.type.OrganizasyonTipi;
import com.sporsimdi.model.type.Status;

public interface MenuFacade {

	  public Menu findById(long id);
	  public List<Menu> listAll();
	  public void persist(Menu menu);
	  public List<Menu> getByStatus(Status status);
	  public void remove(long id);
	  public List<Menu> listByIsletme(Isletme isletme);
	  public List<Menu> listByOrganizasyonTipi(OrganizasyonTipi organizasyonTipi);
}
