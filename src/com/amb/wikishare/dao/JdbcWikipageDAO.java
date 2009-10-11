package com.amb.wikishare.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.Page;


public class JdbcWikipageDAO  {

    protected final Log logger = LogFactory.getLog(getClass());

    public String allowedOrder = "title asc,title desc,timestamp asc,timestamp desc";
    
    
    /**
     * Allowed pages order seperated by a comma, exp: "title asc,title desc"
     * @param order
     */
    public void setAllowedOrder(String order) {
    	this.allowedOrder = order;
    }
    
    
    /**
     * Get all wiki pages or active only pages.
     * @param activePagesOnly
     * @param frontPagesOnly
     * @return List of active wiki pages
     */
    public List<Page> getWikipagesList(
            boolean activePagesOnly,
            boolean frontPagesOnly,
            String order) {
    	
    	order = validateOrder(order);

        String sql = "select * from pages as page order by " + order;
        Integer activePages = 0;
        if(activePagesOnly) {
            sql = "select * from pages as page where active_page = 1 order by " + order;
        }
        Integer frontPages = 0;
        if(frontPagesOnly) {
            sql = "select * from pages as page where and front_page = 1 order by " + order;
        }
        if(activePagesOnly && frontPagesOnly) {
            sql = "select * from pages as page where active_page = 1 and front_page = 1 order by " + order;
        }

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity("page", Page.class);
        List<Page> wikipages = sqlQuery.list();
        session.getTransaction().commit();
        logger.debug("[getWikipagesList] pages amount = " + wikipages.size());

        return wikipages;
    }

    /**
     * Get all wiki page versions of a certain page.
     * @param pageFamilySignature: Signature of a page family
     * @return List of active wiki pages
     */
    public List<Page> getWikipageVersionsList(String pageFamilySignature) throws SQLException {

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Page where signature = :signature order by timestamp desc");
        query.setString("signature", pageFamilySignature);
        List<Page> wikipages = query.list();
        session.getTransaction().commit();
        logger.debug("[getWikipageVersionsList] pages amount = " + wikipages.size());

        return wikipages;
    }

    public int getWikipageVersionsAmount(String pageFamiliSignature) throws SQLException {

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Page where signature = :signature");
        query.setString("signature", pageFamiliSignature);
        Long amount = (Long)query.uniqueResult();
        session.getTransaction().commit();
        logger.debug("[getWikipageVersionsAmount] page versions amount for "+ pageFamiliSignature+ "=" + amount);

        return amount.intValue();
    }

    private static class WikipageMapper implements ParameterizedRowMapper<Page> {

        public Page mapRow(ResultSet rs, int rowNum) throws SQLException {

            Page wikipage = new Page();
            wikipage.setId(rs.getInt("id"));
            wikipage.setSignature(rs.getString("signature"));
            wikipage.setUserId(rs.getInt("user_id"));
            wikipage.setActivePage(rs.getInt("active_page"));
            wikipage.setFrontPage(rs.getInt("front_page"));
            wikipage.setTitle(rs.getString("title"));
            wikipage.setContent(rs.getString("content"));
            wikipage.setDate(rs.getTimestamp("timestamp"));

            return wikipage;
        }
    }


    public Page getPage(int id) {

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Page page = (Page) session.get(Page.class, id);
        session.getTransaction().commit();
        logger.debug("[getPage] page id="+ id);

        return page;
    }

    /**
     * Get wiki page by signature. Only the active page will be returned.
     * @param signature something like s<signature>
     * @return Page active wiki page
     */
    public Page getActivePageBySignature(String signature) {

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Page where active_page = 1 and signature = :signature");
        query.setString("signature", signature);
        List<Page> pages = query.list();

        logger.debug("[getActivePageBySignature] page signature="+ signature);

        if(pages.isEmpty()) {
            return null;
        }
        return pages.get(0);
    }

    public void saveWikipage(Page wikipage) throws Exception {
        logger.debug("[saveWikipage] " + wikipage.getTitle());
        
        deactiveteOtherVersions(wikipage.getSignature());
        
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        wikipage.setDate(new Date());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(wikipage);
        session.getTransaction().commit();
    }


    public void dropWikipage(Page wikipage) throws Exception {
        logger.debug("[dropWikipage] " + wikipage.getTitle());

        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(wikipage);
        session.getTransaction().commit();
    }


    public void updateWikipage(Page wikipage) throws Exception {
        logger.debug("[updateWikipage] " + wikipage.getTitle());

        deactiveteOtherVersions(wikipage.getSignature());
        
        wikipage.setDate(new Date());
        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(wikipage);
        session.getTransaction().commit();
    }


    public List<Page> search(String searchText) {
        logger.debug("[search] " + searchText);
        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Page where " +
                "active_page = 1 and " +
                "(title like :searchText or content like :searchText) " +
                "order by timestamp desc");
        query.setString("searchText", "%" + searchText + "%");
        List<Page> pages = query.list();
        session.getTransaction().commit();

        return pages;
    }

    /**
     * Deactivate all wiki pages.
     * Use is to deactivate viki pages of an certain verison, for example in case
     * or wiki page update or store.
     * @param signature : wiki page family (versions) id
     */
    private void deactiveteOtherVersions(String signature) {
        logger.debug("[deactiveteOtherVersions] signature=" + signature);
        Session session = HibernateFactory.sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        SQLQuery query = session.createSQLQuery("update pages set active_page = 0 where signature = :signature");
        query.setString("signature", signature);
        query.executeUpdate();
        
        session.getTransaction().commit();
    }
    
    protected String validateOrder(String order) {
    	String orderList[] = allowedOrder.split(",");
    	for(String o : orderList) {
	    	if (order.equalsIgnoreCase(o)) {
	    		return o;
	    	}
    	}
    	return "title asc";
    }

}