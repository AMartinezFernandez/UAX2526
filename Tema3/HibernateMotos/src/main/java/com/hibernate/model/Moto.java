package com.hibernate.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motos")
@Data               // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor   // Constructor sin parámetros
@AllArgsConstructor  // Constructor con todos los campos (marca, modelo, cilindrada)
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id autoincremental
    private int id;

    private String marca;
    private String modelo;
    private String cilindrada;

    public Moto(String marca, String modelo, String cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }
}


