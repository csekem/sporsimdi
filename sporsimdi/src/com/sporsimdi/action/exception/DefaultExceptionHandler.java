package com.sporsimdi.action.exception;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;


public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

	//private static final Log LOG = LogFactory.getLog(DefaultExceptionHandler.class);
	private ExceptionHandler wrapped;
	
	public DefaultExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (ExceptionQueuedEvent event : getHandledExceptionQueuedEvents()) {
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable t = context.getException();
			
			FacesContext fc = FacesContext.getCurrentInstance();
			
			String redirectPage = null;
			
			try {
				if (t instanceof AbortProcessingException) {
					redirectPage = "error.jsf?statusCode=jsftoolkit.exception.UncheckedException";
					fc.getExternalContext().getSessionMap().put("ip.client.jsftoolkit.messageDetail", t.getLocalizedMessage());
				} else if(t instanceof ViewExpiredException) {
					redirectPage = "/menu/cikis.jsf";
				} else {
					redirectPage = "error.jsf?statusCode=jsftoolkit.exception.UncheckedException";
					fc.getExternalContext().getSessionMap().put("ip.client.jsftoolkit.messageDetail", t.getLocalizedMessage());
				}
			} finally {
				
			}
			NavigationHandler nav = fc.getApplication().getNavigationHandler();
			nav.handleNavigation(fc, null, redirectPage);
			fc.renderResponse();
		
		}
		super.handle();
	}

}
