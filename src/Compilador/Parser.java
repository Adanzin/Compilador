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
    0,    0,    0,    0,    0,    2,    2,    3,    3,    3,
    4,    4,    4,    4,    4,    6,    6,    9,    9,   11,
   11,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    7,    7,   13,
   13,   13,   13,   13,   14,   15,    5,    5,    5,    5,
    5,    5,    5,   21,   21,   21,   21,   17,   17,   24,
   24,   24,   24,   25,   25,   16,   16,   16,   16,   16,
   16,   16,   16,   26,   26,   26,   26,   26,   26,   26,
   27,   27,   27,   27,   10,   10,   10,   23,    1,   12,
   12,   12,   12,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   28,   28,   28,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   31,   31,   31,   31,
   31,   31,   30,   30,   33,   33,   32,   29,   29,   37,
   37,   37,   36,   34,   34,   34,   35,   22,   19,   19,
   20,   20,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    1,    1,    2,    1,
    1,    2,    2,    1,    1,    3,    3,    1,    1,    1,
    1,    9,    8,    8,    7,    6,    8,    7,    8,    6,
    8,    8,    5,    5,    5,    4,    6,    7,    6,    4,
    3,    3,    2,    3,    1,    4,    1,    1,    1,    1,
    1,    1,    1,    4,    3,    4,    4,    3,    6,    4,
    7,    3,    4,    3,    1,    3,    3,    1,    3,    3,
    3,    3,    2,    3,    3,    1,    3,    3,    3,    3,
    1,    1,    1,    4,    3,    2,    1,    1,    1,    1,
    2,    1,    2,    8,    6,    6,    4,    7,    7,    5,
    7,    6,    6,    8,    9,    8,    8,    7,    5,    4,
    4,    3,    8,    3,    8,    8,    1,    1,    1,    1,
    1,    1,    1,    1,    5,    4,    2,    1,    1,    4,
    2,    3,    1,    3,    1,    2,    1,    1,    3,    3,
    2,    2,
};
final static short yydefred[] = {                         0,
    0,   89,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   20,   21,    0,    0,    7,    8,    0,   11,    0,
    0,    0,   19,   53,   47,   48,   49,   50,   52,    0,
    0,    0,    0,   90,   92,    0,    0,    0,   82,    0,
    0,   83,    0,   76,    0,    0,    0,    0,    0,    0,
    0,    0,  142,  141,    2,    6,    9,   12,   13,    0,
   88,    0,   87,    0,    0,    0,    4,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   91,   93,    0,
  118,  120,  122,  119,  117,    0,    0,  121,    0,    0,
    0,    0,    0,  138,   55,    0,    0,    0,    0,    0,
    0,   18,    0,    0,    0,    0,    0,  140,    0,  137,
  139,  133,  128,  129,    0,    0,    0,   17,   16,    0,
   86,    0,    0,    1,   69,   70,   77,   78,  114,    0,
    0,    0,    0,    0,    0,   62,    0,    0,    0,    0,
    0,    0,    0,    0,   79,   74,   80,   75,    0,   97,
    0,    0,  123,  124,   57,   54,   56,    0,    0,    0,
   36,    0,    0,    0,    0,   46,  131,    0,  135,    0,
    0,   43,    0,    0,    0,   85,    0,    0,    0,    0,
    0,    0,   63,    0,   60,  110,   84,    0,  127,  100,
    0,    0,    0,    0,    0,   35,   34,    0,   33,    0,
    0,    0,  132,    0,  136,    0,   44,   42,   41,    0,
    0,    0,    0,    0,    0,    0,  109,    0,    0,    0,
  103,    0,   95,    0,  102,    0,   96,    0,   37,   26,
    0,    0,    0,   30,    0,    0,  130,  134,    0,   40,
   39,    0,    0,    0,    0,    0,    0,    0,  126,    0,
   98,  101,    0,   99,    0,    0,    0,    0,   28,    0,
   38,    0,    0,    0,    0,    0,   61,  125,  104,   94,
   31,   32,   27,   29,    0,   23,  113,    0,  116,  115,
  106,   22,  105,
};
final static short yydgoto[] = {                          3,
   38,  211,   16,   17,   18,   19,   20,   21,   22,   62,
   23,   39,  117,  212,   24,  130,   25,   26,   27,   28,
   29,   99,   41,   42,   77,   43,   44,   45,  111,  152,
   89,  153,  154,  168,  112,  113,  114,
};
final static short yysindex[] = {                      -179,
  624,    0,    0,  754,  -40,  -13,  229,   64,  -40, -234,
    0,    0,    0,    0,  795,    0,    0,   53,    0,   67,
   74, -239,    0,    0,    0,    0,    0,    0,    0, -113,
  624,  813,  565,    0,    0,  -38, -242,  102,    0,  561,
   12,    0,   32,    0, -109,  296,  258, -228, -228, -128,
  227,  926,    0,    0,    0,    0,    0,    0,    0,  -37,
    0,  444,    0,  227, -111,  831,    0,   55,  -98, -226,
  -93,  -85,   32,  504,  227,  561,   76,    0,    0,  386,
    0,    0,    0,    0,    0,  319,  361,    0,  227,  -79,
  424,  467,  943,    0,    0,  102,  367,   14,  161, -110,
 -228,    0,  -34,   89,  177,  -87,  105,    0,  695,    0,
    0,    0,    0,    0,  230,  -23,   31,    0,    0,   46,
    0,   35,  210,    0,    0,    0,    0,    0,    0,   35,
  733,  227,  227,  554,  551,    0,  299,  129,   55,   32,
   55,   32,  151,  256,    0,    0,    0,    0,  971,    0,
  -48,  324,    0,    0,    0,    0,    0,  118,  -27,   46,
    0,  -14,   46,  -14,  -24,    0,    0,  -53,    0,  127,
  369,    0,  388,  -33,  624,    0,  164,  -28,  401,  152,
   35,  410,    0,  227,    0,    0,    0,  664,    0,    0,
 -161,  236,  198,  340, -174,    0,    0,  434,    0,  436,
  -29,  440,    0,  979,    0,  624,    0,    0,    0,  448,
  624,  224,  227,  227,  -31,  227,    0,  227,  317,  612,
    0,  957,    0,  257,    0,  228,    0,  164,    0,    0,
  -14,  -14,  -14,    0,  -25,  -14,    0,    0,  249,    0,
    0,   35,  123,  227,  415,  420,  455,  450,    0,  993,
    0,    0, -198,    0,  389,  396,  402,  187,    0,  403,
    0,  490,  460,  492,  493,  496,    0,    0,    0,    0,
    0,    0,    0,    0,  407,    0,    0,  497,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -100,    0,    0,    0,  252,    0,  404,
  476,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  541,    0,    0,    0,    0,    0,    1,    0,    0,
   24,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  542,    0,    0,    0,    0,
    0,    0,   70,    0,    0,  461,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  537,    0,    0,    0,   80,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  185,    0,    0,    0,    0,    0,    0,    0,  765,
    0,    0,    0,    0,    0,    0,    0,    0,   93,  116,
  139,  162,  854,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  872,
  779,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  278,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  642,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  209,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  433,
    0,    0,    0,    0,    0,  890,    0,    0,    0,    0,
    0,    0,    0,    0,  452,    0,    0,  908,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  751,   72,  491,    0,  756,    0,    0,    0,    4,    0,
  -60,  758,  429,  342,    0,  853,    0,    0,    0,    0,
    0,    0,  539,    0,  -71,   54,   95,  546,  457, -114,
  -59,    0,    0,  368,  725,    0,    0,
};
final static int YYTABLESIZE=1266;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   88,   75,  116,  131,   37,  204,   37,  209,  244,   65,
  191,  214,  158,   37,  233,   37,  132,  172,  258,  137,
   37,   53,   88,   81,   78,   60,   46,  160,    2,  126,
   37,   84,   88,   85,  195,  164,  192,   79,   54,    2,
   78,   88,   88,   88,   88,   88,   68,   88,   12,   13,
  103,  104,  105,   79,  156,  174,   86,  269,   87,   88,
   88,   88,   88,  270,   81,   81,   81,   81,   81,   73,
   81,  179,   15,   91,  182,   32,  224,   86,   92,   87,
    1,  229,   81,   81,   81,   81,   73,   68,    2,   68,
   68,   68,   71,    2,  221,  234,   71,  222,  201,  259,
  223,   72,   66,   51,  159,   68,   68,   68,   68,  165,
   73,   57,   73,   73,   73,   66,  134,   97,  215,  133,
   81,   81,   81,   88,   81,   58,   81,   73,   73,   73,
   73,   73,   59,   71,   90,   71,   71,   71,   72,  140,
  142,   80,  243,  245,  246,  166,  247,   86,   93,   87,
  106,   71,   71,   71,   71,  123,   66,  125,   66,   66,
   66,   67,  127,  262,   18,   64,  133,   18,   64,  185,
  128,   86,  263,   87,   66,   66,   66,   66,   88,   72,
    2,   72,   72,   72,   58,  146,  148,  144,   73,   12,
   13,  186,  217,   86,   86,   87,   87,   72,   72,   72,
   72,  157,   67,    5,   67,   67,   67,  203,   59,    6,
  149,  162,    8,  190,    2,   33,    9,   74,   10,   11,
   67,   67,   67,   67,   33,   58,   34,    2,   34,    2,
  115,   37,  171,    2,    2,   34,    2,   34,  163,   35,
    2,   35,   34,   58,    2,   81,   82,   83,   35,   59,
   35,   10,   34,   12,   13,   35,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   35,   88,   59,   88,  116,
   88,   37,   88,   88,   88,   88,   88,   88,   88,   81,
   81,   81,   81,   81,   81,   81,   81,   81,   49,   81,
  175,   81,   10,   81,  226,   81,   81,   81,   81,   81,
   81,   81,   68,   68,   68,   68,   68,   68,   68,   68,
   68,  274,   68,    2,   68,  253,   68,  101,   68,   68,
   68,   68,   68,   68,   68,   73,   73,   73,   73,   73,
   73,   73,   73,   73,  177,   73,   95,   73,  184,   73,
   37,   73,   73,   73,   73,   73,   73,   73,   71,   71,
   71,   71,   71,   71,   71,   71,   71,  248,   71,   86,
   71,   87,   71,   37,   71,   71,   71,   71,   71,   71,
   71,   66,   66,   66,   66,   66,   66,   66,   66,   66,
  187,   66,  193,   66,  194,   66,  206,   66,   66,   66,
   66,   66,   66,   66,   72,   72,   72,   72,   72,   72,
   72,   72,   72,   14,   72,   37,   72,  155,   72,  207,
   72,   72,   72,   72,   72,   72,   72,   67,   67,   67,
   67,   67,   67,   67,   67,   67,  136,   67,  208,   67,
   37,   67,   25,   67,   67,   67,   67,   67,   67,   67,
  216,   58,  213,   58,   14,   58,   58,   58,   58,  218,
   58,   24,   58,   34,   58,  264,   58,   58,  133,  227,
  265,   58,   58,  133,  228,   59,   35,   59,   37,   59,
   59,   59,   59,   25,   59,   15,   59,  231,   59,  232,
   59,   59,   33,  236,  241,   59,   59,  120,  240,  254,
  267,   25,   24,   34,    2,  266,    2,  225,  133,   47,
  278,   65,  119,  133,   65,   56,   35,   48,   10,  261,
   24,   37,   10,  271,   10,   10,   15,   10,  252,   10,
  272,   10,   56,   10,   10,    2,  273,  276,   10,   10,
  277,  282,  279,  280,   12,   13,  281,  283,   45,   30,
    5,    3,   30,  170,  129,   71,   69,  239,   70,  151,
   72,   33,    5,   30,   52,  220,   56,    0,    6,    7,
   63,    8,   34,    2,   94,    9,    0,   10,   11,   30,
   30,    0,   12,   13,  139,   35,    0,   88,   88,   88,
    0,   88,    0,   88,  100,   34,    2,   56,    0,    0,
   30,  183,   71,   69,    0,   70,    0,   72,   35,    0,
  121,    0,    0,   86,   30,   87,   71,   69,    0,   70,
    0,   72,    0,   84,   88,   85,  141,    0,    0,    0,
   84,   88,   85,    5,    0,    0,    0,   34,    2,    6,
    7,   30,    8,    0,    2,   30,    9,    0,   10,   11,
   35,  135,    0,   12,   13,    0,    0,   30,    0,    0,
    0,    0,   34,    2,    0,    0,    0,    0,  176,   88,
   14,    0,   12,   13,   14,   35,   14,   14,    0,   14,
  250,   14,    0,   14,    0,   14,   14,    0,    0,  145,
   14,   14,   84,   84,   84,    0,   84,   30,   84,   25,
   34,    2,    0,   25,    0,   25,   25,    0,   25,  118,
   25,   56,   25,   35,   25,   25,   30,    0,   24,   25,
   25,    2,   24,   30,   24,   24,    0,   24,    0,   24,
    0,   24,  147,   24,   24,    0,   30,    0,   24,   24,
    0,    0,   15,   34,    2,    0,   15,    0,   15,   15,
    0,   15,   30,   15,   30,   15,   35,   15,   15,   30,
    4,   14,   15,   15,   14,    0,    0,   50,   30,   68,
   30,    0,    0,    0,    0,   14,    0,    0,    0,    0,
   34,    2,   61,  178,    0,    0,  133,    0,    0,    0,
    0,   14,   14,   35,    0,    0,    0,    0,   30,    0,
    0,    0,   84,   88,   85,    0,   96,  102,  102,  102,
    0,   18,   61,    0,   18,   65,   68,  110,   65,    0,
    0,    0,   61,    0,    0,   88,   14,   34,    2,   64,
   68,    0,   64,    0,   65,   65,   65,   81,   82,   83,
   35,   34,    2,  169,   81,   82,   83,    0,   64,   64,
   64,    0,    0,   61,   35,    0,    0,   14,  110,    0,
    0,  102,    0,  161,    0,    0,  102,   40,    0,   61,
    0,   40,    0,    0,  110,    0,  173,    0,    5,    0,
   61,    0,  249,  189,    6,    0,    0,    8,    0,    2,
    5,    9,    0,   10,   11,    0,    6,    7,   76,    8,
    0,    2,  205,    9,    0,   10,   11,    0,   98,   61,
   12,   13,    0,  107,  110,    0,    0,    0,    0,  196,
  197,    0,  169,  199,    0,    0,  122,    0,   61,  198,
    5,  200,  202,  110,  210,   14,    6,    0,  238,    8,
    0,    2,  138,    9,    0,   10,   11,    0,   61,    0,
    0,  143,    0,  110,  205,  230,  189,    0,    0,    0,
    0,    5,    0,    0,   61,  167,   14,    6,  235,  110,
    8,   14,    2,    0,    9,    0,   10,   11,    0,    0,
   61,    0,   61,    0,  238,  110,    0,  110,    0,    0,
    0,    0,    0,    0,  180,  181,    0,    0,  255,  256,
  257,    0,    0,  260,    0,    0,    0,    0,    0,    0,
   61,    0,    0,    0,    0,  110,   81,   82,   83,    0,
    5,    0,    0,   31,    0,  275,    6,    7,    0,    8,
    0,    2,    0,    9,    0,   10,   11,    0,    0,    0,
   12,   13,    0,    0,    0,    0,  219,    0,   65,   65,
   65,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,   64,   64,   64,   55,    0,    6,    7,    0,
    8,    0,    2,    0,    9,  242,   10,   11,    0,    5,
    0,   12,   13,   67,    0,    6,    7,    0,    8,    0,
    2,    0,    9,    0,   10,   11,    0,    5,    0,   12,
   13,  124,    0,    6,    7,    0,    8,    0,    2,    0,
    9,    0,   10,   11,    0,    0,    0,   12,   13,  112,
  112,  112,    0,  112,    0,    0,  112,    0,    0,  112,
    0,  112,    0,  112,    0,  112,  112,  111,  111,  111,
    0,  111,    0,    0,  111,    0,    0,  111,    0,  111,
    0,  111,    0,  111,  111,  108,  108,  108,    0,  108,
    0,    0,  108,    0,    0,  108,    0,  108,    0,  108,
    0,  108,  108,  107,  107,  107,    0,  107,    0,    0,
  107,    0,    0,  107,    0,  107,    0,  107,    0,  107,
  107,  108,    5,    0,    0,  109,    0,    0,    6,    0,
    0,    8,    0,    2,    0,    9,    0,   10,   11,    5,
    0,  149,  109,    0,  150,    6,    0,    0,    8,    0,
    2,    0,    9,    5,   10,   11,  188,    0,  251,    6,
    0,    0,    8,    0,    2,    0,    9,    5,   10,   11,
  188,    0,    0,    6,    0,    5,    8,    0,    2,  237,
    9,    6,   10,   11,    8,    0,    2,    0,    9,    5,
   10,   11,    0,  268,    0,    6,    0,    0,    8,    0,
    2,    0,    9,    0,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   75,   45,   59,   45,   41,   40,  123,
   59,   40,  123,   45,   44,   45,   76,   41,   44,   80,
   45,  256,  123,    0,  267,  265,   40,   62,  268,  256,
   45,   60,   61,   62,   62,  123,  151,  280,  273,  268,
  267,   41,   42,   43,   44,   45,    0,   47,  277,  278,
   47,   48,   49,  280,   41,  116,   43,  256,   45,   59,
   60,   61,   62,  262,   41,   42,   43,   44,   45,    0,
   47,  131,    1,   42,  134,    4,  191,   43,   47,   45,
  260,  256,   59,   60,   61,   62,   33,   41,  268,   43,
   44,   45,    0,  268,  256,  125,   42,  259,  123,  125,
  262,   47,   31,   40,  101,   59,   60,   61,   62,  106,
   41,   59,   43,   44,   45,    0,   41,   46,  178,   44,
   41,   42,   43,  123,   45,   59,   47,   74,   59,   60,
   61,   62,   59,   41,  123,   43,   44,   45,    0,   86,
   87,   40,  214,  215,  216,   41,  218,   43,  258,   45,
  279,   59,   60,   61,   62,  267,   41,  256,   43,   44,
   45,    0,  256,   41,  265,  279,   44,  268,  279,   41,
  256,   43,  244,   45,   59,   60,   61,   62,  279,   41,
  268,   43,   44,   45,    0,   91,   92,  267,  135,  277,
  278,   41,   41,   43,   43,   45,   45,   59,   60,   61,
   62,   41,   41,  257,   43,   44,   45,  261,    0,  263,
  259,  123,  266,  262,  268,  256,  270,  256,  272,  273,
   59,   60,   61,   62,  256,   41,  267,  268,  267,  268,
  268,   45,  256,  268,  268,  267,  268,  267,   62,  280,
  268,  280,  267,   59,  268,  274,  275,  276,  280,   41,
  280,    0,  267,  277,  278,  280,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  280,  266,   59,  268,   40,
  270,   45,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,  259,  260,  261,  262,  263,  264,   60,  266,
  260,  268,   41,  270,   59,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,  259,  260,  261,  262,  263,
  264,  125,  266,  268,  268,   59,  270,   60,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  264,  125,  266,   41,  268,   40,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,  259,  260,  261,  262,  263,  264,   41,  266,   43,
  268,   45,  270,   45,  272,  273,  274,  275,  276,  277,
  278,  256,  257,  258,  259,  260,  261,  262,  263,  264,
  125,  266,   59,  268,  267,  270,  260,  272,  273,  274,
  275,  276,  277,  278,  256,  257,  258,  259,  260,  261,
  262,  263,  264,    0,  266,   45,  268,   41,  270,   41,
  272,  273,  274,  275,  276,  277,  278,  256,  257,  258,
  259,  260,  261,  262,  263,  264,   41,  266,   41,  268,
   45,  270,    0,  272,  273,  274,  275,  276,  277,  278,
   40,  257,  279,  259,   41,  261,  262,  263,  264,   40,
  266,    0,  268,  267,  270,   41,  272,  273,   44,  262,
   41,  277,  278,   44,  125,  257,  280,  259,   45,  261,
  262,  263,  264,   41,  266,    0,  268,   44,  270,   44,
  272,  273,  256,   44,  261,  277,  278,   44,   41,  262,
   41,   59,   41,  267,  268,   41,  268,  262,   44,  271,
   41,   41,   59,   44,   44,   15,  280,  279,  257,  261,
   59,   45,  261,  125,  263,  264,   41,  266,  262,  268,
  125,  270,   32,  272,  273,  268,  125,  125,  277,  278,
   41,  125,   41,   41,  277,  278,   41,   41,  261,    1,
    0,    0,    4,  115,   41,   42,   43,  206,   45,   93,
   47,  256,  257,   15,    9,  188,   66,   -1,  263,  264,
   22,  266,  267,  268,  269,  270,   -1,  272,  273,   31,
   32,   -1,  277,  278,  256,  280,   -1,   41,   42,   43,
   -1,   45,   -1,   47,   46,  267,  268,   97,   -1,   -1,
   52,   41,   42,   43,   -1,   45,   -1,   47,  280,   -1,
   62,   -1,   -1,   43,   66,   45,   42,   43,   -1,   45,
   -1,   47,   -1,   60,   61,   62,  256,   -1,   -1,   -1,
   60,   61,   62,  257,   -1,   -1,   -1,  267,  268,  263,
  264,   93,  266,   -1,  268,   97,  270,   -1,  272,  273,
  280,  256,   -1,  277,  278,   -1,   -1,  109,   -1,   -1,
   -1,   -1,  267,  268,   -1,   -1,   -1,   -1,  120,  123,
  257,   -1,  277,  278,  261,  280,  263,  264,   -1,  266,
   59,  268,   -1,  270,   -1,  272,  273,   -1,   -1,  256,
  277,  278,   41,   42,   43,   -1,   45,  149,   47,  257,
  267,  268,   -1,  261,   -1,  263,  264,   -1,  266,  256,
  268,  211,  270,  280,  272,  273,  168,   -1,  257,  277,
  278,  268,  261,  175,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  256,  272,  273,   -1,  188,   -1,  277,  278,
   -1,   -1,  257,  267,  268,   -1,  261,   -1,  263,  264,
   -1,  266,  204,  268,  206,  270,  280,  272,  273,  211,
    0,    1,  277,  278,    4,   -1,   -1,    7,  220,  256,
  222,   -1,   -1,   -1,   -1,   15,   -1,   -1,   -1,   -1,
  267,  268,   22,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   31,   32,  280,   -1,   -1,   -1,   -1,  250,   -1,
   -1,   -1,   60,   61,   62,   -1,   46,   47,   48,   49,
   -1,  265,   52,   -1,  268,   41,  256,   52,   44,   -1,
   -1,   -1,   62,   -1,   -1,  279,   66,  267,  268,   41,
  256,   -1,   44,   -1,   60,   61,   62,  274,  275,  276,
  280,  267,  268,  109,  274,  275,  276,   -1,   60,   61,
   62,   -1,   -1,   93,  280,   -1,   -1,   97,   93,   -1,
   -1,  101,   -1,  103,   -1,   -1,  106,    5,   -1,  109,
   -1,    9,   -1,   -1,  109,   -1,  116,   -1,  257,   -1,
  120,   -1,  261,  149,  263,   -1,   -1,  266,   -1,  268,
  257,  270,   -1,  272,  273,   -1,  263,  264,   36,  266,
   -1,  268,  168,  270,   -1,  272,  273,   -1,   46,  149,
  277,  278,   -1,   51,  149,   -1,   -1,   -1,   -1,  159,
  160,   -1,  188,  163,   -1,   -1,   64,   -1,  168,  162,
  257,  164,  165,  168,  174,  175,  263,   -1,  204,  266,
   -1,  268,   80,  270,   -1,  272,  273,   -1,  188,   -1,
   -1,   89,   -1,  188,  220,  195,  222,   -1,   -1,   -1,
   -1,  257,   -1,   -1,  204,  261,  206,  263,  201,  204,
  266,  211,  268,   -1,  270,   -1,  272,  273,   -1,   -1,
  220,   -1,  222,   -1,  250,  220,   -1,  222,   -1,   -1,
   -1,   -1,   -1,   -1,  132,  133,   -1,   -1,  231,  232,
  233,   -1,   -1,  236,   -1,   -1,   -1,   -1,   -1,   -1,
  250,   -1,   -1,   -1,   -1,  250,  274,  275,  276,   -1,
  257,   -1,   -1,  260,   -1,  258,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  184,   -1,  274,  275,
  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  274,  275,  276,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,  213,  272,  273,   -1,  257,
   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  257,   -1,  277,
  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
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
"declaracion_funciones : tipo FUN ID parametros_parentesis BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN parametros_parentesis BEGIN cuerpo_funcion END",
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

