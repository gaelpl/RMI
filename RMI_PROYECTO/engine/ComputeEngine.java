package engine;
//Esta clase recibe la tarea remota del cliente y la ejecuta localmente.
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import compute.Compute;
import compute.Task;

public class ComputeEngine implements Compute {
    
    public ComputeEngine() {
        super();
    }

    // Método remoto: recibe cualquier objeto Task y llama a su método execute()
    public <T> T executeTask(Task<T> t) {
        return t.execute();
    }

    public static void main(String[] args) {
        // Obligatorio para permitir que RMI descargue código (la clase Pi)
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            ComputeEngine engine = new ComputeEngine();
            
            // Exporta el objeto para recibir llamadas remotas
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            
            // Obtiene y enlaza el stub al RMI Registry local
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            
            System.out.println("ComputeEngine enlazado y listo para recibir tareas.");
        } catch (Exception e) {
            System.err.println("Excepción de ComputeEngine:");
            e.printStackTrace();
        }
    }
}