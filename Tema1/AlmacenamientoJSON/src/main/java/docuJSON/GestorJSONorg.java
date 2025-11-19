/* package docuJSON; // 📦 Paquete para organizar clases relacionadas con JSON

import org.json.JSONArray;     // ✅ Representa un array JSON (lista de objetos JSON)
import org.json.JSONObject;    // ✅ Representa un objeto JSON (pares clave-valor)

import java.io.*;               // ✅ Para manejar archivos: File, Reader, Writer, etc.
import java.util.ArrayList;     // ✅ Implementación de lista dinámica
import java.util.List;          // ✅ Interfaz List
import java.util.stream.Collectors; // ✅ Para filtrar listas con streams

/**
 * 🧩 GestorJSONorg (versión con org.json)
 *
 * Funciones principales:
 *  - Crear archivo JSON si no existe
 *  - Leer coches del archivo
 *  - Guardar coches en el archivo
 *  - Insertar coches nuevos
 *  - Borrar coches por marca
 *  - Mostrar coches por consola
 *
 * Esta versión NO usa Gson, sino la librería org.json
 */
public class GestorJSON {

    // 📁 Ruta relativa del archivo JSON donde se guardarán los coches
    private final String rutaArchivo = "src/main/resources/coches.json";

    // 🧱 Constructor: al crear el objeto, nos aseguramos de que existan carpetas y archivo
    public GestorJSON() {
        crearArchivoSiNoExiste(); // ✅ Prepara entorno seguro para evitar errores posteriores
    }

    /**
     * ✅ Crea carpeta y archivo si aún no existen.
     * Esto evita FileNotFoundException al guardar o leer.
     */
    private void crearArchivoSiNoExiste() {
        try {
            File file = new File(rutaArchivo);            // 📄 Objeto que representa el archivo físico
            File carpeta = file.getParentFile();           // 📂 Carpeta padre donde deben estar los recursos

            // 🗂️ Si la carpeta no existe, la creamos
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdirs();         // mkdirs() crea todas las subcarpetas necesarias
                if (creada) System.out.println("Carpeta creada: " + carpeta.getAbsolutePath());
            }

            // 🧾 Si el archivo no existe, lo creamos vacío con una lista JSON vacía []
            if (!file.exists()) {
                boolean creado = file.createNewFile();    // Crea archivo físico
                if (creado) {
                    try (Writer writer = new FileWriter(file)) { // Abrimos el archivo para escribir []
                        writer.write("[]");              // Contenido inicial: lista JSON vacía
                    }
                    System.out.println("Archivo creado: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 🚨 Mensaje de error si algo falla
        }
    }

    /**
     * ✅ Lee el archivo JSON y lo convierte en una lista de objetos Coche.
     * @return lista de coches almacenados en el archivo JSON
     */
    public List<Coche> leerCoches() {
        File file = new File(rutaArchivo);             // 📄 Localizamos archivo
        List<Coche> coches = new ArrayList<>();        // 📦 Lista donde guardaremos los coches leídos

        if (!file.exists()) return coches;             // 🛡️ Protección extra: si no existe, devolvemos lista vacía

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // 📖 Lector de texto
            StringBuilder sb = new StringBuilder();    // Contenedor para acumular todo el JSON
            String linea;

            // 🔄 Leemos línea por línea para reconstruir el JSON completo
            while ((linea = reader.readLine()) != null) {
                sb.append(linea);
            }

            // ✅ Convertimos el String acumulado en un JSONArray
            JSONArray jsonArray = new JSONArray(sb.toString());

            // 🔄 Recorremos cada objeto JSON dentro del array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i); // Extraemos coche como JSONObject

                // ✅ Creamos objeto Coche a partir de datos del JSON
                Coche coche = new Coche(
                        obj.getString("marca"),   // Marca
                        obj.getString("modelo"),  // Modelo
                        obj.getString("tipo"),    // Tipo
                        obj.getDouble("precio")   // Precio
                );

                coches.add(coche); // ✅ Agregamos el coche a la lista final
            }
        } catch (Exception e) {
            e.printStackTrace(); // 🚨 Error de lectura o parseo
        }

        return coches; // ✅ Devolvemos lista cargada
    }

    /**
     * ✅ Convierte una lista de Coche a JSON y la guarda en el archivo.
     */
    public void guardarCoches(List<Coche> coches) {
        JSONArray jsonArray = new JSONArray(); // ✅ Creamos un JSONArray vacío

        // 🔄 Convertimos cada Coche → JSONObject
        for (Coche coche : coches) {
            JSONObject obj = new JSONObject();
            obj.put("marca", coche.getMarca());   // Escribimos cada atributo
            obj.put("modelo", coche.getModelo());
            obj.put("tipo", coche.getTipo());
            obj.put("precio", coche.getPrecio());
            jsonArray.put(obj);                    // ✅ Añadimos el objeto al array
        }

        // ✍️ Guardamos el contenido en el archivo como texto JSON
        try (Writer writer = new FileWriter(rutaArchivo)) {
            writer.write(jsonArray.toString(4));   // Indentación de 4 espacios (bonito y legible)
        } catch (IOException e) {
            e.printStackTrace(); // 🚨 Error de escritura
        }
    }

    /**
     * ✅ Inserta un coche nuevo en el archivo JSON.
     */
    public void insertarCoche(Coche nuevo) {
        List<Coche> coches = leerCoches(); // 1️⃣ Leer coches existentes
        coches.add(nuevo);                 // 2️⃣ Añadir nuevo coche
        guardarCoches(coches);             // 3️⃣ Guardar lista actualizada
        System.out.println("Coche insertado correctamente.");
    }

    /**
     * ✅ Borra coches cuya marca coincida con la dada.
     */
    public void borrarPorMarca(String marca) {
        List<Coche> coches = leerCoches(); // 1️⃣ Cargar lista completa

        // 2️⃣ Filtrar lista dejando solo coches cuya marca NO coincida
        List<Coche> filtrados = coches.stream()
                .filter(c -> !c.getMarca().equalsIgnoreCase(marca)) // Mantener los que NO son esa marca
                .collect(Collectors.toList());

        guardarCoches(filtrados); // 3️⃣ Guardar resultado filtrado
        System.out.println("Coche(s) borrado(s) correctamente.");
    }

    /**
     * ✅ Muestra por consola todos los coches almacenados.
     */
    public void mostrarCoches() {
        List<Coche> coches = leerCoches(); // 1️⃣ Leer lista

        if (coches.isEmpty()) {            // 2️⃣ Comprobar vacía
            System.out.println("No hay coches registrados.");
        } else {
            System.out.println("Lista de coches:");
            coches.forEach(System.out::println); // 3️⃣ Imprimir uno por uno
        }
    }
}
*/