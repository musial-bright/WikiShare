package com.amb.wikishare.service;

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amb.wikishare.dao.JdbcNavigationDAO;
import com.amb.wikishare.domain.Navigation;

import junit.framework.TestCase;

public class NavigationServiceTest extends TestCase {

    //private DriverManagerDataSource dataSource = null;
    private NavigationService navigationService =
        new NavigationService();

    private Navigation navigation = new Navigation();
    private String navigationName = "Titles";
    private String navigationContent =
        "<a href=\"wikishare/s123\">"+ navigationName +"</a>";
    private String navigationEscapedContent =
        "<a href=&quot;wikishare/s123&quot;>Title&#39;s</a>";

    public NavigationServiceTest() {
        /*
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/wikishare_test");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        */
        JdbcNavigationDAO navigationDao = new JdbcNavigationDAO();
        //navigationDao.setDataSource(dataSource);
        navigationService.setJdbcNavigationDAO(navigationDao);

        navigation.setName(navigationName);
        navigation.setContent(navigationContent);
    }

    public void testDeleteTestNavigations() {
        try {
            List<Navigation> navigations =
                navigationService.getNavigationsList();
            for(Navigation navi : navigations) {
                navigationService.dropNavigation(navi);
            }
        } catch (Exception e) {

        }
    }


    public void testGetEscapedContent() {
        List<Navigation> navigations = null;
        try {
            navigationService.saveNavigation(navigation);
            navigations = navigationService.getNavigationsList();
        } catch(Exception e) {
            System.out.println("Error = " + e);
        }
        Navigation testNavi = navigations.get(0);
        System.out.println(testNavi.getContent());

        assertNotNull(navigations);

    }

}
