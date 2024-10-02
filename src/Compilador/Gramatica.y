%{
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION
%start programa

%% /* Gramatica */
									/* PROGRAMA */
									
programa 	: ID BEGIN sentencias END {System.out.println(AnalizadorLexico.saltoDeLinea + " PROGRAMA ");}
;
	
sentencias 	: sentencias sentencia ';'
			| sentencia ';'
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


asignacion	: ID ASIGNACION expresion_arit {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| ID '{' CTE '}' ASIGNACION expresion_arit  {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
;

invocacion	: ID '(' parametro_real ')' 
			| ID '(' tipo parametros_formal ')'   //Conversiones
;

parametro_real	: list_expre
;

list_expre	: list_expre ',' expresion_arit
			| expresion_arit
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
		| ID '{' CTE '}' 
;

variables 	: variables ',' ID  
			| ID
;		

CTE_con_sig : CTE
			| '-' CTE
;

sentencia_IF: IF '(' condicion ')' bloque_unidad bloque_else END_IF ';'
			| IF '(' condicion ')' bloque_unidad END_IF ';'
;

condicion	: list_expre comparador list_expre //Tenemos en cuenta el pattern_matching 
;

comparador	: '>'
			| MAYORIGUAL
			| '<'
			| MENORIGUAL
			| '='
			| DISTINTO		
;

bloque_else: bloque_else_simple
			| bloque_else_multiple
;

bloque_else_multiple:	ELSE BEGIN bloque_sent_ejecutables END
;

bloque_else_simple: THEN ELSE bloque_sentencia_simple
;

bloque_unidad: bloque_unidad_simple
		| bloque_unidad_multiple
;		

bloque_unidad_multiple: THEN BEGIN bloque_sent_ejecutables END			

bloque_unidad_simple: THEN bloque_sentencia_simple
;

bloque_sent_ejecutables	: bloque_sent_ejecutables bloque_sentencia_simple
							| bloque_sentencia_simple
;

bloque_sentencia_simple: sentencia_ejecutable
;

cadena	: '[' CADENAMULTILINEA ']' 
		| '[' ']'
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: WHILE '(' condicion ')' bloque_unidad ';'
;	

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA
;

/* Tema 19: Pattern Matching*/
//Lo tenemos en cuenta en la regla Condicion
//.................HACIA ARRIBA NO HAY ERRORES..........................


%%																	 

int yylex() {
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	return AnalizadorLexico.getToken();
}
