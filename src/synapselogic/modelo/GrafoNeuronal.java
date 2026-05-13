package synapselogic.modelo;

/**
 * Grafo dirigido que representa una red neuronal.
 * Usa lista de adyacencia implementada con arreglos.
 * Tambien contiene la tabla hash de neurotransmisores del diccionario cargado.
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class GrafoNeuronal {

    private Neurona[] neuronas;
    private int numNeuronas;
    private TablaHash tablaHash;

    /**
     * Constructor de la clase GrafoNeuronal.
     * Inicializa el grafo con capacidad para 200 neuronas.
     */
    public GrafoNeuronal() {
        this.neuronas    = new Neurona[200];
        this.numNeuronas = 0;
        this.tablaHash   = new TablaHash();
    }

    /**
     * Agrega una neurona al grafo si no existe ya.
     * @param neurona neurona a agregar
     */
    public void agregarNeurona(Neurona neurona) {
        if (obtenerNeurona(neurona.getId()) == null && numNeuronas < neuronas.length) {
            neuronas[numNeuronas] = neurona;
            numNeuronas++;
        }
    }

    /**
     * Agrega una sinapsis dirigida entre dos neuronas existentes en el grafo.
     * @param origen ID de la neurona de origen
     * @param destino ID de la neurona de destino
     * @param distancia distancia de la sinapsis
     * @param idNeurotransmisor ID del neurotransmisor asociado
     * @param factorK factor de transmision inicial
     */
    public void agregarSinapsis(String origen, String destino, double distancia,
                                String idNeurotransmisor, double factorK) {
        Neurona nOrigen = obtenerNeurona(origen);
        if (nOrigen != null) {
            Sinapsis s = new Sinapsis(origen, destino, distancia, idNeurotransmisor, factorK);
            nOrigen.agregarConexion(s);
        }
    }

    /**
     * Busca y retorna una neurona por su ID.
     * @param id identificador de la neurona a buscar
     * @return la Neurona encontrada, o null si no existe
     */
    public Neurona obtenerNeurona(String id) {
        for (int i = 0; i < numNeuronas; i++) {
            if (neuronas[i].getId().equals(id)) return neuronas[i];
        }
        return null;
    }

    /**
     * Simula fatiga cognitiva multiplicando el factorK de todas las sinapsis por 1.2.
     */
    public void simularFatiga() {
        for (int i = 0; i < numNeuronas; i++) {
            Sinapsis[] conexiones = neuronas[i].getConexiones();
            for (int j = 0; j < neuronas[i].getNumConexiones(); j++) {
                conexiones[j].setFactorK(conexiones[j].getFactorK() * 1.2);
            }
        }
    }

    /**
     * Elimina todos los datos del grafo y vacia la tabla hash.
     */
    public void vaciar() {
        this.neuronas    = new Neurona[200];
        this.numNeuronas = 0;
        this.tablaHash.vaciar();
    }

    /**
     * Obtiene el arreglo de neuronas del grafo.
     * @return arreglo de neuronas
     */
    public Neurona[] getNeuronas() {
        return neuronas;
    }

    /**
     * Obtiene el numero de neuronas en el grafo.
     * @return cantidad de neuronas
     */
    public int getNumNeuronas() {
        return numNeuronas;
    }

    /**
     * Obtiene la tabla hash de neurotransmisores.
     * @return tabla hash del diccionario
     */
    public TablaHash getTablaHash() {
        return tablaHash;
    }
}
