package org.example;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class JaxbParser {
    static File file = new File("D:\\labs\\CN\\XMLParcer\\src\\file.xml");
    JaxbParser(String file){
        this.file = new File(file);
    }
    public void jaxbObjectToXML(Animals animals) {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(Animals.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(animals, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public Animals jaxbXmlFileToObject() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Animals.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Animals animals = (Animals) jaxbUnmarshaller.unmarshal(file);

        return animals;
    }
}
