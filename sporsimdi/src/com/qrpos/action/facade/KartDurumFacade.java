package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.KartDurum;

@Local
public interface KartDurumFacade {

	public void persist(KartDurum kartDurum);

	public void merge(KartDurum kartDurum);

	public void remove(KartDurum kartDurum);

	public void delete(KartDurum kartDurum);

	public KartDurum findById(long id);

	public List<KartDurum> listByOrganizasyon(Long organizasyonId);

	public KartDurum findByQrCard(String no);

}