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
   25,   26,   26,   27,   27,   17,   17,   17,   17,   28,
   28,   28,   29,   29,   29,   29,   10,   10,   24,    1,
   11,   11,   19,   19,   19,   19,   19,   19,   19,   30,
   30,   30,   30,   30,   30,   30,   30,   33,   33,   33,
   33,   33,   33,   32,   32,   35,   34,   31,   31,   39,
   38,   36,   36,   37,   23,   20,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    1,    1,    3,    1,    3,    3,    1,    1,    3,
    3,    1,    1,    1,    1,    4,    3,    1,    1,    1,
    1,    2,    8,    6,    5,    7,    6,    4,    7,    9,
    8,    8,    7,    5,    4,    4,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    4,    2,    1,    1,    3,
    1,    3,    1,    1,    1,    3,    3,    2,
};
final static short yydefred[] = {                         0,
   60,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   34,   29,   30,   31,   32,   33,    0,   49,   61,
    0,    0,    0,   54,    0,    0,   55,    0,   52,    0,
    0,    0,    0,    0,    0,    0,   98,    2,    0,    6,
    0,   59,    0,   58,    0,    0,    0,    0,    0,   62,
    0,   79,   81,   83,   80,   78,    0,    0,   82,    0,
    0,    0,    0,    0,    1,   95,   36,    0,    0,    0,
    0,    0,    0,    0,    0,   97,    0,   94,   96,   91,
   88,   89,    5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   43,    0,    0,    0,    0,
    0,    0,   50,   51,    0,   68,    0,    0,   84,   85,
   38,   35,   37,    0,   16,    0,    0,   28,    0,   93,
    0,    0,   22,    0,   25,    0,   57,    0,    0,    0,
    0,    0,   26,   41,   75,   56,    0,   87,    0,    0,
    0,    0,    0,   90,    0,    0,   23,   21,    0,    0,
    0,    0,    0,   74,    0,    0,    0,   64,    0,   67,
    0,   18,    0,   92,    0,   24,   20,    0,    0,    0,
   86,   69,    0,    0,   19,    0,    0,   63,    0,    0,
   71,   17,   70,
};
final static short yydgoto[] = {                          2,
   33,  160,   15,   16,   17,   18,   19,   20,   21,   53,
   34,   96,  161,  134,  106,   22,  100,   23,   24,   25,
   26,   27,   81,   36,   37,  107,   59,   38,   39,   40,
   89,  118,   70,  119,  120,  129,   90,   91,   92,
};
final static short yysindex[] = {                      -238,
    0,    0,  327,  141,  463,   -4, -172,   40,  141, -161,
    0,    0,    0,  429,   68,    0,    0,    0,    0,    0,
 -148,    0,    0,    0,    0,    0,    0, -110,    0,    0,
  150, -139,  101,    0,  100,   21,    0,   -8,    0, -102,
  447,  248,   90, -116,  122,  287,    0,    0,  107,    0,
  -40,    0,  120,    0,  122,  -95,  122,  100,   77,    0,
  -43,    0,    0,    0,    0,    0,  -41,  -41,    0,  122,
  -93,  -41,  -41,  368,    0,    0,    0,  101,  -24,   12,
  139, -108, -240, -240,   13,    0, -237,    0,    0,    0,
    0,    0,    0,  142,  245,  -71, -238,   60,   67,   60,
   78,  122,  122,  371, -238,    0,  152,  153,   -8,   -8,
   34,   82,    0,    0,  548,    0,  149,  151,    0,    0,
    0,    0,    0,  -58,    0,  154,   88,    0,  -56,    0,
  -46,  178,    0,  -27,    0,  463,    0,  -59,  371,   59,
   60,  181,    0,    0,    0,    0, -237,    0, -120,  -31,
   97, -238,  -29,    0, -237,  463,    0,    0,  179,  463,
  -16,  122,  190,    0,  122,  -49,  534,    0,  188,    0,
  -59,    0,  205,    0,   -9,    0,    0,   60,  122,  105,
    0,    0,   -2,  -29,    0,  129,  222,    0,  145,  225,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -100,  268,  215,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,   24,    0,   47,    0,    0,
  272,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,    0,    0,    0,    0,  143,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -36,    0,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  135,    0,  143,
    0,    0,    0,    0,    0,    0,    0,  233,   70,   93,
  396,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  486,
  144,    0,    0,    0,    0,    0,    0,    0,  158,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  159,    0,    0,    0,    0,    0,    0,  177,    0,    0,
    0,    0,  196,    0,    0,    0,  503,    0,    0,  520,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  524,   22,   18,    0,   36,    0,    0,    0,   64,    0,
 -134,  189,  156,    0,  -83,    0,  547,    0,    0,    0,
    0,    0,    0,  527,    0,    0,  -39,   27,  -32,  286,
  223,  157,   -6,    0,    0,  162,  -66,    0,    0,
};
final static int YYTABLESIZE=820;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         95,
   59,   32,  155,   32,   59,   59,   59,   59,   59,  155,
   59,  135,   56,  158,  124,   32,  121,  101,  173,    4,
  130,  108,   59,   53,   14,    6,   41,    1,    8,    1,
    1,   49,    9,   72,   10,   42,   11,   12,   73,  113,
  114,   59,   59,   59,   59,   59,   48,   59,  148,  189,
  159,  102,  122,  128,   67,   67,   68,   68,   49,   59,
   59,   59,   59,   79,   53,   53,   53,   53,   53,   46,
   53,   53,   53,   53,  145,   53,   67,   53,   68,   45,
  130,   88,   53,   53,   53,   53,   59,   48,  174,   48,
   48,   48,   47,  109,  110,    1,   49,  142,   43,  164,
  148,   67,   67,   68,   68,   48,   48,   48,   48,   88,
   46,   47,   46,   46,   46,   13,   51,  104,  139,    1,
  103,  103,   88,   59,  105,  180,   50,   60,   46,   46,
   46,   46,  163,   47,   39,   47,   47,   47,  167,  186,
   61,  168,   67,   71,   68,  187,  126,  127,  103,   83,
   88,   47,   47,   47,   47,   74,   13,   65,  105,   65,
   69,   66,   84,   97,   16,   93,   32,   16,   55,  190,
   55,   99,  103,  112,   13,   39,   40,   49,   59,  123,
   31,   95,   88,   45,   44,   32,   45,   44,  136,   57,
   88,  138,  144,   39,   32,   66,  103,  105,   65,   56,
   56,   56,   88,   56,  154,   56,  146,  149,  151,  150,
  153,  181,   29,  156,    7,  152,   65,   40,  157,  162,
  165,  171,  176,   30,    1,   30,    1,   94,   16,  179,
  170,   16,    4,   11,   12,   40,   66,   30,    6,    7,
    1,    8,   59,    1,  177,    9,  183,   10,  184,   11,
   12,  185,   11,   12,   66,    7,   59,   59,   59,  188,
   59,   59,  191,   59,   59,  193,   59,    4,   59,  192,
   59,    3,   59,   42,   59,   59,   59,   59,   59,   53,
   53,   53,  131,   53,   53,  133,   53,   53,   77,   53,
   27,   53,   32,   53,   46,   53,  117,   53,   53,   53,
   53,   53,   48,   48,   48,  169,   48,   48,  166,   48,
   48,  175,   48,    0,   48,    0,   48,    0,   48,    0,
   48,   48,   48,   48,   48,   46,   46,   46,    0,   46,
   46,    0,   46,   46,    0,   46,    0,   46,    0,   46,
    0,   46,    0,   46,   46,   46,   46,   46,   47,   47,
   47,    0,   47,   47,    0,   47,   47,    0,   47,    0,
   47,    0,   47,    0,   47,    0,   47,   47,   47,   47,
   47,    0,   13,   62,   63,   64,   13,   29,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,   30,    1,
    0,   39,   13,   13,    0,   39,   29,   39,   39,    0,
   39,    0,   39,    0,   39,   29,   39,   30,    1,    0,
    0,   39,   39,    0,   65,    0,   30,    1,   65,    0,
   65,   65,    0,   65,    0,   65,    0,   65,    0,   65,
   65,   69,   66,   40,   65,   65,    0,   40,    0,   40,
   40,    0,   40,    0,   40,    0,   40,    0,   40,    0,
    0,    0,   66,   40,   40,    0,   66,    0,   66,   66,
    0,   66,    0,   66,    0,   66,    0,   66,    0,    0,
    0,    7,   66,   66,    0,    7,    0,    7,    7,    0,
    7,    0,    7,    0,    7,    0,    7,    0,    0,    0,
    0,    7,    7,    0,    0,    0,    0,    0,    0,    0,
  132,    0,    0,   29,    4,    0,    0,    0,    0,    0,
    6,    7,    1,    8,   30,    1,   76,    9,    0,   10,
    0,   11,   12,    3,   11,   12,   13,    0,   13,   28,
   44,   28,    0,    0,    0,    0,    0,   13,    0,    0,
   28,    0,   86,    4,   52,    0,   87,   54,    0,    6,
   35,    0,    8,    0,    1,   35,    9,    0,   10,    0,
    0,    0,    0,    0,   13,   78,    0,   28,   82,   52,
    0,    0,   28,    0,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    4,   78,    0,    5,    0,   80,    6,
    7,   85,    8,    0,    1,    0,    9,   52,   10,    0,
   28,   98,   13,   11,   12,   28,  125,  125,    0,    0,
   52,    0,    0,   28,    0,    0,  111,    0,  125,    0,
   52,    0,    0,  137,    4,    0,  115,   87,  143,  116,
    6,    0,    0,    8,    0,    1,    0,    9,   52,   10,
    0,   28,    0,    0,   62,   63,   64,    0,  140,  141,
    0,   77,   77,   77,    0,   77,    0,  125,   77,   13,
    0,   77,   28,   77,    0,   77,    0,   77,    0,    0,
   52,    0,    0,   28,    0,  172,    0,    0,   52,   13,
    0,   28,   28,   13,    0,    4,   28,    0,    0,   48,
   52,    6,    7,   28,    8,    0,    1,    0,    9,    0,
   10,    0,    0,    4,    0,   11,   12,   75,  178,    6,
    7,    0,    8,    0,    1,    0,    9,    0,   10,    4,
    0,    0,    0,   11,   12,    6,    7,    0,    8,    0,
    1,    0,    9,    0,   10,    0,    0,    0,    0,   11,
   12,   76,   76,   76,    0,   76,    0,    0,   76,    0,
    0,   76,    0,   76,    0,   76,    0,   76,   73,   73,
   73,    0,   73,    0,    0,   73,    0,    0,   73,    0,
   73,    0,   73,    0,   73,   72,   72,   72,    0,   72,
    0,    0,   72,    0,    0,   72,    0,   72,    0,   72,
    4,   72,    0,  147,    0,  182,    6,    0,    0,    8,
    0,    1,    0,    9,    4,   10,    0,  147,    0,    0,
    6,    0,    0,    8,    0,    1,    0,    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   59,   45,   41,   42,   43,   44,   45,   59,
   47,   95,  123,   41,  123,   45,   41,   57,  153,  257,
   87,   61,  123,    0,    3,  263,    5,  268,  266,  268,
  268,   14,  270,   42,  272,   40,  277,  278,   47,   72,
   73,   41,   42,   43,   44,   45,    0,   47,  115,  184,
  134,   58,   41,   41,   43,   43,   45,   45,   41,   59,
   60,   61,   62,   42,   41,   42,   43,   44,   45,    0,
   47,   41,   42,   43,   41,   45,   43,   47,   45,   40,
  147,   46,   59,   60,   61,   62,  123,   41,  155,   43,
   44,   45,    0,   67,   68,  268,   79,  104,  271,   41,
  167,   43,   43,   45,   45,   59,   60,   61,   62,   74,
   41,  273,   43,   44,   45,    0,  265,   41,   41,  268,
   44,   44,   87,  123,   61,  165,   59,  267,   59,   60,
   61,   62,  139,   41,    0,   43,   44,   45,  259,  179,
   40,  262,   43,  123,   45,   41,   83,   84,   44,   60,
  115,   59,   60,   61,   62,  258,   41,    0,   95,   60,
   61,   62,  279,   44,  265,   59,   45,  268,  279,   41,
  279,  267,   44,  267,   59,   41,    0,  160,  279,   41,
   40,   40,  147,   41,   41,   45,   44,   44,  260,   40,
  155,  125,   41,   59,   45,    0,   44,  134,   41,   41,
   42,   43,  167,   45,  261,   47,  125,   59,  267,   59,
  123,  261,  256,  260,    0,   62,   59,   41,   41,  279,
   40,  125,   44,  267,  268,  267,  268,  268,  265,   40,
  262,  268,  257,  277,  278,   59,   41,  267,  263,  264,
  268,  266,  279,  268,  261,  270,   59,  272,   44,  277,
  278,  261,  277,  278,   59,   41,  256,  257,  258,  262,
  260,  261,   41,  263,  264,   41,  266,    0,  268,  125,
  270,    0,  272,   41,  274,  275,  276,  277,  278,  256,
  257,  258,   94,  260,  261,   41,  263,  264,   41,  266,
  261,  268,   45,  270,    9,  272,   74,  274,  275,  276,
  277,  278,  256,  257,  258,  149,  260,  261,  147,  263,
  264,  156,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
  274,  275,  276,  277,  278,  256,  257,  258,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,  274,  275,  276,  277,  278,  256,  257,
  258,   -1,  260,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,  274,  275,  276,  277,
  278,   -1,  257,  274,  275,  276,  261,  256,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  267,  268,
   -1,  257,  277,  278,   -1,  261,  256,  263,  264,   -1,
  266,   -1,  268,   -1,  270,  256,  272,  267,  268,   -1,
   -1,  277,  278,   -1,  257,   -1,  267,  268,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   60,   61,   62,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,   -1,   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,
  263,  264,  268,  266,  267,  268,  269,  270,   -1,  272,
   -1,  277,  278,    0,  277,  278,    3,   -1,    5,    3,
    7,    5,   -1,   -1,   -1,   -1,   -1,   14,   -1,   -1,
   14,   -1,  256,  257,   21,   -1,  260,   21,   -1,  263,
    4,   -1,  266,   -1,  268,    9,  270,   -1,  272,   -1,
   -1,   -1,   -1,   -1,   41,   42,   -1,   41,   42,   46,
   -1,   -1,   46,   -1,   -1,   -1,   -1,   31,   -1,   -1,
   -1,   -1,   -1,  257,   61,   -1,  260,   -1,   42,  263,
  264,   45,  266,   -1,  268,   -1,  270,   74,  272,   -1,
   74,   55,   79,  277,  278,   79,   83,   84,   -1,   -1,
   87,   -1,   -1,   87,   -1,   -1,   70,   -1,   95,   -1,
   97,   -1,   -1,   97,  257,   -1,  259,  260,  105,  262,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,  115,  272,
   -1,  115,   -1,   -1,  274,  275,  276,   -1,  102,  103,
   -1,  256,  257,  258,   -1,  260,   -1,  134,  263,  136,
   -1,  266,  136,  268,   -1,  270,   -1,  272,   -1,   -1,
  147,   -1,   -1,  147,   -1,  152,   -1,   -1,  155,  156,
   -1,  155,  156,  160,   -1,  257,  160,   -1,   -1,  261,
  167,  263,  264,  167,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,   -1,  257,   -1,  277,  278,  261,  162,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  257,
   -1,   -1,   -1,  277,  278,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,   -1,   -1,   -1,  277,
  278,  256,  257,  258,   -1,  260,   -1,   -1,  263,   -1,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  256,  257,
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
"parametro_real : list_expre",
"parametro_real : parametro",
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

