public class MaquinaVirtual extends Thread{
    private int id;
    private int cantProcesadores;
    private int cantMemoria;
    private int cantAlmacenamiento;
    private ControladorProcesadores ctrlProcesador;
    private ControladorAlmacenamiento ctrlAlmacen;
    private ControladorMemoria ctrlMemo;
    private ZonaEspera esperar;


    @Override 
    public void run(){
        ctrlProcesador.reservarCPUs(id,cantProcesadores);
        ctrlMemo.reservarMemoria(id,cantMemoria);
        ctrlAlmacen.reservarDisco(id,cantAlmacenamiento);
    }


}