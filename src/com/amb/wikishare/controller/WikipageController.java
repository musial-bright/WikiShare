package com.amb.wikishare.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.ClipboardService;
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
		
		// Wikipage action : get wikipage from URI
		Wikipage wikipage = null;
		try {
			String pageIdentify = requestUri.substring(
					requestUri.lastIndexOf("/") + 1, 
					requestUri.length());
			
			// Every URI starting with s linke ../s.. is identified as signature
			// not as page id. Page id starts always with a number.
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
		
		// Navigation action : Delete navigation 
		if ( request.getParameter(WikiShareHelper.ACTION_PARAM) != null && 
				request.getParameter(WikiShareHelper.ACTION_PARAM).equals("delete_navi") && 
				request.getParameter(WikiShareHelper.OBJECT_ID_PARAM) != null) {
			int naviId = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
			logger.debug("Deleting navigation = " + naviId);
			Navigation navi = new Navigation();
			navi.setId(naviId);
			navigationService.dropNavigation(navi);
		}
		navigationService.setWebappPrefix(WikiShareHelper.getWabappContext(request));
		model.put("navigationList", navigationService.getNavigationsList());
		
		// Clipboard 
		ClipboardService clipboard = new ClipboardService(request);
		if(request.getParameter(WikiShareHelper.CLIPBOARD) != null &&
			request.getParameter(WikiShareHelper.CLIPBOARD) != "") {
				clipboard.addClipboard(
						request.getParameter(WikiShareHelper.CLIPBOARD));
		}
		model.put(WikiShareHelper.CLIPBOARD, clipboard);
		
		
		return new ModelAndView("wikipage", "model", this.model);
	}

	
	public void setWikipageService (WikipageService wpService) {
		this.wpService = wpService;
	}

	
	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}
	
}