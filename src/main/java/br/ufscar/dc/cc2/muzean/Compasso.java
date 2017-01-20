/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.muzean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Júnior
 */
public class Compasso extends Estrutura {

    private List<Som> sons = new ArrayList<>();

    public Compasso() {
    }

    public void add(Som s) {
        sons.add(s);
    }

    public void addAll(List<Som> sons) {
        this.sons = sons;
    }

    @Override
    public String toString() {
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

}
