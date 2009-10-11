package com.amb.wikishare.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.Page;
import com.amb.wikishare.service.ClipboardService;
import com.amb.wikishare.service.NavigationService;
import com.amb.wikishare.service.PageService;

public class WikipageController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private PageService pageService;
    private NavigationService navigationService;

    private Map<String, Object> model = new HashMap<String, Object>();

    /**
     * Provide wiki page.
     *
     * Request: /WikiShare/wiki/wikipage/<page-id>|<signature>
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Wikipage action : get wikipage from URI
        Page page = pageService.getWikipageByIdOrSingnature(request);

        if(page != null) {
            model.put(WikiShareHelper.PAGE, page);

            // Navigation action : Delete navigation
            navigationAction(request);

            // Clipboard
            clipboardAction(request);
            return new ModelAndView("wikipage", "model", this.model);
        } 
        return new ModelAndView(new RedirectView("/WikiShare/public/global/error404.jsp"));
    }




    /**
     * Fetch and delete navigations for the wiki page model.
     * @param request
     * @throws Exception
     */
    private void navigationAction(HttpServletRequest request) throws Exception {

        // delete navigation
        if ( request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM).
                equals("delete_navi") &&
                request.getParameter(WikiShareHelper.OBJECT_ID_PARAM) != null) {

            int naviId = Integer.parseInt(
                    request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));

            logger.debug("Deleting navigation = " + naviId);

            // fetch all available navigations
            Navigation navi = new Navigation();
            navi.setId(naviId);
            navigationService.dropNavigation(navi);
        }

        navigationService.setWebappPrefix(
                WikiShareHelper.getWabappPath(request));

        model.put("navigationList", navigationService.getNavigationsList());
    }


    /**
     * Clipboard items for the wiki page model.
     * @param request
     */
    public void clipboardAction(HttpServletRequest request) {

        ClipboardService clipboard = new ClipboardService(request);

        if(request.getParameter(WikiShareHelper.CLIPBOARD) != null &&
            request.getParameter(WikiShareHelper.CLIPBOARD) != "") {

            clipboard.addClipboard(
                    request.getParameter(WikiShareHelper.CLIPBOARD));
        }

        model.put(WikiShareHelper.CLIPBOARD, clipboard);
    }


    public void setWikipageService (PageService wpService) {
        this.pageService = wpService;
    }


    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

}