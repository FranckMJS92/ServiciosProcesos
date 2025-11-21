import java.util.HashSet;

public class ColeccionHashSet {
    public static void main(String[] args) {
        /*
         * HashSet ordena por hascode
         * No es alfabetico
         */
        HashSet<String> nombres = new HashSet<>();

        nombres.add("Pepe");
        nombres.add("Juan");
        nombres.add("Ana");
        nombres.add("Zapata");
        nombres.add("Zapata");

        nombres.stream().forEach(System.out::println);

        // Ejemplo hashCode
        int hash = "Ana".hashCode();
        System.out.println(hash);
    }
}
