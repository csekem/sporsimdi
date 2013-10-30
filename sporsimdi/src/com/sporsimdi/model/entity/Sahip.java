package com.sporsimdi.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SAHIP")
public class Sahip extends Kisi {

	private static final long serialVersionUID = 7383685922929263844L;

	//private Isletme isletme;
	
}
