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
        String input = "algo/algo/algo";
        StringReader sr = new StringReader(input);
        
        Lexer lexer = new Lexer(sr);
        parser pars = new parser(lexer);
        try {
            pars.parse();
        } catch (Exception ex) {
            Logger.getLogger(AnalizadorSintactico01.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Analizador analizador = new Analizador();
//        analizador.setVisible(true);
    }
}
