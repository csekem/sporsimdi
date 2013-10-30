package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.TelefonTipi;

@Table
@Entity
public class Telefon extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@Enumerated(EnumType.STRING)
	private TelefonTipi telefonTipi;

	private String telefon;

	private Long modelId;
	
	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public TelefonTipi getTelefonTipi() {
		return telefonTipi;
	}

	public void setTelefonTipi(TelefonTipi telefonTipi) {
		this.telefonTipi = telefonTipi;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

}
