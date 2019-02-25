package analizadorLexico.backend;

import Node.ManejadorNodo;
/**
 *
 * @author fabricio
 */
public class ManejadorParser {

    private int errors = 0;
    private ManejadorNodo mn = null;
    
    public ManejadorParser() {
        mn = new ManejadorNodo();
    }
    
    public void isNode(String nodoActual, String nodoAnterior, int position){
        if(mn.getNodo(nodoActual, nodoAnterior, position) == null){
            errors++;
        }
    }
    
    public void touchByPath(String nodoAnterior){
        System.out.println(nodoAnterior);
    }
    
    public void touchById(String nombreNodo){
        System.out.println(nombreNodo);
    }
    
    public void exit(){
        System.exit(0);
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
    
}
