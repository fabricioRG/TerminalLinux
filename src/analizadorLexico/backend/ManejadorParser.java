package analizadorLexico.backend;

import Node.*;

/**
 *
 * @author fabricio
 */
public class ManejadorParser {

    public static int OPTION_ARCHIVO = 1;
    public static int OPTION_DIRECTORIO = 0;
    private int errors = 0;
    private ManejadorNodo mn = null;
    private ManejadorAreaTexto mat = null;
    private String pathActual = "/home";

    public ManejadorParser(ManejadorAreaTexto mat) {
        mn = new ManejadorNodo();
        this.mat = mat;
    }

    public Nodo getNode(String nodoActual, String nodoAnterior, Nodo nodo, int position, int option) {
        if (mn.getNodo(nodoActual, nodoAnterior, position, option) == null) {
            errors++;
            return nodo;
        } else {
            return mn.getNodo(nodoActual, nodoAnterior, position, option);
        }
    }

    public void functionByPath(String path, Nodo padre, String nodoAnterior, int option) throws Exception {//Archivo = 1, Carpeta = 0, cd = 2;
        System.out.println(errors);
        if (errors > 1) {
            errors = 0;
            throw new Exception("No se pudo efectuar 'touch' sobre '" + path + "': No existe el archivo o directorio.");
        } else if (errors == 1) {
            if (option == OPTION_DIRECTORIO || option == OPTION_ARCHIVO) {
                mn.addNodo(padre, nodoAnterior, option);
                errors = 0;
            } else if (option == 2) {
                errors = 0;
                throw new Exception("No se pudo efectuar 'cd' sobre '" + path + "': No existe el directorio.");
            }
        } else {
            if (option == 2) {
                pathActual = path;
                errors = 0;
            } else {
                errors = 0;
                throw new Exception("No se ha especificado el archivo a crear");
            }
        }
    }

    public void cdByBack() throws Exception {
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        if (nodo.getPadre() == null) {
            throw new Exception("No se pudo efectuar 'cd' sobre '" + pathActual + "': Nodo raiz.");
        } else {
            String nuevoPath = pathActual.substring(0, pathActual.lastIndexOf("/"));
            pathActual = nuevoPath;
        }
    }

    public void functionById(String nombreNodo, int option) { //Archivo = 1, Carpeta = 0
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        this.mn.addNodo(nodo, nombreNodo, option);
    }

    public void ls() {
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        if (this.mn.getNodosFromNodoId(nodo) != null) {
            this.mat.setText(2, mn.showNodosFromList(this.mn.getNodosFromNodoId(nodo)) + "\n");
        }
    }

    public void rmById(String nombreNodo) throws Exception {
        String path1 = pathActual + "/" + nombreNodo;
        System.out.println(path1);
        Nodo nodo = this.mn.getNodoByPath(path1, OPTION_ARCHIVO);
        if (nodo != null) {
            this.mn.removeNodo(nodo);
        } else {
            throw new Exception("No se pudo efectuar 'rm' sobre '" + nombreNodo + "': No existe el archivo o es directorio");
        }
    }
    
    public void rmByPath(Nodo nodo, String nodoAnterior) throws Exception {
        if (errors <= 1){
            if(!nodo.isDirectory()){
                this.mn.removeNodo(nodo);
            } else {
                throw new Exception("No se pudo efectuar 'rm' sobre '" + nodo.getNombre() + "': Es directorio");
            }
        } else {
            throw new Exception("No se pudo efectuar 'rm' sobre '" + nodoAnterior + "': No existe el archivo.");
        }
    }

    public void pwd() {
        this.mat.setText(2, pathActual + "\n");
    }

    public void exit() {
        System.exit(0);
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public String getPathActual() {
        return pathActual;
    }

    public void setPathActual(String pathActual) {
        this.pathActual = pathActual;
    }

}
