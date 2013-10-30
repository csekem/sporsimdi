package com.sporsimdi.model.converter;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sporsimdi.action.facade.GenelFacade;
import com.sporsimdi.model.base.ExtendedModel;

@FacesConverter("entityConverter")
@SessionScoped
public class EntityConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	GenelFacade genelFacade;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) throws ConverterException {
		ExtendedModel entity;
		if (value == null) {
			entity = null;
		} else if (value != null && value.toString().equals("0")) {
			entity = null;
		} else {
			Integer id = new Integer(value);

			try {

				InitialContext ic = new InitialContext();
				genelFacade = (GenelFacade) ic.lookup("java:global/sporsimdi/GenelFacadeBean");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			entity = (ExtendedModel) genelFacade.findById(getClazz(facesContext, component), id);
			if (entity == null) {
				System.out.println("There is no entity with id:  " + id);

			}
		}
		return entity;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) throws ConverterException {
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

	// Gets the class corresponding to the context in jsf page
	@SuppressWarnings({ "rawtypes" })
	private Class getClazz(FacesContext facesContext, UIComponent component) {
		return component.getValueExpression("value").getType(facesContext.getELContext());
	}

}
