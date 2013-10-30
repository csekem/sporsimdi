package com.sporsimdi.action.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Urun;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.action.list.UrunListesi;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;

@ManagedBean(name = "urunService")
@ViewScoped
public class UrunService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{urunListesi}")
	private UrunListesi urunListesi;

	@EJB
	private OrganizasyonFacade organizasyonFacade;

	@EJB
	private OrgTesisFacade orgTesisFacade;

	@EJB
	private KasaFacade kasaFacade;

	@EJB
	private UrunFacade urunFacade;

	private List<Urun> urunler;

	private DualListModel<Urun> urunOneriler;

	private UploadedFile uploadedFile;

	private StreamedContent urunResim;

	private TreeNode root;

	private TreeNode selectedNode;

	private String selectedLevel;

	private Organizasyon organizasyon;

	private Kasa selectedKasa;

	public TreeNode getRoot() {
		if (root == null) {
			boolean selected = false;
			root = new DefaultTreeNode(getOrganizasyon(), null);
			root.setSelectable(false);
			for (OrgTesis orgTesis : orgTesisFacade.listByOrganizasyon(organizasyon.getId())) {
				TreeNode nodeOrgTesis = new DefaultTreeNode(orgTesis, root);
				nodeOrgTesis.setSelectable(false);
				for (Kasa kasa : kasaFacade.listAllByOrgTesis(orgTesis)) {
					TreeNode nodeKasa = new DefaultTreeNode(kasa, nodeOrgTesis);
					nodeKasa.getChildCount();
					if (!selected) {
						nodeKasa.setSelected(true);
						selected = true;
						setSelectedNode(nodeKasa);
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

	public String getSelectedLevel() {
		return selectedLevel;
	}

	public void setSelectedLevel(String selectedLevel) {
		this.selectedLevel = selectedLevel;
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

	public Kasa getSelectedKasa() {
		return selectedKasa;
	}

	public void setSelectedKasa(Kasa selectedKasa) {
		this.selectedKasa = selectedKasa;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public UrunListesi getUrunListesi() {
		return urunListesi;
	}

	public void setUrunListesi(UrunListesi urunListesi) {
		this.urunListesi = urunListesi;
	}

	public List<Urun> getUrunler() {
		return urunler;
	}

	public void setUrunler(List<Urun> urunler) {
		this.urunler = urunler;
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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public StreamedContent getUrunResim() {
		File f = new File("c:/images/urunler/1.png");
		try {
			urunResim = new DefaultStreamedContent(new FileInputStream(f), "image/png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urunResim;
	}

	public void setUrunResim(StreamedContent urunResim) {
		this.urunResim = urunResim;
	}

	public UrunService() {
	}

	public String listele() {
		if (getSelectedKasa() != null) {
			urunler = urunFacade.listByKasa(getSelectedKasa().getId());
		}

		return "success";
	}

	public void sayfaLoad() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> pMap = fc.getExternalContext().getRequestParameterMap();
		String organizasyonId = pMap.get("organizasyonId");
		if (organizasyonId != null) {
			Organizasyon org = organizasyonFacade.findById(Long.valueOf(organizasyonId));
			setOrganizasyon(org);
			sessionObject.setSelectedOrganizasyon(org);
		} else {
			setOrganizasyon(sessionObject.getSelectedOrganizasyon());
		}
		listele();
	}

	public void onNodeSelect(NodeSelectEvent event) {
		setSelectedNode(event.getTreeNode());
		if (event.getTreeNode().getData() instanceof Organizasyon) {
			selectedLevel = "ORGANIZASYON";
		} else if (event.getTreeNode().getData() instanceof OrgTesis) {
			selectedLevel = "ORGTESIS";
		} else if (event.getTreeNode().getData() instanceof Kasa) {
			selectedKasa = (Kasa) event.getTreeNode().getData();
			// listele();
			selectedLevel = "KASA";

			urunListesi.setSuperId(selectedKasa.getId());
			urunListesi.sayfaLoad();

		}
	}

	public void onTransfer(TransferEvent event) {
		// FacesContext.getCurrentInstance().addMessage(null, "eklendi");
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
		getUrunListesi().fillList();
	}

	public void siraDegistir() {
		int i = 1;
		for (Urun urun : getUrunler()) {
			urun.setSira(i++);
			urunFacade.merge(urun);
		}
	}

	public void urunKaydet() {

		if (uploadedFile != null) {
			urunListesi.getInstance().setImage(uploadedFile.getContents());
		}
		urunListesi.save();

	}

	public void handleFileUpload(Urun urun) {
		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images/urunler/");
		// File f = new File("C:/images/urunler" + urun.getId() + ".jpg");
		File f = new File(path + "/1" + ".png");
		File f2 = new File("C:/sporsimdi/workspace/sporsimdi/WebContent/images/urunler/" + "1" + ".png");
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f);
			fo.write(getUploadedFile().getContents());
			fo.flush();
			fo.close();

			fo = new FileOutputStream(f2);
			fo.write(getUploadedFile().getContents());
			fo.flush();
			fo.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
