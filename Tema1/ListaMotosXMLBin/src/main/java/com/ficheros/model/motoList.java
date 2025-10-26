package com.ficheros.model;
//Clase para trabajar con ficheros XML
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "motos")
@XmlAccessorType(XmlAccessType.FIELD) //Usa todos los campos (incluso privados) directamente.
public class motoList implements Serializable {

    @XmlElement(name = "moto")
    private List<moto> motos = new ArrayList<>();

    public List<moto> getMotos() {
        return motos;
    }

    public void addMoto(moto moto) {
        motos.add(moto);
    }
    public void setMotos(List<moto> motos) {
        this.motos = motos;
    }
}
