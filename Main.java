public class Main extends Thread {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema Gestor de Recursos para MVs ===");

        // 1. Instanciar los Monitores (Objetos Compartidos)
        ControladorProcesadores ctrlProc = new ControladorProcesadores(8); // 8 CPUs totales
        ControladorMemoria ctrlMem = new ControladorMemoria(64.0, 1024);   // 64 GiB total, páginas de 1024 MiB
        ControladorAlmacenamiento ctrlAlm = new ControladorAlmacenamiento(1000.0, 512); // 1000 GiB total, bloques de 512 MiB
        ZonaEspera zonaEspera = new ZonaEspera(5); // Umbral de 5 MVs en espera
        int capacidadBuzon = 5; // Tamaño buzón de entrada
        int totalUsuarios = 3; // Número total de usuarios
        BuzonEntrada buzon = new BuzonEntrada(capacidadBuzon, totalUsuarios);
        // 2. Crear y arrancar los hilos de los Gestores de Solicitudes
        GestorSolicitudes gestor = new GestorSolicitudes(1, buzon, ctrlProc, ctrlMem, ctrlAlm, zonaEspera);
        new Thread(gestor).start();
        // 3. Crear y arrancar los hilos de los Usuarios
        Usuario usuario1 = new Usuario(1, 3, buzon); // Usuario 1 generará 3 solicitudes
        new Thread(usuario1).start();
        // 4. (Opcional) Esperar a que todos los Usuarios terminen de enviar solicitudes ???
        System.out.println(">>> Todos los usuarios han terminado de generar sus solicitudes.");
    }
}