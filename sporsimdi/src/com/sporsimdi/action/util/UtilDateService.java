package com.sporsimdi.action.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.service.GenericService;

@ManagedBean(name = "utilDateService")
@SessionScoped
public class UtilDateService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;
	
	private List<SelectItem> gunListesi;
		
	public List<SelectItem> getGunListesi() {
		if (gunListesi == null || gunListesi.isEmpty()) {
			gunListesi = new ArrayList<SelectItem>();
			for(int i = 1; i<31; i++)	{
				SelectItem si = new SelectItem(i, Integer.toString(i));
				gunListesi.add(si);
			}
		}
		return gunListesi;
	}

	public void setGunListesi(List<SelectItem> gunListesi) {
		this.gunListesi = gunListesi;
	}

}
