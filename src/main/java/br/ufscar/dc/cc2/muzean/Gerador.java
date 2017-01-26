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

        String cabecalho = (String) visitCabecalho(ctx.cabecalho());

        String compassos = "compasses = [\n";
        compassos += TabelaDeSimbolos.getEstruturas().stream().map(i -> i.generateCode()).collect(Collectors.joining(", \n"));
        compassos += "\n]\n";

        return header + cabecalho + compassos + footer;
    }

    @Override
    public Object visitCabecalho(MuzeanParser.CabecalhoContext ctx) {
        // cabecalho : flags '\n' definicoes=definicao*;
        String out = (String) visitFlags(ctx.flags());

        out = ctx.definicao().stream().map(def -> (String) visitDefinicao(def)).reduce(out, String::concat);

        return out;
    }

    @Override
    public Object visitFlags(MuzeanParser.FlagsContext ctx) {
        return "";
    }

    @Override
    public Object visitDefinicao(MuzeanParser.DefinicaoContext ctx) {
        // definicao : '#' ALIAS ':' estruturas '\n'
        String out = ctx.ALIAS().getText() + " = " + /* get ALIAS da TS */ "[]" + "\n";
        return out;
    }
}
