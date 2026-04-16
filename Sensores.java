public class Sensores extends Thread {
    private Evento evento;
    private int n; // Número de eventos a generar'
    private int i;

    public void crearEvento(int id, String nombre, String descripcion) {
        while (i < n) {
            id = i;
            this.evento = new Evento(id, nombre, descripcion);
            i++;
        }
    }




    
}
