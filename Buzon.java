import java.util.*;

public class Buzon {
    private Queue<Evento> eventos;
    private int capacidad;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
    }

    public synchronized void depositar(Evento evento) throws InterruptedException {
        while (capacidad != -1 && eventos.size() >= capacidad) {
            wait();
        }
        eventos.add(evento);
        notifyAll();
    }

    public synchronized Evento retirar() throws InterruptedException {
        while (eventos.isEmpty()) {
            wait();
        }
        Evento evento = eventos.poll();
        notifyAll();
        return evento;
    }

}