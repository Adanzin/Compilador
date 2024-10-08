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
public final static short ERROR=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    2,    2,    2,    3,    3,
    4,    4,    4,    6,    9,    9,   11,   11,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,    8,    7,    7,   13,   13,   13,   15,
   14,   16,    5,    5,    5,    5,    5,    5,    5,   22,
   22,   22,   22,   18,   18,   25,   25,   25,   26,   26,
   17,   17,   17,   17,   27,   27,   27,   28,   28,   28,
   28,   10,   10,   24,    1,   12,   12,   12,   12,   19,
   19,   19,   19,   19,   19,   19,   29,   29,   29,   29,
   29,   29,   29,   29,   32,   32,   32,   32,   32,   32,
   31,   31,   34,   33,   30,   30,   38,   37,   35,   35,
   36,   23,   20,   20,   21,   21,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    3,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    1,    1,    1,    9,    8,
    8,    7,    6,    8,    7,    8,    6,    8,    8,    5,
    5,    5,    4,    6,    7,    6,    3,    2,    3,    2,
    1,    4,    1,    1,    1,    1,    1,    1,    1,    4,
    3,    4,    4,    3,    6,    4,    7,    3,    3,    1,
    3,    3,    1,    1,    3,    3,    1,    1,    1,    1,
    4,    3,    1,    1,    1,    1,    2,    1,    2,    8,
    6,    5,    7,    6,    4,    7,    9,    8,    8,    7,
    5,    4,    4,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    2,    1,    1,    3,    1,    3,    1,
    1,    1,    3,    3,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   75,    0,    0,    0,    0,    0,    0,    0,    0,
   47,   17,   18,    0,    0,    0,    9,   10,   11,   12,
   13,    0,   16,   49,   43,   44,   45,   46,   48,    0,
    0,    0,   64,   76,   78,    0,    0,    0,   69,    0,
    0,   70,    0,   67,    0,    0,    0,    0,    0,    0,
    0,    0,  116,  115,    2,    0,    7,    0,   74,    0,
   73,    0,    0,    0,    4,    0,    0,    0,   77,   79,
    0,   96,   98,  100,   97,   95,    0,    0,   99,    0,
    0,    0,    0,    0,  112,   51,    0,    0,    0,    0,
    0,    0,   15,    0,    0,    0,    0,    0,  114,    0,
  111,  113,  108,  105,  106,    6,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,   58,    0,
    0,    0,    0,    0,    0,   65,   66,    0,   85,    0,
    0,  101,  102,   53,   50,   52,    0,    0,    0,   33,
    0,    0,    0,    0,   42,    0,  110,    0,    0,   38,
    0,    0,    0,   72,    0,    0,    0,    0,    0,    0,
   56,   92,   71,    0,  104,    0,    0,    0,    0,   32,
   31,    0,   30,    0,    0,    0,  107,    0,    0,   39,
   40,   37,    0,    0,    0,    0,   91,    0,    0,    0,
    0,   81,    0,   84,    0,   34,   23,    0,    0,    0,
   27,    0,    0,  109,    0,   36,    0,    0,    0,    0,
  103,   86,    0,    0,    0,    0,    0,   25,    0,   35,
    0,    0,   57,   80,   28,   29,   24,   26,    0,   20,
    0,   88,   19,   87,
};
final static short yydgoto[] = {                          3,
   38,  183,   16,   17,   18,   19,   20,   21,   22,   60,
   23,   39,  109,  184,  152,   24,  114,   25,   26,   27,
   28,   29,   90,   41,   42,   68,   43,   44,   45,  102,
  131,   80,  132,  133,  146,  103,  104,  105,
};
final static short yysindex[] = {                      -165,
  617,    0,    0,  535,  -37,   35,  104,   61,  -37, -223,
    0,    0,    0,    0,  553,  -18,    0,    0,    0,    0,
    0, -230,    0,    0,    0,    0,    0,    0,    0, -110,
  617,  571,    0,    0,    0,  -35, -240,   80,    0,  142,
  -26,    0,   97,    0, -136,  296,  132, -135, -135, -153,
  -45,  489,    0,    0,    0,   81,    0,  -28,    0,  101,
    0,  -45, -118,  600,    0,  -45,  142,   36,    0,    0,
  333,    0,    0,    0,    0,    0,  -20,  -20,    0,  -45,
 -116,  -20,  -20,  694,    0,    0,   80,  277,   14,  115,
 -101, -135,    0,  -60,   37,  110,  -97,  105,    0, -145,
    0,    0,    0,    0,    0,    0,  139,  340,  -72,  -78,
   31,   76,    0,   31,  154,  -45,  -45,  410,    0,  167,
  122,   97,   97,  125,   89,    0,    0,  722,    0,  157,
  161,    0,    0,    0,    0,    0,  -43,  -53,  -78,    0,
  -14,  -78,  -14,  -41,    0,  -48,    0,  -32,  193,    0,
  -78,  203,  617,    0,  -23,  410,  141,   31,  223,  -45,
    0,    0,    0, -145,    0,  -50,    8,  147, -217,    0,
    0,  239,    0,  245,  -38,  249,    0, -145,  617,    0,
    0,    0,  617,   34,  -45,  266,    0,  -45,  148,  -36,
  708,    0,  253,    0,  -23,    0,    0,  -14,  -14,  -14,
    0,   -8,  -14,    0,   78,    0,   31,  -45,  205,  311,
    0,    0,   96,  235,  237,  251,   49,    0,  260,    0,
  210,  323,    0,    0,    0,    0,    0,    0,  270,    0,
  356,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -106,    0,  314,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  402,    0,    0,    0,    0,    0,    1,    0,    0,
   24,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  116,
    0,    0,    0,  404,    0,    0,  291,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -27,    0,    0,    0,
   11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  135,    0,    0,  291,    0,    0,    0,    0,    0,    0,
    0,   70,   93,  375,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  640,  346,    0,    0,
    0,    0,    0,    0,    0,  158,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  145,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  177,    0,    0,    0,
    0,    0,  196,    0,    0,    0,    0,    0,  227,    0,
    0,  658,    0,    0,    0,    0,    0,    0,  250,    0,
  677,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  605,   33,   17,    0,  339,    0,    0,    0,  -19,    0,
  343,  531,  304,  241,    0,    0,  593,    0,    0,    0,
    0,    0,    0,  676,    0,  -47,  128,  350,  416,  345,
  261,  -46,    0,    0,  273,   19,    0,    0,
};
final static int YYTABLESIZE=995;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   74,  139,   36,   37,   66,  200,   37,   37,  169,   37,
  178,  108,   63,   74,   74,   74,   74,   74,  115,   74,
  116,  137,  178,   68,   37,  143,   69,   94,   95,   96,
   37,   56,   53,   15,   58,  217,   32,    2,  196,   70,
   57,   74,   74,   74,   74,   74,   63,   74,   56,   54,
    2,   68,   68,   68,  135,   68,   77,   68,   78,   74,
   74,   74,   74,   64,   68,   68,   68,   68,   68,   61,
   68,  159,  138,   77,   46,   78,  118,  144,   88,  117,
   56,  175,   68,   68,   68,   68,  201,   63,  151,   63,
   63,   63,   62,   37,    1,   74,   81,   71,   71,   71,
   51,   71,    2,   71,   56,   63,   63,   63,   63,  186,
   61,    5,   61,   61,   61,   14,  218,    6,  147,   71,
    8,   84,    2,   74,    9,   97,   10,   11,   61,   61,
   61,   61,    2,   62,   54,   62,   62,   62,   82,  106,
  209,   12,   13,   83,  110,  145,  165,   77,  112,   78,
  125,   62,   62,   62,   62,  136,   14,   82,   15,  141,
  221,   15,  161,   49,   77,  162,   78,   77,   62,   78,
    2,  142,   74,  228,   14,   54,   55,   62,  108,   12,
   13,  187,  147,   77,   77,   78,   78,  153,  210,    2,
   77,   92,   78,   54,  156,   83,  204,  117,   82,   56,
  155,   75,   79,   76,  122,  123,  160,    2,  191,  165,
   33,  192,  177,  163,    2,  166,   82,   55,   33,  167,
   33,   34,    2,  168,  211,   34,   22,  179,   34,   34,
    2,   34,    2,  180,   35,   55,   83,   15,   35,  107,
   15,   35,   35,  182,   35,  222,   34,    2,  117,   21,
  231,   74,   34,  117,   83,  185,   74,   74,   74,   35,
   74,   74,  188,   74,   74,   35,   74,   22,   74,  194,
   74,  195,   74,   74,   74,   74,   74,   74,   74,   68,
   68,   68,  198,   68,   68,   22,   68,   68,  199,   68,
   21,   68,  203,   68,  206,   68,   68,   68,   68,   68,
   68,   68,   63,   63,   63,  208,   63,   63,   21,   63,
   63,  213,   63,    8,   63,   34,   63,  134,   63,   63,
   63,   63,   63,   63,   63,   61,   61,   61,   35,   61,
   61,   60,   61,   61,   60,   61,   86,   61,  220,   61,
   37,   61,   61,   61,   61,   61,   61,   61,   62,   62,
   62,  223,   62,   62,    8,   62,   62,  224,   62,  225,
   62,  226,   62,  232,   62,   62,   62,   62,   62,   62,
   62,    2,   14,  119,   47,  227,   14,   37,   14,   14,
  150,   14,   48,   14,  230,   14,   59,   14,   14,   59,
  101,   54,   14,   14,  233,   54,  234,   54,   54,    2,
   54,    5,   54,    3,   54,   41,   54,   54,   12,   13,
  148,   54,   54,  120,   82,   72,   73,   74,   82,  205,
   82,   82,  101,   82,   52,   82,  193,   82,  130,   82,
   82,  126,  127,   55,   82,   82,  190,   55,  101,   55,
   55,    0,   55,    0,   55,    0,   55,    0,   55,   55,
    0,    0,   83,   55,   55,    0,   83,    0,   83,   83,
    0,   83,    0,   83,    0,   83,  101,   83,   83,   75,
   79,   76,   83,   83,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   22,    0,    0,    0,   22,    0,   22,
   22,    0,   22,    0,   22,    0,   22,    0,   22,   22,
    0,    0,  101,   22,   22,    0,   21,    0,    0,    0,
   21,    0,   21,   21,    0,   21,  101,   21,    0,   21,
    0,   21,   21,    0,    0,    0,   21,   21,    0,  101,
    0,    0,    0,    5,    0,    0,    0,    0,    0,    6,
    7,    0,    8,    0,    2,    0,    9,    0,   10,   11,
    0,   33,    5,   12,   13,    0,    0,    0,    6,    7,
    0,    8,   34,    2,   85,    9,    0,   10,   11,    0,
    8,    0,   12,   13,    8,   35,    8,    8,    0,    8,
    0,    8,    0,    8,    0,    8,    8,    0,   33,    0,
    8,    8,    0,    0,    0,  149,    0,   40,    0,   34,
    2,   40,    0,    0,    4,   14,    0,    2,   14,   12,
   13,   50,   35,    0,    0,    0,   12,   13,    0,   14,
    0,    0,    0,    0,    0,    0,   59,    0,   67,    0,
   94,   94,   94,    0,   94,   14,   14,   94,   89,    0,
   94,    0,   94,   98,   94,    0,   94,   94,    0,    0,
   87,   93,   93,   93,  111,    0,   59,    0,    0,    0,
    0,    0,    0,  121,    0,    0,    0,    0,   14,    0,
    0,  172,  124,  174,  176,    0,   30,    0,    0,   30,
    0,    0,    0,   72,   73,   74,    0,    0,   59,    0,
   30,    0,   14,    0,    0,    0,   93,   61,  140,    0,
    0,   93,    0,    0,   59,  202,   30,   30,  157,  158,
    0,    0,   93,    0,   59,    0,    0,    0,    0,    0,
    0,   91,    0,    0,    0,    0,    0,   30,  214,  215,
  216,    0,   59,  219,    0,    0,    0,    0,    0,   30,
    0,    0,  170,  171,   99,    5,  173,  229,  100,    0,
    0,    6,  189,    0,    8,  181,    2,   14,    9,   30,
   10,   11,    0,   30,    0,    0,    0,    0,   59,    0,
    0,    0,    0,  197,    0,   30,    0,  207,    0,    0,
    0,    0,   59,   14,    0,  154,    0,   14,    0,    0,
    0,    5,    0,    0,   31,   59,    0,    6,    7,    0,
    8,    0,    2,   30,    9,    0,   10,   11,    0,    5,
    0,   12,   13,   55,    0,    6,    7,    0,    8,    0,
    2,    0,    9,    0,   10,   11,    0,    5,   30,   12,
   13,   65,    0,    6,    7,    0,    8,    0,    2,   30,
    9,    0,   10,   11,    0,    0,    0,   12,   13,    0,
    0,    0,    0,   30,   30,    0,    5,    0,   30,    0,
  113,    0,    6,    7,    0,    8,   30,    2,    0,    9,
    0,   10,   11,    5,    0,    0,   12,   13,    0,    6,
    7,    0,    8,    0,    2,    0,    9,    0,   10,   11,
    0,    0,    0,   12,   13,   93,   93,   93,    0,   93,
    0,    0,   93,    0,    0,   93,    0,   93,    0,   93,
    0,   93,   93,   90,   90,   90,    0,   90,    0,    0,
   90,    0,    0,   90,    0,   90,    0,   90,    0,   90,
   90,    0,   89,   89,   89,    0,   89,    0,    0,   89,
    0,    0,   89,    0,   89,    0,   89,    0,   89,   89,
    5,    0,  128,  100,    0,  129,    6,    0,    0,    8,
    0,    2,    0,    9,    5,   10,   11,  164,    0,  212,
    6,    0,    0,    8,    0,    2,    0,    9,    5,   10,
   11,  164,    0,    0,    6,    0,    0,    8,    0,    2,
    0,    9,    0,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   62,   40,   45,   40,   44,   45,   45,   62,   45,
   59,   40,  123,   41,   42,   43,  123,   45,   66,   47,
   67,  123,   59,    0,   45,  123,  267,   47,   48,   49,
   45,   15,  256,    1,  265,   44,    4,  268,  256,  280,
   59,   41,   42,   43,   44,   45,    0,   47,   32,  273,
  268,   41,   42,   43,   41,   45,   43,   47,   45,   59,
   60,   61,   62,   31,   41,   42,   43,   44,   45,    0,
   47,  118,   92,   43,   40,   45,   41,   97,   46,   44,
   64,  123,   59,   60,   61,   62,  125,   41,  108,   43,
   44,   45,    0,   45,  260,  123,  123,   41,   42,   43,
   40,   45,  268,   47,   88,   59,   60,   61,   62,  156,
   41,  257,   43,   44,   45,    0,  125,  263,  100,   40,
  266,  258,  268,  123,  270,  279,  272,  273,   59,   60,
   61,   62,  268,   41,    0,   43,   44,   45,   42,   59,
  188,  277,  278,   47,   44,   41,  128,   43,  267,   45,
  267,   59,   60,   61,   62,   41,   41,    0,  265,  123,
  208,  268,   41,   60,   43,   41,   45,   43,  279,   45,
  268,   62,  279,  125,   59,   41,    0,  279,   40,  277,
  278,   41,  164,   43,   43,   45,   45,  260,   41,  268,
   43,   60,   45,   59,   41,    0,  178,   44,   41,  183,
  125,   60,   61,   62,   77,   78,   40,  268,  259,  191,
  256,  262,  261,  125,  268,   59,   59,   41,  256,   59,
  256,  267,  268,  267,  261,  267,    0,  260,  267,  267,
  268,  267,  268,   41,  280,   59,   41,  265,  280,  268,
  268,  280,  280,   41,  280,   41,  267,  268,   44,    0,
   41,  279,  267,   44,   59,  279,  256,  257,  258,  280,
  260,  261,   40,  263,  264,  280,  266,   41,  268,  262,
  270,  125,  272,  273,  274,  275,  276,  277,  278,  256,
  257,  258,   44,  260,  261,   59,  263,  264,   44,  266,
   41,  268,   44,  270,  261,  272,  273,  274,  275,  276,
  277,  278,  256,  257,  258,   40,  260,  261,   59,  263,
  264,   59,  266,    0,  268,  267,  270,   41,  272,  273,
  274,  275,  276,  277,  278,  256,  257,  258,  280,  260,
  261,   41,  263,  264,   44,  266,   41,  268,  261,  270,
   45,  272,  273,  274,  275,  276,  277,  278,  256,  257,
  258,   41,  260,  261,   41,  263,  264,  262,  266,  125,
  268,  125,  270,   41,  272,  273,  274,  275,  276,  277,
  278,  268,  257,   41,  271,  125,  261,   45,  263,  264,
   41,  266,  279,  268,  125,  270,   41,  272,  273,   44,
   52,  257,  277,  278,  125,  261,   41,  263,  264,  268,
  266,    0,  268,    0,  270,  261,  272,  273,  277,  278,
  107,  277,  278,   71,  257,  274,  275,  276,  261,  179,
  263,  264,   84,  266,    9,  268,  166,  270,   84,  272,
  273,   82,   83,  257,  277,  278,  164,  261,  100,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,  257,  277,  278,   -1,  261,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,  128,  272,  273,   60,
   61,   62,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,  164,  277,  278,   -1,  257,   -1,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,  178,  268,   -1,  270,
   -1,  272,  273,   -1,   -1,   -1,  277,  278,   -1,  191,
   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,  256,  257,  277,  278,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,   -1,  272,  273,   -1,
  257,   -1,  277,  278,  261,  280,  263,  264,   -1,  266,
   -1,  268,   -1,  270,   -1,  272,  273,   -1,  256,   -1,
  277,  278,   -1,   -1,   -1,  256,   -1,    5,   -1,  267,
  268,    9,   -1,   -1,    0,    1,   -1,  268,    4,  277,
  278,    7,  280,   -1,   -1,   -1,  277,  278,   -1,   15,
   -1,   -1,   -1,   -1,   -1,   -1,   22,   -1,   36,   -1,
  256,  257,  258,   -1,  260,   31,   32,  263,   46,   -1,
  266,   -1,  268,   51,  270,   -1,  272,  273,   -1,   -1,
   46,   47,   48,   49,   62,   -1,   52,   -1,   -1,   -1,
   -1,   -1,   -1,   71,   -1,   -1,   -1,   -1,   64,   -1,
   -1,  141,   80,  143,  144,   -1,    1,   -1,   -1,    4,
   -1,   -1,   -1,  274,  275,  276,   -1,   -1,   84,   -1,
   15,   -1,   88,   -1,   -1,   -1,   92,   22,   94,   -1,
   -1,   97,   -1,   -1,  100,  175,   31,   32,  116,  117,
   -1,   -1,  108,   -1,  110,   -1,   -1,   -1,   -1,   -1,
   -1,   46,   -1,   -1,   -1,   -1,   -1,   52,  198,  199,
  200,   -1,  128,  203,   -1,   -1,   -1,   -1,   -1,   64,
   -1,   -1,  138,  139,  256,  257,  142,  217,  260,   -1,
   -1,  263,  160,   -1,  266,  151,  268,  153,  270,   84,
  272,  273,   -1,   88,   -1,   -1,   -1,   -1,  164,   -1,
   -1,   -1,   -1,  169,   -1,  100,   -1,  185,   -1,   -1,
   -1,   -1,  178,  179,   -1,  110,   -1,  183,   -1,   -1,
   -1,  257,   -1,   -1,  260,  191,   -1,  263,  264,   -1,
  266,   -1,  268,  128,  270,   -1,  272,  273,   -1,  257,
   -1,  277,  278,  261,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,  272,  273,   -1,  257,  153,  277,
  278,  261,   -1,  263,  264,   -1,  266,   -1,  268,  164,
  270,   -1,  272,  273,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  178,  179,   -1,  257,   -1,  183,   -1,
  261,   -1,  263,  264,   -1,  266,  191,  268,   -1,  270,
   -1,  272,  273,  257,   -1,   -1,  277,  278,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
   -1,   -1,   -1,  277,  278,  256,  257,  258,   -1,  260,
   -1,   -1,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
   -1,  272,  273,  256,  257,  258,   -1,  260,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,
  273,   -1,  256,  257,  258,   -1,  260,   -1,   -1,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,  272,  273,
  257,   -1,  259,  260,   -1,  262,  263,   -1,   -1,  266,
   -1,  268,   -1,  270,  257,  272,  273,  260,   -1,  262,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,  257,  272,
  273,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,   -1,  272,  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=280;
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
"ASIGNACION","ERROR",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID_simple BEGIN sentencias END",
"programa : BEGIN sentencias END",
"programa : ID_simple BEGIN sentencias",
"programa : ID_simple sentencias END",
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
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo CTE_con_sig ',' CTE_con_sig",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' CTE_con_sig ',' '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION tipo '{' '}'",
"declaracion_subtipo : TYPEDEF ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF ID_simple ASIGNACION '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF '<' tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE tipo '>' ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE tipo ID_simple",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' error",
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
"sentencia_ejecutable : ETIQUETA",
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
"invocacion : ID_simple '(' ')'",
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
"CTE_con_sig : ERROR",
"CTE_con_sig : '-' ERROR",
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
"sentencia_goto : GOTO error",
};

