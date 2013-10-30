package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.OrganizasyonTipi;

@Table
@Entity
public class MenuOrganizasyonTipi extends ExtendedModel {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Menu menu;
	
	@Enumerated(EnumType.STRING)
	private OrganizasyonTipi organizasyonTipi;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public OrganizasyonTipi getOrganizasyonTipi() {
		return organizasyonTipi;
	}

	public void setOrganizasyonTipi(OrganizasyonTipi organizasyonTipi) {
		this.organizasyonTipi = organizasyonTipi;
	}


}
