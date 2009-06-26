package com.amb.playground.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import com.amb.playground.hibernate.User;

public class HibernateDemo
{
    private static final SessionFactory sessionFactory =
          new AnnotationConfiguration().configure("hibernate-test.cfg.xml").buildSessionFactory();


    public static void main(String[] args)
    {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // save user
        User object0 = new User();
        object0.setName("hibernate " + Math.random());

        session.save(object0);

        System.out.println("Object 0");
        System.out.println("Generated ID is: " + object0.getId() + "" + object0.getName());

        transaction.commit();


        // get user
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();

        User user = (User)session.load(User.class, object0.getId());
        System.out.println("List users name with id=" + object0.getId() +" =" + user.getName());

        transaction.commit();

        // list users
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();

        List<User> users = session.createQuery("from User").list();
        for(User u : users) {
            System.out.println("user -> " + u.getName());
        }

        transaction.commit();

        // find user by name
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();

        SQLQuery query =
            session.createSQLQuery("select * from users as user where name = :name and password = :password");
        query.addEntity("user", User.class);
        query.setString("name", "testuser");
        query.setString("password", "test");
        List<User> usersWithUserName = query.list();
        for(User u : usersWithUserName) {
            System.out.println("usersWithUserName -> " + u.getName());
        }

        transaction.commit();
    }
}