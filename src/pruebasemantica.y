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
						//| sentencia_WHILE
						//| sentencia_goto
						| OUTF '(' expresion_arit ')' {System.out.println(AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
						| OUTF '(' cadena ')'	{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
;


asignacion	: ID ASIGNACION expresion_arit {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| ID '{' CTE '}' ASIGNACION expresion_arit  {System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
;

invocacion	: ID '(' parametro_real ')' 
			| ID '(' tipo parametros_formal ')'   //Conversiones
;

parametro_real	: parametro_real ',' expresion_arit
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
		| invocacion //Me hace ruido esto pero no lo cambio por ahora
		| ID '{' CTE_con_sig '}' //No debería ser una constante positiva únicamente?
;

variables 	: variables ',' ID  
			| ID
;		

CTE_con_sig : CTE
			| '-' CTE
;

sentencia_IF: IF '(' condicion ')' bloque_if bloque_else END_IF ';'
			| IF '(' condicion ')' bloque_if END_IF ';'
;

condicion	: expresion_arit comparador expresion_arit
			| expresion_arit
			//| pattern_matching
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

bloque_if: bloque_if_simple
		| bloque_if_multiple
;		

bloque_if_multiple: THEN BEGIN bloque_sent_ejecutables END			

bloque_if_simple: THEN bloque_sentencia_simple
;

bloque_sent_ejecutables	: bloque_sent_ejecutables bloque_sentencia_simple
							| bloque_sentencia_simple
;

bloque_sentencia_simple: sentencia_ejecutable
;

cadena	: '[' CADENAMULTILINEA ']' 
		| '[' ']'
;

//.................HACIA ARRIBA NO HAY ERRORES..........................

%%