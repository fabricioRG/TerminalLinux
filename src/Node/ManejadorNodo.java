package Node;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fabricio
 */
public class ManejadorNodo {

    private List<Nodo> listaNodos = null;
    private int contadorId = 0;

    public ManejadorNodo() {
        this.listaNodos = new LinkedList<>();
        this.listaNodos.add(new Nodo(null, "Root", 0, contadorId, true, true, true, true, null));
        this.listaNodos.add(new Nodo(null, "algo1", 1, ++contadorId, true, true, true, true, null));
        this.listaNodos.add(new Nodo(null, "algo2", 1, ++contadorId, true, true, true, true, null));
        this.listaNodos.add(new Nodo(null, "algo3", 1, ++contadorId, true, true, true, true, null));
        this.listaNodos.add(new Nodo(new Nodo(null, "algo1", 1, 5, true, true, true, true, null), "algo3", 2, ++contadorId, true, true, true, true, null));
    }

    public void addNodo(String padre, String nombre, int position) {
        if (padre.isEmpty()) {
            Nodo nodo = new Nodo(getNodeRoot(), nombre, getNodeRoot().getPosicion() + 1, ++contadorId, true, true, true, true, null);
            listaNodos.add(nodo);
        } else {
            
        }
    }

    public Nodo getNodo(String nodoActual, String nodoAnterior, int position) {
        if (nodoAnterior == null) {
            for (Nodo nodo : listaNodos) {
                if (nodo.getPosicion() == position) {
                    if (nodo.getNombre().equals(nodoActual)) {
                        return nodo;
                    }
                }
            }
        } else {
            for (Nodo nodo : listaNodos) {
                if (nodo.getPosicion() == position){
                    if (nodo.getNombre().equals(nodoActual)){
                        if(nodo.getPadre().getNombre().equals(nodoAnterior)){
                            return nodo;
                        }
                    }
                }
            }
        }
        return null;
    }

    public int getIdParent(String parent, int position) {
        for (Nodo nodo : listaNodos) {
            if (nodo.getPosicion() == position) {

            }
        }
        return 0;
    }

    public int getIdNodo(String padre, String hijo) {
        for (int i = 0; i < listaNodos.size(); i++) {
            if (listaNodos.get(i).getNombre().equals(hijo)) {
                if (listaNodos.get(i).getPadre().getNombre().equals(padre)) {
                    return listaNodos.get(i).getId();
                }
            }
        }
        return 0;
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
