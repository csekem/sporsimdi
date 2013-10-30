package com.sporsimdi.action.list;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.KullaniciFacade;
import com.sporsimdi.action.service.MenuService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Kullanici;

@ManagedBean(name = "kullaniciListesi")
@ViewScoped
public class KullaniciListesi extends LazyDataModel<Kullanici> {

	private static final long serialVersionUID = 1957601917746764088L;

	@EJB
	private KullaniciFacade kullaniciFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty (value="#{menuService}")
	private MenuService menuService;

	private Kullanici kullanici;

	@Override
	public Kullanici getRowData(String rowKey) {
		return kullaniciFacade.findById(Integer.parseInt(rowKey));
	}

	@Override
	public Object getRowKey(Kullanici kullanici) {
		return kullanici.getId();
	}

	public List<Kullanici> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		
		setRowCount(kullaniciFacade.getCount().intValue());
		return kullaniciFacade.listKullanici(first, pageSize, sortField, sortOrder, filters);
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public String yonlendir(Long id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest servletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();	
		String uri = servletRequest.getRequestURI();
		menuService.getScreenStack().push(uri);
		
		//TODO super.yonlendir(id);
		if (id == null) {
			return "kullaniciTanimlama?faces-redirect=true";
		} else {
			return "kullaniciTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;kullaniciId="	+ id;
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
