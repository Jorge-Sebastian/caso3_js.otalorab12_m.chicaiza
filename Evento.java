public class Evento {

    private String id;
    private int idSensor;
    private int consecutivo;
    private int tipoDestino;
    private boolean esFin;

    public Evento(int idSensor, int consecutivo, int tipoDestino, boolean esFin) {
        this.id = "S" + idSensor + "-E" + consecutivo;
        this.idSensor = idSensor;
        this.consecutivo = consecutivo;
        this.tipoDestino = tipoDestino;
        this.esFin = esFin;
    }

    public static Evento crearEventoFin() {
        Evento fin = new Evento(-1, -1, -1, true);
        fin.id = "FIN";
        return fin;
    }

    public String getId() {
        return id;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public int getTipoDestino() {
        return tipoDestino;
    }

    public boolean isEsFin() {
        return esFin;
    }
}