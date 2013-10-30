package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.Kart;

@Local
public interface KartFacade {

	public void persist(Kart kart);

	public void merge(Kart kart);

	public void remove(Kart kart);

	public void delete(Kart kart);

	public Kart findById(long id);

	public List<Kart> listByOrganizasyon(Long organizasyonId);

}