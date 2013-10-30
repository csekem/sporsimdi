package com.qrpos.model.json.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qrpos.model.entity.Fis;
import com.qrpos.model.entity.FisDetay;

public class FisJsonView implements Serializable {

	private static final long serialVersionUID = -5376633737912498989L;

	private Long id;

	private Long uyeId;
	private String uyeAdSoyad;

	private BigDecimal toplamTutar;

	private BigDecimal kdvTutar;

	private Date createDate;

	private List<FisDetayJsonView> detayListesi = new ArrayList<FisDetayJsonView>();

	public Long getId() {
		return id;
	}

	public FisJsonView(Fis fis) {
		super();
		this.id = fis.getId();
		this.uyeId = fis.getKartDurum().getKart().getUye().getId();
		this.uyeAdSoyad = fis.getKartDurum().getKart().getUye().getAdSoyad().toLowerCase();
		this.toplamTutar = fis.getToplamTutar();
		this.kdvTutar = fis.getKdvTutar();
		this.createDate = fis.getCreateDate();
		for (FisDetay fisDetay : fis.getFisDetayListesi()) {

			detayListesi.add(new FisDetayJsonView(fisDetay));
		}

	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getToplamTutar() {
		return toplamTutar;
	}

	public void setToplamTutar(BigDecimal toplamTutar) {
		this.toplamTutar = toplamTutar;
	}

	public BigDecimal getKdvTutar() {
		return kdvTutar;
	}

	public void setKdvTutar(BigDecimal kdvTutar) {
		this.kdvTutar = kdvTutar;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<FisDetayJsonView> getDetayListesi() {
		return detayListesi;
	}

	public void setDetayListesi(List<FisDetayJsonView> detayListesi) {
		this.detayListesi = detayListesi;
	}

	public Long getUyeId() {
		return uyeId;
	}

	public void setUyeId(Long uyeId) {
		this.uyeId = uyeId;
	}

	public String getUyeAdSoyad() {
		return uyeAdSoyad;
	}

	public void setUyeAdSoyad(String uyeAdSoyad) {
		this.uyeAdSoyad = uyeAdSoyad;
	}

}
