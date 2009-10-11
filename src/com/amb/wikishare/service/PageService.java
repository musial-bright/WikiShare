package com.amb.wikishare.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.dao.*;
import com.amb.wikishare.domain.*;
import com.amb.wikishare.service.*;

/**
 * Page CRUD service and page management.
 * @author amusial
 *
 */
public class PageService implements PageInterface {

    protected final Log logger = LogFactory.getLog(getClass());
    private JdbcWikipageDAO wikipageDao;
    private JdbcUserDAO userDao;

    // title asc
    // title desc
    private String pagesOrder = "title asc";
    
    private final int SEARCH_CONTENT_RANGE = 50;

    public void setJdbcWikipageDAO(JdbcWikipageDAO wikipageDao) {
        this.wikipageDao = wikipageDao;
    }
    public void setJdbcUserDAO(JdbcUserDAO userDao) {
        this.userDao = userDao;
    }

    public void setPagesOrder(String order) {
    	this.pagesOrder = order;
    }
    
    
    public List<PageFormBackingObject> getPagesList(
            boolean showActivePagesOnly,
            boolean showFrontPagesOnly) throws Exception {

        List<Page> wikipages =
            wikipageDao.getWikipagesList(
                    showActivePagesOnly,
                    showFrontPagesOnly,
                    pagesOrder);

        // Get version amount for wiki pages
        List<PageFormBackingObject> wikipagesWithPageAmount = new ArrayList<PageFormBackingObject>();
        for(Page wikipage : wikipages) {
            PageFormBackingObject wikipageForm = new PageFormBackingObject(wikipage);
            wikipageForm.setVersionAmount(wikipageDao.getWikipageVersionsAmount(wikipage.getSignature()));
            wikipagesWithPageAmount.add(wikipageForm);
        }

        return wikipagesWithPageAmount;
    }

    public List<Page> getPageVersionsList(String pageIdOrSignature) throws Exception {

        String signature = pageIdOrSignature;

        if(!pageIdOrSignature.startsWith("s")) {
            int pageId = Integer.parseInt(pageIdOrSignature);
            Page page = getPage(pageId);
            signature = page.getSignature();
        }
        logger.debug("[getWikipageVersionsList] signature=" + signature);

        return wikipageDao.getWikipageVersionsList(signature);
    }

    public int getWikipageAmount(String signature) throws Exception {
        return wikipageDao.getWikipageVersionsAmount(signature);
    }

    public Page getPage(int id) throws Exception {
        return wikipageDao.getPage(id);
    }

    public Page getActivePageBySignature(String signature) {
        return wikipageDao.getActivePageBySignature(signature);
    }

    public void savePage(Page wikipage) throws Exception {
        if(wikipage != null && wikipage.getTitle() != null && wikipage.getTitle() != "") {
            wikipageDao.saveWikipage(wikipage);
        } else {
            logger.info("wikipage.title is empty -> this page will not be storen in db.");
        }
    }

    public void dropPage(Page wikipage) throws Exception {
        if(wikipage != null && wikipage.getId() != -1) {
            wikipageDao.dropWikipage(wikipage);
        } else {
            logger.info("wikipage.id is empty -> this page will not be deleted.");
        }

    }

    public void updatePage(Page wikipage) throws Exception {
        if(wikipage != null && wikipage.getId() != -1) {
            wikipageDao.updateWikipage(wikipage);
        } else {
            logger.error("Wikipage.id is empty -> no update possible.");
        }

    }


    /**
     * Get wiki page for the model from the page id or signature.
     * @param request
     */
    public Page getWikipageByIdOrSingnature(HttpServletRequest request)
        throws Exception {

        Page wikipage = null;

        // Get id or signature from requested uri
        try {
            String pageIdOrSignature =
                WikiShareHelper.getLastUriResource(request);

            // Every URI starting with s linke ../s.. is identified as signature
            // not as page id. Page id starts always with a number.
            if( pageIdOrSignature.startsWith("s") ) {
                wikipage = getActivePageBySignature(pageIdOrSignature);
            } else {
                int pageId = Integer.parseInt(pageIdOrSignature);
                wikipage = getPage(pageId);
            }
        } catch (Exception e) {
            //wikipage = getPage(WikiShareHelper.ERROR_PAGE_ID);
            logger.debug("[getWikipageByIdOrSingnature] " + e);
        }

        return wikipage;
    }



    /**
     * Find matching wiki pages by search text.
     * @param searchText search text
     * @param matchingSectionOnly overite the content by the search section
     * @return List<Wikipage> list of pages
     */
    public List<Page> search(String searchText, boolean matchingSectionOnly) {
        List<Page> wikipages = wikipageDao.search(searchText);

        // Get search matching page section
        if(matchingSectionOnly) {
            for(Page page : wikipages) {
                String content = page.getContent().replaceAll("\\<.*?\\>", "");
                int searchTextPosition =
                    content.toLowerCase().indexOf(searchText.toLowerCase());

                int subsectionStart = searchTextPosition - SEARCH_CONTENT_RANGE;
                int subsectionEnd = searchTextPosition +
                    searchText.length() + SEARCH_CONTENT_RANGE;
                if(subsectionStart < 0) { subsectionStart = 0; }
                if(subsectionEnd > content.length()) {
                    subsectionEnd = content.length();
                }
                logger.debug("search: subcontent between (" + searchTextPosition +") " +
                        subsectionStart +
                        " and " + subsectionEnd);
                page.setContent(content.substring(subsectionStart, subsectionEnd));
            }
        }

        return wikipages;
    }
}
