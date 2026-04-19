public class Evento {

    private String id;
    private int idSensor;
    private int consecutivo;
    private int tipoDestino;
    private Boolean esFin;

    public Evento(String id, int idSensor, int consecutivo, int tipoDestino, Boolean esFin) {
        this.id = "S" + idSensor + "-E" + consecutivo;
        this.idSensor = idSensor;
        this.consecutivo = consecutivo;
        this.tipoDestino = tipoDestino;
        this.esFin = esFin;
    }

    public static Evento crearEventoFin() {
        return new Evento("FIN", -1, -1, -1, true);
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

    public Boolean getEsFin() {
        return esFin;
    }
}