package analizadorLexico.backend;

import Node.*;
import java.util.LinkedList;
import java.util.List;

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
    private String pathActual = "/";

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

    public Nodo getNodeRoot() {
        return mn.getNodeRoot();
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
            throw new Exception("No se puede efectuar 'cd' sobre Nodo raiz.");
        } else {
            String nuevoPath = pathActual.substring(0, pathActual.lastIndexOf("/"));
            if (nuevoPath.equals("")) {
                nuevoPath = "/";
            }
            pathActual = nuevoPath;
        }
    }

    public void functionById(String nombreNodo, int option) { //Archivo = 1, Carpeta = 0
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        this.mn.addNodo(nodo, nombreNodo, option);
    }

    public void ls(int option) {
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        if (this.mn.getNodosFromNodoId(nodo) != null) {
            if (option == 0) { //Normal form
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 0) + "\n");
            } else if (option == 1) { // Long form
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 1));
            } else if (option == 2) { //Hidden form
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 2) + "\n");
            } else if (option == 3) { //Long and Hidden form
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 3));
            }
        } else {
            if (option == 2) {
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 2) + "\n");
            } else if (option == 3) {
                this.mat.setText(2, mn.showNodosFromList(nodo, this.mn.getNodosFromNodoId(nodo), 3));
            }
        }
    }

    public void rmById(String nombreNodo, int option) throws Exception {//Archivo = 1, Directorio = 0;
        String path1 = "";
        if (mn.getNodoByPath(pathActual, OPTION_DIRECTORIO).getPadre() == null) {
            path1 = pathActual + nombreNodo;
        } else {
            path1 = pathActual + "/" + nombreNodo;
        }
        System.out.println(path1);
        if (option == 1) {
            Nodo nodo = this.mn.getNodoByPath(path1, OPTION_ARCHIVO);
            if (nodo != null) {
                this.mn.removeNodo(nodo);
            } else {
                throw new Exception("No se pudo efectuar 'rm' sobre '" + nombreNodo + "': No existe el archivo o es directorio");
            }
        } else if (option == 0 || option == 2) {
            Nodo nodo = this.mn.getNodoByPath(path1, OPTION_DIRECTORIO);
            if (nodo != null) {
                if (option == 0) {
                    if (nodo.getNumeroEnlaces() <= 0) {
                        this.mn.removeNodo(nodo);
                    } else {
                        throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nombreNodo + "': Directorio no vacio");
                    }
                } else if (option == 2){
                    this.mn.removeNodo(nodo);
                }
            } else {
                throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nombreNodo + "': No existe el directorio o es archivo");
            }
        }
    }

    public void rmByPath(Nodo nodo, String nodoAnterior) throws Exception {
        if (errors <= 1) {
            Nodo node = this.mn.getArchivoFromPadreNodo(nodo, nodoAnterior);
            if (node != null) {
                mn.removeNodo(node);
            } else {
                throw new Exception("No se pudo efectuar 'rm' sobre '" + nodoAnterior + "': No existe el archivo.");
            }
        } else {
            throw new Exception("No se pudo efectuar 'rm' sobre '" + nodoAnterior + "': No existen carpetas.");
        }
    }

    public void rmdirByPath(Nodo nodo, String nodoAnterior, int option) throws Exception {//rmdir = 0, rm -r = 1 
        if (errors < 1) {
            if (nodo.isDirectory()) {
                if (option == 0) {
                    if (nodo.getNumeroEnlaces() <= 0) {
                        this.mn.removeNodo(nodo);
                    } else {
                        throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodoAnterior + "': Directorio no vacio");
                    }
                } else if (option == 1) {
                    this.mn.removeNodo(nodo);
                }
            } else {
                throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodo.getNombre() + "': no es directorio");
            }
        } else {
            throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodoAnterior + "': No existe el directorio.");
        }
    }

    public void rmdirById() {

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
