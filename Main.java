import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema Gestor de Recursos para MVs ===");

        Properties config = new Properties();
        try (FileReader reader = new FileReader("datos.txt")) {
            config.load(reader);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo datos.txt: " + e.getMessage());
            return; 
        }

        int numUsuarios = Integer.parseInt(config.getProperty("nu"));
        int baseEventos = Integer.parseInt(config.getProperty("base"));
        int numGestores = Integer.parseInt(config.getProperty("ng"));
        
        int capacidadBuzon = Integer.parseInt(config.getProperty("tam1"));
        int umbralEspera = Integer.parseInt(config.getProperty("tam2"));
        
        int totalProcesadores = Integer.parseInt(config.getProperty("procesadores"));
        double capacidadMemoriaGiB = Double.parseDouble(config.getProperty("memoria_gib"));
        int tamPagKiB = Integer.parseInt(config.getProperty("pagina_kib"));
        
        double capacidadAlmacenamientoGiB = Double.parseDouble(config.getProperty("almacenamiento_gib"));
        int tamBloqueKiB = Integer.parseInt(config.getProperty("bloque_kib"));

        System.out.println("Configuración cargada exitosamente. Instanciando monitores...");

        ControladorProcesadores ctrlProc = new ControladorProcesadores(totalProcesadores);
        ControladorMemoria ctrlMem = new ControladorMemoria(capacidadMemoriaGiB, tamPagKiB);
        ControladorAlmacenamiento ctrlAlm = new ControladorAlmacenamiento(capacidadAlmacenamientoGiB, tamBloqueKiB);
        ZonaEspera zonaEspera = new ZonaEspera(umbralEspera);
        BuzonEntrada buzon = new BuzonEntrada(capacidadBuzon, numUsuarios);

        // 4. Crear y arrancar los hilos de los Gestores de Solicitudes
        for (int i = 0; i < numGestores; i++) {
            GestorSolicitudes gestor = new GestorSolicitudes(i + 1, buzon, ctrlProc, ctrlMem, ctrlAlm, zonaEspera);
            new Thread(gestor, "Hilo-Gestor-" + (i + 1)).start();
        }

        // 5. Crear y arrancar los hilos de los Usuarios
        for (int i = 0; i < numUsuarios; i++) {
            int solicitudesAGenerar = baseEventos* i; 
            
            Usuario usuario = new Usuario(capacidadBuzon, solicitudesAGenerar, buzon, solicitudesAGenerar, capacidadMemoriaGiB, capacidadAlmacenamientoGiB);
            new Thread(usuario, "Hilo-Usuario-" + (i + 1)).start();
        }

        System.out.println(">>> Todos los hilos han sido lanzados.");
    }
}