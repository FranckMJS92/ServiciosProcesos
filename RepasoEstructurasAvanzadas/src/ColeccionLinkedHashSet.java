import java.util.LinkedHashSet;

public class ColeccionLinkedHashSet {
    public static void main(String[] args) {
        LinkedHashSet<String> nombres = new LinkedHashSet<>();
        // Ordena por orden de ingreso, como ArrayList
        nombres.add("Pepe");
        nombres.add("Juan");
        nombres.add("Ana");
        nombres.add("Zapata");
        nombres.add("Zapata");

        nombres.stream().forEach(System.out::println);
    }
}
