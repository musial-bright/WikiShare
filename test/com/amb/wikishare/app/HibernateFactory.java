package com.amb.wikishare.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class HibernateFactory {

    public static String configFileName = "hibernate-test.cfg.xml";

    public static SessionFactory sessionFactory =
        new AnnotationConfiguration().configure(configFileName).buildSessionFactory();

}
