package com.amb.wikishare.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amb.wikishare.app.Security;
import com.amb.wikishare.app.WikiShareHelper;
import com.amb.wikishare.dao.JdbcUserDAO;
import com.amb.wikishare.domain.User;

/**
 * User service provides user management (CRUD) and
 * user session access.
 * @author amusial
 *
 */
public class UserService implements UserInterface {

    protected final Log logger = LogFactory.getLog(getClass());
    private JdbcUserDAO userDao;
    private Security security = new Security();

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

    public User getUserWithEmptyPassword(int id) throws Exception {
        User user = userDao.getUser(id);
        // Removing users password because it is encripted
        //user.setPassword("");
        return user;
    }

    public List<User> getUsersList() throws Exception {
        return userDao.getUsersList();
    }

    public void saveUser(User user) throws Exception {
        user.setPassword(security.encript(user.getPassword()));
        userDao.saveUser(user);
    }

    public void updateUser(User user) throws Exception {
        user.setPassword(security.encript(user.getPassword()));
        userDao.updateUser(user);
    }

    public static User getSessionUser(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(WikiShareHelper.USER);
        return user;
    }

}
