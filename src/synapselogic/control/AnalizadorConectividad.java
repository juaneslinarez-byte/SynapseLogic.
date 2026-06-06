package synapselogic.control;

import synapselogic.modelo.GrafoNeuronal;
import synapselogic.modelo.Neurona;
import synapselogic.modelo.Sinapsis;

/**
 * Analiza la conectividad de una red neuronal usando BFS o DFS.
 * Determina que neuronas son alcanzables desde un origen y cuales estan aisladas.
 *
 * @author Juan Linarez
 */
public class AnalizadorConectividad {

    private String[] alcanzables;
    private int numAlcanzables;
    private String[] aisladas;
    private int numAisladas;

    /**
     * Constructor de la clase AnalizadorConectividad.
     */
    public AnalizadorConectividad() {
        this.alcanzables    = new String[0];
        this.numAlcanzables = 0;
        this.aisladas       = new String[0];
        this.numAisladas    = 0;
    }

    /**
     * Ejecuta BFS desde una neurona origen y clasifica las demas como
     * alcanzables o aisladas.
     * @param grafo grafo neuronal sobre el que se realiza el analisis
     * @param idOrigen ID de la neurona desde donde inicia el recorrido
     */
    public void analizarDesde(GrafoNeuronal grafo, String idOrigen) {
        int n            = grafo.getNumNeuronas();
        Neurona[] neuronas = grafo.getNeuronas();

        boolean[] visitado = new boolean[n];
        String[]  cola     = new String[n];
        int frente = 0, fin = 0;

        alcanzables    = new String[n];
        numAlcanzables = 0;
        aisladas       = new String[n];
        numAisladas    = 0;

        // Buscar el indice de la neurona origen
        int indiceOrigen = -1;
        for (int i = 0; i < n; i++) {
            if (neuronas[i].getId().equals(idOrigen)) {
                indiceOrigen = i;
                break;
            }
        }

        if (indiceOrigen == -1) return;

        // BFS: encolar origen y marcar como visitado
        cola[fin++]          = idOrigen;
        visitado[indiceOrigen] = true;

        while (frente < fin) {
            String  actual  = cola[frente++];
            Neurona nActual = grafo.obtenerNeurona(actual);
            alcanzables[numAlcanzables++] = actual;

            Sinapsis[] conexiones = nActual.getConexiones();

            for (int i = 0; i < nActual.getNumConexiones(); i++) {
                String destino = conexiones[i].getDestino();

                for (int j = 0; j < n; j++) {
                    if (neuronas[j].getId().equals(destino) && !visitado[j]) {
                        visitado[j] = true;
                        cola[fin++] = destino;
                        break;
                    }
                }
            }
        }

        // Las neuronas no visitadas son zonas aisladas
        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                aisladas[numAisladas++] = neuronas[i].getId();
            }
        }
    }

    /**
     * Ejecuta DFS desde una neurona origen y clasifica las demas como
     * alcanzables o aisladas. Usa una pila implementada con arreglo.
     * @param grafo grafo neuronal sobre el que se realiza el analisis
     * @param idOrigen ID de la neurona desde donde inicia el recorrido
     */
    public void analizarDesdeDFS(GrafoNeuronal grafo, String idOrigen) {
        int n              = grafo.getNumNeuronas();
        Neurona[] neuronas = grafo.getNeuronas();

        boolean[] visitado = new boolean[n];
        String[]  pila     = new String[n];
        int tope = 0;

        alcanzables    = new String[n];
        numAlcanzables = 0;
        aisladas       = new String[n];
        numAisladas    = 0;

        int indiceOrigen = -1;
        for (int i = 0; i < n; i++) {
            if (neuronas[i].getId().equals(idOrigen)) {
                indiceOrigen = i;
                break;
            }
        }

        if (indiceOrigen == -1) return;

        // DFS: apilar origen y marcar como visitado
        pila[tope++] = idOrigen;
        visitado[indiceOrigen] = true;

        while (tope > 0) {
            String  actual  = pila[--tope];
            Neurona nActual = grafo.obtenerNeurona(actual);
            alcanzables[numAlcanzables++] = actual;

            Sinapsis[] conexiones = nActual.getConexiones();

            for (int i = 0; i < nActual.getNumConexiones(); i++) {
                String destino = conexiones[i].getDestino();

                for (int j = 0; j < n; j++) {
                    if (neuronas[j].getId().equals(destino) && !visitado[j]) {
                        visitado[j]  = true;
                        pila[tope++] = destino;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                aisladas[numAisladas++] = neuronas[i].getId();
            }
        }
    }

    /**
     * Obtiene el arreglo de IDs de neuronas alcanzables desde el origen.
     * @return arreglo de IDs alcanzables
     */
    public String[] getAlcanzables() {
        return alcanzables;
    }

    /**
     * Obtiene la cantidad de neuronas alcanzables.
     * @return numero de neuronas alcanzables
     */
    public int getNumAlcanzables() {
        return numAlcanzables;
    }

    /**
     * Obtiene el arreglo de IDs de neuronas aisladas (no alcanzables).
     * @return arreglo de IDs aisladas
     */
    public String[] getAisladas() {
        return aisladas;
    }

    /**
     * Obtiene la cantidad de neuronas aisladas.
     * @return numero de neuronas aisladas
     */
    public int getNumAisladas() {
        return numAisladas;
    }
}
