package com.qrpos.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.Uye;

@Table
@Entity
public class Kart extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.EAGER)
	private Uye uye;

	private BigDecimal bakiye;

	private BigDecimal depozito;

	private BigDecimal bonus;

	private BigDecimal kredi;

	@OneToOne(fetch = FetchType.LAZY)
	private Organizasyon organizasyon;

	public Uye getUye() {
		return uye;
	}

	public void setUye(Uye uye) {
		this.uye = uye;
	}

	public BigDecimal getBakiye() {
		return bakiye;
	}

	public void setBakiye(BigDecimal bakiye) {
		this.bakiye = bakiye;
	}

	public BigDecimal getDepozito() {
		return depozito;
	}

	public void setDepozito(BigDecimal depozito) {
		this.depozito = depozito;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getKredi() {
		return kredi;
	}

	public void setKredi(BigDecimal kredi) {
		this.kredi = kredi;
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

}
