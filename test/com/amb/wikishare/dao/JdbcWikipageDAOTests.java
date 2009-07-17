package com.amb.wikishare.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.Page;

import junit.framework.TestCase;

public class JdbcWikipageDAOTests extends TestCase {

    private JdbcWikipageDAO pageDao = new JdbcWikipageDAO();
    private Page page = null;

    protected void setUp() throws Exception {
        super.setUp();
        HibernateFactory.configFileName = "hibernate-test.cfg.xml";

        page = new Page();
        page.setTitle("testpage");
        page.setContent("testcontent");
        page.setActivePage(true);
        page.setFrontPage(true);
    }

    public void testDeleteAllPages() throws Exception {
        List<Page> pages = pageDao.getWikipagesList(false, false);
        for(Page page : pages) {
            pageDao.dropWikipage(page);
        }
        List<Page> checkPages = pageDao.getWikipagesList(false, false);
        assertEquals(checkPages.size(), 0);
    }

    public void testSavePage() throws Exception {
        pageDao.saveWikipage(page);
        List<Page> checkPages = pageDao.getWikipagesList(false, false);
        assertTrue(checkPages.size() > 0);
    }

    public void testGetAnyWikipage() throws SQLException {
        List<Page> pages = pageDao.getWikipagesList(false,false);
        assertNotNull(pages);
    }

    public void testUpdateWikipage() throws Exception {
        List<Page> pages = pageDao.getWikipagesList(false, false);
        Page testpage = pages.get(0);

        testpage.setContent("new content");
        pageDao.updateWikipage(testpage);

        Page comparePage = pageDao.getPage(testpage.getId());
        assertEquals(comparePage.getContent(), "new content");
    }

    public void testWikipageSearchCase() {
        List wikiPages = pageDao.search("a");
        assertNotNull(wikiPages);
    }

    public void testPageVersions() throws SQLException {
        List<Page> pages = pageDao.getWikipagesList(false, false);
        int pageVersionAmount = 0;
        for(Page page : pages) {
            pageVersionAmount =
                pageDao.getWikipageVersionsAmount(page.getSignature());
        }
        assertTrue(pageVersionAmount == 1);
    }

    public void testGetPageBySignature() throws Exception {
        List<Page> pages = pageDao.getWikipagesList(false, false);
        int pageVersionAmount = 0;
        Page testPage = null;
        for(Page page : pages) {
            testPage = pageDao.getActivePageBySignature(page.getSignature());
            break;
        }
        assertNotNull(testPage);

    }
}