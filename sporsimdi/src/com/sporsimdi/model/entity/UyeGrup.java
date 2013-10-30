package com.sporsimdi.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class UyeGrup extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Uye uye;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Grup grup;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date girisTarihi = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date cikisTarihi;

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	public Uye getUye() {
		return uye;
	}

	public void setUye(Uye uye) {
		this.uye = uye;
	}

	public Date getGirisTarihi() {
		return girisTarihi;
	}

	public void setGirisTarihi(Date girisTarihi) {
		this.girisTarihi = girisTarihi;
	}

	public Date getCikisTarihi() {
		return cikisTarihi;
	}

	public void setCikisTarihi(Date cikisTarihi) {
		this.cikisTarihi = cikisTarihi;
	}

	@Override
	public String toString() {
		return getGrup().toString() + " - " + getUye().toString();
	}
}
