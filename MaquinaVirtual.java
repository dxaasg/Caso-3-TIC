public class MaquinaVirtual extends Thread{
    private int id;
    private int cantProcesadores;
    private int cantMmemoria;
    private ControladorProcesador ctrlProcesador;
    private ControladorAlmacenamiento ctrlAlmacen;
    private ControladorMemoria ctrlMemo;
    private ZonaEspera esperar;


}