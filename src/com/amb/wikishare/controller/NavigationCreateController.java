package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.NavigationService;


public class NavigationCreateController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass()); 
	private NavigationService navigationService;
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		
		try {
			Navigation navigation = (Navigation)command;
			if(navigation.getUpdateFlag() == false) {
				navigationService.saveNavigation(navigation);
			} else {
				navigationService.updateNavigation(navigation);
			}
		} catch(Exception e) {
			logger.error("Error while saving navigation: " + e);
		}
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Navigation navigation = new Navigation();
		
		// Extra case: PAGE Update
		if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
				request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.UPDATE_PARAM)) {
			try{
				int id = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
				navigation = navigationService.getNavigation(id);
				navigation.setUpdateFlag(true);
			} catch(Exception e) {
				logger.error("Navigation load Error: " + e);
			}
		}
		
		return navigation;
	}

	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}
	
	
}
