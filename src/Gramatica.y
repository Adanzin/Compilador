%{
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION IGUALIGUAL
%start programa

%% /* Gramatica */
									/* PROGRAMA */
									
programa 	: ID BEGIN sentencias ';' END {System.out.println(AnalizadorLexico.saltoDeLinea + " PROGRAMA ");}
;
	
sentencias 	: sentencias sentencia ';'
			| sentencia 
;

sentencia 	: sentencia_declarativa 
			| sentencia_ejecutable
;			
				
									/* SENTENCIAS DECLARATIVAS */	
				
sentencia_declarativa 	: declaracion_variable 
						| declaracion_funciones	
						| declaracion_subtipo 
;

declaracion_variable	: tipo variables 
;

tipo : INTEGER
	 | DOUBLE
	 | ID /* si es una clase, un subtipo */
;

variables 	: variables ',' ID  
			| ID
;		

declaracion_subtipo : TYPEDEF ID ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'
					| TYPEDEF TRIPLE '<' tipo '>' ID 
;							
	
declaracion_funciones 	: tipo FUN ID parametros_formal BEGIN cuerpo_funcion END
						| ID FUN ID parametros_formal BEGIN cuerpo_funcion END	
;

parametros_formal	: parametros_formal parametro ','
					| parametro
;

parametro	: tipo ID	
;

cuerpo_funcion	: sentencias RET '(' expresion_arit ')'
				| sentencias
;


									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF
						| sentencia_WHILE
						| sentencia_goto
						| OUTF '(' expresion_arit ')' {System.out.println(AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
						| OUTF '(' cadena ')'	{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
;

/* USAMOS { en vez de [ para los arreglos asi no nos lo considera como cadena */
asignacion	: ID ASIGNACION expresion_arit {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| ID '{' CTE '}' ASIGNACION expresion_arit  {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
;

expresion_arit  : expresion_arit '+' termino
                | expresion_arit '-' termino
                | termino
;

termino : termino '*' factor 
        | termino '/' factor 
        | factor
;

factor 	: ID
		| CTE_con_sig
		| invocacion
		| ID '{' CTE_con_sig '}'
;				

CTE_con_sig : CTE	/*{if($1.ival > 32767){AnalizadorLexico.TablaDeSimbolos.remove($1.sval); System.out.println(" CTE fuera de rango");}}*/
			| '-' CTE /* { Simbolo simbaux = AnalizadorLexico.TablaDeSimbolos.get($2.sval);
						AnalizadorLexico.TablaDeSimbolos.remove($2.sval);
						String negativo = "-" + $2.sval;
						AnalizadorLexico.TablaDeSimbolos.put(negativo,simbaux);}*/
;

invocacion	: ID '(' parametro_real ')' {System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion");}
			| ID '(' tipo parametros_formal ')'  {System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion");} //Conversiones
;

parametro_real	: parametro_real ',' parametro_real
				| expresion_arit
				| CTE_con_sig
				| variables
;

sentencia_IF: IF '(' condicion ')' THEN bloques_sent_ejecutables ELSE bloques_sent_ejecutables END_IF {System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia IF");}
			| IF condicion THEN bloque_sentencias END_IF {System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia IF");}
;

condicion	: operando comparador operando
			| pattern_matching
;

comparador	: '>'
			| MAYORIGUAL
			| '<'
			| MENORIGUAL
			| IGUALIGUAL
			| DISTINTO		
;			

bloque_sentencias	: BEGIN bloques_sent_ejecutables END ';' {System.out.println(AnalizadorLexico.saltoDeLinea + " Bloque de sentencias");}
                	| BEGIN END ';' { System.out.println("Se esperaban sentencias entre BEGIN y END"); }	
;

bloques_sent_ejecutables	: bloques_sent_ejecutables sentencia_ejecutable
							| sentencia_ejecutable
;

cadena	: '[' CADENAMULTILINEA ']' 
		| '[' ']'
;
     						
									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: WHILE '(' condicion ')' bloques_sent_ejecutables {System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia WHILE");}
;							

/* Tema 19: Pattern Matching */
pattern_matching	: list_expre comparador list_expre {System.out.println(AnalizadorLexico.saltoDeLinea + " Pattern Matching");}
;

list_expre	: list_expre ',' expresion 
;

expresion	: expresion_arit
				| CTE_con_sig
				| variables

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA {System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia GOTO");}
;
																		 
%%

int yylex() {
	//yylval = new ParserVal(AnalizadorLexico.Lexema);
	return AnalizadorLexico.getToken();
}

void yyerror(String s)
{
 System.out.println("par:"+s);
}

void dotest()
{
 System.out.println("BYACC/J Calculator Demo");
 System.out.println("Note: Since this example uses the StringTokenizer");
 System.out.println("for simplicity, you will need to separate the items");
 System.out.println("with spaces, i.e.: '( 3 + 5 ) * 2'");
 /*while (true)
 {
 System.out.print("expression:");
 try
 {
 ins = in.readLine();
 }
 catch (Exception e)
 {
 }
 st = new StringTokenizer(ins);
 newline=false;*/
 yyparse();
 //}
}

public static void main(String args[])
{
 Parser par = new Parser(false);
 par.dotest();
}
