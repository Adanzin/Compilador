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



//#line 2 "Gramatica.y"
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
    7,   13,   13,   14,   14,   14,   14,   14,   14,   15,
   16,   16,    5,    5,    5,    5,    5,    5,    5,   22,
   22,   22,   22,   22,   18,   18,   18,   18,   25,   25,
   25,   25,   26,   26,   26,   26,   17,   17,   17,   17,
   17,   17,   17,   17,   27,   27,   27,   27,   27,   27,
   27,   28,   28,   28,   28,   28,   10,   10,   10,   24,
    1,   12,   12,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   32,   32,   32,   32,   32,
   32,   31,   31,   34,   34,   34,   34,   33,   30,   30,
   38,   38,   38,   38,   37,   39,   39,   41,   41,   41,
   41,   40,   35,   35,   35,   36,   23,   20,   20,   42,
   21,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    2,    2,    1,    1,    3,    3,    1,
    1,    1,    1,    1,    9,    8,    8,    7,    6,    8,
    7,    8,    6,    8,    8,    5,    5,    5,    4,    6,
    5,    3,    2,    4,    4,    3,    3,    2,    3,    1,
    4,    3,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    4,    4,    6,    3,    6,    7,    2,    4,    7,
    3,    4,    3,    2,    2,    1,    3,    3,    1,    3,
    3,    3,    3,    2,    3,    3,    1,    3,    3,    3,
    3,    1,    1,    1,    4,    5,    3,    2,    1,    1,
    1,    1,    2,    7,    5,    4,    6,    6,    4,    6,
    5,    5,    7,    9,    8,    8,    7,    5,    4,    4,
    3,    3,    8,    8,    8,    1,    1,    1,    1,    1,
    1,    1,    1,    5,    3,    4,    4,    2,    1,    1,
    5,    3,    4,    4,    2,    1,    1,    4,    2,    3,
    3,    1,    3,    1,    2,    1,    1,    3,    3,    1,
    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,  101,    0,    0,    0,    0,    0,    0,    0,  160,
    0,   57,   22,   23,    0,   24,    0,    0,    8,    9,
    0,   13,    0,    0,    0,   21,    0,   59,   53,   54,
   55,   56,   58,    0,    0,    0,    0,   10,    0,  102,
    0,    0,    0,   93,    0,    0,   94,    0,   87,    0,
    0,    0,    0,    0,    0,    0,  163,  161,  162,    0,
    3,    7,   11,   14,   15,    0,  100,    0,   99,    0,
    0,    0,    0,    0,    2,    0,    5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  103,    0,  127,
  129,  131,  128,  126,    0,    0,  130,    0,    0,    0,
    0,    0,    0,  139,  140,    0,  157,    0,   61,    0,
    0,    0,    0,    0,    0,   20,    0,    0,    0,    0,
   52,    0,   42,   19,   18,    0,   98,    0,   48,    0,
    0,    0,    0,    0,    0,  159,    0,  156,  152,  158,
  146,  147,    1,   81,   80,   88,   89,    0,    0,    0,
    0,    0,    0,    0,   71,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   90,   85,   91,   86,    0,  106,
  145,    0,  109,    0,    0,  132,  133,    0,   63,   60,
   62,    0,    0,    0,   39,    0,    0,    0,    0,   51,
   97,   49,   47,    0,   46,    0,    0,    0,    0,    0,
  149,    0,  154,    0,    0,    0,    0,    0,   72,    0,
   69,  119,   95,    0,  142,    0,    0,  138,  112,    0,
  105,    0,  111,    0,    0,    0,    0,    0,   38,   37,
    0,   36,    0,    0,    0,   45,   44,   41,    0,    0,
  151,  150,    0,  155,    0,    0,    0,  118,    0,    0,
   96,  144,  143,    0,  135,    0,  107,  110,    0,  108,
   64,    0,    0,   40,   29,    0,    0,    0,   33,    0,
    0,    0,    0,  148,  153,    0,    0,    0,    0,    0,
    0,  141,  137,  136,    0,  113,  104,    0,    0,    0,
    0,    0,   31,    0,    0,    0,    0,    0,    0,    0,
   70,  134,   34,   35,   30,   32,    0,   26,  123,    0,
  125,  124,  115,   25,  114,
};
final static short yydgoto[] = {                          3,
   43,   18,   19,   20,  138,   22,   23,   24,   25,   68,
   26,   44,   27,   71,  198,   28,  149,   29,   30,   31,
   32,   33,  113,   46,   47,   87,   48,   49,   50,  103,
  175,   98,  176,  177,  202,  203,  104,  105,  140,  141,
  142,   35,
};
final static short yysindex[] = {                      -217,
 1057,    0,    0,  947,  -53,  399,   -4,  203,   24,    0,
 -163,    0,    0,    0,   -3,    0,    0,  973,    0,    0,
   43,    0,   45,   65, -146,    0,   42,    0,    0,    0,
    0,    0,    0, -110,  399,  992, 1019,    0,  933,    0,
   69, -156,   83,    0,  154,   16,    0,   31,    0, -115,
  758,  713, -127, -127, -131,  396,    0,    0,    0,    4,
    0,    0,    0,    0,    0, -109,    0,    6,    0,   51,
  -94,   -3,  -11, 1155,    0, 1038,    0,   47, -227,  -80,
  -73,  -65,   31,   -3,  149,  154,  127,    0,   25,    0,
    0,    0,    0,    0,  305,  717,    0,   -3,   13,  743,
  745, 1169,  -36,    0,    0,  927,    0,  -55,    0,   83,
  467,  159,  164, -101, -127,    0,  -59,   88,  150,  288,
    0,  311,    0,    0,    0,  -31,    0,  201,    0,  -41,
  -33, 1057,    4,  143,    8,    0, 1197,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    4,    4,  -16,
   -3,   -3,  873,  569,    0,  236,  346,   47,   31,   47,
   31,  359,  157,   29,    0,    0,    0,    0, 1211,    0,
    0, 1225,    0, -161,  -49,    0,    0,  158,    0,    0,
    0,   18,  -58,  -31,    0,   27,  -31,   27,   -6,    0,
    0,    0,    0,  250,    0,  259, 1057,   44,   30,  185,
    0,  785,    0,  -29,  271,  415,    4,  277,    0,   -3,
    0,    0,    0,  193,    0,  809, 1239,    0,    0, 1183,
    0,  -38,    0,   61,  292,  206,   71, -189,    0,    0,
  295,    0,  296,   17,  324,    0,    0,    0,   -3,   90,
    0,    0, 1253,    0,  149,  290,  149,    0,  149,  480,
    0,    0,    0, 1267,    0,  833,    0,    0, -176,    0,
    0,   30,  248,    0,    0,   27,   27,   27,    0,  -37,
   27,    4,   -3,    0,    0,  133,  149,  178,  226,  260,
  333,    0,    0,    0, 1281,    0,    0,   90,  278,  282,
  287,   11,    0,  289,    4,  334,  283,  378,  382,  390,
    0,    0,    0,    0,    0,    0,  307,    0,    0,  392,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -93,    0,    0,    0,
  632,    0,  659,  687,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  438,    0,    0,    0,
    0,    0,  189,    0,    0,  120,    0,   85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  326,
    0,    0,    0,    0,    0,  402,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  443,    0,    0,    0,    0,
    0,    0,  228,    0,    0,  439,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -27,
    0,    0,    0,  500,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  413,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,   96,    0,
    0,  146,  903,    0,    0,    0,    0,  254,  365,  493,
  519, 1083,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  208,    0,    0,    0,
    0,    0,    0,    0,    0, 1101,  374,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, 1002,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  440,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1090,    0,    0,
    0,    0,    0,  577,  550,    0,    0,    0,    0, 1119,
    0,    0,    0,    0,    0,    0,  606,    0,    0, 1137,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,   64,  843,    0, 1091,    0,    0,    0,  424,    0,
  -13,  885,    0,    0,    0,    0,  911,    0,    0,    0,
    0,    0,    0,   23,    0,  757,  562,   52,  444,    0,
  299,  -69,    0,    0, -134,  914,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=1560;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        193,
    4,   17,  184,  228,   17,   38,  292,  195,   55,  224,
  245,   59,   73,  100,  100,  100,  151,  100,   17,  100,
  259,  182,  174,   34,  204,   67,   34,  152,  144,  100,
   93,   97,   94,  135,  216,   51,   17,   17,   42,   88,
   34,   42,    1,   93,   97,   94,   96,   69,   95,  126,
    2,  110,  116,  116,  116,   42,  131,  164,   34,   34,
  268,   42,  227,   56,  125,  155,  264,   37,   67,   42,
  130,   42,  100,  114,   67,  156,   17,  101,    2,  286,
  205,   70,  256,  208,   79,  287,   74,  293,   81,   74,
  127,  129,   57,   82,  219,  100,   34,  220,   34,   76,
  221,   63,   67,   64,    2,   74,   74,   74,   85,   58,
   88,   17,   84,   42,  111,  116,  234,  185,   66,   92,
  116,    2,   89,   65,   34,   79,   67,   79,   79,   79,
  194,  196,   17,   34,  246,  306,   76,   67,   99,   76,
    2,  269,  102,   79,   79,   79,   79,  120,  191,   13,
   14,  166,  168,   16,   34,   76,   76,   76,  123,   34,
   92,   92,   92,   92,   92,  132,   92,  153,   72,   67,
  152,   20,   67,  296,   20,  145,  152,   72,   92,   92,
   92,   92,  146,  229,  230,  100,   75,  232,  100,   75,
  147,   34,   84,   42,   34,  197,   96,   17,   95,  180,
  178,   96,   67,   95,  181,   75,   75,   75,    2,    2,
  186,  187,  223,   93,   97,   94,   67,   67,  298,   34,
   67,  152,  172,  258,   34,  173,    2,   84,  265,  100,
  100,  100,  100,  100,    2,  100,    2,   20,   34,   34,
   20,  192,   34,   67,   90,   91,   92,  100,  100,  100,
  100,  100,   39,   83,   67,  134,   67,   90,   91,   92,
   40,  124,   54,   40,    2,   34,  299,  199,   84,  152,
   84,   84,   84,    2,  200,  210,   34,   40,   34,  163,
  154,  213,  225,   40,  226,   67,   84,   84,   84,   84,
  236,   40,    2,   40,   83,  214,   83,   83,   83,  237,
  300,   13,   14,  152,  238,   16,  128,   34,  239,  240,
  247,  100,   83,   83,   83,   83,  249,  251,    2,   74,
   74,   74,  260,  310,   39,   68,  152,   13,   14,  277,
  262,   16,  261,   84,   42,   40,    2,  263,  266,  267,
   79,   79,   79,   79,   79,   79,   79,   79,   79,   42,
   79,  190,   79,   96,   79,   95,   79,   79,   79,   79,
   79,   79,   79,   79,   78,   79,   68,  271,  273,   76,
   76,   76,  288,  301,  309,   92,   92,   92,   92,   92,
   92,   92,   92,   92,   68,   92,  211,   92,   96,   92,
   95,   92,   92,   92,   92,   92,   92,   92,   92,  212,
   92,   96,  303,   95,   39,   78,  304,   78,   78,   78,
  188,  305,   65,  308,   73,   40,    2,   73,  311,   75,
   75,   75,  312,   78,   78,   78,   78,   90,   91,   92,
  313,  314,  315,   73,   73,   73,  121,    6,   41,   66,
   42,   43,    4,   42,  100,  100,  100,  100,  100,  100,
  100,  100,  100,   65,  100,  248,  100,   96,  100,   95,
  100,  100,  100,  100,  100,  100,  100,  100,   50,  100,
    2,   65,  222,   52,    0,  117,  118,  119,   74,   76,
   66,   53,   76,   84,   84,   84,   84,   84,   84,   84,
   84,   84,   82,   84,    0,   84,    0,   84,   66,   84,
   84,   84,   84,   84,   84,   84,   84,  179,   84,   83,
   83,   83,   83,   83,   83,   83,   83,   83,   77,   83,
  281,   83,   96,   83,   95,   83,   83,   83,   83,   83,
   83,   83,   83,   82,   83,   82,   82,   82,  183,    0,
   92,   92,   92,  189,   92,   39,   92,    0,    0,   67,
    0,   82,   82,   82,   82,    2,   40,    2,    0,   77,
  158,   77,   77,   77,   13,   14,    0,    0,   16,    0,
    0,   40,    2,    0,    0,    0,   28,   77,   77,   77,
   77,   68,   68,    0,   68,    0,   68,   68,   68,   68,
   67,   68,    0,   68,    0,   68,    0,   68,   68,    0,
   83,    0,   68,   68,   68,   27,   68,    0,   67,  209,
   81,   80,    0,   79,    0,   82,    0,   28,    0,    0,
   78,   78,   78,   78,   78,   78,   78,   78,   78,    0,
   78,   12,   78,    0,   78,   28,   78,   78,   78,   78,
   78,   78,   78,   78,    0,   78,   27,   73,   73,   73,
    0,   39,    0,    0,   39,    0,  159,  161,   16,    0,
    0,    0,   40,    2,   27,   40,    2,   83,   65,   65,
    0,   65,   12,   65,   65,   65,   65,    0,   65,    0,
   65,    0,   65,    0,   65,   65,   17,    0,    0,   65,
   65,   65,    0,   65,    0,   66,   66,    0,   66,   16,
   66,   66,   66,   66,    0,   66,    0,   66,    0,   66,
    0,   66,   66,    0,    0,   83,   66,   66,   66,    0,
   66,    0,    5,    6,    0,    0,    0,   17,    0,    7,
    8,    0,    9,    0,    2,    0,   10,    0,   11,   12,
    0,    0,    0,   13,   14,   15,    0,   16,   82,   82,
   82,   82,   82,   82,   82,   82,   82,    0,   82,    0,
   82,   42,   82,    0,   82,   82,   82,   82,   82,   82,
   82,   82,  115,   82,   77,   77,   77,   77,   77,   77,
   77,   77,   77,    0,   77,    0,   77,   42,   77,   42,
   77,   77,   77,   77,   77,   77,   77,   77,  109,   77,
    0,    0,   42,    0,    0,   67,   67,    0,   67,    0,
   67,   67,   67,   67,    0,   67,    0,   67,    0,   67,
    0,   67,   67,    0,   78,    0,   67,   67,   67,    0,
   67,    0,   28,   28,    0,   40,    2,   28,    0,   28,
   28,  150,   28,  243,   28,    0,   28,    0,   28,   28,
    0,    0,    0,   28,   28,   28,    0,   28,    0,    0,
   62,   27,   27,    0,    0,    0,   27,  254,   27,   27,
    0,   27,    0,   27,    0,   27,    0,   27,   27,   62,
  108,    0,   27,   27,   27,    0,   27,   12,   12,    0,
    0,  285,   12,    0,   12,   12,    0,   12,    0,   12,
    0,   12,    0,   12,   12,    0,    0,    0,   12,   12,
   12,    0,   12,    0,   16,   16,   45,    0,   62,   16,
    0,   16,   16,    0,   16,   60,   16,    0,   16,    0,
   16,   16,   93,   97,   94,   16,   16,   16,    0,   16,
    0,    0,   17,   17,    0,   45,    0,   17,    0,   17,
   17,   86,   17,   62,   17,    0,   17,    0,   17,   17,
    0,  112,    0,   17,   17,   17,  122,   17,   81,   80,
    0,   79,  160,   82,   81,   80,    0,   79,    0,   82,
    2,    0,  133,   40,    2,   38,    0,  139,    0,   13,
   14,    0,    0,   16,  148,    0,    0,    0,  165,  157,
  167,  276,  278,  279,    0,  280,    0,    0,  162,   40,
    2,   40,    2,  106,    6,  171,    0,    0,    0,    0,
    7,    8,    0,    9,   40,    2,  107,   10,    0,   11,
   12,    0,    0,  297,   13,   14,   15,    0,   16,   62,
  241,    6,   95,   95,   95,  242,   95,    7,   95,    0,
    9,    0,    2,    0,   10,    0,   11,   12,    0,    0,
    0,  206,  207,   15,  252,    6,    0,    0,    0,  253,
  231,    7,  233,  235,    9,    0,    2,    0,   10,    0,
   11,   12,    0,    0,    0,  218,    0,   15,  283,    6,
    0,   21,    0,  284,   21,    7,    0,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    0,   21,    0,
    0,   15,    0,    0,    0,  244,    0,    0,  270,    0,
  250,    0,    0,    0,    0,    0,   21,   21,    0,  244,
   96,   96,   96,  218,   96,    0,   96,    0,    0,    0,
    0,   21,    0,    0,    0,    0,   90,   91,   92,  272,
  289,  290,  291,    0,    0,  294,  275,    0,  122,  122,
  122,    0,  122,    0,    0,  122,   21,  275,  122,  244,
  122,    0,  122,    0,  122,  122,  307,    0,    0,    0,
    0,  122,   78,  295,    0,    0,    0,    0,   78,    0,
    0,    0,    0,   40,    2,    0,    0,    0,  275,   40,
    2,   21,    5,    6,    0,    0,   36,    0,    0,    7,
    8,    0,    9,    0,    2,    0,   10,    0,   11,   12,
    0,    0,   21,   13,   14,   15,    0,   16,    5,    6,
    0,    0,    0,   61,    0,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    5,    6,   13,
   14,   15,   75,   16,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    0,    0,   13,   14,
   15,    0,   16,    0,    5,    6,    0,    0,    0,   77,
    0,    7,    8,    0,    9,    0,    2,   21,   10,    0,
   11,   12,    0,    5,    6,   13,   14,   15,  143,   16,
    7,    8,    0,    9,    0,    2,    0,   10,    0,   11,
   12,    0,    5,    6,   13,   14,   15,    0,   16,    7,
    8,    0,    9,    0,    2,    0,   10,    0,   11,   12,
    0,    0,    0,   13,   14,   15,    0,   16,  121,  121,
  121,    0,  121,    0,    0,  121,    0,    0,  121,    0,
  121,    0,  121,    0,  121,  121,  120,  120,  120,    0,
  120,  121,    0,  120,    0,    0,  120,    0,  120,    0,
  120,    0,  120,  120,  117,  117,  117,    0,  117,  120,
    0,  117,    0,    0,  117,    0,  117,    0,  117,    0,
  117,  117,  116,  116,  116,    0,  116,  117,    0,  116,
    0,    0,  116,    0,  116,    0,  116,    0,  116,  116,
  136,    6,    0,    0,  137,  116,    0,    7,    0,    0,
    9,    0,    2,    0,   10,    6,   11,   12,  169,    0,
  170,    7,    0,   15,    9,    0,    2,    0,   10,    6,
   11,   12,  217,    0,  257,    7,    0,   15,    9,    0,
    2,    0,   10,    6,   11,   12,    0,  201,    0,    7,
    0,   15,    9,    0,    2,    0,   10,    6,   11,   12,
    0,  215,    0,    7,    0,   15,    9,    0,    2,    0,
   10,    6,   11,   12,  217,    0,    0,    7,    0,   15,
    9,    0,    2,    0,   10,    6,   11,   12,    0,  255,
    0,    7,    0,   15,    9,    0,    2,    0,   10,    6,
   11,   12,    0,  274,    0,    7,    0,   15,    9,    0,
    2,    0,   10,    6,   11,   12,    0,  282,    0,    7,
    0,   15,    9,    0,    2,    0,   10,    6,   11,   12,
    0,  302,    0,    7,    0,   15,    9,    0,    2,    0,
   10,    0,   11,   12,    0,    0,    0,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,    1,   62,   62,    4,   59,   44,   41,    8,   59,
   40,   11,  123,   41,   42,   43,   86,   45,   18,   47,
   59,  123,   59,    1,   41,   25,    4,   44,  256,  123,
   60,   61,   62,   45,  169,   40,   36,   37,   45,  267,
   18,   45,  260,   60,   61,   62,   43,   25,   45,   44,
  268,   51,   52,   53,   54,   45,   70,   45,   36,   37,
   44,   45,   45,   40,   59,   41,  256,    4,   68,   45,
   70,   45,   42,   51,   74,   89,   76,   47,  268,  256,
  150,   40,  217,  153,    0,  262,   41,  125,   42,   44,
   68,   41,  256,   47,  256,  123,   74,  259,   76,   36,
  262,   59,  102,   59,  268,   60,   61,   62,   40,  273,
  267,  111,   44,   45,   51,  115,  123,  117,  265,    0,
  120,  268,   40,   59,  102,   41,  126,   43,   44,   45,
  130,  131,  132,  111,  204,  125,   41,  137,  123,   44,
  268,  125,  258,   59,   60,   61,   62,  279,  126,  277,
  278,  100,  101,  281,  132,   60,   61,   62,  268,  137,
   41,   42,   43,   44,   45,  260,   47,   41,  279,  169,
   44,  265,  172,   41,  268,  256,   44,  279,   59,   60,
   61,   62,  256,  183,  184,  279,   41,  187,    0,   44,
  256,  169,   44,   45,  172,  132,   43,  197,   45,   41,
  256,   43,  202,   45,   41,   60,   61,   62,  268,  268,
  123,   62,  262,   60,   61,   62,  216,  217,   41,  197,
  220,   44,  259,  262,  202,  262,  268,    0,  228,   41,
   42,   43,   44,   45,  268,   47,  268,  265,  216,  217,
  268,   41,  220,  243,  274,  275,  276,   59,   60,   61,
   62,  279,  256,    0,  254,  267,  256,  274,  275,  276,
  267,  256,   60,  267,  268,  243,   41,  125,   41,   44,
   43,   44,   45,  268,  267,   40,  254,  267,  256,  267,
  256,  125,  125,  267,  267,  285,   59,   60,   61,   62,
   41,  267,  268,  267,   41,  267,   43,   44,   45,   41,
   41,  277,  278,   44,  261,  281,  256,  285,  279,  125,
   40,  123,   59,   60,   61,   62,   40,  125,  268,  274,
  275,  276,  262,   41,  256,    0,   44,  277,  278,   40,
  125,  281,   41,   44,   45,  267,  268,  267,   44,   44,
  256,  257,  258,  259,  260,  261,  262,  263,  264,   45,
  266,   41,  268,   43,  270,   45,  272,  273,  274,  275,
  276,  277,  278,  279,    0,  281,   41,   44,  279,  274,
  275,  276,  125,   41,   41,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   59,  266,   41,  268,   43,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  279,   41,
  281,   43,  125,   45,  256,   41,  125,   43,   44,   45,
  123,  125,    0,  125,   41,  267,  268,   44,   41,  274,
  275,  276,   41,   59,   60,   61,   62,  274,  275,  276,
   41,  125,   41,   60,   61,   62,   41,    0,   40,    0,
   45,   40,    0,   45,  256,  257,  258,  259,  260,  261,
  262,  263,  264,   41,  266,   41,  268,   43,  270,   45,
  272,  273,  274,  275,  276,  277,  278,  279,  261,  281,
  268,   59,  174,  271,   -1,   52,   53,   54,   35,   41,
   41,  279,   44,  256,  257,  258,  259,  260,  261,  262,
  263,  264,    0,  266,   -1,  268,   -1,  270,   59,  272,
  273,  274,  275,  276,  277,  278,  279,   41,  281,  256,
  257,  258,  259,  260,  261,  262,  263,  264,    0,  266,
   41,  268,   43,  270,   45,  272,  273,  274,  275,  276,
  277,  278,  279,   41,  281,   43,   44,   45,  115,   -1,
   41,   42,   43,  120,   45,  256,   47,   -1,   -1,    0,
   -1,   59,   60,   61,   62,  268,  267,  268,   -1,   41,
  256,   43,   44,   45,  277,  278,   -1,   -1,  281,   -1,
   -1,  267,  268,   -1,   -1,   -1,    0,   59,   60,   61,
   62,  256,  257,   -1,  259,   -1,  261,  262,  263,  264,
   41,  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,
   39,   -1,  277,  278,  279,    0,  281,   -1,   59,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   41,   -1,   -1,
  256,  257,  258,  259,  260,  261,  262,  263,  264,   -1,
  266,    0,  268,   -1,  270,   59,  272,  273,  274,  275,
  276,  277,  278,  279,   -1,  281,   41,  274,  275,  276,
   -1,  256,   -1,   -1,  256,   -1,   95,   96,    0,   -1,
   -1,   -1,  267,  268,   59,  267,  268,  106,  256,  257,
   -1,  259,   41,  261,  262,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,    0,   -1,   -1,  277,
  278,  279,   -1,  281,   -1,  256,  257,   -1,  259,   41,
  261,  262,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,  154,  277,  278,  279,   -1,
  281,   -1,  256,  257,   -1,   -1,   -1,   41,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,  279,   -1,  281,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   -1,  266,   -1,
  268,   45,  270,   -1,  272,  273,  274,  275,  276,  277,
  278,  279,   60,  281,  256,  257,  258,  259,  260,  261,
  262,  263,  264,   -1,  266,   -1,  268,   45,  270,   45,
  272,  273,  274,  275,  276,  277,  278,  279,   41,  281,
   -1,   -1,   45,   -1,   -1,  256,  257,   -1,  259,   -1,
  261,  262,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,  256,   -1,  277,  278,  279,   -1,
  281,   -1,  256,  257,   -1,  267,  268,  261,   -1,  263,
  264,   85,  266,   59,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,  279,   -1,  281,   -1,   -1,
   18,  256,  257,   -1,   -1,   -1,  261,   59,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,   37,
  123,   -1,  277,  278,  279,   -1,  281,  256,  257,   -1,
   -1,   59,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,
  279,   -1,  281,   -1,  256,  257,    6,   -1,   76,  261,
   -1,  263,  264,   -1,  266,   15,  268,   -1,  270,   -1,
  272,  273,   60,   61,   62,  277,  278,  279,   -1,  281,
   -1,   -1,  256,  257,   -1,   35,   -1,  261,   -1,  263,
  264,   41,  266,  111,  268,   -1,  270,   -1,  272,  273,
   -1,   51,   -1,  277,  278,  279,   56,  281,   42,   43,
   -1,   45,  256,   47,   42,   43,   -1,   45,   -1,   47,
  268,   -1,   72,  267,  268,   59,   -1,   74,   -1,  277,
  278,   -1,   -1,  281,   84,   -1,   -1,   -1,  256,   89,
  256,  245,  246,  247,   -1,  249,   -1,   -1,   98,  267,
  268,  267,  268,  256,  257,  102,   -1,   -1,   -1,   -1,
  263,  264,   -1,  266,  267,  268,  269,  270,   -1,  272,
  273,   -1,   -1,  277,  277,  278,  279,   -1,  281,  197,
  256,  257,   41,   42,   43,  261,   45,  263,   47,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,
   -1,  151,  152,  279,  256,  257,   -1,   -1,   -1,  261,
  186,  263,  188,  189,  266,   -1,  268,   -1,  270,   -1,
  272,  273,   -1,   -1,   -1,  172,   -1,  279,  256,  257,
   -1,    1,   -1,  261,    4,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,   -1,   18,   -1,
   -1,  279,   -1,   -1,   -1,  202,   -1,   -1,  234,   -1,
  210,   -1,   -1,   -1,   -1,   -1,   36,   37,   -1,  216,
   41,   42,   43,  220,   45,   -1,   47,   -1,   -1,   -1,
   -1,   51,   -1,   -1,   -1,   -1,  274,  275,  276,  239,
  266,  267,  268,   -1,   -1,  271,  243,   -1,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   76,  254,  266,  256,
  268,   -1,  270,   -1,  272,  273,  292,   -1,   -1,   -1,
   -1,  279,  256,  273,   -1,   -1,   -1,   -1,  256,   -1,
   -1,   -1,   -1,  267,  268,   -1,   -1,   -1,  285,  267,
  268,  111,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,  132,  277,  278,  279,   -1,  281,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,  279,  261,  281,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,
  279,   -1,  281,   -1,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,   -1,  266,   -1,  268,  197,  270,   -1,
  272,  273,   -1,  256,  257,  277,  278,  279,  261,  281,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,   -1,  256,  257,  277,  278,  279,   -1,  281,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,  279,   -1,  281,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,
  260,  279,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  256,  257,  258,   -1,  260,  279,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,  256,  257,  258,   -1,  260,  279,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  256,  257,   -1,   -1,  260,  279,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
  262,  263,   -1,  279,  266,   -1,  268,   -1,  270,  257,
  272,  273,  260,   -1,  262,  263,   -1,  279,  266,   -1,
  268,   -1,  270,  257,  272,  273,   -1,  261,   -1,  263,
   -1,  279,  266,   -1,  268,   -1,  270,  257,  272,  273,
   -1,  261,   -1,  263,   -1,  279,  266,   -1,  268,   -1,
  270,  257,  272,  273,  260,   -1,   -1,  263,   -1,  279,
  266,   -1,  268,   -1,  270,  257,  272,  273,   -1,  261,
   -1,  263,   -1,  279,  266,   -1,  268,   -1,  270,  257,
  272,  273,   -1,  261,   -1,  263,   -1,  279,  266,   -1,
  268,   -1,  270,  257,  272,  273,   -1,  261,   -1,  263,
   -1,  279,  266,   -1,  268,   -1,  270,  257,  272,  273,
   -1,  261,   -1,  263,   -1,  279,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,   -1,   -1,   -1,   -1,  279,
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
"parametros_parentesis : '(' ID_simple ID_simple ')'",
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
"outf_rule : OUTF '(' '{' error '}' ')'",
"asignacion : variable_simple ASIGNACION expresion_arit",
"asignacion : variable_simple '{' CTE '}' ASIGNACION expresion_arit",
"asignacion : variable_simple '{' '-' CTE '}' ASIGNACION expresion_arit",
"asignacion : ASIGNACION expresion_arit",
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
"bloque_else_multiple : ELSE BEGIN END",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables END",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables error",
"bloque_else_simple : ELSE bloque_sentencia_simple",
"bloque_THEN : bloque_THEN_simple",
"bloque_THEN : bloque_THEN_multiple",
"bloque_THEN_multiple : THEN BEGIN bloque_sent_ejecutables ';' END",
"bloque_THEN_multiple : THEN BEGIN END",
"bloque_THEN_multiple : THEN BEGIN bloque_sent_ejecutables END",
"bloque_THEN_multiple : THEN BEGIN bloque_sent_ejecutables error",
"bloque_THEN_simple : THEN bloque_sentencia_simple",
"bloque_unidad : bloque_unidad_simple",
"bloque_unidad : bloque_unidad_multiple",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables ';' END",
"bloque_unidad_multiple : BEGIN END",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables END",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables error",
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
"sentencia_goto : GOTO ID_simple",
"sentencia_goto : GOTO error",
};

