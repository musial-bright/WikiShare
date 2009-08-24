package com.amb.wikishare.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.amb.wikishare.app.BeanFactory;
import com.amb.wikishare.app.Security;
import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.dao.JdbcUserDAO;
import com.amb.wikishare.domain.User;


public class AuthenticationFilter implements Filter {

    Log logger = LogFactory.getLog(this.getClass());

    private Security security = new Security();
    
    private final String COOKIE_NAME = "sso";
    private final String SEPARATOR = "_";
    private final int SECONDS_PER_YEAR = 60*60*24*365;

    private FilterConfig config;
    private String salt = null;
    private String rootName = null;
    private String rootPassword = null;
    private String passUrl = "";
    private String loginPage = "";
    private String errorPage = "";

    JdbcUserDAO dao = new JdbcUserDAO();

    public void init(FilterConfig config) throws IllegalStateException {
        this.config = config;
        salt = config.getInitParameter("salt");
        rootName = config.getInitParameter("rootname");
        rootPassword = config.getInitParameter("password");
        passUrl = config.getInitParameter("pass_url");
        loginPage = config.getInitParameter("loginPage");
        errorPage = config.getInitParameter("errorPage");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();

        if (checkLoggedIn(session)) {
	        // Try to logout
	        logout(httpRequest,httpResponse, session);
        } else {
	        // Try to authenticate
	        if ( !authenticate(httpRequest, httpResponse, session) && !passRequestedUrl(httpRequest) ) {
	
	            RequestDispatcher rd =
	                config.getServletContext().getRequestDispatcher(errorPage);
	
	            logger.debug("You are not logged in -> " +
	                "redirecting to <"+ errorPage + ">");
	
	            rd.forward(httpRequest, httpResponse);
	        }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    private boolean checkLoggedIn(HttpSession session) {
    	if ((User)session.getAttribute(WikiShareHelper.USER) == null) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * Authenticate user against the db user table or the root user in web.xml.
     */
    private boolean authenticate(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

    	if (checkLoggedIn(session)) { return true; }
    	
        if (!WikiShareHelper.paramEquals(request, WikiShareHelper.ACTION_PARAM, WikiShareHelper.LOGIN)) {
        	
        	// try to login via sso cookie
        	User user = getUserFromSsoCookie(request);
        	if (user != null) {
        		session.setAttribute(WikiShareHelper.USER, user);
        		return true;
        	} else {
        		return false;
        	}
        }
        
        if (this.rootName == null && this.rootPassword == null) {
            logger.warn("Root system user not set.");
            return false;
        }

        // authentication
        String username = WikiShareHelper.getParam(request, WikiShareHelper.USERNAME);
        if (username == "") {
        	return false;
        }
        
        String password = WikiShareHelper.getParam(request, WikiShareHelper.PASSWORD);

        try {
            logger.debug("Try to log in : " + username);

            // Root user login
            if(	rootName.equals(username) && rootPassword.equals(password)) {

                User user = new User();
                user.setUsername(username);

                session.setAttribute(WikiShareHelper.USER, user);
                logger.info("Authenticating as root user: '" + user.getUsername() + "'");
                return true;
            }

            // Regular user login against user dao
            User loginUser = dao.getUser(username, security.encript(password));
            if(loginUser != null) {
                session.setAttribute(WikiShareHelper.USER, loginUser);
                logger.info("Authenticating as : '" + username + "'");
                setSsoCookieForUser(response, loginUser);
                return true;
            }
        } catch(Exception e) {
            logger.error("Authentication failed: ", e);
        }
        return false;
    }
    
    private User getUserFromSsoCookie(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	if (cookies == null) {
    		return null;
    	}
    	for (Cookie cookie : cookies) {
    		if (cookie.getName().equals(COOKIE_NAME)) {
    			try {
	    			String value = cookie.getValue();
	    			String id = value.substring(0, value.indexOf(SEPARATOR));
	    			
	    			User user = dao.getUser(Integer.valueOf(id).intValue());
	    			String compareValue = encriptUser(user.getId(), user.getUsername());
	    			
	    			if (value.equals(compareValue)) {
	    				return user;
	    			}
	    			
    			} catch (Exception e) {
    				logger.error("[validateSsoCookie] ", e);
    			}
    		}
    	}
    	return null;
    }
    
    private void setSsoCookieForUser(HttpServletResponse response, User user) {
    	Cookie ssoCookie = new Cookie(COOKIE_NAME, encriptUser(user.getId(), user.getUsername()));
    	ssoCookie.setPath("/");
    	ssoCookie.setMaxAge(SECONDS_PER_YEAR);
    	response.addCookie(ssoCookie);
    }
    
    private void removeSsoCookie(HttpServletRequest request, HttpServletResponse response) {
    	for (Cookie cookie : request.getCookies()) {
    		if (cookie.getName().equals(COOKIE_NAME)) {
    			cookie.setMaxAge(0);
    			cookie.setPath("/");
    			response.addCookie(cookie);
    			logger.debug("[removeSsoCookie] " + cookie.getName() + "/" + cookie.getMaxAge());
    		}
    	}
    }
    
    private String encriptUser(int userId, String userName) {
    	return userId + SEPARATOR + security.encript(userName + salt);
    }

    private boolean logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

    	if (!checkLoggedIn(session)) { return false; }
        try {
            if( WikiShareHelper.paramEquals(request, WikiShareHelper.ACTION_PARAM, WikiShareHelper.LOGOUT) ) {
                session.removeAttribute(WikiShareHelper.USER);
                removeSsoCookie(request, response);
                
                // redirect to login page
                RequestDispatcher rd = config.getServletContext().
                	getRequestDispatcher(config.getInitParameter("loginPage"));
                	rd.forward(request, response);
                return true;
            }
        } catch(Exception e) {
            logger.error("Logout faild: ", e);
        }
        return false;
    }

    /**
     * Check requested resource restrictions.
     * @param request
     * @return boolean
     */
    protected boolean passRequestedUrl(HttpServletRequest request) {
        if(request.getRequestURI().matches(passUrl)) {
            logger.debug("Passing url " + request.getRequestURI());
            return true;
        }
        logger.debug("URL forbidden: " + request.getRequestURI());
        return false;
    }

    public void setPassUrl(String passUrl) {
        this.passUrl = passUrl;
    }


    public void destroy() {
    }
}