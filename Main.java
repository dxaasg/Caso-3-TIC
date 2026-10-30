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
        // 3. Crear y arrancar los hilos de los Usuarios
        // 4. (Opcional) Esperar a que todos los Usuarios terminen de enviar solicitudes
        System.out.println(">>> Todos los usuarios han terminado de generar sus solicitudes.");
    }
}