package com.qrpos.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.Organizasyon;

@Table
@Entity
public class Terminal extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Kasa kasa;

	private String terminalNo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organizasyon organizasyon;

	public Kasa getKasa() {
		return kasa;
	}

	public void setKasa(Kasa kasa) {
		this.kasa = kasa;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

}
