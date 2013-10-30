package com.sporsimdi.action.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Uye;
import com.sporsimdi.model.type.Status;

@Local
public interface UyeFacade {

	public void persist(Uye uye);

	public void merge(Uye uye);

	public void remove(Uye uye);

	public void delete(Uye uye);

	public Uye findById(long id);

	public List<Uye> listAll();

	public List<Uye> listByIsletme(Isletme isletme);

	public List<Uye> listByGrup(Long grupId);

	public Long getCount();

	public List<Uye> getByStatus(Status status);

	public List<Uye> getUye(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters);

	public Uye findByIdEager(long id);

	public Uye getWithIsletmeByIdEager(long id);

}