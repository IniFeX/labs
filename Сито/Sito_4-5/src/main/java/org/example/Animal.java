package org.example;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal {
    private String name;
    private String type;
    private String fullOwnerName;
    private int age;

    static public AnimalBuilder toBuilder(){
        return new AnimalBuilder();
    }
}