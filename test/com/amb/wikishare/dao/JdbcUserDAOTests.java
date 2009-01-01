package com.amb.wikishare.dao;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.User;

import junit.framework.TestCase;

public class JdbcUserDAOTests extends TestCase {
	
	DriverManagerDataSource dataSource;
	
	public void testUserCases() throws Exception{
		
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
		JdbcUserDAO dao = new JdbcUserDAO();
		dao.setDataSource(dataSource);
		List<User> users = dao.getUsersList();
		assertNotNull(users);

		User user0 = dao.getUser(0);
		assertEquals("admin", user0.getUsername());
		
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
		
		user0.setPassword("");
		dao.updateUser(user0);
		User updatedUser0 = dao.getUser(user0.getId());
		assertEquals("", updatedUser0.getPassword());
		
		User userWithIdenticalId = dao.getUserWithId(user0);
		assertEquals(user0.getId(), userWithIdenticalId.getId());
	}
	
}