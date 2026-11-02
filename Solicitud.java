public class Solicitud {
    private int numProcesadores; 
    private double memoriaGiB;
    private double almacenamientoGiB;
    private int id;
    private static int idSolicitud = 0;

    public Solicitud(int numProcesadores, double memoriaGiB, double almacenamientoGiB) {
        this.numProcesadores = numProcesadores;
        this.memoriaGiB = memoriaGiB;
        this.almacenamientoGiB = almacenamientoGiB;
        this.id = obtenerSiguienteId();
    }

    private static synchronized int obtenerSiguienteId() {
        return ++idSolicitud;
    }

    public int getNumProcesadores() {
        return numProcesadores;
    }

    public double getMemoriaGiB() {
        return memoriaGiB;
    }

    public double getAlmacenamientoGiB() {
        return almacenamientoGiB;
    }

}
