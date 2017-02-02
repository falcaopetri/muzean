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
        String input_file_path_mask = "casos_de_teste/sintatico/entrada/%1$d.mzn";
        String output_file_path_mask = "casos_de_teste/sintatico/saida_gerada/%1$d.py";

        int test_case = 10;

        boolean success = generate_intermediate(String.format(input_file_path_mask, test_case), String.format(output_file_path_mask, test_case));
        compile(String.format(output_file_path_mask, test_case));
        //execute(String.format(output_file_path_mask, test_case));
    }

    static void compile(String file) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(new String[]{"zsh", "-c", "cat /home/narek/pk.txt"});
    }

    static boolean generate_intermediate(String input_file_path, String output_file_path) throws IOException {
        FileReader input_test_case = new FileReader(input_file_path);
        ANTLRInputStream input = new ANTLRInputStream(input_test_case);
        input_test_case.close();

        MuzeanLexer lexer = new MuzeanLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MuzeanParser parser = new MuzeanParser(tokens);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();

        MeuErrorListener mel = new MeuErrorListener();
        parser.addErrorListener(mel);

        ProgramaContext programa_context = parser.programa();

        boolean success = true;
        if (!Saida.is_modified()) {
            // sem erro léxico/sintático
            AnalisadorSemantico as = new AnalisadorSemantico();
            as.visitPrograma(programa_context);
        }

        if (!Saida.is_modified()) {
            // sem erro semântico
            Gerador g = new Gerador();
            g.activateArduino();
            String out = (String) g.visitPrograma(programa_context);
            Saida.print(out);
        } else {
            success = false;
        }

        System.out.println(Saida.getTexto());

        PrintWriter outputTestCase = new PrintWriter(output_file_path, "UTF-8");
        outputTestCase.print(Saida.getTexto());
        outputTestCase.close();

        return success;
    }
}
