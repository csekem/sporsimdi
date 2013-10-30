package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.action.list.GrupListesi;
import com.sporsimdi.action.list.ListBean;
import com.sporsimdi.action.list.OrgTesisListesi;
import com.sporsimdi.action.list.OrganizasyonListesi;
import com.sporsimdi.action.list.TarifeListesi;
import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;

@ManagedBean(name="organizasyonYapisiService")
@ViewScoped
public class OrganizasyonYapisiService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5390492535300935394L;

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	private TreeNode root;
	
	private TreeNode selectedNode;
	
	private String selectedLevel;
	
	private List<Organizasyon> orgList;	
	private Map<Long, List<Organizasyon>> orgMap;
	private Map<Long, List<OrgTesis>> orgTesisMap;
	private Map<Long, List<Grup>> grupMap;
	private Map<Long, Long> grupUyeSayisiMap;
	private Map<Long, Long> orgTesisUyeSayisiMap;

	@EJB
	private OrganizasyonFacade organizasyonFacade;
	
	@EJB
	private OrgTesisFacade orgTesisFacade;
	
	@EJB
	private GrupFacade grupFacade;

	@EJB
	private UyeGrupFacade uyeGrupFacade;
	
	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{organizasyonListesi}")
	private OrganizasyonListesi organizasyonListesi;

	@ManagedProperty(value = "#{orgTesisListesi}")
	private OrgTesisListesi orgTesisListesi;

	@ManagedProperty(value = "#{grupListesi}")
	private GrupListesi grupListesi;

	@ManagedProperty(value = "#{tarifeListesi}")
	private TarifeListesi tarifeListesi;


	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public OrganizasyonListesi getOrganizasyonListesi() {
		return organizasyonListesi;
	}


	public void setOrganizasyonListesi(OrganizasyonListesi organizasyonListesi) {
		this.organizasyonListesi = organizasyonListesi;
	}


	public OrgTesisListesi getOrgTesisListesi() {
		return orgTesisListesi;
	}


	public void setOrgTesisListesi(OrgTesisListesi orgTesisListesi) {
		this.orgTesisListesi = orgTesisListesi;
	}


	public GrupListesi getGrupListesi() {
		return grupListesi;
	}


	public void setGrupListesi(GrupListesi grupListesi) {
		this.grupListesi = grupListesi;
	}


	public TarifeListesi getTarifeListesi() {
		return tarifeListesi;
	}

	public void setTarifeListesi(TarifeListesi tarifeListesi) {
		this.tarifeListesi = tarifeListesi;
	}

	public String orgTipiGetir(Organizasyon organizasyon) {
		return null; //organizasyonFacade.getIsletmeTipi(isletme);
	}


	public TreeNode getRoot() {
		if (root==null) {
			root = new DefaultTreeNode(sessionObject.getSelectedIsletme(), null);

			for (Organizasyon organizasyon : organizasyonFacade.listByIsletme(sessionObject.getSelectedIsletme())) {
				TreeNode nodeOrg = new DefaultTreeNode(organizasyon, root);

				for (OrgTesis orgTesis : orgTesisFacade.listByOrganizasyon(organizasyon.getId())) {
					TreeNode nodeOrgTesis = new DefaultTreeNode(orgTesis, nodeOrg);

					for (Grup grup : grupFacade.listByOrgTesis(orgTesis.getId())) {
						TreeNode nodeGrup = new DefaultTreeNode(grup, nodeOrgTesis);
					
						nodeGrup.getChildCount();
					}
					nodeOrgTesis.setExpanded(true);
				}
				nodeOrg.setExpanded(true);
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
	
	public String getSelectedLevel() {
		return selectedLevel;
	}


	public void setSelectedLevel(String selectedLevel) {
		this.selectedLevel = selectedLevel;
	}


	public List<Organizasyon> getOrgList() {
		if (!getOrgMap().containsKey(sessionObject.getSelectedIsletme().getId())) {
			getOrgMap().put(sessionObject.getSelectedIsletme().getId(), organizasyonFacade.listByIsletme(sessionObject.getSelectedIsletme()));
		}
		orgList = getOrgMap().get(sessionObject.getSelectedIsletme().getId());
		return orgList;
	}

	public void setOrgList(List<Organizasyon> orgList) {
		this.orgList = orgList;
	}

	public Map<Long, List<Organizasyon>> getOrgMap() {
		if (orgMap==null) {
			orgMap = new HashMap<Long, List<Organizasyon>>();
		}
		return orgMap;
	}

	public void setOrgMap(Map<Long, List<Organizasyon>> orgMap) {
		this.orgMap = orgMap;
	}

	public Map<Long, List<OrgTesis>> getOrgTesisMap() {
		if (orgTesisMap==null) {
			orgTesisMap = new HashMap<Long, List<OrgTesis>>();
		}
		return orgTesisMap;
	}

	public void setOrgTesisMap(Map<Long, List<OrgTesis>> orgTesisMap) {
		this.orgTesisMap = orgTesisMap;
	}

	public Map<Long, List<Grup>> getGrupMap() {
		if (grupMap==null) {
			grupMap = new HashMap<Long, List<Grup>>();
		}
		return grupMap;
	}

	public void setGrupMap(Map<Long, List<Grup>> grupMap) {
		this.grupMap = grupMap;
	}

	public Map<Long, Long> getGrupUyeSayisiMap() {
		if (grupUyeSayisiMap==null) {
			grupUyeSayisiMap = new HashMap<Long, Long>();
		}
		return grupUyeSayisiMap;
	}

	public void setGrupUyeSayisiMap(Map<Long, Long> grupUyeSayisiMap) {
		this.grupUyeSayisiMap = grupUyeSayisiMap;
	}

	public Map<Long, Long> getOrgTesisUyeSayisiMap() {
		if (orgTesisUyeSayisiMap==null) {
			orgTesisUyeSayisiMap = new HashMap<Long, Long>();
		}
		return orgTesisUyeSayisiMap;
	}

	public void setOrgTesisUyeSayisiMap(Map<Long, Long> orgTesisUyeSayisiMap) {
		this.orgTesisUyeSayisiMap = orgTesisUyeSayisiMap;
	}

	public List<OrgTesis> orgTesisleriGetir(Organizasyon org) {
		if (!getOrgTesisMap().containsKey(org.getId())) {
			getOrgTesisMap().put(org.getId(), orgTesisFacade.listByOrganizasyon(org.getId()));
		}
		return getOrgTesisMap().get(org.getId());
	}

	public List<Grup> gruplariGetir(OrgTesis orgTesis) {
		if (!getGrupMap().containsKey(orgTesis.getId())) {
			getGrupMap().put(orgTesis.getId(), grupFacade.listByOrgTesis(orgTesis.getId()));
		}
		return getGrupMap().get(orgTesis.getId());
	}

	public Long grupUyeSayisiGetir(Grup grup) {
		if (!getGrupUyeSayisiMap().containsKey(grup.getId())) {
			getGrupUyeSayisiMap().put(grup.getId(), uyeGrupFacade.countByGrup(grup));
		}
		return getGrupUyeSayisiMap().get(grup.getId());
	}

	public Long orgTesisUyeSayisiGetir(OrgTesis orgTesis) {
		if (!getOrgTesisUyeSayisiMap().containsKey(orgTesis.getId())) {
			Long orgTesisUyeSayisi = new Long(0);
			for (Grup grup : gruplariGetir(orgTesis)) {
				orgTesisUyeSayisi = orgTesisUyeSayisi + grupUyeSayisiGetir(grup);
			}
			getOrgTesisUyeSayisiMap().put(orgTesis.getId(), orgTesisUyeSayisi);
		}
		return getOrgTesisUyeSayisiMap().get(orgTesis.getId());
	}

	public void onNodeSelect(NodeSelectEvent event) {  
		setSelectedNode(event.getTreeNode());
		Object data = event.getTreeNode().getData();
		selectNode(data);
	}

	private void selectNode(Object data) {
		if (data instanceof Isletme) {
			selectedLevel = "ISLETME";
			organizasyonListesi.sayfaLoad();
		} else if (data instanceof Organizasyon) {
			selectedLevel = "ORGANIZASYON";
			orgTesisListesi.setSuperId(((ExtendedModel) data).getId());
			orgTesisListesi.sayfaLoad();
		} else if (data instanceof OrgTesis) {
			selectedLevel = "ORGTESIS";
			grupListesi.setSuperId(((ExtendedModel) data).getId());
			grupListesi.sayfaLoad();
		} else if (data instanceof Grup) {
			selectedLevel = "GRUP";
			tarifeListesi.setSuperId(((ExtendedModel) data).getId());
			tarifeListesi.sayfaLoad();
		}
	}  

	public String saveList(ListBean<ExtendedModel> listBean) {  
		listBean.save();
		return reselectTree();			
	}

	public String deleteList(ListBean<ExtendedModel> listBean) {  
		listBean.delete();
		return reselectTree();			
	}  

	public String cancelList(ListBean<ExtendedModel> listBean) {  
		listBean.disableNewerItem();
		return reselectTree();			
	}  

	private String reselectTree() {
		setRoot(null);
		
		String nodeLevel = getSelectedNode().getData().getClass().toString();
		if (getSelectedNode().getData().getClass().getSuperclass().equals(Isletme.class)) {
			nodeLevel = getSelectedNode().getData().getClass().getSuperclass().toString();
		}
		return "/menu/organizasyonlar?faces-redirect=true&amp;includeViewParams=true&amp;nodeLevel=" + nodeLevel + 
																				"&amp;modelId=" + ((ExtendedModel) getSelectedNode().getData()).getId();
	}  

	public void sayfaLoad() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> pMap = fc.getExternalContext().getRequestParameterMap();
		String pNodeLevel = pMap.get("nodeLevel");
		String pModelId = pMap.get("modelId");
		
		if (pNodeLevel != null && !pNodeLevel.isEmpty()
			&& pModelId != null && !pModelId.isEmpty()) {
			
			if (pNodeLevel.equals(Isletme.class.toString())) {
				getRoot().setSelected(true);
				setSelectedNode(getRoot());
				selectNode(getRoot().getData());
			} else {
				getRoot().setExpanded(true);
				searchChildren(getRoot(), pNodeLevel, pModelId);
			}
		}
		
	}

	
	private void searchChildren (TreeNode rootNode, String nodeLevel, String modelId) {
		for (TreeNode node : rootNode.getChildren()) {
			if (nodeLevel.equals(node.getData().getClass().toString())) {
				if ( ((ExtendedModel) node.getData()).getId().longValue() == new Long(modelId).longValue()) {
					node.setSelected(true);
					expandNode(node);
					setSelectedNode(node);
					selectNode(node.getData());
					break;
				}
			} else {
				searchChildren(node, nodeLevel, modelId);
			}				
		}
	}
	
	private void expandNode(TreeNode node) {
		if (node.getParent()!=null) {
			node.getParent().setExpanded(true);
			expandNode(node.getParent());
		}
	}

}
