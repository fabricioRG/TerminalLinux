package analizadorLexico.backend;

import analizadorLexico.frontend.AreaTexto;
import analizadorLexico.frontend.Analizador;
//import analizadorSintactico.backend.Archivos;
//import analizadorSintactico.backend.AutomataPila;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author fabricio
 */
public class ManejadorAnalizador {

    static final String NEW_TAB = "new tab";
    static final String TIPO_TXT = ".txt";
    private Analizador analizador = null;

    public ManejadorAnalizador(Analizador analizador) {
        this.analizador = analizador;

    }

    //Metodo encargado de abrir una ventana en blanco
    public void agregarVentana() {
        AreaTexto at = new AreaTexto();
        analizador.jTabbedPane.add(NEW_TAB, at);
    }

    //Metodo encargado de agregar una nueva ventana con el texto cargado colocando como
    //nombre de la ventana el path absoluta de tal
    public void agregarVentana(String path, String texto) {
        AreaTexto at = new AreaTexto();
        at.getjTextPane1().setText(texto);
        analizador.jTabbedPane.add(path, at);
        //at.getMat().iniciarAutomata();
    }

    //Metodo encargado de abrir una nueva ventana colocandole como texto inicial el seleccionado

    //Metodo encargado de cerrar una ventanva, verificando que se hayan guardado los cambios o no
    public void cerrarVentana(int ventana) {
        AreaTexto at = (AreaTexto) analizador.jTabbedPane.getSelectedComponent();
        if(ventana >= 0){
        analizador.jTabbedPane.remove(ventana);            
        }
    }

    //Metodo que muestra un dialogo que contiene la informacion del desarrollador
    public void mostrarInformacionDesarrollador() {
        ImageIcon desarrollador = new ImageIcon("desarrollador.png");
        String informacion = "";
        informacion = "                     Analizador Lexico\n\n"
                + "                      Desarrollado por:\n"
                + "            Ivan Fabricio Racancoj García\n"
                + "                            201731115\n4to Semestre Ing. Sistemas CUNOC - USAC";
        JOptionPane.showMessageDialog(analizador, informacion, "About...", JOptionPane.INFORMATION_MESSAGE, desarrollador);
    }

    //Metodo encargado de cerrar todas las ventanas abiertas
    public void cerrarVentanas(int tamaño) {
            System.exit(0);
    }

}
