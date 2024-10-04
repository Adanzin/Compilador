%{
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION
%start programa

%% /* Gramatica */
									/* PROGRAMA */
									
programa 	: ID cuerpo_programa {System.out.println(" Fin de a compilacion ");}	
			| cuerpo_programa {System.out.println(" Error en la linea " + AnalizadorLexico.saltoDeLinea + ": Falta nombre del programa ");}		
									
cuerpo_programa 	: BEGIN sentencias END {System.out.println(" Se identifico el cuerpo_programa");}	
					| sentencias END {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador BEGIN ");}
					| BEGIN sentencias {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador END ");}
					| sentencias {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan los delimitadores del programa ");}
;
	
sentencias 	: sentencias sentencia ';'
			| sentencia ';'
			| sentencia {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia ");}
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

declaracion_funciones 	: tipo FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END {if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");RETORNO=false;}}
						| tipo FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
						| ID FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END	{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico una funcion de <Class> ");}
						| ID FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END {System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
;

parametros_formal	: parametros_formal parametro ','
					| parametro
;

parametro	: tipo ID	
;

cuerpo_funcion	: sentencias 
;

retorno	: RET '(' expresion_arit ')'
									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF 
						| sentencia_WHILE
						| sentencia_goto
						| OUTF '(' expresion_arit ')' {System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
						| OUTF '(' cadena ')'	{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
						| retorno {RETORNO = true;}
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

CTE_con_sig : CTE {if(estaRango($1.sval)) { $$.sval = $1.sval; } }
			| '-' CTE { cambioCTENegativa($2.sval); $$.sval = "-" + $2.sval;}
;				
        		
sentencia_IF: IF '(' condicion ')' THEN bloque_unidad ';' bloque_else ';' END_IF
			| IF '(' condicion ')' THEN bloque_unidad ';' END_IF
;

condicion	:'(' list_expre ')' comparador '(' list_expre ')'//Tenemos en cuenta el pattern_matching 
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

bloque_else_simple:	ELSE bloque_sentencia_simple
;

bloque_unidad	: bloque_unidad_simple
				| bloque_unidad_multiple
;		

bloque_unidad_multiple: BEGIN bloque_sent_ejecutables END			

bloque_unidad_simple:  bloque_sentencia_simple
;

bloque_sent_ejecutables	: bloque_sent_ejecutables bloque_sentencia_simple
						| bloque_sentencia_simple
;

bloque_sentencia_simple: sentencia_ejecutable 
;



cadena	: CADENAMULTILINEA {System.out.println(" > Se leyo la cadena multi linea < ");}
		| '[' ']'
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: WHILE '(' condicion ')' bloque_unidad
;	

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA
;

/* Tema 19: Pattern Matching*/
//Lo tenemos en cuenta en la regla Condicion
//.................HACIA ARRIBA NO HAY ERRORES..........................


%%																	 
private static boolean RETORNO = false;
private static int cantRETORNOS = 0;

int yylex() {
	int tokenSalida = AnalizadorLexico.getToken();
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	if(tokenSalida==0) {
		return AnalizadorLexico.siguienteLectura(AnalizadorLexico.archivo_original,' ');
	}
	return tokenSalida;
}
private static void yyerror(String string) {
	System.out.println(string);
}

private static void cambioCTENegativa(String key) {
	System.out.println(" La key es " + key);
	String keyNeg = "-" + key;
	if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
		AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
	}
	AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
	System.out.println("En la linea " + AnalizadorLexico.saltoDeLinea + " se reconocio token negativo ");
	// es ultimo ya decrementa
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
		AnalizadorLexico.TablaDeSimbolos.remove(key);
	}
}
private static boolean estaRango(String key) {
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
		if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
			AnalizadorLexico.TablaDeSimbolos.remove(key);
			yyerror("La CTE de la linea " + AnalizadorLexico.saltoDeLinea + " está fuera de rango.");
			return false;
		}
	}
	return true;
}
