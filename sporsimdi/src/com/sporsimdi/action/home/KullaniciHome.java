package com.sporsimdi.action.home;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import com.sporsimdi.action.facade.IsletmeFacade;
import com.sporsimdi.action.facade.KisiFacade;
import com.sporsimdi.action.facade.KullaniciFacade;
import com.sporsimdi.action.facade.RolFacade;
import com.sporsimdi.action.service.SessionObject;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Kullanici;
import com.sporsimdi.model.entity.Rol;
import com.sporsimdi.model.entity.Sahip;

@ManagedBean(name = "kullaniciHome")
@ViewScoped
public class KullaniciHome extends HomeBean<Kullanici> {

	private static final long serialVersionUID = 5528752636982509999L;
	
	@EJB
	private KullaniciFacade kullaniciFacade;

	@EJB
	private RolFacade rolFacade;

	@EJB
	private KisiFacade kisiFacade;

	@EJB
	private IsletmeFacade isletmeFacade;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;
	
	private String sifreYeni;
	
	private String sifreTekrar;
	
	private Sahip sahip;
	
	private DualListModel<Isletme> isletmeler;
	

	public void sayfaLoad() {
		super.sayfaLoad();
		FacesContext fc = FacesContext.getCurrentInstance();
		String pKullaniciId = getKullaniciIdParam(fc);

		if (pKullaniciId != null && !pKullaniciId.isEmpty()) {
			setInstance(kullaniciFacade.findById(Long.valueOf(pKullaniciId)));
			setSahip((Sahip)getInstance().getKisi());
		}
	}

	public String getKullaniciIdParam(FacesContext fc) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("kullaniciId");
	}

	@Override
	public String save() {
		if (isManaged()) {
			Kullanici eskiKullanici = kullaniciFacade.findById(getInstance().getId());
			Rol rol = rolFacade.findByKullanici(eskiKullanici.getKullanici());
			rol.setKullanici(getInstance().getKullanici());
			rolFacade.merge(rol);
			
			sahip.setIsletmeListesi(isletmeler.getTarget());
			kisiFacade.merge(sahip);			
		} else {
			Rol rol = new Rol();
			rol.setKullanici(getInstance().getKullanici());
			rol.setRol("user");
			rolFacade.persist(rol);

			sahip.setIsletmeListesi(isletmeler.getTarget());
			kisiFacade.persist(sahip);
		}
			
		getInstance().setKisi(sahip);
				
		String returnStr = super.save();
		
		return returnStr;
	}

	public String sifreDegistir() {
		getInstance().setSifre(getSifreYeni());
		return super.save();
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public String getSifreYeni() {
		return sifreYeni;
	}

	public void setSifreYeni(String sifreYeni) {
		this.sifreYeni = sifreYeni;
	}

	public String getSifreTekrar() {
		return sifreTekrar;
	}

	public void setSifreTekrar(String sifreTekrar) {
		this.sifreTekrar = sifreTekrar;
	}

	public Sahip getSahip() {
		if (sahip==null) {
			sahip = new Sahip();
		}
		return sahip;
	}

	public void setSahip(Sahip sahip) {
		this.sahip = sahip;
	}

	public DualListModel<Isletme> getIsletmeler() {
		if (isletmeler==null) {
			List<Isletme> listAll = isletmeFacade.listAll();
			List<Isletme> listByKisi = isletmeFacade.listByKisi(getSahip());
			
			for (Isletme isletme : listByKisi) {
				listAll.remove(isletme);
			}
			
			isletmeler = new DualListModel<Isletme>(listAll, listByKisi);
		}
		return isletmeler;
	}

	public void setIsletmeler(DualListModel<Isletme> isletmeler) {
		this.isletmeler = isletmeler;
	}

}