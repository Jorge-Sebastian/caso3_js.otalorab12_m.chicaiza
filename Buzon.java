import java.util.*;

public class Buzon {
    private String nombre;
    private Queue<Evento> eventos;
    private int capacidad;

    public Buzon(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
    }

    public synchronized void depositar (Evento evento) throws InterruptedException {
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

Buzon buzonEntrada = new Buzon("Entrada", -1);

Buzon buzonAlertas = new Buzon("Alertas", -1);

private int tam1;
Buzon buzonClasificacion = new Buzon("Clasificacion", tam1);

private int ns;
Buzon[] buzonesConsolidacion = new Buzon[ns];
    for (int i = 0 ; i < ns; i++) {
    int tam2;
    buzonesConsolidacion[i] = new Buzon("Consolidacion " + i, tam2);
    }   

