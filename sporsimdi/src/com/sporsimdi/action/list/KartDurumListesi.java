package com.sporsimdi.action.list;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qrpos.action.facade.KartDurumFacade;
import com.qrpos.model.entity.Kart;
import com.qrpos.model.entity.KartDurum;
import com.qrpos.model.type.KartStatus;
import com.sporsimdi.action.facade.KalemFacade;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.action.home.DefterHome;
import com.sporsimdi.action.service.DefterService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.type.KalemTuru;

@ManagedBean(name = "kartDurumListesi")
@ViewScoped
public class KartDurumListesi extends ListBean<KartDurum> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{defterHome}")
	private DefterHome defterHome;

	@ManagedProperty(value = "#{defterService}")
	private DefterService defterService;

	@EJB
	private OrganizasyonFacade organizasyonFacade;

	@EJB
	private KartDurumFacade kartDurumFacade;

	@EJB
	private KalemFacade kalemFacade;

	@EJB
	private OrgTesisFacade orgTesisFacade;

	@EJB
	private KasaFacade kasaFacade;

	private Organizasyon organizasyon;

	private Kart kart = new Kart();

	private TreeNode root;

	private TreeNode selectedNode;

	private KartDurum selectedKartDurum;
	private Kart selectedKart;

	private String islemHeader;
	private String islemMevcutLabel;
	private BigDecimal islemMevcutValue;
	private String islemEklenenLabel;
	private BigDecimal islemEklenenValue;
	private String islemSonLabel;
	private BigDecimal islemSonValue;
	private String islemLabel;
	private BigDecimal islemCarpan;

	public String getIslemHeader() {
		return islemHeader;
	}

	public void setIslemHeader(String islemHeader) {
		this.islemHeader = islemHeader;
	}

	public String getIslemMevcutLabel() {
		return islemMevcutLabel;
	}

	public void setIslemMevcutLabel(String islemMevcutLabel) {
		this.islemMevcutLabel = islemMevcutLabel;
	}

	public BigDecimal getIslemMevcutValue() {
		return islemMevcutValue;
	}

	public void setIslemMevcutValue(BigDecimal islemMevcutValue) {
		this.islemMevcutValue = islemMevcutValue;
	}

	public String getIslemEklenenLabel() {
		return islemEklenenLabel;
	}

	public void setIslemEklenenLabel(String islemEklenenLabel) {
		this.islemEklenenLabel = islemEklenenLabel;
	}

	public BigDecimal getIslemEklenenValue() {
		return islemEklenenValue;
	}

	public void setIslemEklenenValue(BigDecimal islemEklenenValue) {
		this.islemEklenenValue = islemEklenenValue;
	}

	public String getIslemSonLabel() {
		return islemSonLabel;
	}

	public void setIslemSonLabel(String islemSonLabel) {
		this.islemSonLabel = islemSonLabel;
	}

	public BigDecimal getIslemSonValue() {
		return islemSonValue;
	}

	public void setIslemSonValue(BigDecimal islemSonValue) {
		this.islemSonValue = islemSonValue;
	}

	public String getIslemLabel() {
		return islemLabel;
	}

	public void setIslemLabel(String islemLabel) {
		this.islemLabel = islemLabel;
	}

	public BigDecimal getIslemCarpan() {
		return islemCarpan;
	}

	public void setIslemCarpan(BigDecimal islemCarpan) {
		this.islemCarpan = islemCarpan;
	}

	@Override
	public void fillSuperId() {
		setSuperId(getOrganizasyon().getId());
		setStrSuperId("sporsimdi.com.organizasyonId");
		setModelSuper(organizasyonFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(kartDurumFacade.listByOrganizasyon(getOrganizasyon().getId()));
	}

	@Override
	public void fillSaving() {
		getInstance().getKart().setOrganizasyon(getOrganizasyon());
		getInstance().setKartStatus(KartStatus.ACIK);
		if (getInstance().getKart().getBakiye() != null
				&& getInstance().getKart().getBakiye().compareTo(BigDecimal.ZERO) > 0) {
			kasaIslemiYap(KalemTuru.KART, getInstance().getKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getInstance().getQrcard().getNo() + " nolu karta para yüklemesi.", BigDecimal.ZERO, getInstance()
					.getKart().getBakiye());
		}
		if (getInstance().getKart().getDepozito() != null
				&& getInstance().getKart().getDepozito().compareTo(BigDecimal.ZERO) > 0) {
			kasaIslemiYap(KalemTuru.DEPOZITO, getInstance().getKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getInstance().getQrcard().getNo() + " nolu karta depozito yüklemesi.", BigDecimal.ZERO,
					getInstance().getKart().getDepozito());
		}
		if (getInstance().getKart().getBonus() != null
				&& getInstance().getKart().getBonus().compareTo(BigDecimal.ZERO) > 0) {
			kasaIslemiYap(KalemTuru.BONUS, getInstance().getKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getInstance().getQrcard().getNo() + " nolu karta bonus yüklemesi.", BigDecimal.ZERO,
					getInstance().getKart().getBonus());
			kasaIslemiYap(KalemTuru.BONUS, getInstance().getKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getInstance().getQrcard().getNo() + " nolu karta bonus yüklemesi.", getInstance().getKart()
					.getBonus(), BigDecimal.ZERO);
		}
	}

	@Override
	public List<KartDurum> getList() {
		if (list == null) {
			fillList();
		}
		return super.getList();
	}

	@Override
	public KartDurum createInstance() {
		KartDurum kartDurum = super.createInstance();
		Kart kart = new Kart();
		kartDurum.setKart(kart);
		return kartDurum;
	}

	@Override
	public void updateInstance(KartDurum t) {
		super.updateInstance(t);
		if (getInstance().getKart() == null) {
			getInstance().setKart(new Kart());
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public DefterHome getDefterHome() {
		return defterHome;
	}

	public void setDefterHome(DefterHome defterHome) {
		this.defterHome = defterHome;
	}

	public DefterService getDefterService() {
		return defterService;
	}

	public void setDefterService(DefterService defterService) {
		this.defterService = defterService;
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

	public Kart getKart() {
		return kart;
	}

	public void setKart(Kart kart) {
		this.kart = kart;
	}

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

	public KartDurum getSelectedKartDurum() {
		return selectedKartDurum;
	}

	public void setSelectedKartDurum(KartDurum selectedKartDurum) {
		this.selectedKartDurum = selectedKartDurum;
	}

	public Kart getSelectedKart() {
		return selectedKart;
	}

	public void setSelectedKart(Kart selectedKart) {
		this.selectedKart = selectedKart;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		setSelectedNode(event.getTreeNode());
	}

	public void kasaIslemiYap(KalemTuru kalemTuru, String aciklama, BigDecimal gider, BigDecimal gelir) {
		defterHome.sayfaLoad();

		defterHome.getInstance().setTarih(new UtilDate().getTime());
		defterHome.getInstance().setKalem(
				kalemFacade.getByAdi(sessionObject.getSelectedIsletme(), kalemTuru.getI18nKey()));
		defterHome.getInstance().setAciklama("[Otomatik Kayıt]: " + aciklama);
		defterHome.getInstance().setGider(gider);
		defterHome.getInstance().setGelir(gelir);
		// defterHome.getInstance().setReferenceModel(getInstance());
		// defterHome.getInstance().setReferenceId(tahsilatFacade.getTahakkukDetayByTahsilat(getInstance().getId()).getId());

		defterService.sayfaLoad();
		defterService.setSelectedKasa((Kasa) getSelectedNode().getData());

		try {
			defterHome.saveWithoutNavigation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paraYukleEkranAc(KartDurum kartDurum) {
		setSelectedKartDurum(kartDurum);
		setSelectedKart(kartDurum.getKart());

		setIslemHeader("PARA YÜKLEME");
		setIslemMevcutLabel("Mevcut Bakiye");
		setIslemMevcutValue(getSelectedKart().getBakiye());
		setIslemEklenenLabel("Yüklenen Tutar");
		setIslemEklenenValue(BigDecimal.ZERO);
		setIslemSonLabel("Son Bakiye");
		setIslemSonValue(new BigDecimal(getSelectedKart().getBakiye().doubleValue()));
		setIslemLabel("PARA YÜKLE");
		setIslemCarpan(new BigDecimal(1));
	}

	public void islemTutarBelirle() {
		setIslemSonValue(getIslemMevcutValue().add(getIslemEklenenValue().multiply(getIslemCarpan())));
	}

	public void islemYap() {
		getSelectedKart().setBakiye(getIslemSonValue());
		getEntityManagerDao().updateObject(getSelectedKart());
		if (getIslemCarpan().intValue() == 1) {
			kasaIslemiYap(KalemTuru.KART, getSelectedKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getSelectedKartDurum().getQrcard().getNo() + " nolu karta para yüklemesi.", BigDecimal.ZERO,
					getIslemEklenenValue());
		} else {
			kasaIslemiYap(KalemTuru.KART, getSelectedKart().getUye().getAdSoyad() + " adlı üyenin "
					+ getSelectedKartDurum().getQrcard().getNo() + " nolu karta para iadesi.", getIslemEklenenValue(),
					BigDecimal.ZERO);
		}
		fillList();
	}

	public void paraIadeEkranAc(KartDurum kartDurum) {
		setSelectedKartDurum(kartDurum);
		setSelectedKart(kartDurum.getKart());

		setIslemHeader("PARA İADE ETME");
		setIslemMevcutLabel("Mevcut Bakiye");
		setIslemMevcutValue(getSelectedKart().getBakiye());
		setIslemEklenenLabel("İade Edilen Tutar");
		setIslemEklenenValue(BigDecimal.ZERO);
		setIslemSonLabel("Son Bakiye");
		setIslemSonValue(new BigDecimal(getSelectedKart().getBakiye().doubleValue()));
		setIslemLabel("PARA İADE ET");
		setIslemCarpan(new BigDecimal(-1));
	}
}
