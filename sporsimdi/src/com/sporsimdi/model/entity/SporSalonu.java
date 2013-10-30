package com.sporsimdi.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.sporsimdi.model.entity.facade.IsletmeEntityFacade;

@Entity
@DiscriminatorValue("SPOR_SALONU")
public class SporSalonu extends Isletme implements IsletmeEntityFacade {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMenuValue() {
		return "SPOR SALONU";
	}

	@Override
	public String getMenuUrl() {
		return "/menu/sporsalonu/sporsalonu.jsf";
	}

	@Override
	public String getMenuBgColor() {
		return "background-color:orange;";
	}
}
