package com.sporsimdi.action.list;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.sporsimdi.action.facade.EntityManagerDao;
import com.sporsimdi.action.facade.GenelFacade;
import com.sporsimdi.action.service.MenuService;
import com.sporsimdi.model.base.ExtendedModel;

public class ListBean<T extends ExtendedModel> implements Serializable, ListBeanInterface {

	private static final long serialVersionUID = 1957601917746764088L;

	@EJB
	private EntityManagerDao entityManagerDao;

	private Long id;

	@ManagedProperty(value = "#{menuService}")
	protected MenuService menuService;

	protected List<T> list;

	private Map<String, String> pMap;

	private ExtendedModel modelSuper;

	private String strSuperId;

	private Long superId;

	private Boolean save;

	private Boolean newerItem;

	private Boolean deletion;

	@EJB
	private GenelFacade genelFacade;

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

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
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
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public boolean isManaged() {
		return getInstance().getId() != null;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		fillSaving();
		if (isManaged()) {
			setInstance((T) entityManagerDao.updateObject(getInstance()));
		} else {
			entityManagerDao.createObject(getInstance());
		}

		setNewerItem(false);
		setDeletion(false);
		clearInstance();

		fillList();

		return "saved";
	}

	@SuppressWarnings("unchecked")
	public String saveWithoutNavigation() {
		if (isManaged()) {
			setInstance((T) entityManagerDao.updateObject(getInstance()));
		} else {
			entityManagerDao.createObject(getInstance());
		}
		return "saved";
	}

	public String delete() {
		entityManagerDao.deleteObject(getInstance());
		setNewerItem(false);
		setDeletion(false);
		clearInstance();

		fillList();

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

	public String delete(T t) {
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

	public void clear() {
		clearInstance();
		setNewerItem(false);
		setDeletion(false);
	}

	public void sayfaLoad() {
		FacesContext fc = FacesContext.getCurrentInstance();
		pMap = fc.getExternalContext().getRequestParameterMap();
		Iterator<Entry<String, String>> pIter = pMap.entrySet().iterator();

		Map<String, String> parameterMap = new HashMap<String, String>();
		while (pIter.hasNext()) {
			Entry<String, String> entry = pIter.next();
			if (entry.getKey().startsWith("sporsimdi.com.")) {
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		if (parameterMap.size() > 0) {
			HttpServletRequest servletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();
			String uri = servletRequest.getRequestURI();
			menuService.getParameterStack().put(uri, parameterMap);
		}

		fillSuperId();

		if (superId != null) {
			fillList();
		}

	}

	public void initialize(Long modelId, boolean save) {
		setSuperId(modelId);
		setSave(save);

		fillSuperId();

		if (superId != null) {
			fillList();
		}
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getStrSuperId() {
		return strSuperId;
	}

	public void setStrSuperId(String strSuperId) {
		this.strSuperId = strSuperId;
	}

	public GenelFacade getGenelFacade() {
		return genelFacade;
	}

	public void setGenelFacade(GenelFacade genelFacade) {
		this.genelFacade = genelFacade;
	}

	@Override
	public void fillSuperId() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fillList() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fillSaving() {
		// TODO Auto-generated method stub
	}

	public Long getSuperId() {
		if (superId == null) {
			superId = Long.valueOf(pMap.get(getStrSuperId()));
		}
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}

	public Boolean getNewerItem() {
		if (newerItem == null) {
			newerItem = false;
		}
		return newerItem;
	}

	public void setNewerItem(Boolean newerItem) {
		this.newerItem = newerItem;
	}

	public void enableNewerItem() {
		setNewerItem(true);
	}

	public void disableNewerItem() {
		setNewerItem(false);
		setDeletion(false);
		clearInstance();
		clearSubFields();
	}

	public void updateInstance(T t) {
		setInstance(t);
		setNewerItem(true);
	}

	public void deleteInstance(T t) {
		setDeletion(true);
		setInstance(t);
		setNewerItem(true);
	}

	public Boolean getDeletion() {
		if (deletion == null) {
			deletion = false;
		}
		return deletion;
	}

	public void setDeletion(Boolean deletion) {
		this.deletion = deletion;
	}

	public ExtendedModel getModelSuper() {
		return modelSuper;
	}

	public void setModelSuper(ExtendedModel modelSuper) {
		this.modelSuper = modelSuper;
	}

	private void clearSubFields() {
		Field[] fields = getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().startsWith("sub")) {
				fields[i] = null;
			}
		}
	}

	public Boolean getSave() {
		return save;
	}

	public void setSave(Boolean save) {
		this.save = save;
	}

}
