package com.sporsimdi.action.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Urun;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;

@ManagedBean(name = "urunListesi")
@ViewScoped
public class UrunListesi extends ListBean<Urun> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private UrunFacade urunFacade;

	@EJB
	private KasaFacade kasaFacade;

	@EJB
	private OrgTesisFacade orgTesisFacade;

	private UploadedFile uploadedFile;

	private Urun urunTmp;

	private Organizasyon organizasyon;

	private TreeNode root;

	private TreeNode selectedNode;

	private Kasa selectedKasa;

	private DualListModel<Urun> urunOneriler;

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Urun getUrunTmp() {
		urunTmp = urunFacade.findById(0);
		return urunTmp;
	}

	public void setUrunTmp(Urun urunTmp) {
		this.urunTmp = urunTmp;
	}

	public Organizasyon getOrganizasyon() {
		if (organizasyon == null) {
			organizasyon = sessionObject.getSelectedOrganizasyon();
		}
		return organizasyon;
	}

	public void setOrganizasyon(Organizasyon organizasyon) {
		this.organizasyon = organizasyon;
	}

	public TreeNode getRoot() {
		if (root == null) {
			boolean selected = false;
			root = new DefaultTreeNode(getOrganizasyon(), null);
			root.setSelectable(false);
			for (OrgTesis orgTesis : orgTesisFacade.listByOrganizasyon(getOrganizasyon().getId())) {
				TreeNode nodeOrgTesis = new DefaultTreeNode(orgTesis, root);
				nodeOrgTesis.setSelectable(false);
				for (Kasa kasa : kasaFacade.listAllByOrgTesis(orgTesis)) {
					TreeNode nodeKasa = new DefaultTreeNode(kasa, nodeOrgTesis);
					if (!selected) {
						nodeKasa.setSelected(true);
						selected = true;
						setSelectedNode(nodeKasa);
						setSelectedKasa((Kasa) nodeKasa.getData());
					}
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

	public Kasa getSelectedKasa() {
		return selectedKasa;
	}

	public void setSelectedKasa(Kasa selectedKasa) {
		this.selectedKasa = selectedKasa;
	}

	public DualListModel<Urun> getUrunOneriler() {
		if ((urunOneriler == null || (urunOneriler.getSource().size() == 0 && urunOneriler.getTarget().size() == 0))
				&& getSelectedKasa() != null) {
			List<Urun> sourceList = urunFacade.listByKasa(new Long(0));

			List<Urun> oneriList = urunFacade.listByKasa(new Long(0));
			List<Urun> targetList = urunFacade.listByKasa(getSelectedKasa().getId());
			for (Urun urun : targetList) {
				for (Urun oneri : oneriList) {
					if (urun.getAdi().trim().equals(oneri.getAdi().trim())) {
						sourceList.remove(oneri);
					}
				}
			}
			urunOneriler = new DualListModel<Urun>(sourceList, new ArrayList<Urun>());
		}
		if (urunOneriler == null) {
			urunOneriler = new DualListModel<Urun>(new ArrayList<Urun>(), new ArrayList<Urun>());
		}
		return urunOneriler;
	}

	public void setUrunOneriler(DualListModel<Urun> urunOneriler) {
		this.urunOneriler = urunOneriler;
	}

	@Override
	public void fillSuperId() {
		setSuperId(getSelectedKasa().getId());
		setStrSuperId("sporsimdi.com.kasaId");
		setModelSuper(kasaFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(urunFacade.listByKasa(getSelectedKasa().getId()));
	}

	@Override
	public void fillSaving() {
		if (getUploadedFile() != null) {
			getInstance().setImage(getUploadedFile().getContents());
		}
		if (!isManaged()) {
			getInstance().setKasa(kasaFacade.findById(getSuperId()));
			getInstance().setSira(urunFacade.findNextSira(getSuperId()) + 1);
		}
	}

	public void uploadFile(FileUploadEvent event) {
		uploadedFile = event.getFile();

		getUrunTmp().setImage(getUploadedFile().getContents());
		urunFacade.merge(urunTmp);

	}

	@Override
	public void enableNewerItem() {
		getUrunTmp().setImage(null);
		urunFacade.merge(urunTmp);

		super.enableNewerItem();
	}

	@Override
	public void updateInstance(Urun t) {
		getUrunTmp().setImage(t.getImage());
		urunFacade.merge(urunTmp);
		super.updateInstance(t);
	}

	@Override
	public void deleteInstance(Urun t) {
		getUrunTmp().setImage(t.getImage());
		urunFacade.merge(urunTmp);
		super.deleteInstance(t);
	}

	@Override
	public List<Urun> getList() {
		if (list == null) {
			fillList();
		}
		return super.getList();
	}

	public void onNodeSelect(NodeSelectEvent event) {
		setSelectedNode(event.getTreeNode());
		setSelectedKasa((Kasa) event.getTreeNode().getData());
	}

	public void oneriOnayla() {
		for (Urun urun : getUrunOneriler().getTarget()) {
			urun.setId(null);
			urun.setKasa(getSelectedKasa());
			urun.setSatisFiyat(new BigDecimal(0));
			urun.setSira(urunFacade.findNextSira(getSelectedKasa().getId()) + 1);
			urunFacade.persist(urun);
		}
		urunOneriler = null;
		fillList();
	}

	public void siraDegistir() {
		int i = 1;
		for (Urun urun : getList()) {
			urun.setSira(i++);
			urunFacade.merge(urun);
		}
		fillList();
	}

}
