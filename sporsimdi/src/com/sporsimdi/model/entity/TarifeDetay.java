package com.sporsimdi.model.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class TarifeDetay extends ExtendedModel {

	private static final long serialVersionUID = 3779390903961365714L;

	private int taksitNo;

	private BigDecimal taksitTutar;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Tarife tarife;

	public int getTaksitNo() {
		return taksitNo;
	}

	public void setTaksitNo(int taksitNo) {
		this.taksitNo = taksitNo;
	}

	public BigDecimal getTaksitTutar() {
		return taksitTutar;
	}

	public void setTaksitTutar(BigDecimal taksitTutar) {
		this.taksitTutar = taksitTutar;
	}

	public Tarife getTarife() {
		return tarife;
	}

	public void setTarife(Tarife tarife) {
		this.tarife = tarife;
	}

	@Override
	public String toString() {
		NumberFormat nm = DecimalFormat.getInstance(new Locale("tr"));		
		return getTaksitNo() + ". taksit: " + nm.format(getTaksitTutar());
	}


}
