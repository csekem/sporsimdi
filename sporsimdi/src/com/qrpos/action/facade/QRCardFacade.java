package com.qrpos.action.facade;

import java.util.List;

import javax.ejb.Local;

import com.qrpos.model.entity.QRCard;

@Local
public interface QRCardFacade {

	public void persist(QRCard qrcard);

	public void merge(QRCard qrcard);

	public void remove(QRCard qrcard);

	public void delete(QRCard qrcard);

	public QRCard findById(long id);

	public QRCard findByNo(String no);

	public List<QRCard> listByOrganizasyon(Long organizasyonId);

}