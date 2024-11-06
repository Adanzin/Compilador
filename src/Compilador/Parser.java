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



package Compilador;



//#line 2 "Gramatica.y"
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
//#line 23 "Parser.java"




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
public final static short ERROR=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    2,    2,    3,    3,
    3,    3,    4,    4,    4,    4,    4,    6,    6,    9,
    9,   11,   11,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    7,
   13,   13,   14,   14,   14,   14,   14,   15,   16,   16,
    5,    5,    5,    5,    5,    5,    5,   22,   22,   22,
   22,   18,   18,   18,   25,   25,   25,   25,   26,   26,
   26,   26,   17,   17,   17,   17,   17,   17,   17,   17,
   27,   27,   27,   27,   27,   27,   27,   28,   28,   28,
   28,   28,   10,   10,   10,   24,    1,   12,   12,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   32,   32,   32,   32,   32,   32,   31,   31,
   34,   34,   33,   30,   30,   38,   38,   38,   37,   35,
   35,   35,   36,   23,   20,   20,   39,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    2,    2,    1,    1,    3,    3,    1,
    1,    1,    1,    9,    8,    8,    7,    6,    8,    7,
    8,    6,    8,    8,    5,    5,    5,    4,    6,    5,
    3,    2,    4,    3,    3,    2,    3,    1,    4,    3,
    1,    1,    1,    1,    1,    1,    1,    4,    3,    4,
    4,    3,    6,    7,    4,    7,    3,    4,    3,    2,
    2,    1,    3,    3,    1,    3,    3,    3,    3,    2,
    3,    3,    1,    3,    3,    3,    3,    1,    1,    1,
    4,    5,    3,    2,    1,    1,    1,    1,    2,    8,
    6,    6,    4,    7,    7,    5,    7,    6,    6,    8,
    9,    8,    8,    7,    5,    4,    4,    3,    3,    8,
    8,    8,    1,    1,    1,    1,    1,    1,    1,    1,
    5,    4,    2,    1,    1,    4,    2,    3,    1,    3,
    1,    2,    1,    1,    3,    3,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   97,    0,    0,    0,    0,    0,    0,    0,  147,
    0,   55,   22,   23,    0,    0,    8,    9,    0,   13,
    0,    0,    0,   21,    0,   57,   51,   52,   53,   54,
   56,    0,    0,    0,    0,   10,    0,   98,    0,    0,
    0,   89,    0,    0,   90,    0,   83,    0,    0,    0,
    0,    0,    0,    0,  149,  148,    3,    7,   11,   14,
   15,    0,   96,    0,   95,    0,    0,    0,    0,    0,
    2,    0,    5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   99,    0,  124,  126,  128,  125,  123,
    0,    0,  127,    0,    0,    0,    0,    0,    0,  144,
   59,    0,    0,    0,    0,    0,    0,   20,    0,    0,
    0,    0,   50,    0,   41,   19,   18,    0,   94,    0,
   46,    0,    0,    0,    0,    0,    0,  146,    0,  143,
  145,  139,  134,  135,    1,   77,   76,   84,   85,    0,
    0,    0,    0,    0,    0,    0,   67,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   86,   81,   87,   82,
    0,  103,    0,    0,  129,  130,   61,   58,   60,    0,
    0,    0,   38,    0,    0,    0,    0,   49,   93,   47,
   45,   44,    0,    0,    0,    0,    0,  137,    0,  141,
    0,    0,    0,    0,    0,   68,    0,   65,  116,   91,
    0,    0,  133,  106,    0,    0,    0,    0,    0,    0,
   37,   36,    0,   35,    0,    0,    0,   43,   40,    0,
    0,  138,    0,  142,    0,    0,    0,  115,    0,    0,
   92,    0,  109,    0,  101,    0,  108,    0,  102,    0,
    0,   39,   28,    0,    0,    0,   32,    0,    0,    0,
    0,  136,  140,    0,    0,    0,    0,    0,    0,  132,
    0,  104,  107,    0,  105,    0,    0,    0,    0,    0,
   30,    0,    0,    0,    0,    0,    0,    0,   66,  131,
  110,  100,   33,   34,   29,   31,    0,   25,  120,    0,
  122,  121,  112,   24,  111,
};
final static short yydgoto[] = {                          3,
   41,   16,   17,   18,   19,   20,   21,   22,   23,   64,
   24,   42,   25,   67,  185,   26,  141,   27,   28,   29,
   30,   31,  105,   44,   45,   83,   46,   47,   48,  131,
  164,   94,  165,  166,  189,  132,  133,  134,   33,
};
final static short yysindex[] = {                      -209,
  868,    0,    0,  773,   -3,  467,   18,  231,   40,    0,
 -218,    0,    0,    0,    0,  796,    0,    0,   42,    0,
   63,   67, -125,    0,   95,    0,    0,    0,    0,    0,
    0, -106,  467,  814,  832,    0,  742,    0,  541, -134,
  101,    0,  626,   19,    0,   55,    0, -109,  340,  407,
 -158, -158, -123,  513,    0,    0,    0,    0,    0,    0,
    0, -110,    0,  -23,    0,  -41,  -88,  544,  -32,  963,
    0,  850,    0,   57, -167,  -82,  -77,  -75,   55,  544,
  571,  626,  107,    0,  -37,    0,    0,    0,    0,    0,
  628,  648,    0,  544,  -18,  653,  657,  980,  607,    0,
    0,  101,  300,  125,  150,  -92, -158,    0,  -60,   74,
  142,  -66,    0,  271,    0,    0,    0,  -50,    0,  179,
    0,  193,  -26,  868,  -17,  114,   -1,    0,  750,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -17,
  -17,  -21,  544,  544,  573,  508,    0,  230,  294,   57,
   55,   57,   55,  317,  164,   28,    0,    0,    0,    0,
  274,    0,  -45,  259,    0,    0,    0,    0,    0,  -16,
  -52,  -50,    0,    5,  -50,    5,  -42,    0,    0,    0,
    0,    0,  323,  868,  122,  108,  302,    0,  701,    0,
  -28,  391,  365,  -17,  403,    0,  544,    0,    0,    0,
  325, -145,    0,    0,  328,  -34,  192,  339,  202, -204,
    0,    0,  434,    0,  445,  416,  446,    0,    0,  544,
  215,    0, 1002,    0,  571,  613,  571,    0,  571,  478,
    0,  718,    0,  994,    0,   -6,    0,  234,    0,  108,
  372,    0,    0,    5,    5,    5,    0,  -22,    5,  -17,
  544,    0,    0,  169,  571,  415,  444,  457,  465,    0,
 1016,    0,    0, -180,    0,  215,  389,  401,  414,  -29,
    0,  420,  -17,  487,  550,  491,  511,  518,    0,    0,
    0,    0,    0,    0,    0,    0,  436,    0,    0,  526,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  357,    0,    0,    0,  252,    0,
  404,  495,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  569,    0,    0,    0,    0,    0,
    1,    0,    0,   24,    0,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  531,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  575,    0,    0,    0,    0,    0,    0,   70,    0,
    0,  551,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -36,    0,    0,    0,  147,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  185,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  539,
  570,    0,    0,  579,  680,    0,    0,    0,    0,   93,
  116,  139,  162,  891,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  315,    0,    0,    0,    0,    0,    0,
    0,    0,  909,  585,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  790,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  958,    0,    0,    0,    0,
    0,  452,  433,    0,    0,    0,    0,  927,    0,    0,
    0,    0,    0,    0,    0,    0,  476,    0,    0,  945,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  770,   45,    2,    0,  696,    0,    0,    0,  453,    0,
  -12,  -99,    0,    0,    0,    0,  494,    0,    0,    0,
    0,    0,    0,  794,    0,  -62,  392,   99,  549,  500,
 -133,  -47,    0,    0,  400,  774,    0,    0,    0,
};
final static int YYTABLESIZE=1289;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        121,
   96,  172,   40,  147,   96,   96,   96,   40,   96,  210,
   96,  225,  127,  205,  182,   40,   69,   58,  142,  191,
  118,  270,  144,   88,  238,   92,  156,   91,  209,  206,
  170,   89,   93,   90,  143,  117,   58,   55,   89,   93,
   90,   96,   96,   96,   96,   96,   75,   96,   35,   40,
    1,  242,  264,  123,   56,   36,  176,   49,    2,   96,
   96,   96,   96,    2,   88,   88,   88,   88,   88,   80,
   88,  236,  148,   58,  213,  281,  215,  217,   72,   54,
  216,  282,   88,   88,   88,   88,   96,   75,  136,   75,
   75,   75,   79,  103,  192,  286,   96,  195,   77,   84,
   59,   97,  271,   78,   58,   75,   75,   75,   75,    2,
   80,    6,   80,   80,   80,   74,  248,    7,   13,   14,
    9,   60,    2,   96,   10,   61,   11,   12,   80,   80,
   80,   80,   84,   79,   66,   79,   79,   79,   78,   62,
   85,   95,    2,  226,  267,  268,  269,  145,   98,  272,
  144,   79,   79,   79,   79,  112,   74,  115,   74,   74,
   74,   73,  254,  256,  257,  168,  258,   92,  184,   91,
  287,  124,   68,  137,   74,   74,   74,   74,  138,   78,
  139,   78,   78,   78,   62,   58,   68,   88,   88,   88,
  169,   88,  275,   88,  158,  160,  174,   78,   78,   78,
   78,    2,   73,  175,   73,   73,   73,    2,   63,  274,
   13,   14,  144,  161,  120,    2,  204,    2,  146,  180,
   73,   73,   73,   73,   38,   62,    2,  237,   20,   38,
    2,   20,  116,  181,  126,   13,   14,   38,  186,   13,
   14,    2,   96,   62,    2,   86,   87,   88,  155,   63,
  208,   12,   86,   87,   88,  263,   96,   96,   96,   96,
   96,   96,   96,   96,   96,  187,   96,   63,   96,  197,
   96,   38,   96,   96,   96,   96,   96,   96,   96,   88,
   88,   88,   88,   88,   88,   88,   88,   88,  200,   88,
   52,   88,   12,   88,  201,   88,   88,   88,   88,   88,
   88,   88,   75,   75,   75,   75,   75,   75,   75,   75,
   75,  178,   75,   92,   75,   91,   75,  207,   75,   75,
   75,   75,   75,   75,   75,   80,   80,   80,   80,   80,
   80,   80,   80,   80,  198,   80,   92,   80,   91,   80,
  167,   80,   80,   80,   80,   80,   80,   80,   79,   79,
   79,   79,   79,   79,   79,   79,   79,  199,   79,   92,
   79,   91,   79,  218,   79,   79,   79,   79,   79,   79,
   79,   74,   74,   74,   74,   74,   74,   74,   74,   74,
  101,   74,  219,   74,   40,   74,  220,   74,   74,   74,
   74,   74,   74,   74,   78,   78,   78,   78,   78,   78,
   78,   78,   78,   16,   78,  228,   78,   92,   78,   91,
   78,   78,   78,   78,   78,   78,   78,   73,   73,   73,
   73,   73,   73,   73,   73,   73,  221,   73,   79,   73,
  227,   73,   64,   73,   73,   73,   73,   73,   73,   73,
   62,   62,  229,   62,   16,   62,   62,   62,   62,  231,
   62,   27,   62,  239,   62,  276,   62,   62,  144,  246,
   40,   62,   62,  240,   63,   63,  107,   63,  241,   63,
   63,   63,   63,   64,   63,   26,   63,  244,   63,   96,
   63,   63,  151,  153,  277,   63,   63,  144,  245,  249,
   79,   64,   27,  251,   17,  265,  266,  278,    2,   43,
  144,   50,  109,  110,  111,  279,   39,   12,   12,   51,
   27,   40,   12,  283,   12,   12,   26,   12,  259,   12,
   92,   12,   91,   12,   12,  284,   43,  289,   12,   12,
    6,  291,   82,  202,   26,   17,    7,   79,  285,    9,
  247,    2,  104,   10,  288,   11,   12,  114,  196,   77,
   76,  292,   75,  113,   78,    5,    6,   40,  293,  171,
  294,  125,    7,    8,  177,    9,  295,    2,    6,   10,
   42,   11,   12,  140,    4,   48,   13,   14,  149,   70,
   81,   70,   70,  233,   80,   40,  234,  154,   40,  235,
  290,   72,    0,  144,   72,   99,    6,  163,   70,   70,
   70,  232,    7,    8,    0,    9,   38,    2,  100,   10,
   72,   11,   12,   72,   80,   40,   13,   14,    0,   71,
    0,   20,   71,    0,   20,   69,    0,    0,   69,   72,
   72,   72,   89,   93,   90,   96,  193,  194,   71,   71,
   71,    0,    0,    0,   69,   69,   69,    0,   77,   76,
    0,   75,  255,   78,    0,    0,   80,   40,    0,   16,
   16,    0,    0,    0,   16,   36,   16,   16,   92,   16,
   91,   16,   40,   16,    2,   16,   16,    0,    0,    0,
   16,   16,   38,   13,   14,   89,   93,   90,   64,   64,
  230,   64,   40,   64,   64,   64,   64,   40,   64,    0,
   64,   40,   64,    0,   64,   64,    0,   27,   27,   64,
   64,    0,   27,  250,   27,   27,    0,   27,    0,   27,
    0,   27,   37,   27,   27,    0,    0,    0,   27,   27,
    0,   26,   26,   38,    2,    0,   26,    0,   26,   26,
    0,   26,    0,   26,  273,   26,    0,   26,   26,    0,
   17,   17,   26,   26,    0,   17,    0,   17,   17,  223,
   17,    0,   17,   74,   17,  130,   17,   17,   37,    4,
   15,   17,   17,   15,   38,    2,  261,   53,    0,   38,
    2,    0,    0,   77,   76,   15,   75,    0,   78,    0,
    0,    0,   63,  130,   32,    0,   37,   32,    0,   37,
    0,    0,    0,   15,   15,    0,    0,   38,    2,   32,
   38,    2,   70,   70,   70,    0,   65,    0,  102,  108,
  108,  108,    0,    0,  130,    0,   37,   32,   32,    0,
   91,   91,   91,   63,   91,  122,   91,   38,    2,   63,
    0,   15,  106,   72,   72,   72,   86,   87,   88,    0,
    0,    0,   71,   71,   71,    0,  130,  119,   69,   69,
   69,    0,   74,   32,    0,   32,    0,   63,   37,    0,
    0,    0,   15,   38,    2,    0,  108,    0,  173,   38,
    2,  108,    0,  150,  130,    0,    0,   63,    0,    0,
    0,   32,  183,   15,   38,    2,   32,  130,   63,   86,
   87,   88,  190,  152,    0,    0,    0,    0,  157,    0,
    0,  179,  159,    0,   38,    2,    0,   32,  130,   38,
    2,    0,   32,   38,    2,    0,    0,  130,    0,  130,
   63,    0,    0,    0,  203,  119,  119,  119,    0,  119,
  211,  212,  119,    0,  214,  119,    0,  119,    0,  119,
    0,  119,  119,   15,   32,    0,  130,    6,   63,    0,
    0,  222,  224,    7,    0,    0,    9,    0,    2,    0,
   10,   63,   11,   12,    6,  190,    0,   32,  260,  243,
    7,    0,   32,    9,    0,    2,    0,   10,    0,   11,
   12,    0,   63,    0,    0,   32,  253,   74,   92,   92,
   92,   63,   92,   63,   92,  224,    6,  203,   38,    2,
  188,    0,    7,    0,    0,    9,   32,    2,    0,   10,
    0,   11,   12,    0,    0,   32,    0,   32,    5,    6,
   63,    0,   34,    0,  253,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    0,    0,   13,
   14,    5,    6,    0,   32,    0,   57,    0,    7,    8,
    0,    9,    0,    2,    0,   10,    0,   11,   12,    5,
    6,    0,   13,   14,   71,    0,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    5,    6,    0,
   13,   14,   73,    0,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    5,    6,    0,   13,   14,
  135,    0,    7,    8,    0,    9,    0,    2,    0,   10,
    0,   11,   12,    5,    6,    0,   13,   14,    0,    0,
    7,    8,    0,    9,    0,    2,    0,   10,    0,   11,
   12,    0,    0,    0,   13,   14,  118,  118,  118,    0,
  118,    0,    0,  118,    0,    0,  118,    0,  118,    0,
  118,    0,  118,  118,  117,  117,  117,    0,  117,    0,
    0,  117,    0,    0,  117,    0,  117,    0,  117,    0,
  117,  117,  114,  114,  114,    0,  114,    0,    0,  114,
    0,    0,  114,    0,  114,    0,  114,    0,  114,  114,
  113,  113,  113,    0,  113,    0,    0,  113,    0,    0,
  113,    0,  113,    0,  113,    0,  113,  113,  128,    6,
    0,    0,  129,    0,    0,    7,    0,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    6,    0,  161,  129,
    0,  162,    7,    0,    0,    9,    0,    2,    0,   10,
    6,   11,   12,  202,    0,  262,    7,    0,    6,    9,
    0,    2,  252,   10,    7,   11,   12,    9,    0,    2,
    0,   10,    6,   11,   12,    0,  280,    0,    7,    0,
    0,    9,    0,    2,    0,   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   62,   45,   41,   41,   42,   43,   45,   45,   62,
   47,   40,   45,   59,   41,   45,  123,   16,   81,   41,
   44,   44,   44,    0,   59,   43,   45,   45,   45,  163,
  123,   60,   61,   62,   82,   59,   35,  256,   60,   61,
   62,   41,   42,   43,   44,   45,    0,   47,    4,   45,
  260,  256,   59,   66,  273,   59,  123,   40,  268,   59,
   60,   61,   62,  268,   41,   42,   43,   44,   45,    0,
   47,  205,   85,   72,  174,  256,  176,  177,   34,   40,
  123,  262,   59,   60,   61,   62,  123,   41,  256,   43,
   44,   45,    0,   49,  142,  125,   42,  145,   42,  267,
   59,   47,  125,   47,  103,   59,   60,   61,   62,  268,
   41,  257,   43,   44,   45,    0,  216,  263,  277,  278,
  266,   59,  268,  123,  270,   59,  272,  273,   59,   60,
   61,   62,  267,   41,   40,   43,   44,   45,    0,  265,
   40,  123,  268,  191,  244,  245,  246,   41,  258,  249,
   44,   59,   60,   61,   62,  279,   41,  268,   43,   44,
   45,    0,  225,  226,  227,   41,  229,   43,  124,   45,
  270,  260,  279,  256,   59,   60,   61,   62,  256,   41,
  256,   43,   44,   45,    0,  184,  279,   41,   42,   43,
   41,   45,  255,   47,   96,   97,  123,   59,   60,   61,
   62,  268,   41,   62,   43,   44,   45,  268,    0,   41,
  277,  278,   44,  259,  256,  268,  262,  268,  256,   41,
   59,   60,   61,   62,  267,   41,  268,  262,  265,  267,
  268,  268,  256,   41,  267,  277,  278,  267,  125,  277,
  278,  268,  279,   59,  268,  274,  275,  276,  267,   41,
  267,    0,  274,  275,  276,  262,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  267,  266,   59,  268,   40,
  270,  267,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  259,  260,  261,  262,  263,  264,  125,  266,
   60,  268,   41,  270,  267,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,  259,  260,  261,  262,  263,
  264,   41,  266,   43,  268,   45,  270,   59,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   43,  268,   45,  270,
   41,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   41,  266,   43,
  268,   45,  270,   41,  272,  273,  274,  275,  276,  277,
  278,  256,  257,  258,  259,  260,  261,  262,  263,  264,
   41,  266,  261,  268,   45,  270,  279,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  258,  259,  260,  261,
  262,  263,  264,    0,  266,   41,  268,   43,  270,   45,
  272,  273,  274,  275,  276,  277,  278,  256,  257,  258,
  259,  260,  261,  262,  263,  264,  125,  266,   37,  268,
   40,  270,    0,  272,  273,  274,  275,  276,  277,  278,
  256,  257,   40,  259,   41,  261,  262,  263,  264,  125,
  266,    0,  268,  262,  270,   41,  272,  273,   44,   44,
   45,  277,  278,  125,  256,  257,   60,  259,  267,  261,
  262,  263,  264,   41,  266,    0,  268,   44,  270,  123,
  272,  273,   91,   92,   41,  277,  278,   44,   44,   44,
   99,   59,   41,  279,    0,  262,  125,   41,  268,    6,
   44,  271,   50,   51,   52,   41,   40,  256,  257,  279,
   59,   45,  261,  125,  263,  264,   41,  266,   41,  268,
   43,  270,   45,  272,  273,  125,   33,   41,  277,  278,
  257,   41,   39,  260,   59,   41,  263,  146,  125,  266,
  125,  268,   49,  270,  125,  272,  273,   54,   41,   42,
   43,   41,   45,   41,   47,  256,  257,   45,   41,  107,
  125,   68,  263,  264,  112,  266,   41,  268,    0,  270,
   40,  272,  273,   80,    0,  261,  277,  278,   85,   41,
   40,   33,   44,  256,   44,   45,  259,   94,   45,  262,
   41,   41,   -1,   44,   44,  256,  257,   98,   60,   61,
   62,  202,  263,  264,   -1,  266,  267,  268,  269,  270,
   41,  272,  273,   44,   44,   45,  277,  278,   -1,   41,
   -1,  265,   44,   -1,  268,   41,   -1,   -1,   44,   60,
   61,   62,   60,   61,   62,  279,  143,  144,   60,   61,
   62,   -1,   -1,   -1,   60,   61,   62,   -1,   42,   43,
   -1,   45,   40,   47,   -1,   -1,   44,   45,   -1,  256,
  257,   -1,   -1,   -1,  261,   59,  263,  264,   43,  266,
   45,  268,   45,  270,  268,  272,  273,   -1,   -1,   -1,
  277,  278,  267,  277,  278,   60,   61,   62,  256,  257,
  197,  259,   45,  261,  262,  263,  264,   45,  266,   -1,
  268,   45,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,   -1,  261,  220,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  256,  272,  273,   -1,   -1,   -1,  277,  278,
   -1,  256,  257,  267,  268,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,  251,  270,   -1,  272,  273,   -1,
  256,  257,  277,  278,   -1,  261,   -1,  263,  264,   59,
  266,   -1,  268,  256,  270,   70,  272,  273,  256,    0,
    1,  277,  278,    4,  267,  268,   59,    8,   -1,  267,
  268,   -1,   -1,   42,   43,   16,   45,   -1,   47,   -1,
   -1,   -1,   23,   98,    1,   -1,  256,    4,   -1,  256,
   -1,   -1,   -1,   34,   35,   -1,   -1,  267,  268,   16,
  267,  268,  274,  275,  276,   -1,   23,   -1,   49,   50,
   51,   52,   -1,   -1,  129,   -1,  256,   34,   35,   -1,
   41,   42,   43,   64,   45,   66,   47,  267,  268,   70,
   -1,   72,   49,  274,  275,  276,  274,  275,  276,   -1,
   -1,   -1,  274,  275,  276,   -1,  161,   64,  274,  275,
  276,   -1,  256,   70,   -1,   72,   -1,   98,  256,   -1,
   -1,   -1,  103,  267,  268,   -1,  107,   -1,  109,  267,
  268,  112,   -1,  256,  189,   -1,   -1,  118,   -1,   -1,
   -1,   98,  123,  124,  267,  268,  103,  202,  129,  274,
  275,  276,  129,  256,   -1,   -1,   -1,   -1,  256,   -1,
   -1,  118,  256,   -1,  267,  268,   -1,  124,  223,  267,
  268,   -1,  129,  267,  268,   -1,   -1,  232,   -1,  234,
  161,   -1,   -1,   -1,  161,  256,  257,  258,   -1,  260,
  171,  172,  263,   -1,  175,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  184,  161,   -1,  261,  257,  189,   -1,
   -1,  261,  189,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  202,  272,  273,  257,  202,   -1,  184,  261,  210,
  263,   -1,  189,  266,   -1,  268,   -1,  270,   -1,  272,
  273,   -1,  223,   -1,   -1,  202,  223,  256,   41,   42,
   43,  232,   45,  234,   47,  232,  257,  234,  267,  268,
  261,   -1,  263,   -1,   -1,  266,  223,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,  232,   -1,  234,  256,  257,
  261,   -1,  260,   -1,  261,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,
  278,  256,  257,   -1,  261,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  256,  257,   -1,  277,  278,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  256,  257,   -1,  277,  278,   -1,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,   -1,   -1,   -1,  277,  278,  256,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  257,   -1,  259,  260,
   -1,  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  257,  272,  273,  260,   -1,  262,  263,   -1,  257,  266,
   -1,  268,  261,  270,  263,  272,  273,  266,   -1,  268,
   -1,  270,  257,  272,  273,   -1,  261,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END","END_IF",
