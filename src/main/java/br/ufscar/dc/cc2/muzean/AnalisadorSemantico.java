package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.Arrays;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AnalisadorSemantico extends MuzeanBaseVisitor {

    @Override
    public Object visitFlag(MuzeanParser.FlagContext ctx) {
        TabelaDeSimbolos.adicionarSimbolo(ctx.NOTA().getText(), Tipo.TOM);
        TabelaDeSimbolos.adicionarSimbolo(ctx.ESCALA().getText(), Tipo.ESCALA);
        TabelaDeSimbolos.adicionarSimbolo(ctx.NUMERO().getText(), Tipo.COMPASSO);
        return super.visitFlag(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitFlag_op(MuzeanParser.Flag_opContext ctx) {
        TabelaDeSimbolos.adicionarSimbolo(ctx.NOTA().getText(), Tipo.TRANSPOSICAO);
        return super.visitFlag_op(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    @Override
    public Object visitSons(MuzeanParser.SonsContext ctx) {
        int count = 1 + ctx.b.size();
        if (count != TabelaDeSimbolos.getCompassos()){
            Saida.println("O compasso não tem o número de notas especificadas no cabeçalho", true);
        }
        return super.visitSons(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
}
