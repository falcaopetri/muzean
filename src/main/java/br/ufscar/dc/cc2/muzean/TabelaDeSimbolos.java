package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Prof. Daniel Lucrédio
 */
public class TabelaDeSimbolos {

    /*
        Tabela de Símbolos reaproveitada de Construção de Compiladores 1.
        Modificada conforme o necessário.
     */
    static private List<EntradaTS> simbolos = new ArrayList<>();

    static public void adicionarSimbolo(String nome, Tipo tipo) {
        simbolos.add(new EntradaTS(nome, tipo));
    }

    static public void adicionarSimbolos(List<String> nomes, Tipo tipo) {
        for (String s : nomes) {
            simbolos.add(new EntradaTS(s, tipo));
        }
    }

    static public void adicionarEntrada(EntradaTS entrada) {
        simbolos.add(entrada);
    }

    static public boolean existeSimbolo(String nome) {
        return getSimbolo(nome) != null;
    }

    static public int getCompassos() {
        for (EntradaTS etds : simbolos) {
            if (etds.getTipo() == Tipo.COMPASSO) {
                return Integer.parseInt(etds.getNome());
            }
        }
        return 0;
    }

    static public EntradaTS getSimbolo(String nome) {
        // TODO remove (?!)
        // Formato esperado de nome: IDENT ('.' IDENT)*

        String nomes[] = nome.split("\\.", 2);

        for (EntradaTS etds : simbolos) {
            if (!etds.getNome().equals(nomes[0])) {
                // etds não faz parte do nome
                continue;
            }

            if (nomes.length == 1) {
                // Encontramos nossa entrada
                return etds;
            }

        }

        return null;
    }

    static List<Estrutura> getEstruturas() {
        for (EntradaTS etds : simbolos) {
            if (etds.getTipo() == Tipo.ESTRUTURAS) {
                EstruturasTS estruturas = (EstruturasTS) etds;
                return estruturas.getEstruturas();
            }
        }

        throw new RuntimeException("Lista de estruturas não encontrada");
    }

    @Override
    public String toString() {
        String ret = "";
        for (EntradaTS etds : simbolos) {
            ret += "\n   " + etds;
        }
        return ret;
    }

}
