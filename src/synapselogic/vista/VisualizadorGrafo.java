package synapselogic.vista;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

/**
 * Panel de visualizacion del grafo neuronal usando GraphStream.
 * Muestra las neuronas como nodos y las sinapsis como aristas dirigidas.
 * Los nodos aislados se resaltan en naranja.
 *
 * @author Juan Linarez
 */
public class VisualizadorGrafo extends JPanel {

    private Graph grafo;

    /**
     * Constructor de la clase VisualizadorGrafo.
     * Inicializa el grafo de GraphStream y lo agrega al panel.
     */
    public VisualizadorGrafo() {
        System.setProperty("org.graphstream.ui", "swing");

        grafo = new SingleGraph("red-neuronal");
        grafo.setAttribute("ui.stylesheet",
            "node { fill-color: #4a90d9; size: 30px; text-style: bold; text-size: 14; }" +
            "node.aislada { fill-color: orange; }" +
            "edge { arrow-shape: arrow; arrow-size: 10px, 4px; }");

        SwingViewer viewer = new SwingViewer(grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);

        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);
    }

    /**
     * Agrega una neurona al grafo visual si no existe ya.
     * @param id identificador de la neurona a agregar
     */
    public void agregarNeurona(String id) {
        if (grafo.getNode(id) == null) {
            grafo.addNode(id).setAttribute("ui.label", id);
        }
    }

    /**
     * Agrega una sinapsis dirigida entre dos neuronas al grafo visual.
     * @param origen ID de la neurona de origen
     * @param destino ID de la neurona de destino
     */
    public void agregarSinapsis(String origen, String destino) {
        String idArista = origen + "_" + destino;
        if (grafo.getEdge(idArista) == null) {
            grafo.addEdge(idArista, origen, destino, true);
        }
    }

    /**
     * Marca una neurona como aislada cambiando su color a naranja.
     * @param id identificador de la neurona a marcar
     */
    public void marcarAislada(String id) {
        org.graphstream.graph.Node nodo = grafo.getNode(id);
        if (nodo != null) {
            nodo.setAttribute("ui.class", "aislada");
        }
    }

    /**
     * Elimina todos los nodos y aristas del grafo visual.
     */
    public void limpiar() {
        grafo.clear();
        grafo.setAttribute("ui.stylesheet",
            "node { fill-color: #4a90d9; size: 30px; text-style: bold; text-size: 14; }" +
            "node.aislada { fill-color: orange; }" +
            "edge { arrow-shape: arrow; arrow-size: 10px, 4px; }");
    }
}
