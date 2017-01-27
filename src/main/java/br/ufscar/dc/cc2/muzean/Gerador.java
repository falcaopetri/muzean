package br.ufscar.dc.cc2.muzean;

import java.util.Scanner;
import java.util.stream.Collectors;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;

public class Gerador extends MuzeanBaseVisitor<Object> {

    private static String getResourceAsString(String resource_path) {
        // Source: http://stackoverflow.com/a/18897411
        return new Scanner(Gerador.class.getResourceAsStream(resource_path), "UTF-8").useDelimiter("\\A").next();
    }

    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        String header = getResourceAsString("/header.txt");
        String footer = getResourceAsString("/footer.txt");

        String compassos = "compasses = [\n";
        compassos += TabelaDeSimbolos.getEstruturas("__global__").stream().map(i -> i.generateCode()).collect(Collectors.joining(",\n"));
        compassos += "\n]\n";

        return header + compassos + footer;
    }
}
