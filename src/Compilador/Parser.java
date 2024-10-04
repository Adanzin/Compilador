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
   12,   12,   14,   13,   15,    5,    5,    5,    5,    5,
    5,    5,   17,   17,   23,   23,   24,   25,   25,   16,
   16,   16,   26,   26,   26,   27,   27,   27,   27,   10,
   10,   22,    1,   11,   11,   18,   18,   28,   31,   31,
   31,   31,   31,   31,   30,   30,   33,   32,   29,   29,
   37,   36,   34,   34,   35,   21,   21,   19,   20,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    9,    6,    9,    8,
    3,    1,    2,    1,    4,    1,    1,    1,    1,    4,
    4,    1,    3,    6,    4,    5,    1,    3,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    4,    3,
    1,    1,    1,    1,    2,   10,    8,    7,    1,    1,
    1,    1,    1,    1,    1,    1,    4,    2,    1,    1,
    3,    1,    2,    1,    1,    1,    2,    5,    2,
};
final static short yydefred[] = {                         0,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   15,    0,    0,    0,    8,    9,   10,   11,   12,
    0,   32,   26,   27,   28,   29,    0,    0,    0,    0,
    0,    0,    0,    0,   79,    2,    0,    6,    0,   52,
    0,   51,    0,    0,    0,    0,    1,   54,   76,    0,
    0,   47,    0,    0,    0,   48,    0,   45,    0,    0,
    0,    0,    5,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   77,   30,    0,    0,   31,    0,    0,    0,
    0,   16,    0,    0,   25,    0,    0,    0,    0,   22,
   50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   43,   44,    0,    0,    0,   75,   78,   72,   69,
   70,    0,   23,    0,    0,    0,    0,   60,   62,   64,
   61,   59,   63,    0,    0,   49,    0,   35,   18,    0,
    0,   74,    0,    0,   21,    0,    0,    0,   36,    0,
   71,   73,    0,    0,    0,    0,    0,   57,    0,   65,
   66,    0,    0,   20,   58,    0,   68,    0,   17,   19,
    0,   56,   67,
};
final static short yydgoto[] = {                          2,
   40,  144,   15,   16,   17,   18,   19,   20,   21,   41,
   52,   89,  145,   90,   22,   69,   23,   24,   25,   26,
   54,   27,   56,  100,   70,   57,   58,   46,  108,  149,
  124,  150,  151,  131,  109,  110,  111,
};
final static short yysindex[] = {                      -246,
    0,    0,  -65,   -8,  209,   -4, -242,    9,   15, -200,
    0,    0,    0,  156,   47,    0,    0,    0,    0,    0,
 -235,    0,    0,    0,    0,    0, -112,   74,  193,  -14,
   64, -153,  -11,   74,    0,    0,   69,    0,  -19,    0,
   85,    0,  -11, -136,  -11,   91,    0,    0,    0, -134,
   43,    0,   25,   96,  -27,    0,  -23,    0, -221, -221,
   26,   97,    0,  100, -221, -246,   70,   16,   70,   10,
 -114,    0,    0,    0,  -11,  -11,    0, -121,  -45,  -11,
  -11,    0,   88,   28,    0,  225, -221, -246,  -33,    0,
    0, -125,  -11,  129,  225,  -23,  -23,   30, -221,  115,
  113,    0,    0, -246,  -29,   57,    0,    0,    0,    0,
    0,  -31,    0, -102,  116,  -11,   70,    0,    0,    0,
    0,    0,    0,  121,  103,    0,  -26,    0,    0,  119,
   19,    0,  -96,  209,    0,   70,  -11, -181,    0,  -29,
    0,    0,  209,  209,  -92,   45,  241,    0,  106,    0,
    0,   48,  -91,    0,    0,   57,    0,  -88,    0,    0,
   38,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -38,  175,  130,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   76,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,   20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   94,    0,   46,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,   58,    0,    0,    0,
  137,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  112,    0,    0,    0,    0,
    0,    0,    0,  -82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  356,  118,   -2,    0,   21,    0,    0,    0,   60,    0,
  -87,  -64,   37,  -75,    0,   -5,    0,    0,    0,    0,
    0,   29,    0,    0,  -62,  -35,   11,  147,   89,    0,
    0,    0,    0,   27,  -13,    0,    0,
};
final static int YYTABLESIZE=513;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   46,   52,   52,   52,   52,   52,   52,  114,   52,  133,
   44,   37,   79,  115,  139,   50,  101,  130,   80,   42,
   65,    1,  112,   81,   53,    1,   37,   61,   31,   39,
   50,   28,    1,   50,  127,   30,  115,   67,   40,   96,
   97,   46,   46,   46,   46,   46,    1,   46,   33,   42,
   94,  115,  152,   93,   34,   11,   12,   41,   55,   46,
   42,   55,   42,   42,   42,   74,   85,   75,   75,   76,
   76,   55,   35,   55,  146,   13,   51,  147,   42,   40,
  148,   40,   40,   40,   52,  155,   39,  117,   93,   39,
  102,  103,  132,   33,   91,   78,   38,   40,   41,   38,
   41,   41,   41,   55,   55,   38,  107,   55,   55,   55,
  136,   34,   75,   45,   76,  107,   41,  142,   83,   84,
   14,   55,   29,   59,   88,   60,  107,   63,   66,    7,
   68,   71,   72,  157,   13,   73,   77,   86,   99,   87,
   92,   37,  132,   95,   55,   98,   88,  142,   88,  104,
  105,  107,   33,  116,  126,  128,   93,  134,   88,  135,
  137,  138,  140,  143,  158,   55,   43,  107,  154,  160,
   34,   88,  159,  162,    4,    3,  107,   37,   24,  153,
   62,  107,  161,  125,    0,    0,   88,    0,  121,  123,
  122,    4,    0,    0,    5,    0,    0,    6,    7,    0,
    8,    0,    1,    0,    9,    0,   10,    0,    0,    0,
    0,   11,   12,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    1,    0,    0,    0,   16,    0,    0,   16,
    0,   11,   12,    0,    1,    0,    1,   48,   16,   16,
   52,    1,    0,   11,   12,   11,   12,    0,   64,    0,
   11,   12,   48,    1,   49,   48,    1,   46,    0,    0,
    0,   46,    0,   46,   46,    0,   46,    0,   46,    0,
   46,    0,   46,    0,    0,    4,   42,   46,   46,  141,
   42,    6,   42,   42,    8,   42,    1,   42,    9,   42,
   10,   42,    0,    0,    4,   40,   42,   42,  163,   40,
    6,   40,   40,    8,   40,    1,   40,    9,   40,   10,
   40,    0,    0,    4,   41,   40,   40,    0,   41,    6,
   41,   41,    8,   41,    1,   41,    9,   41,   10,   41,
    0,    0,   13,    0,   41,   41,   13,    0,   13,   13,
    0,   13,    0,   13,    0,   13,    0,   13,    0,    0,
   33,    0,   13,   13,   33,    3,   33,   33,   13,   33,
   13,   33,   32,   33,    0,   33,    0,    0,   34,   13,
   33,   33,   34,    0,   34,   34,    0,   34,    0,   34,
    0,   34,    0,   34,   13,    0,    7,    0,   34,   34,
    7,    0,    7,    7,    0,    7,    0,    7,    0,    7,
    0,    7,  118,  119,  120,    0,    7,    7,    0,    0,
    0,    0,    4,    0,   82,   82,   36,    0,    6,    7,
   82,    8,    0,    1,    0,    9,    0,   10,    0,    0,
    0,    0,   11,   12,   13,    0,    0,    0,    0,    0,
    0,    0,   82,  113,   82,    0,    0,    0,    0,    4,
    0,    0,    0,   47,   82,    6,    7,    0,    8,  129,
    1,    0,    9,    0,   10,    4,    0,   82,    0,   11,
   12,    6,    7,    0,    8,    0,    1,    0,    9,    0,
   10,    4,   82,    0,  106,   11,   12,    6,    0,   13,
    8,    0,    1,    0,    9,    0,   10,    4,   13,   13,
  156,    0,    0,    6,    0,    0,    8,    0,    1,    0,
    9,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   41,   42,   43,   44,   45,   41,   47,   41,
  123,   14,   40,   89,   41,   45,   79,  105,   42,    0,
   40,  268,   87,   47,   30,  268,   29,   33,  271,  265,
   45,   40,  268,   45,   99,   40,  112,   43,    0,   75,
   76,   41,   42,   43,   44,   45,  268,   47,   40,   21,
   41,  127,  140,   44,   40,  277,  278,    0,   30,   59,
   41,   33,   43,   44,   45,   41,   41,   43,   43,   45,
   45,   43,  273,   45,  137,    0,   91,  259,   59,   41,
  262,   43,   44,   45,  123,   41,   41,   93,   44,   44,
   80,   81,  106,    0,   66,  123,   41,   59,   41,   44,
   43,   44,   45,   75,   76,   59,   86,   79,   80,   81,
  116,    0,   43,   40,   45,   95,   59,  131,   59,   60,
    3,   93,    5,   60,   65,  279,  106,   59,   44,    0,
  267,   41,  267,  147,   59,   93,   41,   41,   79,   40,
  125,  144,  156,  258,  116,  267,   87,  161,   89,   62,
  123,  131,   59,  279,  125,   41,   44,  260,   99,   44,
   40,   59,   44,  260,   59,  137,  279,  147,  261,  261,
   59,  112,  125,  262,    0,    0,  156,   41,  261,  143,
   34,  161,  156,   95,   -1,   -1,  127,   -1,   60,   61,
   62,  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,
  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  267,  268,   -1,   -1,   -1,  265,   -1,   -1,  268,
   -1,  277,  278,   -1,  268,   -1,  268,  267,  277,  278,
  279,  268,   -1,  277,  278,  277,  278,   -1,  268,   -1,
  277,  278,  267,  268,  269,  267,  268,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,  257,  257,  277,  278,  261,
  261,  263,  263,  264,  266,  266,  268,  268,  270,  270,
  272,  272,   -1,   -1,  257,  257,  277,  278,  261,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   -1,   -1,  257,  257,  277,  278,   -1,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   -1,   -1,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
  257,   -1,  277,  278,  261,    0,  263,  264,    3,  266,
    5,  268,    7,  270,   -1,  272,   -1,   -1,  257,   14,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   29,   -1,  257,   -1,  277,  278,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  274,  275,  276,   -1,  277,  278,   -1,   -1,
   -1,   -1,  257,   -1,   59,   60,  261,   -1,  263,  264,
   65,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
   -1,   -1,  277,  278,   79,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   87,   88,   89,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,  261,   99,  263,  264,   -1,  266,  104,
  268,   -1,  270,   -1,  272,  257,   -1,  112,   -1,  277,
  278,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
  272,  257,  127,   -1,  260,  277,  278,  263,   -1,  134,
  266,   -1,  268,   -1,  270,   -1,  272,  257,  143,  144,
  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,
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
"declaracion_funciones : tipo FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END",
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

//#line 177 "Gramatica.y"
																	 
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
//#line 464 "Parser.java"
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
case 30:
//#line 64 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 31:
//#line 65 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 32:
//#line 66 "Gramatica.y"
{RETORNO = true;}
break;
case 33:
//#line 70 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 34:
//#line 71 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 54:
//#line 112 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 55:
//#line 113 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 76:
//#line 159 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 677 "Parser.java"
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
