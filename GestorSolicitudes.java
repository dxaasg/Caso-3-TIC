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

    public void run() {
        while (!buzon.terminado()) {

            if (zonaEspera.getCantMvEnEspera() >= zonaEspera.getUmbral()) {
               
                buzon.bloquearPorGestor();
                while (zonaEspera.getCantMvEnEspera() > 0) {
                    Thread.yield();
                }
                buzon.desbloquearPorGestor();

            } else {
                Solicitud solicitud = buzon.retirarSolicitud();
                if (solicitud == null) {
                    Thread.yield(); 
                } else {
                    crearMV(solicitud);
                }
            }
        }

        System.out.println("Gestor " + idGestor + ": No hay más solicitudes. Terminando.");
    }


    public synchronized void crearMV(Solicitud solicitud){
        MaquinaVirtual mv = new MaquinaVirtual(solicitud.getId(), solicitud.getNumProcesadores(), solicitud.getMemoriaGiB(),
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