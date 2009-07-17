package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.NavigationFormBackingObject;
import com.amb.wikishare.service.ClipboardService;
import com.amb.wikishare.service.NavigationService;


public class NavigationCreateController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    private NavigationService navigationService;

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {

        try {
            Navigation navigation = new Navigation((NavigationFormBackingObject)command);
            if(((NavigationFormBackingObject)command).getUpdateFlag() == false) {
                navigationService.saveNavigation(navigation);
            } else {
                navigationService.updateNavigation(navigation);
            }
        } catch(Exception e) {
            logger.error("Error while saving navigation: " + e);
        }

        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        NavigationFormBackingObject navigationFlag = new NavigationFormBackingObject();

        // Extra case: PAGE Update
        if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.UPDATE_PARAM)) {
            try{
                int id = Integer.parseInt(request.getParameter(WikiShareHelper.OBJECT_ID_PARAM));
                navigationFlag = new NavigationFormBackingObject(navigationService.getNavigation(id));
                navigationFlag.setUpdateFlag(true);
            } catch(Exception e) {
                logger.error("Navigation load Error: " + e);
            }
        }

        ClipboardService clipboard = new ClipboardService(request);
        navigationFlag.setClipboard(clipboard);

        return navigationFlag;
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

}