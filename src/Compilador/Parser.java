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



//#line 2 "gramatica.y"
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
    4,    4,    6,    9,    9,   11,   11,    8,    8,    7,
    7,   13,   13,   13,   15,   14,   16,    5,    5,    5,
    5,    5,    5,   22,   22,   22,   22,   18,   18,   25,
   25,   26,   26,   17,   17,   17,   17,   27,   27,   27,
   28,   28,   28,   28,   10,   10,   24,    1,   12,   12,
   19,   19,   19,   19,   19,   19,   19,   29,   29,   29,
   29,   29,   29,   29,   29,   32,   32,   32,   32,   32,
   32,   31,   31,   34,   33,   30,   30,   38,   37,   35,
   35,   36,   23,   20,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    1,    9,    6,    7,
    6,    3,    2,    3,    2,    1,    4,    1,    1,    1,
    1,    1,    1,    4,    3,    4,    4,    3,    6,    4,
    7,    3,    1,    3,    3,    1,    1,    3,    3,    1,
    1,    1,    1,    4,    3,    1,    1,    1,    1,    2,
    8,    6,    5,    7,    6,    4,    7,    9,    8,    8,
    7,    5,    4,    4,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    4,    2,    1,    1,    3,    1,    3,
    1,    1,    1,    3,    3,    2,
};
final static short yydefred[] = {                         0,
   58,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   16,   17,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   15,   33,   28,   29,   30,   31,   32,    0,   47,
   59,    0,    0,    0,   52,    0,    0,   53,    0,   50,
    0,    0,    0,    0,    0,    0,    0,   96,    2,    0,
    6,    0,   57,    0,   56,    0,    0,    0,    0,    0,
   60,    0,   77,   79,   81,   78,   76,    0,    0,   80,
    0,    0,    0,    0,    0,    1,   93,   35,    0,    0,
    0,    0,    0,    0,    0,    0,   95,    0,   92,   94,
   89,   86,   87,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,   49,    0,   66,    0,    0,   82,   83,   37,
   34,   36,    0,   14,    0,    0,   27,    0,   91,    0,
    0,   23,    0,    0,    0,   55,    0,    0,    0,    0,
    0,    0,   40,   73,   54,    0,   85,    0,    0,    0,
    0,    0,   88,    0,    0,   24,   25,   22,    0,    0,
    0,    0,   72,    0,    0,    0,    0,   62,    0,   65,
    0,   19,    0,   90,    0,   21,    0,    0,    0,    0,
   84,   67,    0,    0,   20,    0,    0,   41,   61,    0,
    0,   69,   18,   68,
};
final static short yydgoto[] = {                          2,
   34,  159,   15,   16,   17,   18,   19,   20,   21,   54,
   22,   35,   97,  160,  134,   23,  101,   24,   25,   26,
   27,   28,   82,   37,   38,   60,   39,   40,   41,   90,
  117,   71,  118,  119,  128,   91,   92,   93,
};
final static short yysindex[] = {                      -241,
    0,    0,  286,  -37,  -85,   10, -122,   18,  -37, -224,
    0,    0,    0,  319,   35,    0,    0,    0,    0,    0,
 -118,    0,    0,    0,    0,    0,    0,    0, -110,    0,
    0,  122, -194,   37,    0,  -25,  -28,    0,  -17,    0,
 -148,  383,  248,   52, -153,  141,  501,    0,    0,   68,
    0,  -40,    0,   84,    0,  141, -134,  141,  -25,  107,
    0,  -43,    0,    0,    0,    0,    0,  -41,  -41,    0,
  141, -102,  -41,  -41,  515,    0,    0,    0,   37,  -24,
   11,  125, -108, -246, -246,   14,    0,  157,    0,    0,
    0,    0,    0,    0,  133,  245,  -86, -241,   57,   55,
   57,  119,  141,  141,  142,  144,   77,  -17,  -17,   78,
   64,    0,    0,  543,    0,  123,  131,    0,    0,    0,
    0,    0,  -66,    0,  145,   83,    0,  -49,    0,  -51,
  169,    0, -241,  170,  -85,    0,  -63,  142,   99,   57,
  180,  141,    0,    0,    0,  157,    0,  -98,  -21,   96,
 -241,  -29,    0,  157,  -85,    0,    0,    0,  -85,  -38,
  141,  182,    0,  141,  100,  -47,  529,    0,  186,    0,
  -63,    0,  203,    0,   -9,    0,   57,  141,  147,  219,
    0,    0,    4,  -29,    0,  154,  222,    0,    0,  143,
  229,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -109,  272,  215,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,   24,    0,   47,    0,
    0,  274,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  116,    0,    0,    0,    0,  156,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,    0,   33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  135,    0,
  156,    0,    0,    0,    0,    0,    0,   70,   93,  432,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  449,  164,
    0,    0,    0,    0,    0,    0,    0,  158,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,    0,    0,    0,    0,    0,  177,    0,    0,    0,
    0,    0,  196,    0,    0,    0,  467,    0,    0,    0,
  484,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  524,   36,    9,    0,  414,    0,    0,    0,  -56,    0,
  233, -131,  202,  136,    0,    0,  568,    0,    0,    0,
    0,    0,    0,  527,    0,  -39,  -35,    8,  297,  234,
  166,  -33,    0,    0,  172,  -50,    0,    0,
};
final static int YYTABLESIZE=815;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         96,
   57,   33,   32,   33,   57,   57,   57,   33,   57,  154,
   57,  154,   57,   57,  123,   33,  120,   68,  102,   69,
  173,    1,   50,   51,   73,  103,    1,  125,  126,   74,
   11,   12,  108,  109,   66,   70,   67,  129,   14,  133,
   42,   57,   57,   57,   57,   57,   46,   57,   48,   43,
   50,  121,  190,   68,  127,   69,   68,   46,   69,   57,
   57,   57,   57,  147,   51,   51,   51,   51,   51,   44,
   51,  141,   61,   51,   51,   51,   62,   51,   80,   51,
  112,  113,   51,   51,   51,   51,   57,   46,   50,   46,
   46,   46,   45,   51,   72,  129,   54,   54,   54,   68,
   54,   69,   54,  174,  162,   46,   46,   46,   46,   75,
   44,   84,   44,   44,   44,   13,  147,  143,  144,   68,
   68,   69,   69,   57,  179,   85,   94,   98,   44,   44,
   44,   44,  100,   45,   38,   45,   45,   45,  186,  163,
  180,   68,   68,   69,   69,    1,   52,  105,   44,    1,
  104,   45,   45,   45,   45,   14,   13,   63,   14,  138,
  167,   58,  104,  168,  111,  122,   33,   50,   56,   57,
   56,    4,   96,  135,   13,   38,   39,    6,    7,  137,
    8,  148,    1,  142,    9,   33,   10,  187,  145,  149,
  104,   11,   12,   38,  191,   64,   43,  104,   63,   43,
  150,   66,   70,   67,   42,  152,  151,   42,  155,  156,
  158,  153,   30,  181,    7,  161,   63,   39,   30,  164,
  171,  178,  176,   31,    1,   31,    1,   95,   14,   31,
    1,   14,    4,   11,   12,   39,   64,   31,    6,    7,
  170,    8,   57,    1,  183,    9,  184,   10,   63,   64,
   65,  185,   11,   12,   64,    7,   57,   57,   57,  188,
   57,   57,  192,   57,   57,  189,   57,  193,   57,  194,
   57,    4,   57,    3,   57,   57,   57,   57,   57,   51,
   51,   51,   26,   51,   51,  132,   51,   51,   78,   51,
  175,   51,   33,   51,  106,   51,  130,   51,   51,   51,
   51,   51,   46,   46,   46,   47,   46,   46,  116,   46,
   46,    0,   46,  169,   46,    0,   46,  166,   46,    0,
   46,   46,   46,   46,   46,   44,   44,   44,    0,   44,
   44,    0,   44,   44,    0,   44,    0,   44,    0,   44,
    0,   44,    0,   44,   44,   44,   44,   44,   45,   45,
   45,    0,   45,   45,    0,   45,   45,    0,   45,    0,
   45,    0,   45,    0,   45,    0,   45,   45,   45,   45,
   45,    0,   13,    0,    0,    0,   13,   30,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,   31,    1,
    0,   38,   13,   13,    0,   38,   30,   38,   38,    0,
   38,    0,   38,    0,   38,    0,   38,   31,    1,    0,
    0,   38,   38,    4,   63,   63,   64,   65,   63,    6,
   63,   63,    8,   63,    1,   63,    9,   63,   10,   63,
    0,    0,    0,   39,   63,   63,    0,   39,    0,   39,
   39,    0,   39,    0,   39,    0,   39,    0,   39,    0,
    0,    0,   64,   39,   39,    0,   64,    0,   64,   64,
   89,   64,    0,   64,    0,   64,    0,   64,    0,    0,
    0,    7,   64,   64,    0,    7,    0,    7,    7,    0,
    7,    0,    7,    0,    7,    0,    7,    0,   89,    0,
    0,    7,    7,    0,    0,    0,    0,    0,    0,    0,
  131,   89,    0,   30,    4,    0,    0,    0,    0,    0,
    6,    7,    1,    8,   31,    1,   77,    9,    0,   10,
    0,   11,   12,    3,   11,   12,   13,   89,   13,   29,
   45,   29,    0,    0,    0,    0,    0,   13,    0,    0,
   29,    0,    4,    0,   53,    5,    0,   55,    6,    7,
    0,    8,    0,    1,    0,    9,    0,   10,    0,   89,
    0,    0,   11,   12,    0,   13,   79,   89,   29,   83,
   53,   36,    0,   29,    0,    4,   36,    0,    0,   49,
   89,    6,    7,    0,    8,    0,    1,    0,    9,    0,
   10,    0,    0,    0,    0,   11,   12,    0,   53,   59,
    0,   29,    0,   13,    0,    0,   29,  124,  124,    0,
   81,   53,    0,   86,   29,    0,    0,    0,    0,  124,
    0,   53,    0,   99,  136,    0,    0,    0,    0,  107,
    0,    0,    0,    0,    0,    0,    0,   53,  110,    4,
   29,    0,    0,   76,    0,    6,    7,    0,    8,    0,
    1,    0,    9,    0,   10,    0,  157,    0,   13,   11,
   12,   29,    0,    0,    0,    0,    0,    0,    0,   53,
  139,  140,   29,    0,  172,    0,    0,   53,   13,    0,
   29,   29,   13,    0,    0,   29,    0,   75,   75,   75,
   53,   75,    0,   29,   75,    0,    0,   75,    0,   75,
    0,   75,    0,   75,   74,   74,   74,    0,   74,  165,
    0,   74,    0,    0,   74,    0,   74,    0,   74,    0,
   74,    0,   71,   71,   71,    0,   71,    0,  177,   71,
    0,    0,   71,    0,   71,    0,   71,    0,   71,   70,
   70,   70,    0,   70,    0,    0,   70,    0,    0,   70,
    0,   70,    0,   70,    0,   70,   87,    4,    0,    0,
   88,    0,    0,    6,    0,    0,    8,    0,    1,    0,
    9,    4,   10,  114,   88,    0,  115,    6,    0,    0,
    8,    0,    1,    0,    9,    4,   10,    0,  146,    0,
  182,    6,    0,    0,    8,    0,    1,    0,    9,    4,
   10,    0,  146,    0,    0,    6,    0,    0,    8,    0,
    1,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   40,   45,   41,   42,   43,   45,   45,   59,
   47,   59,  123,  123,  123,   45,   41,   43,   58,   45,
  152,  268,   14,    0,   42,   59,  268,   84,   85,   47,
  277,  278,   68,   69,   60,   61,   62,   88,    3,   96,
    5,   41,   42,   43,   44,   45,    0,   47,  273,   40,
   42,   41,  184,   43,   41,   45,   43,   40,   45,   59,
   60,   61,   62,  114,   41,   42,   43,   44,   45,    0,
   47,  105,  267,   41,   42,   43,   40,   45,   43,   47,
   73,   74,   59,   60,   61,   62,  123,   41,   80,   43,
   44,   45,    0,   59,  123,  146,   41,   42,   43,   43,
   45,   45,   47,  154,  138,   59,   60,   61,   62,  258,
   41,   60,   43,   44,   45,    0,  167,   41,   41,   43,
   43,   45,   45,  123,  164,  279,   59,   44,   59,   60,
   61,   62,  267,   41,    0,   43,   44,   45,  178,   41,
   41,   43,   43,   45,   45,  268,  265,   41,  271,  268,
   44,   59,   60,   61,   62,  265,   41,    0,  268,   41,
  259,   40,   44,  262,  267,   41,   45,  159,  279,  279,
  279,  257,   40,  260,   59,   41,    0,  263,  264,  125,
  266,   59,  268,   40,  270,   45,  272,   41,  125,   59,
   44,  277,  278,   59,   41,    0,   41,   44,   41,   44,
  267,   60,   61,   62,   41,  123,   62,   44,  260,   41,
   41,  261,  256,  261,    0,  279,   59,   41,  256,   40,
  125,   40,  261,  267,  268,  267,  268,  268,  265,  267,
  268,  268,  257,  277,  278,   59,   41,  267,  263,  264,
  262,  266,  279,  268,   59,  270,   44,  272,  274,  275,
  276,  261,  277,  278,   59,   41,  256,  257,  258,   41,
  260,  261,   41,  263,  264,  262,  266,  125,  268,   41,
  270,    0,  272,    0,  274,  275,  276,  277,  278,  256,
  257,  258,  261,  260,  261,   41,  263,  264,   41,  266,
  155,  268,   45,  270,   62,  272,   95,  274,  275,  276,
  277,  278,  256,  257,  258,    9,  260,  261,   75,  263,
  264,   -1,  266,  148,  268,   -1,  270,  146,  272,   -1,
  274,  275,  276,  277,  278,  256,  257,  258,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,  274,  275,  276,  277,  278,  256,  257,
  258,   -1,  260,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,  274,  275,  276,  277,
  278,   -1,  257,   -1,   -1,   -1,  261,  256,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  267,  268,
   -1,  257,  277,  278,   -1,  261,  256,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  267,  268,   -1,
   -1,  277,  278,  257,  257,  274,  275,  276,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   47,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   75,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,   88,   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,
  263,  264,  268,  266,  267,  268,  269,  270,   -1,  272,
   -1,  277,  278,    0,  277,  278,    3,  114,    5,    3,
    7,    5,   -1,   -1,   -1,   -1,   -1,   14,   -1,   -1,
   14,   -1,  257,   -1,   21,  260,   -1,   21,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,  146,
   -1,   -1,  277,  278,   -1,   42,   43,  154,   42,   43,
   47,    4,   -1,   47,   -1,  257,    9,   -1,   -1,  261,
  167,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,   -1,   -1,   -1,  277,  278,   -1,   75,   32,
   -1,   75,   -1,   80,   -1,   -1,   80,   84,   85,   -1,
   43,   88,   -1,   46,   88,   -1,   -1,   -1,   -1,   96,
   -1,   98,   -1,   56,   98,   -1,   -1,   -1,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  114,   71,  257,
  114,   -1,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,  133,   -1,  135,  277,
  278,  135,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  146,
  103,  104,  146,   -1,  151,   -1,   -1,  154,  155,   -1,
  154,  155,  159,   -1,   -1,  159,   -1,  256,  257,  258,
  167,  260,   -1,  167,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  256,  257,  258,   -1,  260,  142,
   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,  256,  257,  258,   -1,  260,   -1,  161,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  256,
  257,  258,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  256,  257,   -1,   -1,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  257,  272,  259,  260,   -1,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  257,  272,   -1,  260,   -1,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,
  272,   -1,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,
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
"tipo : ID_simple",
"tipo : tipo_primitivo",
"tipo_primitivo : INTEGER",
"tipo_primitivo : DOUBLE",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID_simple",
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
};

