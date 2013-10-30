package com.qrpos.model.json;

import java.io.Serializable;
import java.math.BigDecimal;

public class FisDetayJson implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long urunId;

	private BigDecimal adet;

	public FisDetayJson() {
		super();
	}

	public Long getUrunId() {
		return urunId;
	}

	public void setUrunId(Long urunId) {
		this.urunId = urunId;
	}

	public BigDecimal getAdet() {
		return adet;
	}

	public void setAdet(BigDecimal adet) {
		this.adet = adet;
	}

	

}
