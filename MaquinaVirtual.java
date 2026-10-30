public class MaquinaVirtual extends Thread{
    private int id;
    private int cantProcesadores;
    private double cantMemoria;
    private double cantAlmacenamiento;
    private ControladorProcesadores ctrlProcesador;
    private ControladorAlmacenamiento ctrlAlmacen;
    private ControladorMemoria ctrlMemo;
    private ZonaEspera esperar;

    public MaquinaVirtual(int id, int cantProcesadores, double cantMemoria, double cantAlmacenamiento,
                          ControladorProcesadores ctrlProcesador, ControladorMemoria ctrlMemo,
                          ControladorAlmacenamiento ctrlAlmacen, ZonaEspera esperar) {
        this.id = id;
        this.cantProcesadores = cantProcesadores;
        this.cantMemoria = cantMemoria;
        this.cantAlmacenamiento = cantAlmacenamiento;
        this.ctrlProcesador = ctrlProcesador;
        this.ctrlMemo = ctrlMemo;
        this.ctrlAlmacen = ctrlAlmacen;
        this.esperar = esperar;
    }


    @Override 
    public void run(){
        boolean recursosObtenidos = false;
        while (!recursosObtenidos) {
            if (ctrlProcesador.reservarCPUs(id, cantProcesadores)) {
                if (ctrlMemo.reservarMemoria(id, cantMemoria)) {
                    if (ctrlAlmacen.reservarDisco(id, cantAlmacenamiento)) {
                        recursosObtenidos = true; 
                    } 
                    else {
                        ctrlMemo.liberarMemoria(id);
                        ctrlProcesador.liberarCPUs(id);
                    }
                } 
                else {
                    ctrlProcesador.liberarCPUs(id);
                }
            }

            if (!recursosObtenidos) {
                esperar.esperarDisponibilidad(); //en este metodo debe agregarse el wait
            }
        }

        try {
            System.out.println("MV " + id + " iniciando ejecución...");
            Thread.sleep((long) (Math.random() * 2000 + 1000)); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("MV " + id + " terminó. Liberando recursos...");
        
        ctrlAlmacen.liberarDisco(id);
        ctrlMemo.liberarMemoria(id);
        ctrlProcesador.liberarCPUs(id);

        esperar.notificarLiberacion(); //acá toca notificar a todas las mv. 
    }

    
}


