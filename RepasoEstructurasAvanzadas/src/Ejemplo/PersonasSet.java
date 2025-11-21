package Ejemplo;

import java.util.TreeSet;

public class PersonasSet {
    public static void main(String[] args) {
        TreeSet<Persona> personas = new TreeSet<>();

        personas.add(new Persona("Juan", "Gomez"));
        personas.add(new Persona("Pedro", "Gomez"));
        personas.add(new Persona("Ana", "Perez"));

        personas.stream().forEach(System.out::println);

    }
}
