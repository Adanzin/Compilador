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






//#line 2 "Gramatica.y"
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
//#line 24 "Parser.java"




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
    0,    0,    0,    0,    0,    1,    1,    1,    2,    2,
    3,    3,    3,    5,    8,    8,    8,    9,    9,    7,
    7,    6,    6,    6,   11,   11,   13,   13,   13,   12,
   12,   12,    4,    4,    4,    4,    4,    4,   15,   15,
   14,   14,   20,   20,   20,   20,   21,   21,   21,   21,
   10,   10,   22,   22,   23,   23,   23,   23,   16,   16,
   24,   24,   27,   27,   27,   27,   27,   27,   26,   26,
   25,   25,   19,   19,   17,   28,   29,   29,   29,   29,
   18,
};
final static short yylen[] = {                            2,
    5,    4,    3,    4,    4,    3,    2,    2,    1,    1,
    1,    1,    1,    2,    1,    1,    1,    3,    1,    9,
    6,    7,    7,    6,    3,    1,    2,    1,    1,    5,
    5,    1,    1,    1,    1,    1,    4,    4,    3,    6,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    1,    2,    4,    5,    3,    1,    1,    1,    9,    5,
    3,    1,    1,    1,    1,    1,    1,    1,    4,    3,
    2,    1,    3,    2,    5,    3,    3,    1,    1,    1,
    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   15,   16,    0,    0,    9,   10,   11,   12,   13,    0,
   33,   34,   35,   36,   51,    0,    0,    0,    0,   48,
    0,    0,   49,    0,   62,    0,    3,    0,    0,    0,
    0,    0,    0,    0,    0,   81,    0,    0,    8,    0,
   19,    0,    0,    0,    0,   52,    0,   43,   44,   45,
   46,    0,   64,   66,   68,   65,   63,   67,    0,    0,
    0,    0,    2,    0,    0,    0,   48,    0,    0,   42,
    0,    0,    0,    0,    0,    0,    4,    6,    0,    0,
    0,    0,    0,   48,    0,    0,    0,   18,    0,   61,
    0,    0,    0,    0,    1,    0,   74,   37,   38,   17,
    0,    0,   17,    0,    0,   26,    0,    0,    0,    0,
   50,    0,    0,   53,    0,    0,    0,   72,    0,   60,
   73,    0,    0,   27,    0,    0,    0,    0,    0,    0,
    0,   54,    0,    0,   70,    0,   71,    0,   21,    0,
   25,    0,    0,   24,    0,    0,   69,    0,   23,    0,
   22,    0,    0,    0,    0,   59,   20,   30,   31,
};
final static short yydgoto[] = {                          2,
  139,   14,   15,   16,   17,   18,   19,   20,   29,   77,
  115,  140,  116,   31,   21,   22,   23,   24,   79,   62,
   80,   33,   96,   34,  129,  102,   69,   35,   36,
};
final static short yysindex[] = {                      -244,
  175,    0,   -1,  187,   -4, -122, -228, -114,   -2, -226,
    0,    0,  121,    3,    0,    0,    0,    0,    0, -101,
    0,    0,    0,    0,    0,  -27,   41, -209,   19,    0,
  110,  135,    0, -189,    0,   23,    0,  109,   36, -198,
   29, -144,   43, -123,   41,    0, -116,   91,    0, -110,
    0,   19,  -37,   28,  122,    0, -106,    0,    0,    0,
    0,   43,    0,    0,    0,    0,    0,    0,   43,  -85,
   41,   41,    0,  -92,  -27,  -88,    0,  463,  142,    0,
 -170, -170, -135,  110,   81,  173,    0,    0, -165,   94,
  -27, -135,   19,    0,  110,  132,  -76,    0,  110,    0,
  -69,  -30,  184,  184,    0,  148,    0,    0,    0,    0,
  127,  190,    0,  -22, -163,    0,  -10, -131,  215, -143,
    0,   -7,   41,    0, -131,  201, -114,    0,  -59,    0,
    0,  -37,    7,    0,  215,  228,   43, -131,  203,   13,
  215,    0,  233,  227,    0,  222,    0,  243,    0,   27,
    0,  110,  260,    0,   40, -131,    0,  -37,    0,   45,
    0,  239,  177,  -29,  471,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  -91,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   10,    0,    0,   15,    0,
    4,  370,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  159,    0,    0,
    0,   97,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  307,  -41,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,    0,    0,    0,    0,    0,
  -14,    0,  274,    0,  149,    0,    0,    0,  -19,    0,
    0,    0,   18,  -34,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  141,   56,    0,
    0,    0,  281,    0,    0,    0,    0,    0,    0,    0,
    0,   79,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  144,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  206,   -3,    0,   22,    0,    0,    0,   39,    6,   47,
  123, -118,  -11,   48,    0,    0,    0,    0,    0,    0,
  103,    0,  205,  -13,  -57,    0,  290,    0,  -56,
};
final static int YYTABLESIZE=518;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   47,   47,   47,   47,  107,   47,   76,   28,   44,   48,
   54,  168,   54,   55,  103,  104,  150,   47,   47,   47,
   47,   41,  155,    1,   41,   52,   19,   47,   47,   19,
   47,   86,   47,  142,   48,   39,   42,   45,   27,   41,
   41,   41,   41,   28,   78,   28,   46,   78,   28,   30,
   19,   47,   47,   19,   47,   80,   47,   56,   77,   93,
  138,   49,   57,   78,   78,   78,   71,  144,   70,   19,
   19,   19,   28,   30,   80,   80,   80,   77,   77,   77,
   28,   81,   66,   68,   67,   28,   78,   28,   82,   28,
   84,   30,   92,   53,  119,   53,  135,  110,  162,   90,
   94,   95,  113,  136,  113,   32,   11,   12,  136,   99,
  136,   11,   12,   11,   12,   39,  141,   30,   30,  111,
  112,  114,  128,   83,  113,    3,   76,  114,   93,   32,
  114,    5,  113,   11,   12,   48,  127,   40,    9,  128,
   10,   11,   12,   85,   87,   40,  128,   32,   41,   88,
  147,   60,   58,  114,   59,   14,   61,   89,  114,  147,
  114,   98,   97,   50,   43,  147,   51,   74,  105,   94,
   95,  100,  124,   17,  101,  123,   17,  128,  148,   47,
  106,  125,  109,  147,  152,   47,   47,    3,   47,   56,
   47,  126,   56,    5,   66,   68,   67,    3,  127,   75,
    9,  146,   10,    5,  163,  117,   13,  165,  127,   38,
    9,  120,   10,  118,  122,   47,   47,   47,  121,   47,
   47,   47,   47,   76,   47,   47,   47,   71,   47,   25,
   47,  130,   47,   47,   47,   47,   47,   41,   41,   41,
  131,   41,   41,   41,   41,  134,   41,   41,   41,  132,
   41,  133,   41,   17,   41,   41,   41,   41,   41,  145,
  113,   78,   17,   17,   28,   25,   26,   19,  137,   11,
   12,  151,   80,  154,  149,   77,  123,   78,   78,   78,
  157,   28,   28,   19,   19,   19,  158,  159,   80,   80,
   80,   77,   77,   77,   25,   91,   63,   64,   65,  160,
  161,  167,   25,   75,   11,   12,    5,   25,   26,   25,
   75,   25,  164,   39,   58,   39,   32,   39,   39,   39,
   39,   55,   39,   39,   39,   72,   39,  143,   39,    0,
    0,    0,    0,   39,   39,   40,    0,   40,    0,   40,
   40,   40,   40,    0,   40,   40,   40,    0,   40,    0,
   40,    0,    0,   14,    0,   40,   40,   14,    0,   14,
   14,    0,   14,   14,   14,    3,   14,    0,   14,   73,
    0,    5,    6,   14,   14,    7,    8,    3,    9,    0,
   10,    0,    0,    5,    6,   11,   12,    7,    8,    0,
    9,    0,   10,    0,    0,    0,    0,   11,   12,   75,
    0,   75,   75,    0,   75,    0,   75,   75,   63,   64,
   65,   42,   42,   42,   42,    7,   42,   75,   75,    7,
    0,    7,    7,    0,    7,    7,    7,    0,    7,    0,
    7,    3,    0,    0,    4,    7,    7,    5,    6,    0,
    0,    7,    8,    3,    9,    0,   10,   37,    0,    5,
    6,   11,   12,    7,    8,    0,    9,    0,   10,    3,
    0,    0,    0,   11,   12,    5,    6,    0,  153,    7,
    8,    3,    9,    0,   10,    0,    0,    5,    6,   11,
   12,    7,    8,    3,    9,  156,   10,    0,    0,    5,
    0,   11,   12,    0,  127,    3,    9,    0,   10,    0,
  166,    5,    0,  108,   60,   58,  127,   59,    9,   61,
   10,  169,   60,   58,    0,   59,    0,   61,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   93,   47,   41,   45,  123,   13,
   40,   41,   40,   27,   71,   72,  135,   59,   60,   61,
   62,   41,  141,  268,   44,   20,   41,   42,   43,   44,
   45,   45,   47,   41,   38,   40,  265,   40,   40,   59,
   60,   61,   62,   45,   41,   41,  273,   44,   44,    3,
   41,   42,   43,   44,   45,   41,   47,  267,   41,   54,
  118,   59,   44,   60,   61,   62,   44,  125,  258,   60,
   61,   62,   45,   27,   60,   61,   62,   60,   61,   62,
   45,  280,   60,   61,   62,   45,   39,   45,   60,   45,
   43,   45,   54,  123,  260,  123,  260,  268,  156,   53,
   54,   54,  268,  115,  268,    3,  277,  278,  120,   62,
  122,  277,  278,  277,  278,   59,  260,   71,   72,   81,
   82,   83,  101,  268,  268,  257,   91,   89,  123,   27,
   92,  263,  268,  277,  278,  139,  268,   59,  270,  118,
  272,  277,  278,  267,  261,  268,  125,   45,  271,   59,
  129,   42,   43,  115,   45,   59,   47,  268,  120,  138,
  122,  268,   41,  265,  279,  144,  268,   59,  261,  123,
  123,   69,   41,  265,  260,   44,  268,  156,  132,   59,
  269,  258,   41,  162,  137,   42,   43,  257,   45,   41,
   47,  261,   44,  263,   60,   61,   62,  257,  268,   59,
  270,  261,  272,  263,  158,  125,    1,  160,  268,    4,
  270,   89,  272,   41,   92,  257,  258,  259,  125,  261,
  262,  263,  264,  258,  266,  267,  268,   44,  270,  267,
  272,  262,  274,  275,  276,  277,  278,  257,  258,  259,
   93,  261,  262,  263,  264,  268,  266,  267,  268,  123,
  270,   62,  272,  268,  274,  275,  276,  277,  278,   59,
  268,  258,  277,  278,  260,  267,  268,  258,  279,  277,
  278,   44,  258,  261,  268,  258,   44,  274,  275,  276,
   59,  277,  278,  274,  275,  276,   44,  261,  274,  275,
  276,  274,  275,  276,  267,  268,  274,  275,  276,   40,
  261,  125,  267,  268,  277,  278,    0,  267,  268,  267,
  268,  267,  268,  257,   41,  259,  261,  261,  262,  263,
  264,   41,  266,  267,  268,   36,  270,  123,  272,   -1,
   -1,   -1,   -1,  277,  278,  257,   -1,  259,   -1,  261,
  262,  263,  264,   -1,  266,  267,  268,   -1,  270,   -1,
  272,   -1,   -1,  257,   -1,  277,  278,  261,   -1,  263,
  264,   -1,  266,  267,  268,  257,  270,   -1,  272,  261,
   -1,  263,  264,  277,  278,  267,  268,  257,  270,   -1,
  272,   -1,   -1,  263,  264,  277,  278,  267,  268,   -1,
  270,   -1,  272,   -1,   -1,   -1,   -1,  277,  278,  259,
   -1,  261,  262,   -1,  264,   -1,  266,  267,  274,  275,
  276,   42,   43,   44,   45,  257,   47,  277,  278,  261,
   -1,  263,  264,   -1,  266,  267,  268,   -1,  270,   -1,
  272,  257,   -1,   -1,  260,  277,  278,  263,  264,   -1,
   -1,  267,  268,  257,  270,   -1,  272,  261,   -1,  263,
  264,  277,  278,  267,  268,   -1,  270,   -1,  272,  257,
   -1,   -1,   -1,  277,  278,  263,  264,   -1,  266,  267,
  268,  257,  270,   -1,  272,   -1,   -1,  263,  264,  277,
  278,  267,  268,  257,  270,  259,  272,   -1,   -1,  263,
   -1,  277,  278,   -1,  268,  257,  270,   -1,  272,   -1,
  262,  263,   -1,   41,   42,   43,  268,   45,  270,   47,
  272,   41,   42,   43,   -1,   45,   -1,   47,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=280;
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
"DOUBLE","ASIGNACION","\":=\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN sentencias ';' END",
"programa : ID BEGIN sentencias END",
"programa : ID BEGIN END",
"programa : ID sentencias ';' END",
"programa : ID BEGIN sentencias ';'",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencias sentencia",
"sentencias : sentencia ';'",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables",
"tipo : INTEGER",
"tipo : DOUBLE",
"tipo : ID",
"variables : variables ',' ID",
"variables : ID",
"declaracion_subtipo : TYPEDEF ID \":=\" tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID",
"declaracion_funciones : tipo FUN ID parametros_formal BEGIN cuerpo_funcion END",
"declaracion_funciones : CTE FUN ID parametros_formal BEGIN cuerpo_funcion END",
"declaracion_funciones : tipo FUN ID BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID",
"parametro : tipo",
"parametro : ID",
"cuerpo_funcion : sentencias RET '(' ID ')'",
"cuerpo_funcion : sentencias RET '(' expresion_arit ')'",
"cuerpo_funcion : sentencias",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_IF",
"sentencia_ejecutable : sentencia_WHILE",
"sentencia_ejecutable : sentencia_goto",
"sentencia_ejecutable : OUTF '(' expresion_arit ')'",
"sentencia_ejecutable : OUTF '(' cadena ')'",
"asignacion : ID ASIGNACION expresion_arit",
"asignacion : ID '{' CTE '}' ASIGNACION expresion_arit",
"expresion_arit : expresion_arit operacion expresion_arit",
"expresion_arit : operando",
"operacion : '+'",
"operacion : '-'",
"operacion : '*'",
"operacion : '/'",
"operando : ID",
"operando : CTE_con_sig",
"operando : invocacion",
"operando : ID '{' CTE_con_sig '}'",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
"invocacion : ID '(' parametro_real ')'",
"invocacion : ID '(' tipo parametros_formal ')'",
"parametro_real : parametro_real ',' parametro_real",
"parametro_real : expresion_arit",
"parametro_real : CTE_con_sig",
"parametro_real : variables",
"sentencia_IF : IF '(' condicion ')' THEN bloques_sent_ejecutables ELSE bloques_sent_ejecutables END_IF",
"sentencia_IF : IF condicion THEN bloque_sentencias END_IF",
"condicion : operando comparador operando",
"condicion : pattern_matching",
"comparador : '>'",
"comparador : MAYORIGUAL",
"comparador : '<'",
"comparador : MENORIGUAL",
"comparador : '='",
"comparador : DISTINTO",
"bloque_sentencias : BEGIN bloques_sent_ejecutables END ';'",
"bloque_sentencias : BEGIN END ';'",
"bloques_sent_ejecutables : bloques_sent_ejecutables sentencia_ejecutable",
"bloques_sent_ejecutables : sentencia_ejecutable",
"cadena : '[' CADENAMULTILINEA ']'",
"cadena : '[' ']'",
"sentencia_WHILE : WHILE '(' condicion ')' bloques_sent_ejecutables",
"pattern_matching : list_expre comparador list_expre",
"list_expre : list_expre ',' list_expre",
"list_expre : expresion_arit",
"list_expre : CTE_con_sig",
"list_expre : variables",
"sentencia_goto : GOTO ETIQUETA",
};

