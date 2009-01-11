package com.amb.wikishare.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.dao.*;
import com.amb.wikishare.domain.*;
import com.amb.wikishare.service.*;

public class WikipageService implements WikipageInterface {
	
	protected final Log logger = LogFactory.getLog(getClass()); 
	private JdbcWikipageDAO wikipageDao;
	private JdbcUserDAO userDao;
	
	public void setJdbcWikipageDAO(JdbcWikipageDAO wikipageDao) { 
		this.wikipageDao = wikipageDao; 
	} 
	public void setJdbcUserDAO(JdbcUserDAO userDao) {
		this.userDao = userDao;
	}

	public List<Wikipage> getWikipagesList(boolean showActivePagesOnly, boolean showFrontPagesOnly) throws Exception {
		List<Wikipage> wikipages = wikipageDao.getWikipagesList(showActivePagesOnly, showFrontPagesOnly);
		
		// Get version amount for wiki pages
		List<Wikipage> wikipagesWithPageAmount = new ArrayList<Wikipage>();
		for(Wikipage wikipage : wikipages) {
			wikipage.setVersionAmount( 
				wikipageDao.getWikipageVersionsAmount(wikipage.getSignature()) 
			);
			try {
				wikipage.setUser(userDao.getUser(wikipage.getUser().getId()));
			} catch (Exception e) {}
			wikipagesWithPageAmount.add(wikipage);
		}
	
		return wikipagesWithPageAmount;
	}

	public List<Wikipage> getWikipageVersionsList(String signature) throws Exception {
		return wikipageDao.getWikipageVersionsList(signature);
	}
	
	public int getWikipageAmount(String signature) throws Exception {
		return wikipageDao.getWikipageVersionsAmount(signature);
	}

	public Wikipage getPage(int id) throws Exception {
		return wikipageDao.getPage(id);
	}
	
	public Wikipage getPageBySignature(String signature) {
		return wikipageDao.getPageBySignature(signature);
	}
	
	public void saveWikipage(Wikipage wikipage) throws Exception {
		if(wikipage != null && wikipage.getTitle() != null && wikipage.getTitle() != "") { 
			wikipageDao.saveWikipage(wikipage);
		} else {
			logger.info("wikipage.title is empty -> this page will not be storen in db.");
		}
	}

	public void dropWikipage(Wikipage wikipage) throws Exception {
		if(wikipage != null && wikipage.getId() != -1) {
			wikipageDao.dropWikipage(wikipage);
		} else {
			logger.info("wikipage.id is empty -> this page will not be deleted.");
		}
		
	}

	public void updateWikipage(Wikipage wikipage) throws Exception {
		if(wikipage != null && wikipage.getId() != -1) {
			wikipageDao.updateWikipage(wikipage);	
		} else {
			logger.error("Wikipage.id is empty -> no update possible.");
		}
		
	}
	
	public List<Wikipage> search(String searchText) {
		return wikipageDao.search(searchText);
	}
}
