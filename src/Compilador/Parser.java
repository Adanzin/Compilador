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
    0,    0,    1,    1,    1,    1,    2,    2,    2,    3,
    3,    4,    4,    4,    6,    9,    9,    8,    8,    7,
    7,    7,    7,   12,   12,   14,   13,   15,    5,    5,
    5,    5,    5,    5,    5,   17,   17,   22,   22,   23,
   24,   24,   16,   16,   16,   25,   25,   25,   26,   26,
   26,   26,   10,   10,   11,   11,   18,   18,   27,   30,
   30,   30,   30,   30,   30,   29,   29,   32,   31,   28,
   28,   36,   35,   33,   33,   34,   21,   21,   19,   20,
};
final static short yylen[] = {                            2,
    2,    1,    3,    2,    2,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    2,    1,    1,    9,    6,    9,
    8,    9,    8,    3,    1,    2,    1,    4,    1,    1,
    1,    1,    4,    4,    1,    3,    6,    4,    5,    1,
    3,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    3,    1,    1,    2,   10,    8,    7,    1,
    1,    1,    1,    1,    1,    1,    1,    4,    2,    1,
    1,    3,    1,    2,    1,    1,    1,    2,    5,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,   17,
    0,    2,    0,    0,   10,   11,   12,   13,   14,    0,
   35,   29,   30,   31,   32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,   80,    4,    0,
    8,    0,   54,    0,    0,    0,    3,   55,    0,   77,
    0,    0,   50,    0,    0,   51,    0,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    0,    0,
    0,    0,    0,    0,    0,   56,   78,   33,    0,    0,
   34,    0,    0,    0,    0,   28,    0,    0,    0,   25,
    0,    0,    0,    0,   53,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   46,   47,    0,    0,    0,   26,
    0,    0,    0,    0,    0,   76,   79,   73,   70,   71,
    0,    0,    0,   61,   63,   65,   62,   60,   64,    0,
    0,   52,    0,   38,    0,   19,    0,    0,   24,    0,
    0,   75,    0,    0,    0,    0,   39,    0,    0,    0,
    0,   72,   74,    0,    0,    0,    0,   58,    0,   66,
   67,    0,    0,   23,    0,   21,   59,    0,   69,    0,
   18,   22,   20,    0,   57,   68,
};
final static short yydgoto[] = {                         11,
   12,  150,   14,   15,   16,   17,   18,   19,   20,   44,
   53,   89,  151,   90,   21,   71,   22,   23,   24,   25,
   55,   56,  101,   72,   57,   58,   46,  117,  159,  130,
  160,  161,  141,  118,  119,  120,
};
final static short yysindex[] = {                       -90,
  -15,  170,  -11, -230,   -4, -118,   13, -254,    0,    0,
    0,    0,  -62,   36,    0,    0,    0,    0,    0, -214,
    0,    0,    0,    0,    0,   15, -110,  154,  -42, -201,
   27,  -38,  -19,  -38, -153,    0,   15,    0,    0,   51,
    0,  -18,    0,   75,  -38,   85,    0,    0,  -26,    0,
 -135,   41,    0,   25,   99,    0,   10,    0, -260, -260,
   28,  101, -260,   46,   26,  115,    0,  117, -260, -106,
   46,   44, -100, -104,  -21,    0,    0,    0,  -38,  -38,
    0,  -38,  -38,   45,  102,    0, -260,  -96,  -41,    0,
 -102, -139, -260,  -39,    0,  -38,  129, -139,   50, -260,
  138,  139,   10,   10,    0,    0,  -36,  -84,  -37,    0,
  -75,  142,  -38,   38, -113,    0,    0,    0,    0,    0,
  -35,  -67,   46,    0,    0,    0,    0,    0,    0,  156,
  152,    0,  -33,    0,  168,    0,  -47,  170,    0,   46,
 -157,    0,  -46,  170,  -38, -166,    0,  -36,  170,  170,
  -44,    0,    0,  170,  -43,   64,  -63,    0,  160,    0,
    0,   95,  -40,    0,  -29,    0,    0,   38,    0,  -34,
    0,    0,    0,   19,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  222,  130,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  223,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   76,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    0,    0,   94,    0,    0,    0,    0,    0,    0,
   79,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  183,   39,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   84,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  112,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -28,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  228,   31,   -1,    0,   24,    0,    0,    0,  350,    0,
  -92,  -53,  -77,  -59,    0,   -6,    0,    0,    0,    0,
    0,    0,    0,  -64,   57,   61,  198,  150,    0,    0,
    0,    0,   83,  -82,    0,    0,
};
final static int YYTABLESIZE=483;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        111,
   49,  122,   51,  137,   35,  143,   51,  147,   51,   35,
  102,   40,   35,   75,  135,   94,    9,   10,   38,   45,
   63,   69,   54,   51,   26,   61,   40,   64,   29,  112,
   13,  142,   28,  109,  112,   32,   13,   30,   43,  121,
   31,   49,   49,   49,   49,   49,  133,   49,   52,  112,
   42,   82,   37,   43,   45,  162,   83,   44,  153,   49,
   45,  112,   45,   45,   45,   78,  155,   79,   86,   80,
   79,  163,   80,  112,  169,   15,  165,   59,   45,   43,
  156,   43,   43,   43,   97,  142,   60,   96,   79,  123,
   80,  153,  157,   36,   41,  158,   74,   43,   44,    1,
   44,   44,   44,  152,  167,    3,  140,   96,    5,   67,
  115,   37,    7,   65,    8,  116,   44,    1,   70,   42,
  114,  116,   42,    3,   41,   73,    5,   41,  115,    9,
    7,   76,    8,   77,   15,  103,  104,  116,    1,   81,
   87,    2,  105,  106,    3,    4,   33,    5,   40,   27,
   91,    7,   36,    8,   33,   92,   93,   98,    9,   10,
   34,   95,   99,  108,  116,   34,    1,  107,   34,    2,
   37,  110,    3,    4,  132,    5,  113,    6,  134,    7,
  116,    8,   96,  136,  138,  139,    9,   10,  127,  129,
  128,  116,  144,    1,    1,  145,  168,  116,   39,    3,
    3,    4,    5,    5,  115,   27,    7,    7,    8,    8,
  146,  148,  149,  154,    9,   10,  164,  166,  170,  171,
  172,    6,    5,   40,   48,   49,   50,  175,   48,   49,
   48,  173,   27,   36,   66,    9,   10,    9,   10,    9,
   10,    9,   10,    9,   10,   48,   49,  131,   62,   68,
  174,    0,    0,    0,    0,    9,   10,   49,    0,    0,
    0,   49,    0,   49,   49,    0,   49,    0,   49,    0,
   49,    0,   49,    0,    0,    1,   45,   49,   49,  176,
   45,    3,   45,   45,    5,   45,  115,   45,    7,   45,
    8,   45,    0,    0,    1,   43,   45,   45,    0,   43,
    3,   43,   43,    5,   43,  115,   43,    7,   43,    8,
   43,    0,    0,    0,   44,   43,   43,    0,   44,    0,
   44,   44,    0,   44,    0,   44,    0,   44,    0,   44,
    0,    0,   15,    0,   44,   44,   15,    0,   15,   15,
    0,   15,    0,   15,    0,   15,    0,   15,    0,    0,
   36,    0,   15,   15,   36,    0,   36,   36,    0,   36,
    0,   36,    0,   36,    0,   36,    0,    0,   37,    0,
   36,   36,   37,    0,   37,   37,    0,   37,    0,   37,
    0,   37,    0,   37,    0,    0,    9,    0,   37,   37,
    9,    0,    9,    9,    0,    9,    0,    9,    0,    9,
    0,    9,  124,  125,  126,    0,    9,    9,   84,   85,
    1,    0,   88,    0,   47,    0,    3,    4,   88,    5,
    0,   27,    0,    7,  100,    8,    1,    0,    0,    0,
    9,   10,    3,    4,    0,    5,   88,   27,   88,    7,
    0,    8,   88,   88,    0,    0,    9,   10,    0,   88,
    0,    0,    0,    0,    0,    0,    0,    0,   88,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   88,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   45,   41,  123,   41,   45,   41,   45,  123,
   75,   13,  123,   40,  107,   69,  277,  278,  273,    0,
   40,   40,   29,   45,   40,   32,   28,   34,   40,   89,
    0,  114,    2,   87,   94,   40,    6,  268,    0,   93,
  271,   41,   42,   43,   44,   45,  100,   47,   91,  109,
  265,   42,   40,  268,   40,  148,   47,    0,  141,   59,
   41,  121,   43,   44,   45,   41,  144,   43,   41,   45,
   43,  149,   45,  133,  157,    0,  154,  279,   59,   41,
  145,   43,   44,   45,   41,  168,   60,   44,   43,   96,
   45,  174,  259,    0,   59,  262,  123,   59,   41,  257,
   43,   44,   45,  261,   41,  263,  113,   44,  266,   59,
  268,    0,  270,  267,  272,   92,   59,  257,   44,   41,
  260,   98,   44,  263,   41,   41,  266,   44,  268,    0,
  270,  267,  272,   93,   59,   79,   80,  114,  257,   41,
   40,  260,   82,   83,  263,  264,  265,  266,  150,  268,
  125,  270,   59,  272,  265,   41,   40,  258,  277,  278,
  279,  268,  267,   62,  141,  279,  257,  123,  279,  260,
   59,  268,  263,  264,  125,  266,  279,  268,   41,  270,
  157,  272,   44,  268,  260,   44,  277,  278,   60,   61,
   62,  168,  260,  257,  257,   40,  260,  174,  261,  263,
  263,  264,  266,  266,  268,  268,  270,  270,  272,  272,
   59,   44,  260,  260,  277,  278,  261,  261,   59,  125,
  261,    0,    0,   41,  267,  268,  269,  262,  267,  268,
  267,  261,  261,    6,   37,  277,  278,  277,  278,  277,
  278,  277,  278,  277,  278,  267,  268,   98,  268,  268,
  168,   -1,   -1,   -1,   -1,  277,  278,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,
  270,   -1,  272,   -1,   -1,  257,  257,  277,  278,  261,
  261,  263,  263,  264,  266,  266,  268,  268,  270,  270,
  272,  272,   -1,   -1,  257,  257,  277,  278,   -1,  261,
  263,  263,  264,  266,  266,  268,  268,  270,  270,  272,
  272,   -1,   -1,   -1,  257,  277,  278,   -1,  261,   -1,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
   -1,   -1,  257,   -1,  277,  278,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  272,   -1,   -1,
  257,   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,   -1,   -1,  257,   -1,
  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,   -1,   -1,  257,   -1,  277,  278,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  274,  275,  276,   -1,  277,  278,   59,   60,
  257,   -1,   63,   -1,  261,   -1,  263,  264,   69,  266,
   -1,  268,   -1,  270,   75,  272,  257,   -1,   -1,   -1,
  277,  278,  263,  264,   -1,  266,   87,  268,   89,  270,
   -1,  272,   93,   94,   -1,   -1,  277,  278,   -1,  100,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  109,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  121,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  133,
};
}
final static short YYFINAL=11;
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
"programa : ID cuerpo_programa",
"programa : cuerpo_programa",
"cuerpo_programa : BEGIN sentencias END",
"cuerpo_programa : sentencias END",
"cuerpo_programa : BEGIN sentencias",
"cuerpo_programa : sentencias",
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
"declaracion_subtipo : TYPEDEF ID ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID",
"declaracion_funciones : tipo FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : ID FUN ID '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"declaracion_funciones : ID FUN '(' parametros_formal ')' BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID",
"cuerpo_funcion : sentencias",
"retorno : RET '(' expresion_arit ')'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : OUTF '(' expresion_arit ')'",
"sentencia_ejecutable : OUTF '(' cadena ')'",
"sentencia_ejecutable : retorno",
"asignacion : ID ASIGNACION expresion_arit",
"asignacion : ID '{' CTE '}' ASIGNACION expresion_arit",
"invocacion : ID '(' parametro_real ')'",
"invocacion : ID '(' tipo parametros_formal ')'",
"parametro_real : list_expre",
"list_expre : list_expre ',' expresion_arit",
"list_expre : expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE_con_sig",
"factor : invocacion",
"factor : ID '{' CTE '}'",
"variables : variables ',' ID",
"variables : ID",
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

