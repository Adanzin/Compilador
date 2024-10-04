//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Gramatica.y"
package Compilador;
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
//#line 25 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short BEGIN=260;
public final static short END=261;
public final static short END_IF=262;
public final static short OUTF=263;
public final static short TYPEDEF=264;
public final static short FUN=265;
public final static short RET=266;
public final static short CTE=267;
public final static short ID=268;
public final static short CADENAMULTILINEA=269;
public final static short WHILE=270;
public final static short TRIPLE=271;
public final static short GOTO=272;
public final static short ETIQUETA=273;
public final static short MAYORIGUAL=274;
public final static short MENORIGUAL=275;
public final static short DISTINTO=276;
public final static short INTEGER=277;
public final static short DOUBLE=278;
public final static short ASIGNACION=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    3,    3,    3,    5,
    8,    8,    7,    7,    6,    6,   11,   11,   13,   12,
   14,    4,    4,    4,    4,    4,    4,    4,   16,   16,
   21,   21,   22,   23,   23,   15,   15,   15,   24,   24,
   24,   25,   25,   25,   25,    9,    9,   10,   10,   17,
   17,   26,   29,   29,   29,   29,   29,   29,   28,   28,
   31,   30,   27,   27,   35,   34,   32,   32,   33,   20,
   20,   18,   19,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    1,    1,    1,    1,    1,    2,
    1,    1,    9,    6,    9,    9,    3,    1,    2,    1,
    4,    1,    1,    1,    1,    4,    4,    1,    3,    6,
    4,    5,    1,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    3,    1,    1,    2,   10,
    8,    7,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    2,    1,    1,    3,    1,    2,    1,    1,    1,
    2,    5,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   11,   12,    0,    0,    5,    6,    7,    8,    9,    0,
   28,   22,   23,   24,   25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   73,    2,    0,    4,    0,
   47,    0,    0,    0,    0,   48,    0,   70,    0,    0,
   43,    0,    0,   44,    0,   41,    0,    0,    0,    0,
    0,    0,    0,    3,    0,    0,    1,    0,    0,    0,
    0,    0,   49,   71,   26,    0,    0,   27,    0,    0,
    0,    0,   21,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,   40,    0,
    0,    0,    0,   18,    0,    0,    0,   69,   72,   66,
   63,   64,    0,    0,   54,   56,   58,   55,   53,   57,
    0,    0,   45,    0,   31,    0,   14,   19,    0,    0,
    0,    0,   68,    0,    0,    0,   32,    0,    0,   17,
   65,   67,    0,    0,    0,   51,    0,   59,   60,    0,
    0,    0,    0,   52,    0,   62,    0,   13,   16,   15,
    0,   50,   61,
};
final static short yydgoto[] = {                          3,
  151,   14,   15,   16,   17,   18,   19,   20,   42,   51,
  103,  152,  104,   21,   68,   22,   23,   24,   25,   53,
   54,   94,   69,   55,   56,   45,  109,  147,  121,  148,
  149,  132,  110,  111,  112,
};
final static short yysindex[] = {                      -237,
 -121, -227,    0,   23,   25, -246,   47, -113,   58, -233,
    0,    0, -162,   12,    0,    0,    0,    0,    0, -179,
    0,    0,    0,    0,    0, -121,   60,  -40, -210,   52,
  -42, -191,  -42, -148,   60,    0,    0,   62,    0, -140,
    0,   82, -139,  -42,   89,    0,  -27,    0, -119,   53,
    0,   40,  114,    0,   31,    0, -185, -185,   64,  118,
   -4,   38,  123,    0,  131,  -92,    0,   -4,   70,  -81,
  -86,  -44,    0,    0,    0,  -42,  -42,    0,  -42,  -42,
   56,  125,    0, -185,  -94,  -98, -185,    0,  -42,  -54,
  -98,   63, -185,  149,  155,   31,   31,    0,    0,  -36,
  -75,  -67,  -41,    0,  -42,   37, -112,    0,    0,    0,
    0,    0,  -39,   -4,    0,    0,    0,    0,    0,    0,
  163,  146,    0,  -37,    0,  164,    0,    0,  -53,  165,
   -4,  -88,    0,  -50,  -42, -142,    0,  -36, -121,    0,
    0,    0, -121,   91,  -74,    0,  152,    0,    0,   87,
 -121,  -48,  -47,    0,   37,    0,  -46,    0,    0,    0,
  -66,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  156,    0,    0,    0,    0,  -15,    0,    0,    0,
    0,    0,    0,    0,   -7,    0,    0,    0,    0,    0,
   21,    0,    0,    0,    0,    0,    0,   96,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    5,   13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -43,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   17,   -1,    0,  -71,    0,    0,    0,   10,    0,  -76,
  -34,   76,  -58,    0,  -14,    0,    0,    0,    0,    0,
    0,    0,  -56,   77,   81,  195,  141,    0,    0,    0,
    0,   80,  -85,    0,    0,
};
final static int YYTABLESIZE=309;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        129,
   49,  134,   49,  137,   49,  118,  120,  119,   49,   34,
   34,   38,   72,   52,  108,   95,   59,   13,   61,  108,
  133,   29,    1,  126,   30,   42,   42,   42,   42,   42,
    2,   42,   26,   38,  108,   38,   38,   38,   76,   36,
   77,   38,   43,   42,  130,   36,  142,   36,   36,   36,
   50,   38,  113,   37,  130,   37,   37,   37,  124,  156,
  108,  150,   27,   36,   28,  130,   81,   82,   57,  133,
   39,   37,   79,  108,  114,  142,   60,   80,  144,   29,
   75,   93,   76,  108,   77,   40,   31,   30,   41,  108,
  131,   11,   12,  102,    4,   71,  102,   35,   37,   44,
    5,    6,  102,    7,   83,    8,   76,    9,   77,   10,
   90,   58,  102,   89,   11,   12,  145,    4,   62,  146,
   64,   67,  102,    5,    6,   66,    7,   65,    8,   70,
    9,  154,   10,  102,   89,    4,   35,   11,   12,   35,
   34,    5,    6,   34,    7,   74,    8,   73,    9,   38,
   10,   32,   96,   97,   78,   11,   12,   84,    4,   98,
   99,  106,   85,   86,    5,   33,   33,    7,    4,  107,
   87,    9,  141,   10,    5,   88,   91,    7,  100,  107,
   92,    9,    4,   10,  105,  155,  101,  123,    5,  125,
    4,    7,  127,  107,  163,    9,    5,   10,   89,    7,
  128,  107,  135,    9,  136,   10,  139,  138,  140,  143,
  157,  158,  159,  160,   10,  162,   33,   20,  153,  115,
  116,  117,   46,   47,   46,   47,   46,   47,   48,   63,
   46,  122,   11,   12,  161,   11,   12,   11,   12,   11,
   12,   42,    0,    0,    0,   42,    0,   42,    0,   38,
   42,    0,   42,   38,   42,   38,   42,    0,   38,    0,
   38,   36,   38,    0,   38,   36,    0,   36,    0,   37,
   36,    0,   36,   37,   36,   37,   36,   29,   37,    0,
   37,   29,   37,   29,   37,   30,   29,    0,   29,   30,
   29,   30,   29,    4,   30,    0,   30,    0,   30,    5,
   30,    0,    7,    0,  107,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   45,   41,   45,   60,   61,   62,   45,  123,
  123,   13,   40,   28,   86,   72,   31,    1,   33,   91,
  106,  268,  260,  100,  271,   41,   42,   43,   44,   45,
  268,   47,  260,   41,  106,   43,   44,   45,   43,  273,
   45,   43,   26,   59,  103,   41,  132,   43,   44,   45,
   91,   59,   87,   41,  113,   43,   44,   45,   93,  145,
  132,  138,   40,   59,   40,  124,   57,   58,  279,  155,
   59,   59,   42,  145,   89,  161,  268,   47,  135,   59,
   41,   72,   43,  155,   45,  265,   40,   59,  268,  161,
  105,  277,  278,   84,  257,  123,   87,   40,  261,   40,
  263,  264,   93,  266,   41,  268,   43,  270,   45,  272,
   41,   60,  103,   44,  277,  278,  259,  257,  267,  262,
   59,  261,  113,  263,  264,   44,  266,  268,  268,   41,
  270,   41,  272,  124,   44,  257,   41,  277,  278,   44,
   41,  263,  264,   44,  266,   93,  268,  267,  270,  151,
  272,  265,   76,   77,   41,  277,  278,   40,  257,   79,
   80,  260,  125,   41,  263,  279,  279,  266,  257,  268,
   40,  270,  261,  272,  263,  268,  258,  266,  123,  268,
  267,  270,  257,  272,  279,  260,   62,  125,  263,   41,
  257,  266,  268,  268,  261,  270,  263,  272,   44,  266,
  268,  268,   40,  270,   59,  272,  260,   44,   44,  260,
   59,  125,  261,  261,   59,  262,   41,  261,  143,  274,
  275,  276,  267,  268,  267,  268,  267,  268,  269,   35,
  267,   91,  277,  278,  155,  277,  278,  277,  278,  277,
  278,  257,   -1,   -1,   -1,  261,   -1,  263,   -1,  257,
  266,   -1,  268,  261,  270,  263,  272,   -1,  266,   -1,
  268,  257,  270,   -1,  272,  261,   -1,  263,   -1,  257,
  266,   -1,  268,  261,  270,  263,  272,  257,  266,   -1,
  268,  261,  270,  263,  272,  257,  266,   -1,  268,  261,
  270,  263,  272,  257,  266,   -1,  268,   -1,  270,  263,
  272,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END",