//#line 169 "Gramatica.y"

int yylex() {
	yylval = new ParserVal(AnalizadorLexico.Lexema);
	return AnalizadorLexico.getToken();
}

void yyerror(String s)
{
 System.out.println("par:"+s);
}

void dotest()
{
 System.out.println("BYACC/J Calculator Demo");
 System.out.println("Note: Since this example uses the StringTokenizer");
 System.out.println("for simplicity, you will need to separate the items");
 System.out.println("with spaces, i.e.: '( 3 + 5 ) * 2'");
 /*while (true)
 {
 System.out.print("expression:");
 try
 {
 ins = in.readLine();
 }
 catch (Exception e)
 {
 }
 st = new StringTokenizer(ins);
 newline=false;*/
 yyparse();
 //}
}

public static void main(String args[])
{
 Parser par = new Parser(false);
 par.dotest();
}
//#line 471 "Parser.java"
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
//#line 15 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " PROGRAMA ");}
break;
case 2:
//#line 16 "Gramatica.y"
{System.out.println(" Falta el ; al final de la sentencia");}
break;
case 3:
//#line 17 "Gramatica.y"
{System.out.println(" Se esperaba una sentencia");}
break;
case 4:
//#line 18 "Gramatica.y"
{System.out.println(" Se esperaba el BEGIN al iniciar el programa");}
break;
case 5:
//#line 19 "Gramatica.y"
{System.out.println(" Se esperaba un END al final del programa");}
break;
case 7:
//#line 23 "Gramatica.y"
{System.out.println(" Se esperaba ';' al final de la sentencia");}
break;
case 24:
//#line 56 "Gramatica.y"
{System.out.println(" La declaracion de una funcion debe poseer parametros");}
break;
case 28:
//#line 64 "Gramatica.y"
{System.out.println("Se espera un identificador despues del tipo.");}
break;
case 29:
//#line 65 "Gramatica.y"
{System.out.println("Se esperaba el tipo del identificador.");}
break;
case 37:
//#line 79 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida expresion arit ");}
break;
case 38:
//#line 80 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Salida cadena ");}
break;
case 39:
//#line 84 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 40:
//#line 85 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
case 41:
//#line 88 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Expresion Aritmetica");}
break;
case 42:
//#line 89 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Expresion Aritmetica");}
break;
case 51:
//#line 104 "Gramatica.y"
{if(val_peek(0).ival > 32767){AnalizadorLexico.TablaDeSimbolos.remove(val_peek(0).sval); System.out.println(" CTE fuera de rango");}}
break;
case 52:
//#line 105 "Gramatica.y"
{ Simbolo simbaux = AnalizadorLexico.TablaDeSimbolos.get(val_peek(0).sval);
						AnalizadorLexico.TablaDeSimbolos.remove(val_peek(0).sval);
						String negativo = "-" + val_peek(0).sval;
						AnalizadorLexico.TablaDeSimbolos.put(negativo,simbaux);}
break;
case 53:
//#line 111 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion");}
break;
case 54:
//#line 112 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Invocacion");}
break;
case 59:
//#line 121 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia IF");}
break;
case 60:
//#line 122 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia IF");}
break;
case 69:
//#line 137 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Bloque de sentencias");}
break;
case 70:
//#line 138 "Gramatica.y"
{ System.out.println("Se esperaban sentencias entre BEGIN y END"); }
break;
case 75:
//#line 151 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia WHILE");}
break;
case 76:
//#line 155 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Pattern Matching");}
break;
case 81:
//#line 165 "Gramatica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Sentencia GOTO");}
break;
//#line 727 "Parser.java"
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
