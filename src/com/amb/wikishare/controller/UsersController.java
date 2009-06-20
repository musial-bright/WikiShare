package com.amb.wikishare.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.service.UserService;


public class UsersController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private UserService userService;
    private Map<String, Object> model = new HashMap<String, Object>();


    /**
     * Get all uers.
     *
     * /WikiShare/wiki/users
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Actions
        deleteAction(request);

        // User list
        try {
            this.model.put("users", this.userService.getUsersList());
        } catch (Exception e) {
            logger.error("UserService.getUserList() " + e);
            this.model.put("dbConnectionError", "DB connection error");
        }

        return new ModelAndView("users", "model", this.model);
    }
/*
    private void actionDispatcher(HttpServletRequest request) {

        String action = request.getParameter(WikiShareHelper.ACTION_PARAM);
        if(action != null && action.equals(WikiShareHelper.DELETE_PARAM)) {
            deleteAction(request);
        }
    }
*/
    /**
     * Delete a user.
     *
     * Request: /WikiShare/wiki/users/<user-id>&action=delete
     * @param request
     */
    private void deleteAction(HttpServletRequest request) {

        String action = request.getParameter(WikiShareHelper.ACTION_PARAM);
        if(action != null && action.equals(WikiShareHelper.DELETE_PARAM)) {
            try{
                int objectId = Integer.parseInt(
                        WikiShareHelper.getLastUriResource(request));

                this.userService.dropUser(new User(objectId));
            } catch(Exception e) {
                logger.debug("[deleteAction] " + e);
            }
        }
    }

    public void setUserService (UserService userService) {
        this.userService = userService;
    }

}