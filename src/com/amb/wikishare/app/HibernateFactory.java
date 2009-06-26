package com.amb.wikishare.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class HibernateFactory {

    public static SessionFactory sessionFactory =
        new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();

}
