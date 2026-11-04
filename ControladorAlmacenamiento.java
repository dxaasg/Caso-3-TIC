import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControladorAlmacenamiento {
    private int tamañoBloqueKiB;
    private Map<String, List<Integer>> asignaciones;
    private Set<Integer> bloquesDisponibles;

    public ControladorAlmacenamiento(double capacidadTotalGiB, int tamanoBloqueKiB) {
        this.tamañoBloqueKiB = tamanoBloqueKiB;
        this.asignaciones = new HashMap<>();
        this.bloquesDisponibles = new HashSet<>();
        
        double capacidadTotalKiB = capacidadTotalGiB * 1048576.0;
        int totalBloques = (int) Math.round(capacidadTotalKiB / tamanoBloqueKiB);
        
        for (int j = 0; j < totalBloques; j++) {
            bloquesDisponibles.add(j);
        }
    }
    //Exclusion mutua para poder acceder a los bloques disponibles y al mapa de asignaciones
    public synchronized boolean reservarDisco(String id, double cantAlmacenamiento) {
        int bloquesNecesarios = traducirGiBABloques(cantAlmacenamiento);
        if (bloquesNecesarios > bloquesDisponibles.size()){
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
        asignaciones.put(id, bloquesreservados);
        return true;
    }

    //Exclusion mutua para acceder a las asignaciones y a bloques disponibles
    public synchronized void liberarDisco(String id) {
        List<Integer> bloquesRemovidos = asignaciones.remove(id);
        if (bloquesRemovidos != null && !bloquesRemovidos.isEmpty()){
            bloquesDisponibles.addAll(bloquesRemovidos);
            
        }


    }

    private int traducirGiBABloques(double cantAlmacenamiento) {
        double kibTotales = cantAlmacenamiento *1048576.0;
        return (int) Math.round(kibTotales/tamañoBloqueKiB);
    }
}
    