//#line 350 "Gramatica.y"
	
public static StringBuilder AMBITO = new StringBuilder("$MAIN");																 
public static Stack<String> DENTRODELAMBITO = new Stack<String>(); 
public static boolean RETORNOTHEN = false;
public static boolean RETORNOELSE = false;
public static int cantDeOperandos;
public static Map<String,Tipo> tipos = new HashMap<>();
static{
	tipos.put("INTEGER", new Tipo("INTEGER"));
	tipos.put("DOUBLE", new Tipo("DOUBLE"));
	tipos.put("OCTAL", new Tipo("OCTAL"));
	tipos.put("ETIQUETA", new Tipo("ETIQUETA"));
}

public static boolean EerrorSintactico(){
		return CreacionDeSalidas.getOutputSintactico().length()!=0 || CreacionDeSalidas.getOutputLexico().length()!=0;
}


public static void cargarErrorEImprimirloSintactico(String salida) {	
		try {
			AnalizadorLexico.sintactico.newLine();  // Agregar un salto de l nea
			AnalizadorLexico.sintactico.write(" "+salida+" ");
			AnalizadorLexico.sintactico.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

public static void cargarErrorEImprimirloSemantico(String salida) {	
		try {
			AnalizadorLexico.semantico.newLine();  // Agregar un salto de l nea
			AnalizadorLexico.semantico.write(" "+salida+" ");
			AnalizadorLexico.semantico.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

private static void cargarGotos(){
	    while (!GeneradorCodigoIntermedio.BaulDeGotos.isEmpty()) {
	        // Obtenemos el primer elemento
	        String[] elemento = GeneradorCodigoIntermedio.BaulDeGotos.get(0);
	        String key = elemento[0];
	        boolean terminoWhile = false;
	        boolean noHayEtiqueta = false;

	        // Bucle para reducir el key si no se encuentra directamente
	        while (!terminoWhile) {
	            if (GeneradorCodigoIntermedio.Etiquetas.contains(key)) {
	                int pos = Integer.valueOf(elemento[2]);
	                GeneradorCodigoIntermedio.reemplazarElm(key, pos, elemento[1]); // Reemplaza el elemento con el m todo adecuado
	                GeneradorCodigoIntermedio.BaulDeGotos.remove(0); // Eliminamos el primer elemento
	                noHayEtiqueta = false;
	                terminoWhile = true; // Terminamos el ciclo si encontramos la etiqueta
	            } else {
	                noHayEtiqueta = true;
	            }

	            // Reducimos el  mbito: eliminamos el  ltimo '$NIVEL'
	            int pos = key.lastIndexOf("$");
	            if (pos == -1) {
	                terminoWhile = true; // Si no hay m s  mbitos, salimos del ciclo
	            } else {
	                key = key.substring(0, pos); // Reducimos el  mbito actual
	            }
	        }

	        // Si no se encontr  etiqueta, avanzamos al siguiente elemento (ya manejado por el while principal)
	        if (noHayEtiqueta) {
	        	GeneradorCodigoIntermedio.BaulDeGotos.remove(0); // Eliminamos el elemento para evitar ciclos infinitos
	            cargarErrorEImprimirloSemantico("No se encontr  la etiqueta llamada: " + key);
	        }	  
	}
}


private static void cargarCadenaMultilinea(String cadena){
	Tipo t = new Tipo("CADENAMULTILINEA");
	tipos.put("CADENAMULTILINEA",t);
	cargarVariables(cadena,t," Cadena multilinea ");
}

private static void modificarPolacaPM(String operador, int cantDeOp){
	GeneradorCodigoIntermedio.addOperadorEnPattMatch(operador,cantDeOp);
}

private static void opCondicion(String operador){
	GeneradorCodigoIntermedio.addElemento(operador);
	GeneradorCodigoIntermedio.bifurcarF();
};




private static void operacionesWhile(){
	int aux=0;
	while(aux<cantDeOperandos){
		completarBifurcacionF();
		aux++;
	}
	GeneradorCodigoIntermedio.bifurcarAlInicio();
}

private static void completarBifurcacionISinElse(){
	int pos = GeneradorCodigoIntermedio.getPila();
	
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos());
	
	GeneradorCodigoIntermedio.reemplazarElm(elm,pos);
	GeneradorCodigoIntermedio.addElemento("LABEL"+elm);

}

private static void operacionesIF(){
	String elm = String.valueOf(GeneradorCodigoIntermedio.getPos()+2);
	int aux=0;
	while(aux<cantDeOperandos){
		completarBifurcacionF();
		aux++;
	}
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
	if(t!=null){
		AnalizadorLexico.TablaDeSimbolos.get(id+AMBITO.toString()).setTipoParFormal(t.getType());
	}
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
		cargarErrorEImprimirloSemantico( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirloSemantico( "Linea :" +" No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else{
		if(min.contains(".") && max.contains(".")){
			double mini = Double.valueOf(min);
			double maxi = Double.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi,name+AMBITO.toString()));
		}else{
			int mini=Integer.valueOf(min);
			int maxi = Integer.valueOf(max);
			tipos.put(name,new Tipo(t.getType(),mini,maxi,name+AMBITO.toString()));
		}
	}
	return tipos.get(name);
}

private static Tipo cargarTripla(String name, Tipo t, boolean tripla){
	
	if(t.esSubTipo()){
		cargarErrorEImprimirloSemantico( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
	}else if(t.esTripla()){
		cargarErrorEImprimirloSemantico( "Linea :" + " No se puede declarar un subTipo de un tipo definido por el usuario ");
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

        // Reducimos el ambito: eliminamos el ultimo ':NIVEL'
        int pos = ambitoActual.lastIndexOf("$");
        if (pos == -1) {
            break; // Si ya no hay m s  mbitos, salimos del ciclo
        }

        // Reducimos el  mbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

   return false;
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
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String

    // Construimos la clave: id +  mbito actual
    String key = id + ambitoActual;

    // Buscamos en el mapa
    if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
        return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
    }
	return false;
}

private static void cargarVariables(String variables, Tipo tipo, String uso){
	String[] var = getVariables(variables,"/");
	for (String v : var) {
		if(tipo!=null) {
			if(tipo.getType()!="ETIQUETA"){
				if(!existeEnEsteAmbito(v)){
				if(tipo.getType()=="CADENAMULTILINEA"){
					//Si es cadena multiline y tiene '/' a a separarse en 2 la key de la cadena por el metodo "getVariables()", por lo tanto usamos directamente variables
					addTipo(variables,tipo);
					addUso(variables,uso);
					declarar(variables);
				}else if(uso=="nombre de funcion"){
					addAmbitoIDFUN(v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
				}else{
					addAmbitoID(v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
				}
				}else{
				cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +"  La variable  " + v + " ya fue declarada.");
				}
			}else{
					addAmbitoID(v);
					AnalizadorLexico.TablaDeSimbolos.get(v+AMBITO.toString()).setAmbitoVar(AMBITO.toString()+"$"+v);
					addTipo(v+AMBITO.toString(),tipo);
					addUso(v+AMBITO.toString(),uso);
					declarar(v+AMBITO.toString());
			}
		}
    }
}
private static void declarar(String id){
	AnalizadorLexico.TablaDeSimbolos.get(id).fueDeclarada();
};
private static void addUso(String id,String uso){
	AnalizadorLexico.TablaDeSimbolos.get(id).setUso(uso);
};

public static String[] getVariables(String var, String separador) {
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
	AnalizadorLexico.TablaDeSimbolos.get(id).agregarAmbitoaVar(AMBITO.toString());
}

private static void addAmbitoIDFUN(String id) {
	AnalizadorLexico.TablaDeSimbolos.get(id).agregarAmbitoaVar(AMBITO.toString());
	AnalizadorLexico.TablaDeSimbolos.get(id+AMBITO.toString()).setAmbitoVar(AMBITO.toString()+"$"+id);
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
//#line 1154 "Parser.java"
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
{cargarGotos();}
break;
case 3:
//#line 17 "Gramatica.y"
{cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el nombre del programa ");}
break;
case 4:
//#line 18 "Gramatica.y"
{cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador END ");}
break;
case 5:
//#line 19 "Gramatica.y"
{cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el delimitador BEGIN ");}
break;
case 6:
//#line 20 "Gramatica.y"
{cargarErrorEImprimirloSintactico("\u2718"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan los delimitadores del programa ");}
break;
case 7:
//#line 23 "Gramatica.y"
{if(val_peek(1).sval=="RET" || val_peek(0).sval=="RET" ){yyval.sval="RET";}}
break;
case 8:
//#line 24 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 10:
//#line 28 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  ");}
break;
case 11:
//#line 29 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";}}
break;
case 12:
//#line 30 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 16:
//#line 38 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 17:
//#line 39 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 18:
//#line 42 "Gramatica.y"
{if(!EerrorSintactico()){cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); }}
break;
case 19:
//#line 43 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 20:
//#line 46 "Gramatica.y"
{if(!EerrorSintactico()){ 
		if(tipos.containsKey(val_peek(0).sval)){
			yyval.obj = tipos.get(val_peek(0).sval);
		}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea 
				+" Se utilizo un tipo desconocido ");};}}
break;
case 21:
//#line 51 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 22:
//#line 54 "Gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 23:
//#line 56 "Gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 24:
//#line 58 "Gramatica.y"
{ if(!tipos.containsKey("OCTAL")){tipos.put("OCTAL",new Tipo("OCTAL"));}
							yyval.obj = tipos.get("OCTAL");}
break;
case 25:
//#line 62 "Gramatica.y"
{if(!EerrorSintactico()){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
break;
case 26:
//#line 63 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango ");}
break;
case 27:
//#line 64 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango ");}
break;
case 28:
//#line 65 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango ");}
break;
case 29:
//#line 66 "Gramatica.y"
{if(val_peek(2).obj != null){if(!EerrorSintactico()){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}}
break;
case 30:
//#line 67 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior ");}
break;
case 31:
//#line 68 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos ");}
break;
case 32:
//#line 69 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior ");}
break;
case 33:
//#line 70 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos ");}
break;
case 34:
//#line 71 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido ");}
break;
case 35:
//#line 72 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo ");}
break;
case 36:
//#line 73 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE ");}
break;
case 37:
//#line 74 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE");}
break;
case 38:
//#line 75 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE");}
break;
case 39:
//#line 76 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE");}
break;
case 40:
//#line 77 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion");}
break;
case 41:
//#line 81 "Gramatica.y"
{if(!EerrorSintactico())
								{	if(val_peek(1).sval!="RET"){cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion ");}
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal(val_peek(4).sval,(Tipo)val_peek(3).obj);									
								}}
break;
case 42:
//#line 89 "Gramatica.y"
{if(!EerrorSintactico()){yyval.sval=val_peek(0).sval;cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj,"nombre de funcion");agregarAmbito(val_peek(0).sval);DENTRODELAMBITO.push(val_peek(0).sval);GeneradorCodigoIntermedio.addNuevaPolaca();}}
break;
case 43:
//#line 90 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion ");}
break;
case 44:
//#line 93 "Gramatica.y"
{if(!EerrorSintactico()){yyval.obj=val_peek(2).obj; GeneradorCodigoIntermedio.addElemento(val_peek(1).sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}}
break;
case 45:
//#line 95 "Gramatica.y"
{if(!EerrorSintactico())
										{if(tipos.containsKey(val_peek(2).sval))
										{yyval.obj = tipos.get(val_peek(2).sval); GeneradorCodigoIntermedio.addElemento(val_peek(1).sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables(val_peek(1).sval,tipos.get(val_peek(2).sval)," nombre de parametro real ");
										}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}}
break;
case 46:
//#line 99 "Gramatica.y"
{yyval.obj=val_peek(1).obj;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion ");}
break;
case 47:
//#line 100 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion ");}
break;
case 48:
//#line 101 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion ");}
break;
case 49:
//#line 102 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). ");}
break;
case 50:
//#line 105 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 51:
//#line 109 "Gramatica.y"
{if(!EerrorSintactico())
										{if(!existeFuncion()){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
										}else{
											GeneradorCodigoIntermedio.addElemento("RET");
										}}}
break;
case 52:
//#line 114 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  ");	}
break;
case 54:
//#line 119 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 57:
//#line 122 "Gramatica.y"
{if(!EerrorSintactico()){if(fueDeclarado(val_peek(0).sval+AMBITO.toString())){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA "+val_peek(0).sval+" ya existe  ");}else{cargarVariables(val_peek(0).sval,tipos.get("ETIQUETA"),"ETIQUETA");}GeneradorCodigoIntermedio.addEtiqueta(val_peek(0).sval+AMBITO.toString());GeneradorCodigoIntermedio.addElemento("LABEL"+val_peek(0).sval+AMBITO.toString());}}
break;
case 59:
//#line 124 "Gramatica.y"
{yyval.sval="RET";}
break;
case 60:
//#line 127 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("OUTF");}}
break;
case 61:
//#line 128 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
break;
case 62:
//#line 129 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("OUTF");}}
break;
case 63:
//#line 130 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. ");}
break;
case 64:
//#line 131 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Cadena mal escrita en sentencia OUTF. ");}
break;
case 65:
//#line 135 "Gramatica.y"
{if(!EerrorSintactico())
															{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;
															GeneradorCodigoIntermedio.addElemento(val_peek(2).sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + val_peek(2).sval + "' no fue declarada");}
															}}
break;
case 66:
//#line 144 "Gramatica.y"
{if(!EerrorSintactico())
																	{if(fueDeclarado(val_peek(5).sval)){
																		if(Integer.valueOf(val_peek(3).sval) <= 3){
																			yyval.sval = val_peek(5).sval;
																			GeneradorCodigoIntermedio.addElemento(val_peek(5).sval+Parser.AMBITO.toString());
																			GeneradorCodigoIntermedio.addElemento(val_peek(3).sval);
																			GeneradorCodigoIntermedio.addElemento("INDEX");
																			GeneradorCodigoIntermedio.addElemento(":="); 
																			}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");}														
																	}else{
																		cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + val_peek(5).sval + "' no fue declarada");}}}
break;
case 67:
//#line 155 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 68:
//#line 156 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el operando izquierdo de la asignacion. ");}
break;
case 69:
//#line 160 "Gramatica.y"
{if(!EerrorSintactico())
												{if(!fueDeclarado(val_peek(3).sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + val_peek(3).sval + "' no fue declarada");}
													else{	
														GeneradorCodigoIntermedio.invocar(val_peek(3).sval+AMBITO.toString());																																																		
												}}}
break;
case 70:
//#line 167 "Gramatica.y"
{if(!EerrorSintactico())
												{if(!fueDeclarado(val_peek(6).sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + val_peek(6).sval + "' no fue declarada");}
													else{
														GeneradorCodigoIntermedio.invocar(val_peek(6).sval+AMBITO.toString(), ((Tipo)val_peek(4).obj).getType());
												}}}
break;
case 71:
//#line 173 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
break;
case 72:
//#line 174 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
break;
case 73:
//#line 178 "Gramatica.y"
{yyval.ival=val_peek(2).ival + 1;if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento(",");}}
break;
case 74:
//#line 179 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 75:
//#line 180 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 76:
//#line 181 "Gramatica.y"
{yyval.ival=1;if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento(",");}}
break;
case 77:
//#line 184 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("+"); }}
break;
case 78:
//#line 185 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("-"); }}
break;
case 80:
//#line 187 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 81:
//#line 188 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 82:
//#line 189 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 83:
//#line 190 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 84:
//#line 191 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
break;
case 85:
//#line 194 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("*");} }
break;
case 86:
//#line 195 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento("/");}}
break;
case 88:
//#line 197 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 89:
//#line 198 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 90:
//#line 199 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 91:
//#line 200 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 92:
//#line 203 "Gramatica.y"
{if(!EerrorSintactico()){if(fueDeclarado(val_peek(0).sval)){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+val_peek(0).sval+ "' no fue declarada");};}}
break;
case 93:
//#line 204 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}}
break;
case 95:
//#line 207 "Gramatica.y"
{if(!EerrorSintactico())
											{if(fueDeclarado(val_peek(3).sval)){ 
											if(Integer.valueOf(val_peek(1).sval) <= 3){
												GeneradorCodigoIntermedio.addElemento(val_peek(3).sval+Parser.AMBITO.toString());
												GeneradorCodigoIntermedio.addElemento(val_peek(1).sval);
												GeneradorCodigoIntermedio.addElemento("INDEX");
												AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval).incrementarContDeRef(); yyval.sval = val_peek(3).sval;
											}else{
												cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");
											}
										}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+val_peek(3).sval+ "' no fue declarada");};}}
break;
case 96:
//#line 218 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 97:
//#line 220 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 98:
//#line 221 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
break;
case 99:
//#line 222 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 102:
//#line 232 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 103:
//#line 233 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 104:
//#line 236 "Gramatica.y"
{if(val_peek(4).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};if(!EerrorSintactico()){completarBifurcacionI();}}
break;
case 105:
//#line 237 "Gramatica.y"
{if(!EerrorSintactico()){completarBifurcacionISinElse();}}
break;
case 106:
//#line 238 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
break;
case 107:
//#line 239 "Gramatica.y"
{{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}
break;
case 108:
//#line 242 "Gramatica.y"
{if(val_peek(3).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
break;
case 109:
//#line 243 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
break;
case 110:
//#line 244 "Gramatica.y"
{if(val_peek(3).sval=="RET" && val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
break;
case 111:
//#line 245 "Gramatica.y"
{if(val_peek(2).sval=="RET" && val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
break;
case 112:
//#line 247 "Gramatica.y"
{yyval.sval=val_peek(2).sval;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
break;
case 113:
//#line 248 "Gramatica.y"
{if(val_peek(4).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
break;
case 114:
//#line 251 "Gramatica.y"
{if(val_peek(6).ival == val_peek(2).ival){if(!EerrorSintactico()){cantDeOperandos=val_peek(6).ival;modificarPolacaPM(val_peek(4).sval,val_peek(6).ival);}}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
break;
case 115:
//#line 252 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 116:
//#line 253 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 117:
//#line 254 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 118:
//#line 255 "Gramatica.y"
{cantDeOperandos=1;if(!EerrorSintactico()){opCondicion(val_peek(2).sval);}}
break;
case 119:
//#line 256 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 120:
//#line 257 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 121:
//#line 258 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 122:
//#line 260 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
break;
case 123:
//#line 262 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
break;
case 124:
//#line 263 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
break;
case 125:
//#line 264 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
break;
case 126:
//#line 267 "Gramatica.y"
{yyval.sval=">";}
break;
case 127:
//#line 268 "Gramatica.y"
{yyval.sval=">=";}
break;
case 128:
//#line 269 "Gramatica.y"
{yyval.sval="<";}
break;
case 129:
//#line 270 "Gramatica.y"
{yyval.sval="<=";}
break;
case 130:
//#line 271 "Gramatica.y"
{yyval.sval="=";}
break;
case 131:
//#line 272 "Gramatica.y"
{yyval.sval="!=";}
break;
case 132:
//#line 275 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 133:
//#line 276 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 134:
//#line 279 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 135:
//#line 280 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 136:
//#line 281 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 137:
//#line 282 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del ELSE");}
break;
case 138:
//#line 286 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 139:
//#line 289 "Gramatica.y"
{if(!EerrorSintactico()){if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}}
break;
case 140:
//#line 290 "Gramatica.y"
{if(!EerrorSintactico()){if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}}
break;
case 141:
//#line 293 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 142:
//#line 294 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 143:
//#line 295 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 144:
//#line 296 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del THEN");}
break;
case 145:
//#line 299 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 146:
//#line 303 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 147:
//#line 304 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 148:
//#line 308 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 149:
//#line 309 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 150:
//#line 310 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 151:
//#line 311 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias");}
break;
case 152:
//#line 314 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 153:
//#line 317 "Gramatica.y"
{if(val_peek(2).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 154:
//#line 318 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 155:
//#line 319 "Gramatica.y"
{if(val_peek(1).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 156:
//#line 322 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 157:
//#line 325 "Gramatica.y"
{if(!EerrorSintactico()){cargarCadenaMultilinea(val_peek(0).sval);GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}}
break;
case 158:
//#line 330 "Gramatica.y"
{if(!EerrorSintactico()){operacionesWhile();}}
break;
case 159:
//#line 331 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
break;
case 160:
//#line 334 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}}
break;
case 161:
//#line 338 "Gramatica.y"
{if(!EerrorSintactico()){GeneradorCodigoIntermedio.addBaulDeGotos(val_peek(0).sval+AMBITO.toString()
									+"/"+AMBITO.toString()
									+"/"+String.valueOf(GeneradorCodigoIntermedio.getPos()));}}
break;
case 162:
//#line 341 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea 
									+ " Error: Falta el caracter '@' de la etiqueta. ");}
break;
case 163:
//#line 343 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea
									 + " Error: Falta la etiqueta en GOTO ");}
break;
//#line 1959 "Parser.java"
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
