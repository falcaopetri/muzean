package br.ufscar.dc.cc2.muzean;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;
import org.antlr.v4.runtime.misc.Pair;

public class Gerador extends MuzeanBaseVisitor<Object> {

    private boolean arduino = false;

    public void activateArduino() {
        arduino = true;
    }

    private static String getResourceAsString(String resource_path) {
        // Source: http://stackoverflow.com/a/18897411
        return new Scanner(Gerador.class.getResourceAsStream(resource_path), "UTF-8").useDelimiter("\\A").next();
    }

    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        String header, footer;
        String compassos = "compasses = [\n";

        if (!arduino) {
            header = getResourceAsString("/header.txt");
            footer = getResourceAsString("/footer.txt");

            compassos += TabelaDeSimbolos.getEstruturas("__global__").stream().map(i -> i.generateCode()).collect(Collectors.joining(",\n"));
            compassos += "\n]\n";
        } else {
            header = getResourceAsString("/header_arduino.txt");
            footer = getResourceAsString("/footer_arduino.txt");

            compassos = "\nint melody[] = { %s };\nint noteDurations[] = { %s };";
            String notes = "", durations = "";

            // TabelaDeSimbolos.getEstruturas("__global__").stream().map(i -> i.generateArduinoCode()).collect(Collectors.joining(",\n"));
            List<Estrutura> estruturas = TabelaDeSimbolos.getEstruturas("__global__");
            for (int i = 0; i < estruturas.size(); ++i) {
                Estrutura e = estruturas.get(i);
                if (i != 0) {
                    notes += ", ";
                    durations += ", ";
                }

                notes += e.generateArduinoCode().a;
                durations += e.generateArduinoCode().b;
            }

            compassos = String.format(compassos, notes, durations);
            compassos += "\n";

            footer = String.format(footer, notes.split(",").length);
        }

        return header + compassos + footer;
    }
}
