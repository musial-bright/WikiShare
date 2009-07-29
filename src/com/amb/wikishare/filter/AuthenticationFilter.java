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

    private FilterConfig config;
    private String rootName = null;
    private String rootPassword = null;
    private String passUrl = "";
    private String loginPage = "";
    private String errorPage = "";

    //DriverManagerDataSource dataSource;
    JdbcUserDAO dao = new JdbcUserDAO();

    public void init(FilterConfig config) throws IllegalStateException {
        this.config = config;
        this.rootName = config.getInitParameter("rootname");
        this.rootPassword = config.getInitParameter("password");
        this.passUrl = config.getInitParameter("pass_url");
        this.loginPage = config.getInitParameter("loginPage");
        this.errorPage = config.getInitParameter("errorPage");
    }

    /**
     * Authenticate user against the db user table or the root user in web.xml.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();


        // Get user data
        User user = (User) session.getAttribute(WikiShareHelper.USER);

        // Try to logout
        if (user != null && logout(request,session)) {
            RequestDispatcher rd = config.getServletContext().
                getRequestDispatcher(config.getInitParameter("loginPage"));
                rd.forward(request, response);
        }

        // Try to authenticate
        if ( user == null && !authenticate(request,session) ) {
            if( !passRequestedUrl(httpRequest) ) {

                RequestDispatcher rd =
                    config.getServletContext().getRequestDispatcher(errorPage);

                logger.debug("You are not logged in -> " +
                    "redirecting to <"+ errorPage + ">");

              rd.forward(request, response);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean authenticate(ServletRequest request, HttpSession session) {

        // validate parameters
        if(request.getParameter(WikiShareHelper.ACTION_PARAM) == null) {
            logger.debug("ACTION PARAM is NULL");
            return false;
        }
        if(!request.getParameter(WikiShareHelper.ACTION_PARAM).
                equals(WikiShareHelper.LOGIN) ) {
            logger.debug("ACTION PARAM is NOT login");
            return false;
        }
        if (this.rootName == null && this.rootPassword == null) {
            logger.warn("Root system user not set.");
            return false;
        }

        // authentication
        String username = request.getParameter(WikiShareHelper.USERNAME);
        String password = request.getParameter(WikiShareHelper.PASSWORD);

        try{
            logger.debug("Try to log in : " + username);

            // Root user login
            if(	this.rootName.equals(username) &&
                this.rootPassword.equals(password)) {

                User user = new User();
                user.setUsername(username);

                session.setAttribute(WikiShareHelper.USER, user);
                logger.info("Authenticating as root user: '" + user.getUsername() + "'");
                return true;
            }

            // Regular user login against User-DAO
            User loginUser = dao.getUser(username, security.encript(password));
            if(loginUser != null) {
                session.setAttribute(WikiShareHelper.USER, loginUser);
                logger.info("Authenticating as : '" + username + "'");
                return true;
            }
        } catch(Exception e) {
            logger.error("Authentication failed: ", e);
        }

        return false;
    }

    private boolean logout(ServletRequest request, HttpSession session) {

        try{
            if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
                request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.LOGOUT) ) {

                User user = (User)session.getAttribute(WikiShareHelper.USER);
                session.removeAttribute(WikiShareHelper.USER);

                logger.debug("Logging out " + user.getUsername());
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