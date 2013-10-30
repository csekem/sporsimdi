package com.sporsimdi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AidatOLD implements Serializable {

	private static final long serialVersionUID = 8986161490957095179L;

	private OrgTesis orgTesis;
	private Grup grup;
	private UyeGrup uyeGrup;
	private Uye uye;
	private String uyeString;
	private Tarife tarife;

	private TahakkukDetay tahakkukDetay;
	private BigDecimal tahakkukTutar;
	private BigDecimal tahsilTutar;
	private BigDecimal kalanTutar;
	private BigDecimal indirimTutar;
	
	private String bgColor;
	private String odemeDurumu;
	
	private boolean tarifeVar=false;
	private boolean odemeVar=false;
	private boolean odemeTam=false;
	
	private boolean tahsilatDetayBak;
	private boolean odemeAl;
	private boolean odemelerBak;
	private boolean kalanSifirla;
	
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
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public TahakkukDetay getTahakkukDetay() {
		return tahakkukDetay;
	}
	public void setTahakkukDetay(TahakkukDetay tahakkukDetay) {
		this.tahakkukDetay = tahakkukDetay;
	}
	public boolean isOdemeAl() {
		if (getKalanTutar().compareTo(BigDecimal.ZERO)==0) {
			odemeAl = true;
		} else {
			odemeAl = false;			
		}
		return odemeAl;
	}
	public void setOdemeAl(boolean odemeAl) {
		this.odemeAl = odemeAl;
	}
	public boolean isOdemelerBak() {
		if (getTahsilTutar().compareTo(BigDecimal.ZERO)!=0) {
			odemelerBak = true;
		} else {
			odemelerBak = false;			
		}
		return odemelerBak;
	}
	public void setOdemelerBak(boolean odemelerBak) {
		this.odemelerBak = odemelerBak;
	}
	public boolean isKalanSifirla() {
		if (getKalanTutar().compareTo(BigDecimal.ZERO)!=0) {
			kalanSifirla = true;
		} else {
			kalanSifirla = false;			
		}
		return kalanSifirla;
	}
	public void setKalanSifirla(boolean kalanSifirla) {
		this.kalanSifirla = kalanSifirla;
	}
	public boolean isTahsilatDetayBak() {
		if (getTahakkukTutar().compareTo(BigDecimal.ZERO)!=0) {
			tahsilatDetayBak = false;
		} else {
			tahsilatDetayBak = true;			
		}
		return tahsilatDetayBak;
	}
	public void setTahsilatDetayBak(boolean tahsilatDetayBak) {
		this.tahsilatDetayBak = tahsilatDetayBak;
	}
	public BigDecimal getIndirimTutar() {
		return indirimTutar;
	}
	public void setIndirimTutar(BigDecimal indirimTutar) {
		this.indirimTutar = indirimTutar;
	}
	public Uye getUye() {
		return uye;
	}
	public void setUye(Uye uye) {
		this.uye = uye;
	}
	public OrgTesis getOrgTesis() {
		return orgTesis;
	}
	public void setOrgTesis(OrgTesis orgTesis) {
		this.orgTesis = orgTesis;
	}
	public Grup getGrup() {
		return grup;
	}
	public void setGrup(Grup grup) {
		this.grup = grup;
	}
	public UyeGrup getUyeGrup() {
		return uyeGrup;
	}
	public void setUyeGrup(UyeGrup uyeGrup) {
		this.uyeGrup = uyeGrup;
	}
	public String getUyeString() {
		return uyeString;
	}
	public void setUyeString(String uyeString) {
		this.uyeString = uyeString;
	}
	public Tarife getTarife() {
		return tarife;
	}
	public void setTarife(Tarife tarife) {
		this.tarife = tarife;
	}
	public String getOdemeDurumu() {
		return odemeDurumu;
	}
	public void setOdemeDurumu(String odemeDurumu) {
		this.odemeDurumu = odemeDurumu;
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