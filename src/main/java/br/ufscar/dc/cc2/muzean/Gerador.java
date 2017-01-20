package br.ufscar.dc.cc2.muzean;

import java.util.Scanner;
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
        String compassos = "";

        for (Estrutura est : TabelaDeSimbolos.getEstruturas()) {
            compassos += est.toString();
        }

        return header + cabecalho + compassos + footer;
    }

    @Override
    public Object visitCabecalho(MuzeanParser.CabecalhoContext ctx) {
        // cabecalho : flags '\n' definicoes=definicao*;
        String out = (String) visitFlags(ctx.flags());

        for (MuzeanParser.DefinicaoContext def : ctx.definicao()) {
            out += (String) visitDefinicao(def);
        }

        return out;
    }

    @Override
    public Object visitDefinicao(MuzeanParser.DefinicaoContext ctx) {
        // definicao : '#' ALIAS ':' estruturas '\n'
        String out = ctx.ALIAS().getText() + " = " + /* get ALIAS da TS */ "[]" + "\n";
        return out;
    }
}
