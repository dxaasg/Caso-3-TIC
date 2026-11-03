import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControladorMemoria {
    private int tamPag; //KiB
    private Map<Integer, List<Integer>> asignaciones;
    private Set<Integer> pagDisp;

    public ControladorMemoria(double capacidadTotalGiB, int tamPag) {
        this.tamPag = tamPag; 
        this.asignaciones = new HashMap<>();
        this.pagDisp = new HashSet<>();

        // Calcular el número total de páginas iniciales
        double capacidadTotalKiB = capacidadTotalGiB * 1048576.0;
        int totalPaginas = (int) Math.round(capacidadTotalKiB/ tamPag);
        for (int i = 0; i < totalPaginas; i++) {
            pagDisp.add(i);
        }
    }

    public int traducirGiBAPaginas(double gib) {
        double kibTotales = gib * 1048576.0;
        return (int) Math.round(kibTotales / tamPag);
    }
    


    // Método synchronized para aplicar exclusión mutua
    public synchronized boolean reservarMemoria(int idMV, double gib) {
        int paginasNecesarias = traducirGiBAPaginas(gib);

        if (pagDisp.size() < paginasNecesarias) {
            return false; // No hay suficientes páginas disponibles
        }

        List<Integer> paginasAsignadas = new ArrayList<>();
        for (Integer idPagina : pagDisp) {
            paginasAsignadas.add(idPagina);
            if (paginasAsignadas.size() == paginasNecesarias) {
                break;
            }
        }
        pagDisp.removeAll(paginasAsignadas);
        asignaciones.put(idMV, paginasAsignadas);
        return true;
    }

    // Método synchronized para liberar los recursos de MV
    public synchronized void liberarMemoria(int idMV) {
        List<Integer> paginasDevueltas = asignaciones.remove(idMV);
        if (paginasDevueltas != null && !paginasDevueltas.isEmpty()) {
            pagDisp.addAll(paginasDevueltas);
        }
    }       
}