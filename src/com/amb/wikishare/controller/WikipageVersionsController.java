package com.amb.wikishare.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.service.PageService;
import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.Page;

public class WikipageVersionsController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());
    private PageService wpService = null;
    private Map<String, Object> model = new HashMap<String, Object>();

    /**
     * Provide wiki page versions.
     *
     * Request: /WikiShare/wiki/wikipage_versions/<signature>|<page-id>
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String pageIdOrSignature =
            WikiShareHelper.getLastUriResource(request);

        String actionParam = request.getParameter(WikiShareHelper.ACTION_PARAM);
        if ( actionParam != null &&
             actionParam.equals(WikiShareHelper.DELETE_PARAM)) {

            try {
                int pageId = Integer.parseInt(pageIdOrSignature);
                Page page = wpService.getPage(pageId);

                // Turn pageIdOrSignature to a signature before
                // permanently deleting page
                pageIdOrSignature = page.getSignature();

            } catch(Exception e) {
                logger.debug("Delete action " + e);
            }

            deleteAction(request);
        }


        try {
            List<Page> wikipages =
                wpService.getPageVersionsList(pageIdOrSignature);

            model.put("pages", wikipages);
        } catch(Exception e) {
            logger.debug("No page versions found" + e);
        }

        return new ModelAndView("wikipage_versions", "model", this.model);
    }

    /**
     * Delete a wiki page by signature from url and delete action param.
     * /WikiShare/wiki/wikipage_versions/<page-id>?action=delete
     * @param request
     */
    private void deleteAction(HttpServletRequest request) {

        logger.debug("[deleteAction] trying deleting page="+ request.getRequestURI());

        try {
            int page_id = Integer.parseInt(
                    WikiShareHelper.getLastUriResource(request));

            this.wpService.dropPage(new Page(page_id));

        } catch(Exception e) {
            logger.warn("[deleteAction] " + e);
        }
    }

    public void setWikipageService(PageService wpService) {
        this.wpService = wpService;
    }

}