//#line 242 "Gramatica.y"
																	 
private static boolean RETORNO = false;
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
//#line 731 "Parser.java"
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
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 3:
//#line 11 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 4:
//#line 12 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Falta el delimitador BEGIN "+"\u001B[0m");}
break;
case 5:
//#line 13 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ " Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 10:
//#line 22 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 14:
//#line 30 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 15:
//#line 31 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 16:
//#line 34 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 17:
//#line 35 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 22:
//#line 46 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}
break;
case 23:
//#line 47 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 24:
//#line 48 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 25:
//#line 49 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 26:
//#line 50 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}
break;
case 27:
//#line 51 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango inferior "+"\u001B[0m");}
break;
case 28:
//#line 52 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 29:
//#line 53 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta rango superior "+"\u001B[0m");}
break;
case 30:
//#line 54 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 31:
//#line 55 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 32:
//#line 56 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 33:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 34:
//#line 58 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 35:
//#line 59 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 36:
//#line 60 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 37:
//#line 61 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 38:
//#line 64 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); }
break;
case 39:
//#line 65 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 41:
//#line 69 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el nombre del parametro en la funcion "+"\u001B[0m");}
break;
case 42:
//#line 70 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el tipo del parametro en la funcion "+"\u001B[0m");}
break;
case 43:
//#line 71 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 44:
//#line 72 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Se excedio el numero de parametros (1). "+"\u001B[0m");}
break;
case 51:
//#line 86 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 53:
//#line 88 "Gramatica.y"
{RETORNO = true;}
break;
case 54:
//#line 91 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 55:
//#line 92 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 56:
//#line 93 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 57:
//#line 94 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 58:
//#line 97 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 59:
//#line 98 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 60:
//#line 101 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
break;
case 61:
//#line 102 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 62:
//#line 103 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 63:
//#line 104 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  Se excedio el numero de parametros en la invocacion (1)"+"\u001B[0m");}
break;
case 69:
//#line 114 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 70:
//#line 115 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta alguno de los operandos o ambos"+"\u001B[0m");}
break;
case 71:
//#line 116 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 72:
//#line 117 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operando de la derecha"+"\u001B[0m");}
break;
case 73:
//#line 118 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita, falta el operador"+"\u001B[0m");}
break;
case 77:
//#line 124 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 78:
//#line 125 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 79:
//#line 126 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 80:
//#line 127 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  La expresion esta mal escrita"+"\u001B[0m");}
break;
case 86:
//#line 137 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ',' entre variables "+"\u001B[0m");}
break;
case 90:
//#line 148 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 91:
//#line 149 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 94:
//#line 154 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 95:
//#line 155 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 96:
//#line 156 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 97:
//#line 157 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 98:
//#line 158 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 99:
//#line 160 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 100:
//#line 161 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 101:
//#line 162 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 102:
//#line 163 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de los bloques del IF "+"\u001B[0m");}
break;
case 103:
//#line 165 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 104:
//#line 166 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 105:
//#line 169 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 106:
//#line 170 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 107:
//#line 171 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 108:
//#line 172 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 109:
//#line 173 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 110:
//#line 174 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 111:
//#line 175 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 112:
//#line 176 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 113:
//#line 179 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 114:
//#line 180 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el comparador "+"\u001B[0m");}
break;
case 115:
//#line 181 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el ')' en la condicion luego de la lista de expresiones "+"\u001B[0m");}
break;
case 116:
//#line 182 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : Falta el '(' en la condicion luego del comparador"+"\u001B[0m");}
break;
case 126:
//#line 198 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 131:
//#line 209 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el bloque de sentencias "+"\u001B[0m");}
break;
case 132:
//#line 210 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 136:
//#line 218 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 138:
//#line 224 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 139:
//#line 229 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 140:
//#line 230 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 141:
//#line 235 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 142:
//#line 236 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1232 "Parser.java"
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
