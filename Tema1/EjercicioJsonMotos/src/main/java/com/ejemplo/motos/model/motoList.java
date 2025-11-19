package com.ejemplo.motos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
// ✅ Clase contenedora que agrupa una lista de objetos moto
//    Sirve como "wrapper" para trabajar con listas completas usando Gson/JAXB
//    y permite añadir o eliminar motos de forma controlada.
public class motoList {
    // ✅ Lista interna donde se guardan todas las motos
    //    Se inicializa vacía para evitar NullPointerException
    private List<moto> motos = new ArrayList<>();

    // ✅ Método para añadir una moto a la lista
    //    Uso: motoList.addMoto(new moto(...))
    public void addMoto(moto m) {
        motos.add(m);
    }

    // ✅ Elimina la primera moto cuyo modelo coincida (ignorando mayúsculas/minúsculas)
    //    Recorremos la lista manualmente para poder eliminar por índice sin errores
    public void removeMoto(String modelo) {
        for (int i = 0; i < motos.size(); i++) {
            if (motos.get(i).getModelo().equalsIgnoreCase(modelo)) {
                motos.remove(i);
                break;
            }
        }
    }
}
