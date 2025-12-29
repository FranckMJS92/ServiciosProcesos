package ejercicio5;

import java.util.ArrayList;

public class Jugador extends Thread {

    private NumeroOculto numeroOculto;
    private int numeroJugador;
    //private ArrayList<Integer> numeros = new ArrayList<Integer>();

    public Jugador(NumeroOculto numeroOculto, int numeroJugador) {
        this.numeroOculto = numeroOculto;
        this.numeroJugador = numeroJugador;
    }

    @Override
    public void run() {

        // Bucñe hasta que finalice el juego
        while (!numeroOculto.isFinJuego()) {
            // Comprobamos si es nuetsro turno
            if (numeroJugador == numeroOculto.getTurno()) {
                // Generamos numero de forma aleatoria
                int numero = (int) (Math.random() * 20) + 1;
                numeroOculto.comprobarNumero(numeroJugador, numero);
            }
        }
    }
}
