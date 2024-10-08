%{
%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE ASIGNACION ERROR
%start programa

%% /* Gramatica */
									/* PROGRAMA */
																		
programa	: ID_simple BEGIN sentencias END {System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ $1.sval +"\u001B[0m");}	
			| BEGIN sentencias END {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el nombre del programa "+"\u001B[0m");}
			| ID_simple BEGIN sentencias {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el delimitador END "+"\u001B[0m");}
			| ID_simple sentencias END {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el delimitador BEGIN "+"\u001B[0m");}
			| ID_simple sentencias {System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Faltan los delimitadores del programa "+"\u001B[0m");}
;
	
sentencias 	: sentencias sentencia ';'
			| sentencia ';'
			| sentencia {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia "+"\u001B[0m");}
;

sentencia 	: sentencia_declarativa 
            | sentencia_ejecutable
;			
				
									/* SENTENCIAS DECLARATIVAS */	
				
sentencia_declarativa 	: declaracion_variable
						| declaracion_funciones
                        | declaracion_subtipo
;

declaracion_variable	: tipo variables  {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
;

tipo : ID_simple 
		|tipo_primitivo
;	

tipo_primitivo: INTEGER
				|DOUBLE
;

declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '{' en el rango "+"\u001B[0m");}
                    | TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '}' en el rango "+"\u001B[0m");}
                    | TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
                    | TYPEDEF TRIPLE '<' tipo '>' ID_simple  {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango inferior "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta alguno de los rangos "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango superior "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION tipo '{' '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos rangos "+"\u001B[0m");}
					|TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
					| TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
					| TYPEDEF '<' tipo '>' ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
					| TYPEDEF TRIPLE tipo '>' ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE '<' tipo ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE tipo ID_simple {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
					| TYPEDEF TRIPLE '<' tipo '>' error {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta identificador al final de la declaracion"+"\u001B[0m");}			
;

declaracion_funciones     : tipo FUN ID parametros_parentesis BEGIN cuerpo_funcion END {if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); }
                        | tipo FUN parametros_parentesis BEGIN cuerpo_funcion END {if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion "+"\u001B[0m");}
;

parametros_parentesis: '(' parametro ')'
                    | '(' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
                    | '(' error ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion. "+"\u001B[0m");}
;

parametro	: tipo ID_simple	
;

cuerpo_funcion	: sentencias 
;

retorno	: RET '(' expresion_arit ')'
;
									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF 
						| sentencia_WHILE
						| sentencia_goto
						| ETIQUETA {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
						| outf_rule
						| retorno {RETORNO = true;}
;

outf_rule    : OUTF '(' expresion_arit ')' {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
            | OUTF '(' ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
            | OUTF '(' cadena ')'    {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
            | OUTF '(' sentencias ')'  {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
;

asignacion	: variable_simple ASIGNACION expresion_arit {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
			| variable_simple '{' CTE '}' ASIGNACION expresion_arit  {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
;

invocacion	: ID_simple '(' expresion_arit ')' {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
			| ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')' {System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
			| ID_simple '(' ')' {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
;

list_expre	: list_expre ',' expresion_arit
			| expresion_arit 
;

expresion_arit  : expresion_arit '+' termino 
                | expresion_arit '-' termino 
                | termino
				| error {System.out.println("\u001B[31m"+"La expresion está mal escrita "+"\u001B[0m");} 
;

termino : termino '*' factor 
        | termino '/' factor 
        | factor
;

factor 	: variable_simple
		| CTE_con_sig
		| invocacion
		| variable_simple '{' CTE '}' 		 
;

variables 	: variables ',' variable_simple  
			| variable_simple
;		

variable_simple : ID_simple
;

ID_simple : ID
;


CTE_con_sig : CTE {if(estaRango($1.sval)) { $$.sval = $1.sval; } }
			| '-' CTE { cambioCTENegativa($2.sval); $$.sval = "-" + $2.sval;}
			| ERROR 
			| '-' ERROR 		
;				

sentencia_IF: IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
            | IF condicion THEN bloque_unidad ';' END_IF {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
            | IF condicion THEN bloque_unidad ';' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
            | IF condicion THEN bloque_unidad ';' bloque_else ';' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
			| IF condicion THEN bloque_else ';' END_IF {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  ": Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
			| IF condicion THEN END_IF {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
			| IF condicion THEN bloque_unidad ';' ELSE END_IF {{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
;

condicion	: '(' '(' list_expre ')' comparador '(' list_expre ')' ')' {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
			| '(' list_expre ')' comparador '(' list_expre ')' ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
			| '(' '(' list_expre ')' comparador '(' list_expre ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
			|'(' list_expre ')' comparador '(' list_expre ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
			| '(' expresion_arit comparador expresion_arit ')' {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
			|  expresion_arit comparador expresion_arit ')' {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
			| '(' expresion_arit comparador expresion_arit  {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
			| expresion_arit comparador expresion_arit  {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
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

bloque_unidad_multiple  : BEGIN bloque_sent_ejecutables END	
;

bloque_unidad_simple:  bloque_sentencia_simple
;

bloque_sent_ejecutables	: bloque_sent_ejecutables ';' bloque_sentencia_simple 
						| bloque_sentencia_simple
;

bloque_sentencia_simple: sentencia_ejecutable  
;

cadena	: CADENAMULTILINEA {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
;

									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: WHILE condicion bloque_unidad {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
				| WHILE condicion error {System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
;	


/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA {System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
				| GOTO error  {System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
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
	String keyNeg = "-" + key;
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
private static boolean estaRango(String key) {
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
		if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
				if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
					AnalizadorLexico.TablaDeSimbolos.remove(key);
				}
			yyerror("\u001B[31m"+"Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango."+"\u001B[0m");
			return false;
		}
	}
	return true;
}
