package com.ejemplo.motos.database;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class ConexionBD {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("objectdb-motos");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}