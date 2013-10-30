package com.sporsimdi.action.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.action.home.TahsilatHome;
import com.sporsimdi.action.util.SSSchedulerDay;
import com.sporsimdi.action.util.SSSchedulerMonth;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.action.util.spi.SSSchedulerBase;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.TahakkukDetay;
import com.sporsimdi.model.entity.Tahsilat;
import com.sporsimdi.model.entity.UyeGrup;
import com.sporsimdi.model.type.OdemeSekli;
import com.sporsimdi.model.type.TahsilatTipi;

@ManagedBean(name = "aidatService")
@ViewScoped
public class AidatService implements Serializable {

	private static final long serialVersionUID = 2775406662196454231L;

	public static class Aidat {

		private int dateIndex;
		private String aciklama;
		private boolean aciklamaVar = false;

		private UyeGrup uyeGrup;
		// private Tarife tarife;
		private List<TahakkukDetay> tahakkukDetayList;
		private BigDecimal tahakkukTutar;
		private BigDecimal tahsilTutar;
		private BigDecimal kalanTutar;
		private BigDecimal indirimTutar;
		private String odemeDurumu;
		private String bgColor;

		private boolean tarifeVar = false;
		private boolean odemeVar = false;
		private boolean odemeTam = false;

		public final static String TARIFE_YOK = "Tarife Yok";
		public final static String ODEME_YOK = "Ödeme Yok";
		public final static String EKSIK_ODEME = "Eksik Ödeme";
		public final static String FAZLA_ODEME = "Fazla Ödeme";
		public final static String ODEME_IPTAL = "Ödeme İptal";
		public final static String ODEME_AL = "Ödeme Al";
		public final static String INDIRIM_IPTAL = "İndirim İptal";

		public Aidat() {
		}

		public int getDateIndex() {
			return dateIndex;
		}

		public void setDateIndex(int dateIndex) {
			this.dateIndex = dateIndex;
		}

		public Aidat(String aciklama) {
			this.aciklama = aciklama;
			this.aciklamaVar = true;
		}

		public String getAciklama() {
			return aciklama;
		}

		public void setAciklama(String aciklama) {
			this.aciklama = aciklama;
		}

		public boolean isAciklamaVar() {
			return aciklamaVar;
		}

		public void setAciklamaVar(boolean aciklamaVar) {
			this.aciklamaVar = aciklamaVar;
		}

		// public Tarife getTarife() {
		// return tarife;
		// }

		// public void setTarife(Tarife tarife) {
		// this.tarife = tarife;
		// }

		public String getOdemeDurumu() {
			return odemeDurumu;
		}

		public UyeGrup getUyeGrup() {
			return uyeGrup;
		}

		public void setUyeGrup(UyeGrup uyeGrup) {
			this.uyeGrup = uyeGrup;
		}

		public void setOdemeDurumu(String odemeDurumu) {
			this.odemeDurumu = odemeDurumu;
		}

		public List<TahakkukDetay> getTahakkukDetayList() {
			return tahakkukDetayList;
		}

		public void setTahakkukDetayList(List<TahakkukDetay> tahakkukDetayList) {
			this.tahakkukDetayList = tahakkukDetayList;
		}

		public BigDecimal getTahakkukTutar() {
			return tahakkukTutar;
		}

		public void setTahakkukTutar(BigDecimal tahakkukTutar) {
			this.tahakkukTutar = tahakkukTutar;
		}

		public BigDecimal getTahsilTutar() {
			return tahsilTutar;
		}

		public void setTahsilTutar(BigDecimal tahsilTutar) {
			this.tahsilTutar = tahsilTutar;
		}

		public BigDecimal getKalanTutar() {
			return kalanTutar;
		}

		public void setKalanTutar(BigDecimal kalanTutar) {
			this.kalanTutar = kalanTutar;
		}

		public BigDecimal getIndirimTutar() {
			return indirimTutar;
		}

		public void setIndirimTutar(BigDecimal indirimTutar) {
			this.indirimTutar = indirimTutar;
		}

		public String getBgColor() {
			return bgColor;
		}

		public void setBgColor(String bgColor) {
			this.bgColor = bgColor;
		}

		public boolean isTarifeVar() {
			return tarifeVar;
		}

		public void setTarifeVar(boolean tarifeVar) {
			this.tarifeVar = tarifeVar;
		}

		public boolean isOdemeVar() {
			return odemeVar;
		}

		public void setOdemeVar(boolean odemeVar) {
			this.odemeVar = odemeVar;
		}

		public boolean isOdemeTam() {
			return odemeTam;
		}

		public void setOdemeTam(boolean odemeTam) {
			this.odemeTam = odemeTam;
		}

	}

