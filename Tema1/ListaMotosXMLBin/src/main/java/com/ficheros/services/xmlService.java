
package com.ficheros.services;

import com.ficheros.model.motoList;

import javax.xml.bind.*;
        import java.io.File;

public class xmlService {

    private final String filePath;

    public xmlService(String filePath) {
        this.filePath = filePath;
    }

    public void guardarXml(motoList lista) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(motoList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(lista, new File(filePath));
        System.out.println("Lista guardada en fichero XML: " + filePath);
    }

    public motoList leerXml() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(motoList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        motoList lista = (motoList) unmarshaller.unmarshal(new File(filePath));
        System.out.println("Lista leída desde fichero XML.");
        return lista;
    }
}
