package com.amb.wikishare.dao;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.User;

import junit.framework.TestCase;

public class JdbcNavigationDAOTests extends TestCase {

    JdbcNavigationDAO naviDao = new JdbcNavigationDAO();


    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetNavigationCase() throws Exception{
        naviDao.saveNavigation(new Navigation("test" + Math.random(),"s0"));
        List navigations = naviDao.getNavigationsList();
        assertNotNull(navigations);
    }

}