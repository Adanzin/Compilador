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
						//| sentencia_IF
						//| sentencia_WHILE
						//| sentencia_goto
						//| OUTF '(' expresion_arit ')' {System.out.println(AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
						//| OUTF '(' cadena ')'	{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
;


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
		//| invocacion
		| ID '{' CTE_con_sig '}'
;			

CTE_con_sig : CTE	/*{if($1.ival > 32767){AnalizadorLexico.TablaDeSimbolos.remove($1.sval); System.out.println(" CTE fuera de rango");}}*/
			| '-' CTE /* { Simbolo simbaux = AnalizadorLexico.TablaDeSimbolos.get($2.sval);
						AnalizadorLexico.TablaDeSimbolos.remove($2.sval);
						String negativo = "-" + $2.sval;
						AnalizadorLexico.TablaDeSimbolos.put(negativo,simbaux);}*/
;


%%