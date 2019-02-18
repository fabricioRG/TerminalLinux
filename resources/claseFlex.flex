/* codigo de usuario */
package analizadorLexico;
import java_cup.runtime.*;
import static analizadorLexico.sym.*;

%% //separador de area

/* opciones y declaraciones de jflex */

%public
%class Lexer
%cup
%cupdebug
%line
%column


LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t\f]
Letra = [a-zA-Z]
Digito = [1-9][0-9]*
Identificador = ({Letra}|{Digito})({Letra}|{Digito}|"-"|"_"|"@"|"+"|"*"|"#"|".")*
Execute = "x"
Write = "w"
Read = "r"
Agregar = ("+"){Execute}?{Write}?{Read}?
Quitar = ("-"){Execute}?{Write}?{Read}?

%{
    StringBuilder string = new StringBuilder();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }


  private void error(String message) {
    System.out.println("Error en linea line "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
  }
%}

%% // separador de areas

/* reglas lexicas */
<YYINITIAL> {

	"pwd" {return symbol(CURRENT_DIRECTORY);}
	"ls" {return symbol(CONTENTS_DIRECTORY);}
	"-l" {return symbol(LONG);}
	"-a" {return symbol(HIDDEN);}
	"cd" {return symbol(CHANGE_DIRECTORY);}
	"touch"	{return symbol(TOUCH);}
	"mkdir" {return symbol(MAKE_DIR);}
	"mv" {return symbol(MOVE);}
	"cp" {return symbol(COPY);}
	"rm" {return symbol(REMOVE_FILE);}
	"rmdir" {return symbol(REMOVE_DIR);}
	"/" {return symbol(SLASH);}
	{Agregar} {return symbol(AGREGAR, yytext());}
	{Quitar} {return symbol(QUITAR, yytext());}
	{Identificador} {return symbol(ID, yytext());}
	{WhiteSpace} 		{/*Nothing to do*/}
	{LineTerminator} 	{/*Nothing to do*/ System.out.println("salto de linea");}

}
[^] {error("Simbolo invalido <"+ yytext()+">");}
<<EOF>>                 { return symbol(EOF); }
