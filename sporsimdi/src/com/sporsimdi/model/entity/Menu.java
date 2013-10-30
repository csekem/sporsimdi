package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class Menu extends ExtendedModel {

	private static final long serialVersionUID = 1L;
	
	private String menuAdi;
	
	private String menuResmi;

	private String sayfa;
	
	public String getMenuAdi() {
		return menuAdi;
	}

	public void setMenuAdi(String menuAdi) {
		this.menuAdi = menuAdi;
	}

	public String getMenuResmi() {
		return menuResmi;
	}

	public void setMenuResmi(String menuResmi) {
		this.menuResmi = menuResmi;
	}

	public String getSayfa() {
		return sayfa;
	}

	public void setSayfa(String sayfa) {
		this.sayfa = sayfa;
	}


}
