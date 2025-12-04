package com.ejemplo.services;

import com.ejemplo.database.MongoDBConnection;
import com.ejemplo.model.Moto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MotoService {

    private final MongoCollection<Document> collection;

    public MotoService() {
        this.collection = MongoDBConnection.getCollection();
    }

    // Alumno -> Document
    private Document toDocument(Moto m) {
        Document d = new Document();
        d.append("bastidor", m.getBastidor());
        d.append("marca", m.getMarca());
        d.append("modelo", m.getModelo());
        d.append("cilindrada", m.getCilindrada());
        return d;
    }

    // Document -> Alumno
    private Moto fromDocument(Document d) {
        if (d == null) return null;
        String bastidor = d.getString("bastidor");
        String marca = d.getString("marca");
        String modelo = d.getString("modelo");
        int cilindrada = d.getInteger("cilindrada");

        return new Moto(bastidor, marca, modelo, cilindrada);
    }

    // 1) Dar de alta un alumno
    public boolean addMoto(Moto m) {
        // comprobar si ya existe por expediente
        Bson filter = Filters.eq("bastidor", m.getBastidor());
        Document existing = collection.find(filter).first();
        if (existing != null) {
            return false; // ya existe
        }
        Document doc = toDocument(m);
        collection.insertOne(doc);
        return true;
    }

    // 2) Dar de baja (por expediente)
    public boolean deleteMoto(String bastidor) {
        Bson filter = Filters.eq("bastidor", bastidor);
        DeleteResult result = collection.deleteOne(filter);
        return result.getDeletedCount() > 0;
    }

    // 3) Leer todas las motos
    public List<Moto> getAllMotos() {
        List<Moto> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find(); //Lista iterable de documentos.

        for (Document d : docs) {
            lista.add(fromDocument(d));
        }
        return lista;
    }

    // 4) Mostrar una moto (buscar por bastidor)
    public Moto getMoto(String bastidor) {
        Bson filter = Filters.eq("bastidor", bastidor);
        Document d = collection.find(filter).first();
        return fromDocument(d);
    }



}