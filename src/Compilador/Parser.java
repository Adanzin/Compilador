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
//#line 20 "Parser.java"




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
    0,    0,    0,    0,    0,    2,    2,    3,    3,    3,
    4,    4,    4,    4,    4,    6,    6,    9,    9,   11,
   11,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    7,   13,   13,
   14,   14,   14,   14,   14,   15,   16,    5,    5,    5,
    5,    5,    5,    5,   22,   22,   22,   22,   18,   18,
   25,   25,   25,   25,   26,   26,   17,   17,   17,   17,
   17,   17,   17,   17,   27,   27,   27,   27,   27,   27,
   27,   28,   28,   28,   28,   10,   10,   10,   24,    1,
   12,   12,   12,   12,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   32,   32,   32,
   32,   32,   32,   31,   31,   34,   34,   33,   30,   30,
   38,   38,   38,   37,   35,   35,   35,   36,   23,   20,
   20,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    1,    1,    2,    1,
    1,    2,    2,    1,    1,    3,    3,    1,    1,    1,
    1,    9,    8,    8,    7,    6,    8,    7,    8,    6,
    8,    8,    5,    5,    5,    4,    6,    5,    3,    2,
    4,    3,    3,    2,    3,    1,    4,    1,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    7,    3,    4,    3,    1,    3,    3,    1,    3,
    3,    3,    3,    2,    3,    3,    1,    3,    3,    3,
    3,    1,    1,    1,    4,    3,    2,    1,    1,    1,
    1,    2,    1,    2,    8,    6,    6,    4,    7,    7,
    5,    7,    6,    6,    8,    9,    8,    8,    7,    5,
    4,    4,    3,    8,    3,    8,    8,    1,    1,    1,
    1,    1,    1,    1,    1,    5,    4,    2,    1,    1,
    4,    2,    3,    1,    3,    1,    2,    1,    1,    3,
    3,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   90,    0,    0,    0,    0,    0,    0,    0,    0,
   52,   20,   21,    0,    0,    7,    8,    0,   11,    0,
    0,    0,   19,    0,   54,   48,   49,   50,   51,   53,
    0,    0,    0,    0,   91,   93,    0,    0,    0,   83,
    0,    0,   84,    0,   77,    0,    0,    0,    0,    0,
    0,    0,    0,  143,  142,    2,    6,    9,   12,   13,
    0,   89,    0,   88,    0,    0,    0,    0,    0,    4,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   94,    0,  119,  121,  123,  120,  118,    0,    0,
  122,    0,    0,    0,    0,    0,  139,   56,    0,    0,
    0,    0,    0,    0,   18,    0,    0,    0,    0,    0,
  141,    0,  138,  140,  134,  129,  130,   39,   17,   16,
    0,   87,    0,   44,    0,    0,    0,    0,    0,    1,
   70,   71,   78,   79,  115,    0,    0,    0,    0,    0,
    0,   63,    0,    0,    0,    0,    0,    0,    0,    0,
   80,   75,   81,   76,    0,   98,    0,    0,  124,  125,
   58,   55,   57,    0,    0,    0,   36,    0,    0,    0,
    0,   47,  132,    0,  136,   86,   45,   43,   42,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
   61,  111,   85,    0,  128,  101,    0,    0,    0,    0,
    0,   35,   34,    0,   33,    0,    0,    0,  133,    0,
  137,   41,   38,    0,    0,    0,    0,  110,    0,    0,
    0,  104,    0,   96,    0,  103,    0,   97,    0,   37,
   26,    0,    0,    0,   30,    0,    0,  131,  135,    0,
    0,    0,    0,    0,    0,    0,  127,    0,   99,  102,
    0,  100,    0,    0,    0,    0,   28,    0,    0,    0,
    0,    0,    0,   62,  126,  105,   95,   31,   32,   27,
   29,    0,   23,  114,    0,  117,  116,  107,   22,  106,
};
final static short yydgoto[] = {                          3,
   39,   15,   16,   17,   18,   19,   20,   21,   22,   63,
   23,   40,   24,   66,  182,   25,  136,   26,   27,   28,
   29,   30,  102,   42,   43,   80,   44,   45,   46,  114,
  158,   92,  159,  160,  174,  115,  116,  117,
};
final static short yysindex[] = {                      -209,
  815,    0,    0,  703,  -40,   13,  229,   49,  -40, -201,
    0,    0,    0,    0,  757,    0,    0,   28,    0,   43,
   68, -147,    0,   88,    0,    0,    0,    0,    0,    0,
 -110,  815,  780,  556,    0,    0,  -38, -239,   95,    0,
  557,   10,    0,  -17,    0, -109,  271,  371, -158, -158,
 -129,  227,  892,    0,    0,    0,    0,    0,    0,    0,
 -112,    0,  -36,    0,  -23,  -96,  227, -102,  798,    0,
    7,  -85, -230,  -77,  -70,  -17,  512,  227,  557,   82,
    0,    0,  294,    0,    0,    0,    0,    0,  384,  467,
    0,  227,  -80,  561,  578,  909,    0,    0,   95,  365,
   60,  147, -106, -158,    0,  -56,   79,  131,  -87,  102,
    0,  931,    0,    0,    0,    0,    0,    0,    0,    0,
  -64,    0,  173,    0,  174,  -37,  815,  -16,   94,    0,
    0,    0,    0,    0,    0,  -16,  564,  227,  227,  485,
  535,    0,  195,  129,    7,  -17,    7,  -17,  317,  114,
    0,    0,    0,    0,  945,    0,  -49,  259,    0,    0,
    0,    0,    0,   74,  -51,  -64,    0,  -14,  -64,  -14,
  -24,    0,    0,  639,    0,    0,    0,    0,    0,  296,
  815,  103,  108,  -28,  368,  340,  -16,  370,    0,  227,
    0,    0,    0,  233,    0,    0, -182,  -21,  165,  316,
 -192,    0,    0,  399,    0,  406,  -29,  410,    0,  953,
    0,    0,    0,  227,  227,  -31,  227,    0,  227,  478,
  658,    0,  923,    0,  236,    0,  197,    0,  108,    0,
    0,  -14,  -14,  -14,    0,  -25,  -14,    0,    0,  -16,
  107,  227,  122,  151,  420,  419,    0,  967,    0,    0,
 -183,    0,  342,  353,  355,  189,    0,  359,  424,  444,
  448,  450,  461,    0,    0,    0,    0,    0,    0,    0,
    0,  379,    0,    0,  469,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -71,    0,    0,    0,  252,    0,  404,
  476,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  514,    0,    0,    0,    0,    0,    1,    0,
    0,   24,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  486,    0,    0,    0,    0,    0,    0,    0,  531,    0,
    0,    0,    0,    0,    0,   70,    0,    0,  492,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  543,    0,
    0,    0,  522,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  185,    0,    0,
    0,    0,    0,    0,    0,  618,    0,    0,    0,    0,
    0,    0,    0,    0,   93,  116,  139,  162,  596,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  281,    0,    0,    0,    0,  838,  726,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  568,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  433,    0,    0,
    0,    0,  856,    0,    0,    0,    0,    0,    0,    0,
    0,  452,    0,    0,  874,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  751,  164,   89,    0,  479,    0,    0,    0,    8,    0,
  -43,  776,    0,    0,    0,    0,  866,    0,    0,    0,
    0,    0,    0,  774,    0,  -75,    5,    3,  547,  456,
 -122,  -59,    0,    0,  364,  768,    0,    0,
};
final static int YYTABLESIZE=1240;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   89,   78,  137,  179,   38,  166,   38,  121,  242,  197,
  201,  215,   68,   38,  234,   38,  164,  124,  256,  138,
   38,  126,  120,   82,   94,  132,   89,   81,   90,   95,
   38,   87,   91,   88,  198,  170,   81,  227,   76,  143,
   82,   89,   89,   89,   89,   89,   69,   89,   74,   82,
    1,   89,   47,   75,   54,  106,  107,  108,    2,   89,
   89,   89,   89,  230,   82,   82,   82,   82,   82,   74,
   82,   55,  266,  222,  225,    2,  223,  185,  267,  224,
  188,   76,   82,   82,   82,   82,   58,   69,   52,   69,
   69,   69,   72,  146,  148,  235,  152,  154,  207,  257,
  162,   59,   89,   57,   90,   69,   69,   69,   69,    2,
   74,  165,   74,   74,   74,   67,  171,   61,   12,   13,
    2,   57,  140,   89,  216,  139,   60,   65,   74,   74,
   74,   74,   93,   72,   83,   72,   72,   72,   73,  241,
  243,  244,  172,  245,   89,   76,   90,  259,   96,  109,
  139,   72,   72,   72,   72,  118,   67,   57,   67,   67,
   67,   68,  261,  127,  129,  139,  260,   33,   67,  191,
  131,   89,   67,   90,   67,   67,   67,   67,  133,   73,
    2,   73,   73,   73,   59,  134,  150,  163,   57,   12,
   13,  262,  169,   18,  139,   69,   18,   73,   73,   73,
   73,  168,   68,    2,   68,   68,   68,   89,   60,  155,
  100,    2,  196,  177,  178,   34,    2,   77,  183,  119,
   68,   68,   68,   68,   34,   59,   35,    2,   35,    2,
    2,    2,  123,   38,  190,   35,    2,   35,  193,   36,
  226,   36,   35,   59,    2,   84,   85,   86,   36,   60,
   36,   10,   35,   12,   13,   36,   89,   89,   89,   89,
   89,   89,   89,   89,   89,   36,   89,   60,   89,   57,
   89,   38,   89,   89,   89,   89,   89,   89,   89,   82,
   82,   82,   82,   82,   82,   82,   82,   82,   50,   82,
  181,   82,   10,   82,  251,   82,   82,   82,   82,   82,
   82,   82,   69,   69,   69,   69,   69,   69,   69,   69,
   69,   98,   69,  271,   69,   38,   69,  199,   69,   69,
   69,   69,   69,   69,   69,   74,   74,   74,   74,   74,
   74,   74,   74,   74,  142,   74,  212,   74,   38,   74,
  200,   74,   74,   74,   74,   74,   74,   74,   72,   72,
   72,   72,   72,   72,   72,   72,   72,  192,   72,   89,
   72,   90,   72,  213,   72,   72,   72,   72,   72,   72,
   72,   67,   67,   67,   67,   67,   67,   67,   67,   67,
  218,   67,   89,   67,   90,   67,  214,   67,   67,   67,
   67,   67,   67,   67,   73,   73,   73,   73,   73,   73,
   73,   73,   73,   14,   73,  161,   73,  217,   73,  219,
   73,   73,   73,   73,   73,   73,   73,   68,   68,   68,
   68,   68,   68,   68,   68,   68,  228,   68,   38,   68,
  104,   68,   25,   68,   68,   68,   68,   68,   68,   68,
  229,   59,  232,   59,   14,   59,   59,   59,   59,  233,
   59,   24,   59,  237,   59,   35,   59,   59,  252,  264,
  263,   59,   59,  139,  274,   60,  268,   60,   36,   60,
   60,   60,   60,   25,   60,   15,   60,  269,   60,  270,
   60,   60,   34,  273,  275,   60,   60,  139,  276,    5,
  277,   25,   24,   35,    2,    6,    2,  250,    8,   48,
    2,  278,    9,  279,   10,   11,   36,   49,   10,  280,
   24,   38,   10,    5,   10,   10,   15,   10,  246,   10,
   89,   10,   90,   10,   10,   40,   34,    5,   10,   10,
    3,  113,   66,    6,    7,   66,    8,   35,    2,   97,
    9,   46,   10,   11,   87,   91,   88,   12,   13,  141,
   36,  157,  135,   74,   72,   53,   73,  221,   75,    0,
   35,    2,   82,   82,   82,    0,   82,    0,   82,    0,
   12,   13,    0,   36,  113,  189,   74,   72,    0,   73,
    0,   75,    0,   89,   89,   89,    0,   89,    0,   89,
  113,    0,    0,    0,    0,    0,    0,   74,   72,   89,
   73,   90,   75,    0,  184,   38,    0,  139,   85,   85,
   85,    0,   85,    0,   85,    0,   87,   91,   88,    0,
    0,    5,   38,   87,   91,   88,    0,    6,    7,    0,
    8,    0,    2,  113,    9,    0,   10,   11,    2,  145,
    0,   12,   13,    0,    0,    0,    0,   12,   13,    0,
   35,    2,  113,    0,    0,    0,    0,    0,   66,    0,
   14,   66,    0,   36,   14,   89,   14,   14,    0,   14,
    0,   14,  113,   14,    0,   14,   14,   66,   66,   66,
   14,   14,    0,    0,    0,    0,    0,    0,  113,   25,
    0,    0,    0,   25,    0,   25,   25,  210,   25,  113,
   25,  113,   25,    0,   25,   25,    0,    0,   24,   25,
   25,    0,   24,    0,   24,   24,  248,   24,    0,   24,
    0,   24,  147,   24,   24,    0,  113,    0,   24,   24,
    0,    0,   15,   35,    2,    0,   15,    0,   15,   15,
    0,   15,    0,   15,    0,   15,   36,   15,   15,    0,
    4,   14,   15,   15,   14,    0,    0,   51,   84,   85,
   86,    0,    0,    0,    0,   14,   65,   71,    0,   65,
    0,    0,   62,    0,   31,    0,    0,   31,   35,    2,
    0,    0,   14,   14,    0,   65,   65,   65,   31,    0,
   71,   36,    0,    0,    0,   64,    0,   99,  105,  105,
  105,   35,    2,   62,    0,   31,   31,   18,    0,    0,
   18,   71,    0,   62,   36,  125,  151,    0,    0,   14,
  103,   89,   35,    2,    0,    0,   31,   35,    2,    0,
   84,   85,   86,  153,    0,   36,  122,   84,   85,   86,
   36,    0,   31,    0,   35,    2,   62,    0,    0,    0,
   14,  113,  113,  113,  105,  113,  167,   36,  113,  105,
    0,  113,   62,  113,    0,  113,    0,  113,  113,   31,
   41,   62,    0,   31,   41,    0,  180,   14,    0,  175,
    0,    0,    0,    0,    0,   31,    0,    0,    0,    0,
    0,   66,   66,   66,  176,    5,    0,    0,    0,  209,
   31,    6,   79,    0,    8,   62,    2,    0,    9,    0,
   10,   11,  101,    0,    5,  202,  203,  110,  247,  205,
    6,    0,  195,    8,   62,    2,    0,    9,   31,   10,
   11,   14,  128,    0,    0,    0,    0,    0,    0,    0,
    0,  211,    0,  204,   62,  206,  208,   31,  144,    0,
    0,  231,    0,    0,   31,    0,    0,  149,    0,    5,
   62,  175,   32,    0,    0,    6,    7,   31,    8,    0,
    2,   62,    9,   62,   10,   11,    0,  239,    0,   12,
   13,    0,  236,   31,    0,    0,    0,    0,  211,    0,
  195,    0,    0,    0,   31,    0,   31,    0,   62,   65,
   65,   65,    0,  186,  187,    0,    0,  253,  254,  255,
    0,    0,  258,    5,    0,  239,    0,   56,    0,    6,
    7,   31,    8,    0,    2,    0,    9,    0,   10,   11,
    0,  272,    0,   12,   13,    0,    5,    0,    0,    0,
   70,    0,    6,    7,    0,    8,    0,    2,    0,    9,
    0,   10,   11,    0,    5,  220,   12,   13,  130,    0,
    6,    7,    0,    8,    0,    2,    0,    9,    0,   10,
   11,    5,    0,    0,   12,   13,    0,    6,    7,  240,
    8,    0,    2,    0,    9,    0,   10,   11,    0,    0,
    0,   12,   13,  112,  112,  112,    0,  112,    0,    0,
  112,    0,    0,  112,    0,  112,    0,  112,    0,  112,
  112,  109,  109,  109,    0,  109,    0,    0,  109,    0,
    0,  109,    0,  109,    0,  109,    0,  109,  109,  108,
  108,  108,    0,  108,    0,    0,  108,    0,    0,  108,
    0,  108,    0,  108,    0,  108,  108,  111,    5,    0,
    0,  112,    0,    0,    6,    0,    0,    8,    0,    2,
    0,    9,    0,   10,   11,    5,    0,  155,  112,    0,
  156,    6,    0,    0,    8,    0,    2,    0,    9,    5,
   10,   11,  194,    0,  249,    6,    0,    5,    8,    0,
    2,  173,    9,    6,   10,   11,    8,    0,    2,    0,
    9,    5,   10,   11,  194,    0,    0,    6,    0,    5,
    8,    0,    2,  238,    9,    6,   10,   11,    8,    0,
    2,    0,    9,    5,   10,   11,    0,  265,    0,    6,
    0,    0,    8,    0,    2,    0,    9,    0,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   78,   41,   45,   62,   45,   44,   40,   59,
   62,   40,  123,   45,   44,   45,  123,   41,   44,   79,
   45,   65,   59,    0,   42,  256,   43,  267,   45,   47,
   45,   60,   61,   62,  157,  123,  267,   59,   34,   83,
  280,   41,   42,   43,   44,   45,    0,   47,   42,  280,
  260,  123,   40,   47,  256,   48,   49,   50,  268,   59,
   60,   61,   62,  256,   41,   42,   43,   44,   45,    0,
   47,  273,  256,  256,  197,  268,  259,  137,  262,  262,
  140,   77,   59,   60,   61,   62,   59,   41,   40,   43,
   44,   45,    0,   89,   90,  125,   94,   95,  123,  125,
   41,   59,   43,   15,   45,   59,   60,   61,   62,  268,
   41,  104,   43,   44,   45,    0,  109,  265,  277,  278,
  268,   33,   41,  123,  184,   44,   59,   40,   59,   60,
   61,   62,  123,   41,   40,   43,   44,   45,    0,  215,
  216,  217,   41,  219,   43,  141,   45,   41,  258,  279,
   44,   59,   60,   61,   62,  268,   41,   69,   43,   44,
   45,    0,   41,  260,  267,   44,  242,    4,  279,   41,
  256,   43,  279,   45,   59,   60,   61,   62,  256,   41,
  268,   43,   44,   45,    0,  256,  267,   41,  100,  277,
  278,   41,   62,  265,   44,   32,  268,   59,   60,   61,
   62,  123,   41,  268,   43,   44,   45,  279,    0,  259,
   47,  268,  262,   41,   41,  256,  268,  256,  125,  256,
   59,   60,   61,   62,  256,   41,  267,  268,  267,  268,
  268,  268,  256,   45,   40,  267,  268,  267,  125,  280,
  262,  280,  267,   59,  268,  274,  275,  276,  280,   41,
  280,    0,  267,  277,  278,  280,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  280,  266,   59,  268,  181,
  270,   45,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   60,  266,
  127,  268,   41,  270,   59,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,  259,  260,  261,  262,  263,
  264,   41,  266,  125,  268,   45,  270,   59,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   41,  268,   45,  270,
  267,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   41,  266,   43,
  268,   45,  270,  261,  272,  273,  274,  275,  276,  277,
  278,  256,  257,  258,  259,  260,  261,  262,  263,  264,
   41,  266,   43,  268,   45,  270,  279,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  258,  259,  260,  261,
  262,  263,  264,    0,  266,   41,  268,   40,  270,   40,
  272,  273,  274,  275,  276,  277,  278,  256,  257,  258,
  259,  260,  261,  262,  263,  264,  262,  266,   45,  268,
   60,  270,    0,  272,  273,  274,  275,  276,  277,  278,
  125,  257,   44,  259,   41,  261,  262,  263,  264,   44,
  266,    0,  268,   44,  270,  267,  272,  273,  262,   41,
   41,  277,  278,   44,   41,  257,  125,  259,  280,  261,
  262,  263,  264,   41,  266,    0,  268,  125,  270,  125,
  272,  273,  256,  125,   41,  277,  278,   44,   41,  257,
   41,   59,   41,  267,  268,  263,  268,  262,  266,  271,
  268,   41,  270,  125,  272,  273,  280,  279,  257,   41,
   59,   45,  261,    0,  263,  264,   41,  266,   41,  268,
   43,  270,   45,  272,  273,   40,  256,  257,  277,  278,
    0,   53,   41,  263,  264,   44,  266,  267,  268,  269,
  270,  261,  272,  273,   60,   61,   62,  277,  278,  256,
  280,   96,   41,   42,   43,    9,   45,  194,   47,   -1,
  267,  268,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  277,  278,   -1,  280,   96,   41,   42,   43,   -1,   45,
   -1,   47,   -1,   41,   42,   43,   -1,   45,   -1,   47,
  112,   -1,   -1,   -1,   -1,   -1,   -1,   42,   43,   43,
   45,   45,   47,   -1,   41,   45,   -1,   44,   41,   42,
   43,   -1,   45,   -1,   47,   -1,   60,   61,   62,   -1,
   -1,  257,   45,   60,   61,   62,   -1,  263,  264,   -1,
  266,   -1,  268,  155,  270,   -1,  272,  273,  268,  256,
   -1,  277,  278,   -1,   -1,   -1,   -1,  277,  278,   -1,
  267,  268,  174,   -1,   -1,   -1,   -1,   -1,   41,   -1,
  257,   44,   -1,  280,  261,  123,  263,  264,   -1,  266,
   -1,  268,  194,  270,   -1,  272,  273,   60,   61,   62,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  210,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   59,  266,  221,
  268,  223,  270,   -1,  272,  273,   -1,   -1,  257,  277,
  278,   -1,  261,   -1,  263,  264,   59,  266,   -1,  268,
   -1,  270,  256,  272,  273,   -1,  248,   -1,  277,  278,
   -1,   -1,  257,  267,  268,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,  280,  272,  273,   -1,
    0,    1,  277,  278,    4,   -1,   -1,    7,  274,  275,
  276,   -1,   -1,   -1,   -1,   15,   41,  256,   -1,   44,
   -1,   -1,   22,   -1,    1,   -1,   -1,    4,  267,  268,
   -1,   -1,   32,   33,   -1,   60,   61,   62,   15,   -1,
  256,  280,   -1,   -1,   -1,   22,   -1,   47,   48,   49,
   50,  267,  268,   53,   -1,   32,   33,  265,   -1,   -1,
  268,  256,   -1,   63,  280,   65,  256,   -1,   -1,   69,
   47,  279,  267,  268,   -1,   -1,   53,  267,  268,   -1,
  274,  275,  276,  256,   -1,  280,   63,  274,  275,  276,
  280,   -1,   69,   -1,  267,  268,   96,   -1,   -1,   -1,
  100,  256,  257,  258,  104,  260,  106,  280,  263,  109,
   -1,  266,  112,  268,   -1,  270,   -1,  272,  273,   96,
    5,  121,   -1,  100,    9,   -1,  126,  127,   -1,  112,
   -1,   -1,   -1,   -1,   -1,  112,   -1,   -1,   -1,   -1,
   -1,  274,  275,  276,  121,  257,   -1,   -1,   -1,  261,
  127,  263,   37,   -1,  266,  155,  268,   -1,  270,   -1,
  272,  273,   47,   -1,  257,  165,  166,   52,  261,  169,
  263,   -1,  155,  266,  174,  268,   -1,  270,  155,  272,
  273,  181,   67,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  174,   -1,  168,  194,  170,  171,  174,   83,   -1,
   -1,  201,   -1,   -1,  181,   -1,   -1,   92,   -1,  257,
  210,  194,  260,   -1,   -1,  263,  264,  194,  266,   -1,
  268,  221,  270,  223,  272,  273,   -1,  210,   -1,  277,
  278,   -1,  207,  210,   -1,   -1,   -1,   -1,  221,   -1,
  223,   -1,   -1,   -1,  221,   -1,  223,   -1,  248,  274,
  275,  276,   -1,  138,  139,   -1,   -1,  232,  233,  234,
   -1,   -1,  237,  257,   -1,  248,   -1,  261,   -1,  263,
  264,  248,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  256,   -1,  277,  278,   -1,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,  257,  190,  277,  278,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  257,   -1,   -1,  277,  278,   -1,  263,  264,  214,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,
   -1,  277,  278,  256,  257,  258,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,   -1,
   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  257,   -1,  259,  260,   -1,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,
  272,  273,  260,   -1,  262,  263,   -1,  257,  266,   -1,
  268,  261,  270,  263,  272,  273,  266,   -1,  268,   -1,
  270,  257,  272,  273,  260,   -1,   -1,  263,   -1,  257,
  266,   -1,  268,  261,  270,  263,  272,  273,  266,   -1,
  268,   -1,  270,  257,  272,  273,   -1,  261,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
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
"programa : BEGIN sentencias END",
"programa : ID_simple BEGIN sentencias",
"programa : ID_simple sentencias END",
"programa : ID_simple sentencias",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
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
"invocacion : ID_simple '(' expresion_arit ')'",
"invocacion : ID_simple '(' tipo_primitivo '(' expresion_arit ')' ')'",
"invocacion : ID_simple '(' ')'",
"invocacion : ID_simple '(' error ')'",
"list_expre : list_expre ',' expresion_arit",
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
"variables : variables ',' variable_simple",
"variables : variables variable_simple",
"variables : variable_simple",
"variable_simple : ID_simple",
"ID_simple : ID",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
"CTE_con_sig : ERROR",
"CTE_con_sig : '-' ERROR",
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
"condicion : '(' '(' list_expre ')' '(' list_expre ')' ')'",
"condicion : '(' error ')'",
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
"sentencia_WHILE : WHILE condicion bloque_unidad",
"sentencia_WHILE : WHILE condicion error",
"sentencia_goto : GOTO ETIQUETA",
"sentencia_goto : GOTO error",
};

