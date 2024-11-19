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
   19,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   32,   32,   32,   32,   32,   32,   31,
   31,   34,   34,   33,   30,   30,   38,   38,   38,   37,
   35,   35,   35,   36,   23,   20,   20,   39,   21,   21,
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
    8,    6,    6,    4,    7,    7,    5,    7,    6,    6,
    8,    9,    8,    8,    7,    5,    4,    4,    3,    3,
    8,    8,    8,    1,    1,    1,    1,    1,    1,    1,
    1,    5,    4,    2,    1,    1,    4,    2,    3,    1,
    3,    1,    2,    1,    1,    3,    3,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   98,    0,    0,    0,    0,    0,    0,    0,  148,
    0,   56,   22,   23,   24,    0,    0,    8,    9,    0,
   13,    0,    0,    0,   21,    0,   58,   52,   53,   54,
   55,   57,    0,    0,    0,    0,   10,    0,   99,    0,
    0,    0,   90,    0,    0,   91,    0,   84,    0,    0,
    0,    0,    0,    0,    0,  150,  149,    3,    7,   11,
   14,   15,    0,   97,    0,   96,    0,    0,    0,    0,
    0,    2,    0,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  100,    0,  125,  127,  129,  126,
  124,    0,    0,  128,    0,    0,    0,    0,    0,    0,
  145,   60,    0,    0,    0,    0,    0,    0,   20,    0,
    0,    0,    0,   51,    0,   42,   19,   18,    0,   95,
    0,   47,    0,    0,    0,    0,    0,    0,  147,    0,
  144,  146,  140,  135,  136,    1,   78,   77,   85,   86,
    0,    0,    0,    0,    0,    0,    0,   68,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   87,   82,   88,
   83,    0,  104,    0,    0,  130,  131,   62,   59,   61,
    0,    0,    0,   39,    0,    0,    0,    0,   50,   94,
   48,   46,   45,    0,    0,    0,    0,    0,  138,    0,
  142,    0,    0,    0,    0,    0,   69,    0,   66,  117,
   92,    0,    0,  134,  107,    0,    0,    0,    0,    0,
    0,   38,   37,    0,   36,    0,    0,    0,   44,   41,
    0,    0,  139,    0,  143,    0,    0,    0,  116,    0,
    0,   93,    0,  110,    0,  102,    0,  109,    0,  103,
    0,    0,   40,   29,    0,    0,    0,   33,    0,    0,
    0,    0,  137,  141,    0,    0,    0,    0,    0,    0,
  133,    0,  105,  108,    0,  106,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,   67,
  132,  111,  101,   34,   35,   30,   32,    0,   26,  121,
    0,  123,  122,  113,   25,  112,
};
final static short yydgoto[] = {                          3,
   42,   17,   18,   19,   20,   21,   22,   23,   24,   65,
   25,   43,   26,   68,  186,   27,  142,   28,   29,   30,
   31,   32,  106,   45,   46,   84,   47,   48,   49,  132,
  165,   95,  166,  167,  190,  133,  134,  135,   34,
};
final static short yysindex[] = {                      -195,
  952,    0,    0,  848,   -8,  -31,    7,  -53,   51,    0,
 -198,    0,    0,    0,    0,    0,  876,    0,    0,    8,
    0,   41,   48, -159,    0,   89,    0,    0,    0,    0,
    0,    0, -107,  -31,  895,  914,    0,  863,    0,  474,
 -131,  100,    0,  797,   23,    0,  114,    0, -111,  412,
  466,  -70,  -70, -125,  443,    0,    0,    0,    0,    0,
    0,    0, -113,    0,  -40,    0,  520,  -96,  526,  -13,
 1068,    0,  933,    0,  115, -231,  -91,  -82,  -74,  114,
  526,  604,  797,   76,    0,  611,    0,    0,    0,    0,
    0,  647,  661,    0,  526,  -12,  671,  675, 1085,  714,
    0,    0,  100,  573,   38,  142,  -88,  -70,    0,  -62,
   64,  171,  -97,    0,  147,    0,    0,    0,  -27,    0,
  199,    0,  253,  -36,  952,   47,  193,   29,    0,  722,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   47,   47,  -23,  526,  526,  678,  653,    0,  282,  358,
  115,  114,  115,  114,  384,  211,   57,    0,    0,    0,
    0, 1113,    0,  -49,  278,    0,    0,    0,    0,    0,
  -11,  -56,  -27,    0,   39,  -27,   39,  -43,    0,    0,
    0,    0,    0,  308,  952,   92,   83,  250,    0,  749,
    0,  -32,  337,  433,   47,  365,    0,  526,    0,    0,
    0,  262, -135,    0,    0,  235,  -39,  151,  306,  172,
 -216,    0,    0,  396,    0,  411,    5,  425,    0,    0,
  526,  201,    0, 1121,    0,  604,  523,  604,    0,  604,
  463,    0,  789,    0, 1099,    0,  -28,    0,  221,    0,
   83,  360,    0,    0,   39,   39,   39,    0,  -22,   39,
   47,  526,    0,    0,  122,  604,  132,  156,  158,  448,
    0, 1135,    0,    0, -157,    0,  201,  368,  374,  388,
   14,    0,  390,   47,  480,  173,  483,  484,  487,    0,
    0,    0,    0,    0,    0,    0,    0,  406,    0,    0,
  492,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -120,    0,    0,    0,  266,
    0,  347,  373,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  535,    0,    0,    0,    0,
    0,    1,    0,    0,   27,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  497,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  540,    0,    0,    0,    0,    0,    0,   82,
    0,    0,  291,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -30,    0,    0,    0,  616,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  209,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  626,  723,    0,    0,  762,  978,    0,    0,    0,    0,
  108,  134,  160,  186,  996,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  280,    0,    0,    0,    0,    0,
    0,    0,    0, 1014,  821,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  904,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  239,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  982,    0,    0,    0,
    0,    0,  320,  292,    0,    0,    0,    0, 1032,    0,
    0,    0,    0,    0,    0,    0,    0,  451,    0,    0,
 1050,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  701,   60,   85,    0,  866,    0,    0,    0,    3,    0,
  -10,  836,    0,    0,    0,    0,  752,    0,    0,    0,
    0,    0,    0,  745,    0,  730,  -15,   21,  508,  446,
 -140,  -42,    0,    0,  343,  841,    0,    0,    0,
};
final static int YYTABLESIZE=1408;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        173,
   97,   41,   97,  119,  183,  211,   53,  226,   40,  206,
   97,   97,   97,   41,   97,   70,   97,  192,  118,  239,
  145,  271,   80,  207,  137,  177,   89,   90,   94,   91,
  265,  128,  157,  210,  171,   85,   90,   94,   91,  243,
  144,   97,   97,   97,   97,   97,   50,   97,  247,   41,
   37,    2,   76,  110,  111,  112,  124,   56,   41,   97,
   97,   97,   97,   36,    1,  237,   60,   89,   89,   89,
   89,   89,    2,   89,   57,  149,  152,  154,  169,  217,
   93,   81,   92,   41,   80,   89,   89,   89,   89,   93,
   55,   92,   97,   76,   73,   76,   76,   76,  282,   61,
  193,   59,  272,  196,  283,   63,   62,   80,    2,  104,
  172,   76,   76,   76,   76,  178,  146,  159,  161,  145,
   59,    6,   81,   97,   81,   81,   81,    7,   67,  248,
    9,   80,    2,   75,   10,   85,   11,   12,  287,   86,
   81,   81,   81,   81,   20,   96,   99,   20,   80,  227,
   80,   80,   80,  113,  116,   97,   78,   59,   97,   79,
   98,   79,  275,  125,  138,  145,   80,   80,   80,   80,
    2,   69,  277,  139,   75,  145,   75,   75,   75,   13,
   14,  140,  170,   15,  185,   74,  175,  179,   59,   93,
   69,   92,   75,   75,   75,   75,  278,    2,  279,  145,
   79,  145,   79,   79,   79,    2,   13,   14,   63,  162,
   15,    2,  205,  291,    2,  117,  145,   51,   79,   79,
   79,   79,  238,   39,   38,   52,   74,    2,   74,   74,
   74,    2,  176,  264,   20,   39,    2,   20,   64,  181,
    2,   87,   88,   89,   74,   74,   74,   74,   97,   63,
   87,   88,   89,  127,  156,  209,   97,   97,   97,   97,
   97,   97,   97,   97,   97,   12,   97,   63,   97,   59,
   97,   39,   97,   97,   97,   97,   97,   97,   97,   64,
   39,   97,   89,   89,   89,   89,   89,   89,   89,   89,
   89,   65,   89,  182,   89,  188,   89,   64,   89,   89,
   89,   89,   89,   89,   89,   39,   12,   89,   76,   76,
   76,   76,   76,   76,   76,   76,   76,  187,   76,   28,
   76,  198,   76,  202,   76,   76,   76,   76,   76,   76,
   76,   73,   65,   76,   73,  201,  208,   81,   81,   81,
   81,   81,   81,   81,   81,   81,   16,   81,  219,   81,
   65,   81,  220,   81,   81,   81,   81,   81,   81,   81,
   28,  221,   81,   80,   80,   80,   80,   80,   80,   80,
   80,   80,   17,   80,  222,   80,  228,   80,   28,   80,
   80,   80,   80,   80,   80,   80,  232,   16,   80,   75,
   75,   75,   75,   75,   75,   75,   75,   75,  199,   75,
   93,   75,   92,   75,  230,   75,   75,   75,   75,   75,
   75,   75,  240,   17,   75,   79,   79,   79,   79,   79,
   79,   79,   79,   79,  200,   79,   93,   79,   92,   79,
  241,   79,   79,   79,   79,   79,   79,   79,  242,  245,
   79,   74,   74,   74,   74,   74,   74,   74,   74,   74,
   27,   74,  102,   74,  246,   74,   41,   74,   74,   74,
   74,   74,   74,   74,   63,   63,   74,   63,  250,   63,
   63,   63,   63,  229,   63,   93,   63,   92,   63,  252,
   63,   63,  266,  114,  267,   63,   63,   41,  280,   63,
  234,   27,  284,  235,   64,   64,  236,   64,  285,   64,
   64,   64,   64,  260,   64,   93,   64,   92,   64,   27,
   64,   64,  286,   82,  289,   64,   64,   81,   41,   64,
  290,   12,   12,  292,  293,  108,   12,  294,   12,   12,
  295,   12,  296,   12,    6,   12,   43,   12,   12,    4,
   49,   71,   12,   12,  164,  233,   12,   65,   65,    0,
   65,    0,   65,   65,   65,   65,    0,   65,    0,   65,
  122,   65,  256,   65,   65,    0,   81,   41,   65,   65,
   41,    0,   65,    0,    0,   28,   28,    0,    0,    0,
   28,    0,   28,   28,    0,   28,    0,   28,    0,   28,
    0,   28,   28,    0,    0,    0,   28,   28,    0,    0,
   28,    0,   16,   16,    0,    0,    0,   16,    0,   16,
   16,    0,   16,  168,   16,    0,   16,    0,   16,   16,
    0,    0,    0,   16,   16,    0,    0,   16,   17,   17,
    0,    0,    0,   17,    0,   17,   17,    0,   17,    0,
   17,    0,   17,    0,   17,   17,    0,   81,   41,   17,
   17,  148,    0,   17,    0,   41,   89,   89,   89,    0,
   89,    0,   89,    0,    0,    0,   71,  100,    6,   71,
    0,    0,    0,    0,    7,    8,    0,    9,   39,    2,
  101,   10,    0,   11,   12,   71,   71,   71,   13,   14,
    0,   41,   15,  197,   78,   77,    0,   76,   38,   79,
    4,   16,    0,    0,   16,   41,   27,   27,   54,   39,
    2,   27,    0,   27,   27,   41,   27,   16,   27,   41,
   27,    0,   27,   27,   64,    0,    0,   27,   27,   38,
    0,   27,    0,    2,    0,   16,   16,   90,   94,   91,
   39,    2,   13,   14,    0,   33,   15,    0,   33,    0,
  103,  109,  109,  109,    0,   78,   77,   44,   76,    0,
   79,   33,    0,   73,    0,   64,   73,  123,   66,    0,
    0,   64,   37,   16,    0,  121,    0,    0,   38,   33,
   33,   38,   73,   73,   73,   44,    0,    2,    0,   39,
    2,   83,   39,    2,  107,    0,   13,   14,    0,   64,
   15,  105,   72,    0,   16,   72,  115,  224,  109,  120,
  174,  143,    0,  109,    0,   33,    0,   33,    0,   64,
  126,   72,   72,   72,  184,   16,    0,    0,    5,    6,
   64,    0,  141,    0,    0,    7,    8,  150,    9,   93,
    2,   92,   10,   33,   11,   12,  155,  262,   33,   13,
   14,    0,    0,   15,    0,    0,   90,   94,   91,   38,
    0,   70,   64,  180,   70,    0,  147,    0,    0,   33,
   39,    2,  212,  213,   33,    0,  215,   39,    2,    0,
   70,   70,   70,    0,    0,   16,    0,   13,   14,    0,
   64,   15,    0,    0,    0,  194,  195,    0,    0,   71,
   71,   71,  151,   64,   78,   77,   33,   76,   75,   79,
    0,  244,    0,   39,    2,    0,  153,    0,    0,   39,
    2,    0,    0,    0,   64,    0,  158,   39,    2,   33,
  160,    0,    0,   64,   33,   64,  131,   39,    2,    0,
    0,   39,    2,    0,   92,   92,   92,   33,   92,  231,
   92,   87,   88,   89,    0,  255,  257,  258,    0,  259,
    0,    0,   64,    0,  131,    0,    0,    0,   33,   75,
  191,    0,  251,    0,    0,    0,    0,   33,    6,   33,
   39,    2,  189,    0,    7,  276,    0,    9,    0,    2,
    0,   10,    0,   11,   12,  131,   73,   73,   73,    0,
    0,    0,  204,  274,    0,    6,   33,    0,    0,  223,
  214,    7,  216,  218,    9,    0,    2,    0,   10,    0,
   11,   12,   93,   93,   93,    0,   93,  131,   93,    0,
  225,    0,    0,    0,    0,   72,   72,   72,    0,    0,
    0,    0,    0,  191,    0,    6,    0,    0,    0,  261,
    0,    7,  249,    0,    9,  131,    2,    0,   10,    0,
   11,   12,    0,    0,  254,    0,    0,    0,  131,    0,
   87,   88,   89,  225,    0,  204,    0,    0,    0,    0,
  268,  269,  270,    0,    0,  273,    0,    0,    0,  131,
    0,    0,    0,    0,   70,   70,   70,    0,  131,    0,
  131,    0,  254,    5,    6,    0,  288,   35,    0,    0,
    7,    8,    0,    9,    0,    2,    0,   10,   75,   11,
   12,    0,    0,    0,   13,   14,    0,  131,   15,   39,
    2,    5,    6,    0,    0,    0,   58,    0,    7,    8,
    0,    9,    0,    2,    0,   10,    0,   11,   12,    0,
    5,    6,   13,   14,    0,   72,   15,    7,    8,    0,
    9,    0,    2,    0,   10,    0,   11,   12,    0,    5,
    6,   13,   14,    0,   74,   15,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    0,    5,    6,
   13,   14,    0,  136,   15,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    5,    6,   13,
   14,    0,    0,   15,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    0,    0,   13,   14,
    0,    0,   15,  120,  120,  120,    0,  120,    0,    0,
  120,    0,    0,  120,    0,  120,    0,  120,    0,  120,
  120,  119,  119,  119,    0,  119,    0,    0,  119,    0,
    0,  119,    0,  119,    0,  119,    0,  119,  119,  118,
  118,  118,    0,  118,    0,    0,  118,    0,    0,  118,
    0,  118,    0,  118,    0,  118,  118,  115,  115,  115,
    0,  115,    0,    0,  115,    0,    0,  115,    0,  115,
    0,  115,    0,  115,  115,  114,  114,  114,    0,  114,
    0,    0,  114,    0,    0,  114,    0,  114,    0,  114,
    0,  114,  114,  129,    6,    0,    0,  130,    0,    0,
    7,    0,    0,    9,    0,    2,    0,   10,    0,   11,
   12,    6,    0,  162,  130,    0,  163,    7,    0,    0,
    9,    0,    2,    0,   10,    6,   11,   12,  203,    0,
  263,    7,    0,    0,    9,    0,    2,    0,   10,    6,
   11,   12,  203,    0,    0,    7,    0,    6,    9,    0,
    2,  253,   10,    7,   11,   12,    9,    0,    2,    0,
   10,    6,   11,   12,    0,  281,    0,    7,    0,    0,
    9,    0,    2,    0,   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         62,
    0,   45,  123,   44,   41,   62,   60,   40,   40,   59,
   41,   42,   43,   45,   45,  123,   47,   41,   59,   59,
   44,   44,   38,  164,  256,  123,    0,   60,   61,   62,
   59,   45,   45,   45,  123,  267,   60,   61,   62,  256,
   83,   41,   42,   43,   44,   45,   40,   47,   44,   45,
   59,  268,    0,   51,   52,   53,   67,  256,   45,   59,
   60,   61,   62,    4,  260,  206,   59,   41,   42,   43,
   44,   45,  268,   47,  273,   86,   92,   93,   41,  123,
   43,    0,   45,   45,  100,   59,   60,   61,   62,   43,
   40,   45,  123,   41,   35,   43,   44,   45,  256,   59,
  143,   17,  125,  146,  262,  265,   59,    0,  268,   50,
  108,   59,   60,   61,   62,  113,   41,   97,   98,   44,
   36,  257,   41,  123,   43,   44,   45,  263,   40,  125,
  266,  147,  268,    0,  270,  267,  272,  273,  125,   40,
   59,   60,   61,   62,  265,  123,  258,  268,   41,  192,
   43,   44,   45,  279,  268,   42,   42,   73,  279,    0,
   47,   47,   41,  260,  256,   44,   59,   60,   61,   62,
  268,  279,   41,  256,   41,   44,   43,   44,   45,  277,
  278,  256,   41,  281,  125,    0,  123,   41,  104,   43,
  279,   45,   59,   60,   61,   62,   41,  268,   41,   44,
   41,   44,   43,   44,   45,  268,  277,  278,    0,  259,
  281,  268,  262,   41,  268,  256,   44,  271,   59,   60,
   61,   62,  262,  267,  256,  279,   41,  268,   43,   44,
   45,  268,   62,  262,  265,  267,  268,  268,    0,   41,
  268,  274,  275,  276,   59,   60,   61,   62,  279,   41,
  274,  275,  276,  267,  267,  267,  256,  257,  258,  259,
  260,  261,  262,  263,  264,    0,  266,   59,  268,  185,
  270,  267,  272,  273,  274,  275,  276,  277,  278,   41,
  267,  281,  256,  257,  258,  259,  260,  261,  262,  263,
  264,    0,  266,   41,  268,  267,  270,   59,  272,  273,
  274,  275,  276,  277,  278,  267,   41,  281,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  125,  266,    0,
  268,   40,  270,  267,  272,  273,  274,  275,  276,  277,
  278,   41,   41,  281,   44,  125,   59,  256,  257,  258,
  259,  260,  261,  262,  263,  264,    0,  266,   41,  268,
   59,  270,  261,  272,  273,  274,  275,  276,  277,  278,
   41,  279,  281,  256,  257,  258,  259,  260,  261,  262,
  263,  264,    0,  266,  125,  268,   40,  270,   59,  272,
  273,  274,  275,  276,  277,  278,  125,   41,  281,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   41,  266,
   43,  268,   45,  270,   40,  272,  273,  274,  275,  276,
  277,  278,  262,   41,  281,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   43,  268,   45,  270,
  125,  272,  273,  274,  275,  276,  277,  278,  267,   44,
  281,  256,  257,  258,  259,  260,  261,  262,  263,  264,
    0,  266,   41,  268,   44,  270,   45,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  281,  259,   44,  261,
  262,  263,  264,   41,  266,   43,  268,   45,  270,  279,
  272,  273,  262,   41,  125,  277,  278,   45,   41,  281,
  256,   41,  125,  259,  256,  257,  262,  259,  125,  261,
  262,  263,  264,   41,  266,   43,  268,   45,  270,   59,
  272,  273,  125,   40,  125,  277,  278,   44,   45,  281,
   41,  256,  257,   41,   41,   60,  261,   41,  263,  264,
  125,  266,   41,  268,    0,  270,   40,  272,  273,    0,
  261,   34,  277,  278,   99,  203,  281,  256,  257,   -1,
  259,   -1,  261,  262,  263,  264,   -1,  266,   -1,  268,
   41,  270,   40,  272,  273,   -1,   44,   45,  277,  278,
   45,   -1,  281,   -1,   -1,  256,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,   -1,  256,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,   -1,  266,   41,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,   44,   45,  277,
  278,   41,   -1,  281,   -1,   45,   41,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,   -1,   41,  256,  257,   44,
   -1,   -1,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,   -1,  272,  273,   60,   61,   62,  277,  278,
   -1,   45,  281,   41,   42,   43,   -1,   45,  256,   47,
    0,    1,   -1,   -1,    4,   45,  256,  257,    8,  267,
  268,  261,   -1,  263,  264,   45,  266,   17,  268,   45,
  270,   -1,  272,  273,   24,   -1,   -1,  277,  278,  256,
   -1,  281,   -1,  268,   -1,   35,   36,   60,   61,   62,
  267,  268,  277,  278,   -1,    1,  281,   -1,    4,   -1,
   50,   51,   52,   53,   -1,   42,   43,    6,   45,   -1,
   47,   17,   -1,   41,   -1,   65,   44,   67,   24,   -1,
   -1,   71,   59,   73,   -1,  256,   -1,   -1,  256,   35,
   36,  256,   60,   61,   62,   34,   -1,  268,   -1,  267,
  268,   40,  267,  268,   50,   -1,  277,  278,   -1,   99,
  281,   50,   41,   -1,  104,   44,   55,   59,  108,   65,
  110,   82,   -1,  113,   -1,   71,   -1,   73,   -1,  119,
   69,   60,   61,   62,  124,  125,   -1,   -1,  256,  257,
  130,   -1,   81,   -1,   -1,  263,  264,   86,  266,   43,
  268,   45,  270,   99,  272,  273,   95,   59,  104,  277,
  278,   -1,   -1,  281,   -1,   -1,   60,   61,   62,  256,
   -1,   41,  162,  119,   44,   -1,  256,   -1,   -1,  125,
  267,  268,  172,  173,  130,   -1,  176,  267,  268,   -1,
   60,   61,   62,   -1,   -1,  185,   -1,  277,  278,   -1,
  190,  281,   -1,   -1,   -1,  144,  145,   -1,   -1,  274,
  275,  276,  256,  203,   42,   43,  162,   45,  256,   47,
   -1,  211,   -1,  267,  268,   -1,  256,   -1,   -1,  267,
  268,   -1,   -1,   -1,  224,   -1,  256,  267,  268,  185,
  256,   -1,   -1,  233,  190,  235,   71,  267,  268,   -1,
   -1,  267,  268,   -1,   41,   42,   43,  203,   45,  198,
   47,  274,  275,  276,   -1,  226,  227,  228,   -1,  230,
   -1,   -1,  262,   -1,   99,   -1,   -1,   -1,  224,  256,
  130,   -1,  221,   -1,   -1,   -1,   -1,  233,  257,  235,
  267,  268,  261,   -1,  263,  256,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  130,  274,  275,  276,   -1,
   -1,   -1,  162,  252,   -1,  257,  262,   -1,   -1,  261,
  175,  263,  177,  178,  266,   -1,  268,   -1,  270,   -1,
  272,  273,   41,   42,   43,   -1,   45,  162,   47,   -1,
  190,   -1,   -1,   -1,   -1,  274,  275,  276,   -1,   -1,
   -1,   -1,   -1,  203,   -1,  257,   -1,   -1,   -1,  261,
   -1,  263,  217,   -1,  266,  190,  268,   -1,  270,   -1,
  272,  273,   -1,   -1,  224,   -1,   -1,   -1,  203,   -1,
  274,  275,  276,  233,   -1,  235,   -1,   -1,   -1,   -1,
  245,  246,  247,   -1,   -1,  250,   -1,   -1,   -1,  224,
   -1,   -1,   -1,   -1,  274,  275,  276,   -1,  233,   -1,
  235,   -1,  262,  256,  257,   -1,  271,  260,   -1,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,  256,  272,
  273,   -1,   -1,   -1,  277,  278,   -1,  262,  281,  267,
  268,  256,  257,   -1,   -1,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,
  256,  257,  277,  278,   -1,  261,  281,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,
  257,  277,  278,   -1,  261,  281,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,
  277,  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,   -1,   -1,  281,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  256,  257,  258,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,  258,
   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  256,  257,   -1,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  257,   -1,  259,  260,   -1,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,
  272,  273,  260,   -1,   -1,  263,   -1,  257,  266,   -1,
  268,  261,  270,  263,  272,  273,  266,   -1,  268,   -1,
  270,  257,  272,  273,   -1,  261,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,
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

