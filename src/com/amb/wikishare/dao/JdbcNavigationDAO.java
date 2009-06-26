package com.amb.wikishare.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.classic.Session;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.User;

public class JdbcNavigationDAO {

    protected final Log logger = LogFactory.getLog(getClass());

    public List<Navigation> getNavigationsList() {
        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Navigation> navigations = session.createQuery("from Navigation").list();
        session.getTransaction().commit();
        return navigations;
    }

    public Navigation getNavigation(int id) {
        logger.debug("[getNavigation] id=" + id);

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Navigation navigation = (Navigation)session.get(Navigation.class, id);
        session.getTransaction().commit();

        return navigation;
    }

    public void saveNavigation(Navigation navigation) {
        logger.info("[saveNavigation] " + navigation.getName());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(navigation);
        session.getTransaction().commit();
    }

    public void dropNavigation(Navigation navigation) {
        logger.info("[dropNavigation] " + navigation.getName());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(navigation);
        session.getTransaction().commit();
    }

    public void updateNavigation(Navigation  navigation) {
        logger.info("[updateNavigation] " + navigation.getName());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(navigation);
        session.getTransaction().commit();
    }
}
