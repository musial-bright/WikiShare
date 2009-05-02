package com.amb.wikishare.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.Wikipage;

import junit.framework.TestCase;

public class JdbcWikipageDAOTests extends TestCase {
	
	private DriverManagerDataSource dataSource;
	private JdbcWikipageDAO pageDao;
	private Wikipage page = null;
	
	public JdbcWikipageDAOTests() {
		dataSource = new DriverManagerDataSource();
		//dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		//dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
		//dataSource.setUsername("sa");
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/wikishare_test");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		pageDao = new JdbcWikipageDAO();
		pageDao.setDataSource(dataSource);
		
		page = new Wikipage();
		page.setTitle("testpage");
		page.setContent("testcontent");
		page.setActivePage(true);
		page.setFrontPage(true);
	}
	
	public void testDeleteAllPages() throws Exception {
		List<Wikipage> pages = pageDao.getWikipagesList(false, false);
		for(Wikipage page : pages) {
			pageDao.dropWikipage(page);
		}
		List<Wikipage> checkPages = pageDao.getWikipagesList(false, false);
		assertEquals(checkPages.size(), 0);
	}
	
	public void testSavePage() throws Exception {
		pageDao.saveWikipage(page);
		List<Wikipage> checkPages = pageDao.getWikipagesList(false, false);
		assertTrue(checkPages.size() > 0);
	}
	
	public void testGetAnyWikipage() throws SQLException {
		List<Wikipage> pages = pageDao.getWikipagesList(false,false);
		assertNotNull(pages);
	}
	
	public void testUpdateWikipage() throws Exception {
		List<Wikipage> pages = pageDao.getWikipagesList(false, false);
		Wikipage testpage = pages.get(0);
		
		testpage.setContent("new content");
		pageDao.updateWikipage(testpage);
		
		Wikipage comparePage = pageDao.getPage(testpage.getId());
		assertEquals(comparePage.getContent(), "new content");
	}

	public void testWikipageSearchCase() {
		List wikiPages = pageDao.search("a");
		assertNotNull(wikiPages);
	}
	
	public void testPageVersions() throws SQLException {
		List<Wikipage> pages = pageDao.getWikipagesList(false, false);
		int pageVersionAmount = 0;
		for(Wikipage page : pages) {
			pageVersionAmount = 
				pageDao.getWikipageVersionsAmount(page.getSignature());
		}
		assertTrue(pageVersionAmount == 1);
	}
	
	public void testGetPageBySignature() throws Exception {
		List<Wikipage> pages = pageDao.getWikipagesList(false, false);
		int pageVersionAmount = 0;
		Wikipage testPage = null;
		for(Wikipage page : pages) {
			testPage = pageDao.getPageBySignature(page.getSignature());
			break;
		}
		assertNotNull(testPage);
		
	}
}