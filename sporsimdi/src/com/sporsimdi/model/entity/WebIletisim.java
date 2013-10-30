package com.sporsimdi.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.type.WebIletisimTipi;

@Table
@Entity
public class WebIletisim extends ExtendedModel {

	private static final long serialVersionUID = 8986161490957095179L;

	@Enumerated(EnumType.STRING)
	private WebIletisimTipi webIletisimTipi;
	
	@Email
	private String email;

	private String webIletisim;

	private Long modelId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public WebIletisimTipi getWebIletisimTipi() {
		return webIletisimTipi;
	}

	public void setWebIletisimTipi(WebIletisimTipi webIletisimTipi) {
		this.webIletisimTipi = webIletisimTipi;
	}

	public String getWebIletisim() {
		return webIletisim;
	}

	public void setWebIletisim(String webIletisim) {
		this.webIletisim = webIletisim;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}


}
