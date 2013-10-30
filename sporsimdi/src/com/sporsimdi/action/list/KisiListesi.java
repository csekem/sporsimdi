package com.sporsimdi.action.list;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Kisi;

@ManagedBean(name = "kisiListesi")
@ViewScoped
public class KisiListesi extends LazyDataModel<Kisi> {

	private static final long serialVersionUID = 1957601917746764088L;

	@EJB
	private KisiFacade kisiFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	private Kisi kisi;

	@Override
	public Kisi getRowData(String rowKey) {
		return kisiFacade.findById(Integer.parseInt(rowKey));
	}

	@Override
	public Object getRowKey(Kisi kisi) {
		return kisi.getId();
	}

	public List<Kisi> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		
		setRowCount(kisiFacade.getCount().intValue());
		return kisiFacade.listKisi(first, pageSize, sortField, sortOrder, filters);
	}

	public Kisi getKisi() {
		return kisi;
	}

	public void setKisi(Kisi kisi) {
		this.kisi = kisi;
	}

	public String yonlendir(Long id) {
		//TODO super.yonlendir(id);
		if (id == null) {
			return "kisiTanimlama?faces-redirect=true";
		} else {
			return "kisiTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;kisiId=" + id;
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}
