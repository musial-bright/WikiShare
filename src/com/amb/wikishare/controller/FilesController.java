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

import com.amb.wikishare.dao.FileDAO;
import com.amb.wikishare.domain.WikiFile;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.ClipboardService;

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
		
		// Clipboard 
		ClipboardService clipboard = new ClipboardService(request);
		if(request.getParameter(WikiShareHelper.CLIPBOARD) != null &&
			request.getParameter(WikiShareHelper.CLIPBOARD) != "") {
				clipboard.addClipboard(
						request.getParameter(WikiShareHelper.CLIPBOARD));
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