//#line 202 "Gramatica.y"
																	 
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
//#line 556 "Parser.java"
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
case 42:
//#line 87 "Gramatica.y"
{System.out.println(" PARAMETRO REAL ");}
break;
case 43:
//#line 88 "Gramatica.y"
{System.out.println(" se realizo una conversion ");}
break;
case 46:
//#line 95 "Gramatica.y"
{System.out.println(" Se identifico una EXPRESION_ARIT ");}
break;
case 47:
//#line 96 "Gramatica.y"
{System.out.println(" Se identifico una EXPRESION_ARIT ");}
break;
case 49:
//#line 98 "Gramatica.y"
{System.out.println(" La expresion está mal escrita ");}
break;
case 61:
//#line 123 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 62:
//#line 124 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 63:
//#line 127 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Identifico un IF ");}
break;
case 64:
//#line 128 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Reconocio un IF ");}
break;
case 65:
//#line 129 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el END_IF en IF  ");}
break;
case 66:
//#line 130 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta el END_IF en IF ");}
break;
case 67:
//#line 131 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN");}
break;
case 68:
//#line 132 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Falta de contenido en bloque THEN.");}
break;
case 69:
//#line 133 "Gramatica.y"
{{System.out.println(" Error falta cuerpo en el ELSE ");};}
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
case 74:
//#line 140 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " : Se identifico una condicion");}
break;
case 75:
//#line 141 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el '(' en la condicion ");}
break;
case 76:
//#line 142 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el ')' en la condicion ");}
break;
case 77:
//#line 143 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Faltan los parentesis en la condicion ");}
break;
case 95:
//#line 183 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
case 96:
//#line 188 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Se identifico un WHILE ");}
break;
case 97:
//#line 189 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " falta el cuerpo del WHILE ");}
break;
//#line 869 "Parser.java"
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
