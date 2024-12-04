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
   22,   22,   22,   18,   18,   18,   25,   25,   25,   25,
   26,   26,   26,   26,   17,   17,   17,   17,   17,   17,
   17,   17,   27,   27,   27,   27,   27,   27,   27,   28,
   28,   28,   28,   28,   10,   10,   10,   24,    1,   12,
   12,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   32,   32,   32,   32,   32,   32,   31,
   31,   34,   34,   34,   34,   33,   30,   30,   38,   38,
   38,   38,   37,   39,   39,   41,   41,   41,   41,   40,
   35,   35,   35,   36,   23,   20,   20,   42,   21,   21,
   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    2,    2,    1,    1,    3,    3,    1,
    1,    1,    1,    1,    9,    8,    8,    7,    6,    8,
    7,    8,    6,    8,    8,    5,    5,    5,    4,    6,
    5,    3,    2,    4,    4,    3,    3,    2,    3,    1,
    4,    3,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    4,    4,    3,    6,    7,    4,    7,    3,    4,
    3,    2,    2,    1,    3,    3,    1,    3,    3,    3,
    3,    2,    3,    3,    1,    3,    3,    3,    3,    1,
    1,    1,    4,    5,    3,    2,    1,    1,    1,    1,
    2,    7,    5,    4,    6,    6,    4,    6,    5,    5,
    7,    9,    8,    8,    7,    5,    4,    4,    3,    3,
    8,    8,    8,    1,    1,    1,    1,    1,    1,    1,
    1,    5,    3,    4,    4,    2,    1,    1,    5,    3,
    4,    4,    2,    1,    1,    4,    2,    3,    3,    1,
    3,    1,    2,    1,    1,    3,    3,    1,    2,    2,
    2,
};
final static short yydefred[] = {                         0,
    0,   99,    0,    0,    0,    0,    0,    0,    0,  158,
    0,   57,   22,   23,   24,    0,    0,    8,    9,    0,
   13,    0,    0,    0,   21,    0,   59,   53,   54,   55,
   56,   58,    0,    0,    0,    0,   10,    0,  100,    0,
    0,    0,   91,    0,    0,   92,    0,   85,    0,    0,
    0,    0,    0,    0,    0,  161,  159,  160,    3,    7,
   11,   14,   15,    0,   98,    0,   97,    0,    0,    0,
    0,    0,    2,    0,    5,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  101,    0,  125,  127,  129,
  126,  124,    0,    0,  128,    0,    0,    0,    0,    0,
    0,  137,  138,    0,  155,   61,    0,    0,    0,    0,
    0,    0,   20,    0,    0,    0,    0,   52,    0,   42,
   19,   18,    0,   96,    0,   48,    0,    0,    0,    0,
    0,    0,  157,    0,  154,  150,  156,  144,  145,    1,
   79,   78,   86,   87,    0,    0,    0,    0,    0,    0,
    0,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,   83,   89,   84,    0,  104,  143,    0,  107,
    0,    0,  130,  131,   63,   60,   62,    0,    0,    0,
   39,    0,    0,    0,    0,   51,   95,   49,   47,    0,
   46,    0,    0,    0,    0,    0,  147,    0,  152,    0,
    0,    0,    0,    0,   70,    0,   67,  117,   93,    0,
  140,    0,    0,  136,  110,    0,  103,    0,  109,    0,
    0,    0,    0,   38,   37,    0,   36,    0,    0,    0,
   45,   44,   41,    0,    0,  149,  148,    0,  153,    0,
    0,    0,  116,    0,    0,   94,  142,  141,    0,  133,
    0,  105,  108,    0,  106,    0,    0,   40,   29,    0,
    0,    0,   33,    0,    0,    0,    0,  146,  151,    0,
    0,    0,    0,    0,    0,  139,  135,  134,    0,  111,
  102,    0,    0,    0,    0,    0,   31,    0,    0,    0,
    0,    0,    0,    0,   68,  132,   34,   35,   30,   32,
    0,   26,  121,    0,  123,  122,  113,   25,  112,
};
final static short yydgoto[] = {                          3,
   42,   17,   18,   19,  135,   21,   22,   23,   24,   66,
   25,   43,   26,   69,  194,   27,  146,   28,   29,   30,
   31,   32,  110,   45,   46,   85,   47,   48,   49,  101,
  172,   96,  173,  174,  198,  199,  102,  103,  137,  138,
  139,   34,
};
final static short yysindex[] = {                      -203,
  949,    0,    0,  850,   -5,  -31,   37,  -53,   41,    0,
 -178,    0,    0,    0,    0,    0,  873,    0,    0,   33,
    0,   42,   51, -137,    0,   93,    0,    0,    0,    0,
    0,    0, -107,  -31,  892,  911,    0,  841,    0,  562,
 -167,  100,    0,  638,   22,    0,    9,    0, -112,  518,
  428, -202, -202, -125,  435,    0,    0,    0,    0,    0,
    0,    0,    0, -109,    0,  -40,    0,  384,  -97,  486,
  -13, 1065,    0,  930,    0,   60, -209,  -91,  -67,  -66,
    9,  486,  496,  638,  106,    0,  492,    0,    0,    0,
    0,    0,  560,  564,    0,  486,  -12,  577,  612, 1079,
  -49,    0,    0,  822,    0,    0,  100,  416,  121,  158,
  -87, -202,    0,  -62,   77,  145, -120,    0,  142,    0,
    0,    0,  -56,    0,  167,    0,  -36,  -35,  949,   66,
  169,    3,    0, 1101,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   66,   66,  -23,  486,  486,  464,
  816,    0,  256,  358,   60,    9,   60,    9,  448,  193,
   55,    0,    0,    0,    0, 1115,    0,    0, 1129,    0,
 -127,  -39,    0,    0,    0,    0,    0,  -11,  -27,  -56,
    0,   39,  -56,   39,  -43,    0,    0,    0,    0,  283,
    0,  295,  949,   76,   70,  228,    0,  734,    0,  -32,
  322,  463,   66,  335,    0,  486,    0,    0,    0,  252,
    0,  754, 1137,    0,    0, 1093,    0,  -28,    0,  125,
  280,  146, -201,    0,    0,  383,    0,  387,    5,  395,
    0,    0,    0,  486,  161,    0,    0, 1151,    0,  496,
  626,  496,    0,  496,  546,    0,    0,    0, 1159,    0,
  787,    0,    0, -171,    0,   70,  328,    0,    0,   39,
   39,   39,    0,  -20,   39,   66,  486,    0,    0,  130,
  496,  132,  147,  173,  414,    0,    0,    0, 1173,    0,
    0,  161,  344,  349,  353,   14,    0,  359,   66,  442,
  291,  444,  458,  472,    0,    0,    0,    0,    0,    0,
  389,    0,    0,  474,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  306,    0,    0,    0,  266,
    0,  347,  373,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  519,    0,    0,    0,    0,
    0,    1,    0,    0,   27,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  478,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  521,    0,    0,    0,    0,    0,    0,
   82,    0,    0,  453,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -30,    0,    0,    0,
   75,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  534,  615,    0,    0,  705,  975,
    0,    0,    0,    0,  108,  134,  160,  186,  993,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  267,    0,    0,    0,    0,    0,    0,    0,
    0, 1011,  761,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  914,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  239,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, 1053,    0,    0,    0,    0,    0,  320,  292,    0,
    0,    0,    0, 1029,    0,    0,    0,    0,    0,    0,
  451,    0,    0, 1047,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  726,   69,   47,    0,  900,    0,    0,    0,   85,    0,
  -46,  802,    0,    0,    0,    0,  608,    0,    0,    0,
    0,    0,    0,  800,    0,  -60,  858,  -73,  501,    0,
  371,  -44,    0,    0, -114,  854,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=1446;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        180,
   98,   41,  184,  123,  189,  191,   53,  240,   40,  171,
   98,   98,   98,   41,   98,   71,   98,  200,  122,  220,
  149,  128,  147,  286,  163,  165,   90,   91,   95,   92,
  254,  132,  161,  222,  223,  178,   91,   95,   92,  148,
  153,   98,   98,   98,   98,   98,  141,   98,  262,   41,
   98,  212,   77,   37,  258,   99,    1,   86,   41,   98,
   98,   98,   98,   60,    2,    2,    2,   90,   90,   90,
   90,   90,   36,   90,   13,   14,   50,   56,   15,  229,
   55,   82,   60,   41,  280,   90,   90,   90,   90,    2,
  281,   61,   98,   77,   57,   77,   77,   77,  251,   86,
   62,   79,  201,   74,  287,  204,   80,   81,   94,   63,
   93,   77,   77,   77,   77,   90,   90,   90,  108,   90,
   60,   90,   82,   98,   82,   82,   82,   64,  215,  263,
    2,  216,   68,   76,  217,  114,  115,  116,  300,   87,
   82,   82,   82,   82,   97,  100,  150,    2,   81,  149,
   81,   81,   81,  117,   60,  241,   13,   14,  120,   80,
   15,  176,  129,   94,  142,   93,   81,   81,   81,   81,
  290,   70,  292,  149,   76,  149,   76,   76,   76,  270,
  272,  273,  186,  274,   94,   75,   93,  293,  143,  144,
  149,   70,   76,   76,   76,   76,  179,  193,  177,  182,
   80,  185,   80,   80,   80,    2,  183,  188,   64,  169,
  291,    2,  170,  294,    2,  121,  149,   51,   80,   80,
   80,   80,  219,   39,   38,   52,   75,    2,   75,   75,
   75,    2,    2,  253,   20,   39,    2,   20,   65,   60,
    2,   88,   89,   90,   75,   75,   75,   75,   98,   64,
   88,   89,   90,  131,  160,  221,   98,   98,   98,   98,
   98,   98,   98,   98,   98,   12,   98,   64,   98,  196,
   98,   39,   98,   98,   98,   98,   98,   98,   98,   65,
   39,   98,   90,   90,   90,   90,   90,   90,   90,   90,
   90,   66,   90,  195,   90,  206,   90,   65,   90,   90,
   90,   90,   90,   90,   90,   39,   12,   90,   77,   77,
   77,   77,   77,   77,   77,   77,   77,  209,   77,   28,
   77,  210,   77,  231,   77,   77,   77,   77,   77,   77,
   77,  304,   66,   77,  149,  232,  233,   82,   82,   82,
   82,   82,   82,   82,   82,   82,   16,   82,  234,   82,
   66,   82,  235,   82,   82,   82,   82,   82,   82,   82,
   28,  242,   82,   81,   81,   81,   81,   81,   81,   81,
   81,   81,   17,   81,  244,   81,  246,   81,   28,   81,
   81,   81,   81,   81,   81,   81,  255,   16,   81,   76,
   76,   76,   76,   76,   76,   76,   76,   76,  207,   76,
   94,   76,   93,   76,  256,   76,   76,   76,   76,   76,
   76,   76,  257,   17,   76,   80,   80,   80,   80,   80,
   80,   80,   80,   80,  126,   80,  260,   80,   98,   80,
  261,   80,   80,   80,   80,   80,   80,   80,  265,  267,
   80,   75,   75,   75,   75,   75,   75,   75,   75,   75,
   27,   75,  282,   75,  295,   75,  175,   75,   75,   75,
   75,   75,   75,   75,   64,   64,   75,   64,  297,   64,
   64,   64,   64,  298,   64,  118,   64,  299,   64,   41,
   64,   64,  303,  302,  305,   64,   64,  112,  208,   64,
   94,   27,   93,   74,   65,   65,   74,   65,  306,   65,
   65,   65,   65,  243,   65,   94,   65,   93,   65,   27,
   65,   65,  307,  308,  309,   65,   65,   43,    6,   65,
    4,   12,   12,   91,   95,   92,   12,   50,   12,   12,
   41,   12,  152,   12,   72,   12,   41,   12,   12,   82,
   41,  218,   12,   12,    0,    0,   12,   66,   66,    0,
   66,    0,   66,   66,   66,   66,    0,   66,  106,   66,
    0,   66,   41,   66,   66,    0,    0,    0,   66,   66,
   20,    0,   66,   20,   72,   28,   28,   72,    0,    0,
   28,    0,   28,   28,   98,   28,  275,   28,   94,   28,
   93,   28,   28,   72,   72,   72,   28,   28,    0,    0,
   28,   83,   16,   16,   41,   82,   41,   16,   41,   16,
   16,    0,   16,   44,   16,    0,   16,    0,   16,   16,
    0,   41,    0,   16,   16,    0,    0,   16,   17,   17,
    0,    0,    0,   17,    0,   17,   17,    0,   17,  125,
   17,   44,   17,    0,   17,   17,    0,   84,    0,   17,
   17,    2,    0,   17,    0,   74,   41,  109,   74,    0,
   13,   14,  119,    0,   15,  271,    0,    0,    0,   82,
   41,    5,    6,    0,   74,   74,   74,  130,    7,    8,
   94,    9,   93,    2,    0,   10,    0,   11,   12,  145,
   38,    0,   13,   14,  154,    2,   15,   91,   95,   92,
    0,   39,    2,  159,   13,   14,   27,   27,   15,    0,
    0,   27,    0,   27,   27,    0,   27,    0,   27,    0,
   27,    0,   27,   27,    0,    4,   16,   27,   27,   16,
    0,   27,    0,   54,    0,    0,   58,   88,   89,   90,
    0,   38,   16,    0,    0,   73,    0,  151,   73,   65,
    0,   38,   39,    2,    0,  202,  203,    0,   39,    2,
   16,   16,   39,    2,   73,   73,   73,    0,   13,   14,
    0,    0,   15,  104,    6,  107,  113,  113,  113,    0,
    7,    8,    0,    9,   39,    2,  105,   10,    0,   11,
   12,   65,  238,  127,   13,   14,    0,   65,   15,   16,
   33,   71,    0,   33,   71,    0,    0,   72,   72,   72,
    0,    0,  249,  245,    0,  155,   33,   38,    0,  157,
   71,   71,   71,   67,    0,   65,   39,    2,   39,    2,
   39,    2,  162,   16,   33,   33,    0,  113,    0,  181,
    0,  266,  113,   39,    2,  279,    0,    0,   65,  111,
    0,    0,  190,  192,   16,    0,  205,   79,   78,   65,
   77,    0,   80,   79,   78,  124,   77,  164,   80,    0,
    0,   33,    0,   33,  289,    0,    0,    0,   39,    2,
   37,   38,   79,   78,    0,   77,    0,   80,   74,   74,
   74,   65,   39,    2,   65,   81,    0,    0,    0,   33,
   20,    0,    0,   20,  224,  225,    0,   33,  227,    0,
    0,   88,   89,   90,    0,    0,   20,    0,   16,    0,
    0,    0,  187,   65,    0,  136,    0,    0,   33,    0,
    0,    0,    0,   33,   20,   20,    0,   65,   65,    0,
    0,   65,    0,    0,    0,    0,    0,    0,  259,   20,
  156,  158,    0,  168,   93,   93,   93,    0,   93,    0,
   93,   81,    0,   65,    0,   33,    0,    0,   33,    0,
    0,    0,    0,   20,   65,    0,   65,    0,   73,   73,
   73,    0,    0,  226,    0,  228,  230,    0,    0,  236,
    6,    0,   33,    0,  237,    0,    7,   33,    0,    9,
    0,    2,    0,   10,   65,   11,   12,   20,   81,  247,
    6,   33,   33,    0,  248,   33,    7,    0,    0,    9,
    0,    2,  214,   10,    0,   11,   12,    0,   20,    0,
  264,    0,    0,    0,   71,   71,   71,   33,    0,    0,
    0,    0,  277,    6,    0,    0,    0,  278,   33,    7,
   33,  239,    9,    0,    2,    0,   10,    0,   11,   12,
    0,  283,  284,  285,    0,  239,  288,    0,    0,  214,
    0,   76,    0,    0,    0,    0,    0,   76,   33,    0,
    0,    0,   39,    2,    0,    0,    0,  301,   39,    2,
    0,  269,   20,   94,   94,   94,   76,   94,    0,   94,
    0,    0,  269,    0,  239,    5,    6,   39,    2,   35,
    0,    0,    7,    8,    0,    9,    0,    2,    0,   10,
    0,   11,   12,    0,    0,    0,   13,   14,    5,    6,
   15,    0,  269,   59,    0,    7,    8,    0,    9,    0,
    2,    0,   10,    0,   11,   12,    0,    5,    6,   13,
   14,    0,   73,   15,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    5,    6,   13,   14,
    0,   75,   15,    7,    8,    0,    9,    0,    2,    0,
   10,    0,   11,   12,    0,    5,    6,   13,   14,    0,
  140,   15,    7,    8,    0,    9,    0,    2,    0,   10,
    0,   11,   12,    0,    5,    6,   13,   14,    0,    0,
   15,    7,    8,    0,    9,    0,    2,    0,   10,    0,
   11,   12,    0,    0,    0,   13,   14,    0,    0,   15,
  120,  120,  120,    0,  120,    0,    0,  120,    0,    0,
  120,    0,  120,    0,  120,    0,  120,  120,  119,  119,
  119,    0,  119,    0,    0,  119,    0,    0,  119,    0,
  119,    0,  119,    0,  119,  119,  118,  118,  118,    0,
  118,    0,    0,  118,    0,    0,  118,    0,  118,    0,
  118,    0,  118,  118,  115,  115,  115,    0,  115,    0,
    0,  115,    0,    0,  115,    0,  115,    0,  115,    0,
  115,  115,  114,  114,  114,    0,  114,    0,    0,  114,
    0,    0,  114,    0,  114,    0,  114,    0,  114,  114,
  133,    6,    0,    0,  134,    0,    0,    7,    0,    0,
    9,    0,    2,    0,   10,    6,   11,   12,  166,    0,
  167,    7,    0,    0,    9,    0,    2,    0,   10,    6,
   11,   12,  213,    0,  252,    7,    0,    6,    9,    0,
    2,  197,   10,    7,   11,   12,    9,    0,    2,    0,
   10,    6,   11,   12,    0,  211,    0,    7,    0,    0,
    9,    0,    2,    0,   10,    6,   11,   12,  213,    0,
    0,    7,    0,    6,    9,    0,    2,  250,   10,    7,
   11,   12,    9,    0,    2,    0,   10,    6,   11,   12,
    0,  268,    0,    7,    0,    6,    9,    0,    2,  276,
   10,    7,   11,   12,    9,    0,    2,    0,   10,    6,
   11,   12,    0,  296,    0,    7,    0,    0,    9,    0,
    2,    0,   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         62,
    0,   45,  123,   44,   41,   41,   60,   40,   40,   59,
   41,   42,   43,   45,   45,  123,   47,   41,   59,   59,
   44,   68,   83,   44,   98,   99,    0,   60,   61,   62,
   59,   45,   45,   45,   62,  123,   60,   61,   62,   84,
   87,   41,   42,   43,   44,   45,  256,   47,   44,   45,
   42,  166,    0,   59,  256,   47,  260,  267,   45,   59,
   60,   61,   62,   17,  268,  268,  268,   41,   42,   43,
   44,   45,    4,   47,  277,  278,   40,  256,  281,  123,
   40,    0,   36,   45,  256,   59,   60,   61,   62,  268,
  262,   59,  123,   41,  273,   43,   44,   45,  213,  267,
   59,   42,  147,   35,  125,  150,   47,    0,   43,   59,
   45,   59,   60,   61,   62,   41,   42,   43,   50,   45,
   74,   47,   41,  123,   43,   44,   45,  265,  256,  125,
  268,  259,   40,    0,  262,   51,   52,   53,  125,   40,
   59,   60,   61,   62,  123,  258,   41,  268,   41,   44,
   43,   44,   45,  279,  108,  200,  277,  278,  268,    0,
  281,   41,  260,   43,  256,   45,   59,   60,   61,   62,
   41,  279,   41,   44,   41,   44,   43,   44,   45,  240,
  241,  242,   41,  244,   43,    0,   45,   41,  256,  256,
   44,  279,   59,   60,   61,   62,  112,  129,   41,  123,
   41,  117,   43,   44,   45,  268,   62,   41,    0,  259,
  271,  268,  262,   41,  268,  256,   44,  271,   59,   60,
   61,   62,  262,  267,  256,  279,   41,  268,   43,   44,
   45,  268,  268,  262,  265,  267,  268,  268,    0,  193,
  268,  274,  275,  276,   59,   60,   61,   62,  279,   41,
  274,  275,  276,  267,  267,  267,  256,  257,  258,  259,
  260,  261,  262,  263,  264,    0,  266,   59,  268,  267,
  270,  267,  272,  273,  274,  275,  276,  277,  278,   41,
  267,  281,  256,  257,  258,  259,  260,  261,  262,  263,
  264,    0,  266,  125,  268,   40,  270,   59,  272,  273,
  274,  275,  276,  277,  278,  267,   41,  281,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  125,  266,    0,
  268,  267,  270,   41,  272,  273,  274,  275,  276,  277,
  278,   41,   41,  281,   44,   41,  261,  256,  257,  258,
  259,  260,  261,  262,  263,  264,    0,  266,  279,  268,
   59,  270,  125,  272,  273,  274,  275,  276,  277,  278,
   41,   40,  281,  256,  257,  258,  259,  260,  261,  262,
  263,  264,    0,  266,   40,  268,  125,  270,   59,  272,
  273,  274,  275,  276,  277,  278,  262,   41,  281,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   41,  266,
   43,  268,   45,  270,  125,  272,  273,  274,  275,  276,
  277,  278,  267,   41,  281,  256,  257,  258,  259,  260,
  261,  262,  263,  264,   41,  266,   44,  268,  123,  270,
   44,  272,  273,  274,  275,  276,  277,  278,   44,  279,
  281,  256,  257,  258,  259,  260,  261,  262,  263,  264,
    0,  266,  125,  268,   41,  270,   41,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  281,  259,  125,  261,
  262,  263,  264,  125,  266,   41,  268,  125,  270,   45,
  272,  273,   41,  125,   41,  277,  278,   60,   41,  281,
   43,   41,   45,   41,  256,  257,   44,  259,   41,  261,
  262,  263,  264,   41,  266,   43,  268,   45,  270,   59,
  272,  273,   41,  125,   41,  277,  278,   40,    0,  281,
    0,  256,  257,   60,   61,   62,  261,  261,  263,  264,
   45,  266,   41,  268,   34,  270,   45,  272,  273,   44,
   45,  171,  277,  278,   -1,   -1,  281,  256,  257,   -1,
  259,   -1,  261,  262,  263,  264,   -1,  266,   41,  268,
   -1,  270,   45,  272,  273,   -1,   -1,   -1,  277,  278,
  265,   -1,  281,  268,   41,  256,  257,   44,   -1,   -1,
  261,   -1,  263,  264,  279,  266,   41,  268,   43,  270,
   45,  272,  273,   60,   61,   62,  277,  278,   -1,   -1,
  281,   40,  256,  257,   45,   44,   45,  261,   45,  263,
  264,   -1,  266,    6,  268,   -1,  270,   -1,  272,  273,
   -1,   45,   -1,  277,  278,   -1,   -1,  281,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,   -1,  266,  256,
  268,   34,  270,   -1,  272,  273,   -1,   40,   -1,  277,
  278,  268,   -1,  281,   -1,   41,   45,   50,   44,   -1,
  277,  278,   55,   -1,  281,   40,   -1,   -1,   -1,   44,
   45,  256,  257,   -1,   60,   61,   62,   70,  263,  264,
   43,  266,   45,  268,   -1,  270,   -1,  272,  273,   82,
  256,   -1,  277,  278,   87,  268,  281,   60,   61,   62,
   -1,  267,  268,   96,  277,  278,  256,  257,  281,   -1,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,    0,    1,  277,  278,    4,
   -1,  281,   -1,    8,   -1,   -1,   11,  274,  275,  276,
   -1,  256,   17,   -1,   -1,   41,   -1,  256,   44,   24,
   -1,  256,  267,  268,   -1,  148,  149,   -1,  267,  268,
   35,   36,  267,  268,   60,   61,   62,   -1,  277,  278,
   -1,   -1,  281,  256,  257,   50,   51,   52,   53,   -1,
  263,  264,   -1,  266,  267,  268,  269,  270,   -1,  272,
  273,   66,   59,   68,  277,  278,   -1,   72,  281,   74,
    1,   41,   -1,    4,   44,   -1,   -1,  274,  275,  276,
   -1,   -1,   59,  206,   -1,  256,   17,  256,   -1,  256,
   60,   61,   62,   24,   -1,  100,  267,  268,  267,  268,
  267,  268,  256,  108,   35,   36,   -1,  112,   -1,  114,
   -1,  234,  117,  267,  268,   59,   -1,   -1,  123,   50,
   -1,   -1,  127,  128,  129,   -1,   41,   42,   43,  134,
   45,   -1,   47,   42,   43,   66,   45,  256,   47,   -1,
   -1,   72,   -1,   74,  267,   -1,   -1,   -1,  267,  268,
   59,  256,   42,   43,   -1,   45,   -1,   47,  274,  275,
  276,  166,  267,  268,  169,   38,   -1,   -1,   -1,  100,
    1,   -1,   -1,    4,  179,  180,   -1,  108,  183,   -1,
   -1,  274,  275,  276,   -1,   -1,   17,   -1,  193,   -1,
   -1,   -1,  123,  198,   -1,   72,   -1,   -1,  129,   -1,
   -1,   -1,   -1,  134,   35,   36,   -1,  212,  213,   -1,
   -1,  216,   -1,   -1,   -1,   -1,   -1,   -1,  223,   50,
   93,   94,   -1,  100,   41,   42,   43,   -1,   45,   -1,
   47,  104,   -1,  238,   -1,  166,   -1,   -1,  169,   -1,
   -1,   -1,   -1,   74,  249,   -1,  251,   -1,  274,  275,
  276,   -1,   -1,  182,   -1,  184,  185,   -1,   -1,  256,
  257,   -1,  193,   -1,  261,   -1,  263,  198,   -1,  266,
   -1,  268,   -1,  270,  279,  272,  273,  108,  151,  256,
  257,  212,  213,   -1,  261,  216,  263,   -1,   -1,  266,
   -1,  268,  169,  270,   -1,  272,  273,   -1,  129,   -1,
  229,   -1,   -1,   -1,  274,  275,  276,  238,   -1,   -1,
   -1,   -1,  256,  257,   -1,   -1,   -1,  261,  249,  263,
  251,  198,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  260,  261,  262,   -1,  212,  265,   -1,   -1,  216,
   -1,  256,   -1,   -1,   -1,   -1,   -1,  256,  279,   -1,
   -1,   -1,  267,  268,   -1,   -1,   -1,  286,  267,  268,
   -1,  238,  193,   41,   42,   43,  256,   45,   -1,   47,
   -1,   -1,  249,   -1,  251,  256,  257,  267,  268,  260,
   -1,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,   -1,  277,  278,  256,  257,
  281,   -1,  279,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,   -1,  261,  281,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,  256,  257,  277,  278,
   -1,  261,  281,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,  256,  257,  277,  278,   -1,
  261,  281,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,  256,  257,  277,  278,   -1,   -1,
  281,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  256,  257,   -1,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,
  272,  273,  260,   -1,  262,  263,   -1,  257,  266,   -1,
  268,  261,  270,  263,  272,  273,  266,   -1,  268,   -1,
  270,  257,  272,  273,   -1,  261,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
   -1,  263,   -1,  257,  266,   -1,  268,  261,  270,  263,
  272,  273,  266,   -1,  268,   -1,  270,  257,  272,  273,
   -1,  261,   -1,  263,   -1,  257,  266,   -1,  268,  261,
  270,  263,  272,  273,  266,   -1,  268,   -1,  270,  257,
  272,  273,   -1,  261,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,
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

//#line 330 "Gramatica.y"
	
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
//#line 1123 "Parser.java"
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
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}; cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
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
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); }
break;
case 19:
//#line 43 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 20:
//#line 46 "Gramatica.y"
{ if(tipos.containsKey(val_peek(0).sval)){yyval.obj = tipos.get(val_peek(0).sval);
					}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}
