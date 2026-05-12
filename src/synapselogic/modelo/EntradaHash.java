package synapselogic.modelo;

/**
 * Nodo de la lista enlazada usada en el manejo de colisiones de la TablaHash.
 * Cada entrada guarda un neurotransmisor y apunta al siguiente nodo de la cadena.
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class EntradaHash {

    private String clave;
    private Neurotransmisor valor;
    private EntradaHash siguiente;

    /**
     * Constructor de la clase EntradaHash.
     * @param clave ID del neurotransmisor (ej: "GLU")
     * @param valor objeto Neurotransmisor asociado a la clave
     */
    public EntradaHash(String clave, Neurotransmisor valor) {
        this.clave     = clave;
        this.valor     = valor;
        this.siguiente = null;
    }

    /**
     * Obtiene la clave de esta entrada.
     * @return ID del neurotransmisor
     */
    public String getClave() {
        return clave;
    }

    /**
     * Obtiene el neurotransmisor almacenado en esta entrada.
     * @return objeto Neurotransmisor
     */
    public Neurotransmisor getValor() {
        return valor;
    }

    /**
     * Obtiene la siguiente entrada en la cadena de colisiones.
     * @return siguiente EntradaHash, o null si no hay ninguna
     */
    public EntradaHash getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la siguiente entrada en la cadena de colisiones.
     * @param siguiente siguiente EntradaHash de la cadena
     */
    public void setSiguiente(EntradaHash siguiente) {
        this.siguiente = siguiente;
    }
}
