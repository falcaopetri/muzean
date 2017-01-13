package br.ufscar.dc.cc2.muzean;

import java.io.FileReader;
import java.io.IOException;
import main.antlr4.MuzeanLexer;
import main.antlr4.MuzeanParser;
import main.antlr4.MuzeanParser.ProgramaContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) throws IOException {
        String input_file_path = "casos_de_teste/sintatico/entrada/1.mzn";
        String output_file_path = "casos_de_teste/sintatico/saida_gerada/1.mzn";

        FileReader input_test_case = new FileReader(input_file_path);

        ANTLRInputStream input = new ANTLRInputStream(input_test_case);
        MuzeanLexer lexer = new MuzeanLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MuzeanParser parser = new MuzeanParser(tokens);

        ProgramaContext programa_context = parser.programa();

        input_test_case.close();
    }
}
