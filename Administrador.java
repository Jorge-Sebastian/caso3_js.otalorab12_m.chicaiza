import java.util.*;

public class Administrador extends Thread {

    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;
    private int numClasificadores;
    private Random random;

    private boolean esInofensivo() {
        int numero = random.nextInt(21); // Genera un número entre 0 y 20
        return numero % 4 == 0; // Un evento es inofensivo si el número es divisible por 4
    }

    public Administrador(Buzon buzonAlertas, Buzon buzonClasificacion, int numClasificadores) {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.numClasificadores = numClasificadores;
        this.random = new Random();
    }

    public void run() {

        boolean terminar = false;

        while (!terminar) {
            try {
                Evento evento = buzonAlertas.retirar();

                if (evento.isEsFin()) {
                    terminar = true;
                } else {
                    if (esInofensivo()) {
                        buzonClasificacion.depositar(evento);
                        System.out.println("Administrador reviso el evento " + evento.getId()
                                + " y lo envió al buzon de clasificación.");
                    } else {
                        System.out.println("Administrador descartó el evento " + evento.getId() + ".");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            for (int i = 0; i < numClasificadores; i++) {
                buzonClasificacion.depositar(Evento.crearEventoFin());
            }
            System.out.println("Administrador terminó y envió eventos de fin a los clasificadores.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
