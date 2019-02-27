package analizadorLexico.backend;

import analizadorLexico.Lexer;
import analizadorLexico.frontend.AreaTexto;
import analizadorLexico.parser;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabricio
 */
public class ManejadorAreaTexto {

    public static final String COMANDOS_INIT = "comandos ";
    public static final String COMANDOS_FINAL = " $: ";
    public static final String ACCION_VALIDA = "---Accion valida---\n";
    public static final String ACCION_NO_VALIDA = "---Accion no valida: ";
    private AreaTexto at = null;
    private ManejadorParser mp = null;
    private String pathActual = null;
    public static int SALTO_LINEA = 10;

    public ManejadorAreaTexto(AreaTexto at) {
        this.at = at;
        this.mp = new ManejadorParser(this);
    }

    public String getText() {
        String text = at.getjTextPane1().getText();
        return text.substring(text.lastIndexOf("$") + 3, text.length() - 1);
    }

    public void analizarTexto() {
        String input = getText();
        StringReader sr = new StringReader(input);

        Lexer lexer = new Lexer(sr);
        parser pars = new parser(lexer, this.mp);
        try {
            pars.parse();
            setText(0, this.mp.getPathActual());
        } catch (Exception ex) {
            setText(1, ACCION_NO_VALIDA + ex.getMessage() + "---\n" + COMANDOS_INIT + this.mp.getPathActual());
            ex.printStackTrace();
        }
    }

    public void setText(int option, String cadena) {
        if (option == 0) {
            at.getjTextPane1().setText(at.getjTextPane1().getText() /*+ ACCION_VALIDA*/ + COMANDOS_INIT + cadena + COMANDOS_FINAL);
        } else if (option == 1) {
            at.getjTextPane1().setText(at.getjTextPane1().getText() + cadena + COMANDOS_FINAL);
        } else {
            at.getjTextPane1().setText(at.getjTextPane1().getText() + cadena);
        }
    }

    //Metodo que tiene como funcion principal iniciar el automata encargado de evaluar los caracteres
    //contenidos en el area de texto o "jEditorPane"
    public void iniciarAutomata(String entrada) {
        if (entrada.contains("a")) {
            System.out.println("algo");
        }
    }

    public AreaTexto getAt() {
        return at;
    }

    public void setAt(AreaTexto at) {
        this.at = at;
    }
}
