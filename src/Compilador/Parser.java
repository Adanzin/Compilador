package Compilador;
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
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
//#line 24 "Parser.java"




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
    8,    7,    7,    6,    6,   11,   11,   13,   12,   12,
    4,    4,    4,    4,    4,    4,   15,   15,   20,   20,
   21,   22,   22,   14,   14,   14,   23,   23,   23,   24,
   24,   24,   24,    9,    9,   10,   10,   16,   16,   25,
   28,   28,   28,   28,   28,   28,   27,   27,   30,   29,
   26,   26,   34,   33,   31,   31,   32,   19,   19,   17,
   18,
};
final static short yylen[] = {                            2,
    4,    3,    2,    1,    1,    1,    1,    1,    2,    1,
    1,    9,    6,    7,    7,    3,    1,    2,    5,    1,
    1,    1,    1,    1,    4,    4,    3,    6,    4,    5,
    1,    3,    1,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    4,    3,    1,    1,    2,    8,    7,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    4,    3,
    1,    1,    4,    2,    2,    1,    1,    3,    2,    6,
    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   10,
   11,    0,    0,    4,    5,    6,    7,    8,    0,   21,
   22,   23,   24,    0,    0,    0,    0,    0,    0,    0,
    0,   71,    1,    0,    3,    0,   45,    0,   46,    0,
    0,   41,    0,   42,    0,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    0,   47,    0,    0,   52,   54,   56,    0,   53,
   51,   55,    0,    0,    0,    0,    0,   69,   25,   26,
    0,    0,    0,    0,   17,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,   37,   38,    0,
    0,   61,   62,   68,    0,    0,   18,    0,    0,    0,
    0,    0,   43,    0,   29,    0,    0,   67,   64,    0,
    0,    0,    0,   57,   58,    0,   13,    0,    0,   16,
    0,   70,    0,   30,    0,   66,    0,    0,   49,    0,
    0,    0,   15,   14,   63,   65,   60,    0,   48,    0,
    0,   59,   12,    0,   19,
};
final static short yydgoto[] = {                          2,
  128,   13,   14,  118,   16,   17,   18,   83,   38,   42,
   84,  129,   85,   43,   20,   21,   22,   23,   51,   44,
   92,   45,   46,   47,   48,  101,  123,   73,  124,  125,
  135,  136,  102,  103,
};
final static short yysindex[] = {                      -246,
 -233,    0, -105,   -6,    9, -146, -118,   22, -162,    0,
    0, -151,   57,    0,    0,    0,    0,    0, -133,    0,
    0,    0,    0,    2,  -20, -141,   80, -122,    2, -114,
    2,    0,    0,   95,    0, -113,    0,  112,    0,  -32,
 -110,    0,   40,    0,   26,   42,    0,  119,  -89,   49,
  123, -237, -237, -237,   40,   41,  127,    0, -237,  -99,
  -97,  -43,    0,    2,    2,    0,    0,    0,    2,    0,
    0,    0,    2,    2,    2,  -83,   83,    0,    0,    0,
   55,  125,  -87, -239,    0,  -91,  -83, -178,    0,   64,
 -237,  150,  149,   42,   42,   40,  149,    0,    0,  -86,
 -154,    0,    0,    0,  -44,  -73,    0, -105,  152,    2,
  138, -105,    0,  -41,    0,   59, -117,    0,    0,  -61,
  -60,  140,  -51,    0,    0,  165,    0, -127,  -48,    0,
   40,    0,  -40,    0,  -78,    0,   59,   59,    0,  159,
  -44,  180,    0,    0,    0,    0,    0,  -53,    0,   97,
    2,    0,    0,   88,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  167,    0,  -30,
    0,    0,  -18,    0,    0,   -8,    0,    0,    0,    0,
    0,    0,    0,    0,  -56,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  189,   14,   36,    4,  198,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -2,    0,    0,
   56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  238,   -5,    0,    6,    0,    0,    0,   16,    0,  -95,
  -35,  131,  -21,   -9,    0,    0,    0,    0,    0,    0,
    0,  -12,   38,   74,  221,  174,    0,    0,    0,    0,
  136,  -28,    0,    0,
};
final static int YYTABLESIZE=331;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        134,
   41,   41,   27,   78,   30,   30,   34,   62,   15,  126,
   40,   40,   40,   40,   40,   50,   40,   15,   19,   55,
  108,    1,   33,   88,   41,   33,    3,   19,   40,   40,
   40,   40,   36,   24,   36,   36,   36,   10,   11,   10,
   11,   33,   33,   33,   32,  150,   41,   32,   25,   93,
   36,   36,   36,   36,   34,  114,   34,   34,   34,   96,
   97,   31,  109,   32,   32,   32,  109,   81,   82,   69,
   49,  119,   34,   34,   34,   34,   35,   91,   35,   35,
   35,  112,   64,   74,   65,   70,   72,   71,   75,   79,
   61,   64,  109,   65,   35,   35,   35,   35,   10,   11,
  131,   94,   95,  120,  121,    4,  146,  122,  147,   33,
   32,    5,    6,   15,   28,   35,    7,   15,    8,  146,
    9,   26,   34,   19,   27,   10,   11,   19,  155,    4,
   64,   36,   65,   15,   37,    5,    6,   52,  142,   53,
    7,  154,    8,   19,    9,   54,   28,   98,   99,   10,
   11,    4,   56,   58,   59,   60,   63,    5,    6,   76,
   29,   29,    7,   80,    8,   86,    9,   87,   89,   90,
    4,   10,   11,  116,  100,  104,    5,  105,    4,   77,
  107,  117,  145,    8,    5,    9,  106,  110,  113,  117,
  115,    8,   69,    9,  127,  130,  132,  137,  139,  138,
   27,   27,   27,    4,   27,   27,   27,  152,  141,    5,
  140,   27,  143,   27,  117,   27,    8,  149,    9,  151,
  144,  153,   39,   39,   40,    9,   40,   40,   40,   31,
   40,   40,   40,   10,   11,   10,   11,   40,   50,   40,
   12,   40,  133,   40,   40,   40,   39,   40,   36,   36,
   36,   57,   36,   36,   36,   33,   33,   33,   20,   36,
  111,   36,    0,   36,    0,   36,   36,   36,   39,   40,
   34,   34,   34,  148,   34,   34,   34,   32,   32,   32,
    0,   34,    0,   34,    0,   34,    0,   34,   34,   34,
    0,    0,   35,   35,   35,    0,   35,   35,   35,   66,
   67,   68,    0,   35,    0,   35,    0,   35,    0,   35,
   35,   35,   28,   28,   28,    4,   28,   28,   28,    0,
    0,    5,    0,   28,    0,   28,  117,   28,    8,    0,
    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   45,   59,   93,  123,  123,   12,   40,    3,  105,
   41,   42,   43,   44,   45,   25,   47,   12,    3,   29,
  260,  268,   41,   59,   45,   44,  260,   12,   59,   60,
   61,   62,   41,   40,   43,   44,   45,  277,  278,  277,
  278,   60,   61,   62,   41,  141,   45,   44,   40,   62,
   59,   60,   61,   62,   41,   91,   43,   44,   45,   69,
   73,   40,   84,   60,   61,   62,   88,   52,   53,   44,
   91,  100,   59,   60,   61,   62,   41,   62,   43,   44,
   45,  260,   43,   42,   45,   60,   61,   62,   47,   41,
  123,   43,  114,   45,   59,   60,   61,   62,  277,  278,
  110,   64,   65,  258,  259,  257,  135,  262,  137,  261,
  273,  263,  264,  108,   59,   59,  268,  112,  270,  148,
  272,  268,  128,  108,  271,  277,  278,  112,   41,  257,
   43,  265,   45,  128,  268,  263,  264,  279,  266,   60,
  268,  151,  270,  128,  272,  268,  265,   74,   75,  277,
  278,  257,  267,   59,  268,   44,  267,  263,  264,   41,
  279,  279,  268,   41,  270,  125,  272,   41,  268,  267,
  257,  277,  278,  260,  258,   93,  263,  123,  257,  269,
  268,  268,  261,  270,  263,  272,   62,  279,  125,  268,
   41,  270,   44,  272,  268,   44,   59,  259,   59,  260,
  257,  258,  259,  257,  261,  262,  263,  261,   44,  263,
  262,  268,  261,  270,  268,  272,  270,   59,  272,   40,
  261,  125,  267,  267,  268,   59,  257,  258,  259,   41,
  261,  262,  263,  277,  278,  277,  278,  268,   41,  270,
    3,  272,  112,  274,  275,  276,  267,  268,  257,  258,
  259,   31,  261,  262,  263,  274,  275,  276,  261,  268,
   87,  270,   -1,  272,   -1,  274,  275,  276,  267,  268,
  257,  258,  259,  138,  261,  262,  263,  274,  275,  276,
   -1,  268,   -1,  270,   -1,  272,   -1,  274,  275,  276,
   -1,   -1,  257,  258,  259,   -1,  261,  262,  263,  274,
  275,  276,   -1,  268,   -1,  270,   -1,  272,   -1,  274,
  275,  276,  257,  258,  259,  257,  261,  262,  263,   -1,
   -1,  263,   -1,  268,   -1,  270,  268,  272,  270,   -1,
  272,
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
"declaracion_funciones : tipo FUN ID parametros_formal BEGIN cuerpo_funcion END",
"declaracion_funciones : ID FUN ID parametros_formal BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID",
"cuerpo_funcion : sentencias RET '(' expresion_arit ')'",
"cuerpo_funcion : sentencias",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : OUTF '(' expresion_arit ')'",
"sentencia_ejecutable : OUTF '(' cadena ')'",
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
"condicion : list_expre comparador list_expre",
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
"cadena : '[' CADENAMULTILINEA ']'",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE '(' condicion ')' bloque_unidad ';'",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 168 "Gramatica.y"
																	 

int yylex() {
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	return AnalizadorLexico.getToken();
}
//#line 390 "Parser.java"
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
//#line 15 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " PROGRAMA ");}
break;
case 25:
//#line 65 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 26:
//#line 66 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 27:
//#line 70 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 28:
//#line 71 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
//#line 559 "Parser.java"
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



private void yyerror(String string) {
System.out.println(string);
	
}
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
