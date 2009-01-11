package com.amb.wikishare.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.Wikipage;

import junit.framework.TestCase;

public class JdbcWikipageDAOTests extends TestCase {
	
	DriverManagerDataSource dataSource;
	JdbcWikipageDAO pageDao;
	
	public JdbcWikipageDAOTests() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
		pageDao = new JdbcWikipageDAO();
		pageDao.setDataSource(dataSource);
	}
	
	public void testGetAnyWikipageCases() throws SQLException {
		List<Wikipage> pages = pageDao.getWikipagesList(false,false);
		assertNotNull(pages);
	}
	
	public void testGetFirstWikipageCases() throws SQLException{	
		Wikipage wp0 = pageDao.getPage(1);
		assertEquals("Page not found", wp0.getTitle());
		assertEquals(1, wp0.getId());
	}
	
	public void testUpdateWikipageWithOwnContentCases() throws Exception{
		Wikipage wp0 = pageDao.getPage(1);
		wp0.setContent(wp0.getContent());
		pageDao.updateWikipage(wp0);
		Wikipage updatedWp0 = pageDao.getPage(wp0.getId());
		assertEquals(wp0.getContent(),updatedWp0.getContent());
	}

	public void testWikipageSearchCase() {
		List wikiPages = pageDao.search("a");
		assertNotNull(wikiPages);
	}
	
	public void testPageVersionsCase() throws SQLException {	
		List wikiPageVersions = pageDao.getWikipageVersionsList("0");
		assertTrue(wikiPageVersions.size() > 0);
	}
	
	public void testPageAmount() throws SQLException {
		assertTrue(pageDao.getWikipageVersionsAmount("0") == 1);
	}
	
	public void testGetPageBySignature()  {
		assertNotNull(pageDao.getPageBySignature("s1"));
	}
}