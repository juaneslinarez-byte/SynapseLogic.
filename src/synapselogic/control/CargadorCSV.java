package synapselogic.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import synapselogic.modelo.GrafoNeuronal;
import synapselogic.modelo.Neurona;
import synapselogic.modelo.Neurotransmisor;

/**
 * Carga datos de red neuronal y diccionario de neurotransmisores desde archivos CSV.
 * Interactua con GrafoNeuronal y TablaHash para poblar las estructuras de datos.
 *
 * Formato del CSV de red neuronal:
 *   NEURONA,id,nombre
 *   SINAPSIS,origen,destino,distancia,idNeurotransmisor,factorK
 *
 * Formato del CSV de diccionario:
 *   id,nombre,efecto,velocidad,descripcion
 *
 * @author Juan Linarez
 */
public class CargadorCSV {

    /**
     * Carga una red neuronal desde un archivo CSV y la inserta en el grafo.
     * Las lineas que no correspondan al formato esperado se omiten.
     * @param ruta ruta absoluta del archivo CSV de red neuronal
     * @param grafo grafo neuronal donde se insertaran los datos
     * @return true si la carga fue exitosa, false si ocurrio un error
     */
    public boolean cargarRed(String ruta, GrafoNeuronal grafo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",", 6);

                if (partes[0].equals("NEURONA") && partes.length >= 3) {
                    Neurona n = new Neurona(partes[1].trim(), partes[2].trim());
                    grafo.agregarNeurona(n);

                } else if (partes[0].equals("SINAPSIS") && partes.length >= 6) {
                    String origen  = partes[1].trim();
                    String destino = partes[2].trim();
                    double distancia = Double.parseDouble(partes[3].trim());
                    String idNT    = partes[4].trim();
                    double factorK = Double.parseDouble(partes[5].trim());
                    grafo.agregarSinapsis(origen, destino, distancia, idNT, factorK);
                }
            }
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Carga un diccionario de neurotransmisores desde un archivo CSV
     * e inserta cada uno en la tabla hash del grafo.
     * Las lineas que no correspondan al formato esperado se omiten.
     * @param ruta ruta absoluta del archivo CSV del diccionario
     * @param grafo grafo neuronal cuya tabla hash se llenara
     * @return true si la carga fue exitosa, false si ocurrio un error
     */
    public boolean cargarDiccionario(String ruta, GrafoNeuronal grafo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",", 5);
                if (partes.length < 5) continue;

                String id          = partes[0].trim();
                String nombre      = partes[1].trim();
                String efecto      = partes[2].trim();
                double velocidad   = Double.parseDouble(partes[3].trim());
                String descripcion = partes[4].trim();

                Neurotransmisor nt = new Neurotransmisor(id, nombre, efecto, velocidad, descripcion);
                grafo.getTablaHash().insertar(nt);
            }
            return true;

        } catch (IOException e) {
            return false;
        }
    }
}
