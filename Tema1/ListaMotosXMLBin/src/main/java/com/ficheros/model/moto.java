package com.ficheros.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class moto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String marca;
    private String modelo;
    private int cilindrada;
}
