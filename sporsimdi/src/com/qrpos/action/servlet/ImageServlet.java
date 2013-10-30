package com.qrpos.action.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrpos.action.facade.UrunFacade;
import com.qrpos.model.entity.Urun;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("image/jpeg");
		Integer id;
		String paramID = request.getParameter("id");
		if (paramID == null) {
			return;
		}
		if (paramID.equals("")) {
			paramID = "0";
		}

		try {
			id = Integer.valueOf(paramID);
		} catch (NumberFormatException e) {
			return;
		}

		byte[] bytes = null;

		UrunFacade urunFacade;
		try {

			InitialContext ic = new InitialContext();
			urunFacade = (UrunFacade) ic.lookup("java:global/sporsimdi/UrunFacadeBean");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new IOException("System Error!!!");
		}

		Urun urun = urunFacade.findById(id);
		if (urun != null) {
			bytes = urun.getImage();
		}

		if (bytes != null) {
			OutputStream out = response.getOutputStream();

			out.write(bytes);
			out.close();
		}

	}
}