//#line 215 "Gramatica.y"
																	 
private static boolean RETORNO = false;
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
	String keyNeg = "-" + key;
	if (!AnalizadorLexico.TablaDeSimbolos.containsKey(keyNeg)) {
		AnalizadorLexico.TablaDeSimbolos.put(keyNeg, AnalizadorLexico.TablaDeSimbolos.get(key).getCopiaNeg());
	}
	AnalizadorLexico.TablaDeSimbolos.get(keyNeg).incrementarContDeRef();
	System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " se reconocio token negativo ");
	// es ultimo ya decrementa
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
		AnalizadorLexico.TablaDeSimbolos.remove(key);
	}
}
private static boolean estaRango(String key) {
	if (AnalizadorLexico.TablaDeSimbolos.get(key).esEntero()) {
		if (!AnalizadorLexico.TablaDeSimbolos.get(key).enRangoPositivo(key)) {
				if (AnalizadorLexico.TablaDeSimbolos.get(key).esUltimo()) {
					AnalizadorLexico.TablaDeSimbolos.remove(key);
				}
			yyerror("\u001B[31m"+"Linea " +"Linea " + AnalizadorLexico.saltoDeLinea + " Error: " +key +" fuera de rango."+"\u001B[0m");
			return false;
		}
	}
	return true;
}
//#line 627 "Parser.java"
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
{System.out.println("\u001B[32m"+ "\u2714" +"\u001B[0m"+"Se identifico el programa "+"\u001B[32m"+ val_peek(3).sval +"\u001B[0m");}
break;
case 2:
//#line 10 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el nombre del programa "+"\u001B[0m");}
break;
case 3:
//#line 11 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el delimitador END "+"\u001B[0m");}
break;
case 4:
//#line 12 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Falta el delimitador BEGIN "+"\u001B[0m");}
break;
case 5:
//#line 13 "Gramatica.y"
{System.out.println("\u001B[31m"+"\u2718"+"\u001B[0m"+"Linea " + AnalizadorLexico.saltoDeLinea +"\u001B[31m"+ ": Error: Faltan los delimitadores del programa "+"\u001B[0m");}
break;
case 8:
//#line 18 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Falta ';' al final de la sentencia "+"\u001B[0m");}
break;
case 14:
//#line 32 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de variables ");}
break;
case 19:
//#line 43 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Subtipo ");}
break;
case 20:
//#line 44 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '{' en el rango "+"\u001B[0m");}
break;
case 21:
//#line 45 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan el '}' en el rango "+"\u001B[0m");}
break;
case 22:
//#line 46 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '{' '}' en el rango "+"\u001B[0m");}
break;
case 23:
//#line 47 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  " declaracion de Triple ");}
break;
case 24:
//#line 48 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango inferior "+"\u001B[0m");}
break;
case 25:
//#line 49 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta alguno de los rangos "+"\u001B[0m");}
break;
case 26:
//#line 50 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta rango superior "+"\u001B[0m");}
break;
case 27:
//#line 51 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos rangos "+"\u001B[0m");}
break;
case 28:
//#line 52 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de nombre en el tipo definido "+"\u001B[0m");}
break;
case 29:
//#line 53 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta el tipo base en la declaracion de subtipo "+"\u001B[0m");}
break;
case 30:
//#line 54 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta de la palabra reservada TRIPLE "+"\u001B[0m");}
break;
case 31:
//#line 55 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '<' en TRIPLE"+"\u001B[0m");}
break;
case 32:
//#line 56 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta del '>' en TRIPLE"+"\u001B[0m");}
break;
case 33:
//#line 57 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Faltan ambos '<>' en TRIPLE"+"\u001B[0m");}
break;
case 34:
//#line 58 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error: Falta identificador al final de la declaracion"+"\u001B[0m");}
break;
case 35:
//#line 61 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println(" Linea " + AnalizadorLexico.saltoDeLinea + " declaracion de Funcion"); }
break;
case 36:
//#line 62 "Gramatica.y"
{if(RETORNO==false){System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el RETORNO de al funcion "+"\u001B[0m");RETORNO=false;} System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Erro: Faltan el nombre en la funcion "+"\u001B[0m");}
break;
case 38:
//#line 66 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion "+"\u001B[0m");}
break;
case 39:
//#line 67 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +" Error: Falta el parametro en la funcion. "+"\u001B[0m");}
break;
case 47:
//#line 84 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se identifico una etiqueta " );}
break;
case 49:
//#line 86 "Gramatica.y"
{RETORNO = true;}
break;
case 50:
//#line 89 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Se reconocio OUTF de Expresion Aritmetica ");}
break;
case 51:
//#line 90 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Falta el parametro del OUTF  "+"\u001B[0m");}
break;
case 52:
//#line 91 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Se reconocio OUTF de cadena de caracteres ");}
break;
case 53:
//#line 92 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error : Parametro incorrecto en sentencia OUTF. "+"\u001B[0m");}
break;
case 54:
//#line 95 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 55:
//#line 96 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Asignacion a arreglo");}
break;
case 56:
//#line 99 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea + " Invocacion a funcion ");}
break;
case 57:
//#line 100 "Gramatica.y"
{System.out.println("Linea :" + AnalizadorLexico.saltoDeLinea +  " Invocacion con conversion ");}
break;
case 58:
//#line 101 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea +  " Error:  faltan los parametros reales en la invocacion"+"\u001B[0m");}
break;
case 64:
//#line 111 "Gramatica.y"
{System.out.println("\u001B[31m"+"La expresion está mal escrita "+"\u001B[0m");}
break;
case 76:
//#line 136 "Gramatica.y"
{if(estaRango(val_peek(0).sval)) { yyval.sval = val_peek(0).sval; } }
break;
case 77:
//#line 137 "Gramatica.y"
{ cambioCTENegativa(val_peek(0).sval); yyval.sval = "-" + val_peek(0).sval;}
break;
case 80:
//#line 142 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea +  ": Sentencia IF ");}
break;
case 81:
//#line 143 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia IF ");}
break;
case 82:
//#line 144 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF  "+"\u001B[0m");}
break;
case 83:
//#line 145 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + ": Error : Falta el END_IF en IF "+"\u001B[0m");}
break;
case 84:
//#line 146 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  ": Error : Falta de contenido en bloque THEN"+"\u001B[0m");}
break;
case 85:
//#line 147 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta de contenido en bloque THEN."+"\u001B[0m");}
break;
case 86:
//#line 148 "Gramatica.y"
{{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + " Error : falta cuerpo en el ELSE "+"\u001B[0m");};}
break;
case 87:
//#line 151 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion con lista de expresiones ");}
break;
case 88:
//#line 152 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 89:
//#line 153 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 90:
//#line 154 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 91:
//#line 155 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Condicion");}
break;
case 92:
//#line 156 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el '(' en la condicion "+"\u001B[0m");}
break;
case 93:
//#line 157 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Falta el ')' en la condicion "+"\u001B[0m");}
break;
case 94:
//#line 158 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea + "Error : Faltan los parentesis en la condicion "+"\u001B[0m");}
break;
case 112:
//#line 196 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": cadena multilinea ");}
break;
case 113:
//#line 201 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Se identifico un WHILE ");}
break;
case 114:
//#line 202 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea " + AnalizadorLexico.saltoDeLinea +  " Error: falta el cuerpo del WHILE "+"\u001B[0m");}
break;
case 115:
//#line 207 "Gramatica.y"
{System.out.println("Linea " + AnalizadorLexico.saltoDeLinea + ": Sentencia GOTO ");}
break;
case 116:
//#line 208 "Gramatica.y"
{System.out.println("\u001B[31m"+"Linea :" + AnalizadorLexico.saltoDeLinea + " Error: Falta la etiqueta en GOTO "+"\u001B[0m");}
break;
//#line 1020 "Parser.java"
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
