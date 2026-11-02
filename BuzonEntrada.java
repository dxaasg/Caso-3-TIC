import java.util.List;

public class BuzonEntrada {
        private int capacidadMaxima;
        private List<Solicitud> colaSolicitudes;
        private boolean noLleno;
        private boolean noVacio;
        private int usuariosActivos;

        private boolean bloqueado = false;

    public BuzonEntrada(int capacidadBuzon, int totalUsuarios) {
        this.capacidadMaxima = capacidadBuzon;
        this.colaSolicitudes = new java.util.ArrayList<>();
        this.noLleno = true;
        this.noVacio = false;
        this.usuariosActivos = totalUsuarios;
    } 

    public synchronized void enviar(Solicitud solicitud) {
        while (colaSolicitudes.size() == capacidadMaxima || bloqueado) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        colaSolicitudes.add(solicitud);
        noVacio = true;
        notifyAll();
    }

    public synchronized void Termine() {
        usuariosActivos--; 
        notifyAll();       
    }

    public synchronized void bloquearPorGestor(){
        bloqueado = true;
    }

    public synchronized void desbloquearPorGestor(){
        bloqueado = false;
        notifyAll();
    }

    public int getUsuariosActivos() {
        return usuariosActivos;
    }

    public List<Solicitud> getColaSolicitudes() {
        return colaSolicitudes;
    }

    public int getcapacidadMaxima() {
        return capacidadMaxima;
    }

}
