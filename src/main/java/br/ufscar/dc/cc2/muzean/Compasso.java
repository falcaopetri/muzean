package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compasso implements Estrutura {

    private List<Som> sons = new ArrayList<>();

    public Compasso() {
    }

    public void add(Som s) {
        sons.add(s);
    }

    public void addAll(List<Som> sons) {
        this.sons = sons;
    }

    private List<Som> compressSons() {
        List<Som> sons = new ArrayList<>();
        sons.add(new Som(this.sons.get(0)));

        for (int i = 1; i < this.sons.size(); ++i) {
            Som som = this.sons.get(i);
            Som last_added_som = sons.get(sons.size() - 1);

            if (som.toString() == "*") {
                last_added_som.incrementDuration();
            } else {
                sons.add(som);
            }
        }

        return sons;
    }

    @Override
    public String generateCode() {
        List<Som> compressed_sounds = compressSons();

        String out = "[";
        out += " [";
        out += compressed_sounds.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
        out += "], [";
        out += compressed_sounds.stream().map(i -> i.getDuration().toString()).collect(Collectors.joining(", "));
        out += "] ";

        out += "]";
        return out;
    }

}