//#line 174 "Gramatica.y"
																	 
private static boolean RETORNO = false;

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
//#line 461 "Parser.java"
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
{System.out.println(" Fin de a compilacion ");}
break;
case 2:
//#line 10 "Gramatica.y"
{System.out.println(" Error en la linea " + AnalizadorLexico.saltoDeLinea + ": Falta nombre del programa ");}
break;
case 3:
//#line 12 "Gramatica.y"
{System.out.println(" Se identifico el cuerpo_programa");}
break;
case 4:
//#line 13 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador BEGIN ");}
break;
case 5:
//#line 14 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta el delimitador END ");}
break;
case 6:
//#line 15 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan los delimitadores del programa ");}
break;
case 9:
//#line 20 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia ");}
break;
case 20:
//#line 45 "Gramatica.y"
{if(RETORNO==false){System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion ");}}
break;
case 21:
//#line 46 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
break;
case 22:
//#line 47 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico una funcion de <Class> ");}
break;
case 23:
//#line 48 "Gramatica.y"
{System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion ");}
break;
case 33:
//#line 68 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 34:
//#line 69 "Gramatica.y"
{System.out.println("En la linea :" + AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 35:
//#line 70 "Gramatica.y"
{RETORNO = true;}
break;
case 36:
//#line 74 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 37:
//#line 75 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 55:
//#line 109 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 56:
//#line 110 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 77:
//#line 156 "Gramatica.y"
{System.out.println(" > Se leyo la cadena multi linea < ");}
break;
//#line 686 "Parser.java"
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
