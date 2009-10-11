package com.amb.wikishare.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.service.NavigationService;
import com.amb.wikishare.service.PageService;

public class FrontpageController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private Map<String, Object> model = new HashMap<String, Object>();
    private PageService wpService = null;
    private NavigationService navigationService = null;

    public ModelAndView handleRequest(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            model.put("pages", wpService.getPagesList(true, true));
            navigationAction(request);
        } catch (Exception e) {
            logger.error("[handleRequest] " + e);
        }

        return new ModelAndView("frontpage", "model", model);
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

            logger.debug("[navigationAction]ÊDeleting navigation = " + naviId);

            // fetch all available navigations
            Navigation navi = new Navigation();
            navi.setId(naviId);
            navigationService.dropNavigation(navi);
        }

        navigationService.setWebappPrefix(
                WikiShareHelper.getWabappPath(request));

        model.put("navigationList", navigationService.getNavigationsList());
    }


    public void setWikipageService(PageService wpService) {
        this.wpService = wpService;
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
