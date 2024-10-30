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
//#line 22 "Parser.java"




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
    3,    4,    4,    4,    4,    4,    6,    6,    9,    9,
   11,   11,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    7,   13,
   13,   14,   14,   14,   14,   14,   15,   16,    5,    5,
    5,    5,    5,    5,    5,   22,   22,   22,   22,   18,
   18,   25,   25,   25,   25,   26,   26,   17,   17,   17,
   17,   17,   17,   17,   17,   27,   27,   27,   27,   27,
   27,   27,   28,   28,   28,   28,   10,   10,   10,   24,
    1,   12,   12,   12,   12,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   32,   32,
   32,   32,   32,   32,   31,   31,   34,   34,   33,   30,
   30,   38,   38,   38,   37,   35,   35,   35,   36,   23,
   20,   20,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    1,    1,    2,
    1,    1,    2,    2,    1,    1,    3,    3,    1,    1,
    1,    1,    9,    8,    8,    7,    6,    8,    7,    8,
    6,    8,    8,    5,    5,    5,    4,    6,    5,    3,
    2,    4,    3,    3,    2,    3,    1,    4,    1,    1,
    1,    1,    1,    1,    1,    4,    3,    4,    4,    3,
    6,    4,    7,    3,    4,    3,    1,    3,    3,    1,
    3,    3,    3,    3,    2,    3,    3,    1,    3,    3,
    3,    3,    1,    1,    1,    4,    3,    2,    1,    1,
    1,    1,    2,    1,    2,    8,    6,    6,    4,    7,
    7,    5,    7,    6,    6,    8,    9,    8,    8,    7,
    5,    4,    4,    3,    8,    3,    8,    8,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    2,    1,
    1,    4,    2,    3,    1,    3,    1,    2,    1,    1,
    3,    3,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   91,    0,    0,    0,    0,    0,    0,    0,    0,
   53,   21,   22,    0,    0,    8,    9,    0,   12,    0,
    0,    0,   20,    0,   55,   49,   50,   51,   52,   54,
    0,    0,    0,    0,   92,   94,    0,    0,    0,   84,
    0,    0,   85,    0,   78,    0,    0,    0,    0,    0,
    0,    0,    0,  144,  143,    3,    7,   10,   13,   14,
    0,   90,    0,   89,    0,    0,    0,    0,    2,    0,
    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   93,   95,    0,  120,  122,  124,  121,  119,    0,
    0,  123,    0,    0,    0,    0,    0,  140,   57,    0,
    0,    0,    0,    0,    0,   19,    0,    0,    0,    0,
    0,  142,    0,  139,  141,  135,  130,  131,   40,   18,
   17,    0,   88,    0,   45,    0,    0,    0,    0,    0,
    1,   71,   72,   79,   80,  116,    0,    0,    0,    0,
    0,    0,   64,    0,    0,    0,    0,    0,    0,    0,
    0,   81,   76,   82,   77,    0,   99,    0,    0,  125,
  126,   59,   56,   58,    0,    0,    0,   37,    0,    0,
    0,    0,   48,  133,    0,  137,   87,   46,   44,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,   62,  112,   86,    0,  129,  102,    0,    0,    0,
    0,    0,   36,   35,    0,   34,    0,    0,    0,  134,
    0,  138,   42,   39,    0,    0,    0,    0,  111,    0,
    0,    0,  105,    0,   97,    0,  104,    0,   98,    0,
   38,   27,    0,    0,    0,   31,    0,    0,  132,  136,
    0,    0,    0,    0,    0,    0,    0,  128,    0,  100,
  103,    0,  101,    0,    0,    0,    0,   29,    0,    0,
    0,    0,    0,    0,   63,  127,  106,   96,   32,   33,
   28,   30,    0,   24,  115,    0,  118,  117,  108,   23,
  107,
};
final static short yydgoto[] = {                          3,
   39,   15,   16,   17,   18,   19,   20,   21,   22,   63,
   23,   40,   24,   66,  183,   25,  137,   26,   27,   28,
   29,   30,  103,   42,   43,   81,   44,   45,   46,  115,
  159,   93,  160,  161,  175,  116,  117,  118,
};
final static short yysindex[] = {                      -209,
  871,    0,    0,  757,  -40,  -13,  229,   10,  -40, -215,
    0,    0,    0,    0,  779,    0,    0,   -5,    0,   21,
   43, -240,    0,   49,    0,    0,    0,    0,    0,    0,
 -110,  801,  836,  566,    0,    0,  -38, -241,  109,    0,
  820,   -1,    0,   30,    0, -108,  271,  371, -158, -158,
 -121,  227,  966,    0,    0,    0,    0,    0,    0,    0,
 -112,    0,  -36,    0,  -23,  -97,  227,  -96,    0,  854,
    0,   40,  -84, -227,  -77,  -69,   30,  511,  227,  820,
   54,    0,    0,  317,    0,    0,    0,    0,    0,  384,
  424,    0,  227,  -79,  467,  582,  983,    0,    0,  109,
  365,  102,  161, -106, -158,    0,  -56,   81,  149,  -87,
  294,    0,  819,    0,    0,    0,    0,    0,    0,    0,
    0,  -54,    0,  174,    0,  178,  -37,  871,   90,  110,
    0,    0,    0,    0,    0,    0,   90,  506,  227,  227,
  593,  529,    0,  194,  340,   40,   30,   40,   30,  478,
  114,    0,    0,    0,    0, 1011,    0,  -49,  232,    0,
    0,    0,    0,    0,   51,  -51,  -54,    0,  -14,  -54,
  -14,  -24,    0,    0,  626,    0,    0,    0,    0,    0,
  273,  871,   80,   85,  -28,  320,  520,   90,  347,    0,
  227,    0,    0,    0,  233,    0,    0, -162,  -21,  146,
  285, -192,    0,    0,  383,    0,  397,  -29,  399,    0,
 1019,    0,    0,    0,  227,  227,  -31,  227,    0,  227,
  569,  643,    0,  997,    0,  236,    0,  188,    0,   85,
    0,    0,  -14,  -14,  -14,    0,  -22,  -14,    0,    0,
   90,   60,  227,   77,   82,  107,  413,    0, 1033,    0,
    0, -183,    0,  331,  334,  335,  420,    0,  336,  423,
  151,  426,  437,  439,    0,    0,    0,    0,    0,    0,
    0,    0,  359,    0,    0,  444,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -71,    0,    0,    0,  252,    0,  404,
  476,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  488,    0,    0,    0,    0,    0,    1,    0,
    0,   24,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  449,    0,    0,    0,    0,    0,    0,    0,    0,  491,
    0,    0,    0,    0,    0,    0,   70,    0,    0,  152,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  560,
    0,    0,    0,  123,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  185,    0,
    0,    0,    0,    0,    0,    0,  518,    0,    0,    0,
    0,    0,    0,    0,    0,   93,  116,  139,  162,  894,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  241,    0,    0,    0,    0,  912,  555,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  578,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  209,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  433,    0,
    0,    0,    0,  930,    0,    0,    0,    0,    0,    0,
    0,    0,  452,    0,    0,  948,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  751,  142,    4,    0,  837,    0,    0,    0,    7,    0,
  -35,  756,    0,    0,    0,    0,  914,    0,    0,    0,
    0,    0,    0,  773,    0,  -76,  499,   32,  495,  417,
 -123,  -60,    0,    0,  315,  747,    0,    0,
};
final static int YYTABLESIZE=1306;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   90,   79,  138,  180,   38,  167,   38,  122,  243,  198,
  202,  216,   68,   38,  235,   38,  165,  125,   57,  139,
   38,  257,  121,   83,   61,   82,   47,    2,  133,  127,
   38,   88,   92,   89,  199,  171,   57,  228,   83,   82,
   54,   90,   90,   90,   90,   90,   70,   90,  144,   52,
    1,   90,   83,   58,  107,  108,  109,   55,    2,   90,
   90,   90,   90,  231,   83,   83,   83,   83,   83,   75,
   83,   95,  267,   57,  226,    2,   96,  186,  268,   59,
  189,   75,   83,   83,   83,   83,   76,   70,   65,   70,
   70,   70,   73,  223,  141,  236,  224,  140,  208,  225,
  260,   60,  258,  140,   57,   70,   70,   70,   70,    2,
   75,  166,   75,   75,   75,   68,  172,  262,   12,   13,
  140,   94,  263,   90,  217,  140,  153,  155,   75,   75,
   75,   75,   90,   73,   91,   73,   73,   73,   74,  242,
  244,  245,  163,  246,   90,   33,   91,  264,   84,   97,
  140,   73,   73,   73,   73,  119,   68,  110,   68,   68,
   68,   69,  128,   83,   83,   83,  261,   83,   67,   83,
  130,  132,   67,   70,   68,   68,   68,   68,  134,   74,
    2,   74,   74,   74,   60,   57,  135,  151,  101,   12,
   13,  276,   67,   19,  140,   67,   19,   74,   74,   74,
   74,  164,   69,  169,   69,   69,   69,   90,   61,  156,
  170,    2,  197,    2,  178,   34,    2,   78,  179,  120,
   69,   69,   69,   69,   34,   60,   35,    2,   35,    2,
    2,    2,  124,  191,  184,   35,    2,   35,  194,   36,
  227,   36,   35,   60,    2,   85,   86,   87,   36,   61,
   36,   11,   35,   12,   13,   36,   90,   90,   90,   90,
   90,   90,   90,   90,   90,   36,   90,   61,   90,  182,
   90,   38,   90,   90,   90,   90,   90,   90,   90,   83,
   83,   83,   83,   83,   83,   83,   83,   83,   50,   83,
  200,   83,   11,   83,  252,   83,   83,   83,   83,   83,
   83,   83,   70,   70,   70,   70,   70,   70,   70,   70,
   70,   99,   70,  213,   70,   38,   70,  201,   70,   70,
   70,   70,   70,   70,   70,   75,   75,   75,   75,   75,
   75,   75,   75,   75,  173,   75,   90,   75,   91,   75,
  214,   75,   75,   75,   75,   75,   75,   75,   73,   73,
   73,   73,   73,   73,   73,   73,   73,  143,   73,  218,
   73,   38,   73,  215,   73,   73,   73,   73,   73,   73,
   73,   68,   68,   68,   68,   68,   68,   68,   68,   68,
  192,   68,   90,   68,   91,   68,  220,   68,   68,   68,
   68,   68,   68,   68,   74,   74,   74,   74,   74,   74,
   74,   74,   74,   15,   74,  162,   74,  229,   74,  230,
   74,   74,   74,   74,   74,   74,   74,   69,   69,   69,
   69,   69,   69,   69,   69,   69,  233,   69,   38,   69,
  105,   69,   26,   69,   69,   69,   69,   69,   69,   69,
  234,   60,  238,   60,   15,   60,   60,   60,   60,  253,
   60,   25,   60,  265,   60,  269,   60,   60,  270,  271,
  274,   60,   60,  275,   38,   61,  277,   61,   38,   61,
   61,   61,   61,   26,   61,   16,   61,  278,   61,  279,
   61,   61,   34,  280,  281,   61,   61,    6,   41,    5,
    4,   26,   25,   35,    2,    6,    2,  251,    8,   48,
    2,   47,    9,   53,   10,   11,   36,   49,   11,  222,
   25,   38,   11,  158,   11,   11,   16,   11,  193,   11,
   90,   11,   91,   11,   11,    0,   34,    5,   11,   11,
    0,    0,   77,    6,    7,    0,    8,   35,    2,   98,
    9,    0,   10,   11,  272,    0,  185,   12,   13,  140,
   36,  136,   75,   73,    0,   74,    0,   76,   67,    0,
  219,   67,   90,    0,   91,   88,   92,   89,    0,  190,
   75,   73,  142,   74,    0,   76,   77,   67,   67,   67,
    0,    0,    0,   35,    2,    0,    0,    0,  147,  149,
    0,    0,    0,   12,   13,   66,   36,    0,   66,    0,
   90,   90,   90,    0,   90,    0,   90,   75,   73,  247,
   74,   90,   76,   91,   66,   66,   66,    0,   86,   86,
   86,    5,   86,    0,   86,    0,   38,    6,    7,    0,
    8,    0,    2,    0,    9,    0,   10,   11,    2,  146,
   77,   12,   13,    0,    0,    0,    0,   12,   13,    0,
   35,    2,   88,   92,   89,    0,    0,    0,    0,    0,
   15,    0,    0,   36,   15,    0,   15,   15,    0,   15,
    0,   15,    0,   15,    0,   15,   15,    0,    0,  148,
   15,   15,   90,    0,  211,    0,   35,    0,    0,   26,
   35,    2,    0,   26,    0,   26,   26,    0,   26,   36,
   26,  249,   26,   36,   26,   26,    0,    0,   25,   26,
   26,    0,   25,    0,   25,   25,    0,   25,    0,   25,
    0,   25,  152,   25,   25,    0,    0,    0,   25,   25,
    0,    0,   16,   35,    2,    0,   16,    0,   16,   16,
    0,   16,    0,   16,    0,   16,   36,   16,   16,    0,
    4,   14,   16,   16,   14,    0,    0,   51,    0,    0,
    0,    0,    0,    0,    0,   14,   72,    0,    0,    0,
    0,    0,   62,   31,    0,    0,   31,   35,    2,   85,
   86,   87,   14,   14,   72,    0,    0,   31,    0,    0,
   36,   67,   67,   67,   64,   35,    2,  100,  106,  106,
  106,    0,    0,   62,   31,   31,    0,    0,   36,    0,
    0,    0,    0,   62,    0,  126,    0,    0,    0,  104,
   14,   72,    0,    0,   19,   31,    0,   19,   66,   66,
   66,    0,   35,    2,    0,  123,    0,  154,   90,    0,
    0,    0,   31,    0,    0,   36,    0,   62,   35,    2,
    0,   14,    0,    0,    0,  106,    0,  168,    0,  176,
  106,   36,   90,   62,   91,    0,   85,   86,   87,   31,
    0,    0,   62,   31,    0,    0,    0,  181,   14,   88,
   92,   89,    5,    0,    0,   31,  210,    0,    6,  114,
    0,    8,    0,    2,  177,    9,    0,   10,   11,    5,
   31,    0,  196,  248,    0,    6,   62,    0,    8,    0,
    2,    0,    9,    0,   10,   11,  203,  204,   41,    0,
  206,  212,   41,    0,  205,   62,  207,  209,   31,    0,
    0,    0,   14,  114,    0,    0,    0,    0,    0,    0,
    0,  176,    0,    0,    0,   62,    0,   31,    0,  114,
   80,    0,  232,    0,   31,    0,    0,  240,    0,    0,
  102,   62,    0,  237,    0,  111,    0,   31,  212,    0,
  196,    0,   62,    0,   62,    0,    0,    0,    0,    0,
  129,    0,    0,   31,    0,    0,    0,    0,  254,  255,
  256,    0,  114,  259,   31,  240,   31,  145,    0,   62,
    0,    0,    0,    0,    0,    0,  150,    0,    0,    0,
    0,  114,  273,    5,    0,    0,   32,    0,    0,    6,
    7,   31,    8,    0,    2,    0,    9,    0,   10,   11,
    0,  114,    0,   12,   13,    5,    0,    0,    0,   56,
    0,    6,    7,    0,    8,    0,    2,  114,    9,    0,
   10,   11,  187,  188,    0,   12,   13,    5,  114,    0,
  114,   69,    0,    6,    7,    0,    8,    0,    2,    0,
    9,    0,   10,   11,    0,    5,    0,   12,   13,  174,
    0,    6,    0,    0,    8,  114,    2,    0,    9,    0,
   10,   11,    5,   85,   86,   87,   71,    0,    6,    7,
    0,    8,    0,    2,  221,    9,    0,   10,   11,    0,
    5,    0,   12,   13,  131,    0,    6,    7,    0,    8,
    0,    2,    0,    9,    0,   10,   11,    5,  241,    0,
   12,   13,    0,    6,    7,    0,    8,    0,    2,    0,
    9,    0,   10,   11,    0,    0,    0,   12,   13,  114,
  114,  114,    0,  114,    0,    0,  114,    0,    0,  114,
    0,  114,    0,  114,    0,  114,  114,  113,  113,  113,
    0,  113,    0,    0,  113,    0,    0,  113,    0,  113,
    0,  113,    0,  113,  113,  110,  110,  110,    0,  110,
    0,    0,  110,    0,    0,  110,    0,  110,    0,  110,
    0,  110,  110,  109,  109,  109,    0,  109,    0,    0,
  109,    0,    0,  109,    0,  109,    0,  109,    0,  109,
  109,  112,    5,    0,    0,  113,    0,    0,    6,    0,
    0,    8,    0,    2,    0,    9,    0,   10,   11,    5,
    0,  156,  113,    0,  157,    6,    0,    0,    8,    0,
    2,    0,    9,    5,   10,   11,  195,    0,  250,    6,
    0,    0,    8,    0,    2,    0,    9,    5,   10,   11,
  195,    0,    0,    6,    0,    5,    8,    0,    2,  239,
    9,    6,   10,   11,    8,    0,    2,    0,    9,    5,
   10,   11,    0,  266,    0,    6,    0,    0,    8,    0,
    2,    0,    9,    0,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   79,   41,   45,   62,   45,   44,   40,   59,
   62,   40,  123,   45,   44,   45,  123,   41,   15,   80,
   45,   44,   59,    0,  265,  267,   40,  268,  256,   65,
   45,   60,   61,   62,  158,  123,   33,   59,  280,  267,
  256,   41,   42,   43,   44,   45,    0,   47,   84,   40,
  260,  123,  280,   59,   48,   49,   50,  273,  268,   59,
   60,   61,   62,  256,   41,   42,   43,   44,   45,    0,
   47,   42,  256,   70,  198,  268,   47,  138,  262,   59,
  141,   42,   59,   60,   61,   62,   47,   41,   40,   43,
   44,   45,    0,  256,   41,  125,  259,   44,  123,  262,
   41,   59,  125,   44,  101,   59,   60,   61,   62,  268,
   41,  105,   43,   44,   45,    0,  110,   41,  277,  278,
   44,  123,   41,  123,  185,   44,   95,   96,   59,   60,
   61,   62,   43,   41,   45,   43,   44,   45,    0,  216,
  217,  218,   41,  220,   43,    4,   45,   41,   40,  258,
   44,   59,   60,   61,   62,  268,   41,  279,   43,   44,
   45,    0,  260,   41,   42,   43,  243,   45,  279,   47,
  267,  256,  279,   32,   59,   60,   61,   62,  256,   41,
  268,   43,   44,   45,    0,  182,  256,  267,   47,  277,
  278,   41,   41,  265,   44,   44,  268,   59,   60,   61,
   62,   41,   41,  123,   43,   44,   45,  279,    0,  259,
   62,  268,  262,  268,   41,  256,  268,  256,   41,  256,
   59,   60,   61,   62,  256,   41,  267,  268,  267,  268,
  268,  268,  256,   40,  125,  267,  268,  267,  125,  280,
  262,  280,  267,   59,  268,  274,  275,  276,  280,   41,
  280,    0,  267,  277,  278,  280,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  280,  266,   59,  268,  128,
  270,   45,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   60,  266,
   59,  268,   41,  270,   59,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,  259,  260,  261,  262,  263,
  264,   41,  266,   41,  268,   45,  270,  267,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   43,  268,   45,  270,
  261,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   41,  266,   40,
  268,   45,  270,  279,  272,  273,  274,  275,  276,  277,
  278,  256,  257,  258,  259,  260,  261,  262,  263,  264,
   41,  266,   43,  268,   45,  270,   40,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  258,  259,  260,  261,
  262,  263,  264,    0,  266,   41,  268,  262,  270,  125,
  272,  273,  274,  275,  276,  277,  278,  256,  257,  258,
  259,  260,  261,  262,  263,  264,   44,  266,   45,  268,
   60,  270,    0,  272,  273,  274,  275,  276,  277,  278,
   44,  257,   44,  259,   41,  261,  262,  263,  264,  262,
  266,    0,  268,   41,  270,  125,  272,  273,  125,  125,
  125,  277,  278,   41,   45,  257,   41,  259,   45,  261,
  262,  263,  264,   41,  266,    0,  268,   41,  270,   41,
  272,  273,  256,  125,   41,  277,  278,    0,   40,  257,
    0,   59,   41,  267,  268,  263,  268,  262,  266,  271,
  268,  261,  270,    9,  272,  273,  280,  279,  257,  195,
   59,   45,  261,   97,  263,  264,   41,  266,   41,  268,
   43,  270,   45,  272,  273,   -1,  256,  257,  277,  278,
   -1,   -1,   34,  263,  264,   -1,  266,  267,  268,  269,
  270,   -1,  272,  273,  125,   -1,   41,  277,  278,   44,
  280,   41,   42,   43,   -1,   45,   -1,   47,   41,   -1,
   41,   44,   43,   -1,   45,   60,   61,   62,   -1,   41,
   42,   43,  256,   45,   -1,   47,   78,   60,   61,   62,
   -1,   -1,   -1,  267,  268,   -1,   -1,   -1,   90,   91,
   -1,   -1,   -1,  277,  278,   41,  280,   -1,   44,   -1,
   41,   42,   43,   -1,   45,   -1,   47,   42,   43,   41,
   45,   43,   47,   45,   60,   61,   62,   -1,   41,   42,
   43,  257,   45,   -1,   47,   -1,   45,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,  268,  256,
  142,  277,  278,   -1,   -1,   -1,   -1,  277,  278,   -1,
  267,  268,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
  257,   -1,   -1,  280,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,  256,
  277,  278,  123,   -1,   59,   -1,  267,   -1,   -1,  257,
  267,  268,   -1,  261,   -1,  263,  264,   -1,  266,  280,
  268,   59,  270,  280,  272,  273,   -1,   -1,  257,  277,
  278,   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  256,  272,  273,   -1,   -1,   -1,  277,  278,
   -1,   -1,  257,  267,  268,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,  280,  272,  273,   -1,
    0,    1,  277,  278,    4,   -1,   -1,    7,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   15,  256,   -1,   -1,   -1,
   -1,   -1,   22,    1,   -1,   -1,    4,  267,  268,  274,
  275,  276,   32,   33,  256,   -1,   -1,   15,   -1,   -1,
  280,  274,  275,  276,   22,  267,  268,   47,   48,   49,
   50,   -1,   -1,   53,   32,   33,   -1,   -1,  280,   -1,
   -1,   -1,   -1,   63,   -1,   65,   -1,   -1,   -1,   47,
   70,  256,   -1,   -1,  265,   53,   -1,  268,  274,  275,
  276,   -1,  267,  268,   -1,   63,   -1,  256,  279,   -1,
   -1,   -1,   70,   -1,   -1,  280,   -1,   97,  267,  268,
   -1,  101,   -1,   -1,   -1,  105,   -1,  107,   -1,  113,
  110,  280,   43,  113,   45,   -1,  274,  275,  276,   97,
   -1,   -1,  122,  101,   -1,   -1,   -1,  127,  128,   60,
   61,   62,  257,   -1,   -1,  113,  261,   -1,  263,   53,
   -1,  266,   -1,  268,  122,  270,   -1,  272,  273,  257,
  128,   -1,  156,  261,   -1,  263,  156,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  166,  167,    5,   -1,
  170,  175,    9,   -1,  169,  175,  171,  172,  156,   -1,
   -1,   -1,  182,   97,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  195,   -1,   -1,   -1,  195,   -1,  175,   -1,  113,
   37,   -1,  202,   -1,  182,   -1,   -1,  211,   -1,   -1,
   47,  211,   -1,  208,   -1,   52,   -1,  195,  222,   -1,
  224,   -1,  222,   -1,  224,   -1,   -1,   -1,   -1,   -1,
   67,   -1,   -1,  211,   -1,   -1,   -1,   -1,  233,  234,
  235,   -1,  156,  238,  222,  249,  224,   84,   -1,  249,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,  175,  257,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,  249,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  195,   -1,  277,  278,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,   -1,  266,   -1,  268,  211,  270,   -1,
  272,  273,  139,  140,   -1,  277,  278,  257,  222,   -1,
  224,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,  257,   -1,  277,  278,  261,
   -1,  263,   -1,   -1,  266,  249,  268,   -1,  270,   -1,
  272,  273,  257,  274,  275,  276,  261,   -1,  263,  264,
   -1,  266,   -1,  268,  191,  270,   -1,  272,  273,   -1,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  257,  215,   -1,
  277,  278,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,  258,
   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  256,  257,   -1,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  257,
   -1,  259,  260,   -1,  262,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,  257,  272,  273,  260,   -1,  262,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  257,  272,  273,
  260,   -1,   -1,  263,   -1,  257,  266,   -1,  268,  261,
  270,  263,  272,  273,  266,   -1,  268,   -1,  270,  257,
  272,  273,   -1,  261,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,
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

