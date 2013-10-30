package com.qrpos.model.json;

import java.io.Serializable;

public class ResponseJson implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long status;

	private Long errCode;

	private String errDesc;

	public ResponseJson() {
		super();
	}

	public ResponseJson(Long status, Long errCode, String errDesc) {
		super();
		this.status = status;
		this.errCode = errCode;
		this.errDesc = errDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getErrCode() {
		return errCode;
	}

	public void setErrCode(Long errCode) {
		this.errCode = errCode;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
