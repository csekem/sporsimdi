package com.sporsimdi.model.converter;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.orderlist.OrderList;

import com.sporsimdi.action.facade.GenelFacade;
import com.sporsimdi.model.base.ExtendedModel;

@FacesConverter(value = "primeFacesOrderListConverter")
@SessionScoped
public class PrimeFacesOrderListConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GenelFacade genelFacade;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		ExtendedModel entity = null;
		if (value == null) {
			entity = null;
		} else if (value != null && value.toString().equals("0")) {
			entity = null;
		} else {
			Long id = new Long(value);

			@SuppressWarnings("unchecked")
			List<ExtendedModel> list = ((List<ExtendedModel>) ((OrderList) component).getValue());
			for (ExtendedModel model : list) {
				if (model.getId().longValue() == id.longValue()) {
					entity = model;
					break;
				}
			}
		}

		return entity;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (!value.toString().equals("0") && value != null && !(value instanceof ExtendedModel)) {
			throw new IllegalArgumentException("This converter only handles instances of ExtendedModel");
		}
		if (value == null) {
			return "";
		}
		if (value.toString().equals("0")) {
			return "";
		}
		if (value instanceof String) {
			return (String) value;
		}
		ExtendedModel entity = (ExtendedModel) value;
		return entity.getId() == null ? "" : entity.getId().toString();
	}

}
