
package com.ejemplo.motos;

import com.ejemplo.motos.controller.MotoController;

/**
 * Clase principal de la aplicación de gestión de motos
 * Esta es la clase de entrada (entry point) del programa
 */
public class App 
{
    /**
     * Método main - Punto de entrada de la aplicación
     * Se ejecuta cuando se inicia el programa
     * 
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación)
     */
    public static void main( String[] args )
    {
        // Paso 1: Crear una instancia del controlador que gestiona la interfaz de usuario
        MotoController controller = new MotoController();
        
        // Paso 2: Iniciar el menú interactivo para que el usuario pueda gestionar motos
        controller.iniciarMenu();
    }
}
