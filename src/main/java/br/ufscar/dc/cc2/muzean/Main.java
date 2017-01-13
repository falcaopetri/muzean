package br.ufscar.dc.cc2.muzean;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import main.antlr4.MuzeanLexer;
import main.antlr4.MuzeanParser;
import main.antlr4.MuzeanParser.ProgramaContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) throws IOException {
        int test_case = 2;
       
        String input_file_path = "casos_de_teste/sintatico/entrada/" + test_case + ".mzn";
        String output_file_path = "casos_de_teste/sintatico/saida_gerada/" + test_case + ".mzn";
                
        FileReader input_test_case = new FileReader(input_file_path);
        ANTLRInputStream input = new ANTLRInputStream(input_test_case);
        input_test_case.close();

        MuzeanLexer lexer = new MuzeanLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MuzeanParser parser = new MuzeanParser(tokens);

        MeuErrorListener mel = new MeuErrorListener();

        //lexer.removeErrorListeners();
        //parser.removeErrorListeners();

        parser.addErrorListener(mel);

        ProgramaContext programa_context = parser.programa();

        System.out.println(Saida.getTexto());

        PrintWriter outputTestCase = new PrintWriter(output_file_path, "UTF-8");
        outputTestCase.print(Saida.getTexto());
        outputTestCase.close();

    }
}
