package com.amb.wikishare.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.dao.*;
import com.amb.wikishare.domain.*;
import com.amb.wikishare.helper.WikiShareHelper;
import com.amb.wikishare.service.*;

/**
 * Wiki page CRUD service and page management.
 * @author amusial
 *
 */
public class WikipageService implements WikipageInterface {

    protected final Log logger = LogFactory.getLog(getClass());
    private JdbcWikipageDAO wikipageDao;
    private JdbcUserDAO userDao;

    private final int SEARCH_CONTENT_RANGE = 50;

    public void setJdbcWikipageDAO(JdbcWikipageDAO wikipageDao) {
        this.wikipageDao = wikipageDao;
    }
    public void setJdbcUserDAO(JdbcUserDAO userDao) {
        this.userDao = userDao;
    }

    public List<Wikipage> getWikipagesList(
            boolean showActivePagesOnly,
            boolean showFrontPagesOnly) throws Exception {

        List<Wikipage> wikipages =
            wikipageDao.getWikipagesList(
                    showActivePagesOnly,
                    showFrontPagesOnly);

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

    public List<Wikipage> getWikipageVersionsList(String pageIdOrSignature) throws Exception {

        String signature = pageIdOrSignature;

        if(!pageIdOrSignature.startsWith("s")) {
            int pageId = Integer.parseInt(pageIdOrSignature);
            Wikipage page = getPage(pageId);
            signature = page.getSignature();
        }
        logger.debug("[getWikipageVersionsList] signature=" + signature);

        return wikipageDao.getWikipageVersionsList(signature);
    }

    public int getWikipageAmount(String signature) throws Exception {
        return wikipageDao.getWikipageVersionsAmount(signature);
    }

    public Wikipage getPage(int id) throws Exception {
        return wikipageDao.getPage(id);
    }

    public Wikipage getActivePageBySignature(String signature) {
        return wikipageDao.getActivePageBySignature(signature);
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


    /**
     * Get wiki page for the model from the page id or signature.
     * @param request
     */
    public Wikipage getWikipageByIdOrSingnature(HttpServletRequest request)
        throws Exception {

        Wikipage wikipage = null;

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
    public List<Wikipage> search(String searchText, boolean matchingSectionOnly) {
        List<Wikipage> wikipages = wikipageDao.search(searchText);

        // Get search matching page section
        if(matchingSectionOnly) {
            for(Wikipage page : wikipages) {
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