//#line 260 "Gramatica.y"
	
public static StringBuilder AMBITO = new StringBuilder(":MAIN");																 
public static boolean RETORNO = false;
public static Map<String,Tipo> tipos = new HashMap<>();

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


private static void cargarVariables(String variables, Tipo tipo, String uso){
	String[] var = getVariables(variables,"/");
	for (String v : var) {
		if(!fueDeclarado(v)){
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
//#line 863 "Parser.java"
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
//#line 13 "Gramatica.y"
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(3).sval +"\u001B[0m");}
break;
case 2:
//#line 14 "Gramatica.y"
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(2).sval +"\u001B[0m");}
break;
case 3:
//#line 15 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 4:
//#line 16 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 5:
//#line 17 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador BEGIN "+"\u001B[0m");}
break;
case 6:
//#line 18 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 11:
//#line 27 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 15:
//#line 35 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 16:
//#line 36 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 17:
//#line 39 "Gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 18:
//#line 40 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 19:
//#line 43 "Gramatica.y"
{ if(tipos.containsKey(val_peek(0).sval)){yyval.obj = tipos.get(val_peek(0).sval);
					}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido "+"\u001B[0m");};}
break;
case 20:
//#line 45 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 21:
//#line 48 "Gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 22:
//#line 50 "Gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 23:
//#line 54 "Gramatica.y"
{if(val_peek(5).obj != null){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 24:
//#line 55 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 25:
//#line 56 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 26:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 27:
//#line 58 "Gramatica.y"
{if(val_peek(2).obj != null){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 28:
//#line 59 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
break;
case 29:
//#line 60 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 30:
//#line 61 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
break;
case 31:
//#line 62 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 32:
//#line 63 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 33:
//#line 64 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 34:
//#line 65 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 35:
//#line 66 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 36:
//#line 67 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 37:
//#line 68 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 38:
//#line 69 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 39:
//#line 73 "Gramatica.y"
{	if(RETORNO==false)
										{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); 
									sacarAmbito();
								}
break;
case 40:
//#line 79 "Gramatica.y"
{cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj," nombre de funcion ");agregarAmbito(val_peek(0).sval);System.out.println(" Encabezado de la funcion ");}
break;
case 41:
//#line 80 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 42:
//#line 83 "Gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}
break;
case 43:
//#line 84 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");}
break;
case 44:
//#line 85 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
break;
case 45:
//#line 86 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 46:
//#line 87 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
break;
case 53:
//#line 101 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una ETIQUETA ");System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 55:
//#line 103 "Gramatica.y"
{RETORNO = true;}
break;
case 56:
//#line 106 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 57:
//#line 107 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 58:
//#line 108 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 59:
//#line 109 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 60:
//#line 112 "Gramatica.y"
{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;}else{
																System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");}
															System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 61:
//#line 116 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 62:
//#line 119 "Gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}else{
												System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}}
break;
case 63:
//#line 121 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 64:
//#line 122 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 65:
//#line 123 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
break;
case 71:
//#line 133 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 72:
//#line 134 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 73:
//#line 135 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 74:
//#line 136 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 75:
//#line 137 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
break;
case 79:
//#line 143 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 80:
//#line 144 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 81:
//#line 145 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 82:
//#line 146 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 83:
//#line 149 "Gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 86:
//#line 152 "Gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ yyval.sval = val_peek(3).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 87:
//#line 154 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 88:
//#line 155 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
break;
case 89:
//#line 156 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 92:
//#line 166 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 93:
//#line 167 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 96:
//#line 172 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 97:
//#line 173 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 98:
//#line 174 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 99:
//#line 175 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 100:
//#line 176 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 101:
//#line 178 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 102:
//#line 179 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 103:
//#line 180 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 104:
//#line 181 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
break;
case 105:
//#line 183 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 106:
//#line 184 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 107:
//#line 187 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 108:
//#line 188 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 109:
//#line 189 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 110:
//#line 190 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 111:
//#line 191 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 112:
//#line 192 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 113:
//#line 193 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 114:
//#line 194 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 115:
//#line 197 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 116:
//#line 198 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 117:
//#line 199 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
break;
case 118:
//#line 200 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
break;
case 128:
//#line 216 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 133:
//#line 227 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
break;
case 134:
//#line 228 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 138:
//#line 236 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 140:
//#line 242 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una Cadena MultiLinea ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 141:
//#line 247 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 142:
//#line 248 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 143:
//#line 253 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 144:
//#line 254 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1418 "Parser.java"
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
