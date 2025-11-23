package client;
//Estas clases se ejecutan en la máquina del cliente, siendo Pi.java la tarea que se envía al servidor.
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;
import compute.Compute;
import compute.Task;

public class ComputePi {
    public static void main(String args[]) {
        // [args[0]] = IP del Servidor. [args[1]] = Precisión de Pi.
        if (args.length < 2) {
             System.err.println("Uso: java client.ComputePi <host_servidor> <precision_pi>");
             return;
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            
            // *** CLAVE: Usa args[0] (la IP) para buscar el Registry en la máquina remota. ***
            Registry registry = LocateRegistry.getRegistry(args[0]); 
            
            Compute comp = (Compute) registry.lookup(name);
            
            // Crea la tarea (Pi.java) localmente
            Task<BigDecimal> task = new Pi(Integer.parseInt(args[1]));
            
            // Envía el objeto Task a la máquina remota para ejecución
            BigDecimal pi = comp.executeTask(task); 
            
            System.out.println("Cálculo remoto completado. Pi con " + args[1] + " dígitos:");
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("Excepción del Cliente. Verifique la IP del servidor y el Firewall:");
            e.printStackTrace();
        }
    }    
}