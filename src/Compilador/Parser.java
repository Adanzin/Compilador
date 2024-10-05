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
    0,    0,    0,    0,    2,    2,    3,    3,    3,    4,
    4,    4,    4,    4,    6,    6,    9,    9,    9,    8,
    8,    7,    7,    7,   12,   12,   14,   14,   13,   15,
    5,    5,    5,    5,    5,    5,   21,   21,   21,   21,
   17,   17,   24,   24,   25,   26,   26,   16,   16,   16,
   27,   27,   27,   28,   28,   28,   28,   10,   10,   23,
    1,   11,   11,   18,   18,   29,   32,   32,   32,   32,
   32,   32,   31,   31,   34,   33,   30,   30,   38,   37,
   35,   35,   36,   22,   22,   19,   20,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    2,    1,    1,    2,    1,    1,
    2,    1,    2,    1,    3,    3,    1,    1,    1,    9,
    6,    9,    8,    8,    3,    1,    2,    1,    1,    4,
    1,    1,    1,    1,    1,    1,    4,    3,    4,    1,
    3,    6,    4,    5,    1,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    4,    3,    1,    1,
    1,    1,    2,   10,    8,    7,    1,    1,    1,    1,
    1,    1,    1,    1,    4,    2,    1,    1,    3,    1,
    2,    1,    1,    1,    2,    5,    2,
};
final static short yydefred[] = {                         0,
   61,    0,    0,   40,    0,    0,    0,    0,    0,    0,
    0,   17,   18,    0,    0,    6,    7,    0,   10,    0,
    0,    0,   36,   31,   32,   33,   34,   35,    0,    0,
    0,    0,    0,    0,    0,    0,   87,    2,    5,    8,
   11,   13,   59,    0,    0,    0,    0,    0,    0,    0,
    1,   62,   84,   38,    0,    0,   60,   55,    0,    0,
    0,   56,    0,   53,    0,    0,    0,    0,    0,    0,
   16,   15,    0,    0,    0,    0,    0,    0,   63,   85,
   37,    0,    0,   39,    0,    0,    0,    0,   19,    0,
    0,   30,    0,    0,   28,    0,    0,   26,   58,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   52,    0,    0,    0,   83,   86,   80,   77,   78,    0,
    0,   27,    0,    0,    0,    0,   68,   70,   72,   69,
   67,   71,    0,    0,   57,    0,   43,   21,    0,    0,
   82,    0,    0,    0,   25,    0,    0,    0,   44,    0,
   79,   81,    0,    0,    0,    0,    0,    0,   65,    0,
   73,   74,    0,   24,    0,   23,   66,    0,   76,    0,
   20,   22,    0,   64,   75,
};
final static short yydgoto[] = {                          2,
   57,  153,   16,   17,   18,   19,   20,   21,   22,   46,
   58,   97,  154,   98,   23,   76,   24,   25,   26,   27,
   28,   60,   29,   62,  108,   77,   63,   64,   50,  116,
  160,  133,  161,  162,  140,  117,  118,  119,
};
final static short yysindex[] = {                      -249,
    0,    0,  -80,    0,   19,  226,   46, -244,   52,   56,
 -206,    0,    0,    0,  178,    0,    0,   47,    0,   60,
   62, -215,    0,    0,    0,    0,    0,    0, -111,   75,
  196,  -13,   73, -134,  -34,   75,    0,    0,    0,    0,
    0,    0,    0,  -40,   88,  -22,  -34, -110,  -34,  115,
    0,    0,    0,    0, -109,   68,    0,    0,   48,  118,
  -23,    0,    7,    0, -154, -154,   77,  124,  126, -221,
    0,    0, -249,   -9,   42,   -9,   27,  -89,    0,    0,
    0,  -34,  -34,    0,  -97,  154,  -34,  -34,    0,  109,
   49,    0,  245,  -33,    0, -249,  -31,    0,    0, -105,
  -34,  153,  245,    7,    7,   50, -221,  141,  137,    0,
    0, -249,  -19,  305,    0,    0,    0,    0,    0,  -75,
  -25,    0,  -71,  147,  -34,   -9,    0,    0,    0,    0,
    0,    0,  155,  134,    0,  -18,    0,    0,  150,  263,
    0,  226,  -64,  226,    0,   -9,  -34, -155,    0,  -19,
    0,    0,  226,  -61,  226,  -60,   84,  280,    0,  144,
    0,    0,   79,    0,  -56,    0,    0,  305,    0,  -55,
    0,    0,  297,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -38,  206,    0,    0,  112,    0,  130,
  148,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  209,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,   20,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   76,    0,   97,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   58,    0,    0,    0,  170,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   98,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   94,    0,    0,    0,    0,
    0,    0,  -49,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   66,  140,   -2,    0,  -63,    0,    0,    0,   43,    0,
  -98,  -69,  -82,  -66,    0,  -14,    0,    0,    0,    0,
    0,    0,  398,    0,    0,  -72,   44,   67,  180,  114,
    0,    0,    0,    0,   54,  -24,    0,    0,
};
final static int YYTABLESIZE=577;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
   54,   60,   60,   60,   60,   60,   60,  120,   60,  123,
   55,   48,   39,  109,  139,  143,   86,   59,    1,   50,
   67,   73,  149,    1,  121,   55,   33,   54,   39,  115,
  124,   55,   74,   82,   95,   83,   72,  136,   48,  115,
   43,   54,   54,   54,   54,   54,    1,   54,   87,   44,
  115,  163,    1,   88,  124,   12,   13,   49,   30,   54,
   50,  156,   50,   50,   50,    3,   37,  102,   14,  124,
  101,   14,  165,   34,  157,   41,  115,   56,   50,   48,
   14,   48,   48,   48,   60,   32,  126,   45,   81,  141,
   82,   35,   83,   42,  115,   36,   14,   48,   49,   85,
   49,   49,   49,  158,  115,   40,  159,   90,   91,  115,
  146,    9,   96,    1,   49,  152,   49,   92,   41,   82,
   42,   83,   12,   13,  167,  104,  105,  101,  107,   12,
   89,   89,   65,  169,   41,   89,   96,   47,   46,   96,
   47,   46,   15,  141,   66,   31,   71,   14,  152,   96,
   39,   14,   42,  110,  111,   78,   75,   79,   84,   89,
   80,  122,   89,   96,   93,   94,  100,   47,  103,  106,
  112,  113,   89,  125,  135,    4,    5,  138,   96,    6,
  101,  137,    7,    8,  142,    9,   89,    1,  144,   10,
  145,   11,  148,  150,  147,  155,   12,   13,   55,  164,
  166,   89,  170,  171,  172,    4,  174,   14,    3,   14,
   45,   29,  130,  132,  131,   68,  134,   19,   14,    0,
   14,  173,   95,    0,   95,    0,   19,   69,    0,   19,
   95,    0,   52,    1,    1,    0,    1,   95,   19,   19,
   60,    0,    1,   12,   13,   12,   13,   52,    0,    1,
    0,   12,   13,   52,    1,   53,   54,   54,   12,   13,
    0,   54,    0,   54,   54,    0,   54,    0,   54,    0,
   54,    0,   54,    0,    0,   50,   50,   54,   54,    0,
   50,    0,   50,   50,    0,   50,    0,   50,    0,   50,
    0,   50,    0,    0,   48,   48,   50,   50,    0,   48,
    0,   48,   48,    0,   48,    0,   48,    0,   48,    0,
   48,    0,    0,   49,   49,   48,   48,    0,   49,    0,
   49,   49,    0,   49,    0,   49,    0,   49,    0,   49,
    0,   41,   41,    0,   49,   49,   41,    0,   41,   41,
    0,   41,    0,   41,    0,   41,    0,   41,    0,   42,
   42,    0,   41,   41,   42,    0,   42,   42,    0,   42,
    0,   42,    0,   42,    0,   42,    0,    9,    9,    0,
   42,   42,    9,    0,    9,    9,    0,    9,    0,    9,
    0,    9,    0,    9,    0,   12,   12,    0,    9,    9,
   12,    0,   12,   12,    0,   12,    0,   12,    0,   12,
    0,   12,    0,   14,   14,    0,   12,   12,   14,    0,
   14,   14,    0,   14,    0,   14,    0,   14,    0,   14,
   52,    1,    0,    0,   14,   14,  127,  128,  129,   61,
   12,   13,   61,    4,    5,    0,    0,    0,   38,    0,
    7,    8,    0,    9,   61,    1,   61,   10,    0,   11,
    0,    4,    5,    0,   12,   13,   51,    0,    7,    8,
    0,    9,    0,    1,    0,   10,    0,   11,    0,    0,
   99,    0,   12,   13,    0,    0,    0,    0,    0,   61,
   61,    4,    5,   61,   61,   61,    0,    0,    7,    8,
    0,    9,    0,    1,    0,   10,    0,   11,   61,    0,
    4,    5,   12,   13,  114,    0,    0,    7,    0,    0,
    9,    0,    1,    0,   10,    0,   11,    0,    4,    5,
    0,    0,   61,  151,    0,    7,    0,    0,    9,    0,
    1,    0,   10,    0,   11,    4,    5,    0,    0,  168,
    0,    0,    7,    0,   61,    9,    0,    1,    0,   10,
    0,   11,    4,    5,    0,    0,    0,  175,    0,    7,
    4,    5,    9,    0,    1,    0,   10,    7,   11,    0,
    9,    0,    1,    0,   10,    0,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   41,   42,   43,   44,   45,   41,   47,   41,
   45,  123,   15,   86,  113,   41,   40,   32,  268,    0,
   35,   44,   41,  268,   94,   45,  271,   41,   31,   93,
   97,   45,   47,   43,  256,   45,   59,  107,    0,  103,
  256,   41,   42,   43,   44,   45,  268,   47,   42,  265,
  114,  150,  268,   47,  121,  277,  278,    0,   40,   59,
   41,  144,   43,   44,   45,    0,  273,   41,    3,  136,
   44,    6,  155,    8,  147,    0,  140,   91,   59,   41,
   15,   43,   44,   45,  123,   40,  101,   22,   41,  114,
   43,   40,   45,    0,  158,   40,   31,   59,   41,  123,
   43,   44,   45,  259,  168,   59,  262,   65,   66,  173,
  125,    0,   70,  268,   40,  140,   59,   41,   59,   43,
   59,   45,  277,  278,   41,   82,   83,   44,   86,    0,
   65,   66,   60,  158,   59,   70,   94,   41,   41,   97,
   44,   44,    3,  168,  279,    6,   59,    0,  173,  107,
  153,   86,   59,   87,   88,   41,  267,  267,   41,   94,
   93,   96,   97,  121,   41,   40,  125,  279,  258,  267,
   62,  123,  107,  279,  125,  256,  257,  112,  136,  260,
   44,   41,  263,  264,  260,  266,  121,  268,  260,  270,
   44,  272,   59,   44,   40,  260,  277,  278,   45,  261,
  261,  136,   59,  125,  261,    0,  262,  142,    0,  144,
   41,  261,   60,   61,   62,   36,  103,  256,  153,   -1,
  155,  168,  256,   -1,  256,   -1,  265,  268,   -1,  268,
  256,   -1,  267,  268,  268,   -1,  268,  256,  277,  278,
  279,   -1,  268,  277,  278,  277,  278,  267,   -1,  268,
   -1,  277,  278,  267,  268,  269,  256,  257,  277,  278,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,  256,  257,  277,  278,   -1,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,   -1,  256,  257,  277,  278,   -1,  261,
   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,   -1,  256,  257,  277,  278,   -1,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   -1,  256,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,  256,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,   -1,  256,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,  256,  257,   -1,  277,  278,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,  256,  257,   -1,  277,  278,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  267,  268,   -1,   -1,  277,  278,  274,  275,  276,   32,
  277,  278,   35,  256,  257,   -1,   -1,   -1,  261,   -1,
  263,  264,   -1,  266,   47,  268,   49,  270,   -1,  272,
   -1,  256,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   73,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   82,
   83,  256,  257,   86,   87,   88,   -1,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,  101,   -1,
  256,  257,  277,  278,  260,   -1,   -1,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,  256,  257,
   -1,   -1,  125,  261,   -1,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,   -1,  147,  266,   -1,  268,   -1,  270,
   -1,  272,  256,  257,   -1,   -1,   -1,  261,   -1,  263,
  256,  257,  266,   -1,  268,   -1,  270,  263,  272,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,
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
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable ';'",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones ';'",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo ';'",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables ';'",
"declaracion_variable : tipo ID_simple ';'",
"tipo : INTEGER",
"tipo : DOUBLE",
"tipo : ID_simple",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID_simple",
"declaracion_funciones : tipo FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN ID '(' ')' BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID_simple",
"parametro : error",
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
"outf_rule : error",
"asignacion : variable_simple ASIGNACION expresion_arit",
"asignacion : variable_simple '{' CTE '}' ASIGNACION expresion_arit",
"invocacion : variable_simple '(' parametro_real ')'",
"invocacion : variable_simple '(' tipo parametros_formal ')'",
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
"variables : error",
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

