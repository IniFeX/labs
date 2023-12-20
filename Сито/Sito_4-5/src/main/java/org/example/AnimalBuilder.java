package org.example;

public class AnimalBuilder {
    private String name;
    private String type;
    private String fullOwnerName;
    private int age;

    public AnimalBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AnimalBuilder type(String type) {
        this.type = type;
        return this;
    }

    public AnimalBuilder fullOwnerName(String fullOwnerName) {
        this.fullOwnerName = fullOwnerName;
        return this;
    }

    public AnimalBuilder age(int age) {
        this.age = age;
        return this;
    }
    public Animal build(){
        return new Animal(name, type, fullOwnerName, age);
    }
}
