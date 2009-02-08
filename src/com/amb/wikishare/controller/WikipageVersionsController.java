package com.amb.wikishare.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.service.WikipageService;
import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.helper.WikiShareHelper;

public class WikipageVersionsController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private WikipageService wpService = null;
	private Map<String, Object> model = new HashMap<String, Object>();
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if ( request.getParameter(WikiShareHelper.ACTION_PARAM) != null && 
			 request.getParameter(WikiShareHelper.OBJECT_ID_PARAM) != null &&
			 request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.DELETE_PARAM)) {
			deleteAction(request);
		}
		
		if ( request.getParameter(WikiShareHelper.SIGNATURE_PARAM) != null ) {
			List<Wikipage> wikipages = wpService.getWikipageVersionsList(
					request.getParameter(WikiShareHelper.SIGNATURE_PARAM));
			model.put("pages", wikipages);
			if(wikipages != null && wikipages.size() <= 0) {
				// No page versions  tho show, so go to wiki page overview
				return new ModelAndView(new RedirectView("wikipages"));
			}
		}
		
		return new ModelAndView("wikipage_versions", "model", this.model);
	}

	/**
	 * Delete a wikipage.
	 * @param request
	 */
	private void deleteAction(HttpServletRequest request) {
		try{
			int page_id = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
			this.wpService.dropWikipage(new Wikipage(page_id));
		} catch(Exception e) {
			logger.error("Wiki page ID Error: " + e);
		}
	}
	
	public void setWikipageService(WikipageService wpService) {
		this.wpService = wpService;
	}
	
}