//#line 186 "Gramatica.y"
																	 
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
//#line 489 "Parser.java"
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
case 9:
//#line 21 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia ejecutable ");}
break;
case 12:
//#line 28 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la declaracion de funciones ");}
break;
case 14:
//#line 30 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la declaracion de subtipo ");}
break;
case 19:
//#line 39 "Gramatica.y"
{System.out.println(" Se identifico el ID de una clase como declaracion ");}
break;
case 22:
//#line 46 "Gramatica.y"
{if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");RETORNO=false;}}
break;
case 23:
//#line 47 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
break;
case 24:
//#line 48 "Gramatica.y"
{if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");RETORNO=false;}System.out.println(" Erro: Faltan los parametros en la funcion ");}
break;
case 28:
//#line 56 "Gramatica.y"
{System.out.println(" ERROR AL DECLARAR LOS PARAMETROS FORMALES. ");}
break;
case 36:
//#line 70 "Gramatica.y"
{RETORNO = true;}
break;
case 37:
//#line 73 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 38:
//#line 74 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + " : Falta el parametro del OUTF  ");}
break;
case 39:
//#line 75 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 40:
//#line 76 "Gramatica.y"
{System.out.println("Error en la linea :" + AnalizadorLexico.saltoDeLinea + ":  Parámetro incorrecto en sentencia OUTF. ");}
break;
case 41:
//#line 79 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 42:
//#line 80 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 58:
//#line 110 "Gramatica.y"
{System.out.println(" >> Identifico variables var_con_coma ");}
break;
case 59:
//#line 111 "Gramatica.y"
{System.out.println(" >> Falta la , en la declaracion de variables.  ");}
break;
case 60:
//#line 114 "Gramatica.y"
{System.out.println(">> Identifico una varaible_simple ");}
break;
case 61:
//#line 117 "Gramatica.y"
{System.out.println(" >> Identifico un ID ");}
break;
case 62:
//#line 121 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 63:
//#line 122 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 84:
//#line 168 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 742 "Parser.java"
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
