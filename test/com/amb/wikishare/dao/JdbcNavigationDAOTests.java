package com.amb.wikishare.dao;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.User;

import junit.framework.TestCase;

public class JdbcNavigationDAOTests extends TestCase {
	
	DriverManagerDataSource dataSource;
	JdbcNavigationDAO naviDao;
	
	public JdbcNavigationDAOTests() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
		naviDao = new JdbcNavigationDAO();
		naviDao.setDataSource(dataSource);
	}
	
	public void testGetNavigationCase() throws Exception{
		Navigation navi0 = naviDao.getNavigation(0);
		assertNotNull(navi0);
	}

	
}