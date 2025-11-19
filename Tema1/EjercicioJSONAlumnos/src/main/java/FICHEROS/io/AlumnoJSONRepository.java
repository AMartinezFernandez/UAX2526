package FICHEROS.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import FICHEROS.model.Alumno;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoJSONRepository {

    /**
     * 🧩 Repositorio encargado de gestionar la persistencia de datos de Alumno en formato JSON.
     * Se encarga de:
     *  - Crear archivo y carpetas si no existen.
     *  - Leer lista de alumnos desde JSON.
     *  - Guardar lista de alumnos en JSON.
     *
     * Uso recomendado:
     * AlumnoJSONRepository repo = new AlumnoJSONRepository();
     * List<Alumno> lista = repo.leerAlumnos();
     */
    public AlumnoJSONRepository() {
        // ✅ Asegura que el archivo exista al crear el repositorio
        crearArchivoSiNoExiste();
    }

    private static final String FICHERO = "src/main/resources/alumnos.json";

    // Gson con formato pretty printing para mejor legibilidad del JSON
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Método privado que crea el archivo JSON y las carpetas necesarias si no existen.
     * Esto evita errores posteriores al intentar leer o escribir en un archivo inexistente.
     */
    private void crearArchivoSiNoExiste() {
        File f = new File(FICHERO);

        try {
            // Crear carpetas si no existen
            File carpeta = f.getParentFile();
            if (!carpeta.exists()) {
                // Si la carpeta padre no existe, se crea
                carpeta.mkdirs();
                System.out.println("Carpeta creada: " + carpeta.getAbsolutePath());
            }

            // Crear archivo si no existe
            if (!f.exists()) {
                // Si el archivo no existe, se crea uno nuevo
                f.createNewFile();
                try (FileWriter fw = new FileWriter(f)) {
                    // ✅ Inicializamos con una lista JSON vacía, necesaria para evitar errores al leer
                    fw.write("[]");
                }
                System.out.println("Archivo creado: " + f.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee la lista de alumnos desde el archivo JSON.
     * Si el archivo no existe (situación anómala), devuelve una lista vacía para evitar nulls.
     *
     * @return Lista de objetos Alumno leídos del JSON
     */
    public List<Alumno> leerAlumnos() {
        File f = new File(FICHERO);
        // ✅ Si el archivo no existe (situación anómala), devolvemos lista vacía para evitar nulls
        if (!f.exists()) return new ArrayList<>();

        try (FileReader fr = new FileReader(f)) {
            // ✅ TypeToken permite leer List<Alumno> preservando el tipo genérico durante la deserialización
            return gson.fromJson(fr, new TypeToken<List<Alumno>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de alumnos en el archivo JSON, sobrescribiendo su contenido anterior.
     *
     * @param alumnos Lista de objetos Alumno a guardar
     */
    public void guardarAlumnos(List<Alumno> alumnos) {
        try (FileWriter fw = new FileWriter(FICHERO)) {
            // ✅ Sobrescribe completamente el archivo con la nueva lista de alumnos
            gson.toJson(alumnos, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
