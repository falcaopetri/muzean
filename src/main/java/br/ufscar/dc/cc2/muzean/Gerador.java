package br.ufscar.dc.cc2.muzean;

import java.util.stream.Collectors;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;

public class Gerador extends MuzeanBaseVisitor<Object> {
    
    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        String header = IOUtil.getResourceAsString("/header.txt");
        String footer = IOUtil.getResourceAsString("/footer.txt");
        
        String compassos = "compasses = [\n";
        compassos += TabelaDeSimbolos.getEstruturas("__global__").stream().map(i -> i.generateCode()).collect(Collectors.joining(",\n"));
        compassos += "\n]\n";
        
        return String.format(header, TabelaDeSimbolos.getCompassos()) + compassos + footer;
    }
}
