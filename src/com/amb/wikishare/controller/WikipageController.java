package com.amb.wikishare.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.NavigationService;
import com.amb.wikishare.service.WikipageService;

public class WikipageController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass()); 
	
	private WikipageService wpService;
	private NavigationService navigationService;	
	
	private Map<String, Object> model = new HashMap<String, Object>();
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String requestUri = request.getRequestURI();
		logger.debug("URL : " + requestUri);
		
		// Get page id (by id or signature) form the URI
		Wikipage wikipage = null;
		try {
			String pageIdentify = requestUri.substring(
					requestUri.lastIndexOf("/") + 1, 
					requestUri.length());
			
			if( pageIdentify.startsWith("s") ) {
				wikipage = wpService.getPageBySignature(pageIdentify);
			} else {
				int pageId = Integer.parseInt(pageIdentify);
				wikipage = wpService.getPage(pageId);
			}
		} catch (Exception e) {
			wikipage = wpService.getPage(WikiShareHelper.ERROR_PAGE_ID);
			logger.error("Error: " + e);
		}
		if (wikipage == null) {
			wikipage = wpService.getPage(WikiShareHelper.ERROR_PAGE_ID);
		}
		model.put(WikiShareHelper.PAGE, wikipage);
		
		
		
		// Navigation
		navigationService.setWebappPrefix(WikiShareHelper.getWabappContext(request));
		model.put("navigationList", navigationService.getNavigationsList());
		model.put("navigation", navigationService.getNavigationView(0));
		
		return new ModelAndView("wikipage", "model", this.model);
	}

	public void setWikipageService (WikipageService wpService) {
		this.wpService = wpService;
	}

	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}
	
}
