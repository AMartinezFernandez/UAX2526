package com.ejemplo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    private String bastidor; // identificador único
    private String marca;
    private String modelo;
    private int cilindrada;
}