package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class GenericService implements Serializable {

	private static final long serialVersionUID = 5390492535300935394L;

	@ManagedProperty (value="#{menuService}")
	private MenuService menuService;
	
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public String yonlendir(Long id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest servletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();	
		String uri = servletRequest.getRequestURI();
		menuService.getScreenStack().push(uri);
		
		return "";
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
