%{
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION ERROR
%start programa

%% /* Gramatica */
									/* PROGRAMA */
																		
programa	: ID_simple BEGIN sentencias END {System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ $1.sval +"\u001B[0m");}
			| ID_simple BEGIN END {System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ $1.sval +"\u001B[0m");}	
			| BEGIN sentencias END {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el nombre del programa "+"\u001B[0m");}
			| ID_simple BEGIN sentencias {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador END "+"\u001B[0m");}
			| ID_simple sentencias END {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador BEGIN "+"\u001B[0m");}
			| ID_simple sentencias {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Faltan los delimitadores del programa "+"\u001B[0m");}
;
	
sentencias 	: sentencias sentencia
			| sentencia
;

sentencia 	: sentencia_declarativa 
			| error ';' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  "+"\u001B[0m");}
            | sentencia_ejecutable ';'
			| sentencia_ejecutable {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;			
				
									/* SENTENCIAS DECLARATIVAS */	
				
sentencia_declarativa 	: declaracion_variable
						| declaracion_funciones ';'
                        | declaracion_subtipo ';'
						| declaracion_funciones {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
						| declaracion_subtipo {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;

declaracion_variable	: tipo variables ';' {cargarVariables($2.sval,(Tipo)$1.obj," nombre de variable "); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
						| tipo variables error {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;

tipo : ID_simple { if(tipos.containsKey($1.sval)){$$.obj = tipos.get($1.sval);
					}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido "+"\u001B[0m");};}
	 |tipo_primitivo { $$.obj = $1.obj;  }
;	

tipo_primitivo: INTEGER { if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							$$.obj = tipos.get("INTEGER");}
              | DOUBLE  { if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							$$.obj = tipos.get("DOUBLE");}
;

declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {if($4.obj != null){cargarVariables($2.sval,cargarSubtipo($2.sval,(Tipo)$4.obj,$6.sval,$8.sval)," nombre de SubTipo ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
                    | TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
                    | TYPEDEF TRIPLE '<' tipo '>' ID_simple  {if($4.obj != null){cargarVariables($6.sval,cargarTripla($6.sval,(Tipo)$4.obj,true)," nombre de Triple ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
					| TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
					|TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
					| TYPEDEF '<' tipo '>' ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
					| TYPEDEF TRIPLE tipo '>' ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE '<' tipo ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE tipo ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE '<' tipo '>' error {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}			
;

declaracion_funciones     : encabezado_funcion parametros_parentesis BEGIN cuerpo_funcion END 
								{	if(RETORNO==false)
										{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); 
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal($1.sval,(Tipo)$2.obj);
									
								}                  
;

encabezado_funcion 	: tipo FUN ID {$$.sval=$3.sval;cargarFuncion($3.sval,(Tipo)$1.obj," nombre de funcion ");agregarAmbito($3.sval);DENTRODELAMBITO.push($3.sval);System.out.println(" Encabezado de la funcion ");}
					| tipo FUN {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
;

parametros_parentesis: '(' tipo_primitivo ID_simple ')' {$$.obj=$2.obj;cargarVariables($3.sval,(Tipo)$2.obj," nombre de parametro real ");}
					| '(' tipo_primitivo ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");} 
					| '(' ID_simple ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
                    | '(' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
                    | '(' error ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
;

cuerpo_funcion	: sentencias 
;

retorno	: RET '(' expresion_arit ')' {if(!existeFuncion()){System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");}}
		| RET '('  ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  "+"\u001B[0m");
						if(!existeFuncion())
										{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");
						}}
;
									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF 
						| sentencia_WHILE
						| sentencia_goto
						| ETIQUETA {if(existeEnEsteAmbito($1.sval)){completarBifurcacionAGoto($1.sval+AMBITO.toString());System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA que se pretende bifurcar no existe.  "+"\u001B[0m");}}
						| outf_rule
						| retorno {RETORNO = true;}
;

outf_rule    : OUTF '(' expresion_arit ')' {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
            | OUTF '(' ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
            | OUTF '(' cadena ')'    {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
            | OUTF '(' sentencias ')'  {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
;

asignacion	: variable_simple ASIGNACION expresion_arit {if(fueDeclarado($1.sval)){
															$$.sval = $1.sval;
															GeneradorCodigoIntermedio.addElemento($1.sval);
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");}
															System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| variable_simple '{' CTE '}' ASIGNACION expresion_arit  {if(fueDeclarado($1.sval)){
															$$.sval = $1.sval;
															GeneradorCodigoIntermedio.addElemento($1.sval);
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");}
																System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
			| variable_simple '{' '-' CTE '}' ASIGNACION expresion_arit {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
;

invocacion	: ID_simple '(' expresion_arit ')' {if(!fueDeclarado($1.sval)){
													System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}
													else{														
														System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");
														GeneradorCodigoIntermedio.bifurcarIporFuncion($1.sval + AMBITO.toString());}																										
												}
			| ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')' 
												{if(!fueDeclarado($1.sval)){
													System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}
													else{
														System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");
														GeneradorCodigoIntermedio.bifurcarIporFuncion($1.sval + AMBITO.toString());}
												}
			| ID_simple '(' ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
			| ID_simple '(' error ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
;

list_expre	: list_expre ',' expresion_arit
			| ',' expresion_arit {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
			| list_expre ','  {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
			| expresion_arit 
;

expresion_arit  : expresion_arit '+' termino {GeneradorCodigoIntermedio.addElemento("+"); }
                | expresion_arit '-' termino {GeneradorCodigoIntermedio.addElemento("-"); }
                | termino
				| error '+' error{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
				| error '-' error{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
				| expresion_arit '+' error {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
				| expresion_arit '-' error {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
				| error termino {GeneradorCodigoIntermedio.imprimirPolaca();System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
;

termino : termino '*' factor {GeneradorCodigoIntermedio.addElemento("*");} 
        | termino '/' factor {GeneradorCodigoIntermedio.addElemento("/");}
        | factor 
		| error '*' error{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
		| error '/' error{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
		| termino '*' error {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
		| termino '/' error {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
;

factor 	: variable_simple {if(fueDeclarado($1.sval)){GeneradorCodigoIntermedio.addElemento($1.sval);AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
		| CTE_con_sig {GeneradorCodigoIntermedio.addElemento($1.sval);}
		| invocacion 
		| variable_simple '{' CTE '}' {if(fueDeclarado($1.sval)){ GeneradorCodigoIntermedio.addElemento($1.sval+"["+$3.sval+"]");AnalizadorLexico.TablaDeSimbolos.get($1.sval).incrementarContDeRef(); $$.sval = $1.sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
		| variable_simple '{' '-' CTE '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
;
variables 	: variables ',' variable_simple { $$.sval = $1.sval + "/"+$3.sval;} 
			| variables variable_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
			| variable_simple {$$.sval = $1.sval;}
;

variable_simple : ID_simple 
;

ID_simple : ID 
;


CTE_con_sig : CTE {if(estaRango($1.sval)) { $$.sval = $1.sval; } }
			| '-' CTE { cambioCTENegativa($2.sval); $$.sval = "-" + $2.sval;}
;				

sentencia_IF: IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF {completarBifurcacionI();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
            | IF condicion THEN bloque_unidad ';' END_IF {completarBifurcacionI();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
			| IF condicion THEN bloque_else ';' END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
			| IF condicion THEN END_IF {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
			| IF condicion THEN bloque_unidad ';' ELSE END_IF {{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}


			| IF condicion THEN bloque_unidad bloque_else ';' END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN "+"\u001B[0m");}
			| IF condicion THEN bloque_unidad END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  "+"\u001B[0m");}
			| IF condicion THEN bloque_unidad ';' bloque_else END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  "+"\u001B[0m");}
			| IF condicion THEN bloque_unidad bloque_else END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
			
			| IF condicion THEN bloque_unidad ';' error{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
			| IF condicion THEN bloque_unidad ';' bloque_else ';' error{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
;

condicion	: '(' '(' list_expre ')' comparador '(' list_expre ')' ')' {opCondicion($5.sval);System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
			| '(' list_expre ')' comparador '(' list_expre ')' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
			| '(' '(' list_expre ')' comparador '(' list_expre ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
			|'(' list_expre ')' comparador '(' list_expre ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
			| '(' expresion_arit comparador expresion_arit ')' {opCondicion($3.sval); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
			|  expresion_arit comparador expresion_arit ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
			| '(' expresion_arit comparador expresion_arit  {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
			| expresion_arit comparador expresion_arit  {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
			
			| '(' list_expre ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion "+"\u001B[0m");}
			
			|'(' '(' list_expre ')'  '(' list_expre ')' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
			|'(' '(' list_expre comparador '(' list_expre ')' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
			|'(' '(' list_expre ')' comparador list_expre ')' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
;

comparador	: '>' {$$.sval=">";} 
			| MAYORIGUAL {$$.sval=">=";} 
			| '<' {$$.sval="<";} 
			| MENORIGUAL {$$.sval="<=";} 
			| '=' {$$.sval="=";} 
			| DISTINTO {$$.sval="!=";} 
;

bloque_else: bloque_else_simple 
			| bloque_else_multiple
;

bloque_else_multiple:	ELSE BEGIN bloque_sent_ejecutables ';' END
					| ELSE BEGIN bloque_sent_ejecutables END {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;

bloque_else_simple:	ELSE bloque_sentencia_simple 
;

bloque_unidad	: bloque_unidad_simple {if(esWHILE==false){operacionesIF();}}
				| bloque_unidad_multiple {if(esWHILE==false){operacionesIF();}}
;		

bloque_unidad_multiple  : BEGIN bloque_sent_ejecutables ';' END
						| BEGIN END {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
						| BEGIN bloque_sent_ejecutables END {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;				

bloque_unidad_simple:  bloque_sentencia_simple 
;

bloque_sent_ejecutables	: bloque_sent_ejecutables ';' bloque_sentencia_simple 
						| bloque_sentencia_simple
						| bloque_sent_ejecutables bloque_sentencia_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
;

bloque_sentencia_simple: sentencia_ejecutable
;

cadena	: CADENAMULTILINEA {addUso($1.sval, " Es una Cadena MultiLinea ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: encabezado_WHILE condicion bloque_unidad {operacionesWhile();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
				| encabezado_WHILE condicion error {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
;	

encabezado_WHILE : WHILE {esWHILE=true;GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());}
;

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA {cargarVariables($2.sval,(Tipo)$1.obj," Etiqueta ");GeneradorCodigoIntermedio.BifurcarAGoto($2.sval+AMBITO.toString());System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
				| GOTO error  {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
;
/* Tema 19: Pattern Matching*/
//Lo tenemos en cuenta en la regla Condicion
//.................HACIA ARRIBA NO HAY ERRORES..........................

%%	
public static StringBuilder AMBITO = new StringBuilder(":MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>();
public static boolean RETORNO = false;
public static Map<String,Tipo> tipos = new HashMap<>();
public static boolean esWHILE = false;

private static void completarBifurcacionAGoto(String id){
	System.out.println("BIFURCACION a GOTO "+id+" pos "+GeneradorCodigoIntermedio.getPos());
	int pos = GeneradorCodigoIntermedio.getGoto(id);
	System.out.println("BIFURCACION a GOTO "+id+" pos "+ pos);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	System.out.println("BIFURCACION a GOTO "+id+" pos "+ pos + " elm "+elm);
	GeneradorCodigoIntermedio.addElemento(elm,pos);
} 

private static void cargarFuncion(String variable, Tipo tipo, String uso){
	cargarVariables(variable,tipo,uso);
	AnalizadorLexico.TablaDeSimbolos.get(variable+AMBITO.toString()).setDirMEM(GeneradorCodigoIntermedio.getPos());
}

private static void opCondicion(String val){
	GeneradorCodigoIntermedio.addElemento(val);
	GeneradorCodigoIntermedio.bifurcarF();
}

private static void operacionesWhile(){
	completarBifurcacionF();
	GeneradorCodigoIntermedio.bifurcarAlInicio();
}

private static void operacionesIF(){
	//desapilar
	//Completar el paso imcompleto con el destino de la BF
	completarBifurcacionF();
	// generar los pasos de la BI incompleta
	GeneradorCodigoIntermedio.bifurcarI();
	//Apilar el numero del paso imcompleto	

}

private static void completarBifurcacionF(){
	System.out.println("BIFURCACION POR F");
	int pos = GeneradorCodigoIntermedio.getPila();
	System.out.println(pos);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	System.out.println(elm);
	GeneradorCodigoIntermedio.addElemento(elm,pos);
}
private static void completarBifurcacionI(){
	System.out.println("BIFURCACION POR I ");
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	GeneradorCodigoIntermedio.addElemento(elm,pos);
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
		System.out.println(" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		System.out.println(" No se puede declarar un subTipo de un tipo definido por el usuario ");
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
		System.out.println(" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		System.out.println(" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		tipos.put(name,new Tipo(t.getType(),tripla));
	}
	return tipos.get(name);
}

private static boolean fueDeclarado(String id){
	//System.out.println("  > Buscando la declaracion < ");
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
        int pos = ambitoActual.lastIndexOf(":");
        if (pos == -1) {
            break; // Si ya no hay más ámbitos, salimos del ciclo
        }

        // Reducimos el ámbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

    // Si no se encuentra en ningún ámbito, lanzamos un error o devolvemos null
    throw new RuntimeException("\u001B[31m"+ "Linea :" + AnalizadorLexico.saltoDeLinea +"Error: ID '" + id + "' no encontrado en ningún ámbito."+"\u001B[0m");
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
			addAmbitoID(v);
			addTipo(v+AMBITO.toString(),tipo);
			addUso(v+AMBITO.toString(),uso);
			declarar(v+AMBITO.toString());
		}else{
			System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +"  La variable  " + v + " ya fue declarada."+"\u001B[0m");
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
	AMBITO.append(":").append(amb);
}

private static void sacarAmbito() {
	// agarro el index del ultimo ':'
	int pos = AMBITO.lastIndexOf(":");
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
				yyerror("\u001B[31m"+"Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango."+"\u001B[0m");
				return false;
			}
		}
	}
	return true;
}
