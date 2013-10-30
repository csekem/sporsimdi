package com.sporsimdi.action.list;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sporsimdi.action.facade.GrupFacade;
import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.action.facade.KisiIliskiFacade;
import com.sporsimdi.action.facade.TelefonFacade;
import com.sporsimdi.action.facade.UyeFacade;
import com.sporsimdi.action.facade.UyeGrupFacade;
import com.sporsimdi.action.home.TahakkukHome;
import com.sporsimdi.action.service.KisiIliskiService;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.action.util.UtilDate;
import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kisi;
import com.sporsimdi.model.entity.KisiIliski;
import com.sporsimdi.model.entity.Telefon;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;
import com.sporsimdi.model.type.Cinsiyet;
import com.sporsimdi.model.type.TelefonTipi;
import com.sporsimdi.model.type.YakinlikDerecesi;

@ManagedBean(name = "uyeListesi")
@ViewScoped
public class UyeListesi extends ListBean<Uye> implements ListBeanInterface {

	private static final long serialVersionUID = -6269616049556437442L;

	@EJB
	private UyeFacade uyeFacade;
	
	@EJB
	private GrupFacade grupFacade;
	
	@EJB
	private UyeGrupFacade uyeGrupFacade;
	
	@EJB
	private KisiIliskiFacade kisiIliskiFacade;

	@EJB
	private KisiFacade kisiFacade;

	@EJB
	private TelefonFacade telefonFacade;

	@EJB
	private IsletmeFacade isletmeFacade;

	@ManagedProperty(value = "#{tahakkukHome}")
	private TahakkukHome tahakkukHome;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
	
	@ManagedProperty(value = "#{kisiIliskiService}")
	private KisiIliskiService kisiIliskiService;

	private UyeGrup uyeGrup;
	
	private KisiIliski anneIliski;
	
	private KisiIliski babaIliski;
	
	private Telefon anneTel;
	
	private Telefon babaTel;
	
	private boolean eskiUyeler = false;
	
	private boolean yeniUye =false;
	
	private Boolean uyeGrupta;

	private HashMap<Long, List<Uye>> mapDigerUyeler = new HashMap<Long, List<Uye>>();

	private List<Uye> listDigerUyeler;
	
	private int grupCikisAySayi;
	
	public TahakkukHome getTahakkukHome() {
		return tahakkukHome;
	}

