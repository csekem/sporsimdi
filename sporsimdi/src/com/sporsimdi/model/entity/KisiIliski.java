package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.YakinlikDerecesi;

@Table
@Entity
public class KisiIliski extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Kisi kisi;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Kisi iliskiliKisi;
	
	@Enumerated(EnumType.STRING)
	private YakinlikDerecesi yakinlikDerecesi;
	

	public Kisi getKisi() {
		return kisi;
	}

	public void setKisi(Kisi kisi) {
		this.kisi = kisi;
	}

	public YakinlikDerecesi getYakinlikDerecesi() {
		return yakinlikDerecesi;
	}

	public void setYakinlikDerecesi(YakinlikDerecesi yakinlikDerecesi) {
		this.yakinlikDerecesi = yakinlikDerecesi;
	}

	public Kisi getIliskiliKisi() {
		return iliskiliKisi;
	}

	public void setIliskiliKisi(Kisi iliskiliKisi) {
		this.iliskiliKisi = iliskiliKisi;
	}


}
