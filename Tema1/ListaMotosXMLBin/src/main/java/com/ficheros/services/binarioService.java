package com.ficheros.services;

import java.io.*;

import com.ficheros.model.moto;

import java.util.ArrayList;
import java.util.List;

public class binarioService {

    private final String filePath;

    public binarioService(String filePath) {
        this.filePath = filePath;
    }

    public void guardarBinario(List<moto> motos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(motos);
            System.out.println("Motos guardadas correctamente en archivo binario.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<moto> leerBinario() {
        List<moto> motos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            motos = (List<moto>) ois.readObject();
            System.out.println("Motos leídas desde archivo binario.");
        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo binario o está vacío.");
        }
        return motos;
    }
}

