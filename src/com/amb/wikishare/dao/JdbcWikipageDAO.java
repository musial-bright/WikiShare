package com.amb.wikishare.dao;

import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List; 
import java.util.Random;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource; 
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper; 
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport; 

import com.amb.wikishare.domain.Wikipage;
import com.amb.wikishare.service.WikipageInterface;
import com.amb.wikishare.service.UserService;
import com.amb.wikishare.domain.User;


public class JdbcWikipageDAO extends SimpleJdbcDaoSupport implements WikipageInterface {


	private String PAGES_COLS = 
		"id, signature, " +
		"user_id, " +
		"active_page, " +
		"front_page, " +
		"title, " +
		"content, " +
		"timestamp";
	
	/**
	 * Get all wiki pages or active only pages.
	 * @param activePagesOnly
	 * @param frontPagesOnly
	 * @return List of active wiki pages 
	 */
	public List<Wikipage> getWikipagesList(
			boolean activePagesOnly, 
			boolean frontPagesOnly) throws SQLException { 

		String query = 
			"select " + PAGES_COLS + " from pages ";
		if(activePagesOnly) {
			query += "where active_page = 1 ";
		}
		if(frontPagesOnly) {
			query += "and front_page = 1 ";
		}
		query += "order by title asc";
		List<Wikipage> wikipages = getSimpleJdbcTemplate().query( query,new WikipageMapper()); 
		
		return wikipages;
	} 

	/**
	 * Get all wiki page versions of a certain page.
	 * @param pageFamilySignature: Signature of a page family
	 * @return List of active wiki pages 
	 */
	public List<Wikipage> getWikipageVersionsList(String pageFamilySignature) throws SQLException { 
		List<Wikipage> wikipages = getSimpleJdbcTemplate().query( 
				"select  "+ PAGES_COLS + " from pages "+
				"where signature = ? "+
				"order by timestamp desc",
			new WikipageMapper(),
			pageFamilySignature); 
		
		return wikipages;
	} 
	
	public int getWikipageVersionsAmount(String pageFamiliSignature) throws SQLException {
		return getSimpleJdbcTemplate().queryForInt(
				"select count(*) from pages where signature = ?", 
				pageFamiliSignature);
	}
	
	private static class WikipageMapper implements ParameterizedRowMapper<Wikipage> { 
		
		public Wikipage mapRow(ResultSet rs, int rowNum) throws SQLException { 

			Wikipage wikipage = new Wikipage(); 
			wikipage.setId(rs.getInt("id"));
			wikipage.setSignature(rs.getString("signature"));
			wikipage.setUser(new User(rs.getInt("user_id")));
			wikipage.setActivePage(rs.getInt("active_page"));
			wikipage.setFrontPage(rs.getInt("front_page"));
			wikipage.setTitle(rs.getString("title")); 
			wikipage.setContent(rs.getString("content"));
			wikipage.setDate(rs.getTimestamp("timestamp"));
			
			return wikipage; 
		} 
	}
	
	
	public Wikipage getPage(int id) {
		String SELECT = " SELECT " + PAGES_COLS + " FROM pages"
            + " WHERE id = ?";

		Wikipage wikipage = (Wikipage)getSimpleJdbcTemplate().queryForObject(
				SELECT, 
				new WikipageMapper(), 
				id);
				
		return wikipage;
	}
	
	/**
	 * Get wiki page by signature. Only the active page will be returned.
	 * @param signature something like s<signature>
	 * @return Wikipage active wiki page
	 */
	public Wikipage getPageBySignature(String signature) {
		/*
		int id = -1;
		try {
			id = Integer.valueOf(signature.substring(1, signature.length())).intValue();
		} catch (Exception e) {
			logger.debug("Siganture was not valid -> " + signature);
			return null;
		}
		*/
		String SELECT = "SELECT " + PAGES_COLS + " FROM pages " +
            "WHERE active_page = 1 and signature = ?";

		Wikipage wikipage = (Wikipage)getSimpleJdbcTemplate().queryForObject(
				SELECT, 
				new WikipageMapper(), 
				signature);
				
		return wikipage;
	}

	public void saveWikipage(Wikipage wikipage) throws Exception {
		logger.info("Saving page: " + wikipage.getTitle());

		deactiveteOtherVersions(wikipage.getSignature());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int userId = getUserIdFromWikpage(wikipage);
		int count = getSimpleJdbcTemplate().update( 
			"INSERT INTO pages (user_id, signature, active_page, front_page, title, content, timestamp) " +
			"values (?,?,?,?,?,?,?);",
			new Object[] { 
					userId,
					wikipage.getSignature(),
					1,
					wikipage.getFrontPage(),
					wikipage.getTitle(), 
					wikipage.getContent(), 
					dateFormat.format(new Date())
			} ); 
		logger.info("Rows affected: " + count); 
	}


	public void dropWikipage(Wikipage wikipage) throws Exception {
		int count = getSimpleJdbcTemplate().update( 
			"DELETE FROM pages WHERE ID = ?;",
			new Object[] { wikipage.getId() } );
	}


	public void updateWikipage(Wikipage wikipage) throws Exception {
		logger.info("Updating page: " + wikipage.getTitle());
		
		deactiveteOtherVersions(wikipage.getSignature());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int userId = getUserIdFromWikpage(wikipage);
		int count = getSimpleJdbcTemplate().update( 
			"UPDATE pages SET active_page = 1, front_page = ?, user_id = ?, title = ?, content = ?, timestamp = ? " +
			"WHERE id = ?;",
			new Object[] { 
					wikipage.getFrontPage(),
					userId,
					wikipage.getTitle(), 
					wikipage.getContent(), 
					dateFormat.format(new Date()),
					wikipage.getId()
			} ); 
		logger.info("Rows affected: " + count); 
	}  

	
	public List<Wikipage> search(String searchText) {
		
		String SELECT = " SELECT " + PAGES_COLS + " FROM pages "+ 
			"WHERE (title like ? or content like ?) and active_page = 1";

		return getSimpleJdbcTemplate().query(
				SELECT, 
				new WikipageMapper(), 
				"%"+searchText+"%",
				"%"+searchText+"%");
	}
	
	/**
	 * Deactivate all wiki pages.
	 * Use is to deactivate viki pages of an certain verison, for example in case
	 * or wiki page update or store. 
	 * @param signature : wiki page family (versions) id
	 */
	private void deactiveteOtherVersions(String signature) {
		int count = getSimpleJdbcTemplate().update( 
				"UPDATE pages SET active_page = 0 WHERE signature = ?;",
				new Object[] { signature } 
			); 
	}
	
	/**
	 * While loading a wiki page, the user comes from the DB.
	 * In case of creating or updating a new wiki page, the user will be 
	 * recived from the session in the <code>WikipageCreateController</code> 
	 * an stored in the <cond>Wikipage</code> form backing object.
	 * @param wikipage
	 * @return
	 */
	private int getUserIdFromWikpage(Wikipage wikipage) {
		int userId = -1;
		try {
			userId = wikipage.getUser().getId();
		} catch(Exception e) {
			logger.debug("No user id for wiki page " + wikipage.getTitle());
		}
		logger.debug("getUserIdFromWikpage user id = " + userId);
		return userId;
	}


}