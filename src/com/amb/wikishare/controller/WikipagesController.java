package com.amb.wikishare.controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.domain.Page;
import com.amb.wikishare.service.PageService;


public class WikipagesController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private HttpSession session;
    private PageService wpService;
    private final String SHOW_ERRORS = "showError";
    private final String TEMPLATE = "wikipage";
    private Map<String, Object> model = new HashMap<String, Object>();


    /**
     * Show all wikipages.
     *
     * Request: /WikiPage/wiki/wikipages
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // User from session
        session = request.getSession();
        User user = (User)session.getAttribute(WikiShareHelper.USER);

        // Actions
        actionDispatcher(request);

        String now = (new Date()).toString();
        this.model.put("selfUrl", this.TEMPLATE);
        this.model.put("pageTitle", "Default Wiki Page");
        this.model.put("now", now);


        // Page list
        try {
            if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
               request.getParameter(WikiShareHelper.ACTION_PARAM).equals("showAllPageVersions")) {
                this.model.put("pages", this.wpService.getPagesList(false,false));
            } else {
                this.model.put("pages", this.wpService.getPagesList(true,false));
            }
        } catch (Exception e) {
            logger.error("WikipageService.getPagesList() " + e);
            this.model.put(SHOW_ERRORS, true);
            this.model.put("dbConnectionError", "DB connection error");
        }

        return new ModelAndView("wikipages", "model", this.model);
    }

    private void actionDispatcher(HttpServletRequest request) {
        String action = request.getParameter(WikiShareHelper.ACTION_PARAM);
        if(action != null && action.equals(WikiShareHelper.DELETE_PARAM)) {
            deleteAction(request);
        }
    }

    /**
     * Delete a wikipage.
     * @param request
     */
    private void deleteAction(HttpServletRequest request) {
        try{
            int page_id = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
            this.wpService.dropPage(new Page(page_id));
        } catch(Exception e) {
            logger.error("Wiki page ID Error: " + e);
        }
    }

    public void setWikipageService (PageService wpService) {
        this.wpService = wpService;
    }

}