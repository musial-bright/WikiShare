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
	
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	JdbcUserDAO dao = new JdbcUserDAO();
	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.rootName = config.getInitParameter("rootname");
		this.rootPassword = config.getInitParameter("password");
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
		
		String uri = "";
		if( request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			
			uri = httpRequest.getRequestURI();
			HttpSession session = httpRequest.getSession();
			User user = (User) session.getAttribute("user");
			
			// Authenticate or Forward to login page
			if (user == null) {
				if(!authenticate(request,session)) {
					logger.warn("You are not logged in -> redirecting to the login page!");
					
					RequestDispatcher rd = config.getServletContext().
						getRequestDispatcher(config.getInitParameter("loginPage"));
					rd.forward(request, response);
					
				} else {

					// TODO: Goto requested URI before login
					/*
					logger.debug("Redirecting to : " + uri);
					RequestDispatcher rd = config.getServletContext().
					getRequestDispatcher(uri);
					rd.forward(request, response);
					*/
				}
			} else {
				// User already authenticated
				if( !logout(request,session) ) {
					logger.info("You are logged in as '" + user.getUsername() + "'");
				} else {
					// Redirect after logout
					RequestDispatcher rd = config.getServletContext().
						getRequestDispatcher(config.getInitParameter("loginPage"));
				rd.forward(request, response);
				}
			}

		} 
		chain.doFilter(request, response);
	}

	private boolean authenticate(ServletRequest request, HttpSession session) {
		
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
			
			// Try to login root user
			if(	this.rootName.equals(username) && 
				this.rootPassword.equals(password)) {
				user.setUsername(username);
			
				session.setAttribute(WikiShareHelper.USER, user);
				logger.info("Authenticating as root user: '" + user.getUsername() + "'");
				return true;
			} 
			
			// Login regular user from DB
			user.setUsername(username);
			user.setPassword(password);
			User fetchedUser = dao.getUserWithId(user);
			if(fetchedUser != null) {
				session.setAttribute(WikiShareHelper.USER, fetchedUser);
				logger.info("Authenticating as : '" + user.getUsername() + "'");
				return true;
			}
		} catch(Exception e) { logger.error("Authentication failed: " + e);}
		return false;
	}
	
	private boolean logout(ServletRequest request, HttpSession session) {
		try{
			if(request.getParameter(WikiShareHelper.ACTION_PARAM) != null &&
				request.getParameter(WikiShareHelper.ACTION_PARAM).equals(WikiShareHelper.LOGOUT) ) {
				session.removeAttribute(WikiShareHelper.USER);
				return true;
			}
		} catch(Exception e) { logger.error("Logout faild: " + e);}
		return false;
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}