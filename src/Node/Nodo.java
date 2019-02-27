/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author fabricio
 */
public class Nodo {
    
    private Nodo padre;
    private String nombre;
    private  int posicion;
    private int id;
    private int tamano;
    private Date fechaCreacion;
    private boolean directory;
    private boolean execute;
    private boolean write;
    private boolean visible;
    private DefaultMutableTreeNode dmtn;

    public Nodo(Nodo padre, String nombre, int posicion, int id, int tamano, Date fechaCreacion, 
            boolean directory, boolean execute, boolean write, boolean visible, DefaultMutableTreeNode dmtn) {
        this.padre = padre;
        this.nombre = nombre;
        this.posicion = posicion;
        this.id = id;
        this.tamano = tamano;
        this.fechaCreacion = fechaCreacion;
        this.directory = directory;
        this.execute = execute;
        this.write = write;
        this.visible = visible;
        this.dmtn = dmtn;
    }
    
    public Nodo(Nodo padre, String nombre, int posicion, int id, int tamano, boolean directory, 
            Date fechaCreacion, DefaultMutableTreeNode dmtn) {
        this.padre = padre;
        this.nombre = nombre;
        this.posicion = posicion;
        this.id = id;
        this.tamano = tamano;
        this.fechaCreacion = fechaCreacion;
        this.directory = directory;
        this.execute = true;
        this.write = true;
        this.visible = true;
        this.dmtn = dmtn;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public DefaultMutableTreeNode getDmtn() {
        return dmtn;
    }

    public void setDmtn(DefaultMutableTreeNode dmtn) {
        this.dmtn = dmtn;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
    
}
