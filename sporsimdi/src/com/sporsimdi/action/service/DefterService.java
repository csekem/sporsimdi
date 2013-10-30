package com.sporsimdi.action.service;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sporsimdi.action.facade.BakiyeFacade;
import com.sporsimdi.action.facade.DefterFacade;
import com.sporsimdi.action.facade.KasaFacade;
import com.sporsimdi.action.facade.OrgTesisFacade;
import com.sporsimdi.action.facade.OrganizasyonFacade;
import com.sporsimdi.action.facade.TahsilatFacade;
import com.sporsimdi.model.base.ExtendedModel;
import com.sporsimdi.model.entity.Bakiye;
import com.sporsimdi.model.entity.Defter;
import com.sporsimdi.model.entity.Kasa;
import com.sporsimdi.model.entity.OrgTesis;
import com.sporsimdi.model.entity.Organizasyon;
import com.sporsimdi.model.entity.Tahsilat;

@ManagedBean(name = "defterService")
@ViewScoped
public class DefterService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private DefterFacade defterFacade;

	@EJB
	private BakiyeFacade bakiyeFacade;

	@EJB
	private TahsilatFacade tahsilatFacade;

	@EJB
	private OrganizasyonFacade organizasyonFacade;

	@EJB
	private OrgTesisFacade orgTesisFacade;

	@EJB
	private KasaFacade kasaFacade;

	private List<Defter> defterler;

	private String dunkuBakiye;

	private String bugunBakiye;

	private String bugunGider;

	private String bugunGelir;

	private String yarinBakiye;

	private String sonBakiye;

	private Defter defter;

	private Date tarih;

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

	public String getYarinBakiye() {
		return yarinBakiye;
	}

	public void setYarinBakiye(String yarinBakiye) {
		this.yarinBakiye = yarinBakiye;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public DefterService() {
	}

	public String listele() {
		defterler = defterFacade.listAllByTarih(getTarih(), getSelectedKasa());

		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr"));
		numberFormat.setCurrency(Currency.getInstance("TRL"));

		Calendar calDun = Calendar.getInstance(new Locale("tr"));
		calDun.setTime(getTarih());
		calDun.add(Calendar.DATE, -1);
		List<Bakiye> bakiyeList = bakiyeFacade.listAllByTarih(calDun.getTime(), getSelectedKasa());

		double dBakiye = 0;
		if (bakiyeList.size() > 0) {
			dBakiye = bakiyeList.get(0).getBakiye().doubleValue();
		}
		dunkuBakiye = numberFormat.format(dBakiye);

		double bGider = 0;
		double bGelir = 0;
		for (Defter df : defterler) {
			bGider = bGider + df.getGider().doubleValue();
			bGelir = bGelir + df.getGelir().doubleValue();
		}

		bugunGider = numberFormat.format(bGider);
		bugunGelir = numberFormat.format(bGelir);
		bugunBakiye = numberFormat.format(bGelir - bGider);

		double yBakiye = dBakiye + bGelir - bGider;
		yarinBakiye = numberFormat.format(yBakiye);

		return "success";
	}

	public List<Defter> getDefterler() {
		if (defterler == null) {
			listele();
		}
		return defterler;
	}

	public void setDefterler(List<Defter> defterler) {
		this.defterler = defterler;
	}

	public Defter getDefter() {
		return defter;
	}

	public void setDefter(Defter defter) {
		this.defter = defter;
	}

	public Date getTarih() {
		if (tarih == null) {
			tarih = Calendar.getInstance().getTime();
		}
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public String getDunkuBakiye() {
		return dunkuBakiye;
	}

	public void setDunkuBakiye(String dunkuBakiye) {
		this.dunkuBakiye = dunkuBakiye;
	}

	public String getBugunBakiye() {
		return bugunBakiye;
	}

	public void setBugunBakiye(String bugunBakiye) {
		this.bugunBakiye = bugunBakiye;
	}

	public String getBugunGider() {
		return bugunGider;
	}

	public void setBugunGider(String bugunGider) {
		this.bugunGider = bugunGider;
	}

	public String getBugunGelir() {
		return bugunGelir;
	}

	public void setBugunGelir(String bugunGelir) {
		this.bugunGelir = bugunGelir;
	}

	public String getSonBakiye() {
		Bakiye sonBak = bakiyeFacade.findLatest(getSelectedKasa());
		double bakiye = 0;
		if (sonBak.getBakiye() != null) {
			bakiye = sonBak.getBakiye().doubleValue();
		}

		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr"));
		numberFormat.setCurrency(Currency.getInstance("TRL"));

		sonBakiye = numberFormat.format(bakiye);
		return sonBakiye;
	}

	public void setSonBakiye(String sonBakiye) {
		this.sonBakiye = sonBakiye;
	}

	public String yonlendirReference(ExtendedModel extendedModel, long referenceId) {
		super.yonlendir(new Long(0));
		if (extendedModel instanceof Tahsilat) {
			return "/menu/tahsilatYap?faces-redirect=true&amp;includeViewParams=true&amp;tahakkukDetayId="
					+ referenceId;
		} else {
			return "";
		}
	}

	public void onDateSelect(SelectEvent event) {
		listele();
	}

	public void gunEkle() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTarih());
		cal.add(Calendar.DATE, 1);
		setTarih(cal.getTime());
		listele();
	}

	public void gunCikar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTarih());
		cal.add(Calendar.DATE, -1);
		setTarih(cal.getTime());
		listele();
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

			listele();

			selectedLevel = "KASA";
		}
	}

}
