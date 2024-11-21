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



package main.java.Compilador;



//#line 2 "gramatica.y"
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.io.IOException;
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
public final static short ERROR=280;
public final static short OCTAL=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    2,    2,    3,    3,
    3,    3,    4,    4,    4,    4,    4,    6,    6,    9,
    9,   11,   11,   11,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    7,   13,   13,   14,   14,   14,   14,   14,   15,   16,
   16,    5,    5,    5,    5,    5,    5,    5,   22,   22,
   22,   22,   18,   18,   18,   25,   25,   25,   25,   26,
   26,   26,   26,   17,   17,   17,   17,   17,   17,   17,
   17,   27,   27,   27,   27,   27,   27,   27,   28,   28,
   28,   28,   28,   10,   10,   10,   24,    1,   12,   12,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   32,   32,   32,   32,   32,   32,   31,   31,
   34,   34,   33,   30,   30,   38,   38,   37,   39,   39,
   41,   41,   41,   40,   35,   35,   35,   36,   23,   20,
   20,   42,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    2,    2,    1,    1,    3,    3,    1,
    1,    1,    1,    1,    9,    8,    8,    7,    6,    8,
    7,    8,    6,    8,    8,    5,    5,    5,    4,    6,
    5,    3,    2,    4,    3,    3,    2,    3,    1,    4,
    3,    1,    1,    1,    1,    1,    1,    1,    4,    3,
    4,    4,    3,    6,    7,    4,    7,    3,    4,    3,
    2,    2,    1,    3,    3,    1,    3,    3,    3,    3,
    2,    3,    3,    1,    3,    3,    3,    3,    1,    1,
    1,    4,    5,    3,    2,    1,    1,    1,    1,    2,
    7,    5,    4,    6,    6,    4,    6,    5,    5,    7,
    9,    8,    8,    7,    5,    4,    4,    3,    3,    8,
    8,    8,    1,    1,    1,    1,    1,    1,    1,    1,
    5,    4,    2,    1,    1,    5,    4,    2,    1,    1,
    4,    2,    3,    1,    3,    1,    2,    1,    1,    3,
    3,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   98,    0,    0,    0,    0,    0,    0,    0,  152,
    0,   56,   22,   23,   24,    0,    0,    8,    9,    0,
   13,    0,    0,    0,   21,    0,   58,   52,   53,   54,
   55,   57,    0,    0,    0,    0,   10,    0,   99,    0,
    0,    0,   90,    0,    0,   91,    0,   84,    0,    0,
    0,    0,    0,    0,    0,  154,  153,    3,    7,   11,
   14,   15,    0,   97,    0,   96,    0,    0,    0,    0,
    0,    2,    0,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  100,    0,  124,  126,  128,  125,
  123,    0,    0,  127,    0,    0,    0,    0,    0,    0,
  134,  135,    0,  149,   60,    0,    0,    0,    0,    0,
    0,   20,    0,    0,    0,    0,   51,    0,   42,   19,
   18,    0,   95,    0,   47,    0,    0,    0,    0,    0,
    0,  151,    0,  148,  144,  150,  139,  140,    1,   78,
   77,   85,   86,    0,    0,    0,    0,    0,    0,    0,
   68,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   87,   82,   88,   83,    0,  103,  138,    0,  106,    0,
    0,  129,  130,   62,   59,   61,    0,    0,    0,   39,
    0,    0,    0,    0,   50,   94,   48,   46,   45,    0,
    0,    0,    0,    0,  142,    0,  146,    0,    0,    0,
    0,    0,   69,    0,   66,  116,   92,    0,    0,    0,
  133,  109,    0,  102,    0,  108,    0,    0,    0,    0,
   38,   37,    0,   36,    0,    0,    0,   44,   41,    0,
    0,  143,    0,  147,    0,    0,    0,  115,    0,    0,
   93,  137,    0,    0,  104,  107,    0,  105,    0,    0,
   40,   29,    0,    0,    0,   33,    0,    0,    0,    0,
  141,  145,    0,    0,    0,    0,    0,    0,  136,  132,
    0,  110,  101,    0,    0,    0,    0,    0,   31,    0,
    0,    0,    0,    0,    0,    0,   67,  131,   34,   35,
   30,   32,    0,   26,  120,    0,  122,  121,  112,   25,
  111,
};
final static short yydgoto[] = {                          3,
   42,   17,   18,   19,  134,   21,   22,   23,   24,   65,
   25,   43,   26,   68,  192,   27,  145,   28,   29,   30,
   31,   32,  109,   45,   46,   84,   47,   48,   49,  100,
  171,   95,  172,  173,  196,  197,  101,  102,  136,  137,
  138,   34,
};
final static short yysindex[] = {                      -158,
  971,    0,    0,  868,   -5,  -31,    7,  -53,   18,    0,
 -233,    0,    0,    0,    0,    0,  895,    0,    0,   20,
    0,   31,   52, -173,    0,   88,    0,    0,    0,    0,
    0,    0, -107,  -31,  914,  933,    0,  762,    0,  651,
 -151,  105,    0,  795,   23,    0,    9,    0, -104,  412,
  492, -177, -177, -124,  490,    0,    0,    0,    0,    0,
    0,    0, -109,    0,  -40,    0,  384,  -96,  593,  -13,
 1087,    0,  952,    0,   36, -215,  -83,  -65,  -58,    9,
  593,  522,  795,   76,    0,  554,    0,    0,    0,    0,
    0,  614,  675,    0,  593,  -12,  681,  690, 1101,  -49,
    0,    0,  794,    0,    0,  105,  723,   64,  143,  -99,
 -177,    0,  -62,   79,  145, -120,    0,   95,    0,    0,
    0,  -60,    0,  192,    0,  200,  -36,  971,  251,  193,
    3,    0,  842,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  251,  251,  -23,  593,  593,  687,  697,
    0,  282,  358,   36,    9,   36,    9,  386,  199,   69,
    0,    0,    0,    0, 1171,    0,    0, 1129,    0,  229,
  -39,    0,    0,    0,    0,    0,  -11,  -56,  -60,    0,
   39,  -60,   39,  -43,    0,    0,    0,    0,    0,  296,
  971,   92,   70,  237,    0,  771,    0,  -32,  335,  433,
  251,  337,    0,  593,    0,    0,    0,  262,  815, 1171,
    0,    0, 1115,    0,  -28,    0,  151,  280,  172, -191,
    0,    0,  396,    0,  411,    5,  425,    0,    0,  593,
  205,    0, 1137,    0,  522,  659,  522,    0,  522,  463,
    0,    0, 1151,  834,    0,    0, -189,    0,   70,  364,
    0,    0,   39,   39,   39,    0,  -22,   39,  251,  593,
    0,    0,  106,  522,  122,  130,  291,  452,    0,    0,
 1159,    0,    0,  205,  374,  388,  389,   14,    0,  390,
  251,  477,  439,  478,  480,  483,    0,    0,    0,    0,
    0,    0,  400,    0,    0,  485,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -68,    0,    0,    0,  266,
    0,  347,  373,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  528,    0,    0,    0,    0,
    0,    1,    0,    0,   27,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  493,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  537,    0,    0,    0,    0,    0,    0,   82,
    0,    0,  453,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -30,    0,    0,    0,   90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  209,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  612,  626,    0,    0,  741,  997,    0,
    0,    0,    0,  108,  134,  160,  186, 1015,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  284,    0,    0,    0,    0,    0,    0,    0,    0, 1033,
  783,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  140,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  239,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  979,    0,    0,    0,    0,    0,  320,
  292,    0,    0,    0,    0, 1051,    0,    0,    0,    0,
    0,    0,  451,    0,    0, 1069,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  701,   71,   49,    0,  973,    0,    0,    0,  489,    0,
  -10,  864,    0,    0,    0,    0,  725,    0,    0,    0,
    0,    0,    0,  726,    0,  -47,   26,  -72,  512,    0,
  380,   16,    0,    0, -129,  883,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=1444;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        179,
   97,   41,  183,  122,  189,  220,   53,  235,   40,  170,
   97,   97,   97,   41,   97,   70,   97,  198,  121,  217,
  148,  278,   56,  177,  162,  164,   89,   90,   94,   91,
  247,  131,  160,  219,  146,  209,   90,   94,   91,   57,
  140,   97,   97,   97,   97,   97,   50,   97,  255,   41,
   97,   85,   76,   37,   97,   98,  127,   55,   41,   97,
   97,   97,   97,   80,  251,   59,  272,   89,   89,   89,
   89,   89,  273,   89,   36,  152,    2,   78,   60,  226,
  244,   81,   79,   41,   59,   89,   89,   89,   89,   61,
    2,   63,   97,   76,    2,   76,   76,   76,  147,   13,
   14,    1,  279,   15,  175,   73,   93,   80,   92,    2,
   62,   76,   76,   76,   76,   85,  149,  155,  157,  148,
  107,   59,   81,   97,   81,   81,   81,   67,   80,  256,
   89,   89,   89,   75,   89,  185,   89,   93,  292,   92,
   81,   81,   81,   81,   86,   96,  282,    2,   80,  148,
   80,   80,   80,   99,  116,   59,   13,   14,  119,   79,
   15,  199,  284,  128,  202,  148,   80,   80,   80,   80,
  285,   69,  141,  148,   75,   80,   75,   75,   75,   69,
   92,   92,   92,  176,   92,   74,   92,  263,  265,  266,
  142,  267,   75,   75,   75,   75,   20,  143,  191,   20,
   79,  181,   79,   79,   79,    2,  182,    2,   63,  168,
   97,    2,  169,  236,    2,  120,  283,   51,   79,   79,
   79,   79,  216,   39,   38,   52,   74,    2,   74,   74,
   74,    2,  187,  246,   20,   39,    2,   20,   64,   59,
  188,   87,   88,   89,   74,   74,   74,   74,   97,   63,
   87,   88,   89,  130,  159,  218,   97,   97,   97,   97,
   97,   97,   97,   97,   97,   12,   97,   63,   97,  194,
   97,   39,   97,   97,   97,   97,   97,   97,   97,   64,
   39,   97,   89,   89,   89,   89,   89,   89,   89,   89,
   89,   65,   89,   93,   89,   92,   89,   64,   89,   89,
   89,   89,   89,   89,   89,   39,   12,   89,   76,   76,
   76,   76,   76,   76,   76,   76,   76,  193,   76,   28,
   76,  204,   76,  207,   76,   76,   76,   76,   76,   76,
   76,  286,   65,   76,  148,  208,  228,   81,   81,   81,
   81,   81,   81,   81,   81,   81,   16,   81,  230,   81,
   65,   81,  229,   81,   81,   81,   81,   81,   81,   81,
   28,  231,   81,   80,   80,   80,   80,   80,   80,   80,
   80,   80,   17,   80,  237,   80,  239,   80,   28,   80,
   80,   80,   80,   80,   80,   80,  241,   16,   80,   75,
   75,   75,   75,   75,   75,   75,   75,   75,  205,   75,
   93,   75,   92,   75,  249,   75,   75,   75,   75,   75,
   75,   75,  248,   17,   75,   79,   79,   79,   79,   79,
   79,   79,   79,   79,  125,   79,  206,   79,   93,   79,
   92,   79,   79,   79,   79,   79,   79,   79,  250,  253,
   79,   74,   74,   74,   74,   74,   74,   74,   74,   74,
   27,   74,  105,   74,  254,   74,   41,   74,   74,   74,
   74,   74,   74,   74,   63,   63,   74,   63,  258,   63,
   63,   63,   63,  238,   63,   93,   63,   92,   63,  296,
   63,   63,  148,  260,  212,   63,   63,  213,  274,   63,
  214,   27,  287,   73,   64,   64,   73,   64,  289,   64,
   64,   64,   64,  268,   64,   93,   64,   92,   64,   27,
   64,   64,  290,  291,  294,   64,   64,  295,  297,   64,
  298,   12,   12,  299,  300,  301,   12,    6,   12,   12,
  117,   12,   43,   12,   41,   12,    4,   12,   12,  113,
  114,  115,   12,   12,   49,   71,   12,   65,   65,  215,
   65,  111,   65,   65,   65,   65,    0,   65,    0,   65,
    0,   65,    0,   65,   65,   81,   41,    0,   65,   65,
    0,    0,   65,    0,    0,   28,   28,    0,    0,    0,
   28,    0,   28,   28,    0,   28,    0,   28,    0,   28,
    0,   28,   28,    0,  151,    0,   28,   28,   41,  178,
   28,    0,   16,   16,  184,    0,    0,   16,    0,   16,
   16,    0,   16,    0,   16,    0,   16,    0,   16,   16,
    0,    0,    0,   16,   16,    0,    0,   16,   17,   17,
    0,    0,    0,   17,    0,   17,   17,   41,   17,  124,
   17,    0,   17,    0,   17,   17,    0,    0,    0,   17,
   17,    2,   71,   17,    0,   71,    0,    0,   41,    0,
   13,   14,    0,    0,   15,    0,   73,  103,    6,   73,
    0,   71,   71,   71,    7,    8,    0,    9,   39,    2,
  104,   10,    0,   11,   12,   73,   73,   73,   13,   14,
   82,    0,   15,    0,   81,   41,    0,    0,  264,    0,
    4,   16,   81,   41,   16,    0,   27,   27,   54,    0,
    0,   27,    0,   27,   27,    0,   27,   16,   27,   41,
   27,    0,   27,   27,   64,   41,   33,   27,   27,   33,
   44,   27,    0,    0,   41,   16,   16,  203,   78,   77,
    0,   76,   33,   79,    0,   38,   90,   94,   91,   66,
  106,  112,  112,  112,    0,    0,   39,    2,   44,    2,
   33,   33,    0,  174,   83,   64,    0,  126,   13,   14,
    0,   64,   15,   16,  108,  110,    0,   38,    0,  118,
    0,   72,    0,    0,   72,    0,    0,    0,   39,    2,
  123,    0,    0,  129,    0,    0,   33,    0,   33,   64,
   72,   72,   72,   78,   77,  144,   76,   16,   79,  150,
  153,  112,    0,  180,    0,    0,  112,    0,    0,  158,
   39,    2,   64,   70,   33,    0,   70,  190,   16,  233,
   13,   14,   33,   64,   15,   78,   77,   93,   76,   92,
   79,    0,   70,   70,   70,    0,    0,  186,   38,    0,
    0,    0,   37,   33,   90,   94,   91,    0,   33,   39,
    2,    0,    0,    0,    0,   64,    0,    0,   64,  154,
    0,  200,  201,  243,    0,    0,    0,    0,  221,  222,
   39,    2,  224,    0,    0,   71,   71,   71,    0,    0,
   33,   16,  271,   33,    0,    0,   64,    0,    0,   73,
   73,   73,    0,    0,    0,    0,   38,    0,    0,   64,
   64,    0,    0,   64,   38,    0,   33,   39,    2,    0,
  252,   33,    0,    0,    0,   39,    2,    0,  240,    0,
  156,    0,    0,   64,   33,   33,  161,    0,   33,    0,
    0,   39,    2,   64,   64,  163,    0,   39,    2,    0,
    0,    0,   75,  135,  259,    0,   39,    2,   33,    0,
   87,   88,   89,   39,    2,    0,    0,    0,   33,   33,
    0,   64,    0,   20,    0,    0,   20,    0,    5,    6,
    0,  167,    0,    0,  281,    7,    8,    0,    9,   20,
    2,    0,   10,    0,   11,   12,   33,    0,    0,   13,
   14,    0,    0,   15,    0,    0,    0,   20,   20,    0,
    0,    0,    0,    0,   72,   72,   72,   75,    0,   93,
   93,   93,   20,   93,    0,   93,    0,    6,   39,    2,
    0,  232,    0,    7,    0,    0,    9,    0,    2,    0,
   10,    0,   11,   12,  223,   20,  225,  227,    0,   75,
  211,    0,    0,    0,    0,    0,   70,   70,   70,    0,
   39,    2,    0,    0,    0,    0,    0,    0,   87,   88,
   89,    6,    0,    0,    0,  242,    0,    7,  234,   20,
    9,    0,    2,    0,   10,    0,   11,   12,    0,  257,
    6,  234,    0,    0,  270,  211,    7,    0,    6,    9,
   20,    2,  195,   10,    7,   11,   12,    9,    0,    2,
    0,   10,    0,   11,   12,  262,  275,  276,  277,    0,
    0,  280,    0,    5,    6,  262,  234,   35,    0,    0,
    7,    8,    0,    9,    0,    2,    0,   10,    0,   11,
   12,  293,    0,    0,   13,   14,    0,    0,   15,    0,
    5,    6,    0,  262,    0,   58,    0,    7,    8,    0,
    9,    0,    2,   20,   10,    0,   11,   12,    0,    5,
    6,   13,   14,    0,   72,   15,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    0,    5,    6,
   13,   14,    0,   74,   15,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    5,    6,   13,
   14,    0,  139,   15,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    5,    6,   13,   14,
    0,    0,   15,    7,    8,    0,    9,    0,    2,    0,
   10,    0,   11,   12,    0,    0,    0,   13,   14,    0,
    0,   15,  119,  119,  119,    0,  119,    0,    0,  119,
    0,    0,  119,    0,  119,    0,  119,    0,  119,  119,
  118,  118,  118,    0,  118,    0,    0,  118,    0,    0,
  118,    0,  118,    0,  118,    0,  118,  118,  117,  117,
  117,    0,  117,    0,    0,  117,    0,    0,  117,    0,
  117,    0,  117,    0,  117,  117,  114,  114,  114,    0,
  114,    0,    0,  114,    0,    0,  114,    0,  114,    0,
  114,    0,  114,  114,  113,  113,  113,    0,  113,    0,
    0,  113,    0,    0,  113,    0,  113,    0,  113,    0,
  113,  113,  132,    6,    0,    0,  133,    0,    0,    7,
    0,    0,    9,    0,    2,    0,   10,    6,   11,   12,
  165,    0,  166,    7,    0,    0,    9,    0,    2,    0,
   10,    6,   11,   12,  210,    0,  245,    7,    0,    0,
    9,    0,    2,    0,   10,    6,   11,   12,  210,    0,
    0,    7,    0,    6,    9,    0,    2,  261,   10,    7,
   11,   12,    9,    0,    2,    0,   10,    6,   11,   12,
    0,  269,    0,    7,    0,    6,    9,    0,    2,  288,
   10,    7,   11,   12,    9,    0,    2,    6,   10,    0,
   11,   12,    0,    7,    0,    0,    9,    0,    2,    0,
   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         62,
    0,   45,  123,   44,   41,   62,   60,   40,   40,   59,
   41,   42,   43,   45,   45,  123,   47,   41,   59,   59,
   44,   44,  256,  123,   97,   98,    0,   60,   61,   62,
   59,   45,   45,   45,   82,  165,   60,   61,   62,  273,
  256,   41,   42,   43,   44,   45,   40,   47,   44,   45,
   42,  267,    0,   59,  123,   47,   67,   40,   45,   59,
   60,   61,   62,   38,  256,   17,  256,   41,   42,   43,
   44,   45,  262,   47,    4,   86,  268,   42,   59,  123,
  210,    0,   47,   45,   36,   59,   60,   61,   62,   59,
  268,  265,  123,   41,  268,   43,   44,   45,   83,  277,
  278,  260,  125,  281,   41,   35,   43,    0,   45,  268,
   59,   59,   60,   61,   62,  267,   41,   92,   93,   44,
   50,   73,   41,  123,   43,   44,   45,   40,  103,  125,
   41,   42,   43,    0,   45,   41,   47,   43,  125,   45,
   59,   60,   61,   62,   40,  123,   41,  268,   41,   44,
   43,   44,   45,  258,  279,  107,  277,  278,  268,    0,
  281,  146,   41,  260,  149,   44,   59,   60,   61,   62,
   41,  279,  256,   44,   41,  150,   43,   44,   45,  279,
   41,   42,   43,   41,   45,    0,   47,  235,  236,  237,
  256,  239,   59,   60,   61,   62,  265,  256,  128,  268,
   41,  123,   43,   44,   45,  268,   62,  268,    0,  259,
  279,  268,  262,  198,  268,  256,  264,  271,   59,   60,
   61,   62,  262,  267,  256,  279,   41,  268,   43,   44,
   45,  268,   41,  262,  265,  267,  268,  268,    0,  191,
   41,  274,  275,  276,   59,   60,   61,   62,  279,   41,
  274,  275,  276,  267,  267,  267,  256,  257,  258,  259,
  260,  261,  262,  263,  264,    0,  266,   59,  268,  267,
  270,  267,  272,  273,  274,  275,  276,  277,  278,   41,
  267,  281,  256,  257,  258,  259,  260,  261,  262,  263,
  264,    0,  266,   43,  268,   45,  270,   59,  272,  273,
  274,  275,  276,  277,  278,  267,   41,  281,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  125,  266,    0,
  268,   40,  270,  125,  272,  273,  274,  275,  276,  277,
  278,   41,   41,  281,   44,  267,   41,  256,  257,  258,
  259,  260,  261,  262,  263,  264,    0,  266,  279,  268,
   59,  270,  261,  272,  273,  274,  275,  276,  277,  278,
   41,  125,  281,  256,  257,  258,  259,  260,  261,  262,
  263,  264,    0,  266,   40,  268,   40,  270,   59,  272,
  273,  274,  275,  276,  277,  278,  125,   41,  281,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   41,  266,
   43,  268,   45,  270,  125,  272,  273,  274,  275,  276,
  277,  278,  262,   41,  281,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   41,  268,   43,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  267,   44,
  281,  256,  257,  258,  259,  260,  261,  262,  263,  264,
    0,  266,   41,  268,   44,  270,   45,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  281,  259,   44,  261,
  262,  263,  264,   41,  266,   43,  268,   45,  270,   41,
  272,  273,   44,  279,  256,  277,  278,  259,  125,  281,
  262,   41,   41,   41,  256,  257,   44,  259,  125,  261,
  262,  263,  264,   41,  266,   43,  268,   45,  270,   59,
  272,  273,  125,  125,  125,  277,  278,   41,   41,  281,
   41,  256,  257,   41,  125,   41,  261,    0,  263,  264,
   41,  266,   40,  268,   45,  270,    0,  272,  273,   51,
   52,   53,  277,  278,  261,   34,  281,  256,  257,  170,
  259,   60,  261,  262,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   44,   45,   -1,  277,  278,
   -1,   -1,  281,   -1,   -1,  256,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,   41,   -1,  277,  278,   45,  111,
  281,   -1,  256,  257,  116,   -1,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   45,  266,  256,
  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,
  278,  268,   41,  281,   -1,   44,   -1,   -1,   45,   -1,
  277,  278,   -1,   -1,  281,   -1,   41,  256,  257,   44,
   -1,   60,   61,   62,  263,  264,   -1,  266,  267,  268,
  269,  270,   -1,  272,  273,   60,   61,   62,  277,  278,
   40,   -1,  281,   -1,   44,   45,   -1,   -1,   40,   -1,
    0,    1,   44,   45,    4,   -1,  256,  257,    8,   -1,
   -1,  261,   -1,  263,  264,   -1,  266,   17,  268,   45,
  270,   -1,  272,  273,   24,   45,    1,  277,  278,    4,
    6,  281,   -1,   -1,   45,   35,   36,   41,   42,   43,
   -1,   45,   17,   47,   -1,  256,   60,   61,   62,   24,
   50,   51,   52,   53,   -1,   -1,  267,  268,   34,  268,
   35,   36,   -1,   41,   40,   65,   -1,   67,  277,  278,
   -1,   71,  281,   73,   50,   50,   -1,  256,   -1,   55,
   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,  267,  268,
   65,   -1,   -1,   69,   -1,   -1,   71,   -1,   73,   99,
   60,   61,   62,   42,   43,   81,   45,  107,   47,  256,
   86,  111,   -1,  113,   -1,   -1,  116,   -1,   -1,   95,
  267,  268,  122,   41,   99,   -1,   44,  127,  128,   59,
  277,  278,  107,  133,  281,   42,   43,   43,   45,   45,
   47,   -1,   60,   61,   62,   -1,   -1,  122,  256,   -1,
   -1,   -1,   59,  128,   60,   61,   62,   -1,  133,  267,
  268,   -1,   -1,   -1,   -1,  165,   -1,   -1,  168,  256,
   -1,  147,  148,   59,   -1,   -1,   -1,   -1,  178,  179,
  267,  268,  182,   -1,   -1,  274,  275,  276,   -1,   -1,
  165,  191,   59,  168,   -1,   -1,  196,   -1,   -1,  274,
  275,  276,   -1,   -1,   -1,   -1,  256,   -1,   -1,  209,
  210,   -1,   -1,  213,  256,   -1,  191,  267,  268,   -1,
  220,  196,   -1,   -1,   -1,  267,  268,   -1,  204,   -1,
  256,   -1,   -1,  233,  209,  210,  256,   -1,  213,   -1,
   -1,  267,  268,  243,  244,  256,   -1,  267,  268,   -1,
   -1,   -1,  256,   71,  230,   -1,  267,  268,  233,   -1,
  274,  275,  276,  267,  268,   -1,   -1,   -1,  243,  244,
   -1,  271,   -1,    1,   -1,   -1,    4,   -1,  256,  257,
   -1,   99,   -1,   -1,  260,  263,  264,   -1,  266,   17,
  268,   -1,  270,   -1,  272,  273,  271,   -1,   -1,  277,
  278,   -1,   -1,  281,   -1,   -1,   -1,   35,   36,   -1,
   -1,   -1,   -1,   -1,  274,  275,  276,  256,   -1,   41,
   42,   43,   50,   45,   -1,   47,   -1,  257,  267,  268,
   -1,  261,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  181,   73,  183,  184,   -1,  256,
  168,   -1,   -1,   -1,   -1,   -1,  274,  275,  276,   -1,
  267,  268,   -1,   -1,   -1,   -1,   -1,   -1,  274,  275,
  276,  257,   -1,   -1,   -1,  261,   -1,  263,  196,  107,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,  226,
  257,  209,   -1,   -1,  261,  213,  263,   -1,  257,  266,
  128,  268,  261,  270,  263,  272,  273,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  233,  253,  254,  255,   -1,
   -1,  258,   -1,  256,  257,  243,  244,  260,   -1,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  278,   -1,   -1,  277,  278,   -1,   -1,  281,   -1,
  256,  257,   -1,  271,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,  191,  270,   -1,  272,  273,   -1,  256,
  257,  277,  278,   -1,  261,  281,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,
  277,  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,  278,
   -1,   -1,  281,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  256,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  257,  272,  273,
  260,   -1,  262,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  257,  272,  273,  260,   -1,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
   -1,  263,   -1,  257,  266,   -1,  268,  261,  270,  263,
  272,  273,  266,   -1,  268,   -1,  270,  257,  272,  273,
   -1,  261,   -1,  263,   -1,  257,  266,   -1,  268,  261,
  270,  263,  272,  273,  266,   -1,  268,  257,  270,   -1,
  272,  273,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=281;
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
"ASIGNACION","ERROR","OCTAL",
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
"tipo_primitivo : OCTAL",
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
"sentencia_IF : IF condicion bloque_THEN ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion bloque_THEN ';' END_IF",
"sentencia_IF : IF condicion THEN END_IF",
"sentencia_IF : IF condicion bloque_THEN ';' ELSE END_IF",
"sentencia_IF : IF condicion bloque_THEN bloque_else ';' END_IF",
"sentencia_IF : IF condicion bloque_THEN END_IF",
"sentencia_IF : IF condicion bloque_THEN ';' bloque_else END_IF",
"sentencia_IF : IF condicion bloque_THEN bloque_else END_IF",
"sentencia_IF : IF condicion bloque_THEN ';' error",
"sentencia_IF : IF condicion bloque_THEN ';' bloque_else ';' error",
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
"bloque_THEN : bloque_THEN_simple",
"bloque_THEN : bloque_THEN_multiple",
"bloque_THEN_multiple : THEN BEGIN bloque_sent_ejecutables ';' END",
"bloque_THEN_multiple : THEN BEGIN bloque_sent_ejecutables END",
"bloque_THEN_simple : THEN bloque_sentencia_simple",
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

//#line 334 "gramatica.y"
	
public static StringBuilder AMBITO = new StringBuilder("$MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>(); 
public static boolean RETORNOTHEN = false;
public static boolean RETORNOELSE = false;
public static Map<String,Tipo> tipos = new HashMap<>();
public static boolean esWHILE = false;
static{
	tipos.put("INTEGER", new Tipo("INTEGER"));
	tipos.put("DOUBLE", new Tipo("DOUBLE"));
	tipos.put("OCTAL", new Tipo("OCTAL"));
	tipos.put("ETIQUETA", new Tipo("ETIQUETA"));
}


public static void cargarErrorEImprimirlo(String salida) {	
		try {
			AnalizadorLexico.sintactico.newLine();  // Agregar un salto de l nea
			AnalizadorLexico.sintactico.write(" "+salida+" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

private static void cargarCadenaMultilinea(String cadena){
	Tipo t = new Tipo("CADENAMULTILINEA");
	tipos.put("CADENAMULTILINEA",t);
	cargarVariables(cadena,t," Cadena multilinea ");
}

private static void modificarPolacaPM(String operador, int cantDeOp){
	//GeneradorCodigoIntermedio.addOperadorEnPattMatch(operador,cantDeOp);
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
}

private static void opCondicion(String operador){
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
};

private static void completarBifurcacionAGoto(String id){
	int pos = GeneradorCodigoIntermedio.getGoto(id);
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	while (pos!=-1){
		GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
		pos = GeneradorCodigoIntermedio.getGoto(id);
	}
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);
} 

private static void operacionesWhile(int cantDeOperandos){
	completarBifurcacionF();
	GeneradorCodigoIntermedio.bifurcarAlInicio();
}

private static void completarBifurcacionISinElse(){
	
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void operacionesIF(){
	
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.bifurcarI();
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void completarBifurcacionF(){
	
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
}
private static void completarBifurcacionI(){
	
	int pos = GeneradorCodigoIntermedio.getPila();
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);
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
	if(t.esSubTipo()){
		cargarErrorEImprimirlo( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirlo( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		if(min.contains(".") && max.contains(".")){
			double mini = Double.valueOf(min);
			double maxi = Double.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi));
		}else{
			int mini=Integer.valueOf(min);
			int maxi = Integer.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi));
		}
	}
	return tipos.get(name);
}

private static Tipo cargarTripla(String name, Tipo t, boolean tripla){
	
	if(t.esSubTipo()){
		cargarErrorEImprimirlo( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirlo( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		tipos.put(name,new Tipo(t.getType(),tripla));
	}
	return tipos.get(name);
}

private static boolean fueDeclarado(String id){
	
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    while (true) {
        // Construimos la clave: id +  mbito actual
        String key = id + ambitoActual;
        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el  mbito: eliminamos el  ltimo ':NIVEL'
        int pos = ambitoActual.lastIndexOf("$");
        if (pos == -1) {
            break; // Si ya no hay m s  mbitos, salimos del ciclo
        }

        // Reducimos el  mbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

    // Si no se encuentra en ning n  mbito, lanzamos un error o devolvemos null
    throw new RuntimeException( "Linea :" + AnalizadorLexico.saltoDeLinea +"Error: ID '" + id + "' no encontrado en ning n  mbito.");
}

public static Simbolo getVariableFueraDeAmbito(String id){
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String
    String key = id;
    while (true) {
        // Construimos la clave: id +  mbito actual

        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el  mbito: eliminamos el  ltimo '$NIVEL'
        int pos = key.lastIndexOf("$");

        // Reducimos el  mbito actual
        key = key.substring(0,pos);
    }
}
private static boolean existeEnEsteAmbito(String id){
	//System.out.println("  > Buscando la declaracion < ");
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    // Construimos la clave: id +  mbito actual
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
		if(tipo.getType() != "ETIQUETA"){
			if(!existeEnEsteAmbito(v)){
			if(tipo.getType()=="CADENAMULTILINEA"){
				addTipo(v,tipo);
				addUso(v,uso);
				declarar(v);
			}else{
				addAmbitoID(v);
				AnalizadorLexico.TablaDeSimbolos.get(v+AMBITO.toString()).setAmbitoVar(AMBITO.toString()+"$"+v);
				addTipo(v+AMBITO.toString(),tipo);
				addUso(v+AMBITO.toString(),uso);
				declarar(v+AMBITO.toString());
			}
			}else{
			cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +"  La variable  " + v + " ya fue declarada.");
			}
		}else{
			addTipo(v,tipo);
			addUso(v,uso);
			declarar(v);
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
        // Retorna un arreglo vac o o el string completo como  nico elemento
        return new String[] { var };
    }
    // Usa split() para dividir el String por el car cter '/'
    String[] variables = var.split(separador);
    return variables;
}

private static void addTipo(String id, Tipo tipo) {
	AnalizadorLexico.TablaDeSimbolos.get(id).setTipoVar(tipo);
};

private static void agregarAmbito(String amb) {
	AMBITO.append("$").append(amb);
}

private static void sacarAmbito() {
	// agarro el index del ultimo ':'
	int pos = AMBITO.lastIndexOf("$");
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
	System.out.println();
}

private static void cambioCTENegativa(String key) {
	String keyNeg = "-" + key;
	if(AnalizadorLexico.TablaDeSimbolos.get(key) != null){
		if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
			AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
		}
		AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
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
				yyerror("Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango.");
				return false;
			}
		}
	}
	return true;
}
//#line 1060 "Parser.java"
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
case 3:
//#line 17 "gramatica.y"
{cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el nombre del programa ");}
break;
case 4:
//#line 18 "gramatica.y"
{cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador END ");}
break;
case 5:
//#line 19 "gramatica.y"
{cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador BEGIN ");}
break;
case 6:
//#line 20 "gramatica.y"
{cargarErrorEImprimirlo("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan los delimitadores del programa ");}
break;
case 7:
//#line 23 "gramatica.y"
{if(val_peek(1).sval=="RET" || val_peek(0).sval=="RET" ){yyval.sval="RET";}}
break;
case 8:
//#line 24 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 10:
//#line 28 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  ");}
break;
case 11:
//#line 29 "gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";}}
break;
case 12:
//#line 30 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 16:
//#line 38 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 17:
//#line 39 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 18:
//#line 42 "gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); }
break;
case 19:
//#line 43 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 20:
//#line 46 "gramatica.y"
{ if(tipos.containsKey(val_peek(0).sval)){yyval.obj = tipos.get(val_peek(0).sval);
					}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}
break;
case 21:
//#line 48 "gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 22:
//#line 51 "gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 23:
//#line 53 "gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 24:
//#line 55 "gramatica.y"
{ if(!tipos.containsKey("OCTAL")){tipos.put("OCTAL",new Tipo("OCTAL"));}
							yyval.obj = tipos.get("OCTAL");}
break;
case 25:
//#line 59 "gramatica.y"
{if(val_peek(5).obj != null){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
break;
case 26:
//#line 60 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango ");}
break;
case 27:
//#line 61 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango ");}
break;
case 28:
//#line 62 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango ");}
break;
case 29:
//#line 63 "gramatica.y"
{if(val_peek(2).obj != null){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
break;
case 30:
//#line 64 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior ");}
break;
case 31:
//#line 65 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos ");}
break;
case 32:
//#line 66 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior ");}
break;
case 33:
//#line 67 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos ");}
break;
case 34:
//#line 68 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido ");}
break;
case 35:
//#line 69 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo ");}
break;
case 36:
//#line 70 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE ");}
break;
case 37:
//#line 71 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE");}
break;
case 38:
//#line 72 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE");}
break;
case 39:
//#line 73 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE");}
break;
case 40:
//#line 74 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion");}
break;
case 41:
//#line 78 "gramatica.y"
{	if(val_peek(1).sval!="RET"){cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion ");}
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal(val_peek(4).sval,(Tipo)val_peek(3).obj);									
								}
break;
case 42:
//#line 85 "gramatica.y"
{yyval.sval=val_peek(0).sval;cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj," nombre de funcion ");;agregarAmbito(val_peek(0).sval);DENTRODELAMBITO.push(val_peek(0).sval);GeneradorCodigoIntermedio.addNuevaPolaca();}
break;
case 43:
//#line 86 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion ");}
break;
case 44:
//#line 89 "gramatica.y"
{yyval.obj=val_peek(2).obj; GeneradorCodigoIntermedio.addElemento(val_peek(1).sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}
break;
case 45:
//#line 90 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion ");}
break;
case 46:
//#line 91 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion ");}
break;
case 47:
//#line 92 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion ");}
break;
case 48:
//#line 93 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). ");}
break;
case 49:
//#line 96 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 50:
//#line 99 "gramatica.y"
{if(!existeFuncion()){cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
										}else{
											GeneradorCodigoIntermedio.addElemento("RET");
										}}
break;
case 51:
//#line 103 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  ");
						if(!existeFuncion())
										{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
						}}
break;
case 53:
//#line 111 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 56:
//#line 114 "gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){completarBifurcacionAGoto(val_peek(0).sval);}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA que se pretende bifurcar no existe.  ");}}
break;
case 58:
//#line 116 "gramatica.y"
{yyval.sval="RET";}
break;
case 59:
//#line 119 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("OUTF");}
break;
case 60:
//#line 120 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
break;
case 61:
//#line 121 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("OUTF");}
break;
case 62:
//#line 122 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. ");}
break;
case 63:
//#line 125 "gramatica.y"
{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;
															GeneradorCodigoIntermedio.addElemento(val_peek(2).sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una variable no declarada ");}
															}
break;
case 64:
//#line 132 "gramatica.y"
{if(fueDeclarado(val_peek(5).sval)){
																		if(Integer.valueOf(val_peek(3).sval) <= 3){
																			yyval.sval = val_peek(5).sval;
																			GeneradorCodigoIntermedio.addElemento(val_peek(5).sval+Parser.AMBITO.toString());
																			GeneradorCodigoIntermedio.addElemento(val_peek(3).sval);
																			GeneradorCodigoIntermedio.addElemento("INDEX");
																			GeneradorCodigoIntermedio.addElemento(":="); 
																			}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");}														
																	}else{
																		cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una variable no declarada ");}}
break;
case 65:
//#line 142 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 66:
//#line 145 "gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una funcion no declarada ");}
													else{	
														if(AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval+AMBITO.toString()).getTipoParFormal()==AnalizadorLexico.TablaDeSimbolos.get(val_peek(1).sval+AMBITO.toString()).getTipo().getType()){
															GeneradorCodigoIntermedio.invocar(val_peek(3).sval+AMBITO.toString());
														}else{
															cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: Tipos incompatibles entre  "
															 + AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval+AMBITO.toString()).getTipoParFormal()
															  + " y " +AnalizadorLexico.TablaDeSimbolos.get(val_peek(1).sval+AMBITO.toString()).getTipo().getType());
														}																																																		
												}}
break;
case 67:
//#line 157 "gramatica.y"
{if(!fueDeclarado(val_peek(6).sval)){
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una funcion no declarada ");}
													else{
														if(AnalizadorLexico.TablaDeSimbolos.get(val_peek(6).sval+AMBITO.toString()).getTipoParFormal()==((Tipo)val_peek(4).obj).getType()){
															GeneradorCodigoIntermedio.invocar(val_peek(6).sval+AMBITO.toString());
														}else{
															cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: Tipos incompatibles entre  "
															 + AnalizadorLexico.TablaDeSimbolos.get(val_peek(6).sval+AMBITO.toString()).getTipoParFormal()
															  + " y " +((Tipo)val_peek(4).obj).getType());
														}
												}}
break;
case 68:
//#line 168 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
break;
case 69:
//#line 169 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
break;
case 70:
//#line 173 "gramatica.y"
{yyval.ival=val_peek(2).ival + 1;}
break;
case 71:
//#line 174 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 72:
//#line 175 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 73:
//#line 176 "gramatica.y"
{yyval.ival=1;}
break;
case 74:
//#line 179 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("+"); }
break;
case 75:
//#line 180 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("-"); }
break;
case 77:
//#line 182 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 78:
//#line 183 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 79:
//#line 184 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 80:
//#line 185 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 81:
//#line 186 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
break;
case 82:
//#line 189 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("*");}
break;
case 83:
//#line 190 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("/");}
break;
case 85:
//#line 192 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 86:
//#line 193 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 87:
//#line 194 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 88:
//#line 195 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 89:
//#line 198 "gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una variable no declarada ");};}
break;
case 90:
//#line 199 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 92:
//#line 201 "gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ 
											if(Integer.valueOf(val_peek(1).sval) <= 3){
												GeneradorCodigoIntermedio.addElemento(val_peek(3).sval+Parser.AMBITO.toString());
												GeneradorCodigoIntermedio.addElemento(val_peek(1).sval);
												GeneradorCodigoIntermedio.addElemento("INDEX");
												AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval).incrementarContDeRef(); yyval.sval = val_peek(3).sval;
											}else{
												cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");
											}
										}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invoc  una variable no declarada ");};}
break;
case 93:
//#line 211 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 94:
//#line 213 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 95:
//#line 214 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
break;
case 96:
//#line 215 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 99:
//#line 225 "gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 100:
//#line 226 "gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 101:
//#line 229 "gramatica.y"
{if(val_peek(3).sval=="RET" && val_peek(1).sval=="RET"){yyval.sval="RET";};completarBifurcacionI();}
break;
case 102:
//#line 230 "gramatica.y"
{yyval.sval=val_peek(1).sval;completarBifurcacionISinElse();}
break;
case 103:
//#line 231 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
break;
case 104:
//#line 232 "gramatica.y"
{{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}
break;
case 105:
//#line 235 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
break;
case 106:
//#line 236 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
break;
case 107:
//#line 237 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
break;
case 108:
//#line 238 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
break;
case 109:
//#line 240 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
break;
case 110:
//#line 241 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
break;
case 111:
//#line 244 "gramatica.y"
{if(val_peek(6).ival == val_peek(2).ival){yyval.ival=val_peek(6).ival;modificarPolacaPM(val_peek(4).sval,val_peek(6).ival);}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
break;
case 112:
//#line 245 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 113:
//#line 246 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 114:
//#line 247 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 115:
//#line 248 "gramatica.y"
{yyval.ival=1;opCondicion(val_peek(2).sval);}
break;
case 116:
//#line 249 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 117:
//#line 250 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 118:
//#line 251 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 119:
//#line 253 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
break;
case 120:
//#line 255 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
break;
case 121:
//#line 256 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
break;
case 122:
//#line 257 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
break;
case 123:
//#line 260 "gramatica.y"
{yyval.sval=">";}
break;
case 124:
//#line 261 "gramatica.y"
{yyval.sval=">=";}
break;
case 125:
//#line 262 "gramatica.y"
{yyval.sval="<";}
break;
case 126:
//#line 263 "gramatica.y"
{yyval.sval="<=";}
break;
case 127:
//#line 264 "gramatica.y"
{yyval.sval="=";}
break;
case 128:
//#line 265 "gramatica.y"
{yyval.sval="!=";}
break;
case 129:
//#line 268 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 130:
//#line 269 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 131:
//#line 272 "gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 132:
//#line 273 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 133:
//#line 276 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 134:
//#line 279 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}
break;
case 135:
//#line 280 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}
break;
case 136:
//#line 283 "gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 137:
//#line 284 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 138:
//#line 287 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 139:
//#line 291 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 140:
//#line 292 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 141:
//#line 297 "gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 142:
//#line 298 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 143:
//#line 299 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 144:
//#line 302 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 145:
//#line 305 "gramatica.y"
{if(val_peek(2).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 146:
//#line 306 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 147:
//#line 307 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 148:
//#line 310 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 149:
//#line 313 "gramatica.y"
{cargarCadenaMultilinea(val_peek(0).sval);GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 150:
//#line 318 "gramatica.y"
{operacionesWhile(val_peek(1).ival);}
break;
case 151:
//#line 319 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
break;
case 152:
//#line 322 "gramatica.y"
{esWHILE=true;GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}
break;
case 153:
//#line 326 "gramatica.y"
{cargarVariables(val_peek(0).sval,tipos.get("ETIQUETA"),"ETIQUETA");
								GeneradorCodigoIntermedio.BifurcarAGoto(val_peek(0).sval);}
break;
case 154:
//#line 328 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO ");}
break;
//#line 1824 "Parser.java"
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
