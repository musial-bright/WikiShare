package com.amb.wikishare.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.amb.wikishare.domain.User;
import com.amb.wikishare.service.UserService;
import com.amb.wikishare.helper.WikiShareHelper;

public class UserCreateController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    private UserService userService;

    private String appContext = "/WikiShare/wiki/";

    public ModelAndView onSubmit(Object command) {

        try {
            if(((User)command).getUpdateUserFlag()) {
                userService.updateUser((User)command);
            } else {
                userService.saveUser((User) command);
            }
        }catch(Exception e) {
            logger.error("onSubmit Exception: " +e);
        }
        return new ModelAndView(new RedirectView(appContext + "users"));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        appContext = WikiShareHelper.getWabappContext(request);

        User user = new User();

        if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM)
                .equals(WikiShareHelper.UPDATE_PARAM) ) {

            // Update existing user
            try{
                int id = Integer.parseInt(
                        WikiShareHelper.getLastUriResource(request));

                user = userService.getUserWithEmptyPassword(id);
            } catch(Exception e) {
                logger.error("User Error: " + e);
            }
            user.setUpdateUserFlag(true);
        }
        return user;
    }

    public void setWikipageService (UserService userService) {
        this.userService = userService;
    }
}