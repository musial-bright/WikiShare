package com.amb.wikishare.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.dao.JdbcUserDAO;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.helper.WikiShareHelper;



public class AuthenticationFilter implements Filter {

    Log logger = LogFactory.getLog(this.getClass());

    private FilterConfig config;
    private String rootName = null;
    private String rootPassword = null;
    private String passUrl = "";
    private String loginPage = "";
    private String errorPage = "";

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    JdbcUserDAO dao = new JdbcUserDAO();

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.rootName = config.getInitParameter("rootname");
        this.rootPassword = config.getInitParameter("password");
        this.passUrl = config.getInitParameter("pass_url");
        this.loginPage = config.getInitParameter("loginPage");
        this.errorPage = config.getInitParameter("errorPage");
        logger.debug("Root user name: " + rootName);

        dataSource.setDriverClassName(config.getInitParameter("db_driver"));
        dataSource.setUrl(config.getInitParameter("db_server"));
        dataSource.setUsername(config.getInitParameter("db_user"));
        dataSource.setPassword(config.getInitParameter("db_password"));
        dao.setDataSource(dataSource);
    }

    /**
     * Usage: To authenticate send 'username' and 'password' in
     * the request parameter.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
        HttpSession session = httpRequest.getSession();

        // Get user data
        User user = (User) session.getAttribute(WikiShareHelper.USER);

        // Try to logout
        if (user != null && logout(request,session)) {
            RequestDispatcher rd = config.getServletContext().
                getRequestDispatcher(config.getInitParameter("loginPage"));
                rd.forward(request, responseWrapper);
        }

        // Try to authenticate
        if ( user == null && !authenticate(request,session) ) {
            if( !passRequestedUrl(httpRequest) ) {

                RequestDispatcher rd =
                    config.getServletContext().getRequestDispatcher(errorPage);

                logger.debug("You are not logged in -> " +
                    "redirecting to <"+ errorPage + ">");

              rd.forward(request, responseWrapper);
            }
        }

        chain.doFilter(request, responseWrapper);
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
        User user = new User();
        try{
            logger.debug("Try to log in : " + username + "/" + password);

            // Root user login
            if(	this.rootName.equals(username) &&
                this.rootPassword.equals(password)) {

                user.setUsername(username);

                session.setAttribute(WikiShareHelper.USER, user);
                logger.info("Authenticating as root user: '" + user.getUsername() + "'");
                return true;
            }

            // Regular user login against User-DAO
            user.setUsername(username);
            user.setPassword(password);
            User loginUser = dao.getUserWithId(user);
            if(loginUser != null) {
                session.setAttribute(WikiShareHelper.USER, loginUser);
                logger.info("Authenticating as : '" + user.getUsername() + "'");
                return true;
            }
        } catch(Exception e) {
            logger.error("Authentication failed: " + e);
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
            logger.error("Logout faild: " + e);
        }
        return false;
    }

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
        // TODO Auto-generated method stub
    }


    class ResponseWrapper extends HttpServletResponseWrapper {

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void sendError(int sc) throws IOException {
            // TODO Auto-generated method stub
            super.sendError(sc);
        }

        @Override
        public void setStatus(int sc) {
            // TODO Auto-generated method stub
            super.setStatus(sc);
        }


    }
}