//#line 250 "Gramatica.y"
	
private static StringBuilder AMBITO = new StringBuilder(":MAIN");																 
private static boolean RETORNO = false;

private static void  idEsUnTipo(String id){
	AnalizadorLexico.TablaDeSimbolos.get(id).setEsTipo(true);
}

private static void cargarVariables(String variables, Object tipo){
	String[] var = getVariables(variables);
	for (String v : var) {
		addAmbitoID(v);
		addTipo(v+AMBITO.toString(),tipo);
    }

}

private static String[] getVariables(String var) {
        // Usa split() para dividir el String por el carácter '/'
        String[] variables = var.split("/");
        return variables;
}

private static void addTipo(String id, Object tipo) {
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
	 yyerror(string);
}

private static void cambioCTENegativa(String key) {
	String keyNeg = "-" + key;
	if(AnalizadorLexico.TablaDeSimbolos.get(key) != null){
		if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
			AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
		}
		AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
		 yyerror("Linea " + AnalizadorLexico.saltoDeLinea +  " se reconocio token negativo ");
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

//#line 766 "Parser.java"
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
//#line 11 "Gramatica.y"
{ yyerror("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(3).sval +"\u001B[0m");}
break;
case 2:
//#line 12 "Gramatica.y"
{ yyerror("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 3:
//#line 13 "Gramatica.y"
{ yyerror("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 4:
//#line 14 "Gramatica.y"
{ yyerror("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador BEGIN "+"\u001B[0m");}
break;
case 5:
//#line 15 "Gramatica.y"
{ yyerror("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 10:
//#line 24 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 14:
//#line 32 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 15:
//#line 33 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 16:
//#line 36 "Gramatica.y"
{cargarVariables(val_peek(1).sval,val_peek(2).obj);  yyerror("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 17:
//#line 37 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 18:
//#line 40 "Gramatica.y"
{ yyval.obj = new Tipo(val_peek(0).sval); idEsUnTipo(val_peek(0).sval);}
break;
case 19:
//#line 41 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 20:
//#line 44 "Gramatica.y"
{ yyval.obj = new Tipo("integer"); }
break;
case 21:
//#line 45 "Gramatica.y"
{ yyval.obj = new Tipo("double"); }
break;
case 22:
//#line 48 "Gramatica.y"
{ yyerror("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}
break;
case 23:
//#line 49 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 24:
//#line 50 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 25:
//#line 51 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 26:
//#line 52 "Gramatica.y"
{ yyerror("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}
break;
case 27:
//#line 53 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
break;
case 28:
//#line 54 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 29:
//#line 55 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
break;
case 30:
//#line 56 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 31:
//#line 57 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 32:
//#line 58 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 33:
//#line 59 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 34:
//#line 60 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 35:
//#line 61 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 36:
//#line 62 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 37:
//#line 63 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 38:
//#line 67 "Gramatica.y"
{	if(RETORNO==false)
										{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;}  yyerror(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); 
									sacarAmbito();
								}
break;
case 39:
//#line 73 "Gramatica.y"
{addTipo(val_peek(0).sval,val_peek(2).obj);addAmbitoID(val_peek(0).sval);agregarAmbito(val_peek(0).sval); yyerror(" Encabezado de la funcion ");}
break;
case 40:
//#line 74 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 42:
//#line 78 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");}
break;
case 43:
//#line 79 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
break;
case 44:
//#line 80 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 45:
//#line 81 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
break;
case 52:
//#line 95 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 54:
//#line 97 "Gramatica.y"
{RETORNO = true;}
break;
case 55:
//#line 100 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 56:
//#line 101 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 57:
//#line 102 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 58:
//#line 103 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 59:
//#line 106 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 60:
//#line 107 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 61:
//#line 110 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
break;
case 62:
//#line 111 "Gramatica.y"
{ yyerror("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 63:
//#line 112 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 64:
//#line 113 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
break;
case 70:
//#line 123 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 71:
//#line 124 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 72:
//#line 125 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 73:
//#line 126 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 74:
//#line 127 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
break;
case 78:
//#line 133 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 79:
//#line 134 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 80:
//#line 135 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 81:
//#line 136 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 86:
//#line 144 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 87:
//#line 145 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
break;
case 88:
//#line 146 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 91:
//#line 156 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 92:
//#line 157 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 95:
//#line 162 "Gramatica.y"
{ yyerror("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 96:
//#line 163 "Gramatica.y"
{ yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 97:
//#line 164 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 98:
//#line 165 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 99:
//#line 166 "Gramatica.y"
{{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 100:
//#line 168 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 101:
//#line 169 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 102:
//#line 170 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 103:
//#line 171 "Gramatica.y"
{ yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
break;
case 104:
//#line 173 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 105:
//#line 174 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 106:
//#line 177 "Gramatica.y"
{yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 107:
//#line 178 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 108:
//#line 179 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 109:
//#line 180 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 110:
//#line 181 "Gramatica.y"
{yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 111:
//#line 182 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 112:
//#line 183 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 113:
//#line 184 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 114:
//#line 187 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 115:
//#line 188 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 116:
//#line 189 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
break;
case 117:
//#line 190 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
break;
case 127:
//#line 206 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 132:
//#line 217 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
break;
case 133:
//#line 218 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 137:
//#line 226 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 139:
//#line 232 "Gramatica.y"
{yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 140:
//#line 237 "Gramatica.y"
{yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 141:
//#line 238 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 142:
//#line 243 "Gramatica.y"
{yyerror("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 143:
//#line 244 "Gramatica.y"
{yyerror("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1298 "Parser.java"
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
