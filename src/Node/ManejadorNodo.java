package Node;

import analizadorLexico.backend.Importador;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author fabricio
 */
public class ManejadorNodo {

    public static int OPTION_ARCHIVO = 1;
    public static int OPTION_DIRECTORIO = 0;
    public static final int EXTREMO_INFERIOR = 100;
    public static final int EXTREMO_SUPERIOR = 5000;
    public static final String EXECUTE_STRING = "x";
    public static final String WRITE_STRING = "w";
    public static final String READ_STRING = "r";
    public static final String WHITOUT_STRING = "-";
    public static final String DIRECTORY_STRING = "d";
    public static final String SPACE = "   ";
    public static final String USER_STRING = "fabricio";
    public static final String formatoFecha = "yyyy-mm-dd hh:mm:ss ";
    private List<Nodo> listaNodos = null;
    private SimpleDateFormat fechaFormat = null;
    private int contadorId = 0;
    private Importador imp = null;

    public ManejadorNodo(DefaultMutableTreeNode root) {
        this.listaNodos = new LinkedList<>();
        fechaFormat = new SimpleDateFormat(formatoFecha);
        Date date = new Date();
        Nodo nodo = new Nodo(null, "", 0, contadorId, 254, true, date, root);
        this.listaNodos.add(nodo);
        this.imp = new Importador(this);
        this.imp.readFile();
        if(this.imp.getListaNodos() != null){
            this.listaNodos.addAll(this.imp.getListaNodos());
        }
    }

    public void addNodo(Nodo padre, String nombreNodo, int option) { //Archivo = 1, Carpeta = 0
        boolean directory = true;
        if (option == OPTION_ARCHIVO) {
            directory = false;
        } else {
            directory = true;
        }
        int tamano = (int) (Math.random() * EXTREMO_SUPERIOR) + EXTREMO_INFERIOR;
        Date date = new Date();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(nombreNodo);
        padre.getDmtn().add(node);
        Nodo nodo = new Nodo(padre, nombreNodo, padre.getPosicion() + 1, ++contadorId, tamano, directory, date, node);
        padre.setNumeroEnlaces(padre.getNumeroEnlaces() + 1);
        this.listaNodos.add(nodo);
    }

    public void actualizarNodo(Nodo nodo){
        for (Nodo nodos : listaNodos) {
            if(nodos.getId() == nodo.getId()){
                nodos.setExecute(nodo.isExecute());
                nodos.setWrite(nodo.isWrite());
                nodos.setVisible(nodo.isVisible());
            }
        }
    }
    
    public void removeNodo(Nodo nodo) {
        for (int i = 0; i < listaNodos.size(); i++) {
            if (listaNodos.get(i).getId() == nodo.getId()) {
                listaNodos.get(i).getPadre().setNumeroEnlaces(nodo.getPadre().getNumeroEnlaces() - 1);
                listaNodos.remove(i);
                nodo.getPadre().getDmtn().remove(nodo.getDmtn());
                break;
            }
        }
    }