	public class TabModel {
		private String tabTitle;
		private List<String> columns = new ArrayList<String>();
		private List<Aidat[]> values = new ArrayList<Aidat[]>();
		private SSSchedulerBase ssSchedulerBase;

		public String getTabTitle() {
			return tabTitle;
		}

		public void setTabTitle(String tabTitle) {
			this.tabTitle = tabTitle;
		}

		public List<String> getColumns() {
			return columns;
		}

		public void setColumns(List<String> columns) {
			this.columns = columns;
		}

		public List<Aidat[]> getValues() {
			return values;
		}

		public void setValues(List<Aidat[]> values) {
			this.values = values;
		}

		public SSSchedulerBase getSsSchedulerBase() {
			return ssSchedulerBase;
		}

		public void setSsSchedulerBase(SSSchedulerBase ssSchedulerBase) {
			this.ssSchedulerBase = ssSchedulerBase;
		}

	}

	@EJB
	private OrgTesisFacade orgTesisFacade;

	@EJB
	private GrupFacade grupFacade;

	@EJB
	private UyeGrupFacade uyeGrupFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{uyeService}")
	private UyeService uyeService;

	@ManagedProperty(value = "#{aidatTahakkukService}")
	private AidatTahakkukService aidatTahakkukService;

	@ManagedProperty(value = "#{tahsilatHome}")
	private TahsilatHome tahsilatHome;

	private TreeNode root;

	private TreeNode selectedNode;

	private List<TabModel> aidatTabList;

	private int selectedTabIndex;

	private List<String> columns;

	private List<Aidat[]> values;

	private Aidat selectedAidat;

	private TahakkukDetay selectedTahakkukDetay;

	private BigDecimal tahsilTutar;

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public UyeService getUyeService() {
		return uyeService;
	}

	public void setUyeService(UyeService uyeService) {
		this.uyeService = uyeService;
	}

	public AidatTahakkukService getAidatTahakkukService() {
		return aidatTahakkukService;
	}

	public void setAidatTahakkukService(AidatTahakkukService aidatTahakkukService) {
		this.aidatTahakkukService = aidatTahakkukService;
	}

	public TahsilatHome getTahsilatHome() {
		return tahsilatHome;
	}

	public void setTahsilatHome(TahsilatHome tahsilatHome) {
		this.tahsilatHome = tahsilatHome;
	}

