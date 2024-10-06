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
    0,    0,    0,    0,    2,    2,    2,    3,    3,    4,
    4,    4,    6,    9,    9,    9,    8,    8,    7,    7,
   12,   12,   12,   14,   14,   15,   13,   16,    5,    5,
    5,    5,    5,    5,   22,   22,   22,   22,   18,   18,
   25,   25,   26,   27,   27,   17,   17,   17,   28,   28,
   28,   29,   29,   29,   29,   10,   10,   24,    1,   11,
   11,   19,   19,   19,   19,   19,   19,   19,   30,   30,
   30,   30,   30,   30,   30,   30,   33,   33,   33,   33,
   33,   33,   32,   32,   35,   34,   31,   31,   39,   38,
   36,   36,   37,   23,   20,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    5,    1,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    3,    1,    1,    1,    1,
    2,    8,    6,    5,    7,    6,    4,    7,    9,    8,
    8,    7,    5,    4,    4,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    4,    2,    1,    1,    3,    1,
    3,    1,    1,    1,    3,    3,    2,
};
final static short yydefred[] = {                         0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   34,   29,   30,   31,   32,   33,    0,   60,    0,
    0,    0,   53,    0,    0,   54,    0,   51,    0,    0,
    0,    0,    0,    0,    0,   97,    2,    0,    6,    0,
   58,    0,   57,    0,    0,    0,    0,    0,   61,    0,
   78,   80,   82,   79,   77,    0,    0,   81,    0,    0,
    0,    0,    0,    1,   94,   36,    0,    0,    0,    0,
    0,    0,    0,    0,   96,    0,   93,   95,   90,   87,
   88,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   50,    0,   67,    0,    0,   83,   84,   38,   35,
   37,    0,   16,    0,    0,   28,    0,   92,    0,    0,
   22,    0,    0,   25,    0,   56,    0,    0,    0,    0,
    0,    0,   41,   74,   55,    0,   86,    0,    0,    0,
    0,    0,   89,    0,    0,   23,   26,   21,    0,    0,
    0,    0,    0,   73,    0,   42,    0,    0,   63,    0,
   66,    0,   18,    0,   91,    0,   24,   20,    0,    0,
    0,   85,   68,    0,    0,   19,    0,    0,   62,    0,
    0,   70,   17,   69,
};
final static short yydgoto[] = {                          2,
   32,  160,   15,   16,   17,   18,   19,   20,   21,   52,
   33,   95,  161,  133,  134,   22,   99,   23,   24,   25,
   26,   27,   80,   35,   36,  105,   58,   37,   38,   39,
   88,  116,   69,  117,  118,  127,   89,   90,   91,
};
final static short yysindex[] = {                      -253,
    0,    0,  316,  -22,  470,   -7, -143,   10,  -22, -232,
    0,    0,    0,  436,   -8,    0,    0,    0,    0,    0,
 -117,    0,    0,    0,    0,    0,    0, -110,    0,  122,
 -194,   38,    0,  -41,  -43,    0,    7,    0, -164,  454,
  248,   57, -158,  -42,  290,    0,    0,   74,    0,  -40,
    0,  105,    0,  -42, -111,  -42,  -41,  129,    0,  261,
    0,    0,    0,    0,    0,  -42,  -42,    0,  -42, -103,
  -42,  -42,  376,    0,    0,    0,   38,  231,   56,  119,
 -107, -240, -240,   77,    0, -168,    0,    0,    0,    0,
    0,    0,  126,  -29,  -86, -253,  -16,   60,  -16,  148,
  -42,  -42,  142, -240,  139,  137,    7,    7,  120,   83,
    0,    0,  538,    0,  152,  155,    0,    0,    0,    0,
    0,  -54,    0,  157,   97,    0,  -49,    0,  -39,  181,
    0, -253,  -24,    0,  470,    0,  -56,  142,  143,  -16,
  184,   98,    0,    0,    0, -168,    0,  -69,  -32,  106,
 -253,  -20,    0, -168,  470,    0,    0,    0,  194,  470,
  -11,  -42,  200,    0,  -42,    0,  -45,  524,    0,  192,
    0,  -56,    0,  208,    0,   -1,    0,    0,  -16,  -42,
  154,    0,    0,    4,  -20,    0,  156,  222,    0,  145,
  227,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -97,  274,  215,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,   24,    0,   47,    0,    0,  283,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  116,    0,    0,    0,    0,  165,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -36,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  135,    0,  165,    0,
    0,    0,    0,    0,    0,  245,   70,   93,  362,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  398,  166,
    0,    0,    0,    0,    0,    0,    0,  158,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  100,    0,    0,    0,    0,    0,    0,  177,    0,
    0,    0,    0,  196,    0,    0,    0,  493,    0,    0,
  510,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  527,   31,   18,    0,   37,    0,    0,    0,  -30,    0,
 -130,  202,  159,  193, -102,    0,  548,    0,    0,    0,
    0,    0,    0,  530,    0,    0,  -21,  -10,   55,  300,
  239,  168,    2,    0,    0,  172,   33,    0,    0,
};
final static int YYTABLESIZE=810;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         94,
   58,   66,   31,   67,   58,   58,   58,   58,   58,  154,
   58,  131,   55,  154,    1,  122,  158,   30,   64,   68,
   65,  174,   31,   52,   31,   58,   66,    1,   67,  104,
  159,   48,   41,   14,  100,   40,   11,   12,  106,  159,
   46,   58,   58,   58,   58,   58,   48,   58,   71,   44,
   49,  124,  125,   72,  190,  107,  108,   48,  101,   58,
   58,   58,   58,  132,   52,   52,   52,   52,   52,   46,
   52,   78,   59,  132,   52,   52,   52,   60,   52,   70,
   52,   87,   52,   52,   52,   52,   58,   48,    4,   48,
   48,   48,   47,   73,    6,   48,  120,    8,   66,    1,
   67,    9,  132,   10,  141,   48,   48,   48,   48,   87,
   46,  132,   46,   46,   46,   13,   82,  126,  128,   66,
   83,   67,   87,   58,    1,  111,  112,   42,   46,   46,
   46,   46,   92,   47,   39,   47,   47,   47,  166,  163,
   55,   55,   55,  181,   55,  147,   55,   50,   96,   87,
    1,   47,   47,   47,   47,   98,   13,   64,  187,  121,
  144,   56,   66,  110,   67,   94,   31,   16,   54,  103,
   16,   54,  102,  135,   13,   39,   40,   48,  128,  143,
  102,   58,   87,  164,  137,   66,  175,   67,  138,  168,
   87,  102,  169,   39,  188,   65,  191,  102,   64,  102,
  147,   64,   68,   65,   87,   45,   44,  145,   45,   44,
  148,  153,  150,  149,    7,  182,   64,   40,  151,  152,
  155,  156,  162,  165,   29,    1,  130,   93,   16,  171,
  172,   16,   61,   62,   63,   40,   65,  177,    1,  180,
   16,   16,   58,    1,   29,    1,   29,   11,   12,  178,
  184,  185,   11,   12,   65,    7,   58,   58,   58,  186,
   58,   58,  192,   58,   58,  189,   58,  194,   58,  193,
   58,  119,   58,    4,   58,   58,   58,   58,   58,   52,
   52,   52,    3,   52,   52,   43,   52,   52,   76,   52,
   27,   52,   31,   52,  129,   52,  142,   52,   52,   52,
   52,   52,   48,   48,   48,   31,   48,   48,   45,   48,
   48,  115,   48,  176,   48,  170,   48,  167,   48,    0,
   48,   48,   48,   48,   48,   46,   46,   46,    0,   46,
   46,    0,   46,   46,    0,   46,    0,   46,    0,   46,
    0,   46,    0,   46,   46,   46,   46,   46,   47,   47,
   47,    0,   47,   47,    0,   47,   47,    0,   47,    0,
   47,    0,   47,    0,   47,    1,   47,   47,   47,   47,
   47,    0,   13,    0,   11,   12,   13,    0,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,   29,    1,
    0,   39,   13,   13,    0,   39,    0,   39,   39,    0,
   39,    0,   39,    0,   39,    0,   39,    0,    0,    0,
    0,   39,   39,    0,   64,   61,   62,   63,   64,    0,
   64,   64,    0,   64,    0,   64,    0,   64,    0,   64,
    0,    0,    0,   40,   64,   64,    0,   40,    0,   40,
   40,    0,   40,    0,   40,    0,   40,    0,   40,    0,
    0,    0,   65,   40,   40,    0,   65,    0,   65,   65,
    0,   65,    0,   65,    0,   65,    0,   65,    0,    0,
    0,    7,   65,   65,    0,    7,    0,    7,    7,    0,
    7,    0,    7,    0,    7,    0,    7,    4,    0,    0,
    0,    7,    7,    6,    7,    0,    8,    0,    1,    0,
    9,    0,   10,    0,    4,    0,    0,   11,   12,    0,
    6,    7,    0,    8,   29,    1,   75,    9,    0,   10,
    0,    0,    0,    0,   11,   12,    3,   29,    1,   13,
    0,   13,   28,   43,   28,    0,    0,   11,   12,    0,
   13,    0,    0,   28,    0,   85,    4,   51,    0,   86,
   53,   34,    6,    0,    0,    8,   34,    1,    0,    9,
    0,   10,    0,    0,    0,    0,   13,   77,    0,   28,
   81,   51,    4,    0,   28,    5,    0,   57,    6,    7,
    0,    8,    0,    1,    0,    9,   77,   10,   79,    0,
    0,   84,   11,   12,    0,    0,    0,    0,    0,   51,
    0,   97,   28,    0,   13,    0,    0,   28,  123,  123,
    0,    0,   51,    0,    0,   28,  109,   76,   76,   76,
  123,   76,   51,    0,   76,  136,    0,   76,    0,   76,
  123,   76,    4,   76,  113,   86,    0,  114,    6,   51,
    0,    8,   28,    1,    0,    9,    0,   10,  139,  140,
    0,    0,    0,   75,   75,   75,    0,   75,  157,  123,
   75,   13,    0,   75,   28,   75,    0,   75,  123,   75,
    0,    0,   51,    0,    0,   28,    0,  173,    0,    0,
   51,   13,    0,   28,   28,    0,   13,    0,    0,   28,
    0,    0,    4,    0,   51,    0,   47,   28,    6,    7,
    0,    8,    0,    1,    0,    9,    0,   10,    0,  179,
    4,    0,   11,   12,   74,    0,    6,    7,    0,    8,
    0,    1,    0,    9,    0,   10,    4,    0,    0,    0,
   11,   12,    6,    7,    0,    8,    0,    1,    0,    9,
    0,   10,    0,    0,    0,    0,   11,   12,   72,   72,
   72,    0,   72,    0,    0,   72,    0,    0,   72,    0,
   72,    0,   72,    0,   72,   71,   71,   71,    0,   71,
    0,    0,   71,    0,    0,   71,    0,   71,    0,   71,
    4,   71,    0,  146,    0,  183,    6,    0,    0,    8,
    0,    1,    0,    9,    4,   10,    0,  146,    0,    0,
    6,    0,    0,    8,    0,    1,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   45,   45,   41,   42,   43,   44,   45,   59,
   47,   41,  123,   59,  268,  123,   41,   40,   60,   61,
   62,  152,   45,    0,   45,  123,   43,  268,   45,   60,
  133,   14,   40,    3,   56,    5,  277,  278,   60,  142,
  273,   41,   42,   43,   44,   45,    0,   47,   42,   40,
   59,   82,   83,   47,  185,   66,   67,   40,   57,   59,
   60,   61,   62,   94,   41,   42,   43,   44,   45,    0,
   47,   41,  267,  104,   41,   42,   43,   40,   45,  123,
   47,   45,   59,   60,   61,   62,  123,   41,  257,   43,
   44,   45,    0,  258,  263,   78,   41,  266,   43,  268,
   45,  270,  133,  272,  103,   59,   60,   61,   62,   73,
   41,  142,   43,   44,   45,    0,   60,   41,   86,   43,
  279,   45,   86,  123,  268,   71,   72,  271,   59,   60,
   61,   62,   59,   41,    0,   43,   44,   45,   41,  138,
   41,   42,   43,  165,   45,  113,   47,  265,   44,  113,
  268,   59,   60,   61,   62,  267,   41,    0,  180,   41,
   41,   40,   43,  267,   45,   40,   45,  265,  279,   41,
  268,  279,   44,  260,   59,   41,    0,  160,  146,   41,
   44,  279,  146,   41,  125,   43,  154,   45,   41,  259,
  154,   44,  262,   59,   41,    0,   41,   44,   41,   44,
  168,   60,   61,   62,  168,   41,   41,  125,   44,   44,
   59,  261,  267,   59,    0,  261,   59,   41,   62,  123,
  260,   41,  279,   40,  267,  268,  256,  268,  265,  262,
  125,  268,  274,  275,  276,   59,   41,   44,  268,   40,
  277,  278,  279,  268,  267,  268,  267,  277,  278,  261,
   59,   44,  277,  278,   59,   41,  256,  257,  258,  261,
  260,  261,   41,  263,  264,  262,  266,   41,  268,  125,
  270,   41,  272,    0,  274,  275,  276,  277,  278,  256,
  257,  258,    0,  260,  261,   41,  263,  264,   41,  266,
  261,  268,   45,  270,   93,  272,  104,  274,  275,  276,
  277,  278,  256,  257,  258,   45,  260,  261,    9,  263,
  264,   73,  266,  155,  268,  148,  270,  146,  272,   -1,
  274,  275,  276,  277,  278,  256,  257,  258,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,  274,  275,  276,  277,  278,  256,  257,
  258,   -1,  260,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  268,  274,  275,  276,  277,
  278,   -1,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  267,  268,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,
   -1,  277,  278,   -1,  257,  274,  275,  276,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  257,   -1,   -1,
   -1,  277,  278,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,  257,   -1,   -1,  277,  278,   -1,
  263,  264,   -1,  266,  267,  268,  269,  270,   -1,  272,
   -1,   -1,   -1,   -1,  277,  278,    0,  267,  268,    3,
   -1,    5,    3,    7,    5,   -1,   -1,  277,  278,   -1,
   14,   -1,   -1,   14,   -1,  256,  257,   21,   -1,  260,
   21,    4,  263,   -1,   -1,  266,    9,  268,   -1,  270,
   -1,  272,   -1,   -1,   -1,   -1,   40,   41,   -1,   40,
   41,   45,  257,   -1,   45,  260,   -1,   30,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   60,  272,   41,   -1,
   -1,   44,  277,  278,   -1,   -1,   -1,   -1,   -1,   73,
   -1,   54,   73,   -1,   78,   -1,   -1,   78,   82,   83,
   -1,   -1,   86,   -1,   -1,   86,   69,  256,  257,  258,
   94,  260,   96,   -1,  263,   96,   -1,  266,   -1,  268,
  104,  270,  257,  272,  259,  260,   -1,  262,  263,  113,
   -1,  266,  113,  268,   -1,  270,   -1,  272,  101,  102,
   -1,   -1,   -1,  256,  257,  258,   -1,  260,  132,  133,
  263,  135,   -1,  266,  135,  268,   -1,  270,  142,  272,
   -1,   -1,  146,   -1,   -1,  146,   -1,  151,   -1,   -1,
  154,  155,   -1,  154,  155,   -1,  160,   -1,   -1,  160,
   -1,   -1,  257,   -1,  168,   -1,  261,  168,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,  162,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  257,   -1,   -1,   -1,
  277,  278,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,   -1,   -1,   -1,  277,  278,  256,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  256,  257,  258,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  257,  272,   -1,  260,   -1,  262,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,  257,  272,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
};
}
final static short YYFINAL=2;
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
"programa : ID_simple sentencias END",
"programa : ID_simple BEGIN sentencias",
"programa : ID_simple sentencias",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencia ';'",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables",
"tipo : INTEGER",
"tipo : DOUBLE",
"tipo : ID_simple",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID_simple",
"declaracion_funciones : tipo FUN ID parametros_parentesis BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN parametros_parentesis BEGIN cuerpo_funcion END",
"parametros_parentesis : '(' parametros_formal ')'",
"parametros_parentesis : '(' ')'",
"parametros_parentesis : '(' error ')'",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID_simple",
"cuerpo_funcion : sentencias",
"retorno : RET '(' expresion_arit ')'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : outf_rule",
"sentencia_ejecutable : retorno",
"outf_rule : OUTF '(' expresion_arit ')'",
"outf_rule : OUTF '(' ')'",
"outf_rule : OUTF '(' cadena ')'",
"outf_rule : OUTF '(' sentencias ')'",
"asignacion : variable_simple ASIGNACION expresion_arit",
"asignacion : variable_simple '{' CTE '}' ASIGNACION expresion_arit",
"invocacion : ID_simple '(' parametro_real ')'",
"invocacion : ID_simple '(' tipo parametros_formal ')'",
"parametro_real : list_expre",
"list_expre : list_expre ',' expresion_arit",
"list_expre : expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
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
};

