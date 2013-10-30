package com.sporsimdi.action.facade;

import javax.ejb.Local;

import com.sporsimdi.model.base.ExtendedModel;

@Local
public interface GenelFacade {

	public ExtendedModel findById(@SuppressWarnings("rawtypes") Class clazz, long id);

}