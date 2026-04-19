import java.util.*;

public class Sensor extends Thread {

    private int idSensor;
    private int numEventos;
    private int numServidores;
    private Buzon buzonEntrada;
    private Random random = new Random();

    public Sensor(int idSensor, int numEventos, int numServidores, Buzon buzonEntrada) {
        this.idSensor = idSensor;
        this.numEventos = numEventos;
        this.numServidores = numServidores;
        this.buzonEntrada = buzonEntrada;
    }

    public void run() {
        for (int consecutivo = 1; consecutivo <= numEventos; consecutivo++) {
            int tipoDestino = random.nextInt(numServidores) + 1;

            Evento evento = new Evento(idSensor, consecutivo, tipoDestino, false);

            try {
                buzonEntrada.depositar(evento);
                System.out.println("Sensor " + idSensor + " generó el evento " + evento.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("Sensor " + idSensor + " terminó.");
        }
    }
}