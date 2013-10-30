package com.sporsimdi.action.facadeBean;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sporsimdi.action.facade.MenuFacade;
import com.sporsimdi.model.entity.Isletme;
import com.sporsimdi.model.entity.Menu;
import com.sporsimdi.model.type.OrganizasyonTipi;
import com.sporsimdi.model.type.Status;

@Stateless
public class MenuFacadeBean implements MenuFacade {

	@PersistenceContext(unitName = "sporsimdi")
	EntityManager entityManager;

	public MenuFacadeBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getByStatus(Status status) {
		Query q = entityManager.createQuery(
				"select k from Menu k where k.status =:status")
				.setParameter("status", status);
		return q.getResultList();
	}

	@Override
	public void remove(long id) {
		Menu menu = entityManager.find(Menu.class, id);
		entityManager.remove(menu);
	}


	@Override
	public Menu findById(long id) {
		Menu menu = new Menu();
		menu = (Menu) entityManager.createQuery("select s from Menu s where s.id = :id")
									.setParameter("id", id).getSingleResult();		
		return menu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listAll() {
		Query q = entityManager.createQuery("select k from Menu k ");
		return q.getResultList();
	}

	@Override
	public void persist(Menu menu) {
		entityManager.persist(menu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listByIsletme(Isletme isletme) {
		return entityManager.createQuery(
				"select k.menuListesi from Isletme k where k.id =:id")
				.setParameter("id", isletme.getId())
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listByOrganizasyonTipi(OrganizasyonTipi organizasyonTipi) {
		return entityManager.createQuery("select m.menu from MenuOrganizasyonTipi m where m.organizasyonTipi =:organizasyonTipi")
							.setParameter("organizasyonTipi", organizasyonTipi)
							.getResultList();
	}

}
