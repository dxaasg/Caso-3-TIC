public class Main {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema Gestor de Recursos para MVs ===");

        // 1. Instanciar los Monitores (Objetos Compartidos)
        ControladorProcesadores ctrlProc = new ControladorProcesadores(8); // 8 CPUs totales
        ControladorMemoria ctrlMem = new ControladorMemoria(64.0, 1024);   // 64 GiB total, páginas de 1024 MiB
        ControladorAlmacenamiento ctrlAlm = new ControladorAlmacenamiento(1000.0, 512); // 1000 GiB total, bloques de 512 MiB

        ZonaEspera zonaEspera = new ZonaEspera();
        
        int capacidadBuzon = 5; // Tamaño buzón de entrada
        BuzonEntrada buzon = new BuzonEntrada(capacidadBuzon);

        // 2. Crear y arrancar los hilos de los Gestores de Solicitudes
        int cantGestores = 2;
        Thread[] hilosGestores = new Thread[cantGestores];
        
        for (int i = 0; i < cantGestores; i++) {
            GestorSolicitudes gestor = new GestorSolicitudes(
                i + 1, buzon, ctrlProc, ctrlMem, ctrlAlm, zonaEspera
            );
            hilosGestores[i] = new Thread(gestor, "Gestor-" + (i + 1));
            // Los gestores pueden ser hilos Daemon o gestionarse hasta completar todas las solicitudes
            hilosGestores[i].start();
        }

        // 3. Crear y arrancar los hilos de los Usuarios
        int cantUsuarios = 3;
        int solicitudesAGenerar = 4; // Cada usuario genera N solicitudes
        Thread[] hilosUsuarios = new Thread[cantUsuarios];

        for (int i = 0; i < cantUsuarios; i++) {
            Usuario usuario = new Usuario(i + 1, solicitudesAGenerar, buzon);
            hilosUsuarios[i] = new Thread(usuario, "Usuario-" + (i + 1));
            hilosUsuarios[i].start();
        }

        // 4. (Opcional) Esperar a que todos los Usuarios terminen de enviar solicitudes
        for (Thread hUsuario : hilosUsuarios) {
            try {
                hUsuario.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(">>> Todos los usuarios han terminado de generar sus solicitudes.");
    }
}