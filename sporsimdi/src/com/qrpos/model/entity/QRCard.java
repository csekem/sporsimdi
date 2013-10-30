package com.qrpos.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Organizasyon;

@Table
@Entity
public class QRCard extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	private String no;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organizasyon organizasyon;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

}
