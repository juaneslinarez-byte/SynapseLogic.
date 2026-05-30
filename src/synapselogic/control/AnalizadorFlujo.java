package synapselogic.control;

import synapselogic.modelo.GrafoNeuronal;
import synapselogic.modelo.Neurona;
import synapselogic.modelo.Neurotransmisor;
import synapselogic.modelo.Sinapsis;

/**
 * Calcula la ruta optima de transmision sinaptica entre dos neuronas
 * usando el algoritmo de Dijkstra.
 * El peso de cada sinapsis se calcula como: distancia / (velocidad * factorK).
 *
 * @author Juan Linarez
 */
public class AnalizadorFlujo {

    private String[] ruta;
    private int numRuta;
    private double costoTotal;

    /**
     * Constructor de la clase AnalizadorFlujo.
     */
    public AnalizadorFlujo() {
        this.ruta       = new String[0];
        this.numRuta    = 0;
        this.costoTotal = 0;
    }

    /**
     * Ejecuta Dijkstra para encontrar la ruta de menor costo entre dos neuronas.
     * Los resultados quedan almacenados y se acceden con los getters.
     * @param grafo grafo neuronal sobre el que se realiza el calculo
     * @param origen ID de la neurona de origen
     * @param destino ID de la neurona de destino
     */
    public void calcularRuta(GrafoNeuronal grafo, String origen, String destino) {
        int n              = grafo.getNumNeuronas();
        Neurona[] neuronas = grafo.getNeuronas();

        double[]  distancias = new double[n];
        boolean[] visitado   = new boolean[n];
        int[]     previo     = new int[n];

        // Inicializar distancias en infinito y previos en -1
        for (int i = 0; i < n; i++) {
            distancias[i] = Double.MAX_VALUE;
            previo[i]     = -1;
        }

        // Buscar indices de origen y destino
        int indiceOrigen  = -1;
        int indiceDestino = -1;
        for (int i = 0; i < n; i++) {
            if (neuronas[i].getId().equals(origen))  indiceOrigen  = i;
            if (neuronas[i].getId().equals(destino)) indiceDestino = i;
        }

        if (indiceOrigen == -1 || indiceDestino == -1) return;

        distancias[indiceOrigen] = 0;

        for (int iter = 0; iter < n; iter++) {

            // Buscar el nodo no visitado con menor distancia
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && (u == -1 || distancias[i] < distancias[u])) {
                    u = i;
                }
            }

            if (u == -1 || distancias[u] == Double.MAX_VALUE) break;
            visitado[u] = true;

            // Relajar las aristas del nodo actual
            Sinapsis[] conexiones = neuronas[u].getConexiones();

            for (int i = 0; i < neuronas[u].getNumConexiones(); i++) {
                Sinapsis s = conexiones[i];

                double velocidad = 1.0;
                Neurotransmisor nt = grafo.getTablaHash().obtener(s.getIdNeurotransmisor());
                if (nt != null) velocidad = nt.getVelocidad();

                double peso = s.getDistancia() / (velocidad * s.getFactorK());

                // Buscar el indice del nodo destino de esta sinapsis
                for (int j = 0; j < n; j++) {
                    if (neuronas[j].getId().equals(s.getDestino())) {
                        if (distancias[u] + peso < distancias[j]) {
                            distancias[j] = distancias[u] + peso;
                            previo[j]     = u;
                        }
                        break;
                    }
                }
            }
        }

        costoTotal = distancias[indiceDestino];

        // Reconstruir la ruta desde destino hacia origen
        String[] rutaInversa = new String[n];
        int tamRuta = 0;
        int actual  = indiceDestino;

        while (actual != -1) {
            rutaInversa[tamRuta++] = neuronas[actual].getId();
            actual = previo[actual];
        }

        // Invertir para obtener la ruta de origen a destino
        ruta    = new String[tamRuta];
        numRuta = tamRuta;
        for (int i = 0; i < tamRuta; i++) {
            ruta[i] = rutaInversa[tamRuta - 1 - i];
        }
    }

    /**
     * Obtiene el arreglo de IDs de neuronas que forman la ruta optima.
     * @return arreglo de IDs en orden de origen a destino
     */
    public String[] getRuta() {
        return ruta;
    }

    /**
     * Obtiene la cantidad de neuronas en la ruta optima.
     * @return numero de neuronas en la ruta
     */
    public int getNumRuta() {
        return numRuta;
    }

    /**
     * Obtiene el costo total de la ruta optima calculada.
     * @return costo total de transmision
     */
    public double getCostoTotal() {
        return costoTotal;
    }
}