"END_IF","OUTF","TYPEDEF","FUN","RET","CTE","ID","CADENAMULTILINEA","WHILE",
"TRIPLE","GOTO","ETIQUETA","MAYORIGUAL","MENORIGUAL","DISTINTO","INTEGER",
"DOUBLE","ASIGNACION",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN sentencias END",
"programa : BEGIN sentencias END",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencia ';'",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables",
"tipo : INTEGER",
"tipo : DOUBLE",
"declaracion_subtipo : TYPEDEF ID ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID",
"declaracion_funciones : tipo FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : ID FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID",
"cuerpo_funcion : sentencias",
"retorno : RET '(' expresion_arit ')'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : OUTF '(' expresion_arit ')'",
"sentencia_ejecutable : OUTF '(' cadena ')'",
"sentencia_ejecutable : retorno",
"asignacion : ID ASIGNACION expresion_arit",
"asignacion : ID '{' CTE '}' ASIGNACION expresion_arit",
"invocacion : ID '(' parametro_real ')'",
"invocacion : ID '(' tipo parametros_formal ')'",
"parametro_real : list_expre",
"list_expre : list_expre ',' expresion_arit",
"list_expre : expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE_con_sig",
"factor : invocacion",
"factor : ID '{' CTE '}'",
"variables : variables ',' ID",
"variables : ID",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';' END_IF",
"condicion : '(' list_expre ')' comparador '(' list_expre ')'",
"comparador : '>'",
"comparador : MAYORIGUAL",
"comparador : '<'",
"comparador : MENORIGUAL",
"comparador : '='",
"comparador : DISTINTO",
"bloque_else : bloque_else_simple",
"bloque_else : bloque_else_multiple",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables END",
"bloque_else_simple : ELSE bloque_sentencia_simple",
"bloque_unidad : bloque_unidad_simple",
"bloque_unidad : bloque_unidad_multiple",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables END",
"bloque_unidad_simple : bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sent_ejecutables bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE '(' condicion ')' bloque_unidad",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 173 "Gramatica.y"
																	 

int yylex() {
	int tokenSalida = AnalizadorLexico.getToken();
	yylval = new ParserVal(AnalizadorLexico.Lexema);
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
//#line 418 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 16 "Gramatica.y"
{System.out.println(" En la linea " + AnalizadorLexico.saltoDeLinea + " se compilo el programa ");}
break;
case 2:
//#line 17 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta nombre del programa ");}
break;
case 26:
//#line 67 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 27:
//#line 68 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 29:
//#line 73 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 30:
//#line 74 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 48:
//#line 108 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 49:
//#line 109 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 70:
//#line 155 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 603 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
