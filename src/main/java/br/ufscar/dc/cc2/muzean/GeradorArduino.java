package br.ufscar.dc.cc2.muzean;

import java.util.Scanner;
import java.util.stream.Collectors;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;

public class GeradorArduino extends MuzeanBaseVisitor<Object> {

    private static String getResourceAsString(String resource_path) {
        // Source: http://stackoverflow.com/a/18897411
        return new Scanner(Gerador.class.getResourceAsStream(resource_path), "UTF-8").useDelimiter("\\A").next();
    }

    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        String header = getResourceAsString("/header_arduino.txt");
        String footer = getResourceAsString("/footer_arduino.txt");

        String body = TabelaDeSimbolos.getEstruturas("__global__").stream().map(i -> i.generateArduinoCode()).collect(Collectors.joining(",\n"));
        String compassos = "\nint compassos[] = { "
                + body
                + " };";

        compassos += "\n";

        footer = String.format(footer, body.split(",").length / 2);

        return header + compassos + footer;
    }
}
