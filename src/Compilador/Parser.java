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
//#line 18 "Parser.java"




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
    0,    0,    0,    0,    0,    2,    2,    2,    3,    3,
    4,    4,    4,    6,    9,    9,   11,   11,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    7,    7,   13,   13,   13,   15,
   14,   16,    5,    5,    5,    5,    5,    5,    5,   22,
   22,   22,   22,   18,   18,   25,   25,   26,   26,   17,
   17,   17,   17,   27,   27,   27,   28,   28,   28,   28,
   10,   10,   24,    1,   12,   12,   12,   12,   19,   19,
   19,   19,   19,   19,   19,   29,   29,   29,   29,   29,
   29,   29,   29,   32,   32,   32,   32,   32,   32,   31,
   31,   34,   33,   30,   30,   38,   37,   35,   35,   36,
   23,   20,   20,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    1,    3,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    1,    1,    1,    9,    8,
    8,    7,    6,    8,    7,    8,    6,    8,    8,    5,
    5,    5,    4,    6,    7,    6,    3,    2,    3,    2,
    1,    4,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    4,    4,    3,    6,    4,    7,    3,    1,    3,
    3,    1,    1,    3,    3,    1,    1,    1,    1,    4,
    3,    1,    1,    1,    1,    2,    1,    2,    8,    6,
    5,    7,    6,    4,    7,    9,    8,    8,    7,    5,
    4,    4,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    2,    1,    1,    3,    1,    3,    1,    1,
    1,    3,    3,    2,    2,
};
final static short yydefred[] = {                         0,
    5,    0,   74,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   17,   18,    0,    0,    0,    9,   10,   11,
   12,   13,    0,   16,   49,   43,   44,   45,   46,   48,
    0,    0,    0,   63,   75,   77,    0,    0,    0,   68,
    0,    0,   69,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,  115,  114,    2,    0,    7,    0,   73,
    0,   72,    0,    0,    0,    0,    0,    0,   76,   78,
    0,   95,   97,   99,   96,   94,    0,    0,   98,    0,
    0,    0,    0,    0,  111,   51,    0,    0,    0,    0,
    0,    0,   15,    0,    0,    0,    0,    0,  113,    0,
  110,  112,  107,  104,  105,    6,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   64,   65,    0,   84,    0,    0,
  100,  101,   53,   50,   52,    0,    0,    0,   33,    0,
    0,    0,    0,   42,    0,  109,    0,    0,   38,    0,
    0,    0,   71,    0,    0,    0,    0,    0,    0,   56,
   91,   70,    0,  103,    0,    0,    0,    0,   32,   31,
    0,   30,    0,    0,    0,  106,    0,    0,   39,   40,
   37,    0,    0,    0,    0,   90,    0,    0,    0,    0,
   80,    0,   83,    0,   34,   23,    0,    0,    0,   27,
    0,    0,  108,    0,   36,    0,    0,    0,    0,  102,
   85,    0,    0,    0,    0,    0,   25,    0,   35,    0,
    0,   57,   79,   28,   29,   24,   26,    0,   20,    0,
   87,   19,   86,
};
final static short yydgoto[] = {                          4,
   39,  182,   17,   18,   19,   20,   21,   22,   23,   61,
   24,   40,  109,  183,  151,   25,  114,   26,   27,   28,
   29,   30,   90,   42,   43,   68,   44,   45,   46,  102,
  130,   80,  131,  132,  145,  103,  104,  105,
};
final static short yysindex[] = {                      -179,
    0,  541,    0,    0,  447,  -37,   58,  138,   78,  -37,
 -235,    0,    0,    0,    0,  478,    5,    0,    0,    0,
    0,    0, -229,    0,    0,    0,    0,    0,    0,    0,
 -110,  541,  541,    0,    0,    0,  -35, -250,  109,    0,
  100,  -13,    0,   11,    0, -125,  296,  113, -227, -227,
 -138,  -45,  642,    0,    0,    0,   92,    0,  -28,    0,
  127,    0,  -45,  -95,  523,  -45,  100,   13,    0,    0,
  209,    0,    0,    0,    0,    0,  -20,  -20,    0,  -45,
  -87,  -20,  -20,  347,    0,    0,  109,  277,   -8,  144,
  -97, -227,    0,  -60,   63,  128,  -89,   56,    0,  678,
    0,    0,    0,    0,    0,    0,  152,  311,  -40,  -59,
   74,   99,    0,   74,   31,  -45,  -45,  410,  188,  101,
   11,   11,  150,  121,    0,    0,  670,    0,  175,  190,
    0,    0,    0,    0,    0,  -16,  -53,  -59,    0,  -14,
  -59,  -14,  -41,    0,  -48,    0,   -4,  222,    0,  -59,
  229,  541,    0,   -7,  410,  162,   74,  243,  -45,    0,
    0,    0,  678,    0, -157,   33,  164, -228,    0,    0,
  249,    0,  262,  -38,  268,    0,  678,  541,    0,    0,
    0,  541,   71,  -45,  295,    0,  -45,  171,  -36,  656,
    0,  280,    0,   -7,    0,    0,  -14,  -14,  -14,    0,
  -25,  -14,    0,   97,    0,   74,  -45,   84,  319,    0,
    0,  102,  237,  247,  253,   49,    0,  258,    0,  115,
  344,    0,    0,    0,    0,    0,    0,  270,    0,  346,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -101,    0,  314,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  397,    0,    0,    0,    0,    0,    1,    0,
    0,   24,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,    0,    0,  400,    0,  122,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -27,    0,    0,    0,
  159,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  135,    0,    0,  122,    0,    0,    0,    0,    0,    0,
   70,   93,  564,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  588,  143,    0,    0,    0,
    0,    0,    0,    0,  158,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  141,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  438,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  177,    0,    0,    0,    0,
    0,  196,    0,    0,    0,    0,    0,  227,    0,    0,
  606,    0,    0,    0,    0,    0,    0,  250,    0,  624,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  595,   27,   62,    0,   20,    0,    0,    0,   73,    0,
  333,  459,  303,  233,    0,    0,  584,    0,    0,    0,
    0,    0,    0,  665,    0,  -39,  -22,   -3,  404,  332,
  255,  -15,    0,    0,  260,  -51,    0,    0,
};
final static int YYTABLESIZE=951;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   73,  138,   37,   38,   66,  199,   38,   38,  168,   38,
  177,  108,   64,   73,   73,   73,   69,   73,  216,   73,
   54,   73,  177,   67,   38,  136,  115,  195,   16,   70,
   38,   33,  134,  142,   77,   59,   78,   55,    3,    3,
    3,   73,   73,   73,   73,   73,   62,   73,  146,   13,
   14,  116,   82,  118,  121,  122,  117,   83,   65,   73,
   73,   73,   73,   58,   67,   67,   67,   67,   67,   60,
   67,  155,  101,   88,  117,  164,    1,   57,  125,  126,
    2,  174,   67,   67,   67,   67,  200,   62,    3,   62,
   62,   62,   61,   38,   57,   73,  144,   47,   77,  217,
   78,  190,  158,  101,  191,   62,   62,   62,   62,   81,
   60,  146,   60,   60,   60,   14,   77,   52,   78,  101,
   94,   95,   96,   73,  221,  203,   57,  117,   60,   60,
   60,   60,   84,   61,   54,   61,   61,   61,  164,  185,
   97,  160,   77,   77,   78,   78,  101,  208,   71,   57,
  106,   61,   61,   61,   61,  230,   14,   81,  117,   75,
   79,   76,   59,   15,  137,   59,   15,  220,   63,  143,
  110,  112,   92,  227,   14,   54,   55,   73,    3,  124,
  150,   63,  101,   58,  135,  140,   58,   13,   14,  141,
  161,  108,   77,   54,   78,   82,  101,   50,   81,   67,
   67,   67,  186,   67,   77,   67,   78,    3,    3,  101,
   34,  209,  176,   77,    3,   78,   81,   55,   34,  152,
   34,   35,    3,  154,  210,   35,   22,  159,   35,   35,
    3,   35,    3,  165,   36,   55,   82,   15,   36,  107,
   15,   36,   36,   57,   36,  162,   35,    3,  166,   21,
  167,   73,   35,   38,   82,  178,   73,   73,   73,   36,
   73,   73,  179,   73,   73,   36,   73,   22,   73,  181,
   73,  184,   73,   73,   73,   73,   73,   73,   73,   67,
   67,   67,  187,   67,   67,   22,   67,   67,  194,   67,
   21,   67,  197,   67,  193,   67,   67,   67,   67,   67,
   67,   67,   62,   62,   62,  198,   62,   62,   21,   62,
   62,  202,   62,    8,   62,   35,   62,  133,   62,   62,
   62,   62,   62,   62,   62,   60,   60,   60,   36,   60,
   60,  205,   60,   60,  207,   60,   86,   60,  212,   60,
   38,   60,   60,   60,   60,   60,   60,   60,   61,   61,
   61,  149,   61,   61,    8,   61,   61,  219,   61,  222,
   61,  224,   61,  223,   61,   61,   61,   61,   61,   61,
   61,  225,   14,   72,   73,   74,   14,  226,   14,   14,
    3,   14,  229,   14,  231,   14,  233,   14,   14,   13,
   14,   54,   14,   14,  232,   54,    4,   54,   54,    3,
   54,   41,   54,  119,   54,    3,   54,   54,   48,  147,
  204,   54,   54,   53,   81,  129,   49,    0,   81,  192,
   81,   81,  189,   81,    0,   81,    0,   81,    0,   81,
   81,    0,    0,   55,   81,   81,    0,   55,    0,   55,
   55,    0,   55,    0,   55,    0,   55,    0,   55,   55,
    0,    0,   82,   55,   55,    0,   82,    0,   82,   82,
    0,   82,    0,   82,   34,   82,    0,   82,   82,   75,
   79,   76,   82,   82,    0,   35,    3,    0,   70,   70,
   70,    0,   70,   22,   70,   13,   14,   22,   36,   22,
   22,    0,   22,    0,   22,    0,   22,    0,   22,   22,
    0,    0,    0,   22,   22,    0,   21,    0,    0,    0,
   21,    0,   21,   21,    0,   21,    0,   21,    0,   21,
    0,   21,   21,    0,    0,    0,   21,   21,    0,    0,
    0,    0,    0,    6,    0,    0,    0,    0,    0,    7,
    8,    0,    9,    0,    3,    0,   10,    0,   11,   12,
    0,   34,    6,   13,   14,    0,    0,    0,    7,    8,
    0,    9,   35,    3,   85,   10,  148,   11,   12,    0,
    8,    0,   13,   14,    8,   36,    8,    8,    3,    8,
    0,    8,    0,    8,    0,    8,    8,   13,   14,   41,
    8,    8,    0,   41,    5,    0,   15,    0,  171,   15,
  173,  175,   51,    6,    0,  127,  100,    0,  128,    7,
   15,    0,    9,    0,    3,    0,   10,   60,   11,   12,
   67,    0,    0,    0,    0,    0,   15,   15,    0,    0,
   89,    0,  201,    0,    0,   98,    0,    0,    0,    0,
    0,   87,   93,   93,   93,    0,  111,   60,    0,    0,
    0,    0,    0,    0,  120,  213,  214,  215,    0,   15,
  218,    0,    0,  123,    0,    0,   31,    0,    0,   31,
    0,    0,    0,    0,  228,    0,    0,    0,   60,    0,
   31,    0,   15,   72,   73,   74,   93,   62,  139,    0,
    0,   93,    0,    0,   60,    0,   31,   31,    0,  156,
  157,    0,   93,    6,   60,    0,   32,    0,    0,    7,
    8,   91,    9,    0,    3,    0,   10,   31,   11,   12,
    0,   60,    0,   13,   14,    0,    0,    0,    0,   31,
    0,  169,  170,    0,    6,  172,    0,    0,   56,    0,
    7,    8,  188,    9,  180,    3,   15,   10,   31,   11,
   12,    0,   31,    0,   13,   14,    0,   60,    0,    0,
    0,    0,  196,    0,   31,    0,    0,  206,    0,    0,
    0,   60,   15,    0,  153,    0,   15,    0,    0,    6,
    0,    0,    0,  113,   60,    7,    8,    0,    9,    0,
    3,   31,   10,    0,   11,   12,    0,    6,    0,   13,
   14,    0,    0,    7,    8,    0,    9,    0,    3,    0,
   10,    0,   11,   12,    0,    0,   31,   13,   14,   93,
   93,   93,    0,   93,    0,    0,   93,   31,    0,   93,
    0,   93,    0,   93,    0,   93,   93,    0,    0,    0,
    0,   31,   31,   92,   92,   92,   31,   92,    0,    0,
   92,    0,    0,   92,   31,   92,    0,   92,    0,   92,
   92,   89,   89,   89,    0,   89,    0,    0,   89,    0,
    0,   89,    0,   89,    0,   89,    0,   89,   89,   88,
   88,   88,    0,   88,    0,    0,   88,    0,    0,   88,
    0,   88,    0,   88,    0,   88,   88,   99,    6,    0,
    0,  100,    0,    0,    7,    0,    0,    9,    0,    3,
    0,   10,    6,   11,   12,  163,    0,  211,    7,    0,
    0,    9,    0,    3,    0,   10,    6,   11,   12,  163,
    0,    0,    7,    0,    6,    9,    0,    3,    0,   10,
    7,   11,   12,    9,    0,    3,    0,   10,    0,   11,
   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   62,   40,   45,   40,   44,   45,   45,   62,   45,
   59,   40,  123,   41,   42,   43,  267,   45,   44,   47,
  256,  123,   59,    0,   45,  123,   66,  256,    2,  280,
   45,    5,   41,  123,   43,  265,   45,  273,  268,  268,
  268,   41,   42,   43,   44,   45,    0,   47,  100,  277,
  278,   67,   42,   41,   77,   78,   44,   47,   32,   59,
   60,   61,   62,   59,   41,   42,   43,   44,   45,    0,
   47,   41,   53,   47,   44,  127,  256,   16,   82,   83,
  260,  123,   59,   60,   61,   62,  125,   41,  268,   43,
   44,   45,    0,   45,   33,  123,   41,   40,   43,  125,
   45,  259,  118,   84,  262,   59,   60,   61,   62,  123,
   41,  163,   43,   44,   45,    0,   43,   40,   45,  100,
   48,   49,   50,  123,   41,  177,   65,   44,   59,   60,
   61,   62,  258,   41,    0,   43,   44,   45,  190,  155,
  279,   41,   43,   43,   45,   45,  127,  187,   40,   88,
   59,   59,   60,   61,   62,   41,   41,    0,   44,   60,
   61,   62,   41,  265,   92,   44,  268,  207,  279,   97,
   44,  267,   60,  125,   59,   41,    0,  279,  268,  267,
  108,  279,  163,   41,   41,  123,   44,  277,  278,   62,
   41,   40,   43,   59,   45,    0,  177,   60,   41,   41,
   42,   43,   41,   45,   43,   47,   45,  268,  268,  190,
  256,   41,  261,   43,  268,   45,   59,   41,  256,  260,
  256,  267,  268,  125,  261,  267,    0,   40,  267,  267,
  268,  267,  268,   59,  280,   59,   41,  265,  280,  268,
  268,  280,  280,  182,  280,  125,  267,  268,   59,    0,
  267,  279,  267,   45,   59,  260,  256,  257,  258,  280,
  260,  261,   41,  263,  264,  280,  266,   41,  268,   41,
  270,  279,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,   40,  260,  261,   59,  263,  264,  125,  266,
   41,  268,   44,  270,  262,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,   44,  260,  261,   59,  263,
  264,   44,  266,    0,  268,  267,  270,   41,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  280,  260,
  261,  261,  263,  264,   40,  266,   41,  268,   59,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,   41,  260,  261,   41,  263,  264,  261,  266,   41,
  268,  125,  270,  262,  272,  273,  274,  275,  276,  277,
  278,  125,  257,  274,  275,  276,  261,  125,  263,  264,
  268,  266,  125,  268,   41,  270,   41,  272,  273,  277,
  278,  257,  277,  278,  125,  261,    0,  263,  264,    0,
  266,  261,  268,   71,  270,  268,  272,  273,  271,  107,
  178,  277,  278,   10,  257,   84,  279,   -1,  261,  165,
  263,  264,  163,  266,   -1,  268,   -1,  270,   -1,  272,
  273,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,  256,  270,   -1,  272,  273,   60,
   61,   62,  277,  278,   -1,  267,  268,   -1,   41,   42,
   43,   -1,   45,  257,   47,  277,  278,  261,  280,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,   -1,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  256,  257,  277,  278,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  256,  272,  273,   -1,
  257,   -1,  277,  278,  261,  280,  263,  264,  268,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  277,  278,    6,
  277,  278,   -1,   10,    0,   -1,    2,   -1,  140,    5,
  142,  143,    8,  257,   -1,  259,  260,   -1,  262,  263,
   16,   -1,  266,   -1,  268,   -1,  270,   23,  272,  273,
   37,   -1,   -1,   -1,   -1,   -1,   32,   33,   -1,   -1,
   47,   -1,  174,   -1,   -1,   52,   -1,   -1,   -1,   -1,
   -1,   47,   48,   49,   50,   -1,   63,   53,   -1,   -1,
   -1,   -1,   -1,   -1,   71,  197,  198,  199,   -1,   65,
  202,   -1,   -1,   80,   -1,   -1,    2,   -1,   -1,    5,
   -1,   -1,   -1,   -1,  216,   -1,   -1,   -1,   84,   -1,
   16,   -1,   88,  274,  275,  276,   92,   23,   94,   -1,
   -1,   97,   -1,   -1,  100,   -1,   32,   33,   -1,  116,
  117,   -1,  108,  257,  110,   -1,  260,   -1,   -1,  263,
  264,   47,  266,   -1,  268,   -1,  270,   53,  272,  273,
   -1,  127,   -1,  277,  278,   -1,   -1,   -1,   -1,   65,
   -1,  137,  138,   -1,  257,  141,   -1,   -1,  261,   -1,
  263,  264,  159,  266,  150,  268,  152,  270,   84,  272,
  273,   -1,   88,   -1,  277,  278,   -1,  163,   -1,   -1,
   -1,   -1,  168,   -1,  100,   -1,   -1,  184,   -1,   -1,
   -1,  177,  178,   -1,  110,   -1,  182,   -1,   -1,  257,
   -1,   -1,   -1,  261,  190,  263,  264,   -1,  266,   -1,
  268,  127,  270,   -1,  272,  273,   -1,  257,   -1,  277,
  278,   -1,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  273,   -1,   -1,  152,  277,  278,  256,
  257,  258,   -1,  260,   -1,   -1,  263,  163,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,
   -1,  177,  178,  256,  257,  258,  182,  260,   -1,   -1,
  263,   -1,   -1,  266,  190,  268,   -1,  270,   -1,  272,
  273,  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,  256,  257,   -1,
   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,  257,  272,  273,  260,   -1,  262,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,  257,  272,  273,  260,
   -1,   -1,  263,   -1,  257,  266,   -1,  268,   -1,  270,
  263,  272,  273,  266,   -1,  268,   -1,  270,   -1,  272,
  273,
};
}
final static short YYFINAL=4;
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
"programa : ID_simple sentencias",
"programa : error",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencia ';'",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables",
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
"declaracion_funciones : tipo FUN ID parametros_parentesis BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN parametros_parentesis BEGIN cuerpo_funcion END",
"parametros_parentesis : '(' parametro ')'",
"parametros_parentesis : '(' ')'",
"parametros_parentesis : '(' error ')'",
"parametro : tipo ID_simple",
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
"list_expre : list_expre ',' expresion_arit",
"list_expre : expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
"expresion_arit : error",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : variable_simple",
"factor : CTE_con_sig",
"factor : invocacion",
"factor : variable_simple '{' CTE '}'",
"variables : variables ',' variable_simple",
"variables : variable_simple",
"variable_simple : ID_simple",
"ID_simple : ID",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
"CTE_con_sig : ERROR",
"CTE_con_sig : '-' ERROR",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';'",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';'",
"sentencia_IF : IF condicion THEN bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' ELSE END_IF",
"condicion : '(' '(' list_expre ')' comparador '(' list_expre ')' ')'",
"condicion : '(' list_expre ')' comparador '(' list_expre ')' ')'",
"condicion : '(' '(' list_expre ')' comparador '(' list_expre ')'",
"condicion : '(' list_expre ')' comparador '(' list_expre ')'",
"condicion : '(' expresion_arit comparador expresion_arit ')'",
"condicion : expresion_arit comparador expresion_arit ')'",
"condicion : '(' expresion_arit comparador expresion_arit",
"condicion : expresion_arit comparador expresion_arit",
"comparador : '>'",
"comparador : MAYORIGUAL",
"comparador : '<'",
"comparador : MENORIGUAL",
"comparador : '='",
"comparador : DISTINTO",
"bloque_else : bloque_else_simple",
"bloque_else : bloque_else_multiple",
"bloque_else_multiple : ELSE BEGIN bloque_sent_ejecutables END",
"bloque_else_simple : ELSE bloque_sentencia_simple",
"bloque_unidad : bloque_unidad_simple",
"bloque_unidad : bloque_unidad_multiple",
"bloque_unidad_multiple : BEGIN bloque_sent_ejecutables END",
"bloque_unidad_simple : bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sent_ejecutables ';' bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"sentencia_WHILE : WHILE condicion bloque_unidad",
"sentencia_WHILE : WHILE condicion error",
"sentencia_goto : GOTO ETIQUETA",
"sentencia_goto : GOTO error",
};

