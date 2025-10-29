package com.hibernate.dao;

import com.hibernate.model.Alumno;
import com.hibernate.model.Nota;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class GestionBD {

    // Alta alumno
    public void altaAlumno(Alumno a) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(a);
            tx.commit();
            System.out.println("Alumno añadido correctamente.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al añadir alumno: " + e.getMessage());
        }
    }

    // Baja alumno
    public void bajaAlumno(String expediente) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Alumno a = session.get(Alumno.class, expediente);
            if (a != null) session.remove(a);
            tx.commit();
            System.out.println("Alumno eliminado.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al eliminar alumno: " + e.getMessage());
        }
    }

    // Insertar nota
    public void insertarNota(Alumno a, double valorNota) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Nota n = new Nota();
            n.setAlumno(a);
            n.setNota(valorNota);
            session.persist(n);
            tx.commit();
            System.out.println("Nota insertada correctamente.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al insertar nota: " + e.getMessage());
        }
    }

    // Consultar todas las notas
    public List<Nota> consultarTodasNotas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Nota", Nota.class).list();
        }
    }
}
