package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.dao.FileDAO;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.WikipageService;
import com.amb.wikishare.service.UserService;

public class WikipageCreateController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass()); 
	private WikipageService wpService;
	private FileDAO fileDao;
	
	
	public ModelAndView onSubmit(Object command) {
		try {
			if(((Wikipage)command).getSkipNewVersionFlag()) {
				// Page Update
				wpService.updateWikipage((Wikipage) command);
			}else {
				// New Page
				wpService.saveWikipage((Wikipage) command);
			}
		}catch(Exception e) {
			logger.error("onSubmit Exception: " +e);
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException { 
		Wikipage wikipage = new Wikipage();
		
		// New page
		User user = UserService.getSessionUser(request);
		wikipage.setUser(user);

		// Extra case: PAGE Update/Edit
		if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
				request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.UPDATE_PARAM)) {
			try{
				int id = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
				wikipage = wpService.getPage(id);
				wikipage.setUser(user);
				wikipage.setFiles(fileDao.getFiles());
			} catch(Exception e) {
				logger.error("Wiki page load Error: " + e);
			}
		}
		
		return wikipage;
	}  


	public void setWikipageService (WikipageService wpService) {
		this.wpService = wpService;
	}
	
	public void setFileDao(FileDAO fileDao) {
		this.fileDao = fileDao;
	}
}