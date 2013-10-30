package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.action.list.ListBean;
import com.sporsimdi.action.list.TarifeListesi;
import com.sporsimdi.action.list.UyeListesi;
import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;

@ManagedBean(name="organizasyonService")
@ViewScoped
public class OrganizasyonService implements Serializable {

	private static final long serialVersionUID = 5390492535300935394L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	@EJB
	private OrganizasyonFacade organizasyonFacade;
	
	@EJB
	private OrgTesisFacade orgTesisFacade;

	@EJB
	private GrupFacade grupFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{tarifeListesi}")
	private TarifeListesi tarifeListesi;

	@ManagedProperty(value = "#{uyeListesi}")
	private UyeListesi uyeListesi;

	private TreeNode root;
	
	private TreeNode selectedNode;
	
	private String selectedLevel;

	private Organizasyon organizasyon;
	
	private Grup selectedGrup;
	
	public TarifeListesi getTarifeListesi() {
		return tarifeListesi;
	}

	public void setTarifeListesi(TarifeListesi tarifeListesi) {
		this.tarifeListesi = tarifeListesi;
	}

	public UyeListesi getUyeListesi() {
		return uyeListesi;
	}

	public void setUyeListesi(UyeListesi uyeListesi) {
		this.uyeListesi = uyeListesi;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public TreeNode getRoot() {
		if (root==null) {
			root = new DefaultTreeNode(getOrganizasyon(), null);

			for (OrgTesis orgTesis : orgTesisFacade.listByOrganizasyon(organizasyon.getId())) {
				TreeNode nodeOrgTesis = new DefaultTreeNode(orgTesis, root);

				for (Grup grup : grupFacade.listByOrgTesis(orgTesis.getId())) {
					TreeNode nodeGrup = new DefaultTreeNode(grup, nodeOrgTesis);
					
					nodeGrup.getChildCount();
				}				
				nodeOrgTesis.setExpanded(true);
			}			
			root.setExpanded(true);
		}
		return root;
	}


	public void setRoot(TreeNode root) {
		this.root = root;
	}


	public TreeNode getSelectedNode() {
		return selectedNode;
	}


	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}


	public Organizasyon getOrganizasyon() {
		return organizasyon;
	}


	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}


	public Grup getSelectedGrup() {
		return selectedGrup;
	}

	public void setSelectedGrup(Grup selectedGrup) {
		this.selectedGrup = selectedGrup;
	}

	public String getSelectedLevel() {
		return selectedLevel;
	}


	public void setSelectedLevel(String selectedLevel) {
		this.selectedLevel = selectedLevel;
	}


	public void sayfaLoad() {
  		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> pMap = fc.getExternalContext().getRequestParameterMap();
		String organizasyonId  = pMap.get("organizasyonId");
		if (organizasyonId!=null) {
			Organizasyon org = organizasyonFacade.findById(Long.valueOf(organizasyonId));
			setOrganizasyon(org);		
			sessionObject.setSelectedOrganizasyon(org);
			sessionObject.setSelectedOrganizasyonTipi(org.getOrganizasyonTipi());
		} else {
			setOrganizasyon(sessionObject.getSelectedOrganizasyon());
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {  
		setSelectedNode(event.getTreeNode());
		if (event.getTreeNode().getData() instanceof Organizasyon) {
			selectedLevel = "ORGANIZASYON";
		} else if (event.getTreeNode().getData() instanceof OrgTesis) {
			selectedLevel = "ORGTESIS";
		} else if (event.getTreeNode().getData() instanceof Grup) {
			selectedGrup = (Grup) event.getTreeNode().getData();
			
			tarifeListesi.setSuperId(((ExtendedModel)event.getTreeNode().getData()).getId());
			tarifeListesi.sayfaLoad();
			uyeListesi.setSuperId(((ExtendedModel)event.getTreeNode().getData()).getId());
			uyeListesi.sayfaLoad();
			
			tarifeListesi.clear();
			uyeListesi.clear();
			
			selectedLevel = "GRUP";
		}
	}  

	public String saveList(ListBean<ExtendedModel> listBean) {  
		listBean.save();
		/*setRoot(null);
		
		String nodeLevel = getSelectedNode().getData().getClass().toString();
		if (getSelectedNode().getData().getClass().getSuperclass().equals(Isletme.class)) {
			nodeLevel = getSelectedNode().getData().getClass().getSuperclass().toString();
		}
		return "/menu/isletme?faces-redirect=true&amp;includeViewParams=true&amp;nodeLevel=" + nodeLevel + 
																				"&amp;modelId=" + ((ExtendedModel)getSelectedNode().getData()).getId();
																				*/
		return "";
	}  

	public void deleteList(ListBean<ExtendedModel> listBean) {  
		listBean.delete();
		setRoot(null);			
	}  


}
