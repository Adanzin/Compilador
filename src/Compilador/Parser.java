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
   11,   19,   19,   30,   33,   33,   33,   33,   33,   33,
   32,   32,   35,   34,   31,   31,   39,   38,   36,   36,
   37,   23,   23,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    4,    3,    4,    4,    3,    6,
    4,    5,    1,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    3,    1,    1,    1,    1,
    2,   10,    8,    7,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    2,    1,    1,    3,    1,    2,    1,
    1,    1,    2,    5,    2,
};
final static short yydefred[] = {                         0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   34,   29,   30,   31,   32,   33,    0,    0,    0,
    0,    0,    0,    0,    0,   85,    2,    0,    6,    0,
   58,    0,   57,    0,    0,    0,    0,    1,   60,   82,
   36,    0,    0,    0,    0,   53,    0,    0,    0,   54,
    0,   51,    0,    0,    0,    0,    0,    0,    5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   61,   83,
    0,   38,   35,    0,    0,   37,    0,    0,    0,   16,
    0,    0,   28,    0,    0,    0,    0,   22,    0,    0,
   25,    0,   56,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   49,   50,    0,    0,    0,    0,   81,
   84,   78,   75,   76,    0,   23,   26,   21,    0,    0,
    0,    0,    0,   66,   68,   70,   67,   65,   69,    0,
    0,    0,   41,    0,   18,    0,   55,    0,   80,    0,
   24,   20,    0,    0,    0,   42,    0,   77,   79,   19,
    0,    0,   63,    0,   71,   72,    0,   64,    0,   74,
    0,   17,    0,   62,   73,
};
final static short yydgoto[] = {                          2,
   65,  130,   15,   16,   17,   18,   19,   20,   21,   42,
   56,   72,  131,  100,  101,   22,   76,   23,   24,   25,
   26,   27,   58,   28,   60,  109,   77,   61,   62,   47,
  121,  164,  140,  165,  166,  148,  122,  123,  124,
};
final static short yysindex[] = {                      -246,
    0,    0,  -80,   -2, -105,   15, -231,   34,   36, -217,
    0,    0,    0,  -61,   27,    0,    0,    0,    0,    0,
 -193,    0,    0,    0,    0,    0,    0, -110,   41,  241,
  169,    8, -179,  -43,   41,    0,    0,   48,    0,  -40,
    0,   65,    0,  -43, -153,  -43,   88,    0,    0,    0,
    0, -136,   50,  105,  185,    0,    9,  108, -109,    0,
  -19,    0, -242, -242,  105,   28,   37,  109,    0,  122,
  -33,  -94, -246,    4,   56,    4,   72,  -87,    0,    0,
  191,    0,    0,  -43,  -43,    0,  -85,  -43,  -43,    0,
  125,   68,    0,  -72,  287,  -59,  158,    0, -246,  -31,
    0, -105,    0,  -75,  -43,  214,  287, -242,  165,  164,
  -19,  -19,   87,    0,    0, -246,  -34,   90, -124,    0,
    0,    0,    0,    0, -105,    0,    0,    0,  174, -105,
  -48,  -43,    4,    0,    0,    0,    0,    0,    0,  179,
  161,  -26,    0,  -75,    0,  177,    0,   38,    0,  -39,
    0,    0,    4,  -43, -134,    0,  -34,    0,    0,    0,
   86,  322,    0,  170,    0,    0,  106,    0, -124,    0,
  -28,    0,   57,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -111,  232,  153,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  238,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   96,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -38,    0,    0,    0,    0,   46,    0,
   39,    0,    0,    0,    1,    0,   20,    0,    0,    0,
    0,    0,    0,  115,    0,   97,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  202,
   58,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -12,
    0,    0,  135,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   63,    0,    0,    0,    0,    0,    0,
    0,    0,  134,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  470,   22,    2,    0,   16,    0,    0,    0,  -30,    0,
  -98,  178,  128,  142,  -76,    0,  -13,    0,    0,    0,
    0,    0,    0,  451,    0,    0,  -64,  -55,    6,  219,
  148,    0,    0,    0,    0,   92,  -22,    0,    0,
};
final static int YYTABLESIZE=643;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         71,
   58,   52,   58,   58,   58,   58,   58,   98,   58,  128,
   52,   58,   45,   87,  156,   38,  110,   57,  146,   52,
   66,    1,   88,  129,   14,    1,   30,   89,  111,  112,
   74,   38,   91,   92,   11,   12,    1,   29,   48,   32,
   99,   58,   58,   58,   58,   58,   84,   58,   85,   83,
  108,   84,   55,   85,   31,   36,   38,   46,  167,   58,
   52,   52,   52,   52,   52,  129,   52,   63,   93,   99,
   84,   40,   85,   34,    1,   35,   47,   99,   52,   48,
   46,   48,   48,   48,   58,   39,   52,   52,   52,  161,
   52,  133,   52,  114,  115,   13,  149,   48,   46,   64,
   46,   46,   46,   55,   55,   55,   69,   55,   73,   55,
  120,   99,  106,   75,   39,  105,   46,   47,  153,   47,
   47,   47,  120,   58,  162,  159,  168,  163,   78,  105,
   79,   38,    4,   40,  120,   47,   13,   45,    6,  170,
   45,    8,   80,    1,   81,    9,  149,   10,   86,   95,
  159,    4,    7,   16,   13,   39,   16,    6,    7,   94,
    8,   71,    1,  120,    9,  102,   10,   58,   44,   44,
  107,   11,   12,   39,   40,   44,    4,  120,   44,    5,
  104,  113,    6,    7,  120,    8,  116,    1,  120,    9,
  117,   10,   40,    7,  118,    4,   11,   12,  126,   37,
  125,    6,    7,  132,    8,  143,    1,  105,    9,   51,
   10,  144,  152,   52,  147,   11,   12,  151,  154,  155,
  157,  160,   97,   49,    1,   82,   16,   70,  171,   16,
  172,    4,   49,  174,    1,   52,    1,    3,   16,   16,
   58,    1,   43,   11,   12,   11,   12,   96,   27,  142,
   11,   12,  150,   68,  141,    0,    0,   58,    0,   53,
  173,   58,    0,   58,   58,    0,   58,    0,   58,    0,
   58,    0,   58,  137,  139,  138,   52,   58,   58,    0,
   52,    0,   52,   52,    0,   52,    0,   52,    0,   52,
    0,   52,    0,    0,    4,   48,   52,   52,  158,   48,
    6,   48,   48,    8,   48,    1,   48,    9,   48,   10,
   48,    0,    0,    4,   46,   48,   48,  175,   46,    6,
   46,   46,    8,   46,    1,   46,    9,   46,   10,   46,
    0,    0,    0,   47,   46,   46,    0,   47,    0,   47,
   47,    0,   47,    0,   47,    0,   47,    0,   47,    0,
    0,    0,   13,   47,   47,    0,   13,    0,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,    0,    0,
    0,   39,   13,   13,    0,   39,    0,   39,   39,    0,
   39,    0,   39,    0,   39,    0,   39,    0,    0,    0,
   40,   39,   39,    0,   40,    0,   40,   40,    0,   40,
    0,   40,    0,   40,    0,   40,    0,    0,    0,    7,
   40,   40,    0,    7,    0,    7,    7,    0,    7,    0,
    7,    0,    7,    0,    7,    4,    0,    0,    0,    7,
    7,    6,    7,    0,    8,   49,    1,   50,    9,    0,
   10,    4,    0,    0,    0,   11,   12,    6,    7,    0,
    8,    0,    1,    0,    9,    0,   10,   49,    1,    0,
    0,   11,   12,    0,    0,    0,    0,   11,   12,    3,
    0,   43,   13,    0,   13,    0,   33,    0,    0,    0,
    0,   59,    0,   13,   67,    0,    0,  134,  135,  136,
   41,    0,    0,    0,   67,    0,   67,    4,    0,   13,
   54,   48,    0,    6,    7,    0,    8,    0,    1,    0,
    9,    0,   10,    0,    0,    0,    0,   11,   12,    0,
    0,    0,    0,  103,   13,    0,    0,    0,    0,    0,
    0,   67,   90,   90,   67,   67,    0,    0,   67,   67,
   90,    0,   41,    4,    0,    0,  119,    0,    0,    6,
   54,    0,    8,    0,    1,   67,    9,    0,   10,    0,
    0,    0,    0,    0,   41,    0,    0,    0,  127,   90,
    0,   13,    0,    0,    0,    0,   41,   90,    4,    0,
    0,  169,   67,    0,    6,  145,    0,    8,   41,    1,
    0,    9,    0,   10,   13,    0,    0,    0,    0,   13,
    0,    0,    0,    0,   67,    0,    0,    0,    0,    0,
    0,   90,    0,    0,    0,    0,    0,   41,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,   41,    0,
    0,    0,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   41,   42,   43,   44,   45,   41,   47,   41,
   45,  123,  123,  123,   41,   14,   81,   31,  117,    0,
   34,  268,   42,  100,    3,  268,    5,   47,   84,   85,
   44,   30,   63,   64,  277,  278,  268,   40,    0,  271,
   71,   41,   42,   43,   44,   45,   43,   47,   45,   41,
   81,   43,   31,   45,   40,  273,   55,    0,  157,   59,
   41,   42,   43,   44,   45,  142,   47,   60,   41,  100,
   43,  265,   45,   40,  268,   40,    0,  108,   59,   41,
   40,   43,   44,   45,  123,   59,   41,   42,   43,  154,
   45,  105,   47,   88,   89,    0,  119,   59,   41,  279,
   43,   44,   45,   41,   42,   43,   59,   45,   44,   47,
   95,  142,   41,  267,    0,   44,   59,   41,  132,   43,
   44,   45,  107,  123,  259,  148,   41,  262,   41,   44,
  267,  130,  257,    0,  119,   59,   41,   41,  263,  162,
   44,  266,   93,  268,   40,  270,  169,  272,   41,   41,
  173,  257,    0,  265,   59,   41,  268,  263,  264,  123,
  266,   40,  268,  148,  270,  260,  272,  279,  279,  279,
  258,  277,  278,   59,   41,   41,  257,  162,   44,  260,
  125,  267,  263,  264,  169,  266,   62,  268,  173,  270,
  123,  272,   59,   41,  267,  257,  277,  278,   41,  261,
  260,  263,  264,  279,  266,   41,  268,   44,  270,   41,
  272,  125,  261,   45,  125,  277,  278,   44,   40,   59,
   44,  261,  256,  267,  268,   41,  265,  268,   59,  268,
  125,    0,  267,  262,  268,   45,  268,    0,  277,  278,
  279,  268,   41,  277,  278,  277,  278,   70,  261,  108,
  277,  278,  125,   35,  107,   -1,   -1,  257,   -1,   91,
  169,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   60,   61,   62,  257,  277,  278,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,   -1,  257,  257,  277,  278,  261,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   -1,   -1,  257,  257,  277,  278,  261,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,
  257,  277,  278,   -1,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,  257,
  277,  278,   -1,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  257,   -1,   -1,   -1,  277,
  278,  263,  264,   -1,  266,  267,  268,  269,  270,   -1,
  272,  257,   -1,   -1,   -1,  277,  278,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,  267,  268,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,  277,  278,    0,
   -1,   21,    3,   -1,    5,   -1,    7,   -1,   -1,   -1,
   -1,   31,   -1,   14,   34,   -1,   -1,  274,  275,  276,
   21,   -1,   -1,   -1,   44,   -1,   46,  257,   -1,   30,
   31,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,   73,   55,   -1,   -1,   -1,   -1,   -1,
   -1,   81,   63,   64,   84,   85,   -1,   -1,   88,   89,
   71,   -1,   73,  257,   -1,   -1,  260,   -1,   -1,  263,
   81,   -1,  266,   -1,  268,  105,  270,   -1,  272,   -1,
   -1,   -1,   -1,   -1,   95,   -1,   -1,   -1,   99,  100,
   -1,  102,   -1,   -1,   -1,   -1,  107,  108,  257,   -1,
   -1,  260,  132,   -1,  263,  116,   -1,  266,  119,  268,
   -1,  270,   -1,  272,  125,   -1,   -1,   -1,   -1,  130,
   -1,   -1,   -1,   -1,  154,   -1,   -1,   -1,   -1,   -1,
   -1,  142,   -1,   -1,   -1,   -1,   -1,  148,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  162,   -1,   -1,   -1,   -1,   -1,   -1,  169,   -1,
   -1,   -1,  173,
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

//#line 187 "Gramatica.y"
																	 
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
//#line 501 "Parser.java"
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
case 82:
//#line 169 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 730 "Parser.java"
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