"OUTF","TYPEDEF","FUN","RET","CTE","ID","CADENAMULTILINEA","WHILE","TRIPLE",
"GOTO","ETIQUETA","MAYORIGUAL","MENORIGUAL","DISTINTO","INTEGER","DOUBLE",
"ASIGNACION","ERROR",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID_simple BEGIN sentencias END",
"programa : ID_simple BEGIN END",
"programa : BEGIN sentencias END",
"programa : ID_simple BEGIN sentencias",
"programa : ID_simple sentencias END",
"programa : ID_simple sentencias",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : error ';'",
"sentencia : sentencia_ejecutable ';'",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones ';'",
"sentencia_declarativa : declaracion_subtipo ';'",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables ';'",
"declaracion_variable : tipo variables error",
"tipo : ID_simple",
"tipo : tipo_primitivo",
"tipo_primitivo : INTEGER",
"tipo_primitivo : DOUBLE",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' '}'",
"declaracion_subtipo : TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF '<' tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE tipo ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' error",
"declaracion_funciones : encabezado_funcion parametros_parentesis BEGIN cuerpo_funcion END",
"encabezado_funcion : tipo FUN ID",
"encabezado_funcion : tipo FUN",
"parametros_parentesis : '(' tipo_primitivo ID_simple ')'",
"parametros_parentesis : '(' tipo_primitivo ')'",
"parametros_parentesis : '(' ID_simple ')'",
"parametros_parentesis : '(' ')'",
"parametros_parentesis : '(' error ')'",
"cuerpo_funcion : sentencias",
"retorno : RET '(' expresion_arit ')'",
"retorno : RET '(' ')'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : ETIQUETA",
"sentencia_ejecutable : outf_rule",
"sentencia_ejecutable : retorno",
"outf_rule : OUTF '(' expresion_arit ')'",
"outf_rule : OUTF '(' ')'",
"outf_rule : OUTF '(' cadena ')'",
"outf_rule : OUTF '(' sentencias ')'",
"asignacion : variable_simple ASIGNACION expresion_arit",
"asignacion : variable_simple '{' CTE '}' ASIGNACION expresion_arit",
"asignacion : variable_simple '{' '-' CTE '}' ASIGNACION expresion_arit",
"invocacion : ID_simple '(' expresion_arit ')'",
"invocacion : ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')'",
"invocacion : ID_simple '(' ')'",
"invocacion : ID_simple '(' error ')'",
"list_expre : list_expre ',' expresion_arit",
"list_expre : ',' expresion_arit",
"list_expre : list_expre ','",
"list_expre : expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
"expresion_arit : error '+' error",
"expresion_arit : error '-' error",
"expresion_arit : expresion_arit '+' error",
"expresion_arit : expresion_arit '-' error",
"expresion_arit : error termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : error '*' error",
"termino : error '/' error",
"termino : termino '*' error",
"termino : termino '/' error",
"factor : variable_simple",
"factor : CTE_con_sig",
"factor : invocacion",
"factor : variable_simple '{' CTE '}'",
"factor : variable_simple '{' '-' CTE '}'",
"variables : variables ',' variable_simple",
"variables : variables variable_simple",
"variables : variable_simple",
"variable_simple : ID_simple",
"ID_simple : ID",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' ELSE END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad bloque_else END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' error",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';' error",
"condicion : '(' '(' list_expre ')' comparador '(' list_expre ')' ')'",
"condicion : '(' list_expre ')' comparador '(' list_expre ')' ')'",
"condicion : '(' '(' list_expre ')' comparador '(' list_expre ')'",
"condicion : '(' list_expre ')' comparador '(' list_expre ')'",
"condicion : '(' expresion_arit comparador expresion_arit ')'",
"condicion : expresion_arit comparador expresion_arit ')'",
"condicion : '(' expresion_arit comparador expresion_arit",
"condicion : expresion_arit comparador expresion_arit",
"condicion : '(' list_expre ')'",
"condicion : '(' '(' list_expre ')' '(' list_expre ')' ')'",
"condicion : '(' '(' list_expre comparador '(' list_expre ')' ')'",
"condicion : '(' '(' list_expre ')' comparador list_expre ')' ')'",
"comparador : '>'",
"comparador : MAYORIGUAL",
"comparador : '<'",
"comparador : MENORIGUAL",
"comparador : '='",
"comparador : DISTINTO",
"bloque_else : bloque_else_simple",
"bloque_else : bloque_else_multiple",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables ';' END",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables END",
"bloque_else_simple : ELSE bloque_sentencia_simple",
"bloque_unidad : bloque_unidad_simple",
"bloque_unidad : bloque_unidad_multiple",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables ';' END",
"bloque_unidad_multiple : BEGIN END",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables END",
"bloque_unidad_simple : bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sent_ejecutables ';' bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sent_ejecutables bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"sentencia_WHILE : encabezado_WHILE condicion bloque_unidad",
"sentencia_WHILE : encabezado_WHILE condicion error",
"encabezado_WHILE : WHILE",
"sentencia_goto : GOTO ETIQUETA",
"sentencia_goto : GOTO error",
};

