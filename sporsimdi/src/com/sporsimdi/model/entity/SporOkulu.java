package com.sporsimdi.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.sporsimdi.model.entity.facade.IsletmeEntityFacade;

@Entity
@DiscriminatorValue("SPOR_OKULU")
public class SporOkulu extends Isletme implements IsletmeEntityFacade {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMenuValue() {
		return "SPOR OKULU";
	}

	@Override
	public String getMenuUrl() {
		return "/menu/sporokulu/sporokulu.jsf";
	}

	@Override
	public String getMenuBgColor() {
		return "background-color:yellow;";
	}


}
