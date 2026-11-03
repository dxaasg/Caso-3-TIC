public class Solicitud {
    private int numProcesadores; 
    private double memoriaGiB;
    private double almacenamientoGiB;
    private String id;

    public Solicitud(String id, int numProcesadores, double memoriaGiB, double almacenamientoGiB) {
        this.id = id;
        this.numProcesadores = numProcesadores;
        this.memoriaGiB = memoriaGiB;
        this.almacenamientoGiB = almacenamientoGiB;
    }

    public String getId() {
        return id;
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
