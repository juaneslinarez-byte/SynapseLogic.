package synapselogic.vista;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * Panel derecho de la ventana principal que muestra los resultados
 * de los analisis de conectividad, rutas optimas y consultas de neurotransmisores.
 *
 * @author Juan Linarez
 */
public class PanelResultados extends JPanel {

    private JTextArea areaTexto;

    /**
     * Constructor de la clase PanelResultados.
     * Inicializa el area de texto con scroll y borde titulado.
     */
    public PanelResultados() {
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Muestra un texto en el panel de resultados.
     * Reemplaza cualquier contenido previo.
     * @param texto texto a mostrar
     */
    public void mostrar(String texto) {
        areaTexto.setText(texto);
    }

    /**
     * Limpia el contenido del panel de resultados.
     */
    public void limpiar() {
        areaTexto.setText("");
    }
}
