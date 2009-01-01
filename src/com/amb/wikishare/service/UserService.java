package com.amb.wikishare.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.dao.JdbcUserDAO;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.helper.WikiShareHelper;

public class UserService implements UserInterface {

	protected final Log logger = LogFactory.getLog(getClass()); 
	private JdbcUserDAO userDao;
	
	public void setJdbcUserDAO(JdbcUserDAO userDao) { 
		this.userDao = userDao; 
	} 
	
	public void dropUser(User user) throws Exception {
		if(user != null && user.getId() != -1) {
			userDao.dropUser(user);
		} else {
			logger.info("user.id is empty -> this user will not be deleted.");
		} 
	}

	public User getUser(int id) throws Exception {
		return userDao.getUser(id);
	}

	public List<User> getUsersList() throws Exception {
		return userDao.getUsersList();
	}

	public void saveUser(User user) throws Exception {
		userDao.saveUser(user);
	}

	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}
	
	public static User getSessionUser(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(WikiShareHelper.USER);
		return user;
	}

}
