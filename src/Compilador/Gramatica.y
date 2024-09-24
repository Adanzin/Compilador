%{

%}
%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA WHILE TRIPLE GOTO ETIQUETA MAYORIGUAL MENORIGUAL DISTINTO INTEGER DOUBLE
%start programa

%% /* Gramatica */
									/* PROGRAMA */
									
programa 	: nombre BEGIN sentencias ';' END
			| nombre BEGIN sentencias END {System.out.println(" Falta el ; al final de la sentencia")}
         	| nombre BEGIN END {System.out.println(" Se esperaba una sentencia")}
         	| nombre sentencias ';' END {System.out.println(" Se esperaba el BEGIN al iniciar el programa")}
         	| nombre BEGIN sentencias ';' {System.out.println(" Se esperaba un END al final del programa")}
;
	
sentencias 	: sentencias sentencia ';'
			| sentencias sentencia {System.out.println(" Se esperaba ";" al final de la sentencia");}
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
	 | ID /* si es una clase, un subtipo */
;

variables 	: variables ',' ID  
			| ID
;		

declaracion_subtipo : TYPEDEF ID ':=' tipo '{' CTE ',' CTE '}'
					| TYPEDEF TRIPLE '<' tipo '>' ID
;							
	
declaracion_funciones 	: tipo FUN ID parametros_formal BEGIN cuerpo_funcion END
						: CTE FUN ID parametros_formal BEGIN cuerpo_funcion END
						| tipo FUN ID BEGIN cuerpo_funcion END {System.out.println(" La declaracion de una funcion debe poseer parametros")}
;

parametros_formal	: parametros_formal parametro ','
					| parametro
;

parametro	: tipo ID
			| tipo {System.out.println("Se espera un identificador despues del tipo.");}
			| ID {System.out.println("Se esperaba el tipo del identificador.");}			
;

cuerpo_funcion	: sentencias RET '(' ID ')'
				| sentencias RET '(' expresion ')'
				| sentencias
;

									/* SENTENCIAS EJECUTABLES */

sentencia_ejecutable	: asignacion
						| sentencia_IF
						| sentencia_WHILE
						| sentencia_goto
						| OUTF '(' expresion_arit ')'
						| OUTF '(' cadena ')'
;

/* USAMOS { en vez de [ para los arreglos asi no nos lo considera como cadena */
asignacion	: ID ':=' expresion_arit
			| ID '{' CTE '}' ':=' expresion_arit 
;

expresion_arit	: expresion_arit operacion expresion_arit
				| operador
;

operacion	: '+'
			| '-'
			| '*'
			| '/'
;

operando 	: ID
			| CTE
			| invocacion
			| '{' CTE '}'
;			



invocacion	: ID '(' parametro_real ')' 
;

parametro_real	: parametro_real ',' parametro_real
				| expresion_arit
				| CTE
				| variables
;

sentencia_IF: IF '(' condicion ')' THEN bloque_sent_ejecutables ELSE bloque_sent_ejecutables END_IF 
			| IF condicion THEN bloque_sentencias END_IF 
;

condicion	: operando comparador operando
			| pattern_matching
;

comparador	: '>'
			| '>='
			| '<'
			| '<='
			| '='
			| '!='			
;			

bloque_sentencias	: BEGIN bloques_sent_ejecutables END ';'
                	| BEGIN END ';' { System.out.println("Se esperaban sentencias entre BEGIN y END"); }	
;

bloques_sent_ejecutables	: bloques_sent_ejecutables sentencia_ejecutable
							| sentencia_ejecutable
;

cadena	: '[' CADENAMULTILINEA ']'
;
     						
									/* TEMAS PARTICULARES */
/* Temas 13:  Sentencias de Control */
sentencia_WHILE	: WHILE '(' condicion ')' bloques_sent_ejecutables
;							

/* Tema 19: Pattern Matching */
pattern_matching	: list_expre comparador list_expre
;

list_expre	: list_expre ',' list_expre
				| expresion_arit
				| CTE
				| variables
;					

/* Tema 23: goto */
sentencia_goto	: GOTO ETIQUETA
;




	

				
																		 
%%

	public static int yylex(Reader lector) {
		return AnalizadorLexico.getToken(lector);
	}
		

