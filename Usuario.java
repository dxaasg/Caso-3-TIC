import java.util.Random;

public class Usuario implements Runnable{
    private int id;
    private int solicitudesAGenerar;
    private BuzonEntrada buzon; 

    public Usuario(int id, int solicitudesAGenerar, BuzonEntrada buzon) {
        this.id = id;
        this.solicitudesAGenerar = solicitudesAGenerar;
        this.buzon = buzon;
    }

    private int capMaxProc;
    private double maxMemoria;
    private double maxAlmacenamiento;

    @Override
    public void run() {
         for (int i = 1; i <= solicitudesAGenerar; i++){
            escribirYenviarSol();
         }
         buzon.Termine();
    }

    public void escribirYenviarSol(){

        Random random = new Random();
        
        //Generar los datos seudoaleatoriamente necesarios para la solicitud
        int topeProc = (int) Math.round(capMaxProc * 0.75);
        int numProcesadores = random.nextInt(topeProc) + 1;

        double topeMem = maxMemoria * 0.75;
        double memGenerada = 1 + (topeMem - 1) * random.nextDouble(); 
        double memoriaGiB = potenDosInferior(memGenerada);

        double topeAlm = maxAlmacenamiento * 0.75;
        double almGenerado = 1 + (topeAlm - 1) * random.nextDouble();
        double almacenamientoGiB = multDosInferior(almGenerado);


        Solicitud solicitud = new Solicitud(numProcesadores, memoriaGiB, almacenamientoGiB);

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

    public double multDosInferior(double almacenamiento){
        if(almacenamiento < 1){
            return 1;
        }

        return Math.floor(almacenamiento / 2.0) * 2.0; //se usa math.floor por que este siempre redondea hacia abajo, no como el round normal
    }

}
