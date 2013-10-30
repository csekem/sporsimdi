package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.AdresTipi;

@Table
@Entity
public class Adres extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@Enumerated(EnumType.STRING)
	private AdresTipi adresTipi;
	
	private String adres;

	private String mahalle;

	private String postaKodu;

	@ManyToOne(fetch = FetchType.LAZY)
	private Ilce ilce;

	@ManyToOne(fetch = FetchType.LAZY)
	private Il il;

	@ManyToOne(fetch = FetchType.LAZY)
	private Semt semt;

	private String googleMapId;
	
	private Long modelId;


	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public AdresTipi getAdresTipi() {
		return adresTipi;
	}

	public void setAdresTipi(AdresTipi adresTipi) {
		this.adresTipi = adresTipi;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}

	public String getPostaKodu() {
		return postaKodu;
	}

	public void setPostaKodu(String postaKodu) {
		this.postaKodu = postaKodu;
	}

	public String getGoogleMapId() {
		return googleMapId;
	}

	public void setGoogleMapId(String googleMapId) {
		this.googleMapId = googleMapId;
	}


	public Semt getSemt() {
		return semt;
	}

	public void setSemt(Semt semt) {
		this.semt = semt;
	}

	public Ilce getIlce() {
		return ilce;
	}

	public void setIlce(Ilce ilce) {
		this.ilce = ilce;
	}

	public Il getIl() {
		return il;
	}

	public void setIl(Il il) {
		this.il = il;
	}
	
	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}


}
