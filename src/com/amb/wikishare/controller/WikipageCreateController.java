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
import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.service.ClipboardService;
import com.amb.wikishare.service.WikipageService;
import com.amb.wikishare.service.UserService;

public class WikipageCreateController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    private WikipageService wpService;
    private FileDAO fileDao;

    private String appContext = "/WikiShare/wiki/";


    public ModelAndView onSubmit(Object command) {
        try {
            if(((Wikipage)command).getSkipNewVersionFlag()) {
                // Page Update
                wpService.updateWikipage((Wikipage) command);
            }else {
                // New Page
                wpService.saveWikipage((Wikipage) command);
            }
        }catch(Exception e) {
            logger.error("onSubmit Exception: " +e);
        }

        String view = appContext + "wikipages";
        String signature = ((Wikipage)command).getSignature();
        if(signature != null) {
            view = appContext + "wikipage/" + signature;
        }
        return new ModelAndView(new RedirectView(view));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        // set app context for later redirection such as /WikiShare/wiki/
        appContext = WikiShareHelper.getWabappPath(request);

        Wikipage wikipage = new Wikipage();

        // New page
        User user = UserService.getSessionUser(request);
        wikipage.setUser(user);

        // Update/Edit action
        if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.UPDATE_PARAM)) {

            try {
                int id = Integer.parseInt(WikiShareHelper.getLastUriResource(request));
                wikipage = wpService.getPage(id);
                wikipage.setUser(user);
                //wikipage.setFiles(fileDao.getFiles());

            } catch(Exception e) {
                logger.debug("[formBackingObject] " + e);
            }
        }

        // provide clipboard for the new or edited page
        ClipboardService clipboard = new ClipboardService(request);
        wikipage.setClipboard(clipboard);

        return wikipage;
    }


    public void setWikipageService (WikipageService wpService) {
        this.wpService = wpService;
    }

    public void setFileDao(FileDAO fileDao) {
        this.fileDao = fileDao;
    }
}