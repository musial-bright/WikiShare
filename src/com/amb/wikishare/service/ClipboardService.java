package com.amb.wikishare.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.app.WikiShareHelper;

/**
 * Clipboard provides copy & paste capabilities for
 * any wiki service. 
 * Clipboard is stored in session as a ArrayList of Strings.
 * todo: Capability of storing clipboard in DB after logout.
 * @author amusial
 */
public class ClipboardService {

	protected final Log logger = LogFactory.getLog(this.getClass());
	private List<String> clipboard = new ArrayList<String>();
	private final int MAX_SIZE = 5;
	private HttpServletRequest request = null;
	
	public ClipboardService (HttpServletRequest request) {
		this.request = request;
	}

	
	public List<String> getClipboardList() {
		clipboard = (List)request.getSession()
			.getAttribute(WikiShareHelper.CLIPBOARD);
		return clipboard;
	}
	
	public void addClipboard(String text) {
		// Get clipboard from session
		clipboard = (List)request.getSession()
			.getAttribute(WikiShareHelper.CLIPBOARD);
		if(clipboard == null) {
			clipboard = new ArrayList<String>();
		}
		if(clipboard.size() >= MAX_SIZE) {
			clipboard.remove(0);
		}
		clipboard.add(text);
		
		try {
			request.getSession()
				.setAttribute(WikiShareHelper.CLIPBOARD, clipboard);
		} catch(Exception e) {
			logger.error("addCLipboard() " + e);
		}
		
	}
	
	public void clearClipboard() {
		clipboard.clear();
		request.getSession().setAttribute(WikiShareHelper.CLIPBOARD, clipboard);
	}
	
}