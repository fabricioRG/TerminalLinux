package analizadorsintactico01;

import analizadorLexico.Lexer;
import analizadorLexico.frontend.Analizador;
import analizadorLexico.parser;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabricio
 */
public class AnalizadorSintactico01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Analizador analizador = new Analizador();
        analizador.setVisible(true);
    }
}
