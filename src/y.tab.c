#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "pruebasemantica.y"
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;
#line 13 "y.tab.c"
#define IF 257
#define THEN 258
#define ELSE 259
#define BEGIN 260
#define END 261
#define END_IF 262
#define OUTF 263
#define TYPEDEF 264
#define FUN 265
#define RET 266
#define CTE 267
#define ID 268
#define CADENAMULTILINEA 269
#define WHILE 270
#define TRIPLE 271
#define GOTO 272
#define ETIQUETA 273
#define MAYORIGUAL 274
#define MENORIGUAL 275
#define DISTINTO 276
#define INTEGER 277
#define DOUBLE 278
#define ASIGNACION 279
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    1,    2,    2,    3,    3,    3,    5,    8,
    8,    9,    9,    7,    7,    6,    6,   11,   11,   13,
   12,   12,    4,   15,   15,   14,   14,   14,   16,   16,
   16,   17,   17,   17,   10,   10,
};
short yylen[] = {                                         2,
    4,    3,    2,    1,    1,    1,    1,    1,    2,    1,
    1,    3,    1,    9,    6,    7,    7,    3,    1,    2,
    5,    1,    1,    3,    6,    3,    3,    1,    3,    3,
    1,    1,    1,    4,    1,    2,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,   10,   11,    0,    0,    4,
    5,    6,    7,    8,    0,   23,    0,    0,    0,    0,
    0,    1,    0,    3,    0,   13,    0,    0,    0,    0,
   35,    0,    0,   33,    0,    0,   31,    0,    2,    0,
    0,    0,    0,    0,    0,   19,    0,   36,    0,    0,
    0,    0,    0,    0,   12,    0,    0,   20,    0,    0,
    0,    0,    0,   29,   30,    0,    0,    0,   15,    0,
    0,   18,   34,    0,    0,    0,    0,   17,   16,    0,
    0,   14,    0,   21,
};
short yydgoto[] = {                                       2,
   70,    9,   10,   11,   12,   13,   14,   15,   27,   34,
   45,   71,   46,   35,   16,   36,   37,
};
short yysindex[] = {                                   -264,
 -248,    0, -216, -200, -122,    0,    0, -228,  -14,    0,
    0,    0,    0,    0, -196,    0, -209,   15, -190,  -45,
 -188,    0,   21,    0, -187,    0,   38, -214, -214, -214,
    0,  -40, -183,    0,   13,   12,    0,  -39,    0, -214,
 -181,  -38,   26, -179, -235,    0,  -43,    0,  -45,  -45,
  -45,  -45, -189, -231,    0,  -43, -177,    0, -216,   48,
  -31,   12,   12,    0,    0,  -45, -216,   49,    0, -211,
 -166,    0,    0,   13, -165,  -43,   57,    0,    0,  -27,
  -45,    0,   -4,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,    0,    0,    0,
    0,  -32,    0,    0,  -14,  -24,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -21,  -15,    0,    0,    0,    0,    0,    0, -161,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
   99,   -5,    0,    0,    0,    0,    0,  -22,    0,  -42,
   63,   37,    6,  -50,    0,   24,   25,
};
#define YYTABLESIZE 224
short yytable[] = {                                      33,
   21,   33,   23,    1,   61,   42,   43,   44,   32,   32,
   32,    3,   32,   68,   32,   74,   28,   44,   28,   26,
   28,   26,   44,   26,   59,   27,   32,   27,   67,   27,
   83,   44,   22,   80,   28,    4,   84,   26,   49,    5,
   50,    6,    7,   27,   24,    6,    7,    4,    6,    7,
   60,    5,    4,   51,   77,   49,    5,   50,   52,   60,
    6,    7,    6,    7,   23,    6,    7,   17,   25,   28,
   18,   26,   62,   63,   29,   64,   65,   30,   38,   39,
   40,   41,   47,   48,   56,   53,   55,   57,   58,   66,
   69,   72,   76,   73,   78,   79,   81,   82,    9,   22,
   25,    8,   54,   75,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   31,   32,   31,
};
short yycheck[] = {                                      45,
  123,   45,    8,  268,   47,   28,   29,   30,   41,   42,
   43,  260,   45,   56,   47,   66,   41,   40,   43,   41,
   45,   43,   45,   45,  260,   41,   59,   43,  260,   45,
   81,   54,  261,   76,   59,  264,   41,   59,   43,  268,
   45,  277,  278,   59,   59,  277,  278,  264,  277,  278,
   45,  268,  264,   42,  266,   43,  268,   45,   47,   54,
  277,  278,  277,  278,   70,  277,  278,  268,  265,  279,
  271,  268,   49,   50,   60,   51,   52,  268,  267,   59,
  268,   44,  123,  267,  123,  125,  268,   62,  268,  279,
  268,   44,   44,  125,  261,  261,   40,  125,   59,  261,
   59,    3,   40,   67,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  279,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  267,  268,  267,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 279
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'",0,"'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF","TYPEDEF",
"FUN","RET","CTE","ID","CADENAMULTILINEA","WHILE","TRIPLE","GOTO","ETIQUETA",
"MAYORIGUAL","MENORIGUAL","DISTINTO","INTEGER","DOUBLE","ASIGNACION",
};
char *yyrule[] = {
"$accept : programa",
"programa : ID BEGIN sentencias END",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencia ';'",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funciones",
"sentencia_declarativa : declaracion_subtipo",
"declaracion_variable : tipo variables",
"tipo : INTEGER",
"tipo : DOUBLE",
"variables : variables ',' ID",
"variables : ID",
"declaracion_subtipo : TYPEDEF ID ASIGNACION tipo '{' CTE_con_sig ',' CTE_con_sig '}'",
"declaracion_subtipo : TYPEDEF TRIPLE '<' tipo '>' ID",
"declaracion_funciones : tipo FUN ID parametros_formal BEGIN cuerpo_funcion END",
"declaracion_funciones : ID FUN ID parametros_formal BEGIN cuerpo_funcion END",
"parametros_formal : parametros_formal parametro ','",
"parametros_formal : parametro",
"parametro : tipo ID",
"cuerpo_funcion : sentencias RET '(' expresion_arit ')'",
"cuerpo_funcion : sentencias",
"sentencia_ejecutable : asignacion",
"asignacion : ID ASIGNACION expresion_arit",
"asignacion : ID '{' CTE '}' ASIGNACION expresion_arit",
"expresion_arit : expresion_arit '+' termino",
"expresion_arit : expresion_arit '-' termino",
"expresion_arit : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE_con_sig",
"factor : ID '{' CTE_con_sig '}'",
"CTE_con_sig : CTE",
"CTE_con_sig : '-' CTE",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 1:
#line 15 "pruebasemantica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " PROGRAMA ");}
break;
case 24:
#line 74 "pruebasemantica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion ");}
break;
case 25:
#line 75 "pruebasemantica.y"
{System.out.println(AnalizadorLexico.saltoDeLinea + " Asignacion a arreglo");}
break;
#line 379 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