	public TreeNode getRoot() {
		if (root == null) {
			root = new DefaultTreeNode(sessionObject.getSelectedOrganizasyon(), null);

			for (OrgTesis orgTesis : orgTesisFacade.listByOrganizasyon(sessionObject.getSelectedOrganizasyon().getId())) {
				TreeNode nodeOrgTesis = new DefaultTreeNode(orgTesis, root);

				for (Grup grup : grupFacade.listByOrgTesis(orgTesis.getId())) {
					new DefaultTreeNode(grup, nodeOrgTesis);
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
		if (selectedNode == null) {
			selectedNode = getRoot();
		}
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<TabModel> getAidatTabList() {
		if (aidatTabList == null) {
			aidatTabList = new ArrayList<TabModel>();

			TabModel tabModel;
			// tabModel = new TabModel();
			// tabModel.ssSchedulerBase = new SSSchedulerYear();
			// tabModel.setTabTitle(tabModel.ssSchedulerBase.getBaseName());
			// aidatTabList.add(tabModel);

			tabModel = new TabModel();
			tabModel.ssSchedulerBase = new SSSchedulerMonth();
			tabModel.setTabTitle(tabModel.ssSchedulerBase.getBaseName());
			aidatTabList.add(tabModel);

			// tabModel = new TabModel();
			// tabModel.ssSchedulerBase = new SSSchedulerWeek();
			// tabModel.setTabTitle(tabModel.ssSchedulerBase.getBaseName());
			// aidatTabList.add(tabModel);

			tabModel = new TabModel();
			tabModel.ssSchedulerBase = new SSSchedulerDay();
			tabModel.setTabTitle(tabModel.ssSchedulerBase.getBaseName());
			aidatTabList.add(tabModel);

			setSelectedTabIndex(0);

			fillTable();
		}
		return aidatTabList;
	}

	public void setAidatTabList(List<TabModel> aidatTab) {
		this.aidatTabList = aidatTab;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTab) {
		this.selectedTabIndex = selectedTab;
	}

	public TabModel getSelectedTabModel() {
		return getAidatTabList().get(getSelectedTabIndex());
	}

	public List<String> getColumns() {
		if (columns == null) {
			columns = getAidatTabList().get(getSelectedTabIndex()).getColumns();
		}
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<Aidat[]> getValues() {
		if (values == null) {
			values = getAidatTabList().get(getSelectedTabIndex()).getValues();
		}
		return values;
	}

	public void setValues(List<Aidat[]> values) {
		this.values = values;
	}

	public Aidat getSelectedAidat() {
		return selectedAidat;
	}

	public void setSelectedAidat(Aidat selectedAidat) {
		this.selectedAidat = selectedAidat;
	}

	public TahakkukDetay getSelectedTahakkukDetay() {
		return selectedTahakkukDetay;
	}

	public void setSelectedTahakkukDetay(TahakkukDetay selectedTahakkukDetay) {
		this.selectedTahakkukDetay = selectedTahakkukDetay;
	}

	public BigDecimal getTahsilTutar() {
		return tahsilTutar;
	}

	public void setTahsilTutar(BigDecimal tahsilTutar) {
		this.tahsilTutar = tahsilTutar;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		setSelectedNode(event.getTreeNode());
		if (event.getTreeNode().getData() instanceof Organizasyon) {
			event.getTreeNode().setSelected(false); // TODO hata var root
													// seçilince bu yüzden bu
													// satır eklendi.
		} else if (event.getTreeNode().getData() instanceof OrgTesis) {
		} else if (event.getTreeNode().getData() instanceof Grup) {
		}
		clearTable();
		fillTable();
	}

	public void onTabChange(TabChangeEvent event) {
		setSelectedTabIndex(((TabView) event.getComponent()).getActiveIndex());
		clearTable();
		fillTable();

		// getSelectedNode().setSelected(false);
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_INFO, null,
		// "Ağaçtan bir seçim yapınız"));
	}

	private void clearTable() {
		setColumns(null);
		setValues(null);
	}

	private void fillTable() {
		getSelectedTabModel().getColumns().clear();
		fillColumnList();
		getSelectedTabModel().getValues().clear();
		fillValueList();
	}

	public void fillColumnList() {
		if (getSelectedTabModel().getColumns().size() == 0) {
			getSelectedTabModel().getColumns().add("Tesis");
			getSelectedTabModel().getColumns().add("Grup");
			getSelectedTabModel().getColumns().add("Üye");
			for (int i = 0; i < getSelectedTabModel().getSsSchedulerBase().getDateList().size(); i++) {
				getSelectedTabModel().getColumns().add(getSelectedTabModel().getSsSchedulerBase().getDisplayName(i));
			}
		}
	}

	public void fillValueList() {
		if (getSelectedTabModel().getValues().size() == 0) {

			// 1-Tesis bazında döngü
			for (OrgTesis tes : filterOrgTesis()) {

				// 2-Grup bazında döngü
				for (Grup grup : filterGrup(tes)) {

					// 3-Üye Grup bazında döngü
					for (UyeGrup uyeGrup : uyeGrupFacade.listByGrup(grup)) {
						String uye = uyeService.uyeAdiEbeveynli(uyeGrup.getUye());

						// Dönem bazında döngü (Yıl,Ay,Hafta,Gün), tek satır
						// oluşturulur
						Aidat[] aidatRow = new Aidat[getColumns().size()];
						aidatRow[0] = new Aidat(tes.getAdi());
						aidatRow[1] = new Aidat(grup.getAdi());
						aidatRow[2] = new Aidat(uye);
						for (int i = 0; i < getColumns().size() - 3; i++) {
							aidatRow[i + 3] = getAidatTahakkukService().getRow(uyeGrup,
									getAidatTabList().get(getSelectedTabIndex()).getSsSchedulerBase(), i);
						}
						getValues().add(aidatRow);
					}
				}
			}
		}
	}

	public List<OrgTesis> filterOrgTesis() {
		List<OrgTesis> list = new ArrayList<OrgTesis>();
		Object nodeData = getSelectedNode().getData();
		if (nodeData.getClass().equals(Organizasyon.class)) {
			list = orgTesisFacade.listByOrganizasyon(((Organizasyon) nodeData).getId());
		} else if (nodeData.getClass().equals(OrgTesis.class)) {
			list.add((OrgTesis) nodeData);
		} else if (nodeData.getClass().equals(Grup.class)) {
			list.add(((Grup) nodeData).getOrgTesis());
		}
		return list;
	}

	public List<Grup> filterGrup(OrgTesis orgTes) {
		List<Grup> list = new ArrayList<Grup>();
		Object nodeData = getSelectedNode().getData();
		if (nodeData.getClass().equals(Organizasyon.class)) {
			list = grupFacade.listByOrgTesis(orgTes.getId());
		} else if (nodeData.getClass().equals(OrgTesis.class)) {
			list = grupFacade.listByOrgTesis(orgTes.getId());
		} else if (nodeData.getClass().equals(Grup.class)) {
			list.add((Grup) nodeData);
		}
		return list;
	}

	public void addAllColumnsToLeft() {
		getSelectedTabModel().getSsSchedulerBase().addFirstDate();
		fillTable();
	}

	public void addColumnToLeft() {
		getSelectedTabModel().getSsSchedulerBase().addPrevDate();
		fillTable();
	}

	public void addColumnToRight() {
		getSelectedTabModel().getSsSchedulerBase().addNextDate();
		fillTable();
	}

	public void addAllColumnsToRight() {
		getSelectedTabModel().getSsSchedulerBase().addLastDate();
		fillTable();
	}

	public void tahsilatEkranAc(Aidat aidat) {
		setSelectedAidat(aidat);
		if (aidat.getTahakkukDetayList().size() == 1) {
			setSelectedTahakkukDetay(aidat.getTahakkukDetayList().get(0));
			tahsilatHazirla(aidat, getSelectedTahakkukDetay());
		} else {
			setSelectedTahakkukDetay(null);
		}
	}

	public void tahsilatHazirla(Aidat aidat, TahakkukDetay detay) {
		if (aidat.getOdemeDurumu().equals(Aidat.ODEME_AL)) {
			setTahsilTutar(aidat.getTahakkukTutar());
		} else if (aidat.getOdemeDurumu().equals(Aidat.ODEME_IPTAL)) {
			setTahsilTutar(aidat.getTahakkukTutar());
		} else if (aidat.getOdemeDurumu().equals(Aidat.EKSIK_ODEME)) {
			setTahsilTutar(aidat.getTahakkukTutar().subtract(aidat.getTahsilTutar()));
		}
		tahsilatHome.sayfaLoad(detay);
	}

	public void tahsilatYap() throws Exception {
		try {
			tahsilatHome.getInstance().setTahsilTarihi(Calendar.getInstance().getTime());
			tahsilatHome.getInstance().setTahsilTutari(getTahsilTutar());
			tahsilatHome.getInstance().setOdemeSekli(OdemeSekli.NAKIT);
			// tahsilatHome.getInstance().setIskonto(getSelectedAidat().getTahakkukTutar().subtract(getTahsilTutar()));
			tahsilatHome.getInstance().setTahsilatTipi(TahsilatTipi.TAHSILAT);

			tahsilatHome.tahsilatYap();

			getAidatTahakkukService().getAidatRowMap().remove(
					getSelectedAidat().getUyeGrup().getId()
							+ getSelectedTabModel().getSsSchedulerBase().getDisplayName(
									getSelectedAidat().getDateIndex()));

			fillTable();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", this.getClass().toString()));
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", e.getMessage()));
		}
	}

	public void tahsilatIptalEt(Tahsilat tahsilat) throws Exception {
		tahsilatHome.tahsilatIptalEt(tahsilat);

		getAidatTahakkukService().getAidatRowMap().remove(
				getSelectedAidat().getUyeGrup().getId()
						+ getSelectedTabModel().getSsSchedulerBase().getDisplayName(getSelectedAidat().getDateIndex()));

		fillTable();
	}

	public void borcuKapat() throws Exception {
		tahsilatHome.getInstance().setTahsilTarihi(Calendar.getInstance().getTime());
		tahsilatHome.getInstance().setTahsilTutari(BigDecimal.ZERO);
		tahsilatHome.getInstance().setOdemeSekli(OdemeSekli.NAKIT);
		tahsilatHome.getInstance().setIndirimTutari(getTahsilTutar());
		tahsilatHome.getInstance().setTahsilatTipi(TahsilatTipi.INDIRIM);

		tahsilatHome.kalanTutarSifirla();

		getAidatTahakkukService().getAidatRowMap().remove(
				getSelectedAidat().getUyeGrup().getId()
						+ getSelectedTabModel().getSsSchedulerBase().getDisplayName(getSelectedAidat().getDateIndex()));

		fillTable();
	}

	public List<SelectItem> aidatTahakkukDetaylari(Aidat aidat) {

		List<SelectItem> detaySelect = new ArrayList<SelectItem>();
		if (aidat == null) {
			return detaySelect;
		}
		List<TahakkukDetay> list = aidat.getTahakkukDetayList();

		for (TahakkukDetay t : list) {
			SelectItem si = new SelectItem(t, new UtilDate(t.getVadeTarihi()).toString("dd MMM yyyy") + " : "
					+ t.getTutar());
			detaySelect.add(si);
		}

		return detaySelect;
	}
}
