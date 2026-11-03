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

            if(zonaEspera.getCantMvEnEspera() >= zonaEspera.getUmbral()){
                buzon.bloquearPorGestor();
                while(zonaEspera.getCantMvEnEspera() >= zonaEspera.getUmbral()){
                    Thread.yield();
                }
                buzon.desbloquearPorGestor();

            }
            
            else{
                
                while(buzon.getColaSolicitudes().isEmpty()){
                Thread.yield();
                }
                Solicitud solicitud = buzon.retirarSolicitud();
                crearMV(solicitud);
            }

            termine();
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