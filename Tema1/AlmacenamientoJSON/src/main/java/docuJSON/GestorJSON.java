package docuJSON; // 📦 Paquete donde vive esta clase; organiza el código y evita colisiones de nombres

import com.google.gson.Gson; // ✅ Biblioteca Gson: convierte objetos Java ↔ JSON (serialización/deserialización)
import com.google.gson.reflect.TypeToken; // ✅ Captura de tipos genéricos en tiempo de ejecución (List<Coche>)

import java.io.*; // ✅ Clases de Entrada/Salida para leer y escribir archivos (File, Reader, Writer, etc.)
import java.lang.reflect.Type; // ✅ Representa un tipo (incluyendo genéricos) para pasarlo a Gson
import java.util.ArrayList; // ✅ Implementación concreta de List
import java.util.List; // ✅ Interfaz de lista
import java.util.stream.Collectors; // ✅ Utilidad para recolectar resultados de streams a colecciones

/**
 * 🧩 GestorJSON
 *
 * Responsabilidad principal:
 *  - Mantener una lista de "Coche" en un archivo JSON (leer, guardar y operaciones típicas: insertar, borrar, mostrar).
 *
 * Decisiones de diseño:
 *  - Usa Gson para (de)serializar.
 *  - Garantiza que el archivo y la carpeta existan al construir el objeto (fail-safe al inicio).
 *  - Ruta fija relativa al proyecto: src/main/resources/coches.json.
 *
 * Requisitos:
 *  - La clase Coche debe ser un POJO compatible con Gson (getters/setters o campos públicos).
 *  - La ruta debe ser accesible con permisos de lectura y escritura.
 */
public class GestorJSON {
    // 📁 Ruta del archivo JSON donde se guardan los coches; relativa al proyecto (carpeta resources)
    private final String rutaArchivo = "src/main/resources/coches.json";
    // 🛠️ Instancia reutilizable de Gson (crear una vez y aprovecharla en todos los métodos)
    private final Gson gson = new Gson();

    // 🧱 Constructor: al crear el GestorJSON, nos aseguramos de que exista la carpeta/archivo objetivo
    public GestorJSON() {
        crearArchivoSiNoExiste(); // 👉 Llama al método auxiliar que crea carpeta/archivo si faltan
    }

    /**
     * Crea la carpeta y el archivo JSON si aún no existen.
     * Evita que las siguientes operaciones fallen por FileNotFoundException.
     */
    private void crearArchivoSiNoExiste() {
        try {
            File file = new File(rutaArchivo); // 📄 Representa el archivo coches.json en disco
            File carpeta = file.getParentFile(); // 📂 Carpeta padre (src/main/resources)

            // 🗂️ Si la carpeta no existe, la creamos de forma recursiva
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdirs(); // mkdirs() crea todas las carpetas necesarias
                if (creada) {
                    System.out.println("Carpeta creada: " + carpeta.getAbsolutePath()); // 🖨️ Feedback en consola
                }
            }

            // 🧾 Si el archivo no existe, lo creamos con un JSON de lista vacía "[]"
            if (!file.exists()) {
                boolean creado = file.createNewFile(); // Crea el archivo físico
                if (creado) {
                    try (Writer writer = new FileWriter(file)) { // try-with-resources: cierra solo
                        writer.write("[]"); // Contenido inicial: una lista vacía para que Gson lea sin errores
                    }
                    System.out.println("Archivo creado: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            // 🚨 Si algo falla creando carpeta/archivo, lo avisamos con detalle y stacktrace
            System.err.println("Error creando archivo o carpeta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lee del archivo JSON y devuelve la lista de coches.
     * @return List<Coche> con los objetos leídos; si hay cualquier problema, devuelve lista vacía.
     */
    public List<Coche> leerCoches() {
        File file = new File(rutaArchivo); // 📄 Localizamos el archivo de datos
        if (!file.exists()) return new ArrayList<>(); // 🛡️ Si no existe (edge case), devolvemos lista vacía

        try (Reader reader = new FileReader(file)) { // 📖 Abrimos un lector de caracteres al archivo
            // 🧠 Necesitamos describir el tipo exacto List<Coche> para que Gson maneje los genéricos
            Type listType = new TypeToken<List<Coche>>() {}.getType();
            // 🔄 Convertimos el JSON del archivo (reader) a una List<Coche> usando el tipo anterior
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            // 🚨 Si ocurre un error de E/S, mostramos el stack y devolvemos lista vacía
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Sobrescribe el archivo JSON con la lista recibida.
     * @param coches lista completa a guardar en el archivo
     */
    public void guardarCoches(List<Coche> coches) {
        try (Writer writer = new FileWriter(rutaArchivo)) { // ✍️ Abrimos un escritor que sobrescribe el archivo
            gson.toJson(coches, writer); // 🔄 Convertimos la lista a JSON y la escribimos en el archivo
        } catch (IOException e) {
            // 🚨 Error de escritura
            e.printStackTrace();
        }
    }

    /**
     * Inserta un coche nuevo en el JSON persistente.
     * 1) Lee la lista actual
     * 2) Añade el nuevo coche
     * 3) Guarda la lista resultante
     */
    public void insertarCoche(Coche nuevo) {
        List<Coche> coches = leerCoches(); // 1️⃣ Cargamos el estado actual
        coches.add(nuevo); // 2️⃣ Añadimos el nuevo elemento en memoria
        guardarCoches(coches); // 3️⃣ Persistimos la nueva lista al disco
        System.out.println("Coche insertado correctamente."); // ✅ Feedback
    }

    /**
     * Elimina (filtra) todos los coches cuya marca coincida (ignorando mayúsculas/minúsculas).
     * @param marca marca a eliminar del JSON
     */
    public void borrarPorMarca(String marca) {
        List<Coche> coches = leerCoches(); // 1️⃣ Traemos la lista actual
        // 2️⃣ Creamos una nueva lista sin los que coinciden con la marca
        List<Coche> filtrados = coches.stream()
                .filter(c -> !c.getMarca().equalsIgnoreCase(marca)) // Mantén solo los que NO son de esa marca
                .collect(Collectors.toList()); // Recolecta el stream en una nueva lista
        guardarCoches(filtrados); // 3️⃣ Guardamos la lista filtrada
        System.out.println("Coche(s) borrado(s) correctamente."); // ✅ Feedback
    }

    /**
     * Muestra por consola todos los coches almacenados, o un mensaje si no hay ninguno.
     */
    public void mostrarCoches() {
        List<Coche> coches = leerCoches(); // 1️⃣ Leemos del archivo
        if (coches.isEmpty()) { // 2️⃣ Comprobamos si está vacía
            System.out.println("No hay coches registrados."); // ℹ️ Mensaje informativo
        } else {
            System.out.println("Lista de coches:"); // Encabezado
            coches.forEach(System.out::println); // 3️⃣ Imprime cada coche usando su toString()
        }
    }
}
