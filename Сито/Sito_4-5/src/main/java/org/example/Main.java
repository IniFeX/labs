package org.example;

import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String MENU = """
            0. Выйти
            1. Вывести
            2. Добавить
            3. Удалить
            """;
    static String file = "G:\\university\\Sitairis-4-and-5-master\\src\\file.xml";

    static  Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws JAXBException {
        JaxbParser parser = new JaxbParser(file);
        parser.jaxbObjectToXML(new Animals(
                Arrays.asList(new Animal("Murzik", "Cat", "Darya Shumova", 11),
                        new Animal("Govorun", "Parrot", "Darya Shumova", 5),
                        new Animal("Druzhok", "Dog", "Ivan Bronin", 11),
                        new Animal("Leo", "Chameleon", "Andrey Usov", 2),
                        new Animal("Chernysh", "Cat", "Darya Shumova", 3),
                        new Animal("Puaro", "Cat", "Nikita Labuda", 14),
                        new Animal("Linda", "Capybara", "Vadim Solncev", 5),
                        new Animal("Tomas", "Capybara", "Vadim Solncev", 4),
                        new Animal("Barsik", "Cat", "Maria Ivanova", 20))));
        Animals animals = new Animals();
        animals.subscribe(parser);
        Scanner scanner = new Scanner(System.in);
        int switcher = -1;
        while(switcher != 0){
            System.out.println(MENU);
            switcher = scanner.nextInt();
            scanner.nextLine();
            switch (switcher){
                case 0 -> { System.out.println("Buy"); return; }
                case 1 -> {
                    List<Animal> list = animals.getAnimals();
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + " " + list.get(i));
                    }
                }
                case 2 -> animals.add(initAnimal());
                case 3 -> {
                    System.out.println(animals);
                    System.out.println("Введите номер");
                    switcher = scanner.nextInt() - 1;
                    scanner.nextLine();
                    animals.delete(switcher);
                }
                default -> System.out.println("Fuck you");
            }
        }

    }
    private static Animal initAnimal(){
        System.out.println("Введите имя животного");
        String name = scanner.nextLine();
        System.out.println("Введите тип животного");
        String type = scanner.nextLine();
        System.out.println("Введите полное имя владельца");
        String fullOwnerName = scanner.nextLine();
        System.out.println("Введите возраст");
        int age = scanner.nextInt();
        scanner.nextLine();
        return Animal.toBuilder().name(name).type(type)
                .fullOwnerName(fullOwnerName)
                .age(age).build();
    }
}