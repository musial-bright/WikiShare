package com.amb.wikishare.dao;

import java.util.List;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.domain.User;
import com.amb.wikishare.dao.JdbcUserDAO;

import junit.framework.TestCase;

public class JdbcUserDAOTests extends TestCase {

    private DriverManagerDataSource dataSource;
    private JdbcUserDAO dao = null;
    private User user = new User();


    public JdbcUserDAOTests() {
        dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        //dataSource.setUrl("jdbc:hsqldb:hsql://localhost/wikishare");
        //dataSource.setUsername("sa");

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/wikishare_test");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        dao = new JdbcUserDAO();
        dao.setDataSource(dataSource);

        user.setUsername("testuser");
        user.setPassword("test");
    }

    public void testDeleteAllUsers() {
        List<User> checkUsers = null;
        try {
            List<User> users = dao.getUsersList();
            for(User user : users) {
                dao.dropUser(user);
            }

            checkUsers = dao.getUsersList();
        } catch(Exception e) {
            e.printStackTrace();
        }
        assertTrue(checkUsers.size() == 0);
    }

    public void testCreateUser() {
        List<User> users = null;
        try {
            dao.saveUser(user);

        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            users = dao.getUsersList();
        } catch(Exception e) {
            e.printStackTrace();
        }
        User sameUser = dao.getUser(user.getUsername(), user.getPassword());

        assertNotNull(users);
        assertTrue(sameUser.getUsername().equals("testuser"));
    }

    public void testCheckUsername() throws Exception{
        User userCheck = dao.getUser(user.getUsername(), user.getPassword());
        assertEquals("testuser", userCheck.getUsername());
    }


}