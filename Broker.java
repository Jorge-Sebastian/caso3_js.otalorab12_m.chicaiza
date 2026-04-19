import java.util.*;

public class Broker extends Thread {
    private Buzon buzonEntrada;
    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;
    private int totalEventos;
    private Random random;

    private boolean esAnomalo() {
        int numero = random.nextInt(201); // Genera un número entre 0 y 200
        return numero % 8 == 0; // Un evento es anómalo si el número es divisible por 8
    }

    public Broker(Buzon buzonEntrada, Buzon buzonAlertas, Buzon buzonClasificacion, int totalEventos) {
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.totalEventos = totalEventos;
        this.random = new Random();
    }

    public void run() {
        int procesados = 0;

        while (procesados < totalEventos) {
            try {
                Evento evento = buzonEntrada.retirar();

                if (esAnomalo()) {
                    buzonAlertas.depositar(evento);
                    System.out.println("Broker envió el evento " + evento.getId() + " al buzon de alertas.");
                } else {
                    buzonClasificacion.depositar(evento);
                    System.out.println("Broker envió el evento " + evento.getId() + " al buzon de clasificación.");
                }

                procesados++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Evento fin = Evento.crearEventoFin();
            buzonAlertas.depositar(fin);
            System.out.println("Broker terminó y envió evento de fin al administrador por el buzon de alertas.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
