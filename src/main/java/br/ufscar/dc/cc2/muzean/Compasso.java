package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compasso implements Estrutura {

    private List<Som> sons = new ArrayList<>();

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
            Som som = new Som(this.sons.get(i));
            Som last_added_som = sons.get(sons.size() - 1);

            if ("*".equals(som.toString())) {
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

    @Override
    public String generateArduinoCode() {
        List<Som> compressed_sounds = compressSons();
        Integer size = sons.size();

        String out = compressed_sounds.stream().map(i -> i.toArduinoCode() + ", " + (size / i.getDuration())).collect(Collectors.joining(", "));

        return out;
    }

}
