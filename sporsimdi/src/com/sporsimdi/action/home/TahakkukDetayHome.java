package com.sporsimdi.action.home;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.model.entity.TahakkukDetay;

@ManagedBean(name = "tahakkukDetayHome")
@ViewScoped
public class TahakkukDetayHome extends HomeBean<TahakkukDetay> {

	private static final long serialVersionUID = 5528752636982509999L;

	@Override
	public String save() {
		return super.save();
	}
	
}