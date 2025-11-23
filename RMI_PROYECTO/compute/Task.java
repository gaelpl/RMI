package compute;

import java.io.Serializable;

// La tarea debe ser Serializable para poder ser enviada (por valor) del cliente al servidor.
public interface Task<T> extends Serializable { 
    T execute();
}