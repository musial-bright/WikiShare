package com.amb.wikishare.dao;

import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; 

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource; 
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper; 
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport; 

import com.amb.wikishare.domain.User;
import com.amb.wikishare.service.UserInterface;


public class JdbcUserDAO extends SimpleJdbcDaoSupport implements UserInterface {

	public List<User> getUsersList() throws SQLException { 

		List<User> users = getSimpleJdbcTemplate().query( 
				"select id, name, password, timestamp from users", 
			new UserMapper()); 

		return users;
	} 

	
	private static class UserMapper implements ParameterizedRowMapper<User> { 
		
		public User mapRow(ResultSet rs, int rowNum) throws SQLException { 
			User user = new User(); 
			user.setId(rs.getInt("id")); 
			user.setUsername(rs.getString("name")); 
			user.setPassword(rs.getString("password"));
			return user; 
		} 
	}
	
	
	public User getUser(int id) {
		String SELECT = " SELECT id, name, password, timestamp "
            + " FROM users"
            + " WHERE id = ?";

		return (User)getSimpleJdbcTemplate().queryForObject(
				SELECT, 
				new UserMapper(), 
				id);
	}

	/**
	 * Get user reprersentation by name and passowrd.
	 * @param user User object with name and password
	 * @return User representation
	 */
	public User getUserWithId(User user) {
		String SELECT = " SELECT id, name, password, timestamp "
            + " FROM users"
            + " WHERE name = ? and password = ?";

		logger.debug("Looking for user " + user.getUsername() + "/" + user.getPassword());
		
		return (User)getSimpleJdbcTemplate().queryForObject(
				SELECT, 
				new UserMapper(), 
				user.getUsername(),
				user.getPassword());
	}

	public void saveUser(User user) throws Exception {
		logger.info("Saving user: " + user.getUsername());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = getSimpleJdbcTemplate().update( 
			"INSERT INTO users (name, password, timestamp) values (?,?,?);",
			new Object[] { 
					user.getUsername(), 
					user.getPassword(), 
					dateFormat.format(new Date())
			} ); 
		logger.info("Rows affected: " + count); 
	}


	public void dropUser(User user) throws Exception {
		logger.info("Saving user: " + user.getUsername());
		int count = getSimpleJdbcTemplate().update( 
			"DELETE FROM users WHERE ID = ?;",
			new Object[] { user.getId() } ); 
		logger.info("Rows affected: " + count); 
	}


	public void updateUser(User  user) throws Exception {
		logger.info("Updating user: " + user.getUsername());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = getSimpleJdbcTemplate().update( 
			"UPDATE users SET name = ?, password = ?, timestamp = ? WHERE id = ?;",
			new Object[] { 
					user.getUsername(), 
					user.getPassword(), 
					dateFormat.format(new Date()),
					user.getId()
			} ); 
		logger.info("Rows affected: " + count); 
		
	}  

	
}