    public Nodo getNodo(String nodoActual, String nodoAnterior, int position, int option) { //Archivo = 1, Directorio = 0
        if (nodoAnterior == null) {
            for (Nodo nodo : listaNodos) {
                if (nodo.getPosicion() == position) {
                    if (nodo.getNombre().equals(nodoActual)) {
                        if (nodo.isDirectory()) {
                            return nodo;
                        }
                    }
                }
            }
        } else {
            for (Nodo nodo : listaNodos) {
                if (nodo.getPosicion() == position) {
                    if (nodo.getNombre().equals(nodoActual)) {
                        if (nodo.getPadre().getNombre().equals(nodoAnterior)) {
                            if (option == OPTION_DIRECTORIO) {
                                if (nodo.isDirectory()) {
                                    return nodo;
                                }
                            } else if (option == OPTION_ARCHIVO) {
                                if (!nodo.isDirectory()) {
                                    return nodo;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Nodo getNodoByPath(String path, int option) {
        Nodo nodoFinal = null;
        String nodoAnterior = null;
        String[] nodos = path.split("/");
        nodoFinal = getNodo("", nodoAnterior, 0, option);
        nodoAnterior = "";
        for (int i = 1; i < nodos.length; i++) {
            nodoFinal = getNodo(nodos[i], nodoAnterior, i, option);
            nodoAnterior = nodos[i];
        }
        return nodoFinal;
    }

    public List getNodosFromNodoId(Nodo nodo) {
        List<Nodo> lista = new LinkedList<>();
        for (Nodo node : listaNodos) {
            if (node.getPadre() != null) {
                if (node.getPadre().getId() == nodo.getId()) {
                    lista.add(node);
                }
            }
        }
        if (lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
    }
    
    public Nodo getArchivoFromPadreNodo(Nodo padre, String nodoActual){
        List<Nodo> nodos = new LinkedList<>();
        if(getNodosFromNodoId(padre) != null){
            nodos.addAll(getNodosFromNodoId(padre));
        }
        Nodo nodoFinal = null;
        for (Nodo node : nodos) {
                    if(!node.isDirectory()){
                        if(node.getNombre().equals(nodoActual)){
                            nodoFinal = node;
                        }
                    }
                }
        return nodoFinal;
    }

    public String showNodosFromList(Nodo nodo, List lista, int option) { //ls = 0, -l = 1, -a = 2, -la = 3;
        String nodos = "";
        if (option == 2) {
            if (nodo.getId() == 0) {
                nodos += "." + SPACE;
            } else {
                nodos += "." + SPACE + ".." + SPACE;
            }
        } else if (option == 3){
            nodos += getInformationNode(nodo);
            if(nodo.getPadre() != null){
                nodos += getInformationNode(nodo.getPadre());
            }
        }
        if (lista == null) {
            return nodos;
        } else {
            return nodos + getStringNodes(lista, option);
        }
    }

    public String getStringNodes(List lista, int option) {
        List<Nodo> list = new LinkedList<>();
        String nodos = "";
        list.addAll(lista);
        if (option == 0) {
            for (Nodo nodo : list) {
                if (nodo.isVisible()) {
                    nodos += nodo.getNombre() + SPACE;
                }
            }
        } else if (option == 1) {
            for (Nodo nodo : list) {
                if (nodo.isVisible()) {
                    nodos += getInformationNode(nodo);
                }
            }
        } else if (option == 2) {
            for (Nodo nodo : list) {
                nodos += nodo.getNombre() + SPACE;
            }
        } else if (option == 3){
            for (Nodo nodo : list) {
                nodos += getInformationNode(nodo);
            }
        }
        return nodos;
    }

    public String getPermisos(Nodo nodo) {
        String permisos = "";
        String permisosFinales = "";
        if (nodo.isDirectory()) {
            permisosFinales = DIRECTORY_STRING;
        } else {
            permisosFinales = WHITOUT_STRING;
        }
        if (nodo.isVisible()) {
            permisos += READ_STRING;
        } else {
            permisos += WHITOUT_STRING;
        }
        if (nodo.isWrite()) {
            permisos += WRITE_STRING;
        } else {
            permisos += WHITOUT_STRING;
        }
        if (nodo.isExecute()) {
            permisos += EXECUTE_STRING;
        } else {
            permisos += WHITOUT_STRING;
        }
        permisosFinales += permisos + permisos + permisos;
        return permisosFinales;
    }

    public String getInformationNode(Nodo nodo) {
        String information = nodo.getNumeroEnlaces() + SPACE + USER_STRING + SPACE + USER_STRING
                + SPACE + nodo.getTamano() + SPACE + fechaFormat.format(nodo.getFechaCreacion()) + SPACE + nodo.getNombre();
        return getPermisos(nodo) + SPACE + information + "\n";
    }

    public Nodo getNodeRoot() {
        return listaNodos.get(0);
    }

    public Nodo getNodoByPosition(int position) {
        return listaNodos.get(position);
    }

    public List<Nodo> getListaNodos() {
        return listaNodos;
    }

    public void setListaNodos(List<Nodo> listaNodos) {
        this.listaNodos = listaNodos;
    }

}