//#line 317 "gramatica.y"
	
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


private static void cargarErrorEImprimirlo(String salida) {	
		try {
			AnalizadorLexico.sintactico.newLine();  // Agregar un salto de línea
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
        // Construimos la clave: id + ámbito actual
        String key = id + ambitoActual;
        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key).estaDeclarada(); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el ámbito: eliminamos el último ':NIVEL'
        int pos = ambitoActual.lastIndexOf("$");
        if (pos == -1) {
            break; // Si ya no hay más ámbitos, salimos del ciclo
        }

        // Reducimos el ámbito actual
        ambitoActual = ambitoActual.substring(0, pos);
    }

    // Si no se encuentra en ningún ámbito, lanzamos un error o devolvemos null
    throw new RuntimeException( "Linea :" + AnalizadorLexico.saltoDeLinea +"Error: ID '" + id + "' no encontrado en ningún ámbito.");
}

public static Simbolo getVariableFueraDeAmbito(String id){
    String ambitoActual = AMBITO.toString(); // Convertimos AMBITO (StringBuilder) a String
    String key = id;
    while (true) {
        // Construimos la clave: id + ámbito actual

        // Buscamos en el mapa
        if (AnalizadorLexico.TablaDeSimbolos.containsKey(key)) {
            return AnalizadorLexico.TablaDeSimbolos.get(key); // Si la clave existe, devolvemos el valor
        }

        // Reducimos el ámbito: eliminamos el último '$NIVEL'
        int pos = key.lastIndexOf("$");

        // Reducimos el ámbito actual
        key = key.substring(0,pos);
    }
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
//#line 1036 "Parser.java"
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
																cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");}
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
																		cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");}}
break;
case 65:
//#line 142 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 66:
//#line 145 "gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada ");}
													else{	
														if(AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval+AMBITO.toString()).getTipoParFormal()==AnalizadorLexico.TablaDeSimbolos.get(val_peek(1).sval+AMBITO.toString()).getTipo().getType()){
															GeneradorCodigoIntermedio.invocar(val_peek(3).sval+AMBITO.toString());
														}else{
															cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: El parametro real es incompatible con el parametro formal ");
														}																																																		
												}}
