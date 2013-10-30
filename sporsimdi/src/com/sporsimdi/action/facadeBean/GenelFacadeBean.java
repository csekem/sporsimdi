package com.sporsimdi.action.facadeBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sporsimdi.action.facade.GenelFacade;
import com.sporsimdi.model.base.ExtendedModel;


@Stateless
public class GenelFacadeBean implements GenelFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public GenelFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExtendedModel findById(@SuppressWarnings("rawtypes") Class clazz,
			long id) {
		ExtendedModel extendedModel = (ExtendedModel) entityManager.find(clazz, id);
		return extendedModel;
	}


}
