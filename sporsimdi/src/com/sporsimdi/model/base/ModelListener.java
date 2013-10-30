package com.sporsimdi.model.base;

import java.util.Date;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;

public class ModelListener {

	String userName = "unknown";
	String userId = "unknown";

	@PrePersist
	public void onPreInsert(ExtendedModel modelBase) {
		Date now = new Date();
		userName = userGetir();
		modelBase.setCreatedByUser(userName);
		modelBase.setCreateDate(now);
		modelBase.setCreatedByUserId(userId);
		modelBase.setUpdatedByUser(userName);
		modelBase.setUpdateDate(now);
		modelBase.setUpdatedByUserId(userId);

	}

	@PreUpdate
	public void onPreUpdate(ExtendedModel modelBase) {
		userName = userGetir();
		Date now = new Date();
		modelBase.setUpdatedByUser(userName);
		modelBase.setUpdateDate(now);
		modelBase.setUpdatedByUserId(userId);
	}

	private String userGetir() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext != null) {
			ExternalContext ec = facesContext.getExternalContext();
			if (ec != null) {
				HttpServletRequest req = (HttpServletRequest) ec.getRequest();
				if (req.getUserPrincipal() != null)
					return req.getUserPrincipal().getName();
			}

		}
		return "unknown";
	}

}