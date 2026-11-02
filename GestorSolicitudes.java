public class GestorSolicitudes implements Runnable {

    private int idGestor;
    private BuzonEntrada buzon; 
    private  ControladorProcesadores ctrlProc;
    private  ControladorMemoria ctrlMem;
    private  ControladorAlmacenamiento ctrlAlm;
    private  ZonaEspera zonaEspera;

    public GestorSolicitudes(int id, BuzonEntrada buzon2, ControladorProcesadores ctrlProc2, ControladorMemoria ctrlMem2,
            ControladorAlmacenamiento ctrlAlm2, ZonaEspera zonaEspera2) {
        this.idGestor = id;
        this.buzon = buzon2;
        this.ctrlProc = ctrlProc2;
        this.ctrlMem = ctrlMem2;
        this.ctrlAlm = ctrlAlm2;
        this.zonaEspera = zonaEspera2;
    }

    public void run(){
        while(buzon.getUsuariosActivos() > 0 || !buzon.getColaSolicitudes().isEmpty()){

            if(zonaEspera.getCantMvEnEspera() < zonaEspera.getUmbral()){
                while(buzon.getColaSolicitudes().isEmpty()){
                    Thread.yield();
                }

                for(int i = 0; i < buzon.getColaSolicitudes().size(); i++){
                    Solicitud solicitud = buzon.getColaSolicitudes().remove(0); 
                    procesarSol(solicitud);
                }

            }
            else{
                procesarTodasSolicitudes();
            }

            termine();
        }
    }

    public synchronized void procesarSol(Solicitud solicitud){ { 
            crearMV(solicitud);
        }
    }

    public synchronized void procesarTodasSolicitudes(){
        buzon.bloquearPorGestor();
        System.out.println("Gestor " + idGestor + ": Umbral alcanzado. Buzón bloqueado.");

        while(!buzon.getColaSolicitudes().isEmpty()){
            Solicitud solicitud = buzon.getColaSolicitudes().remove(0);
            procesarSol(solicitud); //si el gestor debe de esperar hasta que se procesen todas las solicitudes, entonces quien procesa todas las solicitudes?
        }
    }

    public synchronized void crearMV(Solicitud solicitud){
        MaquinaVirtual mv = new MaquinaVirtual(idGestor, solicitud.getNumProcesadores(), solicitud.getMemoriaGiB(),
                solicitud.getAlmacenamientoGiB(), ctrlProc, ctrlMem, ctrlAlm, zonaEspera);
        Thread hiloMV = new Thread(mv); //preguntar si esto esta bien o va en el main
        hiloMV.start();
    }

    public synchronized void termine(){
        if(buzon.getUsuariosActivos() == 0 && buzon.getColaSolicitudes().isEmpty()){
            System.out.println("Gestor " + idGestor + ": No hay más solicitudes. Terminando.");
        }
    }

}