break;
case 67:
//#line 155 "gramatica.y"
{if(!fueDeclarado(val_peek(6).sval)){
													cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada ");}
													else{
														if(AnalizadorLexico.TablaDeSimbolos.get(val_peek(6).sval+AMBITO.toString()).getTipoParFormal()==((Tipo)val_peek(4).obj).getType()){
															GeneradorCodigoIntermedio.invocar(val_peek(6).sval+AMBITO.toString());
														}else{
															cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: El parametro real se quiere convertir en un tipo incompatible con el parametro formal ");
														}
												}}
break;
case 68:
//#line 164 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
break;
case 69:
//#line 165 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
break;
case 70:
//#line 169 "gramatica.y"
{yyval.ival=val_peek(2).ival + 1;}
break;
case 71:
//#line 170 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 72:
//#line 171 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 73:
//#line 172 "gramatica.y"
{yyval.ival=1;}
break;
case 74:
//#line 175 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("+"); }
break;
case 75:
//#line 176 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("-"); }
break;
case 77:
//#line 178 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 78:
//#line 179 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 79:
//#line 180 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 80:
//#line 181 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 81:
//#line 182 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
break;
case 82:
//#line 185 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("*");}
break;
case 83:
//#line 186 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento("/");}
break;
case 85:
//#line 188 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 86:
//#line 189 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 87:
//#line 190 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 88:
//#line 191 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 89:
//#line 194 "gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");};}
break;
case 90:
//#line 195 "gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 92:
//#line 197 "gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ 
											if(Integer.valueOf(val_peek(1).sval) <= 3){
												GeneradorCodigoIntermedio.addElemento(val_peek(3).sval+Parser.AMBITO.toString());
												GeneradorCodigoIntermedio.addElemento(val_peek(1).sval);
												GeneradorCodigoIntermedio.addElemento("INDEX");
												AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval).incrementarContDeRef(); yyval.sval = val_peek(3).sval;
											}else{
												cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");
											}
										}else{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada ");};}
break;
case 93:
//#line 207 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 94:
//#line 209 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 95:
//#line 210 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
break;
case 96:
//#line 211 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 99:
//#line 221 "gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 100:
//#line 222 "gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 101:
//#line 225 "gramatica.y"
{if(val_peek(4).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};completarBifurcacionI();}
break;
case 102:
//#line 226 "gramatica.y"
{yyval.sval=val_peek(2).sval;completarBifurcacionISinElse();}
break;
case 103:
//#line 227 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN");}
break;
case 104:
//#line 228 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
break;
case 105:
//#line 229 "gramatica.y"
{{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}
break;
case 106:
//#line 232 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
break;
case 107:
//#line 233 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
break;
case 108:
//#line 234 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
break;
case 109:
//#line 235 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
break;
case 110:
//#line 237 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
break;
case 111:
//#line 238 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
break;
case 112:
//#line 241 "gramatica.y"
{if(val_peek(6).ival == val_peek(2).ival){yyval.ival=val_peek(6).ival;modificarPolacaPM(val_peek(4).sval,val_peek(6).ival);}else{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
break;
case 113:
//#line 242 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 114:
//#line 243 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 115:
//#line 244 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 116:
//#line 245 "gramatica.y"
{yyval.ival=1;opCondicion(val_peek(2).sval);}
break;
case 117:
//#line 246 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 118:
//#line 247 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 119:
//#line 248 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 120:
//#line 250 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
break;
case 121:
//#line 252 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
break;
case 122:
//#line 253 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
break;
case 123:
//#line 254 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
break;
case 124:
//#line 257 "gramatica.y"
{yyval.sval=">";}
break;
case 125:
//#line 258 "gramatica.y"
{yyval.sval=">=";}
break;
case 126:
//#line 259 "gramatica.y"
{yyval.sval="<";}
break;
case 127:
//#line 260 "gramatica.y"
{yyval.sval="<=";}
break;
case 128:
//#line 261 "gramatica.y"
{yyval.sval="=";}
break;
case 129:
//#line 262 "gramatica.y"
{yyval.sval="!=";}
break;
case 130:
//#line 265 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 131:
//#line 266 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 132:
//#line 269 "gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 133:
//#line 270 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 134:
//#line 273 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 135:
//#line 276 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};if(esWHILE==false){operacionesIF();}else{esWHILE=false;}}
break;
case 136:
//#line 277 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};if(esWHILE==false){operacionesIF();}else{esWHILE=false;}}
break;
case 137:
//#line 280 "gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 138:
//#line 281 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 139:
//#line 282 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 140:
//#line 285 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 141:
//#line 288 "gramatica.y"
{if(val_peek(2).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 142:
//#line 289 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 143:
//#line 290 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 144:
//#line 293 "gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 145:
//#line 296 "gramatica.y"
{cargarCadenaMultilinea(val_peek(0).sval);GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 146:
//#line 301 "gramatica.y"
{operacionesWhile(val_peek(1).ival);}
break;
case 147:
//#line 302 "gramatica.y"
{cargarErrorEImprimirlo("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
break;
case 148:
//#line 305 "gramatica.y"
{esWHILE=true;GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}
break;
case 149:
//#line 309 "gramatica.y"
{cargarVariables(val_peek(0).sval,tipos.get("ETIQUETA"),"ETIQUETA");
								GeneradorCodigoIntermedio.BifurcarAGoto(val_peek(0).sval);}
break;
case 150:
//#line 311 "gramatica.y"
{cargarErrorEImprimirlo("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO ");}
break;
//#line 1780 "Parser.java"
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
