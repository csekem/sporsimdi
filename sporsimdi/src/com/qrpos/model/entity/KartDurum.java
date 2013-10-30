package com.qrpos.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qrpos.model.type.KartStatus;
import com.sporsimdi.model.base.ExtendedModel;

@Table
@Entity
public class KartDurum extends ExtendedModel implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Kart kart;

	@ManyToOne(fetch = FetchType.EAGER)
	private QRCard qrcard;

	@Enumerated(EnumType.STRING)
	private KartStatus kartStatus;

	public Kart getKart() {
		return kart;
	}

	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public QRCard getQrcard() {
		return qrcard;
	}

	public void setQrcard(QRCard qrcard) {
		this.qrcard = qrcard;
	}

	public KartStatus getKartStatus() {
		return kartStatus;
	}

	public void setKartStatus(KartStatus kartStatus) {
		this.kartStatus = kartStatus;
	}

}
