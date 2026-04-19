import java.util.*;

public class Servidor extends Thread {
    private int idServidor;
    private Buzon buzonConsolidacion;
    private Random random;

    public Servidor(int idServidor, Buzon buzonConsolidacion) {
        this.idServidor = idServidor;
        this.buzonConsolidacion = buzonConsolidacion;
        this.random = new Random();
    }
    
    private void procesarEvento(Evento evento) {
        int tiempo = random.nextInt(901) + 100; // Genera un tiempo de procesamiento entre 100 y 1000 ms
        System.out.println("Servidor " + idServidor + " está procesando el evento " + evento.getId() + ".");
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            System.out.println("Servidor " + idServidor + " fue interrumpido mientras procesaba el evento " + evento.getId() + ".");
            e.printStackTrace();
        }
        System.out.println("Servidor " + idServidor + " ha terminado de procesar el evento " + evento.getId() + ".");
    }

    public void run() {
        boolean terminar = false;

        while(!terminar) {
            try {
                Evento evento = buzonConsolidacion.retirar();

                if(evento.isEsFin()) {
                    terminar = true;
                } else {
                    procesarEvento(evento);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Servidor " + idServidor + " terminó.");
    }
}
