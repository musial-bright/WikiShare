package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.dao.JdbcNavigationDAO;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.Wikipage;

public class NavigationService implements NavigationInterface {
	
	private String webappPrefix;
	private WikipageService ws;
	private JdbcNavigationDAO jdbcNavigationDAO;
	
	public void setWebappPrefix(String webappPrefix) {
		this.webappPrefix = webappPrefix;
	}
	
	
	public String getNavigationView(int navigationId) {
		String html = "";
		// todo: load navigation from db
		// todo: parse navigation

		String test = "s0;s1,s2;s4";
		html = renderNavigationBySignatures(test);
		return html;
	}
	
	
	/**
	 * Get html code from signature markup;
	 * @param signatureMarkup exp.: s123;s46,s235;s89
	 * @return html
	 */
	private String renderNavigationBySignatures(String signatureMarkup) {
		String markup = "";
		
		String[] pagesAndPageGroups = signatureMarkup.split(";");
		for(String signatureIds : pagesAndPageGroups) {
			String[] pages = signatureIds.split(",");
			if(pages.length != 1) {
				markup += "<br/>";
			}
			for(String signatureId : pages) {
				String title = signatureId;
				try {
					// todo: Performance! Get title using SQL directly!
					Wikipage wikipage = ws.getPageBySignature(signatureId);
					title = wikipage.getTitle();
				} catch(Exception e) {}
				
				markup += 
					"[<a href='" + 
					webappPrefix +
					"wikipage/" +
					signatureId +
					"'>" +
					title +
					"</a>] ";
			}
			if(pages.length != 1) {
				markup += "<br/>";
			}
		}
		
		return markup;
	}



	public void dropNavigation(Navigation Navigation) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public List<Navigation> getNavigationsList() throws Exception {
		
		return null;
	}


	public void saveNavigation(Navigation Navigation) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void updateNavigation(Navigation Navigation) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public Navigation getNavigation(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setWikipageService(WikipageService ws) {
		this.ws = ws;
	}


	public void setJdbcNavigationDAO(JdbcNavigationDAO jdbcNavigationDAO) {
		this.jdbcNavigationDAO = jdbcNavigationDAO;
	}

}