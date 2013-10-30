package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sporsimdi.action.facade.TarifeFacade;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Tarife;

@ManagedBean(name = "tarifeService")
@ViewScoped
public class TarifeService extends GenericService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	@EJB
	private TarifeFacade tarifeFacade;
	
	private List<SelectItem> tarifeListesiSelect;
	
	private Map<Long, List<SelectItem>> tarifeMap;

	public String yonlendir(Long id) {
		super.yonlendir(id);
		if (id==null) {
			return "/menu/tarifeTanimlama?faces-redirect=true";		
		} else {
			return "/menu/tarifeTanimlama?faces-redirect=true&amp;includeViewParams=true&amp;tarifeId=" + id;			
		}
	}

	public  List<SelectItem> getTarifeListesiSelect() {
		return tarifeListesiSelect;
	}

	public void setTarifeListesiSelect(List<SelectItem> tarifeListesiSelect) {
		this.tarifeListesiSelect = tarifeListesiSelect;
	}

	public Map<Long, List<SelectItem>> getTarifeMap() {
		if (tarifeMap==null) {
			tarifeMap = new HashMap<Long, List<SelectItem>>();
		}
		return tarifeMap;
	}

	public void setTarifeMap(Map<Long, List<SelectItem>> tarifeMap) {
		this.tarifeMap = tarifeMap;
	}

	public List<SelectItem> grubunTarifeleri(Grup grup) {
		if (!getTarifeMap().containsKey(grup.getId())) {

			List<Tarife> list = tarifeFacade.listByGrup(grup.getId());
		
			tarifeListesiSelect = new ArrayList<SelectItem>();
			
			for(Tarife t : list)	{
				SelectItem si = new SelectItem(t, t.toString());
				tarifeListesiSelect.add(si);
			}
			
			getTarifeMap().put(grup.getId(), tarifeListesiSelect);
		}
		return getTarifeMap().get(grup.getId());
	}

}
