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
    0,    1,    1,    2,    2,    3,    3,    3,    5,    8,
    8,    7,    7,    6,    6,   11,   11,   13,   12,   14,
    4,    4,    4,    4,    4,    4,    4,   16,   16,   21,
   21,   22,   23,   23,   15,   15,   15,   24,   24,   24,
   25,   25,   25,   25,    9,    9,   10,   10,   17,   17,
   26,   29,   29,   29,   29,   29,   29,   28,   28,   31,
   30,   27,   27,   35,   34,   32,   32,   33,   20,   20,
   18,   19,
};
final static short yylen[] = {                            2,
    4,    3,    2,    1,    1,    1,    1,    1,    2,    1,
    1,    9,    6,    9,    9,    3,    1,    2,    1,    4,
    1,    1,    1,    1,    4,    4,    1,    3,    6,    4,
    5,    1,    3,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    4,    3,    1,    1,    2,    8,    7,
    7,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    1,    1,    4,    2,    2,    1,    1,    1,    2,
    6,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,   11,    0,    0,    4,    5,    6,    7,    8,    0,
   27,   21,   22,   23,   24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   72,    1,    0,    3,    0,   46,
    0,    0,    0,   47,    0,   69,    0,    0,   42,    0,
    0,   43,    0,   40,    0,    0,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    0,    0,    0,    0,   48,
   70,   25,    0,    0,   26,    0,    0,    0,    0,   20,
    0,    0,    0,    0,   45,    0,    0,    0,    0,   62,
   63,    0,    0,    0,    0,    0,    0,   38,   39,    0,
    0,    0,    0,   17,    0,    0,    0,    0,   53,   55,
   57,   54,   52,   56,    0,    0,    0,   68,   65,    0,
    0,    0,    0,   58,   59,   44,    0,   30,    0,   13,
   18,    0,    0,    0,   71,    0,    0,    0,   67,    0,
    0,   50,    0,   31,    0,    0,   16,    0,    0,   64,
   66,   61,    0,   49,    0,    0,    0,    0,   51,   60,
   12,   15,   14,
};
final static short yydgoto[] = {                          2,
  156,   14,   15,  118,   17,   18,   19,  102,   41,   49,
  103,  157,  104,   21,   65,   22,   23,   24,   25,   51,
   52,   94,   66,   53,   54,   43,   89,  123,  115,  124,
  125,  138,  139,   90,   91,
};
final static short yysindex[] = {                      -231,
 -188,    0, -140,   48,   51, -220,   53, -117,   54, -187,
    0,    0, -157,   44,    0,    0,    0,    0,    0, -212,
    0,    0,    0,    0,    0,   68,  -42, -174,   50,  -38,
 -156,  -38, -153,   68,    0,    0,   59,    0, -149,    0,
   78,  -38,   84,    0,  -27,    0, -138,   34,    0,   -5,
   90,    0,   -8,    0, -236, -236,    9,   93,   22,   10,
   99,    0,   94, -127,   22,   25, -114, -124,  -44,    0,
    0,    0,  -38,  -38,    0,  -38,  -38,   23,   88,    0,
 -236, -123, -114, -236,    0,  -38,   37, -121, -237,    0,
    0,   27, -236,  116,  114,   -8,   -8,    0,    0,  -36,
 -109, -108,  -41,    0,  -38,  102,  -39,   22,    0,    0,
    0,    0,    0,    0,  125,   65, -115,    0,    0,  -92,
  -91,  111,  -90,    0,    0,    0,  -37,    0,  129,    0,
    0,  -82,  130,   22,    0,  -79,  -38,  -86,    0,   65,
   65,    0,  128,    0,  -36, -140,    0, -140,   30,    0,
    0,    0,  -78,    0,   64, -140,  -68,  -66,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  132,    0,    0,    0,  -15,    0,    0,    0,    0,    0,
    0,    0,    2,    0,    0,    0,    0,    0,  -54,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  155,   19,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -64,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  195,   -2,    0,    7,    0,    0,    0,   20,    0,  -88,
  -69,   52,  -72,    0,  -13,    0,    0,    0,    0,    0,
    0,    0,  -53,   28,  -18,  165,  118,    0,    0,    0,
    0,   61,  -70,    0,    0,
};
final static int YYTABLESIZE=337;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        132,
   47,  136,   47,  144,   28,   33,   47,   33,   47,   16,
   37,  129,   69,   50,  107,   95,   57,  119,   59,   16,
  120,  121,   20,  127,  122,   41,   41,   41,   41,   41,
  133,   41,   20,   76,  133,   72,    1,   73,   77,   74,
   11,   12,   37,   41,   37,   37,   37,   28,   48,   80,
   29,   73,   39,   74,  133,   40,  155,   98,   99,   35,
   37,   35,   35,   35,   73,   87,   74,  151,   86,  152,
  159,    3,  108,   86,   78,   79,   36,   35,   36,   36,
   36,   34,  151,  149,   34,   35,   33,   26,   93,   33,
   27,  134,   30,   34,   36,   68,  112,  114,  113,    4,
   96,   97,   38,   36,   55,    5,    6,   42,    7,   56,
    8,   58,    9,   60,   10,   29,    4,   62,   63,   11,
   12,   64,    5,    6,   67,    7,   71,    8,   70,    9,
   75,   10,   81,   84,   82,    4,   11,   12,  116,   83,
   85,    5,   92,   88,    7,  100,  117,   31,    9,  101,
   10,  126,   16,   37,   16,  105,  128,   86,  130,  131,
  135,   32,   16,   32,  137,   20,  140,   20,  141,  142,
    4,  143,  145,  147,  150,   20,    5,  146,    4,    7,
  148,  117,  160,    9,    5,   10,  154,    7,  161,  117,
    9,    9,  162,   10,  163,   32,   19,   13,   61,  158,
  106,  153,   28,   28,   28,    0,   28,   28,   28,    0,
    0,   28,    0,   28,    0,   28,    0,   28,    0,    0,
    0,    0,   44,   45,   44,   45,   46,    0,   44,   45,
   44,    0,   11,   12,    0,   11,   12,   11,   12,   11,
   12,   41,   41,   41,    0,   41,   41,   41,    0,    0,
   41,    0,   41,    0,   41,    0,   41,    0,   37,   37,
   37,    0,   37,   37,   37,    0,    0,   37,    0,   37,
    0,   37,    0,   37,    0,   35,   35,   35,    0,   35,
   35,   35,    0,    0,   35,    0,   35,    0,   35,    0,
   35,    0,   36,   36,   36,    0,   36,   36,   36,    0,
    0,   36,    0,   36,    0,   36,    0,   36,    0,    0,
  109,  110,  111,   29,   29,   29,    0,   29,   29,   29,
    0,    4,   29,    0,   29,    0,   29,    5,   29,    0,
    7,    0,  117,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   45,   41,   59,  123,   45,  123,   45,    3,
   13,  100,   40,   27,   84,   69,   30,   88,   32,   13,
  258,  259,    3,   93,  262,   41,   42,   43,   44,   45,
  103,   47,   13,   42,  107,   41,  268,   43,   47,   45,
  277,  278,   41,   59,   43,   44,   45,  268,   91,   41,
  271,   43,  265,   45,  127,  268,  145,   76,   77,   41,
   59,   43,   44,   45,   43,   41,   45,  138,   44,  140,
   41,  260,   86,   44,   55,   56,   41,   59,   43,   44,
   45,   41,  153,  137,   44,  273,   41,   40,   69,   44,
   40,  105,   40,   40,   59,  123,   60,   61,   62,  257,
   73,   74,   59,  261,  279,  263,  264,   40,  266,   60,
  268,  268,  270,  267,  272,   59,  257,   59,  268,  277,
  278,   44,  263,  264,   41,  266,   93,  268,  267,  270,
   41,  272,   40,   40,  125,  257,  277,  278,  260,   41,
  268,  263,  267,  258,  266,  123,  268,  265,  270,   62,
  272,  125,  146,  156,  148,  279,   41,   44,  268,  268,
   59,  279,  156,  279,   40,  146,  259,  148,  260,   59,
  257,  262,   44,   44,  261,  156,  263,  260,  257,  266,
  260,  268,  261,  270,  263,  272,   59,  266,  125,  268,
   59,  270,  261,  272,  261,   41,  261,    3,   34,  148,
   83,  141,  257,  258,  259,   -1,  261,  262,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,   -1,  267,  268,  267,  268,  269,   -1,  267,  268,
  267,   -1,  277,  278,   -1,  277,  278,  277,  278,  277,
  278,  257,  258,  259,   -1,  261,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,  257,  258,
  259,   -1,  261,  262,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,  257,  258,  259,   -1,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,  257,  258,  259,   -1,  261,  262,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
  274,  275,  276,  257,  258,  259,   -1,  261,  262,  263,
   -1,  257,  266,   -1,  268,   -1,  270,  263,  272,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,
};
}
final static short YYFINAL=2;
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
"sentencia_IF : IF '(' condicion ')' bloque_unidad bloque_else END_IF ';'",
"sentencia_IF : IF '(' condicion ')' bloque_unidad END_IF ';'",
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
"bloque_else_simple : THEN ELSE bloque_sentencia_simple",
"bloque_unidad : bloque_unidad_simple",
"bloque_unidad : bloque_unidad_multiple",
"bloque_unidad_multiple : THEN BEGIN bloque_sent_ejecutables END",
"bloque_unidad_simple : THEN bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sent_ejecutables bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE '(' condicion ')' bloque_unidad ';'",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 170 "Gramatica.y"
																	 

int yylex() {
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	return AnalizadorLexico.getToken();
}
private void yyerror(String string) {
	System.out.println(" Error Sintactico");
}
//#line 398 "Parser.java"
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
case 25:
//#line 66 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 26:
//#line 67 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 28:
//#line 72 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 29:
//#line 73 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 69:
//#line 152 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 571 "Parser.java"
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
