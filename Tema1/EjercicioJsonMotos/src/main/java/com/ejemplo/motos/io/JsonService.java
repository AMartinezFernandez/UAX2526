package com.ejemplo.motos.io;

import com.ejemplo.motos.model.motoList;
import com.ejemplo.motos.model.moto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonService {

    // ✅ Ruta del archivo JSON donde se guardarán/leerán las motos
    private final String filePath;

    // ✅ Instancia de Gson configurada con formato bonito (pretty printing) para escribir JSON legible
    private final Gson gson;

    /**
     * ✅ Constructor del servicio JSON
     * Recibe la ruta del archivo JSON que gestionará esta clase.
     * - Guarda la ruta en la variable filePath.
     * - Inicializa Gson con pretty printing para que el JSON se guarde formateado.
     *
     * Uso típico:
     * JsonService service = new JsonService("src/main/resources/motos.json");
     */
    public JsonService(String filePath) {
        this.filePath = filePath;           // ✅ Guardamos la ruta para usarla en leer() y guardar()
        this.gson = new GsonBuilder()
                .setPrettyPrinting()        // ✅ Formatea el JSON para que se vea más limpio
                .create();                  // ✅ Crea la instancia final de Gson
    }

    /**
     * ✅ Guarda la lista completa de motos en el archivo JSON indicado por filePath.
     * Este método sobrescribe completamente el archivo.
     *
     * Uso:
     * JsonService service = new JsonService("ruta.json");
     * service.guardar(listaDeMotos);
     */
    // Guardar lista completa en JSON
    public void guardar(motoList lista) {
        try (Writer w = new FileWriter(filePath)) {
            // ✅ Serializamos solo la lista interna de motos, no el objeto motoList completo
            //    Gson convertirá la lista en un array JSON
            gson.toJson(lista.getMotos(), w);
            // ✅ Confirmación visual para depuración
            System.out.println("Motos guardadas en JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ✅ Lee el archivo JSON y devuelve un objeto motoList relleno.
     * Si el archivo no existe, devuelve una lista vacía.
     *
     * Detalles técnicos:
     * - Gson no puede leer directamente List<moto> sin TypeToken, por eso
     *   se lee como moto[] y luego se reconstruye la lista.
     */
    // Leer lista de JSON
    public motoList leer() {
        // ✅ Creamos lista vacía para devolver algo estable incluso si hay errores
        motoList lista = new motoList();
        // ✅ Si el archivo no existe, devolvemos lista vacía sin errores
        if (!Files.exists(Paths.get(filePath))) {
            return lista; // archivo no existe, lista vacía
        }

        try (Reader r = new FileReader(filePath)) {
            // ✅ Leemos el JSON como array de objetos moto
            //    Esto evita problemas con tipos genéricos
            moto[] motos = gson.fromJson(r, moto[].class);


            if (motos != null) {
                // ✅ Reconstruimos internamente la lista dinámicamente
                for (moto m : motos) {
                    lista.addMoto(m);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
