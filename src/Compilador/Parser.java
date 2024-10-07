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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    2,    2,    2,    3,    3,
    4,    4,    4,    6,    9,    9,   11,   11,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    7,    7,   13,   13,   13,   15,
   14,   16,    5,    5,    5,    5,    5,    5,    5,   22,
   22,   22,   22,   18,   18,   25,   25,   26,   26,   17,
   17,   17,   17,   27,   27,   27,   28,   28,   28,   28,
   10,   10,   24,    1,   12,   12,   19,   19,   19,   19,
   19,   19,   19,   29,   29,   29,   29,   29,   29,   29,
   29,   32,   32,   32,   32,   32,   32,   31,   31,   34,
   33,   30,   30,   38,   37,   35,   35,   36,   23,   20,
   20,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    1,    3,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    1,    1,    1,    9,    8,
    8,    7,    6,    8,    7,    8,    6,    8,    8,    5,
    5,    5,    4,    6,    7,    6,    3,    2,    3,    2,
    1,    4,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    4,    4,    3,    6,    4,    7,    3,    1,    3,
    3,    1,    1,    3,    3,    1,    1,    1,    1,    4,
    3,    1,    1,    1,    1,    2,    8,    6,    5,    7,
    6,    4,    7,    9,    8,    8,    7,    5,    4,    4,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    2,    1,    1,    3,    1,    3,    1,    1,    1,    3,
    3,    2,    2,
};
final static short yydefred[] = {                         0,
    5,    0,   74,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   17,   18,    0,    0,    0,    9,   10,   11,
   12,   13,    0,   16,   49,   43,   44,   45,   46,   48,
    0,    0,    0,   63,   75,    0,    0,    0,   68,    0,
    0,   69,    0,   66,    0,    0,    0,    0,    0,    0,
    0,    0,  113,  112,    2,    0,    7,    0,   73,    0,
   72,    0,    0,    0,    0,    0,    0,   76,    0,   93,
   95,   97,   94,   92,    0,    0,   96,    0,    0,    0,
    0,    0,  109,   51,    0,    0,    0,    0,    0,    0,
   15,    0,    0,    0,    0,    0,  111,    0,  108,  110,
  105,  102,  103,    6,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   64,   65,    0,   82,    0,    0,   98,   99,
   53,   50,   52,    0,    0,    0,   33,    0,    0,    0,
    0,   42,    0,  107,    0,    0,   38,    0,    0,    0,
   71,    0,    0,    0,    0,    0,    0,   56,   89,   70,
    0,  101,    0,    0,    0,    0,   32,   31,    0,   30,
    0,    0,    0,  104,    0,    0,   39,   40,   37,    0,
    0,    0,    0,   88,    0,    0,    0,    0,   78,    0,
   81,    0,   34,   23,    0,    0,    0,   27,    0,    0,
  106,    0,   36,    0,    0,    0,    0,  100,   83,    0,
    0,    0,    0,    0,   25,    0,   35,    0,    0,   57,
   77,   28,   29,   24,   26,    0,   20,    0,   85,   19,
   84,
};
final static short yydgoto[] = {                          4,
   38,  180,   17,   18,   19,   20,   21,   22,   23,   60,
   24,   39,  107,  181,  149,   25,  112,   26,   27,   28,
   29,   30,   88,   41,   42,   67,   43,   44,   45,  100,
  128,   78,  129,  130,  143,  101,  102,  103,
};
final static short yysindex[] = {                      -204,
    0,  268,    0,    0,  469,  -37,   -4,  258,   18,  -37,
 -236,    0,    0,    0,    0,  522,  -31,    0,    0,    0,
    0,    0, -227,    0,    0,    0,    0,    0,    0,    0,
 -105,  268,  268,    0,    0,  150, -195,   55,    0,  100,
   -1,    0,   -7,    0, -125,  296,  113, -158, -158, -179,
  211,  618,    0,    0,    0,   81,    0,  -40,    0,  119,
    0,  211, -123,  544,  211,  100,   33,    0,  -43,    0,
    0,    0,    0,    0,  -16,  -16,    0,  211, -117,  -16,
  -16,  635,    0,    0,   55,  -24,   10,  124,  -98, -158,
    0,  -52,   46,  109,  -89,   82,    0,  671,    0,    0,
    0,    0,    0,    0,  140,  265,  -78,  -82,   14,   66,
    0,   14,   53,  211,  211,  349,  152,  106,   -7,   -7,
  127,   68,    0,    0,  663,    0,  149,  151,    0,    0,
    0,    0,    0,  -45,  -48,  -82,    0,  -22,  -82,  -22,
  -41,    0,  -47,    0,  -13,  180,    0,  -82,  182,  268,
    0,   -9,  349,  142,   14,  226,  211,    0,    0,    0,
  671,    0, -103,   21,  147, -229,    0,    0,  245,    0,
  249,  -29,  251,    0,  671,  268,    0,    0,    0,  268,
   51,  211,  276,    0,  211,  162,  -46,  649,    0,  280,
    0,   -9,    0,    0,  -22,  -22,  -22,    0,  -23,  -22,
    0,   74,    0,   14,  211,  168,  311,    0,    0,   98,
  233,  237,  239,  -26,    0,  247,    0,  219,  337,    0,
    0,    0,    0,    0,    0,  260,    0,  342,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -101,    0,  314,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  387,    0,    0,    0,    0,    1,    0,    0,
   24,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  116,
    0,    0,    0,  395,    0,  288,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -36,    0,    0,    0,  159,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  135,    0,
    0,  288,    0,    0,    0,    0,    0,    0,   70,   93,
  391,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  419,  356,    0,    0,    0,    0,    0,
    0,    0,  158,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  141,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  440,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  177,    0,    0,    0,    0,    0,  196,
    0,    0,    0,    0,    0,  227,    0,    0,  575,    0,
    0,    0,    0,    0,    0,  250,    0,  600,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  588,   96,   17,    0,   23,    0,    0,    0,  -17,    0,
  335,  417,  309,  240,    0,    0,  593,    0,    0,    0,
    0,    0,    0,  679,    0,  -39,    4,   37,  410,  341,
  262,  -12,    0,    0,  266,  -49,    0,    0,
};
final static int YYTABLESIZE=944;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        106,
   73,   37,   36,   37,   73,   73,   73,   37,   73,  136,
   73,  175,  175,  166,  197,   37,  131,   63,   37,   53,
  214,   73,   37,   67,  134,  113,  193,   57,   37,   92,
   93,   94,   56,  140,   80,   46,   54,   58,    3,   81,
    3,   73,   73,   73,   73,   73,   62,   73,  144,   56,
  132,    1,   75,  114,   76,    2,   75,   51,   76,   73,
   73,   73,   73,    3,   67,   67,   67,   67,   67,   60,
   67,   68,  135,  116,   99,  162,  115,  141,  119,  120,
   56,  172,   67,   67,   67,   67,   73,   62,  148,   62,
   62,   62,   61,  153,   69,  198,  115,   16,  225,   95,
   33,  215,   56,  156,   99,   62,   62,   62,   62,    3,
   60,  144,   60,   60,   60,   14,  123,  124,   13,   14,
   99,   79,  142,   73,   75,  201,   76,   64,   60,   60,
   60,   60,   82,   61,   54,   61,   61,   61,  162,  104,
  183,   86,   75,  110,   76,  206,  158,   99,   75,  122,
   76,   61,   61,   61,   61,  188,   14,   79,  189,   73,
   77,   74,  108,   15,  133,  218,   15,  159,  138,   75,
  139,   76,   90,   62,   14,   54,   55,   73,    3,  106,
   62,  150,  184,   99,   75,    3,   76,   13,   14,   65,
  152,  157,  160,   54,   37,   80,   56,   99,   79,   67,
   67,   67,  207,   67,   75,   67,   76,  163,  219,  164,
   99,  115,   34,  174,  208,    3,   79,   55,   34,    3,
  177,  165,  179,   35,    3,   35,   22,  105,   15,   35,
    3,   15,    6,   13,   14,   55,   80,   35,    7,    8,
   35,    9,   73,    3,   35,   10,  176,   11,   12,   21,
   35,    3,   13,   14,   80,   37,   73,   73,   73,  228,
   73,   73,  115,   73,   73,  185,   73,   22,   73,  182,
   73,  192,   73,   73,   73,   73,   73,   73,   73,   67,
   67,   67,  191,   67,   67,   22,   67,   67,  195,   67,
   21,   67,  196,   67,  200,   67,   67,   67,   67,   67,
   67,   67,   62,   62,   62,  147,   62,   62,   21,   62,
   62,  203,   62,    8,   62,  205,   62,   49,   62,   62,
   62,   62,   62,   62,   62,   60,   60,   60,   59,   60,
   60,   59,   60,   60,  217,   60,   84,   60,  210,   60,
   37,   60,   60,   60,   60,   60,   60,   60,   61,   61,
   61,  220,   61,   61,    8,   61,   61,  222,   61,  221,
   61,  223,   61,  224,   61,   61,   61,   61,   61,   61,
   61,  227,   14,   70,   71,   72,   14,  229,   14,   14,
    3,   14,  231,   14,  230,   14,    4,   14,   14,   13,
   14,   54,   14,   14,    3,   54,   58,   54,   54,   58,
   54,   41,   54,  117,   54,   34,   54,   54,   73,   77,
   74,   54,   54,  145,   79,  202,   35,    3,   79,   52,
   79,   79,  127,   79,  190,   79,  187,   79,    0,   79,
   79,    0,    0,   55,   79,   79,    0,   55,    0,   55,
   55,    0,   55,    0,   55,    0,   55,    0,   55,   55,
    0,    0,   80,   55,   55,    0,   80,    0,   80,   80,
    0,   80,    0,   80,    0,   80,   34,   80,   80,    0,
    0,    0,   80,   80,    0,    0,    0,   35,    3,    0,
   70,   70,   70,   22,   70,    0,   70,   22,    0,   22,
   22,    0,   22,    0,   22,    0,   22,    0,   22,   22,
    0,    0,    0,   22,   22,    0,   21,    0,    0,    0,
   21,    0,   21,   21,    0,   21,    0,   21,    0,   21,
  146,   21,   21,    0,    6,    3,   21,   21,   47,    0,
    7,    8,    3,    9,    0,    3,   48,   10,    0,   11,
   12,   13,   14,    0,   13,   14,    0,    0,    0,    0,
    0,   34,    6,    0,  169,    0,  171,  173,    7,    8,
    0,    9,   35,    3,   83,   10,    0,   11,   12,    0,
    8,    0,   13,   14,    8,    0,    8,    8,    0,    8,
    0,    8,    0,    8,    0,    8,    8,    5,  199,   15,
    8,    8,   15,    0,    0,   50,    0,    0,   40,    0,
    0,    0,   40,   15,    0,    0,    0,    0,    0,    0,
   59,  211,  212,  213,    0,    0,  216,    0,    0,   15,
   15,    0,   70,   71,   72,    0,    0,    0,   66,    0,
  226,    0,    0,   85,   91,   91,   91,    0,   87,   59,
    0,    0,    0,   96,    0,    0,   91,   91,   91,    0,
   91,   15,    0,   91,  109,    0,   91,    0,   91,    0,
   91,  118,   91,   91,    0,    0,    0,    0,    0,   59,
  121,    0,    0,   15,   90,   90,   90,   91,   90,  137,
   31,   90,   91,   31,   90,   59,   90,    0,   90,    0,
   90,   90,    0,   91,   31,   59,    0,    0,    0,    0,
    0,   61,    0,    0,    0,    0,  154,  155,    0,    0,
   31,   31,   59,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  167,  168,   89,    6,  170,    0,   32,    0,
   31,    7,    8,    0,    9,  178,    3,   15,   10,    0,
   11,   12,   31,    0,    0,   13,   14,    0,   59,  186,
    0,    0,    0,  194,    0,    0,    0,    0,    0,    0,
   31,    0,   59,   15,   31,    0,    0,   15,    0,    0,
    0,    0,    0,    0,  204,   59,   31,    0,    6,    0,
    0,    0,   55,    0,    7,    8,  151,    9,    0,    3,
    0,   10,    0,   11,   12,    0,    0,    0,   13,   14,
    6,    0,    0,   31,  111,    0,    7,    8,    0,    9,
    0,    3,    0,   10,    0,   11,   12,    0,    0,    0,
   13,   14,    0,    0,    0,    0,    0,    0,   31,    0,
   87,   87,   87,    0,   87,    0,    0,   87,    0,   31,
   87,    0,   87,    0,   87,    0,   87,   87,    0,    0,
    0,    0,    0,   31,   31,   86,   86,   86,   31,   86,
    0,    0,   86,    0,    0,   86,   31,   86,    0,   86,
    0,   86,   86,   97,    6,    0,    0,   98,    0,    0,
    7,    0,    0,    9,    0,    3,    0,   10,    0,   11,
   12,    6,    0,  125,   98,    0,  126,    7,    0,    0,
    9,    0,    3,    0,   10,    6,   11,   12,  161,    0,
  209,    7,    0,    0,    9,    0,    3,    0,   10,    6,
   11,   12,  161,    0,    0,    7,    0,    6,    9,    0,
    3,    0,   10,    7,   11,   12,    9,    0,    3,    0,
   10,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   40,   45,   41,   42,   43,   45,   45,   62,
   47,   59,   59,   62,   44,   45,   41,  123,   45,  256,
   44,  123,   45,    0,  123,   65,  256,   59,   45,   47,
   48,   49,   16,  123,   42,   40,  273,  265,  268,   47,
  268,   41,   42,   43,   44,   45,    0,   47,   98,   33,
   41,  256,   43,   66,   45,  260,   43,   40,   45,   59,
   60,   61,   62,  268,   41,   42,   43,   44,   45,    0,
   47,  267,   90,   41,   52,  125,   44,   95,   75,   76,
   64,  123,   59,   60,   61,   62,  123,   41,  106,   43,
   44,   45,    0,   41,   40,  125,   44,    2,  125,  279,
    5,  125,   86,  116,   82,   59,   60,   61,   62,  268,
   41,  161,   43,   44,   45,    0,   80,   81,  277,  278,
   98,  123,   41,  123,   43,  175,   45,   32,   59,   60,
   61,   62,  258,   41,    0,   43,   44,   45,  188,   59,
  153,   46,   43,  267,   45,  185,   41,  125,   43,  267,
   45,   59,   60,   61,   62,  259,   41,    0,  262,   60,
   61,   62,   44,  265,   41,  205,  268,   41,  123,   43,
   62,   45,   60,  279,   59,   41,    0,  279,  268,   40,
  279,  260,   41,  161,   43,  268,   45,  277,  278,   40,
  125,   40,  125,   59,   45,    0,  180,  175,   41,   41,
   42,   43,   41,   45,   43,   47,   45,   59,   41,   59,
  188,   44,  256,  261,  261,  268,   59,   41,  256,  268,
   41,  267,   41,  267,  268,  267,    0,  268,  265,  267,
  268,  268,  257,  277,  278,   59,   41,  267,  263,  264,
  267,  266,  279,  268,  267,  270,  260,  272,  273,    0,
  267,  268,  277,  278,   59,   45,  256,  257,  258,   41,
  260,  261,   44,  263,  264,   40,  266,   41,  268,  279,
  270,  125,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  262,  260,  261,   59,  263,  264,   44,  266,
   41,  268,   44,  270,   44,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,   41,  260,  261,   59,  263,
  264,  261,  266,    0,  268,   40,  270,   60,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,   41,  260,
  261,   44,  263,  264,  261,  266,   41,  268,   59,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,   41,  260,  261,   41,  263,  264,  125,  266,  262,
  268,  125,  270,  125,  272,  273,  274,  275,  276,  277,
  278,  125,  257,  274,  275,  276,  261,   41,  263,  264,
  268,  266,   41,  268,  125,  270,    0,  272,  273,  277,
  278,  257,  277,  278,    0,  261,   41,  263,  264,   44,
  266,  261,  268,   69,  270,  256,  272,  273,   60,   61,
   62,  277,  278,  105,  257,  176,  267,  268,  261,   10,
  263,  264,   82,  266,  163,  268,  161,  270,   -1,  272,
  273,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,  256,  272,  273,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,  267,  268,   -1,
   41,   42,   43,  257,   45,   -1,   47,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,   -1,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
  256,  272,  273,   -1,  257,  268,  277,  278,  271,   -1,
  263,  264,  268,  266,   -1,  268,  279,  270,   -1,  272,
  273,  277,  278,   -1,  277,  278,   -1,   -1,   -1,   -1,
   -1,  256,  257,   -1,  138,   -1,  140,  141,  263,  264,
   -1,  266,  267,  268,  269,  270,   -1,  272,  273,   -1,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,    0,  172,    2,
  277,  278,    5,   -1,   -1,    8,   -1,   -1,    6,   -1,
   -1,   -1,   10,   16,   -1,   -1,   -1,   -1,   -1,   -1,
   23,  195,  196,  197,   -1,   -1,  200,   -1,   -1,   32,
   33,   -1,  274,  275,  276,   -1,   -1,   -1,   36,   -1,
  214,   -1,   -1,   46,   47,   48,   49,   -1,   46,   52,
   -1,   -1,   -1,   51,   -1,   -1,  256,  257,  258,   -1,
  260,   64,   -1,  263,   62,   -1,  266,   -1,  268,   -1,
  270,   69,  272,  273,   -1,   -1,   -1,   -1,   -1,   82,
   78,   -1,   -1,   86,  256,  257,  258,   90,  260,   92,
    2,  263,   95,    5,  266,   98,  268,   -1,  270,   -1,
  272,  273,   -1,  106,   16,  108,   -1,   -1,   -1,   -1,
   -1,   23,   -1,   -1,   -1,   -1,  114,  115,   -1,   -1,
   32,   33,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  135,  136,   46,  257,  139,   -1,  260,   -1,
   52,  263,  264,   -1,  266,  148,  268,  150,  270,   -1,
  272,  273,   64,   -1,   -1,  277,  278,   -1,  161,  157,
   -1,   -1,   -1,  166,   -1,   -1,   -1,   -1,   -1,   -1,
   82,   -1,  175,  176,   86,   -1,   -1,  180,   -1,   -1,
   -1,   -1,   -1,   -1,  182,  188,   98,   -1,  257,   -1,
   -1,   -1,  261,   -1,  263,  264,  108,  266,   -1,  268,
   -1,  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,
  257,   -1,   -1,  125,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  150,   -1,
  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,  161,
  266,   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,
   -1,   -1,   -1,  175,  176,  256,  257,  258,  180,  260,
   -1,   -1,  263,   -1,   -1,  266,  188,  268,   -1,  270,
   -1,  272,  273,  256,  257,   -1,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,  257,   -1,  259,  260,   -1,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,
  272,  273,  260,   -1,   -1,  263,   -1,  257,  266,   -1,
  268,   -1,  270,  263,  272,  273,  266,   -1,  268,   -1,
  270,   -1,  272,  273,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=279;
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
"ASIGNACION",
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

//#line 211 "Gramatica.y"
																	 
private static boolean RETORNO = false;
private static boolean RETORNO_DEL_IF = false;
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
//#line 612 "Parser.java"
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
case 77:
//#line 138 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 78:
//#line 139 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 79:
//#line 140 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 80:
//#line 141 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 81:
//#line 142 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  ": Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 82:
//#line 143 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 83:
//#line 144 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 84:
//#line 147 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 85:
//#line 148 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 86:
//#line 149 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 87:
//#line 150 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 88:
//#line 151 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 89:
//#line 152 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 90:
//#line 153 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 91:
//#line 154 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 109:
//#line 192 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 110:
//#line 197 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 111:
//#line 198 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 112:
//#line 203 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 113:
//#line 204 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1001 "Parser.java"
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
