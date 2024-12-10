%{
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.io.IOException;
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION ERROR OCTAL
%start programa

%% /* Gramatica */
									/* PROGRAMA */
																		
programa	: ID_simple BEGIN sentencias END {cargarGotos();}
			| ID_simple BEGIN END 
			| BEGIN sentencias END {cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el nombre del programa ");}
			| ID_simple BEGIN sentencias {cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador END ");}
			| ID_simple sentencias END {cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador BEGIN ");}
			| ID_simple sentencias {cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan los delimitadores del programa ");}
;
	
sentencias 	: sentencias sentencia  {if($1.sval=="RET" || $2.sval=="RET" ){$$.sval="RET";}}
			| sentencia {if($1.sval=="RET"){$$.sval="RET";}}
;

sentencia 	: sentencia_declarativa 
			| error ';' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  ");}
            | sentencia_ejecutable ';' {if($1.sval=="RET"){$$.sval="RET";}}
			| sentencia_ejecutable {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;			
				
									/* SENTENCIAS DECLARATIVAS */	
				
sentencia_declarativa 	: declaracion_variable
						| declaracion_funciones ';'
                        | declaracion_subtipo ';'
						| declaracion_funciones {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
						| declaracion_subtipo {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

declaracion_variable	: tipo variables ';' {if(!EerrorSintactico()){cargarVariables($2.sval,(Tipo)$1.obj," nombre de variable "); }}
						| tipo variables error {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

tipo : ID_simple {if(!EerrorSintactico()){ 
		if(tipos.containsKey($1.sval)){
			$$.obj = tipos.get($1.sval);
		}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea 
				+" Se utilizo un tipo desconocido ");};}}
	 |tipo_primitivo { $$.obj = $1.obj;  }
;	

tipo_primitivo: INTEGER { if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							$$.obj = tipos.get("INTEGER");}
              | DOUBLE  { if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							$$.obj = tipos.get("DOUBLE");}
              | OCTAL  { if(!tipos.containsKey("OCTAL")){tipos.put("OCTAL",new Tipo("OCTAL"));}
							$$.obj = tipos.get("OCTAL");}			
;

declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {if(!EerrorSintactico()){cargarVariables($2.sval,cargarSubtipo($2.sval,(Tipo)$4.obj,$6.sval,$8.sval)," nombre de SubTipo ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango ");}
                    | TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango ");}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango ");}
                    | TYPEDEF TRIPLE '<' tipo '>' ID_simple  {if($4.obj != null){if(!EerrorSintactico()){cargarVariables($6.sval,cargarTripla($6.sval,(Tipo)$4.obj,true)," nombre de Triple ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}}
					| TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos ");}
					| TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido ");}
					| TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo ");}
					| TYPEDEF '<' tipo '>' ID_simple {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE ");}
					| TYPEDEF TRIPLE tipo '>' ID_simple {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE");}
					| TYPEDEF TRIPLE '<' tipo ID_simple {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE");}
					| TYPEDEF TRIPLE tipo ID_simple {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE");}
					| TYPEDEF TRIPLE '<' tipo '>' error {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion");}			
;

declaracion_funciones     : encabezado_funcion parametros_parentesis BEGIN cuerpo_funcion END 
								{if(!EerrorSintactico())
								{	if($4.sval!="RET"){cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion ");}
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal($1.sval,(Tipo)$2.obj);									
								}}    
;

encabezado_funcion 	: tipo FUN ID {if(!EerrorSintactico()){$$.sval=$3.sval;cargarVariables($3.sval,(Tipo)$1.obj,"nombre de funcion");agregarAmbito($3.sval);DENTRODELAMBITO.push($3.sval);GeneradorCodigoIntermedio.addNuevaPolaca();}}
					| tipo FUN {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion ");}
;

parametros_parentesis: '(' tipo_primitivo ID_simple ')' {if(!EerrorSintactico()){$$.obj=$2.obj; GeneradorCodigoIntermedio.addElemento($3.sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables($3.sval,(Tipo)$2.obj," nombre de parametro real ");}}
					| '(' ID_simple ID_simple ')' 
										{if(!EerrorSintactico())
										{if(tipos.containsKey($2.sval))
										{$$.obj = tipos.get($2.sval); GeneradorCodigoIntermedio.addElemento($3.sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables($3.sval,tipos.get($2.sval)," nombre de parametro real ");
										}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}}								
					| '(' tipo_primitivo ')' {$$.obj=$2.obj;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion ");} 
					| '(' ID_simple ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion ");}
                    | '(' ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion ");}
                    | '(' error ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). ");}
;

cuerpo_funcion	: sentencias {if($1.sval=="RET"){$$.sval="RET";}}
;

retorno	: RET '(' expresion_arit ')' 
										{if(!EerrorSintactico())
										{if(!existeFuncion()){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
										}else{
											GeneradorCodigoIntermedio.addElemento("RET");
										}}}
		| RET '('  ')' {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  ");	}					
;
									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF {if($1.sval=="RET"){$$.sval="RET";}}
						| sentencia_WHILE
						| sentencia_goto
						| ETIQUETA {if(!EerrorSintactico()){if(fueDeclarado($1.sval+AMBITO.toString())){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA "+$1.sval+" ya existe  ");}else{cargarVariables($1.sval,tipos.get("ETIQUETA"),"ETIQUETA");}GeneradorCodigoIntermedio.addEtiqueta($1.sval+AMBITO.toString());GeneradorCodigoIntermedio.addElemento("LABEL"+$1.sval+AMBITO.toString());}}
						| outf_rule
						| retorno {$$.sval="RET";}
;

outf_rule   : OUTF '(' expresion_arit ')' {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("OUTF");}}
            | OUTF '(' ')' {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
            | OUTF '(' cadena ')'    {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("OUTF");}}
            | OUTF '(' sentencias ')'  {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. ");}
            | OUTF '(' '{' error '}' ')'  {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Cadena mal escrita en sentencia OUTF. ");}
;

asignacion	: variable_simple ASIGNACION expresion_arit 
															{if(!EerrorSintactico())
															{if(fueDeclarado($1.sval)){
															$$.sval = $1.sval;
															GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + $1.sval + "' no fue declarada");}
															}}
			| variable_simple '{' CTE '}' ASIGNACION expresion_arit  
																	{if(!EerrorSintactico())
																	{if(fueDeclarado($1.sval)){
																		if(Integer.valueOf($3.sval) <= 3 && Integer.valueOf($3.sval) > 0){
																			$$.sval = $1.sval;
																			GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
																			GeneradorCodigoIntermedio.addElemento($3.sval);
																			GeneradorCodigoIntermedio.addElemento("INDEX");
																			GeneradorCodigoIntermedio.addElemento(":="); 
																			}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La posicion " + $3.sval + " es invalida, se espera un valor entre 1 y 3. ");}														
																	}else{
																		cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + $1.sval + "' no fue declarada");}}}
			| variable_simple '{' '-' CTE '}' ASIGNACION expresion_arit {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
			| ASIGNACION expresion_arit {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el operando izquierdo de la asignacion. ");}
;

invocacion	: ID_simple '(' expresion_arit ')' 
												{if(!EerrorSintactico())
												{if(!fueDeclarado($1.sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + $1.sval + "' no fue declarada");}
													else{	
														GeneradorCodigoIntermedio.invocar($1.sval+AMBITO.toString());																																																		
												}}}
			| ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')' 
												{if(!EerrorSintactico())
												{if(!fueDeclarado($1.sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + $1.sval + "' no fue declarada");}
													else{
														GeneradorCodigoIntermedio.invocar($1.sval+AMBITO.toString(), ((Tipo)$3.obj).getType());
												}}}
			| ID_simple '(' ')' {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
			| ID_simple '(' error ')' {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
;


list_expre	: list_expre ',' expresion_arit {$$.ival=$1.ival + 1;if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento(",");}}
			| ',' expresion_arit {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
			| list_expre ','  {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
			| expresion_arit {$$.ival=1;if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento(",");}}
;

expresion_arit  : expresion_arit '+' termino {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("+"); }}
                | expresion_arit '-' termino {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("-"); }}
                | termino
				| error '+' error{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
				| error '-' error{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
				| expresion_arit '+' error {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
				| expresion_arit '-' error {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
				| error termino {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
;

termino : termino '*' factor {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("*");} }
        | termino '/' factor {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("/");}}
        | factor 
		| error '*' error{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| error '/' error{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| termino '*' error {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| termino '/' error {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
;

factor 	: variable_simple {if(!EerrorSintactico()){if(fueDeclarado($1.sval)){GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+$1.sval+ "' no fue declarada");};}}
		| CTE_con_sig {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento($1.sval);}}
		| invocacion 
		| variable_simple '{' CTE '}' 
										{if(!EerrorSintactico())
											{if(fueDeclarado($1.sval)){ 
											if(Integer.valueOf($3.sval) <= 3 && Integer.valueOf($3.sval) > 0){
												GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
												GeneradorCodigoIntermedio.addElemento($3.sval);
												GeneradorCodigoIntermedio.addElemento("INDEX");
												AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;
											}else{
												cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La posicion " + $3.sval + " es invalida, se espera un valor entre 1 y 3. ");
											}
										}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+$1.sval+ "' no fue declarada");};}}
		| variable_simple '{' '-' CTE '}' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
;
variables 	: variables ',' variable_simple { $$.sval = $1.sval + "/"+$3.sval;} 
			| variables variable_simple {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
			| variable_simple {$$.sval = $1.sval;}
;

variable_simple : ID_simple 
;

ID_simple : ID 
;


CTE_con_sig : CTE {if(estaRango($1.sval)) { $$.sval = $1.sval; } }
			| '-' CTE { cambioCTENegativa($2.sval); $$.sval = "-" + $2.sval;}
;				

sentencia_IF: IF condicion bloque_THEN ';' bloque_else ';' END_IF {if($3.sval=="RET" && $5.sval=="RET"){$$.sval="RET";};if(!EerrorSintactico()){completarBifurcacionI();}}
            | IF condicion bloque_THEN ';' END_IF {if(!EerrorSintactico()){completarBifurcacionISinElse();}}
			| IF condicion THEN END_IF {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
			| IF condicion bloque_THEN ';' ELSE END_IF {{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}


			| IF condicion bloque_THEN bloque_else ';' END_IF {if($3.sval=="RET" && $4.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
			| IF condicion bloque_THEN END_IF {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
			| IF condicion bloque_THEN ';' bloque_else END_IF {if($3.sval=="RET" && $5.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
			| IF condicion bloque_THEN bloque_else END_IF {if($3.sval=="RET" && $4.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
			
			| IF condicion bloque_THEN ';' error{$$.sval=$3.sval;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
			| IF condicion bloque_THEN ';' bloque_else ';' error{if($3.sval=="RET" && $5.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
;

condicion	: '(' '(' list_expre ')' comparador '(' list_expre ')' ')' {if($3.ival == $7.ival){if(!EerrorSintactico()){cantDeOperandos=$3.ival;modificarPolacaPM($5.sval,$3.ival);}}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
			| '(' list_expre ')' comparador '(' list_expre ')' ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
			| '(' '(' list_expre ')' comparador '(' list_expre ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
			|'(' list_expre ')' comparador '(' list_expre ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
			| '(' expresion_arit comparador expresion_arit ')' {cantDeOperandos=1;if(!EerrorSintactico()){opCondicion($3.sval);}}
			|  expresion_arit comparador expresion_arit ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
			| '(' expresion_arit comparador expresion_arit  {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
			| expresion_arit comparador expresion_arit  {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
			
			| '(' list_expre ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
			
			|'(' '(' list_expre ')'  '(' list_expre ')' ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
			|'(' '(' list_expre comparador '(' list_expre ')' ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
			|'(' '(' list_expre ')' comparador list_expre ')' ')' {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
;

comparador	: '>' {$$.sval=">";} 
			| MAYORIGUAL {$$.sval=">=";} 
			| '<' {$$.sval="<";} 
			| MENORIGUAL {$$.sval="<=";} 
			| '=' {$$.sval="=";} 
			| DISTINTO {$$.sval="!=";} 
;
// ELSE
bloque_else: bloque_else_simple {if($1.sval=="RET"){$$.sval="RET";};}
			| bloque_else_multiple {if($1.sval=="RET"){$$.sval="RET";};}
;

bloque_else_multiple:	ELSE BEGIN bloque_sent_ejecutables ';' END {if($3.sval=="RET"){$$.sval="RET";};}
					| ELSE BEGIN END {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
					| ELSE BEGIN bloque_sent_ejecutables END {if($3.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
					| ELSE BEGIN bloque_sent_ejecutables error {if($3.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del ELSE");}
					
;

bloque_else_simple:	ELSE bloque_sentencia_simple {if($2.sval=="RET"){$$.sval="RET";};}
;
//THEN
bloque_THEN: bloque_THEN_simple {if(!EerrorSintactico()){if($1.sval=="RET"){$$.sval="RET";};operacionesIF();}}
			| bloque_THEN_multiple {if(!EerrorSintactico()){if($1.sval=="RET"){$$.sval="RET";};operacionesIF();}}
;

bloque_THEN_multiple:	THEN BEGIN bloque_sent_ejecutables ';' END {if($3.sval=="RET"){$$.sval="RET";};}
					| THEN BEGIN END {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
					| THEN BEGIN bloque_sent_ejecutables END {if($3.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
					| THEN BEGIN bloque_sent_ejecutables error {if($3.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del THEN");}
;

bloque_THEN_simple:	THEN bloque_sentencia_simple {if($2.sval=="RET"){$$.sval="RET";};}
;

//SIMPLE
bloque_unidad	: bloque_unidad_simple {if($1.sval=="RET"){$$.sval="RET";};}
				| bloque_unidad_multiple {if($1.sval=="RET"){$$.sval="RET";};}
;		


bloque_unidad_multiple  : BEGIN bloque_sent_ejecutables ';' END {if($2.sval=="RET"){$$.sval="RET";};}
						| BEGIN END {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
						| BEGIN bloque_sent_ejecutables END {if($2.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
						| BEGIN bloque_sent_ejecutables error {if($2.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias");}						
;				

bloque_unidad_simple:  bloque_sentencia_simple {if($1.sval=="RET"){$$.sval="RET";}}
;

bloque_sent_ejecutables	: bloque_sent_ejecutables ';' bloque_sentencia_simple {if($1.sval=="RET" || $3.sval=="RET"){$$.sval="RET";};}
						| bloque_sentencia_simple {if($1.sval=="RET"){$$.sval="RET";};}
						| bloque_sent_ejecutables bloque_sentencia_simple {if($1.sval=="RET" || $2.sval=="RET"){$$.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

bloque_sentencia_simple: sentencia_ejecutable {if($1.sval=="RET"){$$.sval="RET";};}
;

cadena	: CADENAMULTILINEA {if(!EerrorSintactico()){cargarCadenaMultilinea($1.sval);GeneradorCodigoIntermedio.addElemento($1.sval);}}
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: encabezado_WHILE condicion bloque_unidad {if(!EerrorSintactico()){operacionesWhile();}}
				| encabezado_WHILE condicion error {cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
;	

encabezado_WHILE : WHILE {if(!EerrorSintactico()){GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}}
;

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA {if(!EerrorSintactico()){GeneradorCodigoIntermedio.addBaulDeGotos($2.sval+AMBITO.toString()
									+"/"+AMBITO.toString()
									+"/"+String.valueOf(GeneradorCodigoIntermedio.getPos()));}}
				| GOTO ID_simple {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea 
									+ " Error: Falta el caracter '@' de la etiqueta. ");}
				| GOTO error  {cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea
									 + " Error: Falta la etiqueta en GOTO ");}
;
/* Tema 19: Pattern Matching*/
//Lo tenemos en cuenta en la regla Condicion
//.................HACIA ARRIBA NO HAY ERRORES..........................

%%	
public static StringBuilder AMBITO = new StringBuilder("$MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>(); 
public static boolean RETORNOTHEN = false;
public static boolean RETORNOELSE = false;
public static int cantDeOperandos;
public static Map<String,Tipo> tipos = new HashMap<>();
static{
	tipos.put("INTEGER", new Tipo("INTEGER"));
	tipos.put("DOUBLE", new Tipo("DOUBLE"));
	tipos.put("OCTAL", new Tipo("OCTAL"));
	tipos.put("ETIQUETA", new Tipo("ETIQUETA"));
}

public static boolean EerrorSintactico(){
		return CreacionDeSalidas.getOutputSintactico().length()!=0 || CreacionDeSalidas.getOutputLexico().length()!=0;
}


public static void cargarErrorEImprimirloSintactico(String salida) {	
		try {
			AnalizadorLexico.sintactico.newLine();  // Agregar un salto de l nea
			AnalizadorLexico.sintactico.write(" "+salida+" ");
			AnalizadorLexico.sintactico.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

public static void cargarErrorEImprimirloSemantico(String salida) {	
		try {
			AnalizadorLexico.semantico.newLine();  // Agregar un salto de l nea
			AnalizadorLexico.semantico.write(" "+salida+" ");
			AnalizadorLexico.semantico.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

private static void cargarGotos(){
	    while (!GeneradorCodigoIntermedio.BaulDeGotos.isEmpty()) {
	        // Obtenemos el primer elemento
	        String[] elemento = GeneradorCodigoIntermedio.BaulDeGotos.get(0);
	        String key = elemento[0];
	        boolean terminoWhile = false;
	        boolean noHayEtiqueta = false;

	        // Bucle para reducir el key si no se encuentra directamente
	        while (!terminoWhile) {
	            if (GeneradorCodigoIntermedio.Etiquetas.contains(key)) {
	                int pos = Integer.valueOf(elemento[2]);
	                GeneradorCodigoIntermedio.reemplazarElm(key, pos, elemento[1]); // Reemplaza el elemento con el m todo adecuado
	                GeneradorCodigoIntermedio.BaulDeGotos.remove(0); // Eliminamos el primer elemento
	                noHayEtiqueta = false;
	                terminoWhile = true; // Terminamos el ciclo si encontramos la etiqueta
	            } else {
	                noHayEtiqueta = true;
	            }

	            // Reducimos el  mbito: eliminamos el  ltimo '$NIVEL'
	            int pos = key.lastIndexOf("$");
	            if (pos == -1) {
	                terminoWhile = true; // Si no hay m s  mbitos, salimos del ciclo
	            } else {
	                key = key.substring(0, pos); // Reducimos el  mbito actual
	            }
	        }

	        // Si no se encontr  etiqueta, avanzamos al siguiente elemento (ya manejado por el while principal)
	        if (noHayEtiqueta) {
	        	GeneradorCodigoIntermedio.BaulDeGotos.remove(0); // Eliminamos el elemento para evitar ciclos infinitos
	            cargarErrorEImprimirloSemantico("No se encontr  la etiqueta llamada: " + key);
	        }	  
	}
}


private static void cargarCadenaMultilinea(String cadena){
	Tipo t = new Tipo("CADENAMULTILINEA");
	tipos.put("CADENAMULTILINEA",t);
	cargarVariables(cadena,t," Cadena multilinea ");
}

private static void modificarPolacaPM(String operador, int cantDeOp){
	GeneradorCodigoIntermedio.addOperadorEnPattMatch(operador,cantDeOp);
}

private static void opCondicion(String operador){
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
};




private static void operacionesWhile(){
	int aux=0;
	while(aux<cantDeOperandos){
		completarBifurcacionF();
		aux++;
	}
	GeneradorCodigoIntermedio.bifurcarAlInicio();
}

private static void completarBifurcacionISinElse(){
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void operacionesIF(){
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	int aux=0;
	while(aux<cantDeOperandos){
		completarBifurcacionF();
		aux++;
	}
	GeneradorCodigoIntermedio.bifurcarI();
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void completarBifurcacionF(){
	
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
}
private static void completarBifurcacionI(){
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);
}

private static void cargarParametroFormal(String id,Tipo t){
	if(t!=null){
		AnalizadorLexico.TablaDeSimbolos.get(id+AMBITO.toString()).setTipoParFormal(t.getType());
	}
};

private static boolean existeFuncion(){
	return !DENTRODELAMBITO.isEmpty();
}

private static void  idEsUnTipo(String id){
	AnalizadorLexico.TablaDeSimbolos.get(id).setEsTipo(true);
}

private static Tipo getTipoDef(String id){
	return AnalizadorLexico.TablaDeSimbolos.get(id).getTipoVar();
}

private static Tipo cargarSubtipo(String name, Tipo t,  String min, String max){
	if(t.esSubTipo()){
		cargarErrorEImprimirloSemantico( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirloSemantico( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		if(min.contains(".") && max.contains(".")){
			double mini = Double.valueOf(min);
			double maxi = Double.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi,name+AMBITO.toString()));
		}else{
			int mini=Integer.valueOf(min);
			int maxi = Integer.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi,name+AMBITO.toString()));
		}
	}
	return tipos.get(name);
}

private static Tipo cargarTripla(String name, Tipo t, boolean tripla){
	
	if(t.esSubTipo()){
		cargarErrorEImprimirloSemantico( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirloSemantico( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		tipos.put(name,new Tipo(t.getType(),tripla));
	}
	return tipos.get(name);
}

private static boolean fueDeclarado(String id){
	
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    while (true) {
        // Construimos la clave: id +  mbito actual
        String key = id + ambitoActual;
        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el ambito: eliminamos el ultimo ':NIVEL'
        int pos = ambitoActual.lastIndexOf("$");
        if (pos == -1) {
            break; // Si ya no hay m s  mbitos, salimos del ciclo
        }

        // Reducimos el  mbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

   return false;
}

public static Simbolo getVariableFueraDeAmbito(String id){
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String
    String key = id;
    while (true) {
        // Construimos la clave: id +  mbito actual

        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el  mbito: eliminamos el  ltimo '$NIVEL'
        int pos = key.lastIndexOf("$");

        // Reducimos el  mbito actual
        key = key.substring(0,pos);
    }
}
private static boolean existeEnEsteAmbito(String id){
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    // Construimos la clave: id +  mbito actual
    String key = id + ambitoActual;

    // Buscamos en el mapa
    if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
        return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
    }
	return false;
}

private static void cargarVariables(String variables, Tipo tipo, String uso){
	String[] var = getVariables(variables,"/");
	for (String v : var) {
		if(tipo!=null) {
			if(tipo.getType()!="ETIQUETA"){
				if(!existeEnEsteAmbito(v)){
				if(tipo.getType()=="CADENAMULTILINEA"){
					//Si es cadena multiline y tiene '/' a a separarse en 2 la key de la cadena por el metodo "getVariables()", por lo tanto usamos directamente variables
					addTipo(variables,tipo);
					addUso(variables,uso);
					declarar(variables);
				}else if(uso=="nombre de funcion"){
					addAmbitoIDFUN(v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
				}else{
					addAmbitoID(v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
				}
				}else{
				cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +"  La variable  " + v + " ya fue declarada.");
				}
			}else{
					addAmbitoID(v);
					AnalizadorLexico.TablaDeSimbolos.get(v+AMBITO.toString()).setAmbitoVar(AMBITO.toString()+"$"+v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
			}
		}
    }
}
private static void declarar(String id){
	AnalizadorLexico.TablaDeSimbolos.get(id).fueDeclarada();
};
private static void addUso(String id,String uso){
	AnalizadorLexico.TablaDeSimbolos.get(id).setUso(uso);
};

public static String[] getVariables(String var, String separador) {
    // Verifica si el string contiene '/'
    if (!var.contains(separador)) {
        // Retorna un arreglo vac o o el string completo como  nico elemento
        return new String[] { var };
    }
    // Usa split() para dividir el String por el car cter '/'
    String[] variables = var.split(separador);
    return variables;
}

private static void addTipo(String id, Tipo tipo) {
	AnalizadorLexico.TablaDeSimbolos.get(id).setTipoVar(tipo);
};

private static void agregarAmbito(String amb) {
	AMBITO.append("$").append(amb);
}

private static void sacarAmbito() {
	// agarro el index del ultimo ':'
	int pos = AMBITO.lastIndexOf("$");
	// borro hasta esa posicion
	AMBITO.delete(pos, AMBITO.length());
}

private static void addAmbitoID(String id) {
	AnalizadorLexico.TablaDeSimbolos.get(id).agregarAmbitoaVar(AMBITO.toString());
}

private static void addAmbitoIDFUN(String id) {
	AnalizadorLexico.TablaDeSimbolos.get(id).agregarAmbitoaVar(AMBITO.toString());
	AnalizadorLexico.TablaDeSimbolos.get(id+AMBITO.toString()).setAmbitoVar(AMBITO.toString()+"$"+id);
}

int yylex() {
	int tokenSalida = AnalizadorLexico.getToken();
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	if(tokenSalida==0) {
		return AnalizadorLexico.siguienteLectura(AnalizadorLexico.archivo_original,' ');
	}
	return tokenSalida;
}
private static void yyerror(String string) {
	System.out.println();
}

private static void cambioCTENegativa(String key) {
	String keyNeg = "-" + key;
	if(AnalizadorLexico.TablaDeSimbolos.get(key) != null){
		if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
			AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
		}
		AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
		// es ultimo ya decrementa
		if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
			AnalizadorLexico.TablaDeSimbolos.remove(key);
		}
	}
}
private static boolean estaRango(String key) {
	if(AnalizadorLexico.TablaDeSimbolos.get(key) != null){
		if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
			if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
					if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
						AnalizadorLexico.TablaDeSimbolos.remove(key);
					}
				yyerror("Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango.");
				return false;
			}
		}
	}
	return true;
}