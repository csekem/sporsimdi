package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.Urun;

@Local
public interface UrunFacade {

	public void persist(Urun urun);

	public void merge(Urun urun);

	public void remove(Urun urun);

	public void delete(Urun urun);

	public Urun findById(long id);

	public List<Urun> listByKasa(Long kasaId);

	public int findNextSira(long kasaId);

}