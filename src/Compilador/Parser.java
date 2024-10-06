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
    5,    5,    5,    5,    5,   18,   18,   24,   24,   25,
   26,   26,   17,   17,   17,   27,   27,   27,   28,   28,
   28,   28,   10,   10,   23,    1,   11,   11,   19,   19,
   29,   32,   32,   32,   32,   32,   32,   31,   31,   34,
   33,   30,   30,   38,   37,   35,   35,   36,   22,   22,
   20,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    7,    6,
    3,    2,    3,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    4,    4,    1,    3,    6,    4,    5,    1,
    3,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    3,    1,    1,    1,    1,    2,   10,    8,
    7,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    2,    1,    1,    3,    1,    2,    1,    1,    1,    2,
    5,    2,
};
final static short yydefred[] = {                         0,
   56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   35,   29,   30,   31,   32,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    2,    0,    6,    0,   55,
    0,   54,    0,    0,    0,    0,    1,   57,   79,    0,
    0,   50,    0,    0,    0,   51,    0,   48,    0,    0,
    0,    0,    5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,   80,   33,    0,    0,   34,    0,    0,
    0,    0,   16,    0,    0,   28,    0,    0,    0,   22,
    0,    0,   25,    0,   53,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   46,   47,    0,    0,    0,
   78,   81,   75,   72,   73,    0,   23,   26,   21,    0,
    0,    0,    0,    0,   63,   65,   67,   64,   62,   66,
    0,    0,   52,    0,   38,   18,    0,    0,   77,    0,
   24,   20,    0,    0,    0,   39,    0,   74,   76,   19,
    0,    0,   60,    0,   68,   69,    0,   61,    0,   71,
    0,   17,    0,   59,   70,
};
final static short yydgoto[] = {                          2,
   40,  121,   15,   16,   17,   18,   19,   20,   21,   41,
   52,   66,  122,   92,   93,   22,   70,   23,   24,   25,
   26,   54,   27,   56,  104,   71,   57,   58,   46,  112,
  154,  131,  155,  156,  138,  113,  114,  115,
};
final static short yysindex[] = {                      -243,
    0,    0,  -61,   13,  197,   26, -249,   52,   55, -232,
    0,    0,    0,  154,   51,    0,    0,    0,    0,    0,
 -242,    0,    0,    0,    0,    0, -111,   73,  172,  -14,
   59, -220,  -34,   73,    0,    0,   70,    0,  -40,    0,
   95,    0,  -34, -125,  -34,  104,    0,    0,    0, -118,
   62,    0,   -5,  115,  -23,    0,  -10,    0, -162, -162,
    6,  122,    0,  125,  -33,  -86, -243,   48,   44,   48,
   11,  -81,    0,    0,    0,  -34,  -34,    0,  -88,  -18,
  -34,  -34,    0,  119,   61,    0, -132,  -75,  146,    0,
 -243,  -31,    0,  197,    0,  -87,  -34,  129, -132,  -10,
  -10,   69, -162,  157,  151,    0,    0, -243,  -29,  -90,
    0,    0,    0,    0,    0,  197,    0,    0,    0,  156,
  197,  -57,  -34,   48,    0,    0,    0,    0,    0,    0,
  166,  149,    0,  -26,    0,    0,  168, -120,    0,  -51,
    0,    0,   48,  -34, -205,    0,  -29,    0,    0,    0,
   27, -106,    0,  155,    0,    0,   88,    0,  -90,    0,
  -47,    0,   19,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -38,  218,  130,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  219,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   76,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,   20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,    0,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   39,
   58,    0,    0,    0,  179,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -39,    0,    0,   46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   67,  102,   -1,    0,   34,    0,    0,    0,  -30,    0,
  -91,  160,  105,  123,  -59,    0,   -9,    0,    0,    0,
    0,    0,  376,    0,    0,  -66,   32,   42,  191,  132,
    0,    0,    0,    0,   77,  -41,    0,    0,
};
final static int YYTABLESIZE=520;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         65,
   49,   55,   55,   55,   55,   55,   55,   90,   55,  119,
   50,   44,   37,  105,  146,   50,   80,  137,    1,   45,
   53,   31,   39,   61,    1,    1,   50,   37,   84,   85,
   50,   81,  120,   68,   91,   75,   82,   76,   43,   77,
   35,   49,   49,   49,   49,   49,   86,   49,   76,  103,
   77,   98,   28,  152,   97,  157,  153,   44,   60,   49,
   45,   91,   45,   45,   45,   30,    3,  158,  139,   13,
   97,   13,   91,   32,  120,   13,   51,  151,   45,   43,
   13,   43,   43,   43,   55,   42,   41,  124,   42,   41,
   76,   33,   77,   36,   34,   13,  149,   43,   44,   79,
   44,   44,   44,   91,   14,    1,   29,  100,  101,   38,
  160,   37,   45,  143,   11,   12,   44,  139,   59,   37,
  111,  149,  106,  107,    4,   83,   83,  110,   63,    7,
    6,   83,  111,    8,   13,    1,    4,    9,   67,   10,
  148,   69,    6,  111,   72,    8,   13,    1,   73,    9,
    4,   10,   36,  159,   74,   78,    6,  118,   83,    8,
   13,    1,   87,    9,   65,   10,    4,   43,   96,   83,
   37,  111,    6,   94,  136,    8,   99,    1,  102,    9,
  108,   10,   13,  109,  116,  111,  117,   13,  128,  130,
  129,  123,  111,  133,   97,    4,  111,  135,    5,  141,
   83,    6,    7,  142,    8,  144,    1,  145,    9,  150,
   10,  147,  162,  161,  164,   11,   12,    4,    3,   40,
  140,   27,   89,   88,   62,  134,   16,   64,    0,   16,
  132,    0,   48,    1,    1,  163,    1,   48,   16,   16,
   55,    1,    0,   11,   12,   11,   12,    0,   48,    1,
   11,   12,   48,    1,   49,    0,    0,   49,   11,   12,
    0,   49,    0,   49,   49,    0,   49,    0,   49,    0,
   49,    0,   49,    0,    0,    4,   45,   49,   49,  165,
   45,    6,   45,   45,    8,   45,    1,   45,    9,   45,
   10,   45,    0,    0,    0,   43,   45,   45,    0,   43,
    0,   43,   43,    0,   43,    0,   43,    0,   43,    0,
   43,    0,    0,    0,   44,   43,   43,    0,   44,    0,
   44,   44,    0,   44,    0,   44,    0,   44,    0,   44,
    0,    0,   13,    0,   44,   44,   13,    0,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,    0,    0,
   36,    0,   13,   13,   36,    0,   36,   36,    0,   36,
    0,   36,    0,   36,    0,   36,    0,    0,   37,    0,
   36,   36,   37,    0,   37,   37,    0,   37,    0,   37,
    0,   37,    0,   37,    0,    0,    7,    0,   37,   37,
    7,    0,    7,    7,    0,    7,   42,    7,    0,    7,
    0,    7,  125,  126,  127,   55,    7,    7,   55,    0,
    4,    0,    0,    0,   36,    0,    6,    7,   55,    8,
   55,    1,    0,    9,    0,   10,    0,    0,    4,    0,
   11,   12,   47,    0,    6,    7,    0,    8,    0,    1,
    0,    9,   95,   10,    0,    0,    0,    0,   11,   12,
    0,   55,   55,    4,    0,   55,   55,   55,    0,    6,
    7,    0,    8,    0,    1,    0,    9,    0,   10,    0,
    0,    0,   55,   11,   12,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   55,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   41,   42,   43,   44,   45,   41,   47,   41,
   45,  123,   14,   80,   41,   45,   40,  109,  268,    0,
   30,  271,  265,   33,  268,  268,   45,   29,   59,   60,
   45,   42,   92,   43,   65,   41,   47,   43,    0,   45,
  273,   41,   42,   43,   44,   45,   41,   47,   43,   80,
   45,   41,   40,  259,   44,  147,  262,    0,  279,   59,
   41,   92,   43,   44,   45,   40,    0,   41,  110,    3,
   44,    5,  103,    7,  134,    0,   91,  144,   59,   41,
   14,   43,   44,   45,  123,   41,   41,   97,   44,   44,
   43,   40,   45,    0,   40,   29,  138,   59,   41,  123,
   43,   44,   45,  134,    3,  268,    5,   76,   77,   59,
  152,    0,   40,  123,  277,  278,   59,  159,   60,  121,
   87,  163,   81,   82,  257,   59,   60,  260,   59,    0,
  263,   65,   99,  266,   59,  268,  257,  270,   44,  272,
  261,  267,  263,  110,   41,  266,   80,  268,  267,  270,
  257,  272,   59,  260,   93,   41,  263,   91,   92,  266,
   94,  268,   41,  270,   40,  272,  257,  279,  125,  103,
   59,  138,  263,  260,  108,  266,  258,  268,  267,  270,
   62,  272,  116,  123,  260,  152,   41,  121,   60,   61,
   62,  279,  159,  125,   44,  257,  163,   41,  260,   44,
  134,  263,  264,  261,  266,   40,  268,   59,  270,  261,
  272,   44,  125,   59,  262,  277,  278,    0,    0,   41,
  116,  261,  256,   64,   34,  103,  265,  268,   -1,  268,
   99,   -1,  267,  268,  268,  159,  268,  267,  277,  278,
  279,  268,   -1,  277,  278,  277,  278,   -1,  267,  268,
  277,  278,  267,  268,  269,   -1,   -1,  257,  277,  278,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,  257,  257,  277,  278,  261,
  261,  263,  263,  264,  266,  266,  268,  268,  270,  270,
  272,  272,   -1,   -1,   -1,  257,  277,  278,   -1,  261,
   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   -1,   -1,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,   -1,   -1,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,   -1,  257,   -1,  277,  278,
  261,   -1,  263,  264,   -1,  266,   21,  268,   -1,  270,
   -1,  272,  274,  275,  276,   30,  277,  278,   33,   -1,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,   43,  266,
   45,  268,   -1,  270,   -1,  272,   -1,   -1,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   67,  272,   -1,   -1,   -1,   -1,  277,  278,
   -1,   76,   77,  257,   -1,   80,   81,   82,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,
   -1,   -1,   97,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  144,
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

//#line 183 "Gramatica.y"
																	 
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
//#line 469 "Parser.java"
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
        yyerror(" Error Sintactico");
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
//#line 76 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 37:
//#line 77 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 57:
//#line 118 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 58:
//#line 119 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 79:
//#line 165 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 690 "Parser.java"
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
