package synapselogic.modelo;

/**
 * Representa una neurona del sistema nervioso dentro del grafo neuronal.
 * Almacena su identificador, nombre y las sinapsis de salida hacia otras neuronas.
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class Neurona {

    private String id;
    private String nombre;
    private Sinapsis[] conexiones;
    private int numConexiones;

    /**
     * Constructor de la clase Neurona.
     * @param id identificador unico de la neurona (ej: "N1")
     * @param nombre nombre descriptivo de la neurona
     */
    public Neurona(String id, String nombre) {
        this.id             = id;
        this.nombre         = nombre;
        this.conexiones     = new Sinapsis[50];
        this.numConexiones  = 0;
    }

    /**
     * Agrega una sinapsis de salida a esta neurona.
     * @param sinapsis sinapsis a agregar
     */
    public void agregarConexion(Sinapsis sinapsis) {
        if (numConexiones < conexiones.length) {
            conexiones[numConexiones] = sinapsis;
            numConexiones++;
        }
    }

    /**
     * Obtiene el ID de la neurona.
     * @return identificador de la neurona
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la neurona.
     * @return nombre descriptivo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el arreglo de sinapsis de salida de esta neurona.
     * @return arreglo de sinapsis
     */
    public Sinapsis[] getConexiones() {
        return conexiones;
    }

    /**
     * Obtiene el numero de sinapsis de salida de esta neurona.
     * @return cantidad de conexiones
     */
    public int getNumConexiones() {
        return numConexiones;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre;
    }
}
