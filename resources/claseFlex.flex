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

	"pwd" {return symbol(PWD, yytext());}
	"ls" {return symbol(LS, yytext());}
	"-l" {return symbol(LONG, yytext());}
	"-a" {return symbol(HIDDEN, yytext());}
	"-p" {return symbol(VARIOUS, yytext());}
	"-r" {return symbol(ALL, yytext());}
	"-R" {return symbol(RECURSIVE, yytext());}
	"cd" {return symbol(CD, yytext());}
	"touch"	{return symbol(TOUCH, yytext());}
	"mkdir" {return symbol(MKDIR, yytext());}
	"mv" {return symbol(MV, yytext());}
	"cp" {return symbol(CP, yytext());}
	"rm" {return symbol(RM, yytext());}
	"rmdir" {return symbol(RMDIR, yytext());}
	"chmod" {return symbol(CHMOD, yytext());}
	"exit" {return symbol(EXIT, yytext());}
	".." {return symbol(BACK, yytext());}
	"." {return symbol(ACTUAL, yytext());}
	"x" {return symbol(EXECUTE, yytext());}
	"w" {return symbol(WRITE, yytext());}
	"r" {return symbol(READ, yytext());}
	"+" {return symbol(AGREGAR, yytext());}
	"-" {return symbol(QUITAR, yytext());}
	"/" {return symbol(SLASH, yytext());}
	{Identificador} {return symbol(ID, yytext());}	
	{WhiteSpace} 		{return symbol(SPACE, yytext());}
	{LineTerminator} 	{/*Nothing to do*/ System.out.println("salto de linea");}

}
[^] {error("Simbolo invalido <"+ yytext()+">");}
<<EOF>>                 { return symbol(EOF); }