	public void setTahakkukHome(TahakkukHome tahakkukHome) {
		this.tahakkukHome = tahakkukHome;
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public KisiIliskiService getKisiIliskiService() {
		return kisiIliskiService;
	}

	public void setKisiIliskiService(KisiIliskiService kisiIliskiService) {
		this.kisiIliskiService = kisiIliskiService;
	}

	public KisiIliski getAnneIliski() throws Exception {
		if (anneIliski == null) {
			anneIliski = kisiIliskiFacade.getByKisiYakinlik(getInstance(), YakinlikDerecesi.ANNE);
			if (anneIliski==null) {
				anneIliski = new KisiIliski();
				anneIliski.setKisi(getInstance());
				anneIliski.setYakinlikDerecesi(YakinlikDerecesi.ANNE);
				Kisi anne = new Kisi();
				anne.setSoyad(getInstance().getSoyad());
				anne.setCinsiyet(Cinsiyet.KIZ);
				anneIliski.setIliskiliKisi(anne);
			}
		}
		return anneIliski;
	}

	public UyeGrup getUyeGrup() throws Exception {
		if (uyeGrup == null) {
			uyeGrup = uyeGrupFacade.getByGrupUye((Grup) getModelSuper(), getInstance());
			if (uyeGrup == null) {
				uyeGrup = new UyeGrup();
				uyeGrup.setUye(getInstance());
				Grup grup = (Grup) getModelSuper();
				uyeGrup.setGrup(grup);
				UtilDate girisTarih = new UtilDate();
				uyeGrup.setGirisTarihi(girisTarih.getTime());
				if (grup.getTarihBitis()!=null) {
					setGrupCikisAySayi(girisTarih.getMonthCount(grup.getTarihBitis()));
					uyeGrup.setCikisTarihi(grup.getTarihBitis());
				} else {
					setGrupCikisAySayi(1);
					//UtilDate cikisTarih = new UtilDate(girisTarih.add(Calendar.MONTH, 1));
					//uyeGrup.setCikisTarihi(cikisTarih.add(Calendar.DATE, -1));					
				}
			}
		}
		return uyeGrup;
	}

	public void setUyeGrup(UyeGrup uyeGrup) {
		this.uyeGrup = uyeGrup;
	}

	public void setAnneIliski(KisiIliski anneIliski) {
		this.anneIliski = anneIliski;
	}

	public KisiIliski getBabaIliski() throws Exception {
		if (babaIliski == null) {
			babaIliski = kisiIliskiFacade.getByKisiYakinlik(getInstance(), YakinlikDerecesi.BABA);
			if (babaIliski == null) {
				babaIliski = new KisiIliski();
				babaIliski.setKisi(getInstance());
				babaIliski.setYakinlikDerecesi(YakinlikDerecesi.BABA);
				Kisi baba = new Kisi();
				baba.setSoyad(getInstance().getSoyad());
				baba.setCinsiyet(Cinsiyet.ERKEK);
				babaIliski.setIliskiliKisi(baba);
			}
		}
		return babaIliski;
	}

	public void setBabaIliski(KisiIliski babaIliski) {
		this.babaIliski = babaIliski;
	}

	public Telefon getAnneTel() throws Exception {
		if (anneTel == null) {
			anneTel = telefonFacade.getByModelIdTip(getAnneIliski().getIliskiliKisi().getId(), TelefonTipi.CEP);
			if (anneTel == null) {
				anneTel = new Telefon();
				anneTel.setTelefonTipi(TelefonTipi.CEP);
				anneTel.setModelId(getAnneIliski().getIliskiliKisi().getId());
			}
		}
		return anneTel;
	}

	public void setAnneTel(Telefon anneTel) {
		this.anneTel = anneTel;
	}

	public Telefon getBabaTel() throws Exception {
		if (babaTel == null) {
			babaTel = telefonFacade.getByModelIdTip(getBabaIliski().getIliskiliKisi().getId(), TelefonTipi.CEP);
			if (babaTel == null) {
				babaTel = new Telefon();
				babaTel.setTelefonTipi(TelefonTipi.CEP);
				babaTel.setModelId(getBabaIliski().getIliskiliKisi().getId());
			}
		}
		return babaTel;
	}

	public void setBabaTel(Telefon babaTel) {
		this.babaTel = babaTel;
	}

	public boolean isEskiUyeler() {
		return eskiUyeler;
	}

	public void setEskiUyeler(boolean eskiUyeler) {
		this.eskiUyeler = eskiUyeler;
	}

	public boolean isYeniUye() {
		yeniUye = getNewerItem() && !isEskiUyeler();
		return yeniUye;
	}

	public void setYeniUye(boolean yeniUye) {
		this.yeniUye = yeniUye;
	}


	public Boolean getUyeGrupta() throws Exception {
		if (uyeGrupta == null || getInstance()!=null) {
			UyeGrup uyeG = uyeGrupFacade.getByGrupUye((Grup) getModelSuper(), getInstance());
			if (uyeG==null) {
				uyeGrupta = false;
			} else {
				uyeGrupta = true;
			}
		}
		return uyeGrupta;
	}

	public void setUyeGrupta(Boolean uyeGrupta) {
		this.uyeGrupta = uyeGrupta;
	}

	public int getGrupCikisAySayi() {
		return grupCikisAySayi;
	}

	public void setGrupCikisAySayi(int grupCikisAySayi) {
		this.grupCikisAySayi = grupCikisAySayi;
	}

	@Override
	public void fillSuperId() {
		setStrSuperId("sporsimdi.com.grupId");
		setModelSuper(grupFacade.findById(getSuperId()));
	}

	@Override
	public void fillList() {
		setList(uyeFacade.listByGrup(getSuperId()));
	}

	@Override
	public void fillSaving() {	
		UyeGrup uyeG = null;
		try {
			uyeG = uyeGrupFacade.getByGrupUye((Grup) getModelSuper(), getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (uyeG == null) {
			List<UyeGrup> listUyeGrup = uyeGrupFacade.listByUye(getInstance());
			try {
				listUyeGrup.add(getUyeGrup());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getInstance().setGrupListesi(listUyeGrup);
		}
		
		Isletme isletme = null;
		try {
			isletme = isletmeFacade.getByIsletmeKisi(sessionObject.getSelectedIsletme(), getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isletme ==null) {
			List<Isletme> listIsletme = isletmeFacade.listByKisi(getInstance());
			listIsletme.add(sessionObject.getSelectedIsletme());
			getInstance().setIsletmeListesi(listIsletme);
		}
	}

	@Override
	public String save() {
		fillSaving();

		if (isManaged()) {
			Uye tmpUye = (Uye) getEntityManagerDao().updateObject(getInstance());
			setInstance(uyeFacade.findByIdEager(tmpUye.getId()));
		} else {
			getEntityManagerDao().createObject(getInstance());
		}
		
		
		//uyeGrupFacade.merge(getUyeGrup());

		try {
			saveIliski(anneIliski);
			saveIliski(babaIliski);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setDeletion(false);
		setUyeGrupta(null);

		fillList();
		
		updateInstance(getInstance());
		return "saved";
	}

	@Override
	public String delete() {
		try {
			getUyeGrup().setCikisTarihi(Calendar.getInstance().getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			uyeGrupFacade.delete(getUyeGrup());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setNewerItem(false);
		setDeletion(false);
		clearInstance();

		mapDigerUyeler.remove(((Grup) getModelSuper()).getId());

		fillList();

		// TODO tahakkuk sil
		return "deleted";
	}
	
	public void eskiUyeAktar(Uye eskiUye) throws Exception {
		setInstance(eskiUye);
		tahakkukHome.sayfaLoad(null);
		clearIliskiler();
		setEskiUyeler(false);
	}

	private void clearIliskiler() {
		anneIliski = null;
		babaIliski = null;
		anneTel = null;
		babaTel = null;
	}

	public void soyadAktar() throws Exception {
		getAnneIliski().getIliskiliKisi().setSoyad(getInstance().getSoyad());
		getBabaIliski().getIliskiliKisi().setSoyad(getInstance().getSoyad());
	}


	@Override
	public void updateInstance(Uye t) {
		super.updateInstance(t);
		
		uyeGrup = null;
		try {
			if (getUyeGrup().getId()!=null) {
				tahakkukHome.sayfaLoad(getUyeGrup());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clearIliskiler();
		setUyeGrupta(null);
		
	}

	@Override
	public void deleteInstance(Uye t) {
		super.deleteInstance(t);
		
		uyeGrup = null;
		try {
			if (getUyeGrup().getId()!=null) {
				tahakkukHome.sayfaLoad(getUyeGrup());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clearIliskiler();
		setUyeGrupta(null);
	}

	@Override
	public void disableNewerItem() {
		setEskiUyeler(false);
		setUyeGrupta(null);
		uyeGrup = null;
		super.disableNewerItem();
	}

	@Override
	public void enableNewerItem() {
		mapDigerUyeler.remove(((Grup) getModelSuper()).getId());
		uyeGrup = null;
		clearIliskiler();
		
		if (getListDigerUyeler().size()>0) {
			setEskiUyeler(true);			
		}
		super.enableNewerItem();
	}

	public String saveIliski(KisiIliski iliski) throws Exception {
		if (iliski==null || iliski.getIliskiliKisi().getAd()==null || iliski.getIliskiliKisi().getAd().trim().equals("")) {
			return "failed";
		}
		if (iliski.getIliskiliKisi().getId()==null) {
			kisiFacade.persist(iliski.getIliskiliKisi());
		} else {
			kisiFacade.merge(iliski.getIliskiliKisi());
		}
		
		if (iliski.getId()==null) {
			kisiIliskiFacade.persist(iliski);
		} else {
			kisiIliskiFacade.merge(iliski);
		}
		
		if (iliski.getYakinlikDerecesi().equals(YakinlikDerecesi.ANNE)) {
			anneTel.setModelId(getAnneIliski().getIliskiliKisi().getId());
			if (anneTel.getId()==null) {
				telefonFacade.persist(anneTel);
			} else {
				telefonFacade.merge(anneTel);
			}
		} else if (iliski.getYakinlikDerecesi().equals(YakinlikDerecesi.BABA)) {
			babaTel.setModelId(getBabaIliski().getIliskiliKisi().getId());
			if (babaTel.getId()==null) {
				telefonFacade.persist(babaTel);
			} else {
				telefonFacade.merge(babaTel);
			}
		}
		
		kisiIliskiService.clearMapAnneBaba(iliski.getKisi().getId());
		
		return "success";
	}

	public void eskiUyeDegil() {
		setEskiUyeler(false);
		super.enableNewerItem();
	}

	public HashMap<Long, List<Uye>> getMapDigerUyeler() {
		return mapDigerUyeler;
	}

	public void setMapDigerUyeler(HashMap<Long, List<Uye>> mapDigerUyeler) {
		this.mapDigerUyeler = mapDigerUyeler;
	}

	public List<Uye> getListDigerUyeler() {
		Grup grup = (Grup) getModelSuper();
    	if (grup!=null && !mapDigerUyeler.containsKey(grup.getId())) {
    		listDigerUyeler = uyeFacade.listByIsletme(sessionObject.getSelectedIsletme());
    		List<Uye> isletmeUyeler = uyeFacade.listByIsletme(sessionObject.getSelectedIsletme());
    		List<Uye> listGrupUye = uyeFacade.listByGrup(getSuperId());
    		for (Uye uye : isletmeUyeler) {
				for (Uye grupUye : listGrupUye) {
					if (uye.equals(grupUye)) {
						listDigerUyeler.remove(uye);
						break;
					}
				}
			}
    		mapDigerUyeler.put(grup.getId(), listDigerUyeler);
    	}
    	if (grup != null) {
    		listDigerUyeler = mapDigerUyeler.get(grup.getId());
    	} else {
    		listDigerUyeler = null;
    	}
		return listDigerUyeler;
	}

	public void setListDigerUyeler(List<Uye> listDigerUyeler) {
		this.listDigerUyeler = listDigerUyeler;
	}

	public void cikisTarihiBelirle() throws Exception {
		UtilDate cikisTarih = new UtilDate(getUyeGrup().getGirisTarihi());
		cikisTarih.addMonth(getGrupCikisAySayi());
		uyeGrup.setCikisTarihi(cikisTarih.getTime());					
	}

}
