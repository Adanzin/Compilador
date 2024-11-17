%{
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.io.IOException;
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION ERROR
%start programa

%% /* Gramatica */
									/* PROGRAMA */
																		
programa	: ID_simple BEGIN sentencias END {cargarErrorEImprimirlo( "\u001B[0m"+"Se identifico el programa "+ $1.sval );}
			| ID_simple BEGIN END {	cargarErrorEImprimirlo( "\u001B[0m"+"Se identifico el programa "+ $1.sval );}	
			| BEGIN sentencias END {cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el nombre del programa ");}
			| ID_simple BEGIN sentencias {cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador END ");}
			| ID_simple sentencias END {cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador BEGIN ");}
			| ID_simple sentencias {cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan los delimitadores del programa ");}
;
	
sentencias 	: sentencias sentencia  {if($1.sval=="RET" || $2.sval=="RET" ){$$.sval="RET";}}
			| sentencia {if($1.sval=="RET"){$$.sval="RET";}}
;

sentencia 	: sentencia_declarativa 
			| error ';' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  ");}
            | sentencia_ejecutable ';' {if($1.sval=="RET"){$$.sval="RET";}}
			| sentencia_ejecutable {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;			
				
									/* SENTENCIAS DECLARATIVAS */	
				
sentencia_declarativa 	: declaracion_variable
						| declaracion_funciones ';'
                        | declaracion_subtipo ';'
						| declaracion_funciones {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
						| declaracion_subtipo {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

declaracion_variable	: tipo variables ';' {cargarVariables($2.sval,(Tipo)$1.obj," nombre de variable "); cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
						| tipo variables error {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

tipo : ID_simple { if(tipos.containsKey($1.sval)){$$.obj = tipos.get($1.sval);
					}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}
	 |tipo_primitivo { $$.obj = $1.obj;  }
;	

tipo_primitivo: INTEGER { if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							$$.obj = tipos.get("INTEGER");}
              | DOUBLE  { if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							$$.obj = tipos.get("DOUBLE");}
;

declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {if($4.obj != null){cargarVariables($2.sval,cargarSubtipo($2.sval,(Tipo)$4.obj,$6.sval,$8.sval)," nombre de SubTipo ");cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango ");}
                    | TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango ");}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango ");}
                    | TYPEDEF TRIPLE '<' tipo '>' ID_simple  {if($4.obj != null){cargarVariables($6.sval,cargarTripla($6.sval,(Tipo)$4.obj,true)," nombre de Triple ");cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
					| TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos ");}
					|TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido ");}
					| TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo ");}
					| TYPEDEF '<' tipo '>' ID_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE ");}
					| TYPEDEF TRIPLE tipo '>' ID_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE");}
					| TYPEDEF TRIPLE '<' tipo ID_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE");}
					| TYPEDEF TRIPLE tipo ID_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE");}
					| TYPEDEF TRIPLE '<' tipo '>' error {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion");}			
;

declaracion_funciones     : encabezado_funcion parametros_parentesis BEGIN cuerpo_funcion END 
								{	if($4.sval!="RET"){cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion ");}
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal($1.sval,(Tipo)$2.obj);									
								}                  
;

encabezado_funcion 	: tipo FUN ID {$$.sval=$3.sval;cargarVariables($3.sval,(Tipo)$1.obj," nombre de funcion ");agregarAmbito($3.sval);DENTRODELAMBITO.push($3.sval);GeneradorCodigoIntermedio.addNuevaPolaca();cargarErrorEImprimirlo(" Encabezado de la funcion ");}
					| tipo FUN {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion ");}
;

parametros_parentesis: '(' tipo_primitivo ID_simple ')' {$$.obj=$2.obj;cargarVariables($3.sval,(Tipo)$2.obj," nombre de parametro real ");}
					| '(' tipo_primitivo ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion ");} 
					| '(' ID_simple ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion ");}
                    | '(' ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion ");}
                    | '(' error ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). ");}
;

cuerpo_funcion	: sentencias {if($1.sval=="RET"){$$.sval="RET";}}
;

retorno	: RET '(' expresion_arit ')' {if(!existeFuncion()){cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");}}
		| RET '('  ')' {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  ");
						if(!existeFuncion())
										{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
						}}
;
									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF {if($1.sval=="RET"){$$.sval="RET";}}
						| sentencia_WHILE
						| sentencia_goto
						| ETIQUETA {if(existeEnEsteAmbito($1.sval)){completarBifurcacionAGoto($1.sval+AMBITO.toString());cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA que se pretende bifurcar no existe.  ");}}
						| outf_rule
						| retorno {$$.sval="RET";}
;

outf_rule    : OUTF '(' expresion_arit ')' {GeneradorCodigoIntermedio.addElemento("OUTF");cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
            | OUTF '(' ')' {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
            | OUTF '(' cadena ')'    {GeneradorCodigoIntermedio.addElemento("OUTF");cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
            | OUTF '(' sentencias ')'  {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. ");}
;

asignacion	: variable_simple ASIGNACION expresion_arit {if(fueDeclarado($1.sval)){
															$$.sval = $1.sval;
															GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");}
															cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| variable_simple '{' CTE '}' ASIGNACION expresion_arit  {if(fueDeclarado($1.sval)){
															$$.sval = $1.sval;
															GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento($3.sval);
															GeneradorCodigoIntermedio.addElemento("INDEX");
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");}
																cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
			| variable_simple '{' '-' CTE '}' ASIGNACION expresion_arit {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
;

invocacion	: ID_simple '(' expresion_arit ')' {if(!fueDeclarado($1.sval)){
													System.out.println(" NO SE DECLARO ");
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada ");}
													else{														
														System.out.println(" llegas hasta aca ");
														cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");														
														GeneradorCodigoIntermedio.invocar($1.sval+AMBITO.toString());}																										
												}
			| ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')' 
												{if(!fueDeclarado($1.sval)){
													System.out.println(" CONVERSION ");
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada ");}
													else{
														System.out.println(" CONVERSION ");
														cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");
														GeneradorCodigoIntermedio.invocar($1.sval+AMBITO.toString());}
												}
			| ID_simple '(' ')' {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
			| ID_simple '(' error ')' {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
;

list_expre	: list_expre ',' expresion_arit {$$.ival=$1.ival + 1;}
			| ',' expresion_arit {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
			| list_expre ','  {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
			| expresion_arit {$$.ival=1;}
;

expresion_arit  : expresion_arit '+' termino {GeneradorCodigoIntermedio.addElemento("+"); }
                | expresion_arit '-' termino {GeneradorCodigoIntermedio.addElemento("-"); }
                | termino
				| error '+' error{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
				| error '-' error{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
				| expresion_arit '+' error {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
				| expresion_arit '-' error {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
				| error termino {GeneradorCodigoIntermedio.imprimirPolaca();cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
;

termino : termino '*' factor {GeneradorCodigoIntermedio.addElemento("*");} 
        | termino '/' factor {GeneradorCodigoIntermedio.addElemento("/");}
        | factor 
		| error '*' error{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| error '/' error{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| termino '*' error {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
		| termino '/' error {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
;

factor 	: variable_simple {if(fueDeclarado($1.sval)){System.out.println(" VARSIMPLE "+ $1.sval+Parser.AMBITO.toString());GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");};}
		| CTE_con_sig {GeneradorCodigoIntermedio.addElemento($1.sval);}
		| invocacion 
		| variable_simple '{' CTE '}' {if(fueDeclarado($1.sval)){ 
										GeneradorCodigoIntermedio.addElemento($1.sval+Parser.AMBITO.toString());
										GeneradorCodigoIntermedio.addElemento($3.sval);
										GeneradorCodigoIntermedio.addElemento("INDEX");
										AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");};}
		| variable_simple '{' '-' CTE '}' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
;
variables 	: variables ',' variable_simple { $$.sval = $1.sval + "/"+$3.sval;} 
			| variables variable_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
			| variable_simple {$$.sval = $1.sval;}
;

variable_simple : ID_simple 
;

ID_simple : ID 
;


CTE_con_sig : CTE {if(estaRango($1.sval)) { $$.sval = $1.sval; } }
			| '-' CTE { cambioCTENegativa($2.sval); $$.sval = "-" + $2.sval;}
;				

sentencia_IF: IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF {if($4.sval=="RET" && $6.sval=="RET"){$$.sval="RET";};completarBifurcacionI();cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
            | IF condicion THEN bloque_unidad ';' END_IF {$$.sval=$4.sval;completarBifurcacionI();cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
			| IF condicion THEN bloque_else ';' END_IF {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN");}
			| IF condicion THEN END_IF {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
			| IF condicion THEN bloque_unidad ';' ELSE END_IF {{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}


			| IF condicion THEN bloque_unidad bloque_else ';' END_IF {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
			| IF condicion THEN bloque_unidad END_IF {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
			| IF condicion THEN bloque_unidad ';' bloque_else END_IF {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
			| IF condicion THEN bloque_unidad bloque_else END_IF {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
			
			| IF condicion THEN bloque_unidad ';' error{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
			| IF condicion THEN bloque_unidad ';' bloque_else ';' error{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
;

condicion	: '(' '(' list_expre ')' comparador '(' list_expre ')' ')' {if($3.ival == $7.ival){$$.ival=$3.ival;modificarPolacaPM($5.sval,$3.ival);cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
			| '(' list_expre ')' comparador '(' list_expre ')' ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
			| '(' '(' list_expre ')' comparador '(' list_expre ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
			|'(' list_expre ')' comparador '(' list_expre ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
			| '(' expresion_arit comparador expresion_arit ')' {$$.ival=1;opCondicion($3.sval); cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
			|  expresion_arit comparador expresion_arit ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
			| '(' expresion_arit comparador expresion_arit  {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
			| expresion_arit comparador expresion_arit  {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
			
			| '(' list_expre ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
			
			|'(' '(' list_expre ')'  '(' list_expre ')' ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
			|'(' '(' list_expre comparador '(' list_expre ')' ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
			|'(' '(' list_expre ')' comparador list_expre ')' ')' {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
;

comparador	: '>' {$$.sval=">";} 
			| MAYORIGUAL {$$.sval=">=";} 
			| '<' {$$.sval="<";} 
			| MENORIGUAL {$$.sval="<=";} 
			| '=' {$$.sval="=";} 
			| DISTINTO {$$.sval="!=";} 
;

bloque_else: bloque_else_simple {if($1.sval=="RET"){$$.sval="RET";};}
			| bloque_else_multiple {if($1.sval=="RET"){$$.sval="RET";};}
;

bloque_else_multiple:	ELSE BEGIN bloque_sent_ejecutables ';' END {if($3.sval=="RET"){$$.sval="RET";};}
					| ELSE BEGIN bloque_sent_ejecutables END {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

bloque_else_simple:	ELSE bloque_sentencia_simple {if($2.sval=="RET"){$$.sval="RET";};}
;

bloque_unidad	: bloque_unidad_simple {if($1.sval=="RET"){$$.sval="RET";};if(esWHILE==false){operacionesIF();}}
				| bloque_unidad_multiple {if($1.sval=="RET"){$$.sval="RET";};if(esWHILE==false){operacionesIF();}}
;		

bloque_unidad_multiple  : BEGIN bloque_sent_ejecutables ';' END {if($2.sval=="RET"){$$.sval="RET";};}
						| BEGIN END {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
						| BEGIN bloque_sent_ejecutables END {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;				

bloque_unidad_simple:  bloque_sentencia_simple {if($1.sval=="RET"){$$.sval="RET";}}
;

bloque_sent_ejecutables	: bloque_sent_ejecutables ';' bloque_sentencia_simple {if($1.sval=="RET" && $3.sval=="RET"){$$.sval="RET";};}
						| bloque_sentencia_simple {if($1.sval=="RET"){$$.sval="RET";};}
						| bloque_sent_ejecutables bloque_sentencia_simple {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
;

bloque_sentencia_simple: sentencia_ejecutable {if($1.sval=="RET"){$$.sval="RET";};}
;

cadena	: CADENAMULTILINEA {cargarCadenaMultilinea($1.sval);GeneradorCodigoIntermedio.addElemento($1.sval);cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: encabezado_WHILE condicion bloque_unidad {operacionesWhile($2.ival);cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
				| encabezado_WHILE condicion error {cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
;	

encabezado_WHILE : WHILE {esWHILE=true;GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}
;

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA {cargarVariables($2.sval,(Tipo)$1.obj," Etiqueta ");GeneradorCodigoIntermedio.BifurcarAGoto($2.sval+AMBITO.toString());cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
				| GOTO error  {cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO ");}
;
/* Tema 19: Pattern Matching*/
//Lo tenemos en cuenta en la regla Condicion
//.................HACIA ARRIBA NO HAY ERRORES..........................

%%	
public static StringBuilder AMBITO = new StringBuilder("$MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>(); 
public static boolean RETORNO = false;
public static boolean RETORNOTHEN = false;
public static boolean RETORNOELSE = false;
public static Map<String,Tipo> tipos = new HashMap<>();
public static boolean esWHILE = false;

	private static void cargarErrorEImprimirlo(String salida) {
		System.out.println("\u001B[31m"+salida+"\u001B[0m");
		try {
			AnalizadorLexico.sintactico.newLine();  // Agregar un salto de línea
			AnalizadorLexico.sintactico.write(" "+salida+" ");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

private static void cargarCadenaMultilinea(String cadena){
	Tipo t = new Tipo("CADENAMULTILINEA");
	tipos.put("CADENAMULTILINEA",t);
	tipos.put("DOUBLE",new Tipo("DOUBLE"));
	cargarVariables(cadena,t," Cadena multilinea ");
}

private static void modificarPolacaPM(String operador, int cantDeOp){
	//GeneradorCodigoIntermedio.addOperadorEnPattMatch(operador,cantDeOp);
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
}

private static void opCondicion(String operador){
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
};

private static void completarBifurcacionAGoto(String id){
	System.out.println("BIFURCACION a GOTO "+id+" pos "+GeneradorCodigoIntermedio.getPos());
	int pos = GeneradorCodigoIntermedio.getGoto(id);
	System.out.println("BIFURCACION a GOTO "+id+" pos "+ pos);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	System.out.println("BIFURCACION a GOTO "+id+" pos "+ pos + " elm "+elm);
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);
} 



private static void operacionesWhile(int cantDeOperandos){
	completarBifurcacionF();
	GeneradorCodigoIntermedio.bifurcarAlInicio();
}

private static void operacionesIF(){
	System.out.println("BIFURCACION POR F");
	int pos = GeneradorCodigoIntermedio.getPila();
	System.out.println(pos);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	System.out.println(elm);
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.bifurcarI();
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void completarBifurcacionF(){
	System.out.println("BIFURCACION POR F");
	int pos = GeneradorCodigoIntermedio.getPila();
	System.out.println(pos);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	System.out.println(elm);
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
}
private static void completarBifurcacionI(){
	System.out.println("BIFURCACION POR I ");
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);
}

private static void cargarParametroFormal(String id,Tipo t){
	AnalizadorLexico.TablaDeSimbolos.get(id+AMBITO.toString()).setTipoParFormal(t.getType());
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
	System.out.println(" el tipo es " + t.toString());
	if(t.esSubTipo()){
		cargarErrorEImprimirlo( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirlo( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		double mini = Double.valueOf(min);
		double maxi = Double.valueOf(max);
		tipos.put(name,new Tipo(t.getType(),mini,maxi));
	}
	return tipos.get(name);
}

private static Tipo cargarTripla(String name, Tipo t, boolean tripla){
	System.out.println(" el tipo es " + t.toString());
	if(t.esSubTipo()){
		cargarErrorEImprimirlo( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirlo( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		tipos.put(name,new Tipo(t.getType(),tripla));
	}
	return tipos.get(name);
}

private static boolean fueDeclarado(String id){
	System.out.println("  > Buscando la declaracion < ");
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    while (true) {
        // Construimos la clave: id + ámbito actual
        String key = id + ambitoActual;
		//System.out.println("  > Key buscada "+ key + "En el ambito "+ ambitoActual);

        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el ámbito: eliminamos el último ':NIVEL'
        int pos = ambitoActual.lastIndexOf("$");
        if (pos == -1) {
            break; // Si ya no hay más ámbitos, salimos del ciclo
        }

        // Reducimos el ámbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

    // Si no se encuentra en ningún ámbito, lanzamos un error o devolvemos null
    throw new RuntimeException( "Linea :" + AnalizadorLexico.saltoDeLinea +"Error: ID '" + id + "' no encontrado en ningún ámbito.");
}
private static boolean existeEnEsteAmbito(String id){
	//System.out.println("  > Buscando la declaracion < ");
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    // Construimos la clave: id + ámbito actual
    String key = id + ambitoActual;
		System.out.println(" Se busca la existencia de "+ key);
	//System.out.println("  > Key buscada "+ key + "En el ambito "+ ambitoActual);

    // Buscamos en el mapa
    if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
        return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
    }
	return false;
}

private static void cargarVariables(String variables, Tipo tipo, String uso){
	System.out.println(variables);
	String[] var = getVariables(variables,"/");
	for (String v : var) {
		if(!existeEnEsteAmbito(v)){
			if(tipo.getType()=="CADENAMULTILINEA"){
				addTipo(v,tipo);
				addUso(v,uso);
				declarar(v);
			}else{
				addAmbitoID(v);
				addTipo(v+AMBITO.toString(),tipo);
				addUso(v+AMBITO.toString(),uso);
				declarar(v+AMBITO.toString());
			}
		}else{
			cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +"  La variable  " + v + " ya fue declarada.");
		}
    }

}
private static void declarar(String id){
	AnalizadorLexico.TablaDeSimbolos.get(id).fueDeclarada();
};
private static void addUso(String id,String uso){
	AnalizadorLexico.TablaDeSimbolos.get(id).setUso(uso);
};

private static String[] getVariables(String var, String separador) {
    // Verifica si el string contiene '/'
    if (!var.contains(separador)) {
        // Retorna un arreglo vacío o el string completo como único elemento
        return new String[] { var };
    }
    // Usa split() para dividir el String por el carácter '/'
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
	System.out.println(" add ambito ID :"+ id +" "+ AnalizadorLexico.TablaDeSimbolos.get(id).toString());
	AnalizadorLexico.TablaDeSimbolos.get(id).agregarAmbito(AMBITO.toString());
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
		System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " se reconocio token negativo ");
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
