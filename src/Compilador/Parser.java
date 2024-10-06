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
   11,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   30,   33,   33,   33,   33,   33,   33,   32,   32,
   35,   34,   31,   31,   39,   38,   36,   36,   37,   23,
   23,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    5,    1,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    3,    1,    1,    1,    1,
    2,   10,    8,    7,    9,    9,    7,    9,    7,    8,
    6,    7,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    2,    1,    1,    3,    1,    2,    1,    1,    1,
    2,    5,    2,
};
final static short yydefred[] = {                         0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   34,   29,   30,   31,   32,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   93,    2,    0,    6,
    0,   58,    0,   57,    0,    0,   60,    0,    0,    0,
   53,    0,    0,   54,    0,    0,   51,    0,    0,    0,
    1,   90,   36,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    5,    0,    0,    0,    0,    0,    0,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   89,    0,   86,   83,   84,    0,   91,   38,
   35,   37,    0,   16,    0,    0,   28,    0,    0,    0,
   22,    0,    0,   25,    0,   56,    0,    0,    0,    0,
    0,    0,    0,    0,   74,   76,   78,   75,   73,   77,
    0,   49,   50,    0,    0,    0,   88,    0,    0,    0,
    0,    0,   92,    0,   23,   26,   21,    0,    0,    0,
    0,    0,   41,   55,    0,    0,    0,   85,   87,    0,
   71,    0,   79,   80,    0,    0,   18,    0,    0,   24,
   20,    0,   42,    0,   69,    0,    0,    0,   82,    0,
   67,    0,    0,   19,   72,    0,   63,    0,    0,   70,
    0,    0,   68,    0,   81,   66,   17,   62,
};
final static short yydgoto[] = {                          2,
   42,  149,   15,   16,   93,   18,   19,   20,   21,   43,
   51,   77,  150,  113,  114,   22,   52,   23,   24,   25,
   26,   27,   68,   28,   54,  119,   55,   56,   57,   30,
   94,  162,  131,  163,  164,  136,   95,   96,   97,
};
final static short yysindex[] = {                      -249,
    0,    0,  273,   -4,  357,   33, -216,   41,   48, -233,
    0,    0,    0,  317,   35,    0,    0,    0,    0,    0,
 -193,    0,    0,    0,    0,    0,    0, -110,  -13,  -29,
  340,  208,   40, -172,  -43,   83,    0,    0,   54,    0,
  -40,    0,  104,    0,  -43, -113,    0,  -43, -107,  133,
    0,   90,   44,    0,   45,    7,    0,  -24,  380,  -78,
    0,    0,    0,   88,  133,  225,   52,  135, -109, -243,
 -243,   87,  141,    0,  143,  -33,  -75, -249,   90,   61,
    0,  231,  -43,  -43,  -80,  -43,  252,  -43,  -43,  380,
  -69,   38,    0,  137,    0,    0,    0,  380,    0,    0,
    0,    0,  -65,    0,  130,   74,    0,  380,  -62,  163,
    0, -249,  -31,    0,  357,    0,  -72, -243,  167,  165,
    7,    7,   85,   90,    0,    0,    0,    0,    0,    0,
  171,    0,    0,  155,  380,   76,    0, -153,  156,   91,
 -249,  -34,    0,  357,    0,    0,    0,  174,  357,  -42,
  -43,  -26,    0,    0,  -43, -119,  161,    0,    0,  394,
    0,  177,    0,    0, -118,  -72,    0,  194,  -18,    0,
    0,   90,    0,  101,    0,  189, -112,   38,    0,  -12,
    0,  197,  -34,    0,    0,   -5,    0,  200,   95,    0,
   -1,  138,    0,    8,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -102,  274,  191,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  275,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   96,    0,    0,    0,    0,    0,    0,    1,
    0,  120,   20,    0,    0,   39,    0,    0,    0,    0,
    0,    0,    0,    0,  -38,    0,    0,    0,   69,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  115,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  239,
   58,   77,    0,  127,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   84,    0,    0,    0,    0,
    0,  134,    0,    0,    0,    0,  153,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  172,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  487,   21,    2,    0,   73,    0,    0,    0,  108,    0,
 -114,  207,  145,  169,  -82,    0,    6,    0,    0,    0,
    0,    0,    0,  117,    0,    0,  -64,  -27,   70,   -6,
  -61, -106,    0,    0,    0,  113,  -70,    0,    0,
};
final static int YYTABLESIZE=666;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         76,
   58,   49,   58,   58,   58,   58,   58,  111,   58,  147,
   49,   60,   46,  103,  173,   39,   91,  120,    1,   52,
   58,  137,   58,   14,    1,   31,   48,  168,  134,   73,
  148,   49,   39,   11,   12,   29,  139,   67,   48,   37,
   72,   58,   58,   58,   58,   58,  143,   58,   88,  176,
   79,    1,   66,   89,   33,  121,  122,   46,  182,   58,
   52,   52,   52,   52,   52,  159,   52,   39,  192,  148,
  188,   41,   32,  157,    1,   17,   47,   17,   52,   48,
   35,   48,   48,   48,   58,   87,   17,   36,   86,  179,
  174,  124,  101,   40,   83,   13,   84,   48,   46,   70,
   46,   46,   46,   17,   17,  160,   71,  137,  161,   52,
   52,   52,   74,   52,   39,   52,   46,   47,  159,   47,
   47,   47,   48,   58,   55,   55,   55,  107,   55,   83,
   55,   84,   83,   40,   84,   47,   13,   44,   17,  160,
  160,  185,  175,  181,   86,   53,  160,   78,   69,  187,
   39,   53,   64,   80,   13,   39,  172,  132,  133,   81,
   45,   53,   16,   45,   53,   16,   85,   44,   45,   45,
   44,   65,   82,   39,   40,  102,   58,  105,  106,   98,
   99,  108,   76,  112,  115,  117,  123,   17,  135,  118,
    7,  141,   40,   64,  116,  138,  142,  144,   53,   53,
   53,  140,   53,  145,   53,   53,  151,  153,   86,  154,
  155,   64,   65,  156,  165,  166,   17,  170,  171,  177,
  112,   17,  110,   47,    1,  112,   16,   75,   59,   16,
   65,    7,   47,   90,    1,  180,    1,  183,   16,   16,
   58,    1,  184,   11,   12,   11,   12,  186,   63,  190,
   11,   12,   49,   47,    1,  191,  193,   58,  194,  112,
  196,   58,  197,   58,   58,  100,   58,   53,   58,  198,
   58,   53,   58,    4,    3,   49,   52,   58,   58,   43,
   52,  109,   52,   52,   27,   52,  152,   52,  169,   52,
  189,   52,    0,    0,    4,   48,   52,   52,   64,   48,
    6,   48,   48,    8,   48,    1,   48,    9,   48,   10,
   48,  128,  130,  129,   46,   48,   48,    0,   46,    0,
   46,   46,    0,   46,    0,   46,    0,   46,    0,   46,
    0,    0,    4,   47,   46,   46,  158,   47,    6,   47,
   47,    8,   47,    1,   47,    9,   47,   10,   47,    0,
    0,    4,   13,   47,   47,  195,   13,    6,   13,   13,
    8,   13,    1,   13,    9,   13,   10,   13,    0,    0,
    0,   39,   13,   13,    0,   39,    0,   39,   39,    0,
   39,    0,   39,    0,   39,    0,   39,    0,    0,    0,
   40,   39,   39,    0,   40,    0,   40,   40,    0,   40,
    0,   40,    0,   40,    0,   40,    0,    0,    0,   64,
   40,   40,    0,   64,    0,   64,   64,    0,   64,    0,
   64,    0,   64,    0,   64,    0,    0,    0,   65,   64,
   64,    0,   65,    0,   65,   65,    0,   65,    0,   65,
    0,   65,    0,   65,    0,    0,    0,    7,   65,   65,
    0,    7,    0,    7,    7,    0,    7,    0,    7,    0,
    7,    0,    7,    0,    4,    0,    0,    7,    7,    0,
    6,    7,    0,    8,   47,    1,   62,    9,    0,   10,
    0,    4,    0,    0,   11,   12,    3,    6,    7,   13,
    8,   13,    1,   34,    9,    0,   10,   47,    1,    0,
   13,   11,   12,    0,    0,    0,    0,   11,   12,    0,
    0,    0,    0,    0,    0,   50,    0,   13,   65,    0,
    0,   50,    0,    0,    0,  125,  126,  127,    0,    4,
    0,   50,    5,    0,   50,    6,    7,    0,    8,    0,
    1,    0,    9,    0,   10,    0,    0,    0,    0,   11,
   12,    0,   13,    0,    0,    0,  104,  104,    0,    0,
    0,    0,  104,    0,    0,    0,    0,    0,   65,   50,
   50,    0,   50,    4,   50,   50,    0,   38,    0,    6,
    7,    0,    8,    0,    1,    0,    9,    0,   10,    0,
    0,    0,    0,   11,   12,    0,    4,    0,  146,  104,
   61,   13,    6,    7,  104,    8,    0,    1,    0,    9,
    0,   10,    0,    4,    0,    0,   11,   12,    0,    6,
    7,    0,    8,    0,    1,    0,    9,  167,   10,    0,
   13,    0,    0,   11,   12,   13,    4,   50,  104,   92,
    0,   50,    6,    0,    0,    8,    0,    1,    0,    9,
    4,   10,    0,  178,    0,    0,    6,    0,    0,    8,
    0,    1,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   41,   42,   43,   44,   45,   41,   47,   41,
   45,   41,  123,  123,   41,   14,   41,   82,  268,    0,
  123,   92,   29,    3,  268,    5,   40,  142,   90,   36,
  113,   45,   31,  277,  278,   40,   98,   32,    0,  273,
   35,   41,   42,   43,   44,   45,  108,   47,   42,  156,
   45,  268,   32,   47,  271,   83,   84,    0,  165,   59,
   41,   42,   43,   44,   45,  136,   47,   66,  183,  152,
  177,  265,   40,  135,  268,    3,    0,    5,   59,   41,
   40,   43,   44,   45,  123,   41,   14,   40,   44,  160,
  155,   86,   41,   59,   43,    0,   45,   59,   41,   60,
   43,   44,   45,   31,   32,  259,  279,  178,  262,   41,
   42,   43,   59,   45,    0,   47,   59,   41,  189,   43,
   44,   45,   40,  123,   41,   42,   43,   41,   45,   43,
   47,   45,   43,    0,   45,   59,   41,   21,   66,  259,
  259,   41,  262,  262,   44,   29,  259,   44,   32,  262,
  149,   35,    0,  267,   59,   41,  151,   88,   89,  267,
   41,   45,  265,   44,   48,  268,  123,   41,  279,  279,
   44,    0,   40,   59,   41,   41,  279,   70,   71,  258,
   93,   41,   40,   76,  260,  125,  267,  115,  258,   82,
    0,   62,   59,   41,   78,   59,  123,  260,   82,   83,
   84,  267,   86,   41,   88,   89,  279,   41,   44,  125,
   40,   59,   41,   59,   59,  125,  144,   44,  261,   59,
  113,  149,  256,  267,  268,  118,  265,  268,  258,  268,
   59,   41,  267,  258,  268,   59,  268,   44,  277,  278,
  279,  268,  261,  277,  278,  277,  278,   59,   41,  262,
  277,  278,   45,  267,  268,   59,  262,  257,   59,  152,
  262,  261,  125,  263,  264,   41,  266,  151,  268,  262,
  270,  155,  272,    0,    0,   45,  257,  277,  278,   41,
  261,   75,  263,  264,  261,  266,  118,  268,  144,  270,
  178,  272,   -1,   -1,  257,  257,  277,  278,   91,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   60,   61,   62,  257,  277,  278,   -1,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   -1,   -1,  257,  257,  277,  278,  261,  261,  263,  263,
  264,  266,  266,  268,  268,  270,  270,  272,  272,   -1,
   -1,  257,  257,  277,  278,  261,  261,  263,  263,  264,
  266,  266,  268,  268,  270,  270,  272,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,
  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,  257,
  277,  278,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,   -1,   -1,  257,  277,
  278,   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,   -1,   -1,  257,  277,  278,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,  257,   -1,   -1,  277,  278,   -1,
  263,  264,   -1,  266,  267,  268,  269,  270,   -1,  272,
   -1,  257,   -1,   -1,  277,  278,    0,  263,  264,    3,
  266,    5,  268,    7,  270,   -1,  272,  267,  268,   -1,
   14,  277,  278,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   29,   -1,   31,   32,   -1,
   -1,   35,   -1,   -1,   -1,  274,  275,  276,   -1,  257,
   -1,   45,  260,   -1,   48,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,   -1,   -1,   -1,  277,
  278,   -1,   66,   -1,   -1,   -1,   70,   71,   -1,   -1,
   -1,   -1,   76,   -1,   -1,   -1,   -1,   -1,   82,   83,
   84,   -1,   86,  257,   88,   89,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,   -1,  277,  278,   -1,  257,   -1,  112,  113,
  261,  115,  263,  264,  118,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,  257,   -1,   -1,  277,  278,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,  141,  272,   -1,
  144,   -1,   -1,  277,  278,  149,  257,  151,  152,  260,
   -1,  155,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  257,  272,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,
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
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END",
"END_IF","OUTF","TYPEDEF","FUN","RET","CTE","ID","CADENAMULTILINEA","WHILE",
"TRIPLE","GOTO","ETIQUETA","MAYORIGUAL","MENORIGUAL","DISTINTO","INTEGER",
"DOUBLE","ASIGNACION",
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
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';'",
"sentencia_IF : IF '(' condicion ')' THEN bloque_unidad ';' bloque_else ';'",
"sentencia_IF : IF condicion ')' THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion ')' THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF '(' condicion THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF '(' condicion THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' END_IF",
"condicion : '(' list_expre ')' comparador '(' list_expre ')'",
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
"bloque_sent_ejecutables : bloque_sent_ejecutables bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE '(' condicion ')' bloque_unidad",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 195 "Gramatica.y"
																	 
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
//#line 521 "Parser.java"
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
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en IF  ");}
break;
case 67:
//#line 131 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en IF  ");}
break;
case 68:
//#line 132 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en IF  ");}
break;
case 69:
//#line 133 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en IF  ");}
break;
case 70:
//#line 134 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en IF  ");}
break;
case 71:
//#line 135 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en IF  ");}
break;
case 90:
//#line 177 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 790 "Parser.java"
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
