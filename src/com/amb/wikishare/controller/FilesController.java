package com.amb.wikishare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.dao.FileDAO;
import com.amb.wikishare.domain.WikiFile;

public class FilesController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass()); 
	
	private FileDAO fileDao;
	private Map<String, Object> model = new HashMap<String, Object>();
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(request.getParameter("delete") != null) {
			logger.debug("Deleting file: " + request.getParameter("delete"));
			fileDao.removeFile(request.getParameter("delete"));
		}
		
		ArrayList<WikiFile> files = fileDao.getFiles();
		model.put("files", files);
		model.put("fileStoragePath", fileDao.getFileStoragePath());
		
		return new ModelAndView("files", "model", this.model);
	}
	
	public void setFileDao(FileDAO fileDao) {
		this.fileDao = fileDao;
	}
}
