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
   34,   34,   33,   30,   30,   38,   38,   38,   38,   37,
   35,   35,   35,   36,   23,   20,   20,   21,   21,
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
    5,    4,    2,    1,    1,    4,    2,    3,    4,    1,
    3,    1,    2,    1,    1,    3,    3,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   97,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   22,   23,    0,    0,    8,    9,    0,   13,
    0,    0,    0,   21,    0,   57,   51,   52,   53,   54,
   56,    0,    0,    0,   10,    0,   98,    0,    0,    0,
   89,    0,    0,   90,    0,   83,    0,    0,    0,    0,
    0,    0,    0,    0,  149,  148,    3,    7,   11,   14,
   15,    0,   96,    0,   95,    0,    0,    0,    0,    2,
    0,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   99,    0,  124,  126,  128,  125,  123,    0,
    0,  127,    0,    0,    0,    0,    0,    0,  145,   59,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,   50,    0,  147,    0,  144,  146,  140,  134,  135,
   41,   19,   18,    0,   94,    0,   46,    0,    0,    0,
    0,    0,    0,    1,   77,   76,   84,   85,    0,    0,
    0,    0,    0,    0,    0,   67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   86,   81,   87,   82,    0,
  103,    0,    0,  129,  130,   61,   58,   60,    0,    0,
    0,   38,    0,    0,    0,    0,   49,  137,    0,  142,
   93,   47,   45,   44,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,   65,  116,   91,    0,
    0,  133,  106,    0,    0,    0,    0,    0,    0,   37,
   36,    0,   35,    0,    0,    0,  138,    0,  143,   43,
   40,    0,    0,    0,    0,    0,  115,    0,    0,   92,
    0,  109,    0,  101,    0,  108,    0,  102,    0,    0,
   39,   28,    0,    0,    0,   32,    0,    0,  139,  136,
  141,    0,    0,    0,    0,    0,    0,    0,    0,  132,
    0,  104,  107,    0,  105,    0,    0,    0,    0,    0,
   30,    0,    0,    0,    0,    0,    0,    0,   66,  131,
  110,  100,   33,   34,   29,   31,    0,   25,  120,    0,
  122,  121,  112,   24,  111,
};
final static short yydgoto[] = {                          3,
   40,   16,   17,   18,   19,   20,   21,   22,   23,   64,
   24,   41,   25,   67,  187,   26,  140,   27,   28,   29,
   30,   31,  104,   43,   44,   82,   45,   46,   47,  117,
  163,   93,  164,  165,  179,  118,  119,  120,
};
final static short yysindex[] = {                      -166,
  922,    0,    0,  802,   18,  467,   61,  231,   65,  467,
 -218,    0,    0,    0,    0,  849,    0,    0,   38,    0,
   51,   63, -148,    0,   86,    0,    0,    0,    0,    0,
    0, -106,  867,  886,    0,  826,    0,  508, -126,  108,
    0,  739,   35,    0,    9,    0,  -88,  340,  554, -135,
 -135, -100,  570, 1035,    0,    0,    0,    0,    0,    0,
    0,  -87,    0,  -23,    0,  -41,  -65,  621,  -32,    0,
  904,    0,   57, -167,  -59,  -38,   10,    9,  621,  577,
  739,  127,    0,  -37,    0,    0,    0,    0,    0,  630,
  634,    0,  621,  -18,  639,  648, 1060,  738,    0,    0,
  108,  298,  148,  155, -105, -135,    0,  -60,   81,  158,
  -66,    0,  271,    0, 1082,    0,    0,    0,    0,    0,
    0,    0,    0,    2,    0,  198,    0,  248,  -26,  922,
  -17,  170,   68,    0,    0,    0,    0,    0,  -17,  -17,
  -21,  621,  621,  822,  607,    0,  278,  317,   57,    9,
   57,    9,  365,  212,   74,    0,    0,    0,    0, 1096,
    0,  -45,  305,    0,    0,    0,    0,    0,  -16,  -52,
    2,    0,    5,    2,    5,  -42,    0,    0,  696,    0,
    0,    0,    0,    0,  342,  922,  126,  164,  325,  -28,
  414,  386,  -17,  424,    0,  621,    0,    0,    0,  344,
 -145,    0,    0,  -90,  -34,  205,  359,  222, -219,    0,
    0,  446,    0,  453,  416,  454,    0, 1043,    0,    0,
    0,  621,  225,  577,  540,  577,    0,  577,  460,    0,
  771,    0, 1074,    0,   -6,    0,  244,    0,  164,  389,
    0,    0,    5,    5,    5,    0,  -22,    5,    0,    0,
    0,  -17,  621,  169,  577,  415,  439,  444,  478,    0,
 1104,    0,    0, -204,    0,  225,  396,  402,  406,  -29,
    0,  409,  -17,  496,  450,  498,  501,  503,    0,    0,
    0,    0,    0,    0,    0,    0,  420,    0,    0,  506,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  423,    0,    0,    0,  252,    0,
  404,  495,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  549,    0,    0,    0,    0,    0,    1,
    0,    0,   24,    0,   47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  510,    0,    0,    0,    0,    0,    0,    0,    0,
  551,    0,    0,    0,    0,    0,    0,   70,    0,    0,
  482,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -36,    0,    0,    0,  104,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  185,    0,    0,    0,    0,    0,    0,    0,  538,  575,
    0,    0,  579,  945,    0,    0,    0,    0,   93,  116,
  139,  162,  963,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  295,    0,    0,    0,    0,
    0,  981,  585,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  145,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  209,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  547,    0,    0,    0,    0,
    0,  452,  433,    0,    0,    0,    0,  999,    0,    0,
    0,    0,    0,    0,    0,    0,  476,    0,    0, 1017,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  770,   26,   48,    0,  885,    0,    0,    0,   29,    0,
  -12,  808,    0,    0,    0,    0,  490,    0,    0,    0,
    0,    0,    0,  794,    0,  -61,  442,  -20,  550,  462,
 -131,  -46,    0,    0,  356,  732,    0,    0,
};
final static int YYTABLESIZE=1377;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        127,
   96,  171,   39,  146,   96,   96,   96,   39,   96,  209,
   96,  224,  133,  204,  184,   39,   69,  169,  141,  190,
  124,  270,  143,   88,  237,   91,  155,   90,  208,   34,
  205,   88,   92,   89,  142,  123,  241,   55,   88,   92,
   89,   96,   96,   96,   96,   96,   75,   96,    2,   39,
   95,  281,  264,  129,   56,   96,  175,  282,   71,   96,
   96,   96,   96,   58,   88,   88,   88,   88,   88,   80,
   88,  147,  235,  102,  157,  159,   35,  108,  109,  110,
  215,   58,   88,   88,   88,   88,   96,   75,  135,   75,
   75,   75,   79,    1,  191,  286,   59,  194,   76,   83,
   48,    2,  271,   77,   53,   75,   75,   75,   75,   60,
   80,    6,   80,   80,   80,   74,   62,    7,   58,    2,
    9,   61,    2,   96,   10,   66,   11,   12,   80,   80,
   80,   80,    2,   79,  170,   79,   79,   79,   78,  176,
   83,   13,   14,  225,   88,   88,   88,   84,   88,   58,
   88,   79,   79,   79,   79,  186,   74,   94,   74,   74,
   74,   73,  254,  256,  257,  232,  258,  144,  233,   97,
  143,  234,   68,   68,   74,   74,   74,   74,  111,   78,
  121,   78,   78,   78,   62,   91,   91,   91,  167,   91,
   91,   91,   90,  275,  130,  168,  136,   78,   78,   78,
   78,    2,   73,  173,   73,   73,   73,    2,   63,  274,
   13,   14,  143,  160,  126,    2,  203,  137,  145,  174,
   73,   73,   73,   73,   37,   62,    2,  236,   20,   37,
    2,   20,  122,   58,  132,   13,   14,   37,  182,   13,
   14,    2,   96,   62,    2,   85,   86,   87,  154,   63,
  207,   12,   85,   86,   87,  263,   96,   96,   96,   96,
   96,   96,   96,   96,   96,  138,   96,   63,   96,    2,
   96,   37,   96,   96,   96,   96,   96,   96,   96,   88,
   88,   88,   88,   88,   88,   88,   88,   88,  183,   88,
   51,   88,   12,   88,  188,   88,   88,   88,   88,   88,
   88,   88,   75,   75,   75,   75,   75,   75,   75,   75,
   75,  177,   75,   91,   75,   90,   75,  196,   75,   75,
   75,   75,   75,   75,   75,   80,   80,   80,   80,   80,
   80,   80,   80,   80,  189,   80,  199,   80,  166,   80,
  200,   80,   80,   80,   80,   80,   80,   80,   79,   79,
   79,   79,   79,   79,   79,   79,   79,  197,   79,   91,
   79,   90,   79,  206,   79,   79,   79,   79,   79,   79,
   79,   74,   74,   74,   74,   74,   74,   74,   74,   74,
  100,   74,  220,   74,   39,   74,  221,   74,   74,   74,
   74,   74,   74,   74,   78,   78,   78,   78,   78,   78,
   78,   78,   78,   16,   78,  198,   78,   91,   78,   90,
   78,   78,   78,   78,   78,   78,   78,   73,   73,   73,
   73,   73,   73,   73,   73,   73,  227,   73,   91,   73,
   90,   73,   64,   73,   73,   73,   73,   73,   73,   73,
   62,   62,  222,   62,   16,   62,   62,   62,   62,  223,
   62,   27,   62,  226,   62,  276,   62,   62,  143,  245,
   39,   62,   62,  228,   63,   63,  238,   63,  230,   63,
   63,   63,   63,   64,   63,   26,   63,   78,   63,  277,
   63,   63,  143,  239,  278,   63,   63,  143,  240,  243,
  290,   64,   27,  143,   17,   42,  244,  248,    2,   42,
  259,   49,   91,  253,   90,  265,   38,   12,   12,   50,
   27,   39,   12,  266,   12,   12,   26,   12,  279,   12,
  283,   12,   72,   12,   12,   72,  284,   81,   12,   12,
  285,  150,  152,  288,   26,   17,  289,  103,  291,   78,
  246,  292,  113,  293,  294,   96,  295,   80,    6,   42,
    4,   79,   39,    5,    6,   48,  231,  131,  162,   54,
    7,    8,    0,    9,    0,    2,    0,   10,  139,   11,
   12,    0,    0,  148,   13,   14,    0,    0,   70,  255,
    0,   70,  153,   79,   39,    0,   78,   92,   92,   92,
    0,   92,    0,   92,    0,   98,    6,   70,   70,   70,
    0,    0,    7,    8,    0,    9,   37,    2,   99,   10,
  112,   11,   12,  106,   39,   72,   13,   14,   72,   71,
   79,   39,   71,    0,    0,   69,    0,    0,   69,    0,
    0,  192,  193,    0,   72,   72,   72,    0,   71,   71,
   71,    0,    0,    0,   69,   69,   69,  195,   76,   75,
    0,   74,    0,   77,    0,    0,    0,    0,    0,   16,
   16,    0,    0,    0,   16,   39,   16,   16,    0,   16,
    0,   16,    0,   16,   39,   16,   16,    0,   39,    0,
   16,   16,   37,   39,    0,  229,    0,   20,   64,   64,
   20,   64,   39,   64,   64,   64,   64,    0,   64,    0,
   64,   96,   64,    0,   64,   64,    0,   27,   27,   64,
   64,  252,   27,    0,   27,   27,    0,   27,    0,   27,
    0,   27,   36,   27,   27,    0,    0,    0,   27,   27,
    0,   26,   26,   37,    2,    0,   26,    0,   26,   26,
    0,   26,  273,   26,    0,   26,    0,   26,   26,    0,
   17,   17,   26,   26,  218,   17,    0,   17,   17,    0,
   17,    0,   17,   36,   17,    0,   17,   17,    0,    4,
   15,   17,   17,   15,   37,    2,    0,   52,    0,   76,
   75,   91,   74,   90,   77,   15,    0,    0,    0,    0,
    0,    0,   63,    0,   32,   36,   35,   32,   88,   92,
   89,    0,   15,   15,    0,    0,   37,    2,    0,   32,
    0,   70,   70,   70,    0,    0,   65,  101,  107,  107,
  107,    2,    0,   63,    0,   36,   32,   32,    0,  261,
   13,   14,   36,   63,    0,  128,   37,    2,    0,    0,
   15,  105,    0,   37,    2,    0,  180,   32,   72,   72,
   72,    0,   71,   71,   71,    0,    0,  125,   69,   69,
   69,    0,   73,    0,   32,    0,   63,   76,   75,    0,
   74,   15,   77,   37,    2,  107,   36,  172,    0,    0,
  107,   88,   92,   89,   63,  149,    0,   37,    2,  151,
   32,  202,    0,   63,  156,   32,   37,    2,  185,   15,
   37,    2,    0,  158,    0,   37,    2,    0,   32,    0,
  219,    0,    0,    0,   37,    2,    0,  181,    0,    0,
    0,    0,    0,   32,    0,    0,    0,    0,    0,   63,
    0,    0,  180,    0,    0,    0,    0,    0,  116,  210,
  211,    0,    0,  213,    0,    0,    0,    0,   63,  251,
    0,    0,    6,   32,    0,   15,  217,    0,    7,    0,
    0,    9,  219,    2,  202,   10,    0,   11,   12,    0,
   63,    0,   32,    0,    0,    0,    0,    0,  242,   32,
  212,  116,  214,  216,    0,    0,    0,   63,    0,    0,
    0,    0,  251,   73,   32,    0,    0,    0,    0,  116,
   63,    0,   63,    0,   37,    2,    0,    0,    0,    0,
    0,   32,   85,   86,   87,    0,    0,    0,    0,    0,
    0,    0,  247,    0,   32,    0,   32,    6,    0,    0,
   63,  260,    0,    7,    0,    0,    9,    0,    2,    0,
   10,    0,   11,   12,  116,    0,    0,    0,    0,    0,
  267,  268,  269,    0,   32,  272,    0,    5,    6,    0,
    0,   33,    0,  116,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    0,  287,   13,   14,
    0,   73,    0,    0,    0,  116,    0,    0,    0,    0,
    0,    0,   37,    2,    0,   85,   86,   87,    0,    0,
    0,    0,  116,    0,    5,    6,    0,    0,    0,   57,
    0,    7,    8,    0,    9,  116,    2,  116,   10,    0,
   11,   12,    5,    6,    0,   13,   14,   70,    0,    7,
    8,    0,    9,    0,    2,    0,   10,    0,   11,   12,
    0,    5,    6,   13,   14,  116,   72,    0,    7,    8,
    0,    9,    0,    2,    0,   10,    0,   11,   12,    5,
    6,    0,   13,   14,  134,    0,    7,    8,    0,    9,
    0,    2,    0,   10,    0,   11,   12,    5,    6,    0,
   13,   14,    0,    0,    7,    8,    0,    9,    0,    2,
    0,   10,    0,   11,   12,    0,    0,    0,   13,   14,
  119,  119,  119,    0,  119,    0,    0,  119,    0,    0,
  119,    0,  119,    0,  119,    0,  119,  119,  118,  118,
  118,    0,  118,    0,    0,  118,    0,    0,  118,    0,
  118,    0,  118,    0,  118,  118,  117,  117,  117,    0,
  117,    0,    0,  117,    0,    0,  117,    0,  117,    0,
  117,    0,  117,  117,  114,  114,  114,    0,  114,    0,
    0,  114,    0,    0,  114,    0,  114,    0,  114,    0,
  114,  114,  113,  113,  113,    0,  113,    0,    0,  113,
    0,    0,  113,    0,  113,    0,  113,    0,  113,  113,
  114,    6,    0,    0,  115,    0,    0,    7,  249,    6,
    9,    0,    2,  250,   10,    7,   11,   12,    9,    0,
    2,    0,   10,    0,   11,   12,    6,    0,  160,  115,
    0,  161,    7,    0,    0,    9,    0,    2,    0,   10,
    6,   11,   12,  201,    0,  262,    7,    0,    6,    9,
    0,    2,  178,   10,    7,   11,   12,    9,    0,    2,
    0,   10,    6,   11,   12,  201,    0,    0,    7,    0,
    6,    9,    0,    2,  280,   10,    7,   11,   12,    9,
    0,    2,    0,   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   62,   45,   41,   41,   42,   43,   45,   45,   62,
   47,   40,   45,   59,   41,   45,  123,  123,   80,   41,
   44,   44,   44,    0,   59,   43,   45,   45,   45,    4,
  162,   60,   61,   62,   81,   59,  256,  256,   60,   61,
   62,   41,   42,   43,   44,   45,    0,   47,  268,   45,
   42,  256,   59,   66,  273,   47,  123,  262,   33,   59,
   60,   61,   62,   16,   41,   42,   43,   44,   45,    0,
   47,   84,  204,   48,   95,   96,   59,   49,   50,   51,
  123,   34,   59,   60,   61,   62,  123,   41,  256,   43,
   44,   45,    0,  260,  141,  125,   59,  144,   42,  267,
   40,  268,  125,   47,   40,   59,   60,   61,   62,   59,
   41,  257,   43,   44,   45,    0,  265,  263,   71,  268,
  266,   59,  268,  123,  270,   40,  272,  273,   59,   60,
   61,   62,  268,   41,  106,   43,   44,   45,    0,  111,
  267,  277,  278,  190,   41,   42,   43,   40,   45,  102,
   47,   59,   60,   61,   62,  130,   41,  123,   43,   44,
   45,    0,  224,  225,  226,  256,  228,   41,  259,  258,
   44,  262,  279,  279,   59,   60,   61,   62,  279,   41,
  268,   43,   44,   45,    0,   41,   42,   43,   41,   45,
   43,   47,   45,  255,  260,   41,  256,   59,   60,   61,
   62,  268,   41,  123,   43,   44,   45,  268,    0,   41,
  277,  278,   44,  259,  256,  268,  262,  256,  256,   62,
   59,   60,   61,   62,  267,   41,  268,  262,  265,  267,
  268,  268,  256,  186,  267,  277,  278,  267,   41,  277,
  278,  268,  279,   59,  268,  274,  275,  276,  267,   41,
  267,    0,  274,  275,  276,  262,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  256,  266,   59,  268,  268,
  270,  267,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   41,  266,
   60,  268,   41,  270,  125,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,  259,  260,  261,  262,  263,
  264,   41,  266,   43,  268,   45,  270,   40,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  264,  267,  266,  125,  268,   41,  270,
  267,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   41,  266,   43,
  268,   45,  270,   59,  272,  273,  274,  275,  276,  277,
  278,  256,  257,  258,  259,  260,  261,  262,  263,  264,
   41,  266,   41,  268,   45,  270,  261,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  258,  259,  260,  261,
  262,  263,  264,    0,  266,   41,  268,   43,  270,   45,
  272,  273,  274,  275,  276,  277,  278,  256,  257,  258,
  259,  260,  261,  262,  263,  264,   41,  266,   43,  268,
   45,  270,    0,  272,  273,  274,  275,  276,  277,  278,
  256,  257,  279,  259,   41,  261,  262,  263,  264,  125,
  266,    0,  268,   40,  270,   41,  272,  273,   44,   44,
   45,  277,  278,   40,  256,  257,  262,  259,  125,  261,
  262,  263,  264,   41,  266,    0,  268,   36,  270,   41,
  272,  273,   44,  125,   41,  277,  278,   44,  267,   44,
   41,   59,   41,   44,    0,    6,   44,   44,  268,   10,
   41,  271,   43,  279,   45,  262,   40,  256,  257,  279,
   59,   45,  261,  125,  263,  264,   41,  266,   41,  268,
  125,  270,   41,  272,  273,   44,  125,   38,  277,  278,
  125,   90,   91,  125,   59,   41,   41,   48,   41,   98,
  125,   41,   53,   41,  125,  123,   41,   40,    0,   40,
    0,   44,   45,  256,  257,  261,  201,   68,   97,   10,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   79,  272,
  273,   -1,   -1,   84,  277,  278,   -1,   -1,   41,   40,
   -1,   44,   93,   44,   45,   -1,  145,   41,   42,   43,
   -1,   45,   -1,   47,   -1,  256,  257,   60,   61,   62,
   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,  270,
   41,  272,  273,   60,   45,   41,  277,  278,   44,   41,
   44,   45,   44,   -1,   -1,   41,   -1,   -1,   44,   -1,
   -1,  142,  143,   -1,   60,   61,   62,   -1,   60,   61,
   62,   -1,   -1,   -1,   60,   61,   62,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,  256,
  257,   -1,   -1,   -1,  261,   45,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   45,  272,  273,   -1,   45,   -1,
  277,  278,  267,   45,   -1,  196,   -1,  265,  256,  257,
  268,  259,   45,  261,  262,  263,  264,   -1,  266,   -1,
  268,  279,  270,   -1,  272,  273,   -1,  256,  257,  277,
  278,  222,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  256,  272,  273,   -1,   -1,   -1,  277,  278,
   -1,  256,  257,  267,  268,   -1,  261,   -1,  263,  264,
   -1,  266,  253,  268,   -1,  270,   -1,  272,  273,   -1,
  256,  257,  277,  278,   59,  261,   -1,  263,  264,   -1,
  266,   -1,  268,  256,  270,   -1,  272,  273,   -1,    0,
    1,  277,  278,    4,  267,  268,   -1,    8,   -1,   42,
   43,   43,   45,   45,   47,   16,   -1,   -1,   -1,   -1,
   -1,   -1,   23,   -1,    1,  256,   59,    4,   60,   61,
   62,   -1,   33,   34,   -1,   -1,  267,  268,   -1,   16,
   -1,  274,  275,  276,   -1,   -1,   23,   48,   49,   50,
   51,  268,   -1,   54,   -1,  256,   33,   34,   -1,   59,
  277,  278,  256,   64,   -1,   66,  267,  268,   -1,   -1,
   71,   48,   -1,  267,  268,   -1,  115,   54,  274,  275,
  276,   -1,  274,  275,  276,   -1,   -1,   64,  274,  275,
  276,   -1,  256,   -1,   71,   -1,   97,   42,   43,   -1,
   45,  102,   47,  267,  268,  106,  256,  108,   -1,   -1,
  111,   60,   61,   62,  115,  256,   -1,  267,  268,  256,
   97,  160,   -1,  124,  256,  102,  267,  268,  129,  130,
  267,  268,   -1,  256,   -1,  267,  268,   -1,  115,   -1,
  179,   -1,   -1,   -1,  267,  268,   -1,  124,   -1,   -1,
   -1,   -1,   -1,  130,   -1,   -1,   -1,   -1,   -1,  160,
   -1,   -1,  201,   -1,   -1,   -1,   -1,   -1,   54,  170,
  171,   -1,   -1,  174,   -1,   -1,   -1,   -1,  179,  218,
   -1,   -1,  257,  160,   -1,  186,  261,   -1,  263,   -1,
   -1,  266,  231,  268,  233,  270,   -1,  272,  273,   -1,
  201,   -1,  179,   -1,   -1,   -1,   -1,   -1,  209,  186,
  173,   97,  175,  176,   -1,   -1,   -1,  218,   -1,   -1,
   -1,   -1,  261,  256,  201,   -1,   -1,   -1,   -1,  115,
  231,   -1,  233,   -1,  267,  268,   -1,   -1,   -1,   -1,
   -1,  218,  274,  275,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  215,   -1,  231,   -1,  233,  257,   -1,   -1,
  261,  261,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  160,   -1,   -1,   -1,   -1,   -1,
  243,  244,  245,   -1,  261,  248,   -1,  256,  257,   -1,
   -1,  260,   -1,  179,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,  270,  277,  278,
   -1,  256,   -1,   -1,   -1,  201,   -1,   -1,   -1,   -1,
   -1,   -1,  267,  268,   -1,  274,  275,  276,   -1,   -1,
   -1,   -1,  218,   -1,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,   -1,  266,  231,  268,  233,  270,   -1,
  272,  273,  256,  257,   -1,  277,  278,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  256,  257,  277,  278,  261,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,   -1,
  277,  278,   -1,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,
  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  256,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  256,  257,   -1,   -1,  260,   -1,   -1,  263,  256,  257,
  266,   -1,  268,  261,  270,  263,  272,  273,  266,   -1,
  268,   -1,  270,   -1,  272,  273,  257,   -1,  259,  260,
   -1,  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  257,  272,  273,  260,   -1,  262,  263,   -1,  257,  266,
   -1,  268,  261,  270,  263,  272,  273,  266,   -1,  268,
   -1,  270,  257,  272,  273,  260,   -1,   -1,  263,   -1,
  257,  266,   -1,  268,  261,  270,  263,  272,  273,  266,
   -1,  268,   -1,  270,   -1,  272,  273,
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
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables ';' error",
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

