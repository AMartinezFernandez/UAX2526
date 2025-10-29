package com.hibernate.controller;

import com.hibernate.dao.GestionBD;
import com.hibernate.model.Moto;

import java.util.List;
import java.util.Scanner;

public class AppController {

    private final GestionBD gestionBD = new GestionBD();
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("========== MENÚ MOTOS ==========");
            System.out.println("1. Guardar moto");
            System.out.println("2. Leer motos");
            System.out.println("3. Eliminar moto");
            System.out.println("4. Mostrar moto por marca");
            System.out.println("0. Salir");
            System.out.println("===============================");

            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    guardarMoto();
                    break;
                case 2:
                    mostrarMotos();
                    break;
                case 3:
                    eliminarMoto();
                    break;
                case 4:
                    mostrarMotoPorMarca();
                    break;
                case 0:
                    System.out.println("Hasta pronto...");
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
        } while (opcion != 0);
    }

    private void guardarMoto() {
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Cilindrada: ");
        String cilindrada = sc.nextLine();

        gestionBD.guardarMoto(new Moto(marca, modelo, cilindrada));
        System.out.println("Moto guardada correctamente.");
    }

    private void mostrarMotos() {
        List<Moto> motos = gestionBD.leerMotos();
        if (motos.isEmpty()) {
            System.out.println("No hay motos.");
        } else {
            for (Moto m : motos) {
                System.out.println("Marca: " + m.getMarca() + ", Modelo: " + m.getModelo() +
                        ", Cilindrada: " + m.getCilindrada());
            }
        }
    }

    private void eliminarMoto() {
        System.out.print("ID de la moto a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();
        gestionBD.eliminarMoto(id);
        System.out.println("Moto eliminada si existía.");
    }

    private void mostrarMotoPorMarca() {
        System.out.print("Marca de la moto a buscar: ");
        String marca = sc.nextLine();
        Moto m = gestionBD.buscarPorMarca(marca);
        if (m != null) {
            System.out.println("Marca: " + m.getMarca() + ", Modelo: " + m.getModelo() +
                    ", Cilindrada: " + m.getCilindrada());
        } else {
            System.out.println("No existe ninguna moto con esa marca.");
        }
    }
}

