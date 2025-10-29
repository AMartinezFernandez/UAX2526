package com.hibernate.dao;

import com.hibernate.model.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml") // archivo XML de configuración
                    .addAnnotatedClass(com.hibernate.model.Alumno.class)
                    .addAnnotatedClass(com.hibernate.model.Nota.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error creando SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