//#line 269 "Gramatica.y"
	
public static StringBuilder AMBITO = new StringBuilder(":MAIN");																 
public static boolean DENTRODELAMBITO = false;
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
//#line 886 "Parser.java"
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
case 10:
//#line 26 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: faltan las sentencias antes del ';'  "+"\u001B[0m");}
break;
case 12:
//#line 28 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 16:
//#line 36 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 17:
//#line 37 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 18:
//#line 40 "Gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de variable "); System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 19:
//#line 41 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 20:
//#line 44 "Gramatica.y"
{ if(tipos.containsKey(val_peek(0).sval)){yyval.obj = tipos.get(val_peek(0).sval);
					}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Se utilizo un tipo desconocido "+"\u001B[0m");};}
break;
case 21:
//#line 46 "Gramatica.y"
{ yyval.obj = val_peek(0).obj;  }
break;
case 22:
//#line 49 "Gramatica.y"
{ if(!tipos.containsKey("INTEGER")){tipos.put("INTEGER",new Tipo("INTEGER"));}
							yyval.obj = tipos.get("INTEGER");}
break;
case 23:
//#line 51 "Gramatica.y"
{ if(!tipos.containsKey("DOUBLE")){tipos.put("DOUBLE",new Tipo("DOUBLE"));}
							yyval.obj = tipos.get("DOUBLE");}
break;
case 24:
//#line 55 "Gramatica.y"
{if(val_peek(5).obj != null){cargarVariables(val_peek(7).sval,cargarSubtipo(val_peek(7).sval,(Tipo)val_peek(5).obj,val_peek(3).sval,val_peek(1).sval)," nombre de SubTipo ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 25:
//#line 56 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 26:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 27:
//#line 58 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 28:
//#line 59 "Gramatica.y"
{if(val_peek(2).obj != null){cargarVariables(val_peek(0).sval,cargarTripla(val_peek(0).sval,(Tipo)val_peek(2).obj,true)," nombre de Triple ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}else{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: No se puede crear una tripla de un tipo no declarado. "+"\u001B[0m");}}
break;
case 29:
//#line 60 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
break;
case 30:
//#line 61 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 31:
//#line 62 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
break;
case 32:
//#line 63 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 33:
//#line 64 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 34:
//#line 65 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 35:
//#line 66 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 36:
//#line 67 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 37:
//#line 68 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 38:
//#line 69 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 39:
//#line 70 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 40:
//#line 74 "Gramatica.y"
{	if(RETORNO==false)
										{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); 
									sacarAmbito();
									DENTRODELAMBITO=false;
								}
break;
case 41:
//#line 81 "Gramatica.y"
{cargarVariables(val_peek(0).sval,(Tipo)val_peek(2).obj," nombre de funcion ");agregarAmbito(val_peek(0).sval);DENTRODELAMBITO=true;System.out.println(" Encabezado de la funcion ");}
break;
case 42:
//#line 82 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 43:
//#line 85 "Gramatica.y"
{cargarVariables(val_peek(1).sval,(Tipo)val_peek(2).obj," nombre de parametro real ");}
break;
case 44:
//#line 86 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");}
break;
case 45:
//#line 87 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
break;
case 46:
//#line 88 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 47:
//#line 89 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
break;
case 49:
//#line 95 "Gramatica.y"
{if(DENTRODELAMBITO==false){System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");}}
break;
case 50:
//#line 96 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del RETORNO  "+"\u001B[0m");
						if(DENTRODELAMBITO==false)
										{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : RETORNO declarado fuera del ambito de una funcion  "+"\u001B[0m");
						}}
break;
case 55:
//#line 107 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una ETIQUETA ");System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 57:
//#line 109 "Gramatica.y"
{RETORNO = true;}
break;
case 58:
//#line 112 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 59:
//#line 113 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 60:
//#line 114 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 61:
//#line 115 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 62:
//#line 118 "Gramatica.y"
{if(fueDeclarado(val_peek(2).sval)){
															yyval.sval = val_peek(2).sval;}else{
																System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");}
															System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 63:
//#line 122 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 64:
//#line 123 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
break;
case 65:
//#line 126 "Gramatica.y"
{if(!fueDeclarado(val_peek(3).sval)){System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una funcion no declarada "+"\u001B[0m");}else{
												System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}}
break;
case 66:
//#line 128 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 67:
//#line 129 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 68:
//#line 130 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
break;
case 70:
//#line 134 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
break;
case 71:
//#line 135 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de expresion en lista de expresiones.  "+"\u001B[0m");}
break;
case 76:
//#line 142 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 77:
//#line 143 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 78:
//#line 144 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 79:
//#line 145 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 80:
//#line 146 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
break;
case 84:
//#line 152 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 85:
//#line 153 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 86:
//#line 154 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 87:
//#line 155 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 88:
//#line 158 "Gramatica.y"
{if(fueDeclarado(val_peek(0).sval)){AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval).incrementarContDeRef(); yyval.sval = val_peek(0).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 91:
//#line 161 "Gramatica.y"
{if(fueDeclarado(val_peek(3).sval)){ yyval.sval = val_peek(3).sval;}else{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  se invocó una variable no declarada "+"\u001B[0m");};}
break;
case 92:
//#line 162 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: no se puede acceder a una posicion negativa de un arreglo "+"\u001B[0m");}
break;
case 93:
//#line 164 "Gramatica.y"
{ yyval.sval = val_peek(2).sval + "/"+val_peek(0).sval;}
break;
case 94:
//#line 165 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
break;
case 95:
//#line 166 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 98:
//#line 176 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 99:
//#line 177 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 100:
//#line 180 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 101:
//#line 181 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 102:
//#line 182 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 103:
//#line 183 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 104:
//#line 184 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 105:
//#line 186 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN "+"\u001B[0m");}
break;
case 106:
//#line 187 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del THEN  "+"\u001B[0m");}
break;
case 107:
//#line 188 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia del bloque del ELSE  "+"\u001B[0m");}
break;
case 108:
//#line 189 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
break;
case 109:
//#line 191 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 110:
//#line 192 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 111:
//#line 195 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 112:
//#line 196 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 113:
//#line 197 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 114:
//#line 198 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 115:
//#line 199 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 116:
//#line 200 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 117:
//#line 201 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 118:
//#line 202 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 119:
//#line 204 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el comparador en la condicion "+"\u001B[0m");}
break;
case 120:
//#line 206 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 121:
//#line 207 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
break;
case 122:
//#line 208 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
break;
case 132:
//#line 224 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 137:
//#line 235 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
break;
case 138:
//#line 236 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 139:
//#line 237 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta el END del IF "+"\u001B[0m");}
break;
case 143:
//#line 245 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 145:
//#line 251 "Gramatica.y"
{addUso(val_peek(0).sval, " Es una Cadena MultiLinea ");System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 146:
//#line 256 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 147:
//#line 257 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 148:
//#line 262 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 149:
//#line 263 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1477 "Parser.java"
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
