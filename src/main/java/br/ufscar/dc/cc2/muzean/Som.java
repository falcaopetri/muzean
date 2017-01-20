/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.muzean;

/**
 *
 * @author Júnior
 */
public class Som {

    private String nota;
    private int duration;

    void incrementDuration() {
        duration++;
    }

    String getNota() {
        return nota;
    }

    Som(Som s) {
        this.nota = s.getNota();
        this.duration = s.getDuration();
    }

    Integer getDuration() {
        return duration;
    }

    public Som(String nota) {
        this.nota = nota;
        this.duration = 1;
    }

    @Override
    public String toString() {
        /*
        G5 = 79
        F5 = 77
        d5 = 75 # D#5
        C5 = 72
        a4 = 70 # A#4
        */
        if ("G5".equals(nota)) {
            return "79";
        }
        else if ("F5".equals(nota)) {
            return "77";
        }
        else if ("D#5".equals(nota)) {
            return "75";
        }
        else if ("C5".equals(nota)) {
            return "72";
        }
        else if ("A#4".equals(nota)) {
            return "70";
        }
        return nota;
    }
}
