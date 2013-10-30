package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.ejb.EJB;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.sporsimdi.action.facade.MenuFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Menu;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.SporOkulu;
import com.sporsimdi.model.entity.SporSalonu;
import com.sporsimdi.model.type.OrganizasyonTipi;

@ManagedBean (name="menuService")
@SessionScoped
public class MenuService implements Serializable {

	private static final long serialVersionUID = 1L;

	private MenuModel menuModel;
	
	private Map<OrganizasyonTipi, MenuModel> organizasyonMenuMap;

	private MenuModel organizasyonMenuModel;
	
	private String subMenuType;
	
	
	private MenuModel sporOkuluMenuModel;
	
	private MenuModel sporSalonuMenuModel;

	private List<Isletme> sporOkuluList;
	
	private List<Isletme> sporSalonuList;
	
	private String selectedMainMenu = ""; 

	private boolean manySporOkulu;
	
	private boolean oneSporOkulu;
	
	private boolean sporOkuluVar;
	
	private boolean manySporSalonu;
	
	private boolean oneSporSalonu;
	
	private boolean sporSalonuVar;

	private boolean sporOkuluSelected;
	
	private boolean sporSalonuSelected;
	

	@ManagedProperty(value="#{sessionObject}")
	private SessionObject sessionObject;
	
	@EJB
	private MenuFacade menuFacade;	
	
	@EJB
	private OrganizasyonFacade organizasyonFacade;	
	
	private Stack<String> screenStack;
	private Map<String, Map<String, String>> parameterStack; 

	public Stack<String> getScreenStack() {
		if (screenStack==null) {
			screenStack = new Stack<String>();
		}
		return screenStack;
	}

	public void setScreenStack(Stack<String> screenStack) {
		this.screenStack = screenStack;
	}

	public Map<String, Map<String, String>> getParameterStack() {
		if (parameterStack==null) {
			parameterStack = new HashMap<String, Map<String, String>>();
		}
		return parameterStack;
	}

	public void setParameterStack(Map<String, Map<String, String>> parameterStack) {
		this.parameterStack = parameterStack;
	}

