package com.qrpos.model.json;

import java.io.Serializable;
import java.math.BigDecimal;

public class FisKaydetSonucJson implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private int status;

	private Long hataKodu;

	private String hataDetay;

	private String uyeIsim;

	private BigDecimal oncekiBakiye;

	private BigDecimal kalanBakiye;

	public FisKaydetSonucJson() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getHataKodu() {
		return hataKodu;
	}

	public void setHataKodu(Long hataKodu) {
		this.hataKodu = hataKodu;
	}

	public String getHataDetay() {
		return hataDetay;
	}

	public void setHataDetay(String hataDetay) {
		this.hataDetay = hataDetay;
	}

	public BigDecimal getOncekiBakiye() {
		return oncekiBakiye;
	}

	public void setOncekiBakiye(BigDecimal oncekiBakiye) {
		this.oncekiBakiye = oncekiBakiye;
	}

	public BigDecimal getKalanBakiye() {
		return kalanBakiye;
	}

	public void setKalanBakiye(BigDecimal kalanBakiye) {
		this.kalanBakiye = kalanBakiye;
	}

	public String getUyeIsim() {
		return uyeIsim;
	}

	public void setUyeIsim(String uyeIsim) {
		this.uyeIsim = uyeIsim;
	}

}
