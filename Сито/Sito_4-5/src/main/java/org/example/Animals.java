package org.example;

import lombok.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
    @XmlRootElement(name = "animals")
    @XmlAccessorType(XmlAccessType.FIELD)
public class Animals
{
    @XmlElement(name = "animal")
    @Setter
    private List<Animal> animals = null;

    private transient List <JaxbParser> parsers = new ArrayList<>();
    public Animals(List<Animal> animals) {
        this.animals = animals;
    }
    public void subscribe(JaxbParser parser){
        parsers.add(parser);
    }
    public void unsubscribe(JaxbParser parser){
        parsers.remove(parser);
    }

    public List<Animal> getAnimals() throws JAXBException {
        if (animals == null) {
            JaxbParser parser = new JaxbParser(Main.file);
            animals = parser.jaxbXmlFileToObject().getAnimals();
        }
        return animals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Animals{ animals=\n");
        animals.stream().forEach(animal -> {sb.append(animal); sb.append("\n");});
        return sb.toString();
    }
    public void add(Animal animal){ 
        animals.add(animal);
        for (JaxbParser parser:
             parsers) {
            parser.jaxbObjectToXML(this);
        }
    }
    public void delete(int index) throws IndexOutOfBoundsException {
        if (index >= animals.size()){
            throw new IndexOutOfBoundsException();
        }
        animals.remove(index);
        for (JaxbParser parser:
                parsers) {
            parser.jaxbObjectToXML(this);
        }
    }

}
