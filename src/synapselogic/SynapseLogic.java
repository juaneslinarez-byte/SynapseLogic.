package synapselogic;

import javax.swing.SwingUtilities;
import synapselogic.vista.VentanaPrincipal;

/**
 * Clase principal de la aplicacion SynapseLogic.
 * Lanza la ventana principal en el hilo de eventos de Swing.
 *
 * @author Juan Linarez
 */
public class SynapseLogic {

    /**
     * Punto de entrada de la aplicacion.
     * @param args argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
