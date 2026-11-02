import java.io.BufferedWriter;
import java.io.FileWriter;  
import java.io.IOException;

public class EscribirTXT {
    public static void archivo(int numUsuario, int base, int numGestores, int capBuzon, int capZona, double numProcesadores, double memoria, double almacenamiento) {
        String nombreArchivo = "configuracion.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Número de Usuario: " + numUsuario);
            writer.newLine();
            writer.write("Base: " + base);
            writer.newLine();
            writer.write("Número de Gestores: " + numGestores);
            writer.newLine();
            writer.write("Capacidad del Buzón: " + capBuzon);
            writer.newLine();
            writer.write("Capacidad de la Zona de Espera: " + capZona);
            writer.newLine();
            writer.write("Número de Procesadores: " + numProcesadores);
            writer.newLine();
            writer.write("Memoria (GiB): " + memoria);
            writer.newLine();
            writer.write("Almacenamiento (GiB): " + almacenamiento);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
