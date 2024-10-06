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
   11,   19,   19,   19,   19,   30,   30,   30,   30,   30,
   30,   30,   30,   33,   33,   33,   33,   33,   33,   32,
   32,   35,   34,   31,   31,   39,   38,   36,   36,   37,
   23,   23,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    5,    1,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    3,    1,    1,    1,    1,
    2,    8,    6,    5,    7,    9,    8,    8,    7,    5,
    4,    4,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    2,    1,    1,    3,    1,    2,    1,    1,
    1,    2,    3,    2,
};
final static short yydefred[] = {                         0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   34,   29,   30,   31,   32,   33,    0,   60,    0,
    0,    0,   53,    0,    0,   54,    0,   51,    0,    0,
    0,    0,    0,    0,    0,   94,    2,    0,    6,    0,
   58,    0,   57,    0,    0,    0,    0,    0,   61,    0,
   75,   77,   79,   76,   74,    0,    0,   78,    0,    0,
    0,    0,    0,    1,   91,   36,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   90,   93,   87,   84,
   85,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   50,    0,   92,   38,   35,   37,    0,   16,    0,
    0,   28,    0,   89,    0,    0,   22,    0,    0,   25,
    0,   56,    0,    0,    0,    0,    0,    0,   41,   71,
   55,    0,    0,    0,    0,   86,   88,    0,   23,   26,
   21,    0,    0,    0,    0,    0,   70,    0,   42,    0,
   63,    0,   80,   81,    0,   18,    0,    0,   24,   20,
    0,    0,    0,    0,   83,    0,    0,   19,    0,    0,
    0,   62,    0,    0,   67,   82,   17,   66,
};
final static short yydgoto[] = {                          2,
   32,  153,   15,   16,   17,   18,   19,   20,   21,   52,
   33,   95,  154,  129,  130,   22,   99,   23,   24,   25,
   26,   27,   81,   35,   36,  105,   58,   37,   38,   39,
   88,  162,   69,  163,  164,  123,   89,   90,   91,
};
final static short yysindex[] = {                      -254,
    0,    0,  445,  -23,  461,   -9, -195,   -1,  -23, -221,
    0,    0,    0,  -75,   19,    0,    0,    0,    0,    0,
 -163,    0,    0,    0,    0,    0,    0, -114,    0,   35,
 -213,   82,    0,  475,   -7,    0,  -14,    0, -132,  370,
  247,   73, -144,  -33,  -56,    0,    0,   81,    0,  -38,
    0,  105,    0,  -33, -122,  -33,  475,   79,    0,  -45,
    0,    0,    0,    0,    0,  -33,  -33,    0,  -33, -116,
  -33,  -33,  -56,    0,    0,    0,   75,   82,  231,   12,
  137, -110, -219, -219,   36, -236,    0,    0,    0,    0,
    0,    0,  126,  -30,  -79, -254,   -5,   58,   -5,  106,
  -33,  -33,  313, -219,  143,  146,  -14,  -14,   56,   62,
    0,    0,  133,    0,    0,    0,    0,  -73,    0,  144,
   77,    0,  138,    0,  -55,  170,    0, -254,  -25,    0,
  461,    0,  -66,  313,   76,   -5,  180,  120,    0,    0,
    0, -100,   96, -254,  -42,    0,    0,  461,    0,    0,
    0,  183,  461,  -22,  -33,  184,    0,  -33,    0,   92,
    0,  187,    0,    0,  -66,    0,  205,  -11,    0,    0,
   -5,  -33,  123, -236,    0,   -2,  -42,    0,  129,  210,
  157,    0,  132,  225,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -108,  268,  215,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,   23,    0,   47,    0,    0,  270,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  115,    0,    0,    0,    0,  131,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -37,    0,    0,
    0,   53,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,    0,  131,    0,
    0,    0,    0,    0,    0,  233,   69,   93,  495,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  511,  135,    0,    0,    0,    0,
    0,  158,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   21,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  101,    0,    0,    0,    0,    0,
  177,    0,    0,    0,    0,  196,    0,    0,    0,  527,
    0,    0,    0,  543,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  526,   15,   10,    0,  509,    0,    0,    0,  125,    0,
 -126,  192,  142,  190, -103,    0,  549,    0,    0,    0,
    0,    0,    0,  529,    0,    0,  -31,    5,   32,  287,
  236,    0,  -16,    0,    0,  140,  -49,    0,    0,
};
final static int YYTABLESIZE=815;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
   58,   94,   31,   58,   58,   58,   58,   58,   55,   58,
  127,   31,  118,    1,   58,  151,   30,   14,  167,   40,
    4,   31,   52,   48,  100,  152,    6,   71,  106,    8,
   41,    1,   72,    9,  152,   10,  124,   66,   44,   67,
  101,   58,   58,   58,   58,   58,   48,   58,    1,   48,
  183,   46,  116,   59,   66,   79,   67,   11,   12,   58,
   58,   58,   58,   52,   52,   52,   52,   52,   46,   52,
  107,  108,    1,  147,   56,   42,  122,   49,   66,   31,
   67,   52,   52,   52,   52,   58,  137,   48,   48,   48,
   48,   48,   47,   52,   52,   52,  140,   52,   66,   52,
   67,   50,  111,  112,    1,   48,   48,   48,   48,   46,
  175,   46,   46,   46,   13,   70,  157,  156,   66,  103,
   67,   60,  102,   58,  124,   73,  173,   46,   46,   46,
   46,  147,   83,   47,   84,   47,   47,   47,   39,   92,
  179,   55,   55,   55,   98,   55,  134,   55,   96,  102,
  110,   47,   47,   47,   47,   13,   16,   64,  160,   16,
  159,  161,   48,  180,   54,   94,  102,  114,   54,  184,
   58,   45,  102,   13,   45,   44,   40,  117,   44,   39,
  131,    4,  133,  139,  104,   47,  141,    6,    7,  102,
    8,  142,    1,  143,    9,   65,   10,   39,   64,  145,
    4,   11,   12,   86,  148,  144,    6,  120,  121,    8,
  149,    1,  155,    9,    7,   10,   64,   40,  128,  158,
  165,   29,    1,  172,   29,  126,  169,   16,  128,   93,
   16,   11,   12,   29,    1,   40,   65,    1,  170,   16,
   16,   58,    1,   29,    1,  176,   11,   12,  177,  178,
  185,   11,   12,  128,   65,    7,  187,   58,   58,  182,
   58,   58,  128,   58,   58,  188,   58,    4,   58,    3,
   58,  115,   58,   43,   58,   58,   58,   58,   58,   52,
   52,   27,   52,   52,  125,   52,   52,   76,   52,  168,
   52,   31,   52,  138,   52,   45,   52,   52,   52,   52,
   52,   29,    1,   48,   48,    0,   48,   48,  113,   48,
   48,    0,   48,  181,   48,    0,   48,    0,   48,    0,
   48,   48,   48,   48,   48,   46,   46,    0,   46,   46,
    0,   46,   46,    0,   46,    0,   46,   77,   46,    0,
   46,    0,   46,   46,   46,   46,   46,    0,    4,   47,
   47,  174,   47,   47,    6,   47,   47,    8,   47,    1,
   47,    9,   47,   10,   47,    0,   47,   47,   47,   47,
   47,   13,   64,   68,   65,   13,    0,   13,   13,    0,
   13,    0,   13,    0,   13,    0,   13,    1,    0,    0,
    0,   13,   13,    0,    4,   39,   11,   12,  146,   39,
    6,   39,   39,    8,   39,    1,   39,    9,   39,   10,
   39,    0,    0,    4,   64,   39,   39,  186,   64,    6,
   64,   64,    8,   64,    1,   64,    9,   64,   10,   64,
    0,    0,    0,   40,   64,   64,    0,   40,    0,   40,
   40,    0,   40,    0,   40,    0,   40,    0,   40,    0,
    0,    0,   65,   40,   40,    0,   65,    0,   65,   65,
    0,   65,    0,   65,    0,   65,    0,   65,    0,    0,
    0,    7,   65,   65,    0,    7,    0,    7,    7,    0,
    7,    0,    7,    0,    7,    0,    7,    4,    0,    0,
    0,    7,    7,    6,    7,    0,    8,    0,    1,    0,
    9,    0,   10,    4,    0,    0,    0,   11,   12,    6,
    7,    0,    8,   29,    1,   75,    9,   66,   10,   67,
    0,    0,    0,   11,   12,    3,    0,    0,   13,    0,
   13,   28,   43,   28,   64,   68,   65,    0,    0,   13,
    0,    0,   28,    0,    0,    0,   51,    0,    0,   53,
    0,    0,   34,   87,    0,    0,    0,   34,    0,    0,
    0,    0,    0,    0,    0,   13,   78,    0,   28,   82,
   51,    0,    0,   28,    0,    0,    0,    0,   57,    0,
    0,   87,    0,    0,    0,   78,   61,   62,   63,   80,
    0,    0,   85,    0,   87,    0,    0,    0,   51,    0,
    0,   28,   97,    0,   13,    0,    0,   28,  119,  119,
    0,   51,    0,    0,   28,    0,    0,  109,    0,  119,
    0,   51,    0,    0,  132,    0,    4,    0,    0,  119,
   74,   87,    6,    7,    0,    8,    0,    1,    0,    9,
    0,   10,    0,    0,    0,    0,   11,   12,   51,  135,
  136,   28,    0,  150,  119,    0,   13,    0,    0,   28,
    0,    0,    0,  119,    0,    0,    0,    0,   87,  166,
    0,    0,    0,   13,    0,    0,   28,    0,   13,    0,
    0,   28,   87,    0,    0,   51,    0,    0,   28,   87,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
    0,    4,   28,  171,    5,    0,   51,    6,    7,   28,
    8,    0,    1,    0,    9,    0,   10,    4,    0,    0,
    0,   11,   12,    6,    7,    0,    8,    0,    1,    0,
    9,    0,   10,    0,    0,    0,    0,   11,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   61,   62,
   63,   73,   73,    0,   73,    0,    0,   73,    0,    0,
   73,    0,   73,    0,   73,    0,   73,   72,   72,    0,
   72,    0,    0,   72,    0,    0,   72,    0,   72,    0,
   72,    0,   72,   69,   69,    0,   69,    0,    0,   69,
    0,    0,   69,    0,   69,    0,   69,    0,   69,   68,
   68,    0,   68,    0,    0,   68,    0,    0,   68,    0,
   68,    0,   68,    0,   68,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   45,   41,   42,   43,   44,   45,  123,   47,
   41,   45,  123,  268,  123,   41,   40,    3,  145,    5,
  257,   45,    0,   14,   56,  129,  263,   42,   60,  266,
   40,  268,   47,  270,  138,  272,   86,   43,   40,   45,
   57,   41,   42,   43,   44,   45,    0,   47,  268,   40,
  177,  273,   41,  267,   43,   41,   45,  277,  278,   59,
   60,   61,   62,   41,   42,   43,   44,   45,    0,   47,
   66,   67,  268,  123,   40,  271,   41,   59,   43,   45,
   45,   59,   60,   61,   62,  123,  103,   41,   79,   43,
   44,   45,    0,   41,   42,   43,   41,   45,   43,   47,
   45,  265,   71,   72,  268,   59,   60,   61,   62,   41,
  160,   43,   44,   45,    0,  123,   41,  134,   43,   41,
   45,   40,   44,  123,  174,  258,  158,   59,   60,   61,
   62,  181,   60,   41,  279,   43,   44,   45,    0,   59,
  172,   41,   42,   43,  267,   45,   41,   47,   44,   44,
  267,   59,   60,   61,   62,   41,  265,    0,  259,  268,
   41,  262,  153,   41,  279,   40,   44,   93,  279,   41,
  279,   41,   44,   59,   44,   41,    0,   41,   44,   41,
  260,  257,  125,   41,   60,  261,  125,  263,  264,   44,
  266,   59,  268,  267,  270,    0,  272,   59,   41,  123,
  257,  277,  278,  260,  260,   62,  263,   83,   84,  266,
   41,  268,  279,  270,    0,  272,   59,   41,   94,   40,
  125,  267,  268,   40,  267,  256,   44,  265,  104,  268,
  268,  277,  278,  267,  268,   59,   41,  268,  261,  277,
  278,  279,  268,  267,  268,   59,  277,  278,   44,  261,
   41,  277,  278,  129,   59,   41,  125,  257,  258,  262,
  260,  261,  138,  263,  264,   41,  266,    0,  268,    0,
  270,   41,  272,   41,  274,  275,  276,  277,  278,  257,
  258,  261,  260,  261,   93,  263,  264,   41,  266,  148,
  268,   45,  270,  104,  272,    9,  274,  275,  276,  277,
  278,  267,  268,  257,  258,   -1,  260,  261,   73,  263,
  264,   -1,  266,  174,  268,   -1,  270,   -1,  272,   -1,
  274,  275,  276,  277,  278,  257,  258,   -1,  260,  261,
   -1,  263,  264,   -1,  266,   -1,  268,   91,  270,   -1,
  272,   -1,  274,  275,  276,  277,  278,   -1,  257,  257,
  258,  260,  260,  261,  263,  263,  264,  266,  266,  268,
  268,  270,  270,  272,  272,   -1,  274,  275,  276,  277,
  278,  257,   60,   61,   62,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  268,   -1,   -1,
   -1,  277,  278,   -1,  257,  257,  277,  278,  261,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   -1,   -1,  257,  257,  277,  278,  261,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  257,   -1,   -1,
   -1,  277,  278,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  257,   -1,   -1,   -1,  277,  278,  263,
  264,   -1,  266,  267,  268,  269,  270,   43,  272,   45,
   -1,   -1,   -1,  277,  278,    0,   -1,   -1,    3,   -1,
    5,    3,    7,    5,   60,   61,   62,   -1,   -1,   14,
   -1,   -1,   14,   -1,   -1,   -1,   21,   -1,   -1,   21,
   -1,   -1,    4,   45,   -1,   -1,   -1,    9,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   40,   41,
   45,   -1,   -1,   45,   -1,   -1,   -1,   -1,   30,   -1,
   -1,   73,   -1,   -1,   -1,   60,  274,  275,  276,   41,
   -1,   -1,   44,   -1,   86,   -1,   -1,   -1,   73,   -1,
   -1,   73,   54,   -1,   79,   -1,   -1,   79,   83,   84,
   -1,   86,   -1,   -1,   86,   -1,   -1,   69,   -1,   94,
   -1,   96,   -1,   -1,   96,   -1,  257,   -1,   -1,  104,
  261,  123,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,   -1,   -1,   -1,  277,  278,  123,  101,
  102,  123,   -1,  128,  129,   -1,  131,   -1,   -1,  131,
   -1,   -1,   -1,  138,   -1,   -1,   -1,   -1,  160,  144,
   -1,   -1,   -1,  148,   -1,   -1,  148,   -1,  153,   -1,
   -1,  153,  174,   -1,   -1,  160,   -1,   -1,  160,  181,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  174,
   -1,  257,  174,  155,  260,   -1,  181,  263,  264,  181,
  266,   -1,  268,   -1,  270,   -1,  272,  257,   -1,   -1,
   -1,  277,  278,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,  275,
  276,  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  257,
  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,
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
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';' END_IF",
"sentencia_IF : IF condicion THEN bloque_unidad ';'",
"sentencia_IF : IF condicion THEN bloque_unidad ';' bloque_else ';'",
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
"bloque_sent_ejecutables : bloque_sent_ejecutables bloque_sentencia_simple",
"bloque_sent_ejecutables : bloque_sentencia_simple",
"bloque_sentencia_simple : sentencia_ejecutable",
"cadena : CADENAMULTILINEA",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE condicion bloque_unidad",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 198 "Gramatica.y"
																	 
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
//#line 549 "Parser.java"
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
case 67:
//#line 133 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 68:
//#line 134 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 69:
//#line 135 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 70:
//#line 136 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " : Se identifico una condicion");}
break;
case 71:
//#line 137 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 72:
//#line 138 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 73:
//#line 139 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 91:
//#line 179 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
case 93:
//#line 185 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Se identifico un WHILE ");}
break;
//#line 826 "Parser.java"
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
