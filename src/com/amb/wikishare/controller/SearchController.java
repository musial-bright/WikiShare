package com.amb.wikishare.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.WikipageService;

public class SearchController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private WikipageService wpService = null;
	private Map<String,Object> model = new HashMap<String,Object>();
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String searchText = request.getParameter(WikiShareHelper.SEARCH_PARAM);
		if(searchText != null && !searchText.equals("")) {
			this.model.put("pages", this.wpService.search(searchText));
		}
		return new ModelAndView("search", "model", model);
	}

	public void setWikipageService(WikipageService wpService) {
		this.wpService = wpService;
	}

}