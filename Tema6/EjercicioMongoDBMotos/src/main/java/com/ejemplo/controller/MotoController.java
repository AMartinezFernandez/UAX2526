package com.ejemplo.controller;

import com.ejemplo.model.Moto;
import com.ejemplo.services.MotoService;

import java.util.List;
import java.util.Scanner;

public class MotoController {

    private final MotoService service;
    private final Scanner sc;

    public MotoController() {
        this.service = new MotoService();
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {

        int opcion = -1;

        do {
            System.out.println("\n===== MENÚ MOTOS =====");
            System.out.println("1. Guardar una moto");
            System.out.println("2. Leer todas las motos");
            System.out.println("3. Mostrar todas las motos");
            System.out.println("4. Eliminar moto");
            System.out.println("5. Mostrar una moto");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    guardarMoto();
                    break;

                case 2:
                    leerMotos();
                    break;

                case 3:
                    mostrarMotos();
                    break;

                case 4:
                    eliminarMoto();
                    break;

                case 5:
                    mostrarMoto();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    // Opción 1
    private void guardarMoto() {
        System.out.print("Introduce bastidor: ");
        String bastidor = sc.nextLine();

        System.out.print("Introduce marca: ");
        String marca = sc.nextLine();

        System.out.print("Introduce modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Introduce cilindrada: ");
        int cilindrada = Integer.parseInt(sc.nextLine());

        Moto m = new Moto(bastidor, marca, modelo, cilindrada);

        boolean insertada = service.addMoto(m);

        if (insertada) {
            System.out.println("Moto guardada correctamente.");
        } else {
            System.out.println("ERROR: Ya existe una moto con ese bastidor.");
        }
    }

    // Opción 2
    private void leerMotos() {
        List<Moto> motos = service.getAllMotos();
        System.out.println("Se han leído " + motos.size() + " motos desde la base de datos.");
    }

    // Opción 3
    private void mostrarMotos() {
        List<Moto> motos = service.getAllMotos();

        if (motos.isEmpty()) {
            System.out.println("No hay motos registradas.");
            return;
        }

        System.out.println("\n--- LISTA DE MOTOS ---");
        for (Moto m : motos) {
            System.out.println(m);
        }
    }

    // Opción 4
    private void eliminarMoto() {
        System.out.print("Introduce el bastidor de la moto a eliminar: ");
        String bastidor = sc.nextLine();

        boolean eliminada = service.deleteMoto(bastidor);

        if (eliminada) {
            System.out.println("Moto eliminada correctamente.");
        } else {
            System.out.println("No existe ninguna moto con ese bastidor.");
        }
    }

    // Opción 5
    private void mostrarMoto() {
        System.out.print("Introduce el bastidor de la moto: ");
        String bastidor = sc.nextLine();

        Moto m = service.getMoto(bastidor);

        if (m != null) {
            System.out.println("\n--- MOTO ENCONTRADA ---");
            System.out.println(m);
        } else {
            System.out.println("No existe ninguna moto con ese bastidor.");
        }
    }
}
