package ejercicio5;

public class NumeroOculto {

    private int numeroJugadores;
    private int turno;
    private int NumeroOculto;
    private boolean finJuego;
    private int jugadorGanador;

    public NumeroOculto(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
        this.turno = 1;
        NumeroOculto = (int) (Math.random() * 20) + 1;
        this.finJuego = false;
    }

    synchronized public int getTurno() {
        return this.turno;
    }

    synchronized public boolean isFinJuego() {
        return this.finJuego;
    }

    public int getJugadorGanadro() {
        return this.jugadorGanador;
    }

    synchronized public void comprobarNumero(int jugador, int numeroJugador) {

        // Comprobar si es el turno del jugador
        if (jugador == getTurno()) {
            // Si es el turno del jugador
            System.out.println("[INFO] Jugador " + jugador + " su numero " + numeroJugador);

            // Comprobar si ha adivinado el numero
            if (numeroJugador == NumeroOculto) {
                System.out.println("[INFO] Jugador " + jugador + " ha acertado!!!");
                // Finaliza la partida
                this.finJuego = true;
                // Actualizamos el ganador
                this.jugadorGanador = jugador;
            } else {
                this.turno++;
                // Si es el ultimo, el turno "vuelve" al primero
                if (this.turno > numeroJugadores) {
                    this.turno = 1;
                }
                // Cuando "cambiamos" el turno, activamos a los hilos que estan dormidos
                notifyAll();
            }
        } else {
            // Si no es su turno dormimos el hilo
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("[ERROR] Hilo interrumpido");
                e.printStackTrace();
            }
        }
    }
}
