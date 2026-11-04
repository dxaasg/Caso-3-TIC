import java.util.Random;

public class Usuario implements Runnable {
    private int id;
    private int solicitudesAGenerar;
    private BuzonEntrada buzon; 
    private int capMaxProc;
    private double maxMemoria;
    private double maxAlmacenamiento;

    public Usuario(int id, int solicitudesAGenerar, BuzonEntrada buzon, int capMaxProc, double maxMemoria, double maxAlmacenamiento) {
        this.id = id;
        this.solicitudesAGenerar = solicitudesAGenerar;
        this.buzon = buzon;
        this.capMaxProc = capMaxProc;
        this.maxMemoria = maxMemoria;
        this.maxAlmacenamiento = maxAlmacenamiento;
    }

    @Override
    public void run() {

        while(buzon.getcapacidadMaxima() == 0){ //este while deberia ser synchronized?
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 1; i <= solicitudesAGenerar; i++){
            escribirYenviarSol(i);
         }

         System.out.println("Usuario " + id + ": generó todas las solicitudes (" + solicitudesAGenerar + ").");
         buzon.Termine();
    }

    public void escribirYenviarSol(int secuencial) {

        Random random = new Random();
        
        // Generar ID único de la solicitud
        String idSolicitud = id + "-" + secuencial;

        //Generar los datos seudoaleatoriamente necesarios para la solicitud
        int topeProc = (int) Math.round(capMaxProc * 0.75);
        int numProcesadores = random.nextInt(topeProc) + 1;

        double topeMem = maxMemoria * 0.75;
        double memGenerada = 1 + (topeMem - 1) * random.nextDouble(); 
        double memoriaGiB = potenDosInferior(memGenerada);

        double topeAlm = maxAlmacenamiento * 0.75;
        double almGenerado = 1 + (topeAlm - 1) * random.nextDouble();
        double almacenamientoGiB = multDosInferior(almGenerado);


        Solicitud solicitud = new Solicitud(idSolicitud, numProcesadores, memoriaGiB, almacenamientoGiB);

        buzon.enviar(solicitud);
    }


    public double potenDosInferior(double memoria){
        if(memoria < 1){
            return 1;
        }

        double potencia = 1 ;
        while (potencia*2 <= memoria){
            potencia *=2;
        }
        return potencia;
    }

    public double multDosInferior(double almacenamiento) {
        double r = Math.floor(almacenamiento / 2.0) * 2.0;
        return Math.max(2.0, r);
    }

}
