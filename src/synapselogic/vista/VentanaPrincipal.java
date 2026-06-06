package synapselogic.vista;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.JScrollPane;
import synapselogic.control.AnalizadorConectividad;
import synapselogic.control.AnalizadorFlujo;
import synapselogic.control.CargadorCSV;
import synapselogic.modelo.GrafoNeuronal;
import synapselogic.modelo.Neurona;
import synapselogic.modelo.Neurotransmisor;
import synapselogic.modelo.Sinapsis;

/**
 * Ventana principal de la aplicacion SynapseLogic.
 * Contiene la barra de herramientas, el panel de analisis,
 * el visualizador del grafo y el area de resultados.
 *
 * @author Juan Linarez
 */
public class VentanaPrincipal extends JFrame {

    private GrafoNeuronal         grafo;
    private CargadorCSV           cargador;
    private AnalizadorConectividad analizadorConectividad;
    private AnalizadorFlujo       analizadorFlujo;
    private VisualizadorGrafo     visualizador;

    private JTextField    campoNeuronFuente;
    private JTextField    campoOrigen;
    private JTextField    campoDestino;
    private JTextField    campoBusquedaNT;
    private JTextField    campoAgregarId;
    private JTextField    campoEliminarId;
    private PanelResultados panelResultados;
    private JLabel        etiquetaEstado;

    /**
     * Constructor de la clase VentanaPrincipal.
     * Inicializa las estructuras de datos y construye la interfaz grafica.
     */
    public VentanaPrincipal() {
        this.grafo                 = new GrafoNeuronal();
        this.cargador              = new CargadorCSV();
        this.analizadorConectividad = new AnalizadorConectividad();
        this.analizadorFlujo       = new AnalizadorFlujo();

        inicializarVentana();
        construirInterfaz();
    }

    /**
     * Configura las propiedades basicas de la ventana.
     */
    private void inicializarVentana() {
        setTitle("SynapseLogic - Analisis de Conectividad Neuronal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Ensambla todos los paneles de la interfaz.
     */
    private void construirInterfaz() {
        add(crearBarraHerramientas(), BorderLayout.NORTH);
        add(crearPanelAnalisis(),     BorderLayout.WEST);

        visualizador = new VisualizadorGrafo();
        add(visualizador, BorderLayout.CENTER);

        panelResultados = new PanelResultados();
        panelResultados.setPreferredSize(new Dimension(250, 0));
        add(panelResultados, BorderLayout.EAST);
        add(crearBarraEstado(),     BorderLayout.SOUTH);
    }

    /**
     * Crea la barra de herramientas superior con los botones principales.
     * @return panel de la barra de herramientas
     */
    private JPanel crearBarraHerramientas() {
        JPanel panel    = new JPanel(new BorderLayout());
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel derecha   = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCargarRed        = new JButton("Cargar red CSV");
        JButton btnCargarDiccionario = new JButton("Cargar diccionario");
        JButton btnSimularFatiga    = new JButton("Simular Fatiga");

        izquierda.add(btnCargarRed);
        izquierda.add(btnCargarDiccionario);
        derecha.add(btnSimularFatiga);

        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);

        btnCargarRed.addActionListener(e        -> accionCargarRed());
        btnCargarDiccionario.addActionListener(e -> accionCargarDiccionario());
        btnSimularFatiga.addActionListener(e    -> accionSimularFatiga());

        return panel;
    }

    /**
     * Crea el panel izquierdo con los controles de analisis.
     * @return panel de analisis
     */
    private JPanel crearPanelAnalisis() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Analisis"));
        panel.setPreferredSize(new Dimension(200, 0));

        panel.add(new JLabel("Neurona fuente:"));
        campoNeuronFuente = new JTextField();
        campoNeuronFuente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(campoNeuronFuente);
        panel.add(Box.createVerticalStrut(5));

        JButton btnDetectarAisladas = new JButton("Detectar zonas aisladas");
        btnDetectarAisladas.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnDetectarAisladas);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Ruta optima (Dijkstra):"));
        panel.add(Box.createVerticalStrut(3));
        panel.add(new JLabel("Origen:"));
        campoOrigen = new JTextField();
        campoOrigen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(campoOrigen);

        panel.add(new JLabel("Destino:"));
        campoDestino = new JTextField();
        campoDestino.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(campoDestino);
        panel.add(Box.createVerticalStrut(5));

