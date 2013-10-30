package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.Fis;
import com.qrpos.model.json.FisJson;
import com.qrpos.model.json.FisKaydetSonucJson;
import com.qrpos.model.json.view.FisJsonView;

@Local
public interface FisFacade {

	public void persist(Fis fis);

	public void merge(Fis fis);

	public void remove(Fis fis);

	public void delete(Fis fis);

	public Fis findById(long id);

	public List<Fis> listByQRCard(String qrCardNo);

	public FisKaydetSonucJson fisKaydet(FisJson fisJson);

	public FisKaydetSonucJson fisIptal(FisJson fisJson);

	public FisJsonView getFisJsonView(long id);

	public List<FisJsonView> getFisJsonView(String qrCardNo);
}