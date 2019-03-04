package analizadorLexico.backend;

import Node.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
    private Nodo nodoActual = null;
    private DefaultMutableTreeNode root = null;

    public ManejadorParser(ManejadorAreaTexto mat) {
        this.mat = mat;
        root = (DefaultMutableTreeNode) this.mat.getAt().getjTree1().getModel().getRoot();
        mn = new ManejadorNodo(root);
        this.nodoActual = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
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
                refrescarArbol();
                errors = 0;
            } else if (option == 2) {
                errors = 0;
                throw new Exception("No se pudo efectuar 'cd' sobre '" + path + "': No existe el directorio.");
            }
        } else {
            if (option == 2) {
                pathActual = path;
                nodoActual = this.mn.getNodoByPath(pathActual, 0);
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
            nodoActual = this.mn.getNodoByPath(pathActual, 0);
            System.out.println("");
        }
    }

    public void functionById(String nombreNodo, int option) { //Archivo = 1, Carpeta = 0
        Nodo nodo = this.mn.getNodoByPath(pathActual, OPTION_DIRECTORIO);
        this.mn.addNodo(nodo, nombreNodo, option);
        refrescarArbol();
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
                refrescarArbol();
            } else {
                throw new Exception("No se pudo efectuar 'rm' sobre '" + nombreNodo + "': No existe el archivo o es directorio");
            }
        } else if (option == 0 || option == 2) {
            Nodo nodo = this.mn.getNodoByPath(path1, OPTION_DIRECTORIO);
            if (nodo != null) {
                if (option == 0) {
                    if (nodo.getNumeroEnlaces() <= 0) {
                        this.mn.removeNodo(nodo);
                        refrescarArbol();
                    } else {
                        throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nombreNodo + "': Directorio no vacio");
                    }
                } else if (option == 2) {
                    this.mn.removeNodo(nodo);
                    refrescarArbol();
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
                refrescarArbol();
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
                        refrescarArbol();
                    } else {
                        throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodoAnterior + "': Directorio no vacio");
                    }
                } else if (option == 1) {
                    this.mn.removeNodo(nodo);
                    refrescarArbol();
                }
            } else {
                throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodo.getNombre() + "': no es directorio");
            }
        } else {
            throw new Exception("No se pudo efectuar 'rmdir' sobre '" + nodoAnterior + "': No existe el directorio.");
        }
    }

    public void chmodByPathAndId(Nodo nodo, int option, int funtion, boolean recursive, String id) throws Exception {
        List<Nodo> listaNodos = new LinkedList<>();
        if (errors <= 1) {
            errors = 0;
            if (recursive) {
                if (this.mn.getNodosFromNodoId(nodo) != null) {
                    listaNodos.addAll(this.mn.getNodosFromNodoId(nodo));
                    for (Nodo node : listaNodos) {
                        chmodByPathAndId(node, option, funtion, false, id);
                    }
                    setChmod(nodo, option);
                }
            } else {
                if (funtion == 1) {
                    String archivo = id.substring(id.lastIndexOf("/") + 1, id.length());
                    if(this.mn.getArchivoFromPadreNodo(nodo, archivo) != null) {
                        nodo = this.mn.getArchivoFromPadreNodo(nodo, archivo);
                    } else {
                        throw new Exception("El archivo no existe o es directorio");
                    }
                    setChmod(nodo, option);
                } else if (funtion == 2) {
                    String path = "";
                    if (pathActual.equals("/")) {
                        pathActual = "";
                    }
                    path = pathActual + "/" + id;
                    if (this.mn.getNodoByPath(path, 1) != null) {
                        nodo = this.mn.getNodoByPath(path, 1);
                        setChmod(nodo, option);
                    } else {
                        throw new Exception("Archivo no encontrado o es directorio");
                    }
                }
            }
        } else {
            throw new Exception("Archivo no existente");
        }
    }

    public void setChmod(Nodo nodo, int option) {

        boolean execute = nodo.isExecute();
        boolean write = nodo.isWrite();
        boolean read = nodo.isVisible();

        switch (option) {
            case 1:
                read = true;
                System.out.println("1");
                break;
            case 2:
                write = true;
                System.out.println("2");
                break;
            case 3:
                execute = true;
                System.out.println("3");
                break;
            case 4:
                read = true;
                write = true;
                System.out.println("4");
                break;
            case 5:
                execute = true;
                read = true;
                System.out.println("5");
                break;
            case 6:
                execute = true;
                write = true;
                System.out.println("6");
                break;
            case 7:
                read = true;
                write = true;
                execute = true;
                System.out.println("7");
                break;
            case 8:
                read = false;
                System.out.println("8");
                break;
            case 9:
                write = false;
                System.out.println("9");
                break;
            case 10:
                execute = false;
                System.out.println("10");
                break;
            case 11:
                read = false;
                write = false;
                System.out.println("11");
                break;
            case 12:
                execute = false;
                read = false;
                System.out.println("12");
                break;
            case 13:
                execute = false;
                write = false;
                System.out.println("13");
                break;
            case 14:
                read = false;
                write = false;
                execute = false;
                System.out.println("14");
                break;
        }

        nodo.setExecute(execute);
        nodo.setWrite(write);
        nodo.setVisible(read);

        this.mn.actualizarNodo(nodo);
    }

    public void rmdirById() {

    }

    public void refrescarArbol() {
        DefaultTreeModel model = (DefaultTreeModel) this.mat.getAt().getjTree1().getModel();
        model.reload(getRoot());
    }

    public void pwd() {
        this.mat.setText(2, pathActual + "\n");
    }

    public void exit() {
        Exportador exp = new Exportador();
        exp.printList(this.mn.getListaNodos());
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

    public Nodo getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(Nodo nodoActual) {
        this.nodoActual = nodoActual;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }

}
