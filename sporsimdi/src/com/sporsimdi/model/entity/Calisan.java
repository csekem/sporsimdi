package com.sporsimdi.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CALISAN")
public class Calisan extends Kisi {

	private static final long serialVersionUID = 1L;

	//private Isletme isletme;
	
}
