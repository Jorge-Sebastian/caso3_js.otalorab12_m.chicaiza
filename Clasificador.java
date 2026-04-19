public class Clasificador extends Thread {
    private Buzon buzonClasificacion;
    private Buzon[] buzonesConsolidacion;

    private static int clasificadoresActivos;

    public static void inicializar(int numClasificadores) {
        clasificadoresActivos = numClasificadores;
    }

    public Clasificador(Buzon buzonClasificacion, Buzon[] buzonesConsolidacion) {
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
    }

    private static synchronized boolean soyElUltimo() {
        clasificadoresActivos--;
        return clasificadoresActivos == 0;
    }

    public void run() {
        boolean terminar = false;

        while (!terminar) {
            try {
                Evento evento = buzonClasificacion.retirar();

                if (evento.isEsFin()) {
                    if (soyElUltimo()) {
                        for (int i = 0; i < buzonesConsolidacion.length; i++) {
                            buzonesConsolidacion[i].depositar(Evento.crearEventoFin());
                        }
                        System.out.println("El último clasificador terminó y envió eventos de fin a los servidores por medio de los buzones de consolidación.");
                    }
                    terminar = true;
                } else {
                    int destino = evento.getTipoDestino() - 1;
                    buzonesConsolidacion[destino].depositar(evento);
                    System.out.println("Clasificador envió el evento " + evento.getId() + " al buzón de consolidación " + (destino + 1) + ".");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Clasificador terminó.");
    }
}
