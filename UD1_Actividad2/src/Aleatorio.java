import java.util.Random;

public class Aleatorio {
    public static void main(String[] args) {
        Random random = new Random();

        System.out.println("El numero elegido es: " + random.nextInt(11));
    }
}
