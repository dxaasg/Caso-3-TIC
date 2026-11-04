public class ZonaEspera{

    private int cantMvEnEspera = 0;
    private int umbral;

    public ZonaEspera(int umbral) {
        this.umbral = umbral;
    }

    public synchronized void esperarDisponibilidad() {
        try {
            cantMvEnEspera++;
            wait();
            cantMvEnEspera--; //preguntar si esta bien poner eso ahi
        } catch (Exception e) {
           Thread.currentThread().interrupt();
           cantMvEnEspera--;
           System.out.println("Una MV fue interrumpida mientras esperaba en la Zona de Espera");
        }
    }

    public synchronized void notificarLiberacion() {
        notifyAll();
    }
    
    public synchronized int getCantMvEnEspera(){
        return cantMvEnEspera;
    }

    public int getUmbral() {
        return umbral;
    }

}