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
    0,  134,  135,    0,  149,   60,    0,    0,    0,    0,
    0,    0,   20,    0,    0,    0,    0,   51,    0,   42,
   19,   18,    0,   95,    0,   47,    0,    0,    0,    0,
    0,    0,  151,    0,  148,  144,  150,  139,  140,    1,
   78,   77,   86,   85,    0,    0,    0,    0,    0,    0,
    0,   68,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   82,   88,   83,   87,    0,  103,  138,    0,  106,
    0,    0,  129,  130,   62,   59,   61,    0,    0,    0,
   39,    0,    0,    0,    0,   50,   94,   48,   46,   45,
    0,    0,    0,    0,    0,  142,    0,  146,    0,    0,
    0,    0,    0,   69,    0,   66,  116,   92,    0,    0,
    0,  133,  109,    0,  102,    0,  108,    0,    0,    0,
    0,   38,   37,    0,   36,    0,    0,    0,   44,   41,
    0,    0,  143,    0,  147,    0,    0,    0,  115,    0,
    0,   93,  137,    0,    0,  104,  107,    0,  105,    0,
    0,   40,   29,    0,    0,    0,   33,    0,    0,    0,
    0,  141,  145,    0,    0,    0,    0,    0,    0,  136,
  132,    0,  110,  101,    0,    0,    0,    0,    0,   31,
    0,    0,    0,    0,    0,    0,    0,   67,  131,   34,
   35,   30,   32,    0,   26,  120,    0,  122,  121,  112,
   25,  111,
};
final static short yydgoto[] = {                          3,
   42,   17,   18,   19,  135,   21,   22,   23,   24,   65,
   25,   43,   26,   68,  193,   27,  146,   28,   29,   30,
   31,   32,  110,   45,   46,   84,   47,   48,   49,  101,
  172,   95,  173,  174,  197,  198,  102,  103,  137,  138,
  139,   34,
};
final static short yysindex[] = {                      -184,
 1004,    0,    0,  905,  -43,  567,   27,  558,   38,    0,
 -220,    0,    0,    0,    0,    0,  924,    0,    0,   26,
    0,   42,   46, -171,    0,   71,    0,    0,    0,    0,
    0,    0, -114,  567,  943,  962,    0,  736,    0,  592,
 -146,  100,    0,  839,   48,    0,  -28,    0,  -69,  598,
  -53, -159, -159,  -81,  384,    0,    0,    0,    0,    0,
    0,    0,  -61,    0,  465,    0,  526,  -52,  227,  -25,
 1120,    0,  985,    0,   32, -190,  -38,  -22,  -20,  -28,
  227,  638,  839,   66,    0,  550,    0,    0,    0,    0,
    0,  382,  681,    0,  227,   41,  146,  684,   -5, 1134,
  278,    0,    0,  877,    0,    0,  100,  399,   94,  176,
  -99, -159,    0,  -62,  130,  193, -105,    0,  113,    0,
    0,    0,   29,    0,  254,    0,  281,  -36, 1004,  139,
  199,   84,    0,  826,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  139,  139,  637,  227,  227,  703,
  768,    0,  309,  116,   32,  -28,   32,  -28,  121,  228,
   95,    0,    0,    0,    0, -216,    0,    0, 1162,    0,
 -126,  -49,    0,    0,    0,    0,    0,   59,  -35,   29,
    0,   68,   29,   68,  -11,    0,    0,    0,    0,    0,
  332, 1004,  114,   98,  262,    0,  757,    0,  569,  359,
  142,  139,  361,    0,  227,    0,    0,    0,  280,  771,
 -216,    0,    0, 1148,    0,  -21,    0,  141,  306,  188,
 -217,    0,    0,  409,    0,  413,  -23,  425,    0,    0,
  227,  197,    0, 1170,    0,  638,  717,  638,    0,  638,
  147,    0,    0, 1184,  791,    0,    0, -230,    0,   98,
  353,    0,    0,   68,   68,   68,    0,  -19,   68,  139,
  227,    0,    0,   87,  638,  104,  156,  158,  443,    0,
    0, 1192,    0,    0,  197,  366,  368,  375,   13,    0,
  380,  139,  466,  444,  470,  473,  475,    0,    0,    0,
    0,    0,    0,  394,    0,    0,  479,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -42,    0,    0,    0,  266,
    0,  293,  347,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  525,    0,    0,    0,    0,
    0,    1,    0,    0,   28,    0,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  486,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  528,    0,    0,    0,    0,    0,    0,   82,
    0,    0,  448,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -30,    0,    0,    0,
  -39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  726,  836,    0,    0,  848, 1030,
    0,    0,    0,    0,  108,  134,  160,  186, 1048,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  270,    0,    0,    0,    0,    0,    0,    0,    0,
 1066,  882,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  240,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  169,    0,    0,    0,    0,    0,
  439,  320,    0,    0,    0,    0, 1084,    0,    0,    0,
    0,    0,    0,  474,    0,    0, 1102,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  724,   45,   47,    0, 1045,    0,    0,    0,  606,    0,
  -27,  823,    0,    0,    0,    0,  620,    0,    0,    0,
    0,    0,    0,  749,    0,  832,   -1,   49,  501,    0,
  370,  -18,    0,    0, -143,  891,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=1465;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        180,
   97,   89,   89,   89,  190,   89,  112,   89,   70,  218,
   97,   97,   97,   99,   97,   37,   97,  184,   98,  132,
  256,   41,  210,  178,  279,  273,  221,   89,   92,   92,
   92,  274,   92,   41,   92,   56,   80,  248,  252,  128,
    6,   97,   97,   97,   97,   97,    7,   97,   36,    9,
    2,    2,   57,   10,   76,   11,   12,   41,  153,   97,
   97,   97,   97,   59,  148,  141,   50,  245,   89,   89,
   89,   89,   89,   79,   89,    1,   85,   55,   78,   73,
   97,   81,   59,    2,   60,  161,   89,   89,   89,   89,
  156,  158,   97,   63,  108,   76,    2,   76,   76,   76,
   61,  257,   80,  220,   62,  280,  150,   80,    2,  149,
   67,  227,   41,   76,   76,   76,   76,   13,   14,   59,
   85,   15,   81,   97,   81,   81,   81,  283,  200,  213,
  149,  203,  214,   75,  176,  215,   93,  293,   92,   86,
   81,   81,   81,   81,  285,  162,  164,  149,   80,   80,
   80,   80,   80,  186,   59,   93,  206,   92,   93,   79,
   92,  207,    2,   93,   69,   92,   80,   80,   80,   80,
   96,   13,   14,  192,   75,   15,   75,   75,   75,   69,
  237,   93,  239,   92,   93,   74,   92,  269,  100,   93,
   41,   92,   75,   75,   75,   75,  286,  117,  287,  149,
   79,  149,   79,   79,   79,    2,  120,  129,   63,   93,
   93,   93,  217,   93,    2,   93,  177,  142,   79,   79,
   79,   79,   20,   13,   14,   20,   74,   15,   74,   74,
   74,    2,    2,  143,   20,  144,   97,   20,   59,   64,
  247,  131,   89,   39,   74,   74,   74,   74,   97,   63,
  165,   97,  182,   97,  183,   39,   97,   97,   97,   97,
   97,   97,   97,   97,   97,   12,   97,   63,   97,   92,
   97,   41,   97,   97,   97,   97,   97,   97,   97,   39,
   64,   97,   97,   89,   89,   89,   89,   89,   89,   89,
   89,   89,   16,   89,  188,   89,    2,   89,   64,   89,
   89,   89,   89,   89,   89,   89,   12,  160,   89,   89,
   76,   76,   76,   76,   76,   76,   76,   76,   76,   65,
   76,  189,   76,  194,   76,  219,   76,   76,   76,   76,
   76,   76,   76,   16,   39,   76,  171,   81,   81,   81,
   81,   81,   81,   81,   81,   81,   17,   81,  205,   81,
  195,   81,  208,   81,   81,   81,   81,   81,   81,   81,
   65,  209,   81,   80,   80,   80,   80,   80,   80,   80,
   80,   80,  229,   80,  230,   80,  231,   80,   65,   80,
   80,   80,   80,   80,   80,   80,  232,   17,   80,   75,
   75,   75,   75,   75,   75,   75,   75,   75,  238,   75,
  240,   75,  249,   75,  242,   75,   75,   75,   75,   75,
   75,   75,   39,    2,   75,   79,   79,   79,   79,   79,
   79,   79,   79,   79,  118,   79,   41,   79,   41,   79,
  250,   79,   79,   79,   79,   79,   79,   79,   28,  175,
   79,   74,   74,   74,   74,   74,   74,   74,   74,   74,
   93,   74,  254,   74,  251,   74,  255,   74,   74,   74,
   74,   74,   74,   74,   63,   63,   74,   63,  259,   63,
   63,   63,   63,   27,   63,  261,   63,  275,   63,   28,
   63,   63,   38,  288,  297,   63,   63,  149,   73,   63,
  290,   73,  291,   39,    2,   64,   64,   28,   64,  292,
   64,   64,   64,   64,  295,   64,  296,   64,  123,   64,
  298,   64,   64,  299,   27,  300,   64,   64,  301,  302,
   64,   12,   12,  122,    6,   43,   12,    4,   12,   12,
   49,   12,   27,   12,   71,   12,  169,   12,   12,  170,
  216,    0,   12,   12,    0,    0,   12,    0,   16,   16,
    0,    0,    0,   16,    0,   16,   16,    0,   16,    0,
   16,    0,   16,    0,   16,   16,  126,    0,    0,   16,
   16,    0,    0,   16,    0,   65,   65,    0,   65,    0,
   65,   65,   65,   65,    0,   65,    0,   65,    0,   65,
  152,   65,   65,    0,   41,    0,   65,   65,    0,    0,
   65,    0,   17,   17,    0,    0,   40,   17,  236,   17,
   17,   41,   17,    0,   17,    0,   17,   53,   17,   17,
    0,    0,    0,   17,   17,   44,    0,   17,   90,   94,
   91,   82,    0,    0,    0,   81,   41,  155,  106,   38,
    0,    0,   41,    0,    0,    0,    0,    0,   39,    2,
   39,    2,    0,   44,    5,    6,  114,  115,  116,   83,
    0,    7,    8,    0,    9,    0,    2,    0,   10,  109,
   11,   12,    0,    0,  119,   13,   14,  199,    0,   15,
  149,   81,   41,    0,    0,    0,    0,    0,  130,    0,
    0,    0,    0,    0,   28,   28,   90,   94,   91,   28,
  145,   28,   28,    0,   28,  154,   28,    0,   28,    0,
   28,   28,    0,    0,  159,   28,   28,  179,    0,   28,
  121,    0,  185,    4,   16,   41,    0,   16,   41,   27,
   27,   54,    2,    0,   27,    0,   27,   27,    0,   27,
   16,   27,    0,   27,    0,   27,   27,   64,    0,   33,
   27,   27,   33,    0,   27,    0,  265,    0,   16,   16,
   81,   41,   90,   94,   91,   33,   71,  201,  202,   71,
    0,    0,   66,  107,  113,  113,  113,   79,   77,    0,
   76,  125,   78,   33,   33,   71,   71,   71,   64,    0,
  127,    0,    0,    2,   64,    0,   16,    0,  111,    0,
    0,    0,   13,   14,    0,  151,   15,    0,  204,   79,
   77,    0,   76,  124,   78,  234,   39,    2,    0,   33,
    0,   33,   38,   64,  241,    2,   13,   14,   51,  244,
   15,   16,    0,   39,    2,  113,   52,  181,    0,    0,
  113,    0,   87,   88,   89,    0,   64,   38,   33,  272,
  260,  191,   16,  104,    6,    0,   33,   64,   39,    2,
    7,    8,    0,    9,   39,    2,  105,   10,    0,   11,
   12,  187,    0,    0,   13,   14,   73,   33,   15,   73,
  282,   93,   33,   92,    0,    0,    0,    0,   72,   64,
    0,   72,   64,   38,    0,   73,   73,   73,   90,   94,
   91,    0,  222,  223,   39,    2,  225,   72,   72,   72,
   87,   88,   89,  147,   33,   16,    0,   33,   79,   77,
   64,   76,   70,   78,    0,   70,    0,    0,    0,    0,
    0,    0,    0,   64,   64,   37,  157,   64,    0,  163,
   33,   70,   70,   70,  253,   33,    0,   39,    2,    0,
   39,    2,    0,    0,    0,    0,    0,   64,   33,   33,
    0,  136,   33,    0,    0,    0,    0,   64,   64,    0,
    0,    0,   38,    0,    0,    0,   87,   88,   89,    0,
    0,    0,   33,   39,    2,    0,    0,    0,    0,    0,
  168,   75,   33,   33,    0,   64,    0,    0,    0,   71,
   71,   71,   39,    2,  224,    0,  226,  228,    0,    0,
    0,    0,    0,    6,    0,    0,    0,  233,    0,    7,
   33,    0,    9,   75,    2,    0,   10,    6,   11,   12,
    0,  243,    0,    7,   39,    2,    9,    0,    2,    0,
   10,    0,   11,   12,    0,   20,    0,    6,   20,  258,
    0,  271,    0,    7,    0,    0,    9,    0,    2,  212,
   10,   20,   11,   12,    0,    0,    0,  264,  266,  267,
    0,  268,    0,    0,    0,    0,  276,  277,  278,   20,
   20,  281,    6,    0,    0,    0,  196,  235,    7,    0,
    0,    9,    0,    2,   20,   10,  284,   11,   12,    0,
  235,  294,    0,    0,  212,    0,    0,    0,    0,   73,
   73,   73,   87,   88,   89,    0,    0,   20,    0,    0,
    0,   72,   72,   72,  263,    0,    0,    0,    0,    0,
    0,    0,   75,    0,  263,  235,    0,    0,    0,    0,
    0,    0,    0,   39,    2,    0,    0,    0,    0,    0,
    0,    0,   20,    0,    0,   70,   70,   70,    0,    0,
    5,    6,  263,    0,   35,    0,    0,    7,    8,    0,
    9,    0,    2,   20,   10,    0,   11,   12,    0,    5,
    6,   13,   14,    0,   58,   15,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    0,    5,    6,
   13,   14,    0,   72,   15,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    5,    6,   13,
   14,    0,   74,   15,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,   20,    0,   13,   14,
    5,    6,   15,    0,    0,  140,    0,    7,    8,    0,
    9,    0,    2,    0,   10,    0,   11,   12,    0,    5,
    6,   13,   14,    0,    0,   15,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    0,    0,    0,
   13,   14,    0,    0,   15,  119,  119,  119,    0,  119,
    0,    0,  119,    0,    0,  119,    0,  119,    0,  119,
    0,  119,  119,  118,  118,  118,    0,  118,    0,    0,
  118,    0,    0,  118,    0,  118,    0,  118,    0,  118,
  118,  117,  117,  117,    0,  117,    0,    0,  117,    0,
    0,  117,    0,  117,    0,  117,    0,  117,  117,  114,
  114,  114,    0,  114,    0,    0,  114,    0,    0,  114,
    0,  114,    0,  114,    0,  114,  114,  113,  113,  113,
    0,  113,    0,    0,  113,    0,    0,  113,    0,  113,
    0,  113,    0,  113,  113,  133,    6,    0,    0,  134,
    0,    0,    7,    0,    0,    9,    0,    2,    0,   10,
    6,   11,   12,  166,    0,  167,    7,    0,    0,    9,
    0,    2,    0,   10,    6,   11,   12,  211,    0,  246,
    7,    0,    0,    9,    0,    2,    0,   10,    6,   11,
   12,  211,    0,    0,    7,    0,    6,    9,    0,    2,
  262,   10,    7,   11,   12,    9,    0,    2,    0,   10,
    6,   11,   12,    0,  270,    0,    7,    0,    6,    9,
    0,    2,  289,   10,    7,   11,   12,    9,    0,    2,
    0,   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         62,
    0,   41,   42,   43,   41,   45,   60,   47,  123,   59,
   41,   42,   43,   42,   45,   59,   47,  123,   47,   45,
   44,   45,  166,  123,   44,  256,   62,    0,   41,   42,
   43,  262,   45,   45,   47,  256,   38,   59,  256,   67,
  257,   41,   42,   43,   44,   45,  263,   47,    4,  266,
  268,  268,  273,  270,    0,  272,  273,   45,   86,   59,
   60,   61,   62,   17,   83,  256,   40,  211,   41,   42,
   43,   44,   45,   42,   47,  260,  267,   40,   47,   35,
  123,    0,   36,  268,   59,   45,   59,   60,   61,   62,
   92,   93,  123,  265,   50,   41,  268,   43,   44,   45,
   59,  125,  104,   45,   59,  125,   41,    0,  268,   44,
   40,  123,   45,   59,   60,   61,   62,  277,  278,   73,
  267,  281,   41,  123,   43,   44,   45,   41,  147,  256,
   44,  150,  259,    0,   41,  262,   43,  125,   45,   40,
   59,   60,   61,   62,   41,   97,   98,   44,   41,  151,
   43,   44,   45,   41,  108,   43,   41,   45,   43,    0,
   45,   41,  268,   43,  279,   45,   59,   60,   61,   62,
  123,  277,  278,  129,   41,  281,   43,   44,   45,  279,
  199,   43,   41,   45,   43,    0,   45,   41,  258,   43,
   45,   45,   59,   60,   61,   62,   41,  279,   41,   44,
   41,   44,   43,   44,   45,  268,  268,  260,    0,   41,
   42,   43,  262,   45,  268,   47,   41,  256,   59,   60,
   61,   62,  265,  277,  278,  268,   41,  281,   43,   44,
   45,  268,  268,  256,  265,  256,  279,  268,  192,    0,
  262,  267,  282,  267,   59,   60,   61,   62,  279,   41,
  256,  282,  123,  282,   62,  267,  256,  257,  258,  259,
  260,  261,  262,  263,  264,    0,  266,   59,  268,  282,
  270,   45,  272,  273,  274,  275,  276,  277,  278,  267,
   41,  281,  282,  256,  257,  258,  259,  260,  261,  262,
  263,  264,    0,  266,   41,  268,  268,  270,   59,  272,
  273,  274,  275,  276,  277,  278,   41,  267,  281,  282,
  256,  257,  258,  259,  260,  261,  262,  263,  264,    0,
  266,   41,  268,  125,  270,  267,  272,  273,  274,  275,
  276,  277,  278,   41,  267,  281,   59,  256,  257,  258,
  259,  260,  261,  262,  263,  264,    0,  266,   40,  268,
  267,  270,  125,  272,  273,  274,  275,  276,  277,  278,
   41,  267,  281,  256,  257,  258,  259,  260,  261,  262,
  263,  264,   41,  266,  261,  268,  279,  270,   59,  272,
  273,  274,  275,  276,  277,  278,  125,   41,  281,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   40,  266,
   40,  268,  262,  270,  125,  272,  273,  274,  275,  276,
  277,  278,  267,  268,  281,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   45,  268,   45,  270,
  125,  272,  273,  274,  275,  276,  277,  278,    0,   41,
  281,  256,  257,  258,  259,  260,  261,  262,  263,  264,
  282,  266,   44,  268,  267,  270,   44,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  281,  259,   44,  261,
  262,  263,  264,    0,  266,  279,  268,  125,  270,   41,
  272,  273,  256,   41,   41,  277,  278,   44,   41,  281,
  125,   44,  125,  267,  268,  256,  257,   59,  259,  125,
  261,  262,  263,  264,  125,  266,   41,  268,   44,  270,
   41,  272,  273,   41,   41,   41,  277,  278,  125,   41,
  281,  256,  257,   59,    0,   40,  261,    0,  263,  264,
  261,  266,   59,  268,   34,  270,  259,  272,  273,  262,
  171,   -1,  277,  278,   -1,   -1,  281,   -1,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   41,   -1,   -1,  277,
  278,   -1,   -1,  281,   -1,  256,  257,   -1,  259,   -1,
  261,  262,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   41,  272,  273,   -1,   45,   -1,  277,  278,   -1,   -1,
  281,   -1,  256,  257,   -1,   -1,   40,  261,   40,  263,
  264,   45,  266,   -1,  268,   -1,  270,   60,  272,  273,
   -1,   -1,   -1,  277,  278,    6,   -1,  281,   60,   61,
   62,   40,   -1,   -1,   -1,   44,   45,  256,   41,  256,
   -1,   -1,   45,   -1,   -1,   -1,   -1,   -1,  267,  268,
  267,  268,   -1,   34,  256,  257,   51,   52,   53,   40,
   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,   50,
  272,  273,   -1,   -1,   55,  277,  278,   41,   -1,  281,
   44,   44,   45,   -1,   -1,   -1,   -1,   -1,   69,   -1,
   -1,   -1,   -1,   -1,  256,  257,   60,   61,   62,  261,
   81,  263,  264,   -1,  266,   86,  268,   -1,  270,   -1,
  272,  273,   -1,   -1,   95,  277,  278,  112,   -1,  281,
  256,   -1,  117,    0,    1,   45,   -1,    4,   45,  256,
  257,    8,  268,   -1,  261,   -1,  263,  264,   -1,  266,
   17,  268,   -1,  270,   -1,  272,  273,   24,   -1,    1,
  277,  278,    4,   -1,  281,   -1,   40,   -1,   35,   36,
   44,   45,   60,   61,   62,   17,   41,  148,  149,   44,
   -1,   -1,   24,   50,   51,   52,   53,   42,   43,   -1,
   45,  256,   47,   35,   36,   60,   61,   62,   65,   -1,
   67,   -1,   -1,  268,   71,   -1,   73,   -1,   50,   -1,
   -1,   -1,  277,  278,   -1,  256,  281,   -1,   41,   42,
   43,   -1,   45,   65,   47,   59,  267,  268,   -1,   71,
   -1,   73,  256,  100,  205,  268,  277,  278,  271,   59,
  281,  108,   -1,  267,  268,  112,  279,  114,   -1,   -1,
  117,   -1,  274,  275,  276,   -1,  123,  256,  100,   59,
  231,  128,  129,  256,  257,   -1,  108,  134,  267,  268,
  263,  264,   -1,  266,  267,  268,  269,  270,   -1,  272,
  273,  123,   -1,   -1,  277,  278,   41,  129,  281,   44,
  261,   43,  134,   45,   -1,   -1,   -1,   -1,   41,  166,
   -1,   44,  169,  256,   -1,   60,   61,   62,   60,   61,
   62,   -1,  179,  180,  267,  268,  183,   60,   61,   62,
  274,  275,  276,   82,  166,  192,   -1,  169,   42,   43,
  197,   45,   41,   47,   -1,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  210,  211,   59,  256,  214,   -1,  256,
  192,   60,   61,   62,  221,  197,   -1,  267,  268,   -1,
  267,  268,   -1,   -1,   -1,   -1,   -1,  234,  210,  211,
   -1,   71,  214,   -1,   -1,   -1,   -1,  244,  245,   -1,
   -1,   -1,  256,   -1,   -1,   -1,  274,  275,  276,   -1,
   -1,   -1,  234,  267,  268,   -1,   -1,   -1,   -1,   -1,
  100,  256,  244,  245,   -1,  272,   -1,   -1,   -1,  274,
  275,  276,  267,  268,  182,   -1,  184,  185,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,  263,
  272,   -1,  266,  256,  268,   -1,  270,  257,  272,  273,
   -1,  261,   -1,  263,  267,  268,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,    1,   -1,  257,    4,  227,
   -1,  261,   -1,  263,   -1,   -1,  266,   -1,  268,  169,
  270,   17,  272,  273,   -1,   -1,   -1,  236,  237,  238,
   -1,  240,   -1,   -1,   -1,   -1,  254,  255,  256,   35,
   36,  259,  257,   -1,   -1,   -1,  261,  197,  263,   -1,
   -1,  266,   -1,  268,   50,  270,  265,  272,  273,   -1,
  210,  279,   -1,   -1,  214,   -1,   -1,   -1,   -1,  274,
  275,  276,  274,  275,  276,   -1,   -1,   73,   -1,   -1,
   -1,  274,  275,  276,  234,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,   -1,  244,  245,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  267,  268,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  108,   -1,   -1,  274,  275,  276,   -1,   -1,
  256,  257,  272,   -1,  260,   -1,   -1,  263,  264,   -1,
  266,   -1,  268,  129,  270,   -1,  272,  273,   -1,  256,
  257,  277,  278,   -1,  261,  281,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,
  277,  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,  192,   -1,  277,  278,
  256,  257,  281,   -1,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,
  257,  277,  278,   -1,   -1,  281,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  256,  257,  258,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,  258,
   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  257,  272,  273,  260,   -1,  262,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,  262,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,  272,
  273,  260,   -1,   -1,  263,   -1,  257,  266,   -1,  268,
  261,  270,  263,  272,  273,  266,   -1,  268,   -1,  270,
  257,  272,  273,   -1,  261,   -1,  263,   -1,  257,  266,
   -1,  268,  261,  270,  263,  272,  273,  266,   -1,  268,
   -1,  270,   -1,  272,  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=282;
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
"ASIGNACION","ERROR","OCTAL","\"\"",
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
"termino : termino \"\" factor",
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
		double mini = Double.valueOf(min);
		double maxi = Double.valueOf(max);
		tipos.put(name,new Tipo(t.getType(),mini,maxi));
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
//#line 1059 "Parser.java"
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
{GeneradorCodigoIntermedio.addElemento("");}
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
//#line 1823 "Parser.java"
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
