package com.amb.wikishare.service;

import java.util.ArrayList;
import java.util.List;

import com.amb.wikishare.dao.JdbcNavigationDAO;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.Page;

/**
 * Navigation service manages the wiki page navigations.
 * @author amusial
 */
public class NavigationService implements NavigationInterface {

    private String webappPrefix;
    private PageService ws;
    private JdbcNavigationDAO jdbcNavigationDAO;

    public void setWebappPrefix(String webappPrefix) {
        this.webappPrefix = webappPrefix;
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
                    Page wikipage = ws.getActivePageBySignature(signatureId);
                    title = wikipage.getTitle();
                    title = replaceSingleTick(title);
                    markup +=
                        "[<a href=\"" +
                        webappPrefix +
                        "wikipage/" +
                        signatureId +
                        "\">" +
                        title +
                        "</a>] ";
                } catch(Exception e) {}
            }
            if(pages.length != 1) {
                markup += "<br/>";
            }
        }

        return markup;
    }



    public void dropNavigation(Navigation navigation) throws Exception {
        this.jdbcNavigationDAO.dropNavigation(navigation);
    }


    /**
     * Get navigation list with <code>Navigation</code> elements.
     * The content in the <code>Navigation</code> is already rendered and
     * single ticks are escaped.
     * @return List<Navigation>
     */
    public List<Navigation> getNavigationsList() throws Exception {
        List<Navigation> navigationList = new ArrayList<Navigation>();
        for(Navigation navigation : jdbcNavigationDAO.getNavigationsList()) {
            navigation.setContent(
                    renderNavigationBySignatures(navigation.getContent()));
            navigationList.add(navigation);
        }
        return navigationList;
    }


    public void saveNavigation(Navigation navigation) throws Exception {
        navigation.setContent(replaceCharacters(navigation.getContent()));
        this.jdbcNavigationDAO.saveNavigation(navigation);
    }


    public void updateNavigation(Navigation navigation) throws Exception {
        navigation.setContent(replaceCharacters(navigation.getContent()));
        jdbcNavigationDAO.updateNavigation(navigation);
    }

    private String replaceCharacters(String content) {
        content.replaceAll("\t|\n|\r", "");
        content = content.replaceAll("\"", "&quot;");
        return replaceSingleTick(content);
    }

    private String replaceSingleTick(String content) {
        return content.replaceAll("'", "&#39;");
    }

    public Navigation getNavigation(int id) throws Exception {
        return this.jdbcNavigationDAO.getNavigation(id);
    }



    public void setWikipageService(PageService ws) {
        this.ws = ws;
    }


    public void setJdbcNavigationDAO(JdbcNavigationDAO jdbcNavigationDAO) {
        this.jdbcNavigationDAO = jdbcNavigationDAO;
    }

}