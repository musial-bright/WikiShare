package com.amb.wikishare.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.User;
import com.amb.wikishare.service.UserInterface;

/**
 * User dao based on hibernate.
 * @author amusial
 */
public class JdbcUserDAO implements UserInterface {

    protected final Log logger = LogFactory.getLog(getClass());

    public List<User> getUsersList() {
        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        return users;
    }

    public User getUser(int id) {
        logger.debug("[getUser] id=" + id);

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.get(User.class, id);
        session.getTransaction().commit();

        return user;
    }

    /**
     * Get user reprersentation by name and passowrd.
     * @param user User object with name and password
     * @return User representation
     */
    public User getUser(String username, String password) {
        logger.debug("[getUser] username=" + username);

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();

        SQLQuery query =
            session.createSQLQuery("select * from users as user where name = :name and password = :password");
        query.addEntity("user", User.class);
        query.setString("name", username);
        query.setString("password", password);
        List<User> users = query.list();
        session.getTransaction().commit();

        if(users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public void saveUser(User user) {
        logger.info("[saveUser] " + user.getUsername());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public void dropUser(User user) {
        logger.info("[dropUser] " + user.getUsername());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

    public void updateUser(User  user) {
        logger.info("[updateUser] " + user.getUsername());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }
}