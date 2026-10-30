import java.util.ArrayList;
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
   //EXCLUSIÓN MUTUA para reservar las cpus (se accede al recurso compartido de cpus disponibles)
   public synchronized boolean reservarCPUs( int idMV, int cantidad){
      if (cantidad > cpusDisponibles.size() ){
         return false;
      }
      List<Integer> procesadoresAsignados = new ArrayList<Integer>();
      for (Integer cpu :cpusDisponibles){
         procesadoresAsignados.add(cpu);
         if (procesadoresAsignados.size()== cantidad) {
            break;
         }
      }

      cpusDisponibles.removeAll(procesadoresAsignados);
      asignaciones.put(idMV,procesadoresAsignados);
      return true;
         


   }
   // EXCLUSIÓN MUTUA para liberar las cpus(tambien se accede al recurso compartido de cpus disponibles)
   public synchronized void liberarCPUs(int idMV){
       List<Integer> procesadoresDevueltos = asignaciones.remove(idMV);
        if (procesadoresDevueltos.size() != 0) {
            cpusDisponibles.addAll(procesadoresDevueltos);
        }
      
   }


}