package br.ufscar.dc.cc2.muzean;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.antlr4.MuzeanLexer;
import main.antlr4.MuzeanParser;
import main.antlr4.MuzeanParser.ProgramaContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    /*
        Quantidade de casos de teste de cada tipo
     */
    static final int SINTATICO_COUNT = 3;
    static final int SEMANTICO_COUNT = 6;
    static final int GERADOR_COUNT = 6;

    /*
        force_case = -1 => testa todos os casos de teste do tipo especificado por to_test
        force_case = x => testa caso de teste x
     */
    static int force_case = 1;
    // Opções: SINTATICO, SEMANTICO e GERADOR
    static Teste to_test = Teste.GERADOR;

    public static void main(String[] args) throws IOException {
        int lo = Math.max(force_case, 1);
        int hi = force_case == -1 ? to_test.getValue() : Math.min(force_case, to_test.getValue());

        for (int i = lo; i <= hi; ++i) {
            System.out.println("Compilando caso de teste " + i + " - " + to_test);
            boolean success = generate_intermediate(String.format(input_file_path_mask, to_test.toString().toLowerCase(), i),
                    String.format(output_file_path_mask, to_test.toString().toLowerCase(), i));

            if (success) {
                System.out.println("Gerando midi");
                compile(String.format(output_file_path_mask, to_test.toString().toLowerCase(), i));
                System.out.println("Executando midi");
                execute(String.format(output_file_path_mask, to_test.toString().toLowerCase(), i));
            }

            // Bad design: estados não encapsulados geram a necessidade de um reset
            Saida.clear();
            TabelaDeSimbolos.clear();
            Escala.clear();
        }
    }

    static void compile(String file) throws IOException {
        Process pr = null;
        ProcessBuilder pb = new ProcessBuilder("python", new File(file).getName());
        pb.directory(new File(file).getParentFile());
        pr = pb.start();

        try {
            pr.waitFor();
            //System.out.println(IOUtil.convertStreamToString(pr.getErrorStream()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void execute(String file) throws IOException {
        Process pr = null;
        ProcessBuilder pb = new ProcessBuilder("timidity", "output.mid");
        pb.directory(new File(file).getParentFile());
        pr = pb.start();

        try {
            pr.waitFor();
            //System.out.println(IOUtil.convertStreamToString(pr.getInputStream()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        if (Saida.is_modified()) {
            System.err.println(Saida.getTexto());
            return false;
        }

        // sem erro léxico/sintático
        AnalisadorSemantico as = new AnalisadorSemantico();
        as.visitPrograma(programa_context);

        if (Saida.is_modified()) {
            System.err.println(Saida.getTexto());
            return false;
        }

        // sem erro semântico
        Gerador g = new Gerador();
        //GeradorArduino g = new GeradorArduino();

        String out = (String) g.visitPrograma(programa_context);
        Saida.print(out);

        //System.out.println(Saida.getTexto());
        PrintWriter outputTestCase = new PrintWriter(output_file_path, "UTF-8");
        outputTestCase.print(Saida.getTexto());
        outputTestCase.close();

        return true;
    }

    static enum Teste {
        SINTATICO(SINTATICO_COUNT), SEMANTICO(SEMANTICO_COUNT), GERADOR(GERADOR_COUNT);

        private final int count;

        Teste(int count) {
            this.count = count;
        }

        public int getValue() {
            return count;
        }
    }

    static String input_file_path_mask = "casos_de_teste/%s/entrada/%d.mzn";
    static String output_file_path_mask = "casos_de_teste/%s/saida_gerada/%d.py";

}
