package Node;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fabricio
 */
public class ManejadorNodo {

    public static int OPTION_ARCHIVO = 1;
    public static int OPTION_DIRECTORIO = 0;
    public static final int EXTREMO_INFERIOR = 100;
    public static final int EXTREMO_SUPERIOR = 5000;
    public static final String formatoFecha = "aaaa.MM.dd HH: mm: ss ";
    private List<Nodo> listaNodos = null;
    private SimpleDateFormat fechaFormat = null;
    private int contadorId = 0;

    public ManejadorNodo() {
        this.listaNodos = new LinkedList<>();
        fechaFormat = new SimpleDateFormat("aaaa.MM.dd HH: mm: ss ");
        Date date = new Date();
        Nodo nodo = new Nodo(null, "home", 0, contadorId, 1, true, date, null);
        this.listaNodos.add(nodo);
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
        Nodo nodo = new Nodo(padre, nombreNodo, padre.getPosicion() + 1, ++contadorId, tamano, directory, date, null);
        this.listaNodos.add(nodo);
    }

    public void removeNodo(Nodo nodo) {
        for (int i = 0; i < listaNodos.size(); i++) {
            if (listaNodos.get(i).getId() == nodo.getId()) {
                listaNodos.remove(i);
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
                            } else if (option == OPTION_ARCHIVO){
                                if (!nodo.isDirectory()){
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
        for (int i = 1; i < nodos.length; i++) {
            nodoFinal = getNodo(nodos[i], nodoAnterior, i - 1, option);
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

    public String showNodosFromList(List lista) {
        List<Nodo> list = new LinkedList<>();
        String nodos = "";
        if (lista == null) {
            return nodos;
        } else {
            list.addAll(lista);
            for (Nodo nodo : list) {
                nodos += nodo.getNombre() + "  ";
            }
            return nodos;
        }
    }

    private Nodo getNodeRoot() {
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
