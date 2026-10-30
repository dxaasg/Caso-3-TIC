import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControladorAlmacenamiento {
    private int tamañoBloqueMiB;
    private Map<Integer, List<Integer>> asignaciones;
    private Set<Integer> bloquesDisponibles;

    public ControladorAlmacenamiento(double capacidadTotalGiB, int tamanoBloqueMiB) {
        this.tamañoBloqueMiB = tamanoBloqueMiB;
        this.asignaciones = new HashMap<>();
        this.bloquesDisponibles = new HashSet<>();
        
        int totalBloques = (int) Math.round((capacidadTotalGiB * 1024.0) / tamanoBloqueMiB);
        
        for (int j = 0; j < totalBloques; j++) {
            bloquesDisponibles.add(j);
        }
    }
    //Exclusion mutua para poder acceder a los bloques disponibles y al mapa de asignaciones
    public synchronized boolean reservarDisco(int idMV, double cantAlmacenamiento) {
        int bloquesNecesarios = traducirGiBABloques(cantAlmacenamiento);
        if (traducirGiBABloques(cantAlmacenamiento) > bloquesDisponibles.size()){
            return false;
        }
        ArrayList<Integer> bloquesreservados = new ArrayList<>();
        for (Integer bloque: bloquesDisponibles){
            bloquesreservados.add( bloque);
            if (bloquesreservados.size()== bloquesNecesarios){
                break;
            }
        }
        bloquesDisponibles.removeAll(bloquesreservados);
        asignaciones.put(idMV, bloquesreservados);
        return true;
    }

    //Exclusion mutua para acceder a las asignaciones y a bloques disponibles
    public synchronized void liberarDisco(int idMV) {
        List<Integer> bloquesRemovidos = asignaciones.remove(idMV);
        if (bloquesRemovidos != null && !bloquesRemovidos.isEmpty()){
            bloquesDisponibles.addAll(bloquesRemovidos);
            
        }


    }

    private int traducirGiBABloques(double cantAlmacenamiento) {
        double mibTotales = cantAlmacenamiento *1024.0;
        return (int) Math.round(mibTotales/tamañoBloqueMiB);
    }
}
    