//#line 214 "Gramatica.y"
																	 
private static boolean RETORNO = false;
private static int cantRETORNOS = 0;
int yylex() {
	int tokenSalida = AnalizadorLexico.getToken();
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	if(tokenSalida==0) {
		return AnalizadorLexico.siguienteLectura(AnalizadorLexico.archivo_original,' ');
	}
	return tokenSalida;
}
private static void yyerror(String string) {
	System.out.println(string);
}

private static void cambioCTENegativa(String key) {
	String keyNeg = "-" + key;
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
private static boolean estaRango(String key) {
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
		if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
			AnalizadorLexico.TablaDeSimbolos.remove(key);
			yyerror("\u001B[31m"+"Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango."+"\u001B[0m");
			return false;
		}
	}
	return true;
}
//#line 616 "Parser.java"
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
//#line 9 "Gramatica.y"
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(3).sval +"\u001B[0m");}
break;
case 2:
//#line 10 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 3:
//#line 11 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 4:
//#line 12 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 5:
//#line 13 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+" No forma parte de la estructura de un programa "+"\u001B[0m");}
break;
case 8:
//#line 18 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 14:
//#line 32 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 19:
//#line 43 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}
break;
case 20:
//#line 44 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 21:
//#line 45 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 22:
//#line 46 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 23:
//#line 47 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}
break;
case 24:
//#line 48 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango inferior "+"\u001B[0m");}
break;
case 25:
//#line 49 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 26:
//#line 50 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango superior "+"\u001B[0m");}
break;
case 27:
//#line 51 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 28:
//#line 52 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 29:
//#line 53 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 30:
//#line 54 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 31:
//#line 55 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 32:
//#line 56 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 33:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 34:
//#line 58 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 35:
//#line 61 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); }
break;
case 36:
//#line 62 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 38:
//#line 66 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 39:
//#line 67 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion. "+"\u001B[0m");}
break;
case 47:
//#line 83 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 49:
//#line 85 "Gramatica.y"
{RETORNO = true;}
break;
case 50:
//#line 88 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 51:
//#line 89 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  ");}
break;
case 52:
//#line 90 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 53:
//#line 91 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 54:
//#line 94 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 55:
//#line 95 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 56:
//#line 98 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
break;
case 57:
//#line 99 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 63:
//#line 109 "Gramatica.y"
{System.out.println("\u001B[31m"+"La expresion está mal escrita "+"\u001B[0m");}
break;
case 75:
//#line 134 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 76:
//#line 135 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 79:
//#line 141 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 80:
//#line 142 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 81:
//#line 143 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 82:
//#line 144 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 83:
//#line 145 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  ": Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 84:
//#line 146 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 85:
//#line 147 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 86:
//#line 150 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 87:
//#line 151 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 88:
//#line 152 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 89:
//#line 153 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 90:
//#line 154 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 91:
//#line 155 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 92:
//#line 156 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 93:
//#line 157 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 111:
//#line 195 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 112:
//#line 200 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 113:
//#line 201 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 114:
//#line 206 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 115:
//#line 207 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1005 "Parser.java"
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