break;
case 21:
//#line 48 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 22:
//#line 51 "Gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 23:
//#line 53 "Gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 24:
//#line 55 "Gramatica.y"
{ if(!tipos.containsKey("OCTAL")){tipos.put("OCTAL",new Tipo("OCTAL"));}
							yyval.obj = tipos.get("OCTAL");}
break;
case 25:
//#line 59 "Gramatica.y"
{if(val_peek(5).obj != null){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
break;
case 26:
//#line 60 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango ");}
break;
case 27:
//#line 61 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango ");}
break;
case 28:
//#line 62 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango ");}
break;
case 29:
//#line 63 "Gramatica.y"
{if(val_peek(2).obj != null){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");}else{cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. ");}}
break;
case 30:
//#line 64 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior ");}
break;
case 31:
//#line 65 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos ");}
break;
case 32:
//#line 66 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior ");}
break;
case 33:
//#line 67 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos ");}
break;
case 34:
//#line 68 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido ");}
break;
case 35:
//#line 69 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo ");}
break;
case 36:
//#line 70 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE ");}
break;
case 37:
//#line 71 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE");}
break;
case 38:
//#line 72 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE");}
break;
case 39:
//#line 73 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE");}
break;
case 40:
//#line 74 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion");}
break;
case 41:
//#line 78 "Gramatica.y"
{	if(val_peek(1).sval!="RET"){cargarErrorEImprimirloSemantico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion ");}
									sacarAmbito();
									DENTRODELAMBITO.pop();
									cargarParametroFormal(val_peek(4).sval,(Tipo)val_peek(3).obj);									
								}
break;
case 42:
//#line 85 "Gramatica.y"
{yyval.sval=val_peek(0).sval;cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj,"nombre de funcion");agregarAmbito(val_peek(0).sval);DENTRODELAMBITO.push(val_peek(0).sval);GeneradorCodigoIntermedio.addNuevaPolaca();}
break;
case 43:
//#line 86 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion ");}
break;
case 44:
//#line 89 "Gramatica.y"
{yyval.obj=val_peek(2).obj; GeneradorCodigoIntermedio.addElemento(val_peek(1).sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}
break;
case 45:
//#line 90 "Gramatica.y"
{if(tipos.containsKey(val_peek(2).sval))
										{yyval.obj = tipos.get(val_peek(2).sval); GeneradorCodigoIntermedio.addElemento(val_peek(1).sval + AMBITO.toString()); GeneradorCodigoIntermedio.addElemento("PF");cargarVariables(val_peek(1).sval,tipos.get(val_peek(2).sval)," nombre de parametro real ");
										}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido ");};}
break;
case 46:
//#line 93 "Gramatica.y"
{yyval.obj=val_peek(1).obj;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion ");}
break;
case 47:
//#line 94 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion ");}
break;
case 48:
//#line 95 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion ");}
break;
case 49:
//#line 96 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). ");}
break;
case 50:
//#line 99 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 51:
//#line 102 "Gramatica.y"
{if(!existeFuncion()){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
										}else{
											GeneradorCodigoIntermedio.addElemento("RET");
										}}
break;
case 52:
//#line 106 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  ");
						if(!existeFuncion())
										{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  ");
						}}
break;
case 54:
//#line 114 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 57:
//#line 117 "Gramatica.y"
{if(fueDeclarado(val_peek(0).sval+AMBITO.toString())){cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : La ETIQUETA "+val_peek(0).sval+" ya existe  ");}else{cargarVariables(val_peek(0).sval,tipos.get("ETIQUETA"),"ETIQUETA");}GeneradorCodigoIntermedio.addEtiqueta(val_peek(0).sval+AMBITO.toString());GeneradorCodigoIntermedio.addElemento("LABEL"+val_peek(0).sval+AMBITO.toString());}
break;
case 59:
//#line 119 "Gramatica.y"
{yyval.sval="RET";}
break;
case 60:
//#line 122 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("OUTF");}
break;
case 61:
//#line 123 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
break;
case 62:
//#line 124 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("OUTF");}
break;
case 63:
//#line 125 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. ");}
break;
case 64:
//#line 128 "Gramatica.y"
{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;
															GeneradorCodigoIntermedio.addElemento(val_peek(2).sval+Parser.AMBITO.toString());
															GeneradorCodigoIntermedio.addElemento(":="); 
															}else{
																cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + val_peek(2).sval + "' no fue declarada");}
															}
break;
case 65:
//#line 135 "Gramatica.y"
{if(fueDeclarado(val_peek(5).sval)){
																		if(Integer.valueOf(val_peek(3).sval) <= 3){
																			yyval.sval = val_peek(5).sval;
																			GeneradorCodigoIntermedio.addElemento(val_peek(5).sval+Parser.AMBITO.toString());
																			GeneradorCodigoIntermedio.addElemento(val_peek(3).sval);
																			GeneradorCodigoIntermedio.addElemento("INDEX");
																			GeneradorCodigoIntermedio.addElemento(":="); 
																			}else{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");}														
																	}else{
																		cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La variable '" + val_peek(5).sval + "' no fue declarada");}}
break;
case 66:
//#line 145 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 67:
//#line 148 "Gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + val_peek(3).sval + "' no fue declarada");}
													else{	
														GeneradorCodigoIntermedio.invocar(val_peek(3).sval+AMBITO.toString());																																																		
												}}
break;
case 68:
//#line 154 "Gramatica.y"
{if(!fueDeclarado(val_peek(6).sval)){
													cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La funcion '" + val_peek(6).sval + "' no fue declarada");}
													else{
														GeneradorCodigoIntermedio.invocar(val_peek(6).sval+AMBITO.toString(), ((Tipo)val_peek(4).obj).getType());
												}}
break;
case 69:
//#line 159 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion");}
break;
case 70:
//#line 160 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)");}
break;
case 71:
//#line 164 "Gramatica.y"
{yyval.ival=val_peek(2).ival + 1;GeneradorCodigoIntermedio.addElemento(",");}
break;
case 72:
//#line 165 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 73:
//#line 166 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  ");}
break;
case 74:
//#line 167 "Gramatica.y"
{yyval.ival=1;GeneradorCodigoIntermedio.addElemento(",");}
break;
case 75:
//#line 170 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("+"); }
break;
case 76:
//#line 171 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("-"); }
break;
case 78:
//#line 173 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 79:
//#line 174 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos");}
break;
case 80:
//#line 175 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 81:
//#line 176 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha");}
break;
case 82:
//#line 177 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador");}
break;
case 83:
//#line 180 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("*");}
break;
case 84:
//#line 181 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento("/");}
break;
case 86:
//#line 183 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 87:
//#line 184 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 88:
//#line 185 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 89:
//#line 186 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita");}
break;
case 90:
//#line 189 "Gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){GeneradorCodigoIntermedio.addElemento(val_peek(0).sval+Parser.AMBITO.toString());AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+val_peek(0).sval+ "' no fue declarada");};}
break;
case 91:
//#line 190 "Gramatica.y"
{GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 93:
//#line 192 "Gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ 
											if(Integer.valueOf(val_peek(1).sval) <= 3){
												GeneradorCodigoIntermedio.addElemento(val_peek(3).sval+Parser.AMBITO.toString());
												GeneradorCodigoIntermedio.addElemento(val_peek(1).sval);
												GeneradorCodigoIntermedio.addElemento("INDEX");
												AnalizadorLexico.TablaDeSimbolos.get(val_peek(3).sval).incrementarContDeRef(); yyval.sval = val_peek(3).sval;
											}else{
												cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Tripla fuera de rango ");
											}
										}else{cargarErrorEImprimirloSemantico("Linea :" + AnalizadorLexico.saltoDeLinea +  " Error: La variable '"+val_peek(3).sval+ "' no fue declarada");};}
break;
case 94:
//#line 202 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo ");}
break;
case 95:
//#line 204 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 96:
//#line 205 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables ");}
break;
case 97:
//#line 206 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 100:
//#line 216 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 101:
//#line 217 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 102:
//#line 220 "Gramatica.y"
{if(val_peek(4).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};completarBifurcacionI();}
break;
case 103:
//#line 221 "Gramatica.y"
{yyval.sval=val_peek(2).sval;completarBifurcacionISinElse();}
break;
case 104:
//#line 222 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN.");}
break;
case 105:
//#line 223 "Gramatica.y"
{{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE ");};}
break;
case 106:
//#line 226 "Gramatica.y"
{if(val_peek(3).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN ");}
break;
case 107:
//#line 227 "Gramatica.y"
{yyval.sval=val_peek(1).sval;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  ");}
break;
case 108:
//#line 228 "Gramatica.y"
{if(val_peek(3).sval=="RET" && val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  ");}
break;
case 109:
//#line 229 "Gramatica.y"
{if(val_peek(2).sval=="RET" && val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF ");}
break;
case 110:
//#line 231 "Gramatica.y"
{yyval.sval=val_peek(2).sval;cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  ");}
break;
case 111:
//#line 232 "Gramatica.y"
{if(val_peek(4).sval=="RET" && val_peek(2).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF ");}
break;
case 112:
//#line 235 "Gramatica.y"
{if(val_peek(6).ival == val_peek(2).ival){cantDeOperandos=val_peek(6).ival;modificarPolacaPM(val_peek(4).sval,val_peek(6).ival);}else{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Cantidad de operandor incompatibles en la comparacion ");}}
break;
case 113:
//#line 236 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 114:
//#line 237 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 115:
//#line 238 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 116:
//#line 239 "Gramatica.y"
{cantDeOperandos=1;opCondicion(val_peek(2).sval);}
break;
case 117:
//#line 240 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion ");}
break;
case 118:
//#line 241 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion ");}
break;
case 119:
//#line 242 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion ");}
break;
case 120:
//#line 244 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion ");}
break;
case 121:
//#line 246 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador ");}
break;
case 122:
//#line 247 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones ");}
break;
case 123:
//#line 248 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador");}
break;
case 124:
//#line 251 "Gramatica.y"
{yyval.sval=">";}
break;
case 125:
//#line 252 "Gramatica.y"
{yyval.sval=">=";}
break;
case 126:
//#line 253 "Gramatica.y"
{yyval.sval="<";}
break;
case 127:
//#line 254 "Gramatica.y"
{yyval.sval="<=";}
break;
case 128:
//#line 255 "Gramatica.y"
{yyval.sval="=";}
break;
case 129:
//#line 256 "Gramatica.y"
{yyval.sval="!=";}
break;
case 130:
//#line 259 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 131:
//#line 260 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 132:
//#line 263 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 133:
//#line 264 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 134:
//#line 265 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 135:
//#line 266 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del ELSE");}
break;
case 136:
//#line 270 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 137:
//#line 273 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}
break;
case 138:
//#line 274 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};operacionesIF();}
break;
case 139:
//#line 277 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 140:
//#line 278 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 141:
//#line 279 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 142:
//#line 280 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias del THEN");}
break;
case 143:
//#line 283 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 144:
//#line 287 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 145:
//#line 288 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 146:
//#line 292 "Gramatica.y"
{if(val_peek(2).sval=="RET"){yyval.sval="RET";};}
break;
case 147:
//#line 293 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias ");}
break;
case 148:
//#line 294 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 149:
//#line 295 "Gramatica.y"
{if(val_peek(1).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END al final de las sentencias");}
break;
case 150:
//#line 298 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";}}
break;
case 151:
//#line 301 "Gramatica.y"
{if(val_peek(2).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 152:
//#line 302 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 153:
//#line 303 "Gramatica.y"
{if(val_peek(1).sval=="RET" || val_peek(0).sval=="RET"){yyval.sval="RET";};cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia ");}
break;
case 154:
//#line 306 "Gramatica.y"
{if(val_peek(0).sval=="RET"){yyval.sval="RET";};}
break;
case 155:
//#line 309 "Gramatica.y"
{cargarCadenaMultilinea(val_peek(0).sval);GeneradorCodigoIntermedio.addElemento(val_peek(0).sval);}
break;
case 156:
//#line 314 "Gramatica.y"
{operacionesWhile();}
break;
case 157:
//#line 315 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE ");}
break;
case 158:
//#line 318 "Gramatica.y"
{GeneradorCodigoIntermedio.apilar(GeneradorCodigoIntermedio.getPos());GeneradorCodigoIntermedio.addElemento("LABEL"+GeneradorCodigoIntermedio.getPos());}
break;
case 159:
//#line 322 "Gramatica.y"
{GeneradorCodigoIntermedio.addBaulDeGotos(val_peek(0).sval+AMBITO.toString()+"/"+AMBITO.toString()+"/"+String.valueOf(GeneradorCodigoIntermedio.getPos()));}
break;
case 160:
//#line 323 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta el caracter '@' de la etiqueta. ");}
break;
case 161:
//#line 324 "Gramatica.y"
{cargarErrorEImprimirloSintactico("Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO ");}
break;
//#line 1908 "Parser.java"
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
