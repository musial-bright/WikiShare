package com.amb.wikishare.dao;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.User;

import junit.framework.TestCase;

public class JdbcUserDAOTests extends TestCase {
	
	DriverManagerDataSource dataSource;
	JdbcUserDAO dao;
	
	public JdbcUserDAOTests() {
		dataSource = new DriverManagerDataSource();
		//dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		//dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
		//dataSource.setUsername("sa");
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/wikishare_test");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		
	}
	
	public void testCreateUserCases() throws Exception{
		dao = new JdbcUserDAO();
		dao.setDataSource(dataSource);
		
		User user1 = new User();
		user1.setUsername("testuser");
		user1.setPassword("test");
		
		dao.saveUser(user1);
		List<User> users = dao.getUsersList();

		assertNotNull(users);
	}
	
	public void testUsernameCases() throws Exception{
		User user0 = dao.getUser(0);
		assertEquals("testuser", user0.getUsername());
	}
	
	public void testUserCases() throws Exception{
		
		List<User> users = dao.getUsersList();
		String newUserName = "testuser";
		User newUser = new User();
		newUser.setUsername(newUserName);
		newUser.setPassword("testuserpassword");
		dao.saveUser(newUser);
		users = dao.getUsersList();
		int testuserid = -1; 
		for(User u : users) {
			if(u.getUsername().equals(newUserName)) {
				testuserid = u.getId();
			}
		}
		User loadUser = dao.getUser(testuserid);
		assertNotNull(loadUser);
		assertEquals(loadUser.getUsername(), newUserName);
		dao.dropUser(loadUser);
		
	}
	
}