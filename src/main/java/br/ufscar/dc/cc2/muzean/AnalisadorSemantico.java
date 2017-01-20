package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AnalisadorSemantico extends MuzeanBaseVisitor {

    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        visitCabecalho(ctx.cabecalho());
        EstruturasTS estruturas = new EstruturasTS("Estruturas", (List<Estrutura>)visitEstruturas(ctx.estruturas()));
        TabelaDeSimbolos.adicionarEntrada(estruturas);
        return estruturas;
    }

    
    
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
        Compasso compasso = new Compasso();
        int count = 1 + ctx.b.size();
        if (count != TabelaDeSimbolos.getCompassos()){
            Saida.println("O compasso não tem o número de notas especificadas no cabeçalho", true);
        }
        compasso.add((Som)visitSom(ctx.a));
        for(MuzeanParser.SomContext x : ctx.b){
            compasso.add((Som)visitSom(x));
        }
        return compasso; 
    }
    
    

    @Override
    public Object visitSom(MuzeanParser.SomContext ctx) {
        Som s = null;
        if(ctx.nota() != null){
            s = new Som(ctx.nota().NOTA().getText() + ctx.nota().NUMERO().getText());
        }
        else if(ctx.acorde() != null){
            s = new Som(ctx.acorde().nota().NOTA().getText() + ctx.acorde().nota().NUMERO().getText());
        }
        else if(ctx.getStart().getText().equals("*")){
            s = new Som("*");
        }
        else if(ctx.getStart().getText().equals("-")){
            s = new Som("-");
        }
        return s; 
    }

    @Override
    public Object visitLoop(MuzeanParser.LoopContext ctx) {
        Loop loop = new Loop(Integer.parseInt(ctx.NUMERO().getText()));
        loop.addAll((List<Estrutura>)visitEstruturas(ctx.estruturas()));
        
        return loop; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitCompasso(MuzeanParser.CompassoContext ctx) {
        Compasso compasso = (Compasso)visitSons(ctx.sons());
        return compasso;
    }

    @Override
    public Object visitEstrutura(MuzeanParser.EstruturaContext ctx) {
         if(ctx.loop() != null){
             return visitLoop(ctx.loop());
         }
         else if(ctx.compasso() != null){
             return visitCompasso(ctx.compasso());
         }
         throw new UnsupportedOperationException("Alias não implementado!");
    }

    @Override
    public Object visitEstruturas(MuzeanParser.EstruturasContext ctx) {
        List<Estrutura> estruturas = new ArrayList<Estrutura>();
        for(MuzeanParser.EstruturaContext x : ctx.estrutura()){
            estruturas.add((Estrutura)visitEstrutura(x));
        }
        return estruturas;
    }
    
    
    
    
    
            
    
    
    
    
}
