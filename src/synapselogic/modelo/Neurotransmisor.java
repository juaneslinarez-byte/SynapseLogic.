package synapselogic.modelo;

/**
 * Representa un neurotransmisor del sistema nervioso.
 * Almacena su identificador, nombre, efecto, velocidad y descripcion.
 *
 * @author Juan Linarez
 * @author Leonardo Zambrano
 */
public class Neurotransmisor {

    private String id;
    private String nombre;
    private String efecto;
    private double velocidad;
    private String descripcion;

    /**
     * Constructor de la clase Neurotransmisor.
     * @param id codigo unico del neurotransmisor (ej: "GLU")
     * @param nombre nombre completo (ej: "Glutamato")
     * @param efecto tipo de efecto: Excitatorio, Inhibitorio o Modulador
     * @param velocidad factor de velocidad de transmision sinaptica
     * @param descripcion descripcion breve del rol biologico
     */
    public Neurotransmisor(String id, String nombre, String efecto,
                           double velocidad, String descripcion) {
        this.id          = id;
        this.nombre      = nombre;
        this.efecto      = efecto;
        this.velocidad   = velocidad;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID del neurotransmisor.
     * @return id del neurotransmisor
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del neurotransmisor.
     * @return nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el efecto del neurotransmisor.
     * @return efecto: Excitatorio, Inhibitorio o Modulador
     */
    public String getEfecto() {
        return efecto;
    }

    /**
     * Obtiene la velocidad de transmision sinaptica.
     * Se usa en la formula: peso = distancia / (velocidad * k)
     * @return velocidad de transmision
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * Obtiene la descripcion del neurotransmisor.
     * @return descripcion breve
     */
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | " + efecto + " | Velocidad: " + velocidad;
    }
}
