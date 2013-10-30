package com.sporsimdi.action.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qrpos.action.facade.KartDurumFacade;
import com.qrpos.action.facade.QRCardFacade;
import com.qrpos.model.entity.KartDurum;
import com.qrpos.model.entity.QRCard;
import com.qrpos.model.type.KartStatus;

@ManagedBean(name = "qrcardService")
@ViewScoped
public class QRCardService extends GenericService implements Serializable {

	private static final long serialVersionUID = 5085685830081343847L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private QRCardFacade qrCardFacade;

	@EJB
	private KartDurumFacade kartDurumFacade;

	private List<QRCard> qrcardListForUse;

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<QRCard> getQrcardListForUse() {
		if (qrcardListForUse == null) {
			qrcardListForUse = qrCardFacade.listByOrganizasyon(sessionObject.getSelectedOrganizasyon().getId());

			List<KartDurum> listKartDurum = kartDurumFacade.listByOrganizasyon(sessionObject.getSelectedOrganizasyon().getId());
			for (KartDurum kartDurum : listKartDurum) {
				if (kartDurum.getKartStatus().equals(KartStatus.ACIK)) {
					for (QRCard card : qrcardListForUse) {
						if (kartDurum.getQrcard().getNo().equals(card.getNo())) {
							qrcardListForUse.remove(card);
							break;
						}
					}
				}
			}
		}
		return qrcardListForUse;
	}

	public void setQrcardListWithKartForUse(List<QRCard> qrcardListForUse) {
		this.qrcardListForUse = qrcardListForUse;
	}

}
