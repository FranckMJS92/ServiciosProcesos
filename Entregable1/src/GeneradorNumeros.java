import java.util.Random;

public class GeneradorNumeros {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Debe recibir un parametro");
        }

        Random random = new Random();

        int aleatorio;

        for (byte i = 1; i <= Byte.parseByte(args[0]); i++) {
            System.out.println(aleatorio = random.nextInt(101));
        }
    }
}
