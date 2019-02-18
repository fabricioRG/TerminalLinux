package analizadorLexico.backend;

import analizadorLexico.frontend.AreaTexto;

/**
 *
 * @author fabricio
 */
public class ManejadorAreaTexto {

    public static final String COMANDOS = "comandos / $: ";
    private AreaTexto at = null;
    public static int SALTO_LINEA = 10;

    public ManejadorAreaTexto(AreaTexto at) {
        this.at = at;
    }
    
    public String getText(){
        String text = at.getjTextPane1().getText();
        return text.substring(text.lastIndexOf("$") + 2, text.length() - 1);
    }
    
    public void setText(){
        at.getjTextPane1().setText(at.getjTextPane1().getText() + COMANDOS);
    }
    
    //Metodo que tiene como funcion principal iniciar el automata encargado de evaluar los caracteres
    //contenidos en el area de texto o "jEditorPane"
    public void iniciarAutomata(String entrada){
        if(entrada.contains("a")){
            System.out.println("algo");
        }
    }
    
    
}
