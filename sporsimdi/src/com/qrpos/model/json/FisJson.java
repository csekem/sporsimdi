package com.qrpos.model.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FisJson implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long fisId;

	private String qrCardNo;

	private Long terminalId;

	private List<FisDetayJson> fisDetayListesi = new ArrayList<FisDetayJson>();

	public List<FisDetayJson> getFisDetayListesi() {
		return fisDetayListesi;
	}

	public void setFisDetayListesi(List<FisDetayJson> fisDetayListesi) {
		this.fisDetayListesi = fisDetayListesi;
	}

	public Long getFisId() {
		return fisId;
	}

	public void setFisId(Long fisId) {
		this.fisId = fisId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public FisJson() {
		super();
	}

	public String getQrCardNo() {
		return qrCardNo;
	}

	public void setQrCardNo(String kartId) {
		this.qrCardNo = kartId;
	}

}
