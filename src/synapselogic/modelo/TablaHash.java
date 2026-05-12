package synapselogic.modelo;

/**
 * Tabla hash para almacenar neurotransmisores con busqueda e insercion O(1).
 * Usa encadenamiento separado para manejar colisiones.
 * La funcion de dispersion es propia, basada en multiplicacion por el primo 31.
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class TablaHash {

    private EntradaHash[] tabla;
    private int capacidad;
    private int cantidad;

    /**
     * Constructor de la clase TablaHash.
     * Inicializa la tabla con capacidad fija de 64 posiciones.
     */
    public TablaHash() {
        this.capacidad = 64;
        this.cantidad  = 0;
        this.tabla     = new EntradaHash[capacidad];
    }

    /**
     * Calcula el indice en la tabla para una clave dada.
     * Funcion propia basada en multiplicacion por el primo 31.
     * @param clave ID del neurotransmisor
     * @return indice valido dentro del arreglo
     */
    private int calcularIndice(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % capacidad;
        }
        return Math.abs(hash);
    }

    /**
     * Inserta un neurotransmisor en la tabla.
     * Si la clave ya existe, no hace nada.
     * @param nt neurotransmisor a insertar
     */
    public void insertar(Neurotransmisor nt) {
        int indice = calcularIndice(nt.getId());
        EntradaHash actual = tabla[indice];

        // Recorremos la cadena para ver si la clave ya existe
        while (actual != null) {
            if (actual.getClave().equals(nt.getId())) return;
            actual = actual.getSiguiente();
        }

        // Insertamos al inicio de la cadena
        EntradaHash nueva = new EntradaHash(nt.getId(), nt);
        nueva.setSiguiente(tabla[indice]);
        tabla[indice] = nueva;
        cantidad++;
    }

    /**
     * Busca y retorna el neurotransmisor asociado a la clave dada.
     * @param clave ID del neurotransmisor (ej: "GLU")
     * @return el Neurotransmisor encontrado, o null si no existe
     */
    public Neurotransmisor obtener(String clave) {
        int indice = calcularIndice(clave);
        EntradaHash actual = tabla[indice];

        // Recorremos la cadena buscando la clave
        while (actual != null) {
            if (actual.getClave().equals(clave)) return actual.getValor();
            actual = actual.getSiguiente();
        }

        return null;
    }

    /**
     * Elimina todos los elementos de la tabla.
     * Se usa cuando el usuario carga un diccionario distinto.
     */
    public void vaciar() {
        this.tabla    = new EntradaHash[capacidad];
        this.cantidad = 0;
    }

    /**
     * Obtiene la cantidad de neurotransmisores almacenados.
     * @return numero de elementos insertados
     */
    public int getCantidad() {
        return cantidad;
    }
}
