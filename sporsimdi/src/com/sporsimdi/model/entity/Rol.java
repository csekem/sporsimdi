package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Entity
@Table
public class Rol extends ExtendedModel {

	private static final long serialVersionUID = -466099611456309250L;

	private String rol;
	
	private String kullanici;

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getKullanici() {
		return kullanici;
	}

	public void setKullanici(String kullanici) {
		this.kullanici = kullanici;
	}
	
	//private Okul okul;
}
