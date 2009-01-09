package com.amb.wikishare.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.amb.wikishare.domain.Navigation;

public class JdbcNavigationDAO extends SimpleJdbcDaoSupport {

	public List<Navigation> getNavigationsList() throws SQLException { 

		List<Navigation> Navigations = getSimpleJdbcTemplate().query( 
				"select id, name, content from Navigations", 
			new NavigationMapper()); 

		return Navigations;
	}
	
	private static class NavigationMapper implements ParameterizedRowMapper<Navigation> { 
		
		public Navigation mapRow(ResultSet rs, int rowNum) throws SQLException { 
			Navigation navigation = new Navigation(); 
			navigation.setId(rs.getInt("id"));
			navigation.setName(rs.getString("name"));
			navigation.setContent(rs.getString("content"));
			return navigation; 
		} 
	}
	

	public Navigation getNavigation(int id) {
		String SELECT = " SELECT id, name, content "
            + " FROM Navigations"
            + " WHERE id = ?";

		return (Navigation)getSimpleJdbcTemplate().queryForObject(
				SELECT, 
				new NavigationMapper(), 
				id);
	}
	
	public void saveNavigation(Navigation navigation) throws Exception {
		logger.info("Saving Navigation id: " + navigation.getId());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = getSimpleJdbcTemplate().update( 
			"INSERT INTO Navigations (name, content) values (?,?);",
			new Object[] { 
					navigation.getName(), 
					navigation.getContent()
			} ); 
		logger.info("Rows affected: " + count); 
	}
	
	public void dropNavigation(Navigation navigation) throws Exception {
		logger.info("Deleting navigation with id: " + navigation.getId());
		int count = getSimpleJdbcTemplate().update( 
			"DELETE FROM Navigations WHERE ID = ?;",
			new Object[] { navigation.getId() } ); 
		logger.info("Rows affected: " + count); 
	}
	
	public void updateNavigation(Navigation  navigation) throws Exception {
		logger.info("Updating Navigation id: " + navigation.getId());
		
		int count = getSimpleJdbcTemplate().update( 
			"UPDATE Navigations SET name = ?, content = ? WHERE id = ?;",
			new Object[] { 
					navigation.getName(), 
					navigation.getContent(), 
					navigation.getId()
			} ); 
		logger.info("Rows affected: " + count); 
		
	}  
}
