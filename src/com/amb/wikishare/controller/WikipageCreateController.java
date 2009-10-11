package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.dao.FileDAO;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.domain.Page;
import com.amb.wikishare.domain.PageFormBackingObject;
import com.amb.wikishare.service.ClipboardService;
import com.amb.wikishare.service.PageService;
import com.amb.wikishare.service.UserService;

public class WikipageCreateController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    private PageService wpService;
    private FileDAO fileDao;

    private String appContext = "/WikiShare/wiki/";


    public ModelAndView onSubmit(Object command) {
        try {
            Page wikipage = new Page((PageFormBackingObject)command);
            if(((PageFormBackingObject)command).getSkipNewVersionFlag()) {
                // Page Update
                wpService.updatePage(wikipage);
            }else {
                // New Page
                wpService.savePage(wikipage);
            }
        }catch(Exception e) {
            logger.error("onSubmit Exception: " +e);
        }

        String view = appContext + "wikipages";
        String signature = ((Page)command).getSignature();
        if(signature != null) {
            view = appContext + "wikipage/" + signature;
        }
        return new ModelAndView(new RedirectView(view));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        // set app context for later redirection such as /WikiShare/wiki/
        appContext = WikiShareHelper.getWabappPath(request);

        PageFormBackingObject wikipageForm = new PageFormBackingObject();


        // Update/Edit action
        if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.UPDATE_PARAM)) {

            try {
                int id = Integer.parseInt(WikiShareHelper.getLastUriResource(request));
                wikipageForm = new PageFormBackingObject(wpService.getPage(id));
                //wikipage.setFiles(fileDao.getFiles());

            } catch(Exception e) {
                logger.debug("[formBackingObject] " + e);
            }
        }

        // New page
        User user = UserService.getSessionUser(request);
        if(user != null) {
            wikipageForm.setUserId(user.getId());
        }


        // provide clipboard for the new or edited page
        ClipboardService clipboard = new ClipboardService(request);
        wikipageForm.setClipboard(clipboard);

        return wikipageForm;
    }


    public void setWikipageService (PageService wpService) {
        this.wpService = wpService;
    }

    public void setFileDao(FileDAO fileDao) {
        this.fileDao = fileDao;
    }
}