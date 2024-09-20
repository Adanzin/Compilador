package Compilador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedWriter;

public class Parser {
	
	public static ParserVal yylval;
	public static final short IF = 257;
	public final static short THEN = 258;
	public final static short ELSE = 259;
	public final static short BEGIN = 260;
	public final static short END = 261;
	public final static short END_IF = 262;
	public final static short OUTF = 263;
	public final static short TYPEDEF = 264;
	public final static short FUN = 265;
	public final static short RET = 266;
	public final static short CTE = 267;
	public final static short ID = 268;
	public final static short CADENAMULTILINEA = 269;
	public final static short WHILE = 270;
	public final static short TRIPLE = 271;
	public final static short GOTO = 272;
	public final static short ASIGNACION = 273; 
	public final static short MAYORIGUAL = 274;
	public final static short MENORIGUAL = 275; 
	public final static short DISTINTO = 276;
	
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
	
		public static void yyparser() {
			try {
				Reader lector = new FileReader("Codigo.txt");
				@SuppressWarnings("resource")
				BufferedWriter salida = new BufferedWriter(new FileWriter("Salida.txt"));
				int token=yylex(lector);
				while(token!= 0){
					if(yylval!=null) {
						System.out.println("Token de la linea"+AnalizadorLexico.saltoDeLinea+" ["+token+"] ["+yylval+"]");
						salida.write(" ["+token+"] ["+yylval+"]");
					}else {
						System.out.println("Token de la linea"+AnalizadorLexico.saltoDeLinea+" ["+token+"]");
						salida.write(" ["+token+"]");
					}
					token=yylex(lector);			
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
