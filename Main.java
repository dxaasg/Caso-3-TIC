public class Main extends Thread {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema Gestor de Recursos para MVs ===");

        // 1. Instanciar los Monitores (Objetos Compartidos)
        int totalProcesadores = 0;
        int capacidadTotalGiBMemoria = 0;
        int tamPagKiB =0 ;
        int capacidadTotalGiBAlmacenamiento = 0;
        int tamañoBloqueKiB = 0;
        int umbral = 0;


        ControladorProcesadores ctrlProc = new ControladorProcesadores(totalProcesadores); // CPUs totales
        ControladorMemoria ctrlMem = new ControladorMemoria(capacidadTotalGiBMemoria, tamPagKiB);   // GiB total,  tamañoPagKiB
        ControladorAlmacenamiento ctrlAlm = new ControladorAlmacenamiento(capacidadTotalGiBAlmacenamiento, tamañoBloqueKiB); // GiB total, tamañoBloqueKiB
        ZonaEspera zonaEspera = new ZonaEspera(umbral); // x MVs en espera

        int capacidadBuzon = 0; // Tamaño buzón de entrada
        int totalUsuarios = 0; // Número total de usuarios

        BuzonEntrada buzon = new BuzonEntrada(capacidadBuzon, totalUsuarios);
        // 2. Crear y arrancar los hilos de los Gestores de Solicitudes

        GestorSolicitudes gestor = new GestorSolicitudes(1, buzon, ctrlProc, ctrlMem, ctrlAlm, zonaEspera);
        new Thread(gestor).start();
        // 3. Crear y arrancar los hilos de los Usuarios
        for (int i = 0; i < numUsuarios; i++){
            Buzon buzon = this.buzon;
            int solicitudesAGenerar = 0; //TODO
            Usuario usuario = new Usuario(i, solicitudesAGenerar,buzon);
        }
        // 4. (Opcional) Esperar a que todos los Usuarios terminen de enviar solicitudes ???
        System.out.println(">>> Todos los usuarios han terminado de generar sus solicitudes.");
    }
}