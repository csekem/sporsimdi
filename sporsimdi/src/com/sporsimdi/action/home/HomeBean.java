package com.sporsimdi.action.home;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.sporsimdi.action.facade.EntityManagerDao;
import com.sporsimdi.action.service.MenuService;
import com.sporsimdi.model.base.ExtendedModel;

public class HomeBean<T extends ExtendedModel> implements Serializable {

	private static final long serialVersionUID = 1957601917746764088L;

	@EJB
	private EntityManagerDao entityManagerDao;

	private Long id;
	
	@ManagedProperty(value="#{menuService}")
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	protected T instance;

	public T getInstance() {
		if (instance == null) {
			if (getId() != null) {
				instance = loadInstance();
			} else {
				instance = createInstance();
			}
		}
		return instance;
	}
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public T loadInstance() {
		return entityManagerDao.find(getClassType(), getId());
	}

	public T createInstance() {
		try {
			return getClassType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public boolean isManaged() {
		return getInstance().getId() != null;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		if (isManaged()) {
			setInstance((T) entityManagerDao.updateObject(getInstance()));
		} else {
			entityManagerDao.createObject(getInstance());
		}
		return menuService.goToLastScreen();
		//return "saved";
	}
	
	@SuppressWarnings("unchecked")
	public String saveWithoutNavigation() throws Exception {
		try {
			if (isManaged()) {
				setInstance((T) entityManagerDao.updateObject(getInstance()));
			} else {
				entityManagerDao.createObject(getInstance());
			}			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata", this.getClass().toString()));  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata", e.getLocalizedMessage()));  
		}
		return "saved";
	}
	
	public String delete() throws Exception {	
		entityManagerDao.deleteObject(getInstance());
		createInstance();
		return "deleted";
	}
	
	public String cancel() {
		return "cancelled";
	}

	public EntityManagerDao getEntityManagerDao() {
		return entityManagerDao;
	}

	public void setEntityManagerDao(EntityManagerDao entityManagerDao) {
		this.entityManagerDao = entityManagerDao;
	}
	public String delete(T t){
		entityManagerDao.deleteObject(t);
		return "deleted";
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}
	
	public void clearInstance() {
	      setInstance(null);
	      setId(null);
	}
	
	public void sayfaLoad() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> pMap = fc.getExternalContext().getRequestParameterMap();
		Iterator<Entry<String, String>> pIter = pMap.entrySet().iterator();
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		while (pIter.hasNext()) {
			Entry<String, String> entry = pIter.next();
			parameterMap.put(entry.getKey(), entry.getValue());
		}
		
		HttpServletRequest servletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();	
		String uri = servletRequest.getRequestURI();
		menuService.getParameterStack().put(uri, parameterMap);
	}

}