        JButton btnCalcularRuta = new JButton("Calcular ruta");
        btnCalcularRuta.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnCalcularRuta);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Neurotransmisor:"));
        campoBusquedaNT = new JTextField();
        campoBusquedaNT.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(campoBusquedaNT);
        panel.add(Box.createVerticalStrut(5));

        JButton btnBuscarNT = new JButton("Buscar");
        btnBuscarNT.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnBuscarNT);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Agregar neurona:"));
        campoAgregarId = new JTextField();
        campoAgregarId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        campoAgregarId.setToolTipText("ID de la nueva neurona");
        panel.add(campoAgregarId);
        panel.add(Box.createVerticalStrut(3));
        JButton btnAgregar = new JButton("Agregar neurona");
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnAgregar);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Eliminar neurona:"));
        campoEliminarId = new JTextField();
        campoEliminarId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        campoEliminarId.setToolTipText("ID de la neurona a eliminar");
        panel.add(campoEliminarId);
        panel.add(Box.createVerticalStrut(3));
        JButton btnEliminar = new JButton("Eliminar neurona");
        btnEliminar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnEliminar);

        btnDetectarAisladas.addActionListener(e -> accionDetectarAisladas());
        btnCalcularRuta.addActionListener(e     -> accionCalcularRuta());
        btnBuscarNT.addActionListener(e         -> accionBuscarNT());
        btnAgregar.addActionListener(e          -> accionAgregarNeurona());
        btnEliminar.addActionListener(e         -> accionEliminarNeurona());

        return panel;
    }

    /**
     * Crea la barra de estado inferior.
     * @return panel de la barra de estado
     */
    private JPanel crearBarraEstado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        etiquetaEstado = new JLabel("Sin red cargada");
        panel.add(etiquetaEstado);
        return panel;
    }

    /**
     * Abre un selector de archivo y carga una red neuronal desde CSV.
     */
    private void accionCargarRed() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo CSV de red neuronal");
        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        if (grafo.getNumNeuronas() > 0) {
            int opcion = JOptionPane.showConfirmDialog(this,
                "Ya existe una red neuronal cargada.\n" +
                "Si continuas, los datos actuales se perderan.\n" +
                "¿Deseas continuar?",
                "Red ya cargada en memoria",
                JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) return;
            grafo.vaciar();
            visualizador.limpiar();
        }

        File archivo = selector.getSelectedFile();
        if (cargador.cargarRed(archivo.getAbsolutePath(), grafo)) {
            actualizarVisualizador();
            actualizarBarraEstado(archivo.getName());
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un selector de archivo y carga el diccionario de neurotransmisores.
     */
    private void accionCargarDiccionario() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo CSV de diccionario");
        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File archivo = selector.getSelectedFile();
        if (cargador.cargarDiccionario(archivo.getAbsolutePath(), grafo)) {
            panelResultados.mostrar("Diccionario cargado: " +
                grafo.getTablaHash().getCantidad() + " neurotransmisores.");
            actualizarBarraEstado("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar el diccionario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Simula fatiga cognitiva multiplicando todos los factorK por 1.2.
     */
    private void accionSimularFatiga() {
        if (grafo.getNumNeuronas() == 0) {
            JOptionPane.showMessageDialog(this, "No hay red cargada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        grafo.simularFatiga();
        panelResultados.mostrar("Fatiga cognitiva simulada.\nTodos los factorK multiplicados por 1.2.");
    }

    /**
     * Ejecuta BFS desde la neurona fuente y muestra alcanzables y aisladas.
     */
    private void accionDetectarAisladas() {
        String origen = campoNeuronFuente.getText().trim();
        if (origen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una neurona fuente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        analizadorConectividad.analizarDesde(grafo, origen);

        StringBuilder sb = new StringBuilder();
        sb.append("BFS desde ").append(origen).append("\n\n");
        sb.append("Alcanzables (").append(analizadorConectividad.getNumAlcanzables()).append("):\n");
        for (int i = 0; i < analizadorConectividad.getNumAlcanzables(); i++) {
            sb.append("  ").append(analizadorConectividad.getAlcanzables()[i]).append("\n");
        }
        sb.append("\nAisladas (").append(analizadorConectividad.getNumAisladas()).append("):\n");
        for (int i = 0; i < analizadorConectividad.getNumAisladas(); i++) {
            sb.append("  ").append(analizadorConectividad.getAisladas()[i]).append("\n");
            visualizador.marcarAislada(analizadorConectividad.getAisladas()[i]);
        }
        panelResultados.mostrar(sb.toString());
    }

    /**
     * Ejecuta Dijkstra entre origen y destino y muestra la ruta optima.
     */
    private void accionCalcularRuta() {
        String origen  = campoOrigen.getText().trim();
        String destino = campoDestino.getText().trim();
        if (origen.isEmpty() || destino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa origen y destino.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        analizadorFlujo.calcularRuta(grafo, origen, destino);

        StringBuilder sb = new StringBuilder();
        sb.append("Ruta optima: ").append(origen).append(" → ").append(destino).append("\n\n");
        if (analizadorFlujo.getNumRuta() == 0) {
            sb.append("No existe ruta entre las neuronas indicadas.");
        } else {
            for (int i = 0; i < analizadorFlujo.getNumRuta(); i++) {
                sb.append(analizadorFlujo.getRuta()[i]);
                if (i < analizadorFlujo.getNumRuta() - 1) sb.append(" → ");
            }
            sb.append("\n\nCosto total: ").append(String.format("%.4f", analizadorFlujo.getCostoTotal()));
        }
        panelResultados.mostrar(sb.toString());
    }

    /**
     * Busca un neurotransmisor en la tabla hash y muestra su informacion.
     */
    private void accionBuscarNT() {
        String id = campoBusquedaNT.getText().trim();
        if (id.isEmpty()) return;

        Neurotransmisor nt = grafo.getTablaHash().obtener(id);
        if (nt == null) {
            panelResultados.mostrar("Neurotransmisor \"" + id + "\" no encontrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(nt.getId()).append("\n\n");
        sb.append("Nombre:      ").append(nt.getNombre()).append("\n");
        sb.append("Efecto:      ").append(nt.getEfecto()).append("\n");
        sb.append("Velocidad:   ").append(nt.getVelocidad()).append("\n");
        sb.append("Descripcion: ").append(nt.getDescripcion());
        panelResultados.mostrar(sb.toString());
    }

    /**
     * Agrega una nueva neurona al grafo y al visualizador.
     */
    private void accionAgregarNeurona() {
        String id = campoAgregarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID para la neurona.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (grafo.obtenerNeurona(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una neurona con ese ID.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        grafo.agregarNeurona(new synapselogic.modelo.Neurona(id, id));
        visualizador.agregarNeurona(id);
        panelResultados.mostrar("Neurona \"" + id + "\" agregada.");
        campoAgregarId.setText("");
        actualizarBarraEstado("");
    }

    /**
     * Elimina una neurona del grafo y del visualizador.
     */
    private void accionEliminarNeurona() {
        String id = campoEliminarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID de la neurona a eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!grafo.eliminarNeurona(id)) {
            JOptionPane.showMessageDialog(this, "No existe una neurona con ese ID.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        visualizador.eliminarNeurona(id);
        panelResultados.mostrar("Neurona \"" + id + "\" eliminada.");
        campoEliminarId.setText("");
        actualizarBarraEstado("");
    }

    /**
     * Recorre el grafo neuronal y actualiza el visualizador con sus datos.
     * Primero agrega todos los nodos y luego todas las aristas.
     */
    private void actualizarVisualizador() {
        Neurona[] neuronas = grafo.getNeuronas();

        for (int i = 0; i < grafo.getNumNeuronas(); i++) {
            visualizador.agregarNeurona(neuronas[i].getId());
        }

        for (int i = 0; i < grafo.getNumNeuronas(); i++) {
            Sinapsis[] conexiones = neuronas[i].getConexiones();
            for (int j = 0; j < neuronas[i].getNumConexiones(); j++) {
                visualizador.agregarSinapsis(conexiones[j].getOrigen(), conexiones[j].getDestino());
            }
        }
    }

    /**
     * Actualiza el texto de la barra de estado con los datos de la red cargada.
     * @param nombreArchivo nombre del archivo CSV cargado
     */
    private void actualizarBarraEstado(String nombreArchivo) {
        String texto = "";
        if (!nombreArchivo.isEmpty()) {
            texto = "Red cargada: " + nombreArchivo + " - " + grafo.getNumNeuronas() + " neuronas";
        }
        texto += " | Diccionario: " + grafo.getTablaHash().getCantidad() + " neurotransmisores";
        etiquetaEstado.setText(texto);
    }
}
