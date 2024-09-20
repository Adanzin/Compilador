%{

}%

%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CTE ID CADENAMULTILINEA TYPEDEF WHILE TRIPLE GOTO MAYORIGUAL MENORIGUAL DISTINTO

%%
	
%%
main(){
	
	public static int yylex(Reader reader) throws IOException {
		int caracter;
		int token;
		yylval=null;
		// Recorrer cada carácter del archivo
		while ((caracter = reader.read()) != -1) {
			// Convertimos el entero leído a char
			char letra = (char) caracter;
			reader.mark(1);
			token = AnalizadorLexico.siguienteLectura(reader, letra);
			// Llamar al método que procesa el carácter
			if (token == -2) {
				System.out.println("ERROR EN LA LINEA " + AnalizadorLexico.saltoDeLinea);
			} else if (token != -1) { // Si no es un token activo, se carga en el archivo.
				return token;
			}

		}
		return 0; //fin de archivo.
	}
	public static void main(String args[])
	{
	 Parser par = new Parser(false);
	 par.yyParser();
	}	

}