	public String goToLastScreen() {
		String latestScreen = getScreenStack().pop();
		Map<String, String> paramMap = getParameterStack().get(latestScreen);
		if (paramMap==null || paramMap.size()==0) {
			return latestScreen + "?faces-redirect=true";
			//return latestScreen.substring(10).substring(0, latestScreen.length() - 14) + "?faces-redirect=true";
		} else {
			Iterator<Entry<String, String>> entryIter = paramMap.entrySet().iterator();
			String params = "?faces-redirect=true&amp;includeViewParams=true&amp;";
			while (entryIter.hasNext()) {
				Entry<String, String> entry = entryIter.next();
				params = params + entry.getKey() + "=" + entry.getValue();
				if (entryIter.hasNext()) {
					params = params + ",";
				}
			}
			return latestScreen + params;
			//return latestScreen.substring(10).substring(0, latestScreen.length() - 14) + params;
		}
	}
	
	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public MenuModel getMenuModel() {
		if (menuModel==null && sessionObject.getIsletmeSecildi()) {
		
			menuModel = new DefaultMenuModel();
						
			for (Organizasyon org : organizasyonFacade.listByIsletme(sessionObject.getSelectedIsletme())) {
				MenuItem menuItem = new MenuItem();
				menuItem.setValue(org.getAdi());
				// TODO bu ikisi çalışmıyor
				menuItem.addActionListener(createMethodActionListener("#{organizasyonService.sayfaLoad}", String.class, new Class[]{ActionEvent.class}));
				menuItem.getAttributes().put("organizasyonId", org.getId());
				menuItem.setUrl("/menu/organizasyonAcilis.jsf?organizasyonId=" + org.getId());					
				
				menuModel.addMenuItem(menuItem);				
			}
			
		}
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

    public MenuModel getOrganizasyonMenuModel() {
		if (!getOrganizasyonMenuMap().containsKey(sessionObject.getSelectedOrganizasyonTipi())) {
			
			organizasyonMenuModel = new DefaultMenuModel();
						
			for (Menu menu : menuFacade.listByOrganizasyonTipi(sessionObject.getSelectedOrganizasyonTipi())) {
				MenuItem menuItem = new MenuItem();
				menuItem.setId("menu"+sessionObject.getSelectedOrganizasyonTipi()+menu.getMenuAdi());
				menuItem.setValue(menu.getMenuAdi());
				//menuItem.addActionListener(createMethodActionListener("#{organizasyonService.sayfaLoad}", String.class, new Class[]{ActionEvent.class}));
				//menuItem.getAttributes().put("organizasyonId", org.getId());
				menuItem.setUrl(menu.getSayfa());		
				menuItem.setStyle("background-color:purple;color:white");
				
				organizasyonMenuModel.addMenuItem(menuItem);				
			}
			
			getOrganizasyonMenuMap().put(sessionObject.getSelectedOrganizasyonTipi(), organizasyonMenuModel);
			
		}
		return getOrganizasyonMenuMap().get(sessionObject.getSelectedOrganizasyonTipi());
	}

	public void setOrganizasyonMenuModel(MenuModel organizasyonMenuModel) {
		this.organizasyonMenuModel = organizasyonMenuModel;
	}

	public Map<OrganizasyonTipi, MenuModel> getOrganizasyonMenuMap() {
		if (organizasyonMenuMap==null) {
			organizasyonMenuMap = new HashMap<OrganizasyonTipi, MenuModel>();
		}
		return organizasyonMenuMap;
	}

	public void setOrganizasyonMenuMap(Map<OrganizasyonTipi, MenuModel> organizasyonMenuMap) {
		this.organizasyonMenuMap = organizasyonMenuMap;
	}


	private MethodExpression createMethodExpression(String valueExpression, Class<?> valueType, Class<?>[] expectedParamTypes) {

        MethodExpression methodExpression = null;
        try {
            ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
            methodExpression = factory.createMethodExpression(FacesContext.getCurrentInstance().getELContext(), valueExpression, valueType, expectedParamTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return methodExpression;
    }

    private MethodExpressionActionListener createMethodActionListener(String valueExpression, Class<?> valueType, Class<?>[] expectedParamTypes) {
        MethodExpressionActionListener actionListener = null;
        try {
            actionListener = new MethodExpressionActionListener(createMethodExpression(valueExpression, valueType, expectedParamTypes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actionListener;
    }

	public void isletmeSec(Isletme isletme) {
		sessionObject.setIsletmeSecildi(null);
		sessionObject.setSelectedIsletmeTipi(null);
		menuModel = null;
		sessionObject.setSelectedIsletme(isletme);
	}


	public List<Isletme> getSporOkuluList() {
		if (sporOkuluList == null) {
			sporOkuluList = new ArrayList<Isletme>();
			List<Isletme> isletmeListesi = sessionObject.getKisi().getIsletmeListesi();
			for (Iterator<Isletme> iterator = isletmeListesi.iterator(); iterator.hasNext();) {
				Isletme isletme = iterator.next();
				if (isletme.getClass().equals(SporOkulu.class)) {
					sporOkuluList.add(isletme);
				}
			}
			if (sporOkuluList.size()==1) {
				sessionObject.setSporOkulu((SporOkulu)sporOkuluList.get(0));
			}
		}
		return sporOkuluList;
	}

	public void setSporOkuluList(List<Isletme> sporOkuluList) {
		this.sporOkuluList = sporOkuluList;
	}
	
	public List<Isletme> getSporSalonuList() {
		if (sporSalonuList == null) {
			sporSalonuList = new ArrayList<Isletme>();
			List<Isletme> isletmeListesi = sessionObject.getKisi().getIsletmeListesi();
			for (Iterator<Isletme> iterator = isletmeListesi.iterator(); iterator.hasNext();) {
				Isletme isletme = iterator.next();
				if (isletme.getClass().equals(SporSalonu.class)) {
					sporSalonuList.add(isletme);
				}
			}
			if (sporSalonuList.size()==1) {
				sessionObject.setSporSalonu((SporSalonu)sporSalonuList.get(0));
			}
		}
		return sporSalonuList;
	}

	public void setSporSalonuList(List<Isletme> sporSalonuList) {
		this.sporSalonuList = sporSalonuList;
	}

	public String getSelectedMainMenu() {
		return selectedMainMenu;
	}

	public void setSelectedMainMenu(String selectedMainMenu) {
		this.selectedMainMenu = selectedMainMenu;
	}

	public MenuModel getSporOkuluMenuModel() {
		if (sporOkuluMenuModel==null) {
			
			sporOkuluMenuModel = new DefaultMenuModel();
			
			MenuItem menuItem = new MenuItem();
			menuItem.setValue("OKUL BİLGİLERİ");
			menuItem.setUrl("/menu/sporokulu/sporokulu.jsf");
			menuItem.setId("okulBilgileri");
			menuItem.setStyle("background-color:yellow;");
			sporOkuluMenuModel.addMenuItem(menuItem);
			
			menuItem = new MenuItem();
			menuItem.setValue("ÖĞRENCİLER");
			menuItem.setUrl("/menu/uyeler.jsf");
			menuItem.setId("ogrenciler");
			menuItem.setStyle("background-color:yellow;");
			sporOkuluMenuModel.addMenuItem(menuItem);
			
			menuItem = new MenuItem();
			menuItem.setValue("AİDATLAR");
			menuItem.setUrl("/menu/aidatlar.jsf");
			menuItem.setId("aidatlar");
			menuItem.setStyle("background-color:yellow;");
			sporOkuluMenuModel.addMenuItem(menuItem);
			
			menuItem = new MenuItem();
			menuItem.setValue("DEFTER");
			menuItem.setUrl("/menu/defter.jsf");
			menuItem.setId("defter");
			menuItem.setStyle("background-color:yellow;");
			sporOkuluMenuModel.addMenuItem(menuItem);

		}
		return sporOkuluMenuModel;
	}

	public void setSporOkuluMenuModel(MenuModel sporOkuluMenuModel) {
		this.sporOkuluMenuModel = sporOkuluMenuModel;
	}

	public MenuModel getSporSalonuMenuModel() {
		if (sporSalonuMenuModel==null) {
			
			sporSalonuMenuModel = new DefaultMenuModel();
			
			MenuItem menuItem = new MenuItem();
			menuItem.setValue("SALON BİLGİLERİ");
			menuItem.setUrl("/menu/sporsalonu/sporsalonu.jsf");
			menuItem.setId("salonBilgileri");
			menuItem.setStyle("background-color:orange;");
			sporSalonuMenuModel.addMenuItem(menuItem);
			
			menuItem = new MenuItem();
			menuItem.setValue("ÜYELER");
			menuItem.setUrl("/menu/uyeler.jsf");
			menuItem.setId("uyeler");
			menuItem.setStyle("background-color:orange;");
			sporSalonuMenuModel.addMenuItem(menuItem);
			
			menuItem = new MenuItem();
			menuItem.setValue("AİDATLAR");
			menuItem.setUrl("/menu/aidatlar.jsf");
			menuItem.setId("aidatlar2");
			menuItem.setStyle("background-color:orange;");
			sporSalonuMenuModel.addMenuItem(menuItem);			

			menuItem = new MenuItem();
			menuItem.setValue("DEFTER");
			menuItem.setUrl("/menu/defter.jsf");
			menuItem.setId("defter");
			menuItem.setStyle("background-color:orange;");
			sporSalonuMenuModel.addMenuItem(menuItem);			

		}
		return sporSalonuMenuModel;
	}

	public void setSporSalonuMenuModel(MenuModel sporSalonuMenuModel) {
		this.sporSalonuMenuModel = sporSalonuMenuModel;
	}

	public boolean isManySporOkulu() {
		manySporOkulu = (getSporOkuluList().size()>1) && (sessionObject.getSelectedIsletmeTipi().equals("SPOR_OKULU")); 
		return manySporOkulu;
	}

	public void setManySporOkulu(boolean manySporOkulu) {
		this.manySporOkulu = manySporOkulu;
	}

	public boolean isOneSporOkulu() {
		oneSporOkulu = (getSporOkuluList().size()==1) && (sessionObject.getSelectedIsletmeTipi().equals("SPOR_OKULU"));
		return oneSporOkulu;
	}

	public void setOneSporOkulu(boolean oneSporOkulu) {
		this.oneSporOkulu = oneSporOkulu;
	}

	public boolean isManySporSalonu() {
		manySporSalonu = (getSporSalonuList().size()>1) && (sessionObject.getSelectedIsletmeTipi().equals("SPOR_SALONU"));
		return manySporSalonu;
	}

	public void setManySporSalonu(boolean manySporSalonu) {
		this.manySporSalonu = manySporSalonu;
	}

	public boolean isOneSporSalonu() {
		oneSporSalonu = (getSporSalonuList().size()==1) && (sessionObject.getSelectedIsletmeTipi().equals("SPOR_SALONU"));
		return oneSporSalonu;
	}

	public void setOneSporSalonu(boolean oneSporSalonu) {
		this.oneSporSalonu = oneSporSalonu;
	}

	public boolean isSporOkuluSelected() {
		return sporOkuluSelected;
	}

	public void setSporOkuluSelected(boolean sporOkuluSelected) {
		this.sporOkuluSelected = sporOkuluSelected;
	}

	public boolean isSporSalonuSelected() {
		return sporSalonuSelected;
	}

	public void setSporSalonuSelected(boolean sporSalonuSelected) {
		this.sporSalonuSelected = sporSalonuSelected;
	}

	
	public void selectSporOkulu() {
		setSporOkuluSelected(true);
		setSporSalonuSelected(false);
	}

	public void selectSporSalonu() {
		setSporSalonuSelected(true);
		setSporOkuluSelected(false);
	}

	public boolean isSporOkuluVar() {
		sporOkuluVar = (getSporOkuluList().size()>0);
		return sporOkuluVar;
	}

	public void setSporOkuluVar(boolean sporOkuluVar) {
		this.sporOkuluVar = sporOkuluVar;
	}

	public boolean isSporSalonuVar() {
		sporSalonuVar = (getSporSalonuList().size()>0);
		return sporSalonuVar;
	}

	public void setSporSalonuVar(boolean sporSalonuVar) {
		this.sporSalonuVar = sporSalonuVar;
	}

	public String yonlendir(String url) {
			return url + "?faces-redirect=true";			
	}

	public String getSubMenuType() {
		return subMenuType;
	}

	public void setSubMenuType(String subMenuType) {
		this.subMenuType = subMenuType;
	}

}