//#line 195 "gramatica.y"
																	 
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
//#line 554 "Parser.java"
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
//#line 9 "gramatica.y"
{System.out.println(" Se identifico el cuerpo_programa");}
break;
case 2:
//#line 10 "gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador BEGIN ");}
break;
case 3:
//#line 11 "gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador END ");}
break;
case 4:
//#line 12 "gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan los delimitadores del programa ");}
break;
case 7:
//#line 17 "gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia ");}
break;
case 14:
//#line 34 "gramatica.y"
{System.out.println(" Se identifico el ID de una clase como declaracion ");}
break;
case 20:
//#line 46 "gramatica.y"
{if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");RETORNO=false;}}
break;
case 21:
//#line 47 "gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
break;
case 23:
//#line 51 "gramatica.y"
{System.out.println(" Erro: Falta el parametro en la funcion ");}
break;
case 24:
//#line 52 "gramatica.y"
{System.out.println(" ERROR AL DECLARAR EL PARAMETRO FORMAL. ");}
break;
case 33:
//#line 69 "gramatica.y"
{RETORNO = true;}
break;
case 34:
//#line 72 "gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 35:
//#line 73 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el parametro del OUTF  ");}
break;
case 36:
//#line 74 "gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 37:
//#line 75 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + ":  Parametro incorrecto en sentencia OUTF. ");}
break;
case 38:
//#line 78 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 39:
//#line 79 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 40:
//#line 82 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
break;
case 41:
//#line 83 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion con conversion ");}
break;
case 44:
//#line 90 "gramatica.y"
{System.out.println(" Se identifico una EXPRESION_ARIT ");}
break;
case 45:
//#line 91 "gramatica.y"
{System.out.println(" Se identifico una EXPRESION_ARIT ");}
break;
case 47:
//#line 93 "gramatica.y"
{System.out.println(" La expresion está mal escrita ");}
break;
case 59:
//#line 118 "gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 60:
//#line 119 "gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 61:
//#line 122 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Identifico un IF ");}
break;
case 62:
//#line 123 "gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Reconocio un IF ");}
break;
case 63:
//#line 124 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el END_IF en IF  ");}
break;
case 64:
//#line 125 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta el END_IF en IF ");}
break;
case 65:
//#line 126 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN");}
break;
case 66:
//#line 127 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN.");}
break;
case 67:
//#line 128 "gramatica.y"
{{System.out.println(" Error falta cuerpo en el ELSE ");};}
break;
case 69:
//#line 132 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 70:
//#line 133 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 71:
//#line 134 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 72:
//#line 135 "gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " : Se identifico una condicion");}
break;
case 73:
//#line 136 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 74:
//#line 137 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 75:
//#line 138 "gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 93:
//#line 176 "gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
case 94:
//#line 181 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Se identifico un WHILE ");}
break;
case 95:
//#line 182 "gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " falta el cuerpo del WHILE ");}
break;
//#line 867 "Parser.java"
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
