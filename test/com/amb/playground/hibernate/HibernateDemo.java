package com.amb.playground.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;

import com.amb.playground.hibernate.User;
import com.amb.wikishare.app.HibernateFactory;
import com.amb.wikishare.domain.Page;

public class HibernateDemo
{
    private static final SessionFactory sessionFactory =
          new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();


    public static void main(String[] args)
    {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // save user
        User object0 = new User();
        object0.setName("hibernate " + Math.random());

        session.save(object0);

        System.out.println("Object 0");
        System.out.println("Generated ID is: " + object0.getId() + "" + object0.getName());

        session.getTransaction().commit();


        // get user
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = (User)session.get(User.class, object0.getId());
        System.out.println("List users name with id=" + object0.getId() +" =" + user.getName());

        session.getTransaction().commit();

        // list users
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<User> users = session.createQuery("from User").list();
        /*
        for(User u : users) {
            System.out.println("user -> " + u.getName());
        }
        */

        session.getTransaction().commit();

        // find user by name
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        SQLQuery query =
            session.createSQLQuery("select * from users as user where name = :name and password = :password");
        query.addEntity("user", User.class);
        query.setString("name", "testuser");
        query.setString("password", "test");
        List<User> usersWithUserName = query.list();
        for(User u : usersWithUserName) {
            System.out.println("usersWithUserName -> " + u.getName());
        }

        session.getTransaction().commit();

        // pages
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query2 =
            session.createQuery("from Page where signature = :signature");
        query2.setString("signature", "s993630370");
        List<Page> pages = query2.list();
        for(Page p : pages) {
            System.out.println("page name = " + p.getTitle());
        }

        session.getTransaction().commit();

        // count pages
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //Integer count = (Integer) session.createQuery("select count(*) from ....").uniqueResult();

        Query query3 =
            session.createQuery("select count(*) from Page where signature = :signature");
        query3.setString("signature", "s1");
        Long amount = (Long)query3.uniqueResult();
        System.out.println("Pages amount = " + amount);

        session.getTransaction().commit();

        // find
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query findQuery = session.createQuery(
                "from Page where " +
                "title like :searchText or content like :searchText " +
                "order by timestamp desc");
        findQuery.setString("searchText", "%" + "page" + "%");
        pages = findQuery.list();
        for(Page p : pages) {
            System.out.println("Found page: " + p.getTitle());
        }

        session.getTransaction().commit();


        // deactivate test
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        SQLQuery deactivateQuery = session.createSQLQuery(
                "update pages set active_page = 1 where signature = :signature");
        deactivateQuery.setString("signature", "s1");
        deactivateQuery.executeUpdate();

        session.getTransaction().commit();
    }
}