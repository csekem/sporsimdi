package com.sporsimdi.action.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.service.GenericService;

@ManagedBean(name = "utilNumberService")
@SessionScoped
public class UtilNumberService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;
	
	private List<SelectItem> sayiListesi;
		
	public List<SelectItem> getSayiListesi() {
		if (sayiListesi == null || sayiListesi.isEmpty()) {
			sayiListesi = new ArrayList<SelectItem>();
			for(int i = 1; i<60; i++)	{
				SelectItem si = new SelectItem(i, Integer.toString(i));
				sayiListesi.add(si);
			}
		}
		return sayiListesi;
	}

	public void setSayiListesi(List<SelectItem> sayiListesi) {
		this.sayiListesi = sayiListesi;
	}

}
