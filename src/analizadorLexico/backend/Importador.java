/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorLexico.backend;

import Node.ManejadorNodo;
import Node.Nodo;
import static analizadorLexico.backend.Exportador.FILE_NAME;
import static analizadorLexico.backend.Exportador.formatoFecha;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author fabricio
 */
public class Importador {

    public static String FILE_NAME = "Nodos.txt";
    public static String INICIO_TEXT = "(";
    public static String FIN_TEXT = ")";
    public static String SEPARADOR_TEXT = ",";
    public static String TRUE = "true";
    public static String FALSE = "false";
    public static final String formatoFecha = "yyyy-mm-dd hh:mm:ss";
    private SimpleDateFormat fechaFormat = null;
    private ManejadorNodo mn = null;
    private List<Nodo> listaNodos = null;
    private Nodo nodoRaiz = null;
    private int rootCount = 0;

    public Importador(ManejadorNodo mn) {
        this.mn = mn;
        this.listaNodos = new LinkedList<>();
        fechaFormat = new SimpleDateFormat(formatoFecha);
        this.nodoRaiz = this.mn.getNodeRoot();
    }

    private Nodo getPadre(int idPadre) {
        if (idPadre == 0) {
            return this.nodoRaiz;
        } else {
            for (Nodo nodo : listaNodos) {
                if (nodo.getId() == idPadre) {
                    return nodo;
                }
            }
        }
        return null;
    }

    private void addNodo(String linea) {
        String[] nodos = linea.split(SEPARADOR_TEXT);
        int idPadre = Integer.parseInt(nodos[0]);
        String nombreNodo = nodos[1];
        int posicion = Integer.parseInt(nodos[2]);
        int id = Integer.parseInt(nodos[3]);
        int tamano = Integer.parseInt(nodos[4]);
        int numeroEnlaces = Integer.parseInt(nodos[5]);
        Date fechaCreacion = null;
        try {
            fechaCreacion = fechaFormat.parse(nodos[6]);
        } catch (Exception e) {
        }
        boolean isDirectory = Boolean.valueOf(nodos[7]);
        boolean isExecute = Boolean.valueOf(nodos[8]);
        boolean isWrite = Boolean.valueOf(nodos[9]);
        boolean isVisible = Boolean.valueOf(nodos[10]);
        if(idPadre == 0){
            this.rootCount++;
        }
        Nodo padre = getPadre(idPadre);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(nombreNodo);
        padre.getDmtn().add(node);
        Nodo nodo = new Nodo(padre, nombreNodo, posicion, id, tamano, numeroEnlaces, 
                fechaCreacion, isDirectory, isExecute, isWrite, isVisible, node);
        listaNodos.add(nodo);
    }

    public void readFile() {
        File direccion = new File(FILE_NAME);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(direccion);
            br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {
                addNodo(linea.substring(linea.indexOf(INICIO_TEXT) + 1, linea.indexOf(FIN_TEXT)));
            }
            this.mn.getNodeRoot().setNumeroEnlaces(this.rootCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public List<Nodo> getListaNodos() {
        return listaNodos;
    }

    public void setListaNodos(List<Nodo> listaNodos) {
        this.listaNodos = listaNodos;
    }

}