//#line 284 "Gramatica.y"
	
public static StringBuilder AMBITO = new StringBuilder(":MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>();
public static boolean RETORNO = false;
public static Map<String,Tipo> tipos = new HashMap<>();
public static boolean esWHILE = false;

private static void completarBifurcacionF(){
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+3);
	GeneradorCodigoIntermedio.addElemento(elm,pos);
}
private static void completarBifurcacionI(){
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+1);
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
	//System.out.println("  > Key buscada "+ key + "En el ambito "+ ambitoActual);

    // Buscamos en el mapa
    if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
        return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
    }
	return false;
}

private static void cargarVariables(String variables, Tipo tipo, String uso){
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
//#line 902 "Parser.java"
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
//#line 14 "Gramatica.y"
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(3).sval +"\u001B[0m");}
break;
case 2:
//#line 15 "Gramatica.y"
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(2).sval +"\u001B[0m");}
break;
case 3:
//#line 16 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 4:
//#line 17 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 5:
//#line 18 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador BEGIN "+"\u001B[0m");}
break;
case 6:
//#line 19 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 10:
//#line 27 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  "+"\u001B[0m");}
break;
case 12:
//#line 29 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 16:
//#line 37 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 17:
//#line 38 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 18:
//#line 41 "Gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 19:
//#line 42 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 20:
//#line 45 "Gramatica.y"
{ if(tipos.containsKey(val_peek(0).sval)){yyval.obj = tipos.get(val_peek(0).sval);
					}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido "+"\u001B[0m");};}
break;
case 21:
//#line 47 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 22:
//#line 50 "Gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 23:
//#line 52 "Gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 24:
//#line 56 "Gramatica.y"
{if(val_peek(5).obj != null){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 25:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 26:
//#line 58 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 27:
//#line 59 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 28:
//#line 60 "Gramatica.y"
{if(val_peek(2).obj != null){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 29:
//#line 61 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
break;
case 30:
//#line 62 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 31:
//#line 63 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
break;
case 32:
//#line 64 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 33:
//#line 65 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 34:
//#line 66 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 35:
//#line 67 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 36:
//#line 68 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 37:
//#line 69 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 38:
//#line 70 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 39:
//#line 71 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 40:
//#line 75 "Gramatica.y"
{	if(RETORNO==false)
										{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); 
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal(val_peek(4).sval,(Tipo)val_peek(3).obj);
								}
break;
case 41:
//#line 83 "Gramatica.y"
{yyval.sval=val_peek(0).sval;cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj," nombre de funcion ");agregarAmbito(val_peek(0).sval);DENTRODELAMBITO.push(val_peek(0).sval);System.out.println(" Encabezado de la funcion ");}
break;
case 42:
//#line 84 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 43:
//#line 87 "Gramatica.y"
{yyval.obj=val_peek(2).obj;cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}
break;
case 44:
//#line 88 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");}
break;
case 45:
//#line 89 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
break;
case 46:
//#line 90 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 47:
//#line 91 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
break;
case 49:
//#line 97 "Gramatica.y"
{if(!existeFuncion()){System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");}}
break;
case 50:
//#line 98 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  "+"\u001B[0m");
						if(!existeFuncion())
										{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");
						}}
break;
case 55:
//#line 109 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una ETIQUETA ");System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 57:
//#line 111 "Gramatica.y"
{RETORNO = true;}
break;
case 58:
//#line 114 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 59:
//#line 115 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 60:
//#line 116 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 61:
//#line 117 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 62:
//#line 120 "Gramatica.y"
{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;
															GeneradorCodigoIntermedio.addElemento(val_peek(2).sval);
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");}
															System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 63:
//#line 127 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 64:
//#line 128 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
break;
case 65:
//#line 131 "Gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){
													System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}
													else{														
														System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}																										
												}
break;
case 66:
//#line 137 "Gramatica.y"
{if(!fueDeclarado(val_peek(6).sval)){
													System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}
													else{
														System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
												}
break;
case 67:
//#line 142 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 68:
//#line 143 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
break;
case 70:
//#line 147 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
break;
case 71:
//#line 148 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
break;
case 73:
//#line 152 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("+"); }
break;
case 74:
//#line 153 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("-"); }
break;
case 76:
//#line 155 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 77:
//#line 156 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 78:
//#line 157 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 79:
//#line 158 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 80:
//#line 159 "Gramatica.y"
{GeneradorCodigoIntermedio.imprimirPolaca();System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
break;
case 81:
//#line 162 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("*");}
break;
case 82:
//#line 163 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("/");}
break;
case 84:
//#line 165 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 85:
//#line 166 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 86:
//#line 167 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 87:
//#line 168 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 88:
//#line 171 "Gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 89:
//#line 172 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 91:
//#line 174 "Gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ yyval.sval = val_peek(3).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 92:
//#line 175 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
break;
case 93:
//#line 177 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 94:
//#line 178 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
break;
case 95:
//#line 179 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 98:
//#line 189 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 99:
//#line 190 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 100:
//#line 193 "Gramatica.y"
{completarBifurcacionI();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 101:
//#line 194 "Gramatica.y"
{completarBifurcacionI();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 102:
//#line 195 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 103:
//#line 196 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 104:
//#line 197 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 105:
//#line 200 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN "+"\u001B[0m");}
break;
case 106:
//#line 201 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  "+"\u001B[0m");}
break;
case 107:
//#line 202 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  "+"\u001B[0m");}
break;
case 108:
//#line 203 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
break;
case 109:
//#line 205 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 110:
//#line 206 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 111:
//#line 209 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(4).sval);GeneradorCodigoIntermedio.bifurcarF();System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 112:
//#line 210 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 113:
//#line 211 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 114:
//#line 212 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 115:
//#line 213 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(2).sval);GeneradorCodigoIntermedio.bifurcarF(); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 116:
//#line 214 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 117:
//#line 215 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 118:
//#line 216 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 119:
//#line 218 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion "+"\u001B[0m");}
break;
case 120:
//#line 220 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 121:
//#line 221 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
break;
case 122:
//#line 222 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
break;
case 123:
//#line 225 "Gramatica.y"
{yyval.sval=">";}
break;
case 124:
//#line 226 "Gramatica.y"
{yyval.sval=">=";}
break;
case 125:
//#line 227 "Gramatica.y"
{yyval.sval="<";}
break;
case 126:
//#line 228 "Gramatica.y"
{yyval.sval="<=";}
break;
case 127:
//#line 229 "Gramatica.y"
{yyval.sval="=";}
break;
case 128:
//#line 230 "Gramatica.y"
{yyval.sval="!=";}
break;
case 132:
//#line 238 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 134:
//#line 244 "Gramatica.y"
{if(esWHILE==false){completarBifurcacionF();GeneradorCodigoIntermedio.bifurcarI();}else{completarBifurcacionF();GeneradorCodigoIntermedio.bifurcarAlInicio();}}
break;
case 135:
//#line 245 "Gramatica.y"
{if(esWHILE==false){completarBifurcacionF();GeneradorCodigoIntermedio.bifurcarI();}else{completarBifurcacionF();GeneradorCodigoIntermedio.bifurcarAlInicio();}}
break;
case 137:
//#line 249 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
break;
case 138:
//#line 250 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 142:
//#line 258 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 144:
//#line 264 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una Cadena MultiLinea ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 145:
//#line 269 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 146:
//#line 270 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 147:
//#line 273 "Gramatica.y"
{esWHILE=true;GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());}
break;
case 148:
//#line 277 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 149:
//#line 278 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1556 "Parser.java"
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
