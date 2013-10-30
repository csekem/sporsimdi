package com.sporsimdi.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.sporsimdi.model.entity.Grup;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.entity.UyeGrup;

@Local
public interface UyeGrupFacade {

	public void persist(UyeGrup uyeGrup);
	public void merge(UyeGrup uyeGrup);
	public void remove(UyeGrup uyeGrup);
	public void delete(UyeGrup uyeGrup);

	public UyeGrup findById(long id);

	public UyeGrup getByGrupUye(Grup grup, Uye uye) throws Exception;

	public List<UyeGrup> listAll();
	public List<UyeGrup> listByUye(Uye uye);
	public List<UyeGrup> listByGrup(Grup grup);
	public List<UyeGrup> listLikeUye(Grup grup, String ad);
	
	public Long countByGrup(Grup grup);

}