//#line 201 "Gramatica.y"
																	 
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
	System.out.println(" La key es " + key);
	String keyNeg = "-" + key;
	if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
		AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
	}
	AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
	System.out.println("En la linea " + AnalizadorLexico.saltoDeLinea + " se reconocio token negativo ");
	// es ultimo ya decrementa
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
		AnalizadorLexico.TablaDeSimbolos.remove(key);
	}
}
private static boolean estaRango(String key) {
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
		if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
			AnalizadorLexico.TablaDeSimbolos.remove(key);
			yyerror("La CTE de la linea " + AnalizadorLexico.saltoDeLinea + " está fuera de rango.");
			return false;
		}
	}
	return true;
}
//#line 553 "Parser.java"
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
{System.out.println(" Se identifico el cuerpo_programa");}
break;
case 2:
//#line 10 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador BEGIN ");}
break;
case 3:
//#line 11 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador END ");}
break;
case 4:
//#line 12 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan los delimitadores del programa ");}
break;
case 7:
//#line 17 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia ");}
break;
case 16:
//#line 36 "Gramatica.y"
{System.out.println(" Se identifico el ID de una clase como declaracion ");}
break;
case 19:
//#line 43 "Gramatica.y"
{if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");RETORNO=false;}}
break;
case 20:
//#line 44 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
break;
case 22:
//#line 48 "Gramatica.y"
{System.out.println(" Erro: Faltan los parametros en la funcion ");}
break;
case 23:
//#line 49 "Gramatica.y"
{System.out.println(" ERROR AL DECLARAR LOS PARAMETROS FORMALES. ");}
break;
case 34:
//#line 71 "Gramatica.y"
{RETORNO = true;}
break;
case 35:
//#line 74 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 36:
//#line 75 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el parametro del OUTF  ");}
break;
case 37:
//#line 76 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 38:
//#line 77 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + ":  Parametro incorrecto en sentencia OUTF. ");}
break;
case 39:
//#line 80 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 40:
//#line 81 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 60:
//#line 122 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 61:
//#line 123 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 62:
//#line 126 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Identifico un IF ");}
break;
case 63:
//#line 127 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Reconocio un IF ");}
break;
case 64:
//#line 128 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el END_IF en IF  ");}
break;
case 65:
//#line 129 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta el END_IF en IF ");}
break;
case 66:
//#line 130 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN");}
break;
case 67:
//#line 131 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN.");}
break;
case 68:
//#line 132 "Gramatica.y"
{{System.out.println(" Error falta cuerpo en el ELSE ");};}
break;
case 70:
//#line 136 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 71:
//#line 137 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 72:
//#line 138 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 73:
//#line 139 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " : Se identifico una condicion");}
break;
case 74:
//#line 140 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 75:
//#line 141 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 76:
//#line 142 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 94:
//#line 182 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
case 95:
//#line 187 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Se identifico un WHILE ");}
break;
case 96:
//#line 188 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " falta el cuerpo del WHILE ");}
break;
//#line 846 "Parser.java"
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
