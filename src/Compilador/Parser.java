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
    5,    5,    5,    5,    5,    5,   18,   18,   23,   23,
   25,   26,   26,   17,   17,   17,   27,   27,   27,   28,
   28,   28,   28,   10,   10,   24,    1,   11,   11,   19,
   19,   29,   32,   32,   32,   32,   32,   32,   31,   31,
   34,   33,   30,   30,   38,   37,   35,   35,   36,   22,
   22,   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    4,    4,    1,    1,    3,    6,    4,    5,
    1,    3,    1,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    4,    3,    1,    1,    1,    1,    2,   10,
    8,    7,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    2,    1,    1,    3,    1,    2,    1,    1,    1,
    2,    5,    2,
};
final static short yydefred[] = {                         0,
   57,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   35,   29,   30,   31,   32,   36,    0,    0,    0,
    0,    0,    0,    0,    0,   83,    0,    2,    0,    6,
    0,   56,    0,   55,    0,    0,    0,    0,    1,   58,
   80,    0,    0,    0,   51,    0,    0,   52,    0,    0,
   49,    0,    0,    0,    0,    0,    0,    0,    0,    5,
    0,    0,    0,    0,    0,    0,    0,    0,   59,   81,
   33,    0,    0,   34,    0,    0,    0,   16,    0,    0,
   28,    0,    0,    0,   25,   39,    0,    0,    0,   22,
    0,    0,   54,    0,    0,    0,    0,    0,    0,   47,
   48,    0,    0,    0,   79,   82,   76,   73,   74,   26,
   40,    0,    0,    0,   23,   21,    0,    0,    0,   64,
   66,   68,   65,   63,   67,    0,    0,   53,   18,    0,
    0,   78,   24,    0,   20,    0,    0,    0,    0,   75,
   77,   19,    0,    0,   61,    0,   69,   70,    0,   62,
    0,   72,    0,   17,    0,   60,   71,
};
final static short yydgoto[] = {                          2,
   54,  127,   15,   16,   17,   18,   19,   20,   21,   43,
   55,   73,  128,   94,   95,   22,   67,   23,   24,   25,
   26,   57,   27,   28,   68,   69,   60,   61,   48,  116,
  156,  136,  157,  158,  141,  117,  118,  119,
};
final static short yysindex[] = {                      -244,
    0,    0,  174,  -10,  252,   34, -202,   48,   52, -214,
    0,    0,   53,  192,   45,    0,    0,    0,    0,    0,
 -197,    0,    0,    0,    0,    0,    0, -111,   74,  218,
  -13,   56, -156,  -43,   74,    0,  -18,    0,   69,    0,
  -40,    0,   85,    0,  -43, -135,  -43,   94,    0,    0,
    0, -130,   46,   53,    0,   -7,   99,    0,   18,  -21,
    0, -221, -221,   10,  103, -221,    9,  109,  108,    0,
  115,  -33, -107, -244,    9,   33,   29,  -96,    0,    0,
    0,  -43,  -43,    0, -104,  -43,  -43,    0,  102,   42,
    0, -235, -244,  -31,    0,    0,  -43,  -94,  126,    0,
  -26,  252,    0, -110,  148, -235,  -21,  -21,   49,    0,
    0, -244,  -34,  -51,    0,    0,    0,    0,    0,    0,
    0,  127,    9,  252,    0,    0,  252,  -86,  -43,    0,
    0,    0,    0,    0,    0,  136,  118,    0,    0,  134,
   38,    0,    0,  -82,    0,    9,  -43, -172,  -34,    0,
    0,    0,   65,  271,    0,  124,    0,    0,   61,    0,
  -51,    0,  -75,    0,   57,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -38,  188,  149,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  193,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   95,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,   20,   39,
    0,    0,    0,    0,    0,    0,   71,    0,  151,    0,
    0,    0,    0,    0,  113,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,   77,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   86,    0,    0,    0,  -67,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  131,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  411,  105,   -1,    0,   19,    0,    0,    0,  119,    0,
  -99,  128,   78,  123,  -78,    0,   41,    0,    0,    0,
    0,    0,  114,   60,    0,  -28,  -42,  -69,  163,   97,
    0,    0,    0,    0,   43,  -65,    0,    0,
};
final static int YYTABLESIZE=543;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         72,
   56,   52,   56,   56,   56,   56,   56,  100,   56,  121,
   52,   46,   39,  140,  126,  122,  110,  111,   77,   50,
   86,    4,  122,    1,  114,   87,   52,    6,   39,   29,
    8,   52,    1,   81,    9,   82,   10,   83,   46,  107,
  108,   56,   56,   56,   56,   56,    1,   56,  142,  159,
   91,   82,   82,   83,   83,   11,   12,   44,   36,   56,
   50,   50,   50,   50,   50,    1,   50,   41,   32,  105,
    1,   56,   97,   31,   64,  151,   45,   53,   50,   46,
   44,   46,   46,   46,   56,   75,  154,   34,  162,  155,
   59,   35,   37,   59,   13,  142,   59,   46,   44,  151,
   44,   44,   44,   40,   59,  160,   59,   14,   97,   30,
  115,   43,   37,   47,   43,   62,   44,   45,  153,   45,
   45,   45,   63,   56,  115,   39,   42,   70,   74,   42,
   38,   76,  115,  103,   78,   45,   79,  123,   80,   84,
   85,   59,   59,   92,   58,   59,   59,   58,    7,   96,
   58,   97,  102,   13,   72,   66,   59,  104,   58,  115,
   58,  106,  109,  112,  113,  124,  125,   45,  129,  146,
  143,   37,  115,  138,  145,  147,  148,  149,  152,  115,
   89,   90,  163,  115,   93,  164,  166,    4,   59,   38,
   93,   41,    3,   27,  101,   58,   58,   65,   98,   58,
   58,  144,  137,  165,    0,    4,   59,  133,  135,  134,
   58,    6,   93,    0,    8,    0,    1,    0,    9,   93,
   10,    0,   99,   50,    1,    0,   16,   71,    0,   16,
    0,    0,   50,    0,    1,    0,    1,    0,   16,   16,
   56,    1,   58,   11,   12,   11,   12,    0,   50,    1,
   11,   12,    0,   50,    1,   51,    0,   56,   11,   12,
   58,   56,    0,   56,   56,    0,   56,    0,   56,    0,
   56,    0,   56,    0,    0,    0,   50,   56,   56,   56,
   50,    0,   50,   50,    0,   50,    0,   50,    0,   50,
    0,   50,    0,    0,    4,   46,   50,   50,  150,   46,
    6,   46,   46,    8,   46,    1,   46,    9,   46,   10,
   46,    0,    0,    4,   44,   46,   46,  167,   44,    6,
   44,   44,    8,   44,    1,   44,    9,   44,   10,   44,
    0,    0,    0,   45,   44,   44,    0,   45,    0,   45,
   45,    0,   45,    0,   45,    0,   45,    0,   45,    0,
    0,   13,    0,   45,   45,   13,    0,   13,   13,    0,
   13,    0,   13,    0,   13,    0,   13,    0,    0,   37,
    0,   13,   13,   37,    0,   37,   37,    0,   37,    0,
   37,    0,   37,    0,   37,    0,    0,   38,    0,   37,
   37,   38,    0,   38,   38,    0,   38,    0,   38,    0,
   38,    0,   38,    0,    0,    7,    0,   38,   38,    7,
    3,    7,    7,   13,    7,   13,    7,   33,    7,    0,
    7,  130,  131,  132,   13,    7,    7,    0,    0,    0,
    4,   42,    0,    5,    0,    0,    6,    7,    0,    8,
   13,    1,    0,    9,    0,   10,    0,   13,    4,    0,
   11,   12,   38,    0,    6,    7,    0,    8,    0,    1,
    0,    9,    0,   10,    0,    0,    0,    0,   11,   12,
    0,    0,   88,   88,    4,    0,   88,    0,   49,    0,
    6,    7,   88,    8,   42,    1,    0,    9,    0,   10,
    0,    0,    0,    0,   11,   12,    0,    0,    0,    0,
    0,    0,    0,  120,   88,    0,    0,    0,    4,    0,
    0,   88,   13,    0,    6,    7,    0,    8,    0,    1,
    0,    9,  139,   10,    0,    0,    0,    4,   11,   12,
  161,    0,    0,    6,   13,    0,    8,   13,    1,    0,
    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   41,   42,   43,   44,   45,   41,   47,   41,
   45,  123,   14,  113,   41,   94,   86,   87,   47,    0,
   42,  257,  101,  268,  260,   47,   45,  263,   30,   40,
  266,   45,  268,   41,  270,   43,  272,   45,    0,   82,
   83,   41,   42,   43,   44,   45,  268,   47,  114,  149,
   41,   43,   43,   45,   45,  277,  278,    0,  273,   59,
   41,   42,   43,   44,   45,  268,   47,  265,  271,   41,
  268,   31,   44,   40,   34,  141,    0,   91,   59,   41,
   21,   43,   44,   45,  123,   45,  259,   40,  154,  262,
   31,   40,   40,   34,    0,  161,   37,   59,   41,  165,
   43,   44,   45,   59,   45,   41,   47,    3,   44,    5,
   92,   41,    0,   40,   44,   60,   59,   41,  147,   43,
   44,   45,  279,  123,  106,  127,   41,   59,   44,   44,
    0,  267,  114,   74,   41,   59,  267,   97,   93,   41,
  123,   82,   83,   41,   31,   86,   87,   34,    0,   41,
   37,   44,  260,   59,   40,   37,   97,  125,   45,  141,
   47,  258,  267,   62,  123,  260,   41,  279,  279,  129,
   44,   59,  154,  125,  261,   40,   59,   44,  261,  161,
   62,   63,   59,  165,   66,  125,  262,    0,  129,   59,
   72,   41,    0,  261,   72,   82,   83,   35,   71,   86,
   87,  124,  106,  161,   -1,  257,  147,   60,   61,   62,
   97,  263,   94,   -1,  266,   -1,  268,   -1,  270,  101,
  272,   -1,  256,  267,  268,   -1,  265,  268,   -1,  268,
   -1,   -1,  267,   -1,  268,   -1,  268,   -1,  277,  278,
  279,  268,  129,  277,  278,  277,  278,   -1,  267,  268,
  277,  278,   -1,  267,  268,  269,   -1,  257,  277,  278,
  147,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,   -1,  257,  277,  278,  279,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,   -1,   -1,  257,  257,  277,  278,  261,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   -1,   -1,  257,  257,  277,  278,  261,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,  257,
   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,   -1,   -1,  257,   -1,  277,
  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,  257,   -1,  277,  278,  261,
    0,  263,  264,    3,  266,    5,  268,    7,  270,   -1,
  272,  274,  275,  276,   14,  277,  278,   -1,   -1,   -1,
  257,   21,   -1,  260,   -1,   -1,  263,  264,   -1,  266,
   30,  268,   -1,  270,   -1,  272,   -1,   37,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,   62,   63,  257,   -1,   66,   -1,  261,   -1,
  263,  264,   72,  266,   74,  268,   -1,  270,   -1,  272,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   93,   94,   -1,   -1,   -1,  257,   -1,
   -1,  101,  102,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  112,  272,   -1,   -1,   -1,  257,  277,  278,
  260,   -1,   -1,  263,  124,   -1,  266,  127,  268,   -1,
  270,   -1,  272,
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
"sentencia_ejecutable : OUTF '(' expresion_arit ')'",
"sentencia_ejecutable : OUTF '(' cadena ')'",
"sentencia_ejecutable : retorno",
"sentencia_ejecutable : invocacion",
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

//#line 184 "Gramatica.y"
																	 
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
//#line 476 "Parser.java"
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
case 33:
//#line 70 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 34:
//#line 71 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 35:
//#line 72 "Gramatica.y"
{RETORNO = true;}
break;
case 36:
//#line 73 "Gramatica.y"
{System.out.println(" Una invocacion no es una sentencia ejecutable ");}
break;
case 37:
//#line 77 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 38:
//#line 78 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 58:
//#line 119 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 59:
//#line 120 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 80:
//#line 166 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 701 "Parser.java"
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
