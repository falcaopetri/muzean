package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.antlr4.MuzeanBaseVisitor;
import main.antlr4.MuzeanParser;

public class AnalisadorSemantico extends MuzeanBaseVisitor {

    @Override
    public Object visitPrograma(MuzeanParser.ProgramaContext ctx) {
        visitCabecalho(ctx.cabecalho());
        EstruturasTS estruturas = new EstruturasTS("global", (List<Estrutura>) visitEstruturas(ctx.estruturas()));
        TabelaDeSimbolos.adicionarEntrada(estruturas);
        return estruturas;
    }

    @Override
    public Object visitDefinicao(MuzeanParser.DefinicaoContext ctx) {
        // definicao : '#' ALIAS ':' estruturas '\n'
        String name = ctx.ALIAS().getText();
        List<Estrutura> estruturas = (List<Estrutura>) visitEstruturas(ctx.estruturas());

        if (TabelaDeSimbolos.existeSimbolo(name)) {
            Saida.println("O Alias " + name + " já foi declarado!", true);
        } else {
            TabelaDeSimbolos.adicionarEntrada(new EstruturasTS(name, estruturas));
        }
        return null;
    }

    @Override
    public Object visitFlag(MuzeanParser.FlagContext ctx) {
        TabelaDeSimbolos.adicionarSimbolo(ctx.NOTA().getText(), Tipo.TOM);
        TabelaDeSimbolos.adicionarSimbolo(ctx.ESCALA().getText(), Tipo.ESCALA);
        TabelaDeSimbolos.adicionarSimbolo(ctx.NUMERO().getText(), Tipo.COMPASSO);
        return super.visitFlag(ctx);
    }

    @Override
    public Object visitFlag_op(MuzeanParser.Flag_opContext ctx) {
        TabelaDeSimbolos.adicionarSimbolo(ctx.SIGNED_NUMERO().getText(), Tipo.TRANSPOSICAO);
        return super.visitFlag_op(ctx);
    }

    @Override
    public Object visitSons(MuzeanParser.SonsContext ctx) {
        Compasso compasso = new Compasso();
        int count = 1 + ctx.b.size();
        if (count != TabelaDeSimbolos.getCompassos()) {
            Saida.println("Linha " + ctx.a.getStart().getLine() + ": O compasso não tem o número de notas especificadas no cabeçalho", true);
        }
        if (ctx.a.getText().equals("*")) {
            Saida.println("Linha " + ctx.a.getStart().getLine() + ": O compasso não pode ser iniciado com *", true);
        }
        compasso.add((Som) visitSom(ctx.a));
        for (MuzeanParser.SomContext x : ctx.b) {
            compasso.add((Som) visitSom(x));
        }
        return compasso;
    }

    @Override
    public Object visitSom(MuzeanParser.SomContext ctx) {
        Som s = null;
        if (ctx.nota() != null) {
            s = new Som(ctx.nota().NOTA().getText() + ctx.nota().NUMERO().getText());

            try {
                int number = Integer.parseInt(s.toString()) + TabelaDeSimbolos.getTransposicao();
                if (number < 0 || number > 127) {
                    Saida.println("Nota " + s.getNota() + " transposta em "
                            + (TabelaDeSimbolos.getTransposicao() > 0 ? "+" : "")
                            + TabelaDeSimbolos.getTransposicao() + " está fora do range MIDI [0, 127].");
                }
            } finally {
            }
        } else if (ctx.getStart().getText().equals("*")) {
            s = new Som("*");
        } else if (ctx.getStart().getText().equals("-")) {
            s = new Som("-");
        }
        return s;
    }

    @Override
    public Object visitLoop(MuzeanParser.LoopContext ctx
    ) {
        Loop loop = new Loop(Integer.parseInt(ctx.NUMERO().getText()));
        loop.addAll((List<Estrutura>) visitEstruturas(ctx.estruturas()));

        return loop;
    }

    @Override
    public Object visitCompasso(MuzeanParser.CompassoContext ctx
    ) {
        Compasso compasso = (Compasso) visitSons(ctx.sons());
        return compasso;
    }

    @Override
    public Object visitEstrutura(MuzeanParser.EstruturaContext ctx
    ) {
        if (ctx.loop() != null) {
            return visitLoop(ctx.loop());
        } else if (ctx.compasso() != null) {
            return visitCompasso(ctx.compasso());
        } else if (ctx.ALIAS() != null) {
            List<Estrutura> estruturas = TabelaDeSimbolos.getEstruturas(ctx.ALIAS().getText());
            Alias alias = null;

            if (estruturas == null) {
                Saida.println("Alias " + ctx.ALIAS().getText() + " não definido.");
            } else {
                alias = new Alias(ctx.ALIAS().getText(), estruturas);
            }
            return alias;
        }
        throw new UnsupportedOperationException("unreachable code");
    }

    @Override
    public Object visitEstruturas(MuzeanParser.EstruturasContext ctx
    ) {
        List<Estrutura> estruturas = new ArrayList<>();

        for (MuzeanParser.EstruturaContext x : ctx.estrutura()) {
            estruturas.add((Estrutura) visitEstrutura(x));
        }

        return estruturas;
    }
}
