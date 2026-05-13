package synapselogic.modelo;

/**
 * Representa una sinapsis dirigida entre dos neuronas.
 * Almacena la distancia, el neurotransmisor asociado y el factor k de transmision.
 * El peso de la sinapsis se calcula como: distancia / (velocidad * factorK).
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class Sinapsis {

    private String origen;
    private String destino;
    private double distancia;
    private String idNeurotransmisor;
    private double factorK;

    /**
     * Constructor de la clase Sinapsis.
     * @param origen ID de la neurona de origen
     * @param destino ID de la neurona de destino
     * @param distancia distancia entre las dos neuronas
     * @param idNeurotransmisor ID del neurotransmisor que actua en esta sinapsis
     * @param factorK factor de transmision sinaptica (por defecto 1.0)
     */
    public Sinapsis(String origen, String destino, double distancia,
                    String idNeurotransmisor, double factorK) {
        this.origen           = origen;
        this.destino          = destino;
        this.distancia        = distancia;
        this.idNeurotransmisor = idNeurotransmisor;
        this.factorK          = factorK;
    }

    /**
     * Obtiene el ID de la neurona de origen.
     * @return ID de la neurona de origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Obtiene el ID de la neurona de destino.
     * @return ID de la neurona de destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Obtiene la distancia de la sinapsis.
     * @return distancia entre las dos neuronas
     */
    public double getDistancia() {
        return distancia;
    }

    /**
     * Obtiene el ID del neurotransmisor asociado a esta sinapsis.
     * @return ID del neurotransmisor
     */
    public String getIdNeurotransmisor() {
        return idNeurotransmisor;
    }

    /**
     * Obtiene el factor k de transmision sinaptica.
     * @return factor k actual
     */
    public double getFactorK() {
        return factorK;
    }

    /**
     * Establece el factor k de transmision sinaptica.
     * Se usa al simular fatiga cognitiva (factorK *= 1.2).
     * @param factorK nuevo valor del factor k
     */
    public void setFactorK(double factorK) {
        this.factorK = factorK;
    }

    @Override
    public String toString() {
        return origen + " -> " + destino + " | Distancia: " + distancia
                + " | NT: " + idNeurotransmisor + " | k: " + factorK;
    }
}
