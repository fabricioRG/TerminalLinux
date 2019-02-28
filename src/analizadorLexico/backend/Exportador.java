package analizadorLexico.backend;

import Node.Nodo;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fabricio
 */
public class Exportador {

    public static String FILE_NAME = "Nodos.txt";
    public static String INICIO_TEXT = "(";
    public static String FIN_TEXT = ")";
    public static String SEPARADOR_TEXT = ",";
    public static String TRUE = "true";
    public static String  FALSE = "false";
    public static final String formatoFecha = "yyyy-mm-dd hh:mm:ss";
    private SimpleDateFormat fechaFormat = null;

    public Exportador() {
        fechaFormat = new SimpleDateFormat(formatoFecha);
    }

    void quicksort(List<Nodo> lista, int primero, int ultimo) {
        List<Nodo> listaNodos = new LinkedList<>();
        listaNodos.addAll(lista);
        int central, i, j;
        int pivote;
        central = (primero + ultimo) / 2;
        pivote = listaNodos.get(central).getPosicion();
        i = primero;
        j = ultimo;
        do {
            while (listaNodos.get(i).getPosicion() < pivote) {
                i++;
            }
            while (listaNodos.get(j).getPosicion() > pivote) {
                j--;
            }
            if (i <= j) {
                Nodo temp;
                temp = listaNodos.get(i);
                listaNodos.set(i, listaNodos.get(j));
                /*intercambia A[i] con A[j] */
                listaNodos.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);
        if (primero < j) {
            quicksort(listaNodos, primero, j);
            /*mismo proceso con sublista izquierda*/
        }
        if (i < ultimo) {
            quicksort(listaNodos, i, ultimo);
            /*mismo proceso con sublista derecha*/
        }
    }

    void printList(List lista) {
        List<Nodo> nodos = new LinkedList<>();
        nodos.addAll(lista);
        quicksort(nodos, 0, nodos.size() - 1);
        File direccion = new File(FILE_NAME);
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(direccion);
            pw = new PrintWriter(fichero);
            nodos.remove(0);
            for (Nodo nodo : nodos) {
                pw.println(INICIO_TEXT + nodo.getPadre().getId() + SEPARADOR_TEXT + nodo.getNombre() + SEPARADOR_TEXT + 
                        nodo.getPosicion() + SEPARADOR_TEXT + nodo.getId() + SEPARADOR_TEXT + nodo.getTamano() + 
                        SEPARADOR_TEXT + nodo.getNumeroEnlaces() + SEPARADOR_TEXT + fechaFormat.format(nodo.getFechaCreacion()) + 
                        SEPARADOR_TEXT + Boolean.toString(nodo.isDirectory()) + SEPARADOR_TEXT + Boolean.toString(nodo.isExecute()) + 
                        SEPARADOR_TEXT + Boolean.toString(nodo.isWrite()) + SEPARADOR_TEXT + Boolean.toString(nodo.isVisible())+ FIN_TEXT) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
