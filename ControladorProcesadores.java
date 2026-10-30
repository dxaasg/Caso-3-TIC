import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControladorProcesadores {
   private Map<Integer, List<Integer>> asignaciones;
   private Set<Integer> cpusDisponibles;
   public ControladorProcesadores(int totalProcesadores) {
        this.asignaciones = new HashMap<>();
        this.cpusDisponibles = new HashSet<>();
        
        for (int j = 0; j < totalProcesadores; j++) {
            cpusDisponibles.add(j);
        }
    }

   public boolean reservarCPUs( int idMV, int cantidad){
      return false;

   }
   public void liberarCPUs(int idMV){
      
   }


}