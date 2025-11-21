import java.util.TreeSet;

public class ColecccionTreeSet {
    public static void main(String[] args) {
        TreeSet<String> nombres = new TreeSet<>();
        //Ordena "de mayor a menor"
        nombres.add("Pepe");
        nombres.add("Juan");
        nombres.add("Ana");
        nombres.add("Zapata");
        nombres.add("Zapata");

        nombres.stream().forEach(System.out::println);

        TreeSet<Integer> numeros = new TreeSet<>();

        numeros.add(5);
        numeros.add(9);
        numeros.add(2);
        numeros.add(3);

        numeros.stream().forEach(System.out::println);

    }
}
