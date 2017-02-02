package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.misc.Pair;

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
    public Pair<String, String> generateArduinoCode() {
        String notes_all = "", durations_all = "";
        String notes = "", durations = "";

        for (int i = 0; i < estrutura.size(); ++i) {
            Estrutura e = estrutura.get(i);

            if (i != 0) {
                notes += ", ";
                durations += ", ";
            }

            notes += e.generateArduinoCode().a;
            durations += e.generateArduinoCode().b;
        }

        for (int i = 0; i < numeroRep; ++i) {
            if (i != 0) {
                notes_all += ", ";
                durations_all += ", ";
            }

            notes_all += notes;
            durations_all += durations;
        }
        return new Pair<>(notes_all, durations_all);
    }
}
