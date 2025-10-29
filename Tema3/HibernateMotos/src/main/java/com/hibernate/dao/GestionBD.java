package com.hibernate.dao;

import com.hibernate.model.Moto;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GestionBD {

    public void guardarMoto(Moto moto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(moto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Moto> leerMotos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Moto", Moto.class).list();
        }
    }

    public void eliminarMoto(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Moto moto = session.get(Moto.class, id);
            if (moto != null) session.remove(moto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Moto buscarPorMarca(String marca) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Moto where marca = :marca", Moto.class)
                    .setParameter("marca", marca)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
