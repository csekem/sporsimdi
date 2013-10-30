package com.qrpos.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Organizasyon;

@Table
@Entity
public class Fis extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.LAZY)
	private KartDurum kartDurum;

	@ManyToOne(fetch = FetchType.LAZY)
	private Terminal terminal;

	@OneToMany(mappedBy = "fis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FisDetay> fisDetayListesi = new ArrayList<FisDetay>();

	private BigDecimal toplamTutar;

	private BigDecimal kdvTutar;

	private BigDecimal indirimTutar;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organizasyon organizasyon;

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public BigDecimal getToplamTutar() {
		return toplamTutar;
	}

	public void setToplamTutar(BigDecimal toplamTutar) {
		this.toplamTutar = toplamTutar;
	}

	public BigDecimal getKdvTutar() {
		return kdvTutar;
	}

	public void setKdvTutar(BigDecimal kdvTutar) {
		this.kdvTutar = kdvTutar;
	}

	public BigDecimal getIndirimTutar() {
		return indirimTutar;
	}

	public void setIndirimTutar(BigDecimal indirimTutar) {
		this.indirimTutar = indirimTutar;
	}

	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

	public KartDurum getKartDurum() {
		return kartDurum;
	}

	public void setKartDurum(KartDurum kartDurum) {
		this.kartDurum = kartDurum;
	}

	public List<FisDetay> getFisDetayListesi() {
		return fisDetayListesi;
	}

	public void setFisDetayListesi(List<FisDetay> fisDetayListesi) {
		this.fisDetayListesi = fisDetayListesi;
	}

}
