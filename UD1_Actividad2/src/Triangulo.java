public class Triangulo {
    public static void main(String[] args) {
       /*  if (args.length != 1) {
            System.err.println("Error se debe recibir 1 argumento");
        }

        byte base = 0; */

        try {
            //base = Byte.parseByte(args[0]);

            byte base= 5;
            for (int i = 1; i <= base; i++) {
            for (int j = 1; j <=i; j++) {
                System.out.print(j);
            }
            System.out.println();
        }
            
        } catch (Exception e) {

        }

    }
}
