package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Júnior
 */
public class Loop implements Estrutura {

    private int numeroRep;
    private List<Estrutura> estrutura = new ArrayList<>();

    public Loop(int numeroRep) {
        this.numeroRep = numeroRep;
    }

    public void add(Estrutura s) {
        estrutura.add(s);
    }

    public void addAll(List<Estrutura> estrutura) {
        this.estrutura.addAll(estrutura);
    }

    @Override
    public String generateCode() {
        String out = "";
        String body = estrutura.stream().map(x -> x.generateCode()).collect(Collectors.joining(", \n"));

        for (int i = 0; i < numeroRep; ++i) {
            out += body;
            if (i < numeroRep - 1) {
                out += ",\n";
            }
        }

        return out;
    }

    @Override
    public String generateArduinoCode() {
        String out = "";
        String body = estrutura.stream().map(x -> x.generateArduinoCode()).collect(Collectors.joining(", \n"));

        for (int i = 0; i < numeroRep; ++i) {
            out += body;
            if (i < numeroRep - 1) {
                out += ",\n";
            }
        }

        return out;
    }
}
