%{

}%

%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA TYPEDEF WHILE TRIPLE GOTO MAYORIGUAL MENORIGUAL DISTINTO

%%
	
%%

	public static int yylex(Reader lector) {
		return AnalizadorLexico.getToken(lector);
	}
		

