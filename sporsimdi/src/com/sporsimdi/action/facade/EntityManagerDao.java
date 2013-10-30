package com.sporsimdi.action.facade;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface EntityManagerDao extends Serializable {
	public Object updateObject(Object object);

	public void createObject(Object object);

	public void refresh(Object object);

	public <T> T find(Class<T> clazz, Long id);

	public void deleteObject(Object object);

	public void flush();
}
