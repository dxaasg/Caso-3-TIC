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
    
    public boolean reservarDisco(int id, double cantAlmacenamiento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reservarDisco'");
    }

    public void liberarDisco(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'liberarDisco'");
    }
}
    


