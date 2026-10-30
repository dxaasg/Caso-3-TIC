public class ZonaEspera{

    public synchronized void esperarDisponibilidad() {
        try {
            wait();
        } catch (Exception e) {
           Thread.currentThread().interrupt();
           System.out.println("Una MV fue interrumpida mientras esperaba en la Zona de Espera");
        }
    }

    public void notificarLiberacion() {
        notifyAll();
    }
    
}