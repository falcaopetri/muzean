package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Júnior
 */
public class Alias implements Estrutura {

    private List<Estrutura> estruturas = new ArrayList<>();
    private String name;

    Alias(String text, List<Estrutura> estruturas) {
        name = text;
        this.estruturas = estruturas;
    }

    public void addLista(Estrutura s) {
        estruturas.add(s);
    }

    @Override
    public String generateCode() {
        return estruturas.stream().map(i -> i.generateCode()).collect(Collectors.joining(",\n"));
    }
}
