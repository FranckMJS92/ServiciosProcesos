import java.util.HashSet;

public class Principal {
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

        for (String s : nombres) {
            System.out.println(s);
        }

        // Ejemplo hashCode
        int hash = "Ana".hashCode();
        System.out.println(hash);
    }
}
