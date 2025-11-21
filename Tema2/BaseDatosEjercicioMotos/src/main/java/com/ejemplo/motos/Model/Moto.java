package com.ejemplo.motos.Model;

/**
 * Clase Moto - Modelo de datos que representa una motocicleta
 * Esta clase sigue el patrón JavaBean con getters y setters
 * Se utiliza para transferir datos entre la base de datos y la aplicación
 */
public class Moto {
    // Atributos privados de la clase (encapsulación)
    
    /** Identificador único de la moto en la base de datos (clave primaria) */
    private int id;
    
    /** Marca de la motocicleta (ej: Ducati, Yamaha, Honda) */
    private String marca;
    
    /** Modelo específico de la motocicleta (ej: Monster, R1, CBR) */
    private String modelo;
    
    /** Cilindrada del motor en centímetros cúbicos (ej: 999, 600, 1000) */
    private String cilindrada;

    // CONSTRUCTORES
    
    /**
     * Constructor vacío (sin parámetros)
     * Necesario para crear objetos Moto sin inicializar valores
     * Útil cuando se van a establecer los valores después con setters
     */
    public Moto() {
    }

    /**
     * Constructor con todos los parámetros
     * Permite crear un objeto Moto con todos sus datos inicializados
     * 
     * @param id Identificador único de la moto
     * @param marca Marca de la motocicleta
     * @param modelo Modelo de la motocicleta
     * @param cilindrada Cilindrada del motor
     */
    public Moto(int id, String marca, String modelo, String cilindrada) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    // GETTERS - Métodos para obtener (leer) los valores de los atributos
    
    /**
     * Obtiene el ID de la moto
     * @return El identificador único de la moto
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la marca de la moto
     * @return La marca de la motocicleta
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtiene el modelo de la moto
     * @return El modelo de la motocicleta
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene la cilindrada de la moto
     * @return La cilindrada del motor
     */
    public String getCilindrada() {
        return cilindrada;
    }

    // SETTERS - Métodos para establecer (modificar) los valores de los atributos
    
    /**
     * Establece el ID de la moto
     * @param id El nuevo identificador único
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la marca de la moto
     * @param marca La nueva marca de la motocicleta
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece el modelo de la moto
     * @param modelo El nuevo modelo de la motocicleta
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Establece la cilindrada de la moto
     * @param cilindrada La nueva cilindrada del motor
     */
    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }
}
