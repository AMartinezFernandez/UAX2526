package com.ejemplo.alumnos.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexionDB {

    private static final EntityManagerFactory emf =
            //Crea directamente el archivo alumnos.db dentro de la carpeta db.
            Persistence.createEntityManagerFactory("objectdb:db/alumnos.odb");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        emf.close();
    